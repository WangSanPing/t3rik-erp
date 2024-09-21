package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.domain.TranOrderLine;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售送货单列Service接口
 *
 * @author t3rik
 * @date 2024-09-13
 */
public interface ITranOrderLineService extends IService<TranOrderLine> {

    public AjaxResult execute(TranOrderLine tranOrderLine) throws Exception;
}
