package com.t3rik.system.service;

import com.t3rik.common.enums.system.TimeRangeEnum;
import com.t3rik.system.domain.dto.XYChartDTO;

/**
 * 仪表盘数据统计
 *
 * @author t3rik
 * @date 2025/4/9 22:12
 */
public interface IDashboardService {

    /**
     * 获取登录数据
     */
    XYChartDTO getLoginData(TimeRangeEnum timeRangeEnum);
}
