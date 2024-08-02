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
public enum WorkOrderTypeEnum {
    SELF("SELF", "自产"),
    OUTSOURCE("OUTSOURCE", "外协"),
    PURCHASE("PURCHASE", "外购");

    private final String code;
    private final String desc;

    public static final Map<String, WorkOrderTypeEnum> WORK_ORDER_TYPE_ENUM_MAP;

    static {
        WORK_ORDER_TYPE_ENUM_MAP = new HashMap<>();
        for (WorkOrderTypeEnum value : WorkOrderTypeEnum.values()) {
            WORK_ORDER_TYPE_ENUM_MAP.put(value.getCode(), value);
        }
    }

    public static WorkOrderTypeEnum getEnumByCode(String code) {
        return WORK_ORDER_TYPE_ENUM_MAP.get(code);
    }
} 