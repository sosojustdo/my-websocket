package com.cloudyoung.wx.comet.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * Description:项目启动配置websocket服务
 * All Rights Reserved.
 * @version 1.0  2018年5月8日 下午4:59:33  by 代鹏（daipeng.456@gmail.com）创建
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    private static final Logger logger = LogManager.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.info("注册webSocket地址开始");
        registry.addHandler(systemWebSocketHandler(), "/webSocketServer").addInterceptors(handshake());
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }

    @Bean
    public WebSocketHandshakeInterceptor handshake() {
        return new WebSocketHandshakeInterceptor();
    }

}
