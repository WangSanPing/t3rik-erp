package com.t3rik.hrm.md.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.hrm.md.mapper.HrmRankMapper;
import com.t3rik.hrm.md.domain.HrmRank;
import com.t3rik.hrm.md.service.IHrmRankService;

/**
 * 职级管理Service业务层处理
 *
 * @author t3rik
 * @date 2024-07-23
 */
@Service
public class HrmRankServiceImpl  extends ServiceImpl<HrmRankMapper, HrmRank>  implements IHrmRankService
{
    @Autowired
    private HrmRankMapper hrmRankMapper;

}
