package com.t3rik.hrm.sm.state;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.enums.hrm.StaffActionEnum;
import com.t3rik.common.enums.hrm.StaffStatusEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工状态机
 *
 * @author t3rik
 * @date 2024/9/11 22:52
 */
@Getter
public enum StaffState {

    /**
     * 录入人才资源池
     */
    CREATE_STAFF(
            StaffActionEnum.CREATE_STAFF_ACTION,
            null,
            StaffStatusEnum.ALTERNATE,
            StaffStatusEnum.INTERVIEW),

    /**
     * 邀请面试
     */
    INTERVIEW(
            StaffActionEnum.INTERVIEW_ACTION,
            List.of(StaffStatusEnum.BE_PENDING,StaffStatusEnum.ALTERNATE, StaffStatusEnum.INTERVIEW_FAIL),
            StaffStatusEnum.INTERVIEW,
            null),

    /**
     * 待复试
     */
    REEXAMINATION(
            StaffActionEnum.REEXAMINATION_ACTION,
            List.of(StaffStatusEnum.BE_PENDING,StaffStatusEnum.INTERVIEW,StaffStatusEnum.REEXAMINATION, StaffStatusEnum.ALTERNATE, StaffStatusEnum.INTERVIEW_FAIL),
            StaffStatusEnum.REEXAMINATION,
            null),

    /**
     * 邀请面试-面试通过
     */
    INTERVIEW_PASS(
            StaffActionEnum.PASS_THE_INTERVIEW_ACTION,
            List.of(StaffStatusEnum.BE_PENDING,StaffStatusEnum.REEXAMINATION,StaffStatusEnum.ALTERNATE, StaffStatusEnum.INTERVIEW_FAIL),
            StaffStatusEnum.PASS_THE_INTERVIEW,
            StaffStatusEnum.EMPLOYMENT_APPLICATION),

    /**
     * 邀请面试-面试失败
     */
    INTERVIEW_REFUSE(
            StaffActionEnum.INTERVIEW_FAIL_ACTION,
            List.of(StaffStatusEnum.BE_PENDING,StaffStatusEnum.REEXAMINATION,StaffStatusEnum.ALTERNATE, StaffStatusEnum.INTERVIEW_FAIL),
            StaffStatusEnum.INTERVIEW_FAIL,
            null),

    /**
     * 邀请面试-待定
     */
    BE_PENDING(
            StaffActionEnum.BE_PENDING_ACTION,
            List.of(StaffStatusEnum.REEXAMINATION,StaffStatusEnum.ALTERNATE, StaffStatusEnum.INTERVIEW_FAIL),
            StaffStatusEnum.BE_PENDING,
            StaffStatusEnum.EMPLOYMENT_APPLICATION),

    /**
     * 申请入职
     */
    EMPLOYMENT_APPLICATION(
            StaffActionEnum.EMPLOYMENT_APPLICATION_ACTION,
            List.of(StaffStatusEnum.PASS_THE_INTERVIEW),
            StaffStatusEnum.EMPLOYMENT_APPLICATION,
            null),

    /**
     * 申请入职-申请通过
     */
    ALLOW_ENTRY(
            StaffActionEnum.ALLOW_ENTRY_ACTION,
            List.of(StaffStatusEnum.EMPLOYMENT_APPLICATION),
            StaffStatusEnum.ALLOW_ENTRY,
            StaffStatusEnum.PROBATION),

    /**
     * 申请入职-申请被拒绝
     */
    REFUSE_ENTRY(
            StaffActionEnum.REFUSE_ENTRY_ACTION,
            List.of(StaffStatusEnum.EMPLOYMENT_APPLICATION),
            StaffStatusEnum.REFUSE_ENTRY,
            null),

    /**
     * 试用期
     */
    PROBATION(
            StaffActionEnum.PROBATION_ACTION,
            List.of(StaffStatusEnum.ALLOW_ENTRY),
            StaffStatusEnum.PROBATION,
            StaffStatusEnum.BECOME_A_REGULAR_WORKER),

    /**
     * 已转正
     */
    BECOME_A_REGULAR_WORKER(
            StaffActionEnum.BECOME_A_REGULAR_WORKER_ACTION,
            List.of(StaffStatusEnum.PROBATION, StaffStatusEnum.ALLOW_ENTRY),
            StaffStatusEnum.BECOME_A_REGULAR_WORKER,
            null),

    /**
     * 已离职
     */
    RESIGNED_ACTION(
            StaffActionEnum.RESIGNED_ACTION,
            null,
            StaffStatusEnum.RESIGNED,
            null);

    /**
     * 动作枚举
     */
    private final StaffActionEnum actionEnum;

    /**
     * 执行动作后的状态
     */
    private final StaffStatusEnum currentStatus;

    /**
     * 执行动作后的状态
     */
    private final StaffStatusEnum nextStatus;

    /**
     * 当前订单状态编码集合，用于校验
     */
    private List<Integer> checkStatusCds;

    public static final Map<Integer, StaffState> STAFF_STATE_MAP;

    static {
        STAFF_STATE_MAP = new HashMap<>();
        for (StaffState state : StaffState.values()) {
            STAFF_STATE_MAP.put(state.getActionCode(), state);
        }
    }


    /**
     * 构造处理。
     *
     * @param actionEnum     动作枚举
     * @param checkStatusCds 状态集合，用于校验
     * @param currentStatus  执行当前操作的状态码
     * @param nextStatus     执行当前操作后下一步的状态码
     */
    StaffState(
            StaffActionEnum actionEnum,
            List<StaffStatusEnum> checkStatusCds,
            StaffStatusEnum currentStatus,
            StaffStatusEnum nextStatus) {
        this.actionEnum = actionEnum;
        this.currentStatus = currentStatus;
        this.nextStatus = nextStatus;
        if (CollectionUtils.isNotEmpty(checkStatusCds)) {
            this.checkStatusCds = checkStatusCds.stream().map(StaffStatusEnum::getCode).toList();
        }

    }

    /**
     * 检验当前状态码
     *
     * @return boolean true通过，false不通过
     */
    public boolean checkCurrentStatusCd(Integer status) {
        return CollectionUtils.isEmpty(this.checkStatusCds) || this.checkStatusCds.contains(status);
    }


    /**
     * 获取操作后的状态码
     */
    public Integer getCurrentStatus() {
        return this.currentStatus.getCode();
    }

    /**
     * 获取操作后的下一步的状态码
     */
    public Integer getNextStatus() {
        return this.nextStatus.getCode();
    }

    /**
     * 获取动作编码
     */
    public Integer getActionCode() {
        return this.actionEnum.getAction();
    }

    /**
     * 获取动作名称
     */
    public String getActionName() {
        return this.actionEnum.getActionName();
    }
}
