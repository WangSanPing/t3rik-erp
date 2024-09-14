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
     */
    public static ThrowExceptionMsg checkStaffStatus(StaffState state, Integer status) {
        return (errorMsg) -> {
            if (!state.checkCurrentStatusCd(status)) {
                throw new RuntimeException(errorMsg);
            }
        };
    }
}
