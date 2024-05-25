package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static WorkOrderTypeEnum getEnumByCode(String code) {
        for (WorkOrderTypeEnum e : WorkOrderTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 