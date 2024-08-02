package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产订单来源类型
 *
 * @author t3rik
 * @date 2024-05-24
 */
@Getter
@AllArgsConstructor
public enum WorkOrderSourceTypeEnum {
    ORDER("ORDER", "客户订单"),
    STORE("STORE", "库存备货");

    private final String code;
    private final String desc;

    public static final Map<String, WorkOrderSourceTypeEnum> WORK_ORDER_SOURCE_TYPE_ENUM_MAP;

    static {
        WORK_ORDER_SOURCE_TYPE_ENUM_MAP = new HashMap<>();
        for (WorkOrderSourceTypeEnum value : WorkOrderSourceTypeEnum.values()) {
            WORK_ORDER_SOURCE_TYPE_ENUM_MAP.put(value.getCode(), value);
        }
    }

    public static WorkOrderSourceTypeEnum getEnumByCode(String code) {
        return WORK_ORDER_SOURCE_TYPE_ENUM_MAP.get(code);
    }
}
