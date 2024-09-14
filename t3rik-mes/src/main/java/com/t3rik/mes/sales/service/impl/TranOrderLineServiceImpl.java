package com.t3rik.mes.sales.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderLineMapper;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;

/**
 * 销售送货单列Service业务层处理
 *
 * @author t3rik
 * @date 2024-09-13
 */
@Service
public class TranOrderLineServiceImpl  extends ServiceImpl<TranOrderLineMapper, TranOrderLine>  implements ITranOrderLineService
{
    @Autowired
    private TranOrderLineMapper tranOrderLineMapper;

}
