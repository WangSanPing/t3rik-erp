package com.t3rik.controller;

import com.t3rik.common.core.domain.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author t3rik
 * @date 2025/1/10 00:10
 */
@RestController
@RequestMapping("/t3rik/jmreport")
public class JMReportController {
    @GetMapping
    public AjaxResult getJMReportUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http 或 https
        String serverName = request.getServerName(); // 主机名或 IP
        int serverPort = request.getServerPort(); // 端口号
        // 检验是否是服务器中通过java -jar运行，如果是，需要加入nginx转发路径
        String classPath = System.getProperty("java.class.path");
        String url;
        if (classPath.contains(".jar")) {
            url = scheme + "://" + serverName + ":" + serverPort + "/prod-api/jmreport/list";
        } else {
            url = scheme + "://" + serverName + ":" + serverPort + "/jmreport/list";
        }

        return AjaxResult.success(url);
    }
}
