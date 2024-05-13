package com.t3rik.mes.pro.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户订单材料
Service接口
 *
 * @author t3rik
 * @date 2024-05-07
 */
public interface IProClientOrderItemService extends IService<ProClientOrderItem> {

    /**
     * 获取级别
     */
    Integer getClientOrderItemLevel(Long clientOrderId);
}
