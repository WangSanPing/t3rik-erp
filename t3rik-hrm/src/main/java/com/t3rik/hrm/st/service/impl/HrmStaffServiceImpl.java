package com.t3rik.hrm.st.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.hrm.st.mapper.HrmStaffMapper;
import com.t3rik.hrm.st.domain.HrmStaff;
import com.t3rik.hrm.st.service.IHrmStaffService;

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

}
