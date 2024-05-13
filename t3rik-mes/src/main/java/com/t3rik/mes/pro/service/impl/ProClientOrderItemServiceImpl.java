package com.t3rik.mes.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.mapper.ProClientOrderItemMapper;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 客户订单材料
 * Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-07
 */
@Service
public class ProClientOrderItemServiceImpl extends ServiceImpl<ProClientOrderItemMapper, ProClientOrderItem> implements IProClientOrderItemService {

    @Override
    public Integer getClientOrderItemLevel(Long clientOrderId) {
        QueryWrapper<ProClientOrderItem> queryWrapper = new QueryWrapper<>();
        // 获取当前数据的级别最大值
        queryWrapper.select("max(level) as maxLevel");
        queryWrapper.eq("client_order_id", clientOrderId);
        Map<String, Object> map = this.getMap(queryWrapper);
        return map == null || map.get("maxLevel") == null ? 1 : Integer.parseInt((String) map.get("maxLevel")) + 1;
    }
}
