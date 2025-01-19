package com.t3rik.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.t3rik.enums.OssTypeEnum;
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
        // 非aliyun配置 不初始化客户端
        if (!ossProperties.getOssType().equals(OssTypeEnum.AliYun)) {
            return null;
        }
        return new OSSClientBuilder()
                .build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
    }
}
