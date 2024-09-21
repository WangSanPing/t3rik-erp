package com.t3rik.mes.sales.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.entity.SysDictData;
import com.t3rik.common.enums.mes.ClientOrderStatusEnum;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import com.t3rik.mes.sales.service.ITranOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderLineMapper;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售送货单列Service业务层处理
 *
 * @author t3rik
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
