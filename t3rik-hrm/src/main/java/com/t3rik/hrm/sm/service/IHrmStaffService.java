package com.t3rik.hrm.sm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.hrm.sm.domain.HrmStaff;

/**
 * 员工花名册Service接口
 *
 * @author 施子安
 * @date 2024-07-11
 */
public interface IHrmStaffService extends IService<HrmStaff> {

    /**
     * 员工审批流程
     * @param hrmStaff
     */
    void process(HrmStaff hrmStaff);
}
