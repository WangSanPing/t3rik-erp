package com.t3rik.common.enums.hrm;

import lombok.Getter;

/**
 * 员工状态
 *
 * @author 施子安
 * @date 2024年7月18日 14点14分
 */
@Getter
public enum StaffStatusEnum {
    ALTERNATE(0, "备选", 0),
    INTERVIEW(10, "面试", 1),
    REEXAMINATION(11, "待复试", 1),
    PASS_THE_INTERVIEW(20, "面试通过", 2),
    INTERVIEW_FAIL(21, "面试失败", 3),
    BE_PENDING(22, "待定", 3),
    EMPLOYMENT_APPLICATION(30, "入职申请", 4),
    ALLOW_ENTRY(40, "入职通过", 5),
    REFUSE_ENTRY(41, "申请被拒绝", 5),
    PROBATION(50, "试用期", 6),
    BECOME_A_REGULAR_WORKER(60, "已转正", 7),
    RESIGNED(100, "已离职", 8),
    ;

    /**
     * 备注
     */
    private final String remark;

    /**
     * 状态数值
     */
    private final Integer code;

    private final Integer sort;

    StaffStatusEnum(Integer code, String remark, Integer sort) {
        this.remark = remark;
        this.code = code;
        this.sort = sort;
    }
}
