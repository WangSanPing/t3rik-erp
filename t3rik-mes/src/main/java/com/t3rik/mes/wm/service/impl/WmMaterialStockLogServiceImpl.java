package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.wm.domain.WmMaterialStockLog;
import com.t3rik.mes.wm.mapper.WmMaterialStockLogMapper;
import com.t3rik.mes.wm.service.IWmMaterialStockLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 库存变化日志Service业务层处理
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Service
public class WmMaterialStockLogServiceImpl extends ServiceImpl<WmMaterialStockLogMapper, WmMaterialStockLog> implements IWmMaterialStockLogService {

    @Resource
    private WmMaterialStockLogMapper wmMaterialStockLogMapper;

}
