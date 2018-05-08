package com.cloudyoung.wx.comet.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.cloudyoung.wx.comet.constant.WXCacheKeys;
import com.cloudyoung.wx.comet.constant.WebsocketConstants;
import com.cloudyoung.wx.comet.jedis.JedisTemplate;
import com.cloudyoung.wx.comet.util.LogUtil;
import com.cloudyoung.wx.comet.util.UUIDUtil;

/**
 * Description:项目初始化根据服务编号订阅redis topic
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:51:23  by 代鹏（daipeng.456@gmail.com）创建
 */
@Component
public class WebSocketInit implements InitializingBean {

    private static final Logger logger = LogManager.getLogger(WebSocketInit.class);

    @Autowired
    private JedisTemplate jedisTemplate;

    @Autowired
    private SubscribeListener subscribeListener;

    public static Map<String, String> serverIdMap;

    public int listenerId;
    
    static {
        serverIdMap = new HashMap<String, String>();
    }

    @Override
    public void afterPropertiesSet() {
        try {
            String uuid = UUIDUtil.uuid();
            serverIdMap.put(WebsocketConstants.WX_WEBSOCKET_SERVER_ID, uuid);
            SubThread subThread = new SubThread(WXCacheKeys.WX_EBSOCKETK_DIALOGUE_TOPIC+ uuid);
            subThread.start();
            addStopHook();
            LogUtil.info(logger, "afterPropertiesSet_invoker", String.format("uuid:%s", uuid));
        } catch (Exception e) {
            LogUtil.error(logger, e, "WebSocketListener_invoke_error", "");
        }
    }
    
    private class SubThread extends Thread {
        private String topic;

        public SubThread(String topic) {
            this.topic = topic;
        }
        @Override
        public void run() {
                jedisTemplate.subscribe(subscribeListener, topic);
        }
    }
    
    /**
     * Description:JVM关闭时，解除对应关系
     * @Version1.0 2018年5月8日 下午4:50:52 by 代鹏（daipeng.456@gmail.com）创建
     */
    private void addStopHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    String serverId = serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
                    jedisTemplate.unsubscribe(subscribeListener, WXCacheKeys.WX_EBSOCKETK_DIALOGUE_TOPIC + serverId);
                    Collection<ArrayList<WebSocketSession>> values = SystemWebSocketHandler.getWxuser().values();
                    for (ArrayList<WebSocketSession> arrayList : values) {
                        for (WebSocketSession webSocketSession : arrayList) {
                            if (null!=webSocketSession&&webSocketSession.isOpen()) {
                                closeWebsocket(webSocketSession);
                            }
                        }
                    }
                    SystemWebSocketHandler.getWxuser().clear();
                    serverIdMap.clear();
                    LogUtil.info(logger, "StopHook_invoker", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Description:服务结束关闭websocket
     * @Version1.0 2018年5月8日 下午4:50:35 by 代鹏（daipeng.456@gmail.com）创建
     * @param session
     */
    private void closeWebsocket(WebSocketSession session) {
        Map<String, Object> handshakeAttributes = session.getHandshakeAttributes();
        if (null != handshakeAttributes) {
            String appId = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_APPID_KEY).toString();
            String module = handshakeAttributes.get(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY).toString();
            LogUtil.info(logger, "serverOvercloseWebsocket_invoke_method", String.format("appId:%s, module:%s", appId, module));
            try {
                session.close();
                if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(module)) {
                    jedisTemplate.deleteKey(module + appId);
                }
            } catch (Exception e) {
                LogUtil.error(logger, e, "serverOvercloseWebsocket_invoke_fail", String.format("appId:%s,module:%s", appId, module));
            }
        }
    }
}
