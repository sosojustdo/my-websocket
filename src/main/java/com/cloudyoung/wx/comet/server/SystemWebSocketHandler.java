package com.cloudyoung.wx.comet.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.cloudyoung.wx.comet.constant.WebsocketConstants;
import com.cloudyoung.wx.comet.jedis.JedisTemplate;
import com.cloudyoung.wx.comet.model.WebsocketDialogueVo;

/**
 * 
 * Description:Websocket处理类
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:52:23  by 代鹏（daipeng.456@gmail.com）创建
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LogManager.getLogger(SystemWebSocketHandler.class);

    private static final Map<String, ArrayList<WebSocketSession>> wxUser;

    @Autowired
    private JedisTemplate jedisTemplate;
    
    static {
        wxUser = new ConcurrentHashMap<String, ArrayList<WebSocketSession>>();
    }

    /**
     * websocket 客户端建立握手连接
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        Map<String, Object> handshakeAttributes = session.getHandshakeAttributes();
        String appId = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_APPID_KEY).toString();
        String module = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY).toString();
        String serverId = WebSocketInit.serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
        if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(module)) {
            ArrayList<WebSocketSession> wxUserList = wxUser.get(module + appId);
            if (wxUserList == null) {
                wxUserList = new ArrayList<>();
            }
            jedisTemplate.addSetMember(module+appId, serverId);
            wxUserList.add(session);
            wxUser.put(module + appId, wxUserList);
        }
    }

    /**
     * @Description 消息处理 
     * @param session
     * @param message
     * @throws Exception      
     * @version V1.0
     * 2018年3月9日 上午10:48:13
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
        if ("ping".equals(message.getPayload().toString())) {
            session.sendMessage(new TextMessage("pong"));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description websocket 客户端异常 
     * @param session
     * @param exception
     * @throws Exception      
     * @version V1.0
     * 2018年3月8日 上午10:09:47
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        Map<String, Object> handshakeAttributes = session.getHandshakeAttributes();
        if (null != handshakeAttributes) {
            String appId = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_APPID_KEY).toString();
            String module = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY).toString();
            String serverId = WebSocketInit.serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
            try {
                if (session.isOpen()) {
                    session.close();
                }
                if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(module)) {
                    ArrayList<WebSocketSession> wxUserList = wxUser.get(module + appId);
                    if (wxUserList != null && wxUserList.size() > 0) {
                        wxUserList.remove(session);
                    }
                    if (wxUserList != null && wxUserList.size() == 0) {
                        wxUser.remove(module + appId);
                        jedisTemplate.removeSetMember(module + appId, serverId);
                    } 
                    if (wxUserList != null && wxUserList.size() > 0) {
                        wxUser.put(module + appId, wxUserList);
                    }
                    handshakeAttributes.remove(WebsocketConstants.WX_WEBSOCKET_APPID_KEY);
                    handshakeAttributes.remove(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * @Description websocket 客户端关闭连接
    * @param session
    * @param closeStatus
    * @throws Exception      
    * @version V1.0
    * 2018年3月8日 上午10:09:21
    */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Map<String, Object> handshakeAttributes = session.getHandshakeAttributes();
        if (null != handshakeAttributes) {
            String appId = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_APPID_KEY).toString();
            String module = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY).toString();
            String serverId = WebSocketInit.serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
            try {
                if (session.isOpen()) {
                    session.close();
                }
                if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(module)) {
                    ArrayList<WebSocketSession> wxUserList = wxUser.get(module + appId);
                    if (wxUserList != null && wxUserList.size() > 0) {
                        wxUserList.remove(session);
                    }
                    if (wxUserList != null && wxUserList.size() == 0) {
                        wxUser.remove(module + appId);
                        jedisTemplate.removeSetMember(module + appId, serverId);
                    } 
                    if (wxUserList != null && wxUserList.size() > 0) {
                        wxUser.put(module + appId, wxUserList);
                    }
                    handshakeAttributes.remove(WebsocketConstants.WX_WEBSOCKET_APPID_KEY);
                    handshakeAttributes.remove(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description 是否支持部分消息 
     * @return      
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月9日 上午10:46:57
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * @Description 微信粉丝单发消息
     * @param sign
     * @param module
     * @param websocketDialogueVo
     * @return    
     * @return Boolean     
     * @version V1.0
     * 2018年3月6日 下午12:03:35
     */
    public Boolean sendMessageToUser(String sign, String module, WebsocketDialogueVo websocketDialogueVo) {
        ArrayList<WebSocketSession> wxUserList = wxUser.get(module + sign);
        Boolean isSuccess = false;
        if (wxUserList != null) {
            for (int i = 0; i < wxUserList.size(); i++) {
                WebSocketSession user = wxUserList.get(i);
                if (sign.equals((String) user.getHandshakeAttributes().get(WebsocketConstants.WX_WEBSOCKET_APPID_KEY))) {
                    try {
                        if (user.isOpen()) {
                            String clientSign = (String) user.getHandshakeAttributes().get(WebsocketConstants.WX_WEBSOCKET_CLIENT_SIGN);
                            websocketDialogueVo.setClientSign(clientSign);
                            TextMessage message = new TextMessage(JSON.toJSONString(websocketDialogueVo));
                            isSuccess = true;
                            user.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return isSuccess;
    }

    public static Map<String, ArrayList<WebSocketSession>> getWxuser() {
        return wxUser;
    }

}
