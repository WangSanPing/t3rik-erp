package com.t3rik.mes.sales.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.sales.domain.PurchaseOrder;
import com.t3rik.mes.sales.mapper.PurchaseOrderMapper;
import com.t3rik.mes.sales.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 采购订单Service业务层处理
 *
 * @author t3rik
 * @date 2024-11-02
 */
@Service
public class PurchaseOrderServiceImpl  extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder>  implements IPurchaseOrderService
{
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

}
