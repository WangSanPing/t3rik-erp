package com.t3rik.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author t3rik
 * @date 2024/4/25 16:29
 */
@Slf4j
@Component
public class RedisHeartbeatTask {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 20分钟执行一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 20)
    public void heartbeat() {
        try (RedisConnection connection = redisTemplate.getConnectionFactory().getConnection()) {
            byte[] ping = (byte[]) connection.execute("PING");
            String result = new String(ping);
            log.info("定时检查redis连接，结果: {},执行时间: {}", result, LocalDate.now());
        } catch (Exception e) {
            log.error("定时检查redis连接失败，原因: {},执行时间: {}", e.getMessage(), LocalDate.now());
        }
    }
}
