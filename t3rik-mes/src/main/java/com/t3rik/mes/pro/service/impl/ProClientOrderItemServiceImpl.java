package com.t3rik.mes.pro.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.pro.mapper.ProClientOrderItemMapper;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.service.IProClientOrderItemService;

/**
 * 客户订单材料
Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-07
 */
@Service
public class ProClientOrderItemServiceImpl  extends ServiceImpl<ProClientOrderItemMapper, ProClientOrderItem>  implements IProClientOrderItemService
{
    @Autowired
    private ProClientOrderItemMapper proClientOrderItemMapper;

}
