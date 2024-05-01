package com.t3rik.mes.pro.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.pro.mapper.ProClientOrderMapper;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.service.IProClientOrderService;

/**
 * 客户订单Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-01
 */
@Service
public class ProClientOrderServiceImpl  extends ServiceImpl<ProClientOrderMapper, ProClientOrder>  implements IProClientOrderService
{
    @Autowired
    private ProClientOrderMapper proClientOrderMapper;

}
