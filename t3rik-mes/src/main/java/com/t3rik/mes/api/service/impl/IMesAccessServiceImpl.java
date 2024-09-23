package com.t3rik.mes.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.api.domain.Access;
import com.t3rik.mes.api.mapper.AccessMapper;
import com.t3rik.mes.api.service.IMesAccessService;
import org.springframework.stereotype.Service;

@Service
public class IMesAccessServiceImpl extends ServiceImpl<AccessMapper, Access> implements IMesAccessService{

}