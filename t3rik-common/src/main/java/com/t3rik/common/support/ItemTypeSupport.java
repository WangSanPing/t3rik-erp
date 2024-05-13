package com.t3rik.common.support;

import cn.hutool.core.lang.Assert;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.enums.DefaultDataEnum;
import com.t3rik.common.enums.ItemTypeEnum;
import com.t3rik.common.exception.BusinessException;

/**
 * item类型支持类
 *
 * @author t3rik
 * @date 2024/5/11 21:21
 */
public class ItemTypeSupport extends CommonSupport {

    /**
     * 根据传入的类型返回对应的数据默认id
     */
    public static Long getDefaultDataIdByItemType(String itemType) {
        var itemTypeEnum = ItemTypeEnum.getEnumByCode(itemType);
        Assert.notNull(itemTypeEnum, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        return switch (itemTypeEnum) {
            case ITEM -> DefaultDataEnum.MATERIAL.getDataId();
            case PRODUCT -> DefaultDataEnum.PRODUCTS.getDataId();
            default -> null;
        };
    }
}
