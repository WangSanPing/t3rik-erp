package com.t3rik.common.sensitive;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 脱敏策略
 *
 * @author t3rik
 * @date 2025/1/7 22:35
 */
@Getter
@AllArgsConstructor
public enum SensitiveStrategyEnum {

    // 将字符串 s 中的第一个中文字符后面的所有字符用 * 替代
    NAME(s -> s.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1*")),
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2")),
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    ADDRESS(s -> s.replaceAll("(.{4})(.*)(.{4})", "$1****$3"));

    private final SensitiveDataProcessor sensitiveData;
}
