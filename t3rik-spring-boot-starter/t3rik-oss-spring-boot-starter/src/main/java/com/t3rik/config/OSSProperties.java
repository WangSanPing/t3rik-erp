package com.t3rik.config;

import com.t3rik.OSSTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * oss配置类
 *
 * @author t3rik
 * @date 2025/1/16 22:30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "t3rik-platform.oss")
public class OSSProperties {
    /**
     * oss类型
     */
    private OSSTypeEnum type;
    private String accessKey;
    private String secretKey;
    /**
     * 桶名称
     */
    private String buket;
    private String endPoint;
}
