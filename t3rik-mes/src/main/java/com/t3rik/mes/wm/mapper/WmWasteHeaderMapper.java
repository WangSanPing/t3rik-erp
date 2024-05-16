package com.t3rik.mes.wm.mapper;
import java.util.Collection;

import com.t3rik.mes.wm.domain.tx.WmWasteTxBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.t3rik.mes.wm.domain.WmWasteHeader;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产废料单头Mapper接口
 *
 * @author t3rik
 * @date 2024-05-11
 */
@Mapper
public interface WmWasteHeaderMapper extends BaseMapper<WmWasteHeader> {
    /**
     * 批量删除生产废料单头
     *
     * @param wasteIds 需要删除的生产废料单头主键集合
     * @return 结果
     */
    int delWmWasteHeaderIds(@Param("wasteIds")List<Long> wasteIds);

    /**
     *  关联查询废料信息所对应的库存记录
     * @param wasteId 废料id
     * @return
     */
    List<WmWasteTxBean> getTxBeans(Long wasteId);
}
