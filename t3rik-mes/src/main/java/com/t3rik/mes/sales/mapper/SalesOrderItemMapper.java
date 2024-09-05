package com.t3rik.mes.sales.mapper;

import java.util.List;

import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售订单产品列Mapper接口
 *
 * @author t3rik
 * @date 2024-08-29
 */
@Mapper
public interface SalesOrderItemMapper extends BaseMapper<SalesOrderItem> {

}
