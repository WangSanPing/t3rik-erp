package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static WorkOrderSourceTypeEnum getEnumByCode(String code) {
        for (WorkOrderSourceTypeEnum e : WorkOrderSourceTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
