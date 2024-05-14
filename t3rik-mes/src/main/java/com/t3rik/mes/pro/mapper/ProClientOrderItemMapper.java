package com.t3rik.mes.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户订单材料
 * Mapper接口
 *
 * @author t3rik
 * @date 2024-05-07
 */
@Mapper
public interface ProClientOrderItemMapper extends BaseMapper<ProClientOrderItem> {

    /**
     * 查询当前客户订单最大级别数据
     */
    Integer selectLevelByClientOrderId(@Param("clientOrderId") Long clientOrderId);

}
