package com.t3rik.hrm.sm.service;

import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.state.StaffState;

/**
 * 入职申请
 *
 * @author t3rik
 * @date 2024/9/22 21:19
 */
public interface IHrmEmploymentApplicationService {

    /**
     * 入职申请
     */
    void employmentApplication(HrmInterviewRecord hrmInterviewRecord, StaffState state);

    /**
     * 审核通过
     */
    String pass(HrmInterviewRecord hrmInterviewRecord, StaffState state);
}
