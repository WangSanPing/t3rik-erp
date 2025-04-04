// package com.t3rik.framework.config;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import redis.clients.jedis.Jedis;
// import redis.clients.jedis.JedisPool;
// import redis.clients.jedis.JedisPoolConfig;
//
// /**
//  * jedis客户端
//  *
//  * @author t3rik
//  * @date 2025/4/4 20:08
//  */
// @Configuration
// public class JedisConfig {
//
//     @Value("${spring.data.redis.host}")
//     private String host;
//
//     @Value("${spring.data.redis.password}")
//     private String password;
//
//     // 配置 Jedis 连接池
//     @Bean()
//     public JedisPool jedisPool() {
//         // 设置连接池配置
//         JedisPoolConfig poolConfig = new JedisPoolConfig();
//         poolConfig.setMaxTotal(50); // 最大连接数
//         poolConfig.setMaxIdle(20);   // 最大空闲连接数
//         poolConfig.setMinIdle(5);   // 最小空闲连接数
//         poolConfig.setTestOnBorrow(true);  // 在借用连接时进行测试
//         poolConfig.setJmxEnabled(false); // 禁用 JMX 注册
//         // 创建 Jedis 连接池，连接 Redis 服务
//         return new JedisPool(poolConfig, host, 6379, 5000, password);
//     }
//
//     // 配置 Jedis 实例
//     @Bean
//     public Jedis jedis(JedisPool jedisPool) {
//         return jedisPool.getResource();
//     }
// }
