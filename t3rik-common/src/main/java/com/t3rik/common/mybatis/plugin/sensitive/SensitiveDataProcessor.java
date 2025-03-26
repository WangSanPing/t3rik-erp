package com.t3rik.common.mybatis.plugin.sensitive;

/**
 * 脱敏处理
 *
 * @author t3rik
 * @date 2025/1/7 22:42
 */
@FunctionalInterface
public interface SensitiveDataProcessor {
    /**
     * 处理方法
     */
    String processor(String s);
}
