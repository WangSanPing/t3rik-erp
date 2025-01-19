package com.t3rik.config;

import com.t3rik.enums.OssTypeEnum;
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
        // 非minio配置 不初始化客户端
        if (!ossProperties.getOssType().equals(OssTypeEnum.MinIO)) {
            return null;
        }
        return MinioClient.builder()
                .endpoint(ossProperties.getEndPoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
    }
}
