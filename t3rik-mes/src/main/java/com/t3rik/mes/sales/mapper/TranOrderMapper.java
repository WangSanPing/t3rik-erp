package com.t3rik.mes.sales.mapper;

import java.util.List;

import com.t3rik.mes.sales.domain.TranOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.t3rik.mes.sales.domain .TranOrderLine;

/**
 * 销售送货单Mapper接口
 *
 * @author t3rik
 * @date 2024-09-09
 */
@Mapper
public interface TranOrderMapper extends BaseMapper<TranOrder> {

}
