package com.t3rik.mes.pro.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.dto.ClientOrderItemPageDto;
import com.t3rik.mes.pro.mapper.ProClientOrderItemMapper;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户订单材料
 * Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-07
 */
@Service
@AllArgsConstructor
public class ProClientOrderItemServiceImpl extends ServiceImpl<ProClientOrderItemMapper, ProClientOrderItem> implements IProClientOrderItemService {

    private final ProClientOrderItemMapper proClientOrderItemMapper;

    @Override
    public Integer getClientOrderItemLevel(Long clientOrderId) {
        Integer maxLevel = this.proClientOrderItemMapper.selectLevelByClientOrderId(clientOrderId);
        return maxLevel == null ? 1 : maxLevel + 1;
    }

    /**
     * 获取分页数据
     */
    @Override
    public Page<ClientOrderItemPageDto> getClientOrderItemPage(Page<ClientOrderItemPageDto> page, Long clientOrderId, List<String> whCodes) {
        return this.proClientOrderItemMapper.getClientOrderItemPage(page, clientOrderId, whCodes);
    }
}
