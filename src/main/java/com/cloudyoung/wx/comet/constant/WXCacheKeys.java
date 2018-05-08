package com.cloudyoung.wx.comet.constant;

/**
 * Description: 微信相关Cache Key Prefix
 * Copyright (c) Department of Research and Development/Beijing
 * All Rights Reserved.
 * @version 1.0  2017年9月1日 下午2:25:16  by 代鹏（daipeng@cloud-young.com）创建
 */
public interface WXCacheKeys {

    /**微信公众号缓存的access_token**/
    public static final String WX_SOA_ACCESS_TOKEN = "WX_SOA_ACCESS_TOKEN_";

    /**微信开放平台component_verify_ticket值 **/
    public static final String WX_OPEN_PLATFORM_COMPONENT_VERIFY_TICKET = "WX_OPEN_PLATFORM_COMPONENT_VERIFY_TICKET_";

    /**微信开放平台component_access_token值 **/
    public static final String WX_OPEN_PLATFORM_COMPONENT_ACCESS_TOKEN = "WX_OPEN_PLATFORM_COMPONENT_ACCESS_TOKEN_";

    /**微信开放平台pre_auth_code值 **/
    public static final String WX_OPEN_PLATFORM_PRE_AUTH_CODE = "WX_OPEN_PLATFORM_PRE_AUTH_CODE_";

    /**微信开放平台authorizer_access_token值 **/
    public static final String WX_OPEN_PLATFORM_AUTHORIZER_ACCESS_TOKEN = "WX_OPEN_PLATFORM_AUTHORIZER_ACCESS_TOKEN_";

    /**微信开放平台authorizer_refresh_token值 **/
    public static final String WX_OPEN_PLATFORM_AUTHORIZER_REFRESH_TOKEN = "WX_OPEN_PLATFORM_AUTHORIZER_REFRESH_TOKEN_";

    /**微信开放平台authorizer_appid值 **/
    public static final String WX_OPEN_PLATFORM_AUTHORIZER_APPID = "WX_OPEN_PLATFORM_AUTHORIZER_APPID_";

    /**微信开放平台authorizer_code值 **/
    public static final String WX_OPEN_PLATFORM_AUTHORIZER_CODE = "WX_OPEN_PLATFORM_AUTHORIZER_CODE_";

    /**微信消息缓存的去重key**/
    public static final String WX_MESSAGE_EXCLUDE_REPEAT = "WX_MESSAGE_EXCLUDE_REPEAT";

    /**component accesstoken sufixx:type获取微信jssdk相关ticket**/
    public static final String WX_COMPONENT_JS_SDK_TICKET = "WX_COMPONENT_JS_SDK_TICKET_";
    
    /**公众号accesstoken sufixx:appId获取微信jssdk相关ticket**/
    public static final String WX_JS_SDK_TICKET = "WX_JS_SDK_TICKET_";
   
    /**公众号accesstoken sufixx:appId获取微信jssdk apiticket**/
    public static final String WX_CARD_SDK_TICKET = "WX_CARD_SDK_TICKET_";

    /**获取登录用户key**/
    public static final String WX_LOGIN_DEALER_OR_MANUFACTURER_INFO = "WX_LOGIN_DEALER_OR_MANUFACTURER_INFO_";

    /**小程序提交审核id备份**/
    public static final String WX_MINIPROGRAM_SUBMIT_AUDIT_ID = "WX_MINIPROGRAM_SUBMIT_AUDIT_ID_";

    /**用户登录最后一次访问公众号key **/
    public static final String WX_USER_LAST_VISIT_AUTHORIZER_APPID = "WX_USER_LAST_VISIT_AUTHORIZER_APPID_";
    
    /**用户最近访问公众号key**/
    public static final String WX_USER_LAST_ACTIVITY_AUTHORIZER_APPID = "WX_USER_LAST_ACTIVITY_AUTHORIZER_APPID_";
    
    /**获取某个公众号近七日粉丝数据以及发送、接收数据、图文阅读和分享keyprefix_appid**/
    public static final String STATICTIS_FANS_INCRORDECR_AND_MESSAGE_DATA = "STATICTIS_FANS_INCRORDECR_AND_MESSAGE_DATA_";
    
    /**获取所有公众号近七日粉丝数据以及发送、接收数据、图文阅读和分享key**/
    public static final String STATICTIS_ALL_FANS_INCRORDECR_AND_MESSAGE_DATA = "STATICTIS_ALL_FANS_INCRORDECR_AND_MESSAGE_DATA";
    
    /** 公众号图文消息统计数据key前缀_appid **/
    public static final String STATICTIS_PUBLIC_PROGRAM_ARTICLE_SUMMARY_DATA = "STATICTIS_PUBLIC_PROGRAM_ARTICLE_SUMMARY_DATA_";

    /** 公众号图文消息小时统计数据key前缀_appid **/
    public static final String STATICTIS_PUBLIC_PROGRAM_ARTICLE_SUMMARY_HOUR_DATA = "STATICTIS_PUBLIC_PROGRAM_ARTICLE_SUMMARY_HOUR_DATA_";
    
    /** 公众号模板id缓存key前缀 hash**/
    public static final String WX_PUBLIC_PROGRAM_TEMPLATE_ID_KEY_PREFIX = "WX_PUBLIC_PROGRAM_TEMPLATE_ID_KEY_PREFIX_";
    
    /** 公众号模板id缓存key前缀 string**/
    public static final String WX_SYN_MATERIAL_DATA_RECORD_STATUS = "WX_SYN_MATERIAL_DATA_RECORD_STATUS_";
    
    /** 消息统计数据key前缀 string**/
    public static final String WX_MESSAGE_STATISTICE_DATA = "WX_MESSAGE_STATISTICE_DATA_";
    
    /** 小程序模板id缓存key前缀 hash**/
    public static final String WX_MINI_PROGRAM_TEMPLATE_ID_KEY_PREFIX = "WX_MINI_PROGRAM_TEMPLATE_ID_KEY_PREFIX_";
   
    /** websocket微信对话订阅topic 通道**/
    public static final String WX_EBSOCKETK_DIALOGUE_TOPIC = "WX_EBSOCKETK_DIALOGUE_TOPIC_";

    /**微信授权校验码前缀*/
    public static final String WX_AUTH_CHECK_CODE = "WX_AUTH_CHECK_CODE_";
    
    /** 增加用户对话表key**/
    public static final String WX_USER_DIALOG_PREFIX = "WX_USER_DIALOG_PREFIX";
    
    /** 微信素材设置不可修改状态缓存key前缀，example:key_{appId}_{mediaId}**/
    public static final String WX_AUTHORIZER_APPID_NO_OPRATION_MEDIAID = "WX_AUTHORIZER_APPID_NO_OPRATION_MEDIAID_";

    /**微信授权页面，授权失败重定向到授权页面次数控制key前缀**/
    public static final String OPEN_PLATFORM_AUTH_PAGE_ACCESS_LIMIT = "WX_OPEN_PLATFORM_AUTH_PAGE_ACCESS_LIMIT_";
    
}
