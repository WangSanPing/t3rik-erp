package com.t3rik.mes.sales.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.sales.domain.PurchaseOrderLine;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购订单列表Mapper接口
 *
 * @author t3rik
 * @date 2024-11-02
 */
@Mapper
public interface PurchaseOrderLineMapper extends BaseMapper<PurchaseOrderLine> {

}
