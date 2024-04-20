package com.t3rik.system.strategy;

import com.t3rik.common.core.domain.entity.SysAutoCodePart;

public interface PartTypeTemplate {
    /**
     * 分段的处理规则
     * @param sysAutoCodePart
     * @return
     */
    String partHandle(SysAutoCodePart sysAutoCodePart);
}
