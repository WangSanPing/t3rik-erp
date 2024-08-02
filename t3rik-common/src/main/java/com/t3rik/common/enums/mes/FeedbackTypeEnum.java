package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单类型
 *
 * @author t3rik
 * @date 2024-05-24
 */
@Getter
@AllArgsConstructor
public enum FeedbackTypeEnum {
    SELF("SELF", "自行报工"),
    UNI("UNI", "统一报工");

    private final String code;
    private final String desc;

    public static final Map<String, FeedbackTypeEnum> FEEDBACK_TYPE_ENUM_MAP;

    static {
        FEEDBACK_TYPE_ENUM_MAP = new HashMap<>();
        for (FeedbackTypeEnum value : FeedbackTypeEnum.values()) {
            FEEDBACK_TYPE_ENUM_MAP.put(value.getCode(), value);
        }
    }

    public static FeedbackTypeEnum getEnumByCode(String code) {
        return FEEDBACK_TYPE_ENUM_MAP.get(code);
    }
} 