package com.t3rik.hrm.common;

import com.t3rik.hrm.sm.state.StaffState;

/**
 * hrm校验工具类
 *
 * @author t3rik
 * @date 2024/9/12 21:34
 */
public class HrmCheckUtils {

    /**
     * 校验状态是否可以允许更新
     * @param state 要更新的状态
     * @param currentStatus 当前状态
     */
    public static ThrowExceptionMsg checkStaffStatus(StaffState state, Integer currentStatus) {
        return (errorMsg) -> {
            if (!state.checkCurrentStatusCd(currentStatus)) {
                throw new RuntimeException(errorMsg);
            }
        };
    }
}
