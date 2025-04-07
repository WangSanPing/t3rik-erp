package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 库存日志类型枚举
 *
 * @author t3rik
 * @date 2025/4/2 19:24
 */
@Getter
@AllArgsConstructor
public enum LogChangeTypeEnum {

    INBOUND(10, "入库"),
    OUTBOUND(20, "出库"),
    INVENTORY_CHECK(30, "盘点");

    private final Integer code;
    private final String desc;

    public static final Map<Integer, LogChangeTypeEnum> LOG_CHANGE_TYPE_ENUM;

    static {
        LOG_CHANGE_TYPE_ENUM = new HashMap<>();
        for (LogChangeTypeEnum value : LogChangeTypeEnum.values()) {
            LOG_CHANGE_TYPE_ENUM.put(value.getCode(), value);
        }
    }

    public static LogChangeTypeEnum getEnumByCode(String code) {
        return LOG_CHANGE_TYPE_ENUM.get(code);
    }
}
