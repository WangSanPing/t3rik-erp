package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 物料类型
 *
 * @author t3rik
 */
@Getter
@AllArgsConstructor
public enum ItemTypeEnum {
    PRODUCT("product", "产品类型"),
    ITEM("item", "原材料类型");

    public static final Map<String, ItemTypeEnum> ITEM_TYPE_MAP;

    static {
        ITEM_TYPE_MAP = new HashMap<>();
        for (ItemTypeEnum value : ItemTypeEnum.values()) {
            ITEM_TYPE_MAP.put(value.getCode(), value);
        }
    }

    private final String code;
    private final String desc;

    public static ItemTypeEnum getEnumByCode(String code) {
        return ITEM_TYPE_MAP.get(code);
    }
} 