package com.t3rik.system.service.impl;

import com.t3rik.common.core.domain.entity.SysAutoCodeResult;
import com.t3rik.system.mapper.SysAutoCodeResultMapper;
import com.t3rik.system.service.IAutoCodeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAutoCodeResultServiceImpl implements IAutoCodeResultService {

    @Autowired
    private SysAutoCodeResultMapper sysAutoCodeResultMapper;

    @Override
    public List<SysAutoCodeResult> list(SysAutoCodeResult sysAutoCodeResult) {
        return sysAutoCodeResultMapper.selectSysAutoCodeResultList(sysAutoCodeResult);
    }

    @Override
    public void saveAutoCodeResult(SysAutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.add(sysAutoCodeResult);
    }

    @Override
    public void updateAutoCodeResult(SysAutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.updateAutoCodeResult(sysAutoCodeResult);
    }
}
