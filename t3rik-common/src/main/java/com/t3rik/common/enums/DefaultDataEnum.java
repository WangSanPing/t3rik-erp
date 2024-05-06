package com.t3rik.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 默认数据,code和数据库中数据id对应
 */
@Getter
@AllArgsConstructor
public enum DefaultDataEnum {
    /**
     * 原材料
     */
    MATERIAL(10000001L, "原材料","md_item_type"),
    /**
     * 半成品
     */
    SEMI_FINISHED_PRODUCTS(10000003L, "半成品","md_item_type"),
    /**
     * 产成品
     */
    PRODUCTS(10000004L, "产成品","md_item_type");

    /**
     * 数据id
     */
    private final Long dataId;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 数据对应的表
     */
    private final String table;

    public static DefaultDataEnum getEnumByCode(Long code) {
        for (DefaultDataEnum e : DefaultDataEnum.values()) {
            if (e.getDataId().equals(code)) {
                return e;
            }
        }
        return null;
    }
} 