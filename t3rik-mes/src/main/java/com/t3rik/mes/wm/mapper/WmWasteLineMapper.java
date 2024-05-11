package com.t3rik.mes.wm.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import java.util.List;

import com.t3rik.mes.wm.domain.WmWasteLine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产废料单行Mapper接口
 *
 * @author t3rik
 * @date 2024-05-11
 */
@Mapper
public interface WmWasteLineMapper extends BaseMapper<WmWasteLine> {
    /**
     * 批量删除废料行数据
     * @param wasteIds
     * @return
     */
    int delLineByWasteIds(@Param("wasteIds")List<Long> wasteIds);



}
