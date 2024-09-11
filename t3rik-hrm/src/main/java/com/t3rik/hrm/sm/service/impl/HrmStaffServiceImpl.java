package com.t3rik.hrm.sm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.mapper.HrmStaffMapper;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import com.t3rik.hrm.sm.vo.HrmStaffVo;
import com.t3rik.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
     *
     * @param hrmStaff
     * @return
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
}
