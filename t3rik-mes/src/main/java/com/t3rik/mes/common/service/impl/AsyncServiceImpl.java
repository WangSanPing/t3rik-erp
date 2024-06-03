package com.t3rik.mes.common.service.impl;

import cn.hutool.core.lang.Assert;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 异步共通实现类
 *
 * @author t3rik
 * @date 2024/6/3 23:04
 */
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Resource
    private IMdProductBomService productBomService;

    @Async
    @Override
    public void updateItemBomAndLevel(Long itemId, List<ProClientOrderItem> clientOrderItemList) {
        Assert.notNull(itemId, () -> new BusinessException("itemId不能为空"));
        List<MdProductBom> productBomList =
                clientOrderItemList.stream().map(orderItem -> {
                    MdProductBom mdProductBom = new MdProductBom();
                    mdProductBom.setItemId(itemId);
                    mdProductBom.setBomItemId(orderItem.getItemId());
                    mdProductBom.setLevel(orderItem.getLevel());
                    return mdProductBom;
                }).toList();
        this.productBomService.updateItemBomAndLevel(productBomList);
    }
}
