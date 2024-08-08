package com.t3rik.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.system.domain.SysConfigJasypt;
import com.t3rik.system.mapper.SysConfigJasyptMapper;
import com.t3rik.system.service.ISysConfigJasyptService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 加密配置Service业务层处理
 *
 * @author t3rik
 * @date 2024-07-01
 */
@Service
public class SysConfigJasyptServiceImpl extends ServiceImpl<SysConfigJasyptMapper, SysConfigJasypt> implements ISysConfigJasyptService {
    @Resource
    private SysConfigJasyptMapper sysConfigJasyptMapper;

}
