package com.t3rik.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * aliyun配置
 *
 * @author t3rik
 * @date 2025/1/18 16:50
 */
@Configuration
public class AliYunConfig {

    @Bean
    public OSS ossClient(OssProperties ossProperties) {
        return new OSSClientBuilder()
                .build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
    }
}
