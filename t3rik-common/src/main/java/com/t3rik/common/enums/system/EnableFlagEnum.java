package com.t3rik.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableFlagEnum {
    YES("Y", "是"),
    NO("N", "否");

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

    public static EnableFlagEnum getEnumByCode(String code) {
        for (EnableFlagEnum e : EnableFlagEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 