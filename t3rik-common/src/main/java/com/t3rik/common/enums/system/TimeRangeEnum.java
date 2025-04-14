package com.t3rik.common.enums.system;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间范围枚举
 *
 * @author t3rik
 * @date 2025/4/9 23:16
 */
@Getter
public enum TimeRangeEnum {
    WEEK("week", "本周"),
    MONTH("month", "本月"),
    LAST_7_DAYS("last7", "近七天"),
    LAST_30_DAYS("last30", "近30天");

    private final String code;
    private final String desc;

    TimeRangeEnum(String value, String desc) {
        this.code = value;
        this.desc = desc;
    }

    private static final Map<String, TimeRangeEnum> DEFAULT_DATA_ENUM_MAP = new HashMap<>();

    static {
        for (TimeRangeEnum value : TimeRangeEnum.values()) {
            DEFAULT_DATA_ENUM_MAP.put(value.getCode(), value);
        }
    }


    public static TimeRangeEnum getEnumByCode(String code) {
        return DEFAULT_DATA_ENUM_MAP.get(code);
    }
}
