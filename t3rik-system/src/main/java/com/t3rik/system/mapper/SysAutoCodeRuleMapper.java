package com.t3rik.system.mapper;

import com.t3rik.common.core.domain.entity.SysAutoCodeRule;

import java.util.List;

public interface SysAutoCodeRuleMapper {

    public List<SysAutoCodeRule> selectSysAutoCodeResultList(SysAutoCodeRule sysAutoCodeRule);

    public SysAutoCodeRule findById(Long ruleId);

    public int add(SysAutoCodeRule sysAutoCodeRule);

    public int updateSysAutoCodeRule(SysAutoCodeRule sysAutoCodeRule);

    public int deleteById(Long ruleId);

    public SysAutoCodeRule checkRuleCodeUnique(String ruleCode);

    public SysAutoCodeRule checkRuleNameUnique(String ruleName);
}
