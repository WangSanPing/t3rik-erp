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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Set;

/**
 * WebSocket 处理器
 * 处理WebRTC信令消息的接收和广播
 * 支持多人视频会议
 */
public class MeetingWSHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MeetingWSHandler.class);

    // 所有连接的会话
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // 房间管理：roomId -> 会话集合
    private static final ConcurrentHashMap<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    // 会话房间映射：sessionId -> roomId
    private static final ConcurrentHashMap<String, String> sessionRoomMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 建立连接时触发
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        logger.info("新连接建立: {}, 当前连接数: {}", session.getId(), sessions.size());

        // 发送JSON格式欢迎消息
        MessageData welcomeMsg = new MessageData();
        welcomeMsg.setType("welcome");
        welcomeMsg.setName("欢迎连接到WebRTC信令服务器！请发送join消息加入房间。");
        try {
            String jsonMessage = objectMapper.writeValueAsString(welcomeMsg);
            session.sendMessage(new TextMessage(jsonMessage));
        } catch (IOException e) {
            logger.error("发送欢迎消息失败: {}", e.getMessage());
        }
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

            // 处理join消息，加入房间
            if ("join".equals(msgData.getType())) {
                String roomId = "default-room"; // 默认房间，可以根据需要修改
                String userName = msgData.getName() != null ? msgData.getName() : "未知用户";

                joinRoom(session, roomId);

                // 通知其他用户
                MessageData notifyMsg = new MessageData();
                notifyMsg.setType("join");
                notifyMsg.setName(userName);

                broadcastToRoom(roomId, notifyMsg, session);

                logger.info("用户 {} 加入房间 {}, 房间人数: {}",
                    userName, roomId, getRoomSize(roomId));
                return;
            }

            // 获取发送者所在的房间
            String roomId = sessionRoomMap.get(session.getId());

            if (roomId == null) {
                logger.warn("用户 {} 未加入任何房间", session.getId());
                sendMessage(session, "请先发送join消息加入房间");
                return;
            }

            // 广播消息给同房间的其他用户（除了发送者）
            broadcastToRoom(roomId, msgData, session);

            logger.debug("消息已广播到房间 {}, 发送者: {}", roomId, session.getId());

        } catch (Exception e) {
            logger.error("处理消息时出错: {}", e.getMessage(), e);
            sendMessage(session, "错误：消息格式不正确 - " + e.getMessage());
        }
    }

    /**
     * 连接关闭时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);

        // 从房间中移除用户
        String roomId = sessionRoomMap.remove(session.getId());
        if (roomId != null) {
            Set<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null) {
                roomSessions.remove(session);
                logger.info("用户 {} 离开房间 {}, 剩余人数: {}", session.getId(), roomId, roomSessions.size());

                // 如果房间为空，删除房间
                if (roomSessions.isEmpty()) {
                    rooms.remove(roomId);
                    logger.info("房间 {} 已空，已删除", roomId);
                }
            }
        }

        logger.info("连接关闭: {}, 原因: {}, 当前连接数: {}", session.getId(), status, sessions.size());
    }

    /**
     * 传输错误时触发
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket传输错误: {}", exception.getMessage(), exception);
        sessions.remove(session);

        // 从房间中移除用户
        String roomId = sessionRoomMap.remove(session.getId());
        if (roomId != null) {
            Set<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null) {
                roomSessions.remove(session);
                logger.info("用户 {} 因错误离开房间 {}, 剩余人数: {}", session.getId(), roomId, roomSessions.size());
            }
        }
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
     * 加入房间
     */
    private void joinRoom(WebSocketSession session, String roomId) {
        rooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        sessionRoomMap.put(session.getId(), roomId);
        logger.debug("用户 {} 加入房间 {}", session.getId(), roomId);
    }

    /**
     * 广播消息给指定房间的所有客户端（除了发送者）
     */
    private void broadcastToRoom(String roomId, MessageData message, WebSocketSession excludeSession) {
        try {
            Set<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions == null || roomSessions.isEmpty()) {
                logger.warn("房间 {} 为空，无法广播消息", roomId);
                return;
            }

            String jsonMessage = objectMapper.writeValueAsString(message);
            int count = 0;

            for (WebSocketSession session : roomSessions) {
                if (session.equals(excludeSession) || !session.isOpen()) {
                    continue;
                }

                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                    count++;
                } catch (IOException e) {
                    logger.error("发送消息给 {} 失败: {}", session.getId(), e.getMessage());
                }
            }

            logger.debug("消息已广播到房间 {}, 接收者数量: {}", roomId, count);
        } catch (Exception e) {
            logger.error("广播消息到房间 {} 时出错: {}", roomId, e.getMessage(), e);
        }
    }

    /**
     * 获取房间大小
     */
    private int getRoomSize(String roomId) {
        Set<WebSocketSession> roomSessions = rooms.get(roomId);
        return roomSessions != null ? roomSessions.size() : 0;
    }

    /**
     * 广播消息给所有客户端（除了发送者）- 保留兼容性
     */
    private void broadcastMessage(MessageData message, String senderId) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            int count = 0;

            for (WebSocketSession session : sessions) {
                if (session.getId().equals(senderId) || !session.isOpen()) {
                    continue;
                }

                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                    count++;
                } catch (IOException e) {
                    logger.error("发送消息给 {} 失败: {}", session.getId(), e.getMessage());
                }
            }

            logger.info("消息已广播给 {} 个客户端", count);
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
        private String name; // 用于join消息的用户名
        private String from; // 发送者ID（用于多人会议）
    }
}
