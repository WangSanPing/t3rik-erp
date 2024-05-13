package com.t3rik.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {
    YES("Y", "YES"),
    NO("N", "NO");

    private final String code;
    private final String desc;

    public static YesOrNoEnum getEnumByCode(String code) {
        for (YesOrNoEnum e : YesOrNoEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 