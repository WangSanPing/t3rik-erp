package com.t3rik.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认数据,code和数据库中数据id对应
 */
@Getter
@AllArgsConstructor
public enum DefaultDataEnum {
    /**
     * 原材料
     */
    MATERIAL(10000001L, "10000001L", "原材料", "md_item_type"),
    /**
     * 半成品
     */
    SEMI_FINISHED_PRODUCTS(10000003L, "10000003L", "半成品", "md_item_type"),
    /**
     * 产成品
     */
    PRODUCTS(10000004L, "10000004L", "产成品", "md_item_type");

    /**
     * 数据id
     */
    private final Long dataId;

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
    /**
     * 数据对应的表
     */
    private final String table;

    /**
     * 枚举集合
     */
    public static final Map<String, DefaultDataEnum> DEFAULT_DATA_ENUM_MAP;

    static {
        // 初始化加入集合
        DEFAULT_DATA_ENUM_MAP = new HashMap<>();
        for (DefaultDataEnum value : DefaultDataEnum.values()) {
            DEFAULT_DATA_ENUM_MAP.put(value.getCode(), value);
        }
    }

    /**
     *  根据code获取对应的枚举
     */
    public static DefaultDataEnum getEnumByCode(String code) {
        return DEFAULT_DATA_ENUM_MAP.get(code);
    }
} 