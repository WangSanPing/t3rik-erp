package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户订单状态
 *
 * @date 2024-05-24
 */
@Getter
@AllArgsConstructor
public enum ClientOrderStatusEnum {
    PREPARE("PREPARE", "草稿", 10),
    WORK_ORDER_FINISHED("WORK_ORDER_FINISHED", "已生成生产订单", 20);

    private final String code;
    private final String desc;
    private final Integer sort;

    public static ClientOrderStatusEnum getEnumByCode(String code) {
        for (ClientOrderStatusEnum e : ClientOrderStatusEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
} 