package com.t3rik.mes.wm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.wm.domain.WmMaterialStockLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存变化日志Mapper接口
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Mapper
public interface WmMaterialStockLogMapper extends BaseMapper<WmMaterialStockLog> {

}
