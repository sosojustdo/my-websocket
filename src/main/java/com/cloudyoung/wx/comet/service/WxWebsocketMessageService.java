package com.cloudyoung.wx.comet.service;

import com.cloudyoung.wx.comet.model.WebsocketDialogueVo;

public interface WxWebsocketMessageService {
    
    public Boolean sendMessage(WebsocketDialogueVo websocketDialogueVo);

}
