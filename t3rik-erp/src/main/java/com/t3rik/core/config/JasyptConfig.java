package com.t3rik.core.config;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.system.domain.SysConfigJasypt;
import com.t3rik.system.service.ISysConfigJasyptService;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 加密配置类
 *
 * @author t3rik
 * @date 2024/7/1 16:16
 */
@Configuration
public class JasyptConfig {

    @Resource
    private ISysConfigJasyptService jasyptService;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SysConfigJasypt jasypt = this.jasyptService.lambdaQuery().eq(SysConfigJasypt::getConfigKey, UserConstants.JASYPT_SALT_KEY).one();
        Optional.ofNullable(jasypt).orElseThrow(() -> new BusinessException("未找到加密方式，无法启动"));
        encryptor.setPassword(jasypt.getConfigValue());
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        return encryptor;
    }
}
