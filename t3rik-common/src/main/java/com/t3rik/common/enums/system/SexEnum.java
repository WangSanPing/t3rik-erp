package com.t3rik.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {
    MALE("0", "男"),
    FEMALE("1", "女"),
    UNKNOWN("2", "未知");

    private final String code;
    private final String desc;

    public static SexEnum getEnumByCode(String code) {
        for (SexEnum e : SexEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 