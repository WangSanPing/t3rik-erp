package com.t3rik.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置
 *
 * @author t3rik
 * @date 2025/3/30 22:21
 */
@Data
@Configuration
// application-t3rik配置文件中的t3rik-platform.ThreadPool配置
@ConfigurationProperties(prefix = "t3rik-platform.thread-pool")
public class ThreadPoolProperties {
    /**
     * 核心线程数
     */
    private Integer corePoolSize;
    /**
     * 最大可创建的线程数
     */
    private Integer maxPoolSize;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private Integer keepAliveSeconds;
    /**
     * 队列最大长度
     */
    private Integer queueCapacity;
}
