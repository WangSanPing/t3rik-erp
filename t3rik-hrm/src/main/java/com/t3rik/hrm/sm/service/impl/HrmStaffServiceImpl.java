package com.t3rik.hrm.sm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.mapper.HrmStaffMapper;
import com.t3rik.hrm.sm.service.IHrmInterviewRecordService;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import com.t3rik.hrm.sm.state.StaffState;
import com.t3rik.hrm.sm.vo.HrmStaffVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工花名册Service业务层处理
 *
 * @author 施子安
 * @date 2024-07-11
 */
@Service
public class HrmStaffServiceImpl extends ServiceImpl<HrmStaffMapper, HrmStaff> implements IHrmStaffService {
    @Resource
    private HrmStaffMapper hrmStaffMapper;

    /**
     * 人才登记列表查询
     */
    @Override
    public List<HrmStaffVo> listTalents(HrmStaff hrmStaff) {
        return hrmStaffMapper.listTalents(hrmStaff);
    }

    /**
     * 获取人才详情信息
     */
    @Override
    public HrmStaffVo getTalents(Long staffId) {
        return hrmStaffMapper.getTalents(staffId);
    }

    /**
     * 入职申请
     */
    @Transactional
    @Override
    public void employmentApplication(HrmInterviewRecord hrmInterviewRecord, StaffState state) {
        // 更新状态和实际入职薪资
        this.lambdaUpdate()
                .set(HrmStaff::getStatus, state.getCurrentStatus())
                .set(HrmStaff::getActualSalary, hrmInterviewRecord.getActualSalary())
                .eq(HrmStaff::getStaffId, hrmInterviewRecord.getStaffId())
                .update(new HrmStaff());
    }
}
