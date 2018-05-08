package com.cloudyoung.wx.comet.server;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cloudyoung.wx.comet.constant.WXCacheKeys;
import com.cloudyoung.wx.comet.constant.WebsocketConstants;
import com.cloudyoung.wx.comet.model.WebsocketDialogueVo;

import redis.clients.jedis.JedisPubSub;
/**
 * 
 * Description:订阅监听类
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:54:33  by 代鹏（daipeng.456@gmail.com）创建
 */
@Component
public class SubscribeListener extends JedisPubSub {
    
    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;
    
    public SubscribeListener() {
        
    }
    /**
     * @Description 订阅发布监听 
     * @param channel
     * @param message      
     * @version V1.0
     * 2018年3月20日 下午5:44:40
     */
    @Override  
    public void onMessage(String channel, String message) { 
        String serverId = WebSocketInit.serverIdMap.get(WebsocketConstants.WX_WEBSOCKET_SERVER_ID);
        if ((WXCacheKeys.WX_EBSOCKETK_DIALOGUE_TOPIC+serverId).equals(channel) && StringUtils.isNotBlank(message)) {
            WebsocketDialogueVo websocketDialogueVo = JSON.parseObject(message, WebsocketDialogueVo.class);
            if (null != websocketDialogueVo) {
                systemWebSocketHandler.sendMessageToUser(websocketDialogueVo.getAuthorizerAppid(), WebsocketConstants.WX_WEBSOCKET_DIALOGUE_MODULE,websocketDialogueVo);
            }
        }
    }
    /**
     * @Description 消息订阅监听 
     * @param channel
     * @param subscribedChannels      
     * @version V1.0
     * 2018年3月20日 下午5:45:16
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
    }
    /**
     * @Description 消息取消订阅监听 
     * @param channel
     * @param subscribedChannels      
     * @version V1.0
     * 2018年3月20日 下午5:45:55
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
    }

}
