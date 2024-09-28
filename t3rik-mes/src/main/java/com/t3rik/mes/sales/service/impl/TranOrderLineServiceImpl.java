package com.t3rik.mes.sales.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderLineMapper;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售送货单列Service业务层处理
 *
 * @author 堇年
 * @date 2024-09-13
 */
@Service
public class TranOrderLineServiceImpl extends ServiceImpl<TranOrderLineMapper, TranOrderLine> implements ITranOrderLineService {
    @Autowired
    private TranOrderLineMapper tranOrderLineMapper;

    @Autowired
    private ISalesOrderItemService salesOrderItemService;


    @Transactional
    @Override
    public AjaxResult execute(TranOrderLine tranOrderLine) throws Exception {

        return AjaxResult.success();
    }
}
