package com.t3rik.system.service;

import com.t3rik.common.core.domain.entity.SysAutoCodePart;

import java.util.List;

public interface IAutoCodePartService {

    public List<SysAutoCodePart> listPart(SysAutoCodePart sysAutoCodePart);

    public SysAutoCodePart findById(Long partId);

    public String checkPartUnique(SysAutoCodePart sysAutoCodePart);


    public int insertPart(SysAutoCodePart sysAutoCodePart);

    public int updatePart(SysAutoCodePart sysAutoCodePart);

    public int deleteByIds(Long[] partIds);

}
