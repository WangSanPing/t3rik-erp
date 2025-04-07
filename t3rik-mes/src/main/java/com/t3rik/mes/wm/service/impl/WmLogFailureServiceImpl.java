package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.wm.domain.WmLogFailure;
import com.t3rik.mes.wm.mapper.WmLogFailureMapper;
import com.t3rik.mes.wm.service.IWmLogFailureService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 日志写入失败记录Service业务层处理
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Service
public class WmLogFailureServiceImpl extends ServiceImpl<WmLogFailureMapper, WmLogFailure> implements IWmLogFailureService {
    @Resource
    private WmLogFailureMapper wmLogFailureMapper;

}
