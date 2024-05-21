package com.t3rik.mes.pro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.dto.ClientOrderItemPageDto;

import java.util.List;

/**
 * 客户订单材料
 * Service接口
 *
 * @author t3rik
 * @date 2024-05-07
 */
public interface IProClientOrderItemService extends IService<ProClientOrderItem> {

    /**
     * 获取级别
     */
    Integer getClientOrderItemLevel(Long clientOrderId);

    /**
     * 获取分页数据
     */
    Page<ClientOrderItemPageDto> getClientOrderItemPage(Page<ClientOrderItemPageDto> page, Long clientOrderId, List<String> whCodes);
}
