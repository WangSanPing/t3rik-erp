package com.t3rik.framework.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket 配置类
 * 配置WebSocket连接和信令服务器
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 一对一聊天
        registry.addHandler(new OneOnOneWSHandler(), "/websocket/one-on-one")
                .setAllowedOrigins("*");
        // 多人会议
        registry.addHandler(new MeetingWSHandler(), "/websocket/meeting")
                .setAllowedOrigins("*");
    }
}
