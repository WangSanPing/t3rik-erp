package com.t3rik.mes.sales.mapper;

import java.util.List;

import com.t3rik.mes.sales.domain.SalesOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.t3rik.mes.sales.domain .SalesOrderItem;

/**
 * 销售订单Mapper接口
 *
 * @author t3rik
 * @date 2024-08-29
 */
@Mapper
public interface SalesOrderMapper extends BaseMapper<SalesOrder> {

}
