package com.t3rik.mes.sales.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.sales.domain.PurchaseOrderLine;
import com.t3rik.mes.sales.mapper.PurchaseOrderLineMapper;
import com.t3rik.mes.sales.service.IPurchaseOrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 采购订单列表Service业务层处理
 *
 * @author t3rik
 * @date 2024-11-02
 */
@Service
public class PurchaseOrderLineServiceImpl  extends ServiceImpl<PurchaseOrderLineMapper, PurchaseOrderLine>  implements IPurchaseOrderLineService
{
    @Autowired
    private PurchaseOrderLineMapper purchaseOrderLineMapper;

}
