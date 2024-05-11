package com.t3rik.mes.wm.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.wm.mapper.WmWasteLineMapper;
import com.t3rik.mes.wm.domain.WmWasteLine;
import com.t3rik.mes.wm.service.IWmWasteLineService;

/**
 * 生产废料单行Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-11
 */
@Service
public class WmWasteLineServiceImpl  extends ServiceImpl<WmWasteLineMapper, WmWasteLine>  implements IWmWasteLineService
{
    @Autowired
    private WmWasteLineMapper wmWasteLineMapper;

    /**
     * 批量删除废料行数据
     * @param wasteIds
     * @return
     */
    @Override
    public int delWmWasteLineIds(List<Long> wasteIds) {
        return wmWasteLineMapper.delLineByWasteIds(wasteIds);
    }
}
