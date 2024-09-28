package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.sales.domain.TranOrder;

/**
 * 销售送货单Service接口
 *
 * @author 堇年
 * @date 2024-09-09
 */
public interface ITranOrderService extends IService<TranOrder> {

     void saveTranOrder(TranOrder tranOrder);

    StringBuffer execute(TranOrder tranOrder) throws Exception;

    //update
    boolean updateTranOrder(TranOrder tranOrder);

    //remove
    StringBuffer deleteByIds(List<Long> tranOrderIds);

    //审批
    boolean refuse(TranOrder tranOrder);

}
