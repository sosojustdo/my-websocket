package com.cloudyoung.wx.comet.model;

import java.io.Serializable;

/**
 * 
 * Description:websocket微信对话实体
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:45:15  by 代鹏（daipeng.456@gmail.com）创建
 */
public class WebsocketDialogueVo implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 公众号appId
     */
    private String authorizerAppid;
    /**
     * 粉丝用户Id
     */
    private Long wxUserId;
    /**
     * openId
     */
    private String openId;
    /**
     * 头像地址
     */
    private String headImgUrl;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息内容文本 :text图片 :image音频 :voice视频 :video图文 :news
     */
    private String content;
    /**
     * 内容展示地址（messageFrom 为4时有效）        
     */
    private String url;
    /**
     * 消息来源标识 1、高级群发消息发送数、2、客服消息发送数 、3、自动回复数 4、接收粉丝消息 、5 客服一对一回复粉丝消息
     */
    private Integer messageFrom;
    /**
     * 发送消息时间
     */
    private long  crationTime;
    /**
     * 客户端唯一标识
     */
    private String clientSign;
    
    public String getAuthorizerAppid() {
        return authorizerAppid;
    }
    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }
    public Long getWxUserId() {
        return wxUserId;
    }
    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getMessageFrom() {
        return messageFrom;
    }
    public void setMessageFrom(Integer messageFrom) {
        this.messageFrom = messageFrom;
    }
    public long getCrationTime() {
        return crationTime;
    }
    public void setCrationTime(long crationTime) {
        this.crationTime = crationTime;
    }
    public String getClientSign() {
        return clientSign;
    }
    public void setClientSign(String clientSign) {
        this.clientSign = clientSign;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
