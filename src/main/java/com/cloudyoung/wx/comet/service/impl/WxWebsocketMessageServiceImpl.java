package com.cloudyoung.wx.comet.service.impl;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cloudyoung.wx.comet.constant.WXCacheKeys;
import com.cloudyoung.wx.comet.constant.WebsocketConstants;
import com.cloudyoung.wx.comet.jedis.JedisTemplate;
import com.cloudyoung.wx.comet.model.WebsocketDialogueVo;
import com.cloudyoung.wx.comet.server.SystemWebSocketHandler;
import com.cloudyoung.wx.comet.server.WebSocketInit;
import com.cloudyoung.wx.comet.service.WxWebsocketMessageService;
import com.cloudyoung.wx.comet.util.LogUtil;

/**
 * 
 * Description:微信推送消息websocket 业务接口实现
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:46:29  by 代鹏（daipeng.456@gmail.com）创建
 */
@Service("wxWebsocketMessageService")
public class WxWebsocketMessageServiceImpl implements WxWebsocketMessageService {

    private static final Logger logger = LogManager.getLogger(WxWebsocketMessageServiceImpl.class);
    
    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;
    
    @Autowired
    private JedisTemplate jedisTemplate;
    
    @Override
    public Boolean sendMessage(WebsocketDialogueVo websocketDialogueVo) {
        try {
            String key=WebsocketConstants.WX_WEBSOCKET_DIALOGUE_MODULE+websocketDialogueVo.getAuthorizerAppid();
            Set<String> setMembers = jedisTemplate.getSetMembers(key);
            if(null!=setMembers&&setMembers.size()>0) {
                String localServerId=WebSocketInit.serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
                for(String serverId:setMembers) {
                    if(localServerId.equals(serverId)) {
                        systemWebSocketHandler.sendMessageToUser(websocketDialogueVo.getAuthorizerAppid(), WebsocketConstants.WX_WEBSOCKET_DIALOGUE_MODULE, websocketDialogueVo);
                    }else {
                        Long publish = jedisTemplate.publish(WXCacheKeys.WX_EBSOCKETK_DIALOGUE_TOPIC+serverId, JSON.toJSONString(websocketDialogueVo));
                        if(publish==0) {
                         jedisTemplate.removeSetMember(key, serverId);
                        }
                    }
                }
                return true;
            }
            jedisTemplate.deleteKey(key);
            return false;
        } catch (Exception e) {
            LogUtil.error(logger, e, "sendMessage_invoke_error", "");
        }
        return false;
    }
}
