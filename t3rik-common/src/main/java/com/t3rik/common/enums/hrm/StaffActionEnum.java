package com.t3rik.common.enums.hrm;

import lombok.Getter;

/**
 * 员工状态动作枚举
 *
 * @author t3rik
 * @date 2024/9/11 21:53
 */
@Getter
public enum StaffActionEnum {

    CREATE_STAFF_ACTION(0, "创建备选员工"),
    INTERVIEW_ACTION(10, "已邀请面试"),
    PASS_THE_INTERVIEW_ACTION(20, "面试通过"),
    INTERVIEW_FAIL_ACTION(21, "面试失败"),
    BE_PENDING_ACTION(22, "待定"),
    EMPLOYMENT_APPLICATION_ACTION(30, "已申请入职"),
    ALLOW_ENTRY_ACTION(40, "申请通过，已入职"),
    REFUSE_ENTRY_ACTION(41, "申请被拒绝"),
    PROBATION_ACTION(50, "试用期"),
    BECOME_A_REGULAR_WORKER_ACTION(60, "已转正"),
    RESIGNED_ACTION(100, "已离职"),
    ;

    /**
     * 动作代码
     */
    private final Integer action;

    /**
     * 动作名称
     */
    private final String actionName;

    StaffActionEnum(Integer action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }
}
