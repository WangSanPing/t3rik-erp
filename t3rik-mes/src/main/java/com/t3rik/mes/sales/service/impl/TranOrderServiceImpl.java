package com.t3rik.mes.sales.service.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderMapper;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.service.ITranOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售送货单Service业务层处理
 *
 * @author t3rik
 * @date 2024-09-09
 */
@Service
public class TranOrderServiceImpl  extends ServiceImpl<TranOrderMapper, TranOrder>  implements ITranOrderService
{
    @Autowired
    private TranOrderMapper TranOrderMapper;
    @Autowired
    private ITranOrderLineService tranOrderLineService;
    @Autowired
    private AutoCodeUtil autoCodeUtil;
    @Transactional
    public void saveTranOrder(TranOrder tranOrder){
        if(tranOrder.getTranOrderLineList().size()>0){
            this.save(tranOrder);
            for(TranOrderLine obj:tranOrder.getTranOrderLineList()){
                obj.setTranCode(autoCodeUtil.genSerialCode("TRAN_CODE", null));
                obj.setTranOrderId(tranOrder.getTranOrderId());
                obj.setBusMan(tranOrder.getBusMan());
                obj.setFollowerMan(tranOrder.getFollowerMan());
            }
            tranOrderLineService.saveBatch(tranOrder.getTranOrderLineList());
        }else{
            this.save(tranOrder);
        }
    }

}
