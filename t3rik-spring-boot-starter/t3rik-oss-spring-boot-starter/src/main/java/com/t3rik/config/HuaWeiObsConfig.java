package com.t3rik.config;

import com.obs.services.ObsClient;
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
public class HuaWeiObsConfig {

    @Bean
    public ObsClient obsClient(OssProperties ossProperties) {
        // 非huawei配置 不初始化客户端
        if (!ossProperties.getOssType().equals(OssTypeEnum.HuaWeiObs)) {
            return null;
        }
        return new ObsClient(ossProperties.getAccessKey(),ossProperties.getSecretKey(),ossProperties.getEndPoint());
    }
}
