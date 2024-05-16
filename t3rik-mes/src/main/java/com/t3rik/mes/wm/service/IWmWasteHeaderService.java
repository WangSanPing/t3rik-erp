package com.t3rik.mes.wm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.wm.domain.WmWasteHeader;
import com.t3rik.mes.wm.domain.tx.WmWasteTxBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产废料单头Service接口
 *
 * @author t3rik
 * @date 2024-05-11
 */
public interface IWmWasteHeaderService extends IService<WmWasteHeader> {

    /**
     * 检查编号是否重复
     * @param wmWasteHeader
     * @return
     */
    String checkUnique(WmWasteHeader wmWasteHeader);

    /**
     * 批量删除生产废料单头
     *
     * @param wasteIds 需要删除的生产废料单头主键集合
     * @return 结果
     */
    int delWmWasteHeaderIds(List<Long> wasteIds);

    /**
     * 查询废料信息所对应的库存记录
     * @param wasteId 废料id
     * @return
     */
    List<WmWasteTxBean> getTxBeans(Long wasteId);
}
