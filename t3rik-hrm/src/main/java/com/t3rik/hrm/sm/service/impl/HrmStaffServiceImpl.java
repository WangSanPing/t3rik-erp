package com.t3rik.hrm.sm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.core.domain.entity.SysUser;
import com.t3rik.common.enums.hrm.StaffStatusEnum;
import com.t3rik.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.hrm.sm.mapper.HrmStaffMapper;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.vo.HrmStaffVo;
import com.t3rik.hrm.sm.service.IHrmStaffService;

import java.util.List;

/**
 * 员工花名册Service业务层处理
 *
 * @author 施子安
 * @date 2024-07-11
 */
@Service
public class HrmStaffServiceImpl  extends ServiceImpl<HrmStaffMapper, HrmStaff>  implements IHrmStaffService
{
    @Autowired
    private HrmStaffMapper hrmStaffMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 员工审批流程
     * @param hrmStaff
     */
    @Override
    public void process(HrmStaff hrmStaff) {
        //获取状态及id
        Long staffId = hrmStaff.getStaffId();
        Long status = hrmStaff.getStatus();
        Long resultStatus= 0L;
        switch (status.intValue()){
           case  0 : resultStatus = StaffStatusEnum.INTERVIEW.getCode();break;
           case  1 : resultStatus = StaffStatusEnum.INTERVIEW_PASS.getCode();break;
           case  2 : resultStatus = StaffStatusEnum.ENTRY_APPLICATION.getCode();break;
           case  3 : resultStatus = StaffStatusEnum.PASS_ADMISSION.getCode();break;
           case  4 : resultStatus = StaffStatusEnum.INTERVIEW_FAIL.getCode();break;
        }
        //修改员工状态
        this.lambdaUpdate().eq(HrmStaff::getStaffId,staffId).set(HrmStaff::getStatus,resultStatus).update();
        //todo 如果入职申请通过生成登录账号密码，开通账号
        if (StaffStatusEnum.INTERVIEW_FAIL.getCode().equals(status)){
            SysUser sysUser = new SysUser();
            sysUserService.insertUser(sysUser);
        }
    }
    /**
     * 人才登记列表查询
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
