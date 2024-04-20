package com.t3rik.system.strategy;

import com.t3rik.common.core.domain.entity.SysAutoCodePart;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PartTypeFixCharHandler implements PartTypeTemplate {
    @Override
    public String partHandle(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePart.getFixCharacter();
    }
}
