package com.t3rik.mes.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.constant.RedisConstants;
import com.t3rik.common.core.redis.RedisCache;
import com.t3rik.common.enums.EnableFlagEnum;
import com.t3rik.common.enums.mes.ItemTypeEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.domain.MdUnitMeasure;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.md.service.IMdUnitMeasureService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private RedisCache redisCache;
    @Resource
    private IMdUnitMeasureService unitMeasureService;

    @PostConstruct
    void initData() {
        this.updateUnitMeasuresToRedis();
    }

    @Async
    @Override
    public void updateUnitMeasuresToRedis() {
        List<MdUnitMeasure> mdUnitMeasures = unitMeasureService.selectMdUnitMeasureList(null);
        if (CollectionUtils.isEmpty(mdUnitMeasures)) {
            return;
        }
        // 先删除，避免写入重复的数据
        this.redisCache.deleteObject(RedisConstants.UNITS_MEASURES_LIST_KEY);
        this.redisCache.setCacheList(RedisConstants.UNITS_MEASURES_LIST_KEY, mdUnitMeasures);
        mdUnitMeasures.forEach(item -> redisCache.setCacheObject(item.getMeasureCode(), item.getMeasureName()));
    }

    /**
     * 更新单位到redis
     * 可以选择是否需要先删除
     *
     * @param delete true代表需要先删除指定的key
     */
    @Override
    public void updateUnitMeasuresToRedis(Boolean delete, List<String> keys) {
        // 如果需要删除
        if (delete) {
            this.deleteUnitMeasuresToRedis(keys);
        }
        // 更新单位到redis
        this.updateUnitMeasuresToRedis();
    }

    /**
     * 删除redis种的单位
     */
    @Override
    public void deleteUnitMeasuresToRedis(List<String> keys) {
        // 转为可变集合
        ArrayList<String> list = new ArrayList<>(keys);
        // 添加单位集合列表的key，一起删除
        list.add(RedisConstants.UNITS_MEASURES_LIST_KEY);
        this.redisCache.deleteObject(list);
    }

    @Async
    @Override
    public void updateItemBomAndLevel(Long itemId, List<ProClientOrderItem> clientOrderItemList) {
        Assert.notNull(itemId, () -> new BusinessException("itemId不能为空"));
        // 判断当前是否存在产品相应的bom列表,如果存在,更新,如果不存在,新增bom列表
        // 转化数据
        List<MdProductBom> productBomData =
                clientOrderItemList.stream().map(orderItem -> {
                    MdProductBom mdProductBom = new MdProductBom();
                    mdProductBom.setItemId(itemId);
                    mdProductBom.setBomItemId(orderItem.getItemId());
                    mdProductBom.setLevel(orderItem.getLevel());

                    mdProductBom.setBomItemCode(orderItem.getItemCode());
                    mdProductBom.setBomItemName(orderItem.getItemName());
                    mdProductBom.setBomItemSpec(orderItem.getSpecification());
                    mdProductBom.setUnitOfMeasure(orderItem.getUnitOfMeasure());
                    mdProductBom.setItemOrProduct(ItemTypeEnum.ITEM.getCode());
                    mdProductBom.setQuantity(orderItem.getQuantity());
                    mdProductBom.setEnableFlag(EnableFlagEnum.YES.getCode());
                    return mdProductBom;
                }).toList();
        // 如果集合不为空,代表已经设置过bom数据
        List<MdProductBom> productBomList = this.productBomService.lambdaQuery().eq(MdProductBom::getItemId, itemId).list();
        if (CollectionUtil.isEmpty(productBomList)) {
            // 新增
            this.productBomService.saveBatch(productBomData);
        } else {
            // 更新,只更新传递过来的数据中的level字段
            this.productBomService.updateItemBomAndLevel(productBomData);
        }
    }
}
