package com.t3rik.service;

import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.stereotype.Service;

/**
 * @author t3rik
 * @date 2025/1/9 20:44
 */
@Service
public class JMReportTokenServiceImpl implements JmReportTokenServiceI {
    @Override
    public String getUsername(String s) {
        return "";
    }

    @Override
    public String[] getRoles(String s) {
        return new String[0];
    }

    @Override
    public Boolean verifyToken(String s) {
        return Boolean.TRUE;
    }
}
