package com.t3rik.mes.pro.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProClientOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户订单Service接口
 *
 * @author t3rik
 * @date 2024-05-01
 */
public interface IProClientOrderService extends IService<ProClientOrder> {

    /**
     * 生成生产订单
     */
    Long generateWorkOrder(ProClientOrder clientOrder);
}
