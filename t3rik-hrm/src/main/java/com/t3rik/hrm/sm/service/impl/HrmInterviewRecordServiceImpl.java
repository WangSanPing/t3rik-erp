package com.t3rik.hrm.sm.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.hrm.sm.mapper.HrmInterviewRecordMapper;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.service.IHrmInterviewRecordService;

/**
 * 面试记录Service业务层处理
 *
 * @author t3rik
 * @date 2024-09-15
 */
@Service
public class HrmInterviewRecordServiceImpl  extends ServiceImpl<HrmInterviewRecordMapper, HrmInterviewRecord>  implements IHrmInterviewRecordService
{
    @Autowired
    private HrmInterviewRecordMapper hrmInterviewRecordMapper;

}
