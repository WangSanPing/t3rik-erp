package com.t3rik.common.helper;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 检查Redisson是否正确关闭
 *
 * @author t3rik
 * @date 2025/4/7 17:17
 */
@Slf4j
@Component
public class RedissonLifecycleHelper {
    @Resource
    private RedissonClient redissonClient;

    @PreDestroy
    public void shutdownRedisson() {
        log.info(">> 正在关闭 RedissonClient ...");

        if (redissonClient != null && !redissonClient.isShutdown()) {
            redissonClient.shutdown();
            log.info(">> RedissonClient 已关闭");
        } else {
            log.info(">> RedissonClient 已经是关闭状态或为 null");
        }
    }
}
