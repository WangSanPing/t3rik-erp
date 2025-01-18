package com.t3rik.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIo配置
 *
 * @author t3rik
 * @date 2025/1/17 15:58
 */
@Configuration
public class MinIOConfig {

    @Bean
    public MinioClient minioClient(OssProperties ossProperties) {
        return MinioClient.builder()
                .endpoint(ossProperties.getEndPoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
    }
}
