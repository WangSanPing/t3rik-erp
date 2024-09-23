package com.t3rik.hrm.sm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.dto.InterviewRecordDTO;
import com.t3rik.hrm.sm.state.StaffState;

import java.util.List;

/**
 * 面试记录Service接口
 *
 * @author t3rik
 * @date 2024-09-15
 */
public interface IHrmInterviewRecordService extends IService<HrmInterviewRecord> {

    /**
     * 按员工分组查询列表
     */
    Page<InterviewRecordDTO> pageGroupByStaff(HrmInterviewRecord query);

    /**
     * 按员工分组查询列表-可以添加状态条件
     */
    Page<InterviewRecordDTO> pageGroupByStaffWithStatus(HrmInterviewRecord query, List<Integer> statusList);

    /**
     * 更新面试结果和员工状态
     */
    Boolean updateWithStaff(HrmInterviewRecord hrmInterviewRecord);
}
