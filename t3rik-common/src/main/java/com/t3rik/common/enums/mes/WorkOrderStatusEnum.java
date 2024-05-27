package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生产订单状态
 *
 * @date 2024-05-24
 */
@Getter
@AllArgsConstructor
public enum WorkOrderStatusEnum {
    PREPARE("PREPARE", "草稿", 10),
    CONFIRMED("CONFIRMED", "已确认", 20),
    APPROVING("APPROVING", "审批中", 30),
    APPROVED("APPROVED", "已审批", 40),
    FINISHED("FINISHED", "已完成", 50);

    private final String code;
    private final String desc;
    private final Integer sort;

    public static WorkOrderStatusEnum getEnumByCode(String code) {
        for (WorkOrderStatusEnum e : WorkOrderStatusEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 