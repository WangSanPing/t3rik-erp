package com.t3rik.hrm.sm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.state.StaffState;
import com.t3rik.hrm.sm.vo.HrmStaffVo;

import java.util.List;

/**
 * 员工花名册Service接口
 *
 * @author 施子安
 * @date 2024-07-11
 */
public interface IHrmStaffService extends IService<HrmStaff> {


    /**
     * 人才登记列表查询
     * @param hrmStaff
     * @return
     */
    List<HrmStaffVo> listTalents(HrmStaff hrmStaff);

    /**
     * 获取人才详情信息
     */
    HrmStaffVo getTalents(Long staffId);

    /**
     * 入职申请
     */
    void employmentApplication(HrmInterviewRecord hrmInterviewRecord, StaffState state);
}
