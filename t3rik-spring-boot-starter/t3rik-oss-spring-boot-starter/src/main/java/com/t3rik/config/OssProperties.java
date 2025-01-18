package com.t3rik.config;

import com.t3rik.enums.OssTypeEnum;
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
public class OssProperties {
    /**
     * oss类型
     */
    private OssTypeEnum ossType = OssTypeEnum.MinIO;
    private String accessKey = "minIO";
    private String secretKey = "minIO";
    /**
     * 桶名称
     */
    private String buket = "minIO";
    private String endPoint = "http://127.0.0.1:9000";
}
