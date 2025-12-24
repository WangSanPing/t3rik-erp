package com.t3rik.framework.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket 处理器
 * 处理WebRTC信令消息的接收和广播
 */
public class OneOnOneWSHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(OneOnOneWSHandler.class);
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 建立连接时触发
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        logger.info("新连接建立: {}, 当前连接数: {}", session.getId(), sessions.size());

        // 发送欢迎消息
        sendMessage(session, "欢迎连接到WebRTC信令服务器！");
    }

    /**
     * 接收文本消息时触发
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            logger.info("收到消息 from {}: {}", session.getId(), message.getPayload());

            // 解析JSON消息
            MessageData msgData = objectMapper.readValue(message.getPayload(), MessageData.class);

            // 广播消息给其他所有客户端（除了发送者）
            broadcastMessage(msgData, session.getId());

        } catch (Exception e) {
            logger.error("处理消息时出错: {}", e.getMessage(), e);
            sendMessage(session, "错误：消息格式不正确");
        }
    }

    /**
     * 连接关闭时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.info("连接关闭: {}, 原因: {}, 当前连接数: {}", session.getId(), status, sessions.size());
    }

    /**
     * 传输错误时触发
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket传输错误: {}", exception.getMessage(), exception);
        sessions.remove(session);
    }

    /**
     * 发送消息给指定会话
     */
    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            logger.error("发送消息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 广播消息给所有客户端（除了发送者）
     */
    private void broadcastMessage(MessageData message, String senderId) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            sessions.stream()
                    .filter(s -> !s.getId().equals(senderId) && s.isOpen())
                    .forEach(session -> {
                        try {
                            session.sendMessage(new TextMessage(jsonMessage));
                        } catch (IOException e) {
                            logger.error("广播消息失败: {}", e.getMessage(), e);
                        }
                    });
            logger.info("消息已广播给 {} 个客户端", sessions.size() - 1);
        } catch (Exception e) {
            logger.error("广播消息时出错: {}", e.getMessage(), e);
        }
    }

    /**
     * 消息数据结构
     */
    @Data
    public static class MessageData {
        private String type;
        private Object offer;
        private Object answer;
        private Object candidate;
        private Long timestamp;
    }
}
