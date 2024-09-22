package com.t3rik.hrm.sm.service.impl;

import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.service.IHrmEmploymentApplicationService;
import com.t3rik.hrm.sm.service.IHrmInterviewRecordService;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import com.t3rik.hrm.sm.state.StaffState;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入职申请
 *
 * @author t3rik
 * @date 2024/9/22 21:20
 */
@Service
public class HrmEmploymentApplicationServiceImpl implements IHrmEmploymentApplicationService {

    @Resource
    private IHrmInterviewRecordService interviewRecordService;
    @Resource
    private IHrmStaffService hrmStaffService;

    /**
     * 入职申请
     */
    @Transactional
    @Override
    public void employmentApplication(HrmInterviewRecord hrmInterviewRecord, StaffState state) {
        // 更新面试申请表
        this.interviewRecordService
                .lambdaUpdate()
                .set(HrmInterviewRecord::getActualSalary, hrmInterviewRecord.getActualSalary())
                .eq(HrmInterviewRecord::getInterviewRecordId, hrmInterviewRecord.getInterviewRecordId())
                .update(new HrmInterviewRecord());
        // 更新员工表
        this.hrmStaffService.employmentApplication(hrmInterviewRecord, state);
    }
}
