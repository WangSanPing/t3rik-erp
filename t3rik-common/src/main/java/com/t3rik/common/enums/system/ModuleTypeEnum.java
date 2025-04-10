package com.t3rik.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块类型
 *
 * @author t3rik
 * @date 2024-06-10
 */
@Getter
@AllArgsConstructor
public enum ModuleTypeEnum {
    MANAGER_SYSTEM("1", "管理后台"),
    MOBILE("2", "移动端"),
    PAD("3", "平板端");

    public static final Map<String, ModuleTypeEnum> MODULE_TYPE_MAP;

    static {
        MODULE_TYPE_MAP = new HashMap<>();
        for (ModuleTypeEnum value : ModuleTypeEnum.values()) {
            MODULE_TYPE_MAP.put(value.getCode(), value);
        }
    }

    private final String code;
    private final String desc;

    public static ModuleTypeEnum getEnumByCode(String code) {
        return MODULE_TYPE_MAP.get(code);
    }
} 