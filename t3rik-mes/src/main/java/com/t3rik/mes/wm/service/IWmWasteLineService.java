package com.t3rik.mes.wm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.wm.domain.WmWasteLine;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产废料单行Service接口
 *
 * @author t3rik
 * @date 2024-05-11
 */
public interface IWmWasteLineService extends IService<WmWasteLine> {

    /**
     * 批量删除废料行数据
     * @param wasteIds
     * @return
     */
    int delWmWasteLineIds(List<Long> wasteIds);
}
