package com.t3rik.system.strategy;

import com.t3rik.common.core.domain.entity.SysAutoCodePart;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Order(1)
public class PartTypeNowDateHandler implements PartTypeTemplate {

    @Override
    public String partHandle(SysAutoCodePart sysAutoCodePart) {
        String formatDate = sysAutoCodePart.getDateFormat();
        return DateTimeFormatter.ofPattern(formatDate).format(LocalDateTime.now());
    }
}
