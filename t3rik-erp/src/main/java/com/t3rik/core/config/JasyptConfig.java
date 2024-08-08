// package com.t3rik.core.config;
//
// import com.t3rik.common.constant.UserConstants;
// import com.t3rik.common.exception.BusinessException;
// import com.t3rik.system.domain.SysConfigJasypt;
// import com.t3rik.system.service.ISysConfigJasyptService;
// import jakarta.annotation.Resource;
// import org.jasypt.encryption.StringEncryptor;
// import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.Optional;
//
// /**
//  * 加密配置类
//  *
//  * @author t3rik
//  * @date 2024/7/1 16:16
//  */
// @Configuration
// public class JasyptConfig {
//
//     @Resource
//     private ISysConfigJasyptService jasyptService;
//
//     @Bean(name = "jasyptStringEncryptor")
//     public StringEncryptor stringEncryptor() {
//         StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//         SysConfigJasypt jasypt = this.jasyptService.lambdaQuery().eq(SysConfigJasypt::getConfigKey, UserConstants.JASYPT_SALT_KEY).one();
//         Optional.ofNullable(jasypt).orElseThrow(() -> new BusinessException("未找到加密方式，无法启动，如不需要加密启动，可以去掉该配置类，把redis的密码改为自己的服务器密码即可"));
//         encryptor.setPassword(jasypt.getConfigValue());
//         encryptor.setAlgorithm("PBEWithMD5AndDES");
//         return encryptor;
//     }
//
//
//     // public static void main(String[] args) {
//     //     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//     //     encryptor.setPassword("your-secret-password"); // 加密密钥
//     //     encryptor.setAlgorithm("PBEWithMD5AndDES");
//     //
//     //     String encryptedPassword = encryptor.encrypt("your-redis-password");
//     //     System.out.println("Encrypted password: ENC(" + encryptedPassword + ")");
//     // }
// }
