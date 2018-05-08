package com.cloudyoung.wx.comet.server;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.cloudyoung.wx.comet.constant.WebsocketConstants;

/**
 * @Description websocket握手前的拦截器
 * @date  2018年3月7日上午9:00:45
 * @version V1.0  
 * <p>Copyright (c) Department of Research and Development/Beijing.</p>
 */
@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    
    private static final Logger logger = LogManager.getLogger(WebSocketHandshakeInterceptor.class);

    /**
     * @Description 握手前拦截器 
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception      
     * @version V1.0
     * 2018年3月9日 下午5:36:20
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
                request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
            }
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String appId = servletRequest.getServletRequest().getParameter("appId");
            String module = servletRequest.getServletRequest().getParameter("module");
            String clientSign = servletRequest.getServletRequest().getParameter("clientSign");
            if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(module)) {
                if (WebsocketConstants.WX_WEBSOCKET_DIALOGUE_MODULE.equals(module)) {
                    attributes.put(WebsocketConstants.WX_WEBSOCKET_APPID_KEY, appId);
                    attributes.put(WebsocketConstants.WX_WEBSOCKET_MODULE_KEY, WebsocketConstants.WX_WEBSOCKET_DIALOGUE_MODULE);
                    attributes.put(WebsocketConstants.WX_WEBSOCKET_CLIENT_SIGN, clientSign);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
