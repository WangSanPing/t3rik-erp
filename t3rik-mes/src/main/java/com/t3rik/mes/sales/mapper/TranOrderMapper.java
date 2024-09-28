package com.t3rik.mes.sales.mapper;


import com.t3rik.common.annotation.DataSource;
import com.t3rik.mes.sales.domain.TranOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售送货单Mapper接口
 *
 * @author 堇年
 * @date 2024-09-09
 */
@Mapper
@DataSource()
public interface TranOrderMapper extends BaseMapper<TranOrder> {

}
