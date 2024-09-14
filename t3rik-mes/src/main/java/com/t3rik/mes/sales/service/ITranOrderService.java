package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.sales.domain.TranOrder;

/**
 * 销售送货单Service接口
 *
 * @author t3rik
 * @date 2024-09-09
 */
public interface ITranOrderService extends IService<TranOrder> {

    public void saveTranOrder(TranOrder tranOrder);

}
