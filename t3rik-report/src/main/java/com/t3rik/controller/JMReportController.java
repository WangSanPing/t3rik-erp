package com.t3rik.controller;

import com.t3rik.common.core.domain.AjaxResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author t3rik
 * @date 2025/1/10 00:10
 */
@Slf4j
@RestController
@RequestMapping("/t3rik/jmreport")
public class JMReportController {

    @Resource
    private Environment environment;
    @Value("${url}")
    private String url;

    @GetMapping
    public AjaxResult getJMReportUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http 或 https
        String serverName = request.getServerName(); // 主机名或 IP
        int serverPort = request.getServerPort(); // 端口号
        String[] activeProfiles = environment.getActiveProfiles();
        String reportUrl;
        if (activeProfiles[0].equals("pro")) {
            reportUrl = url + "/prod-api/jmreport/list";
        } else {
            reportUrl = scheme + "://" + serverName + ":" + serverPort + "/jmreport/list";
        }
        log.info("当前访问的报表地址: {}", reportUrl);
        return AjaxResult.success(reportUrl);
    }
}
