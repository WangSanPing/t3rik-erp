package com.t3rik.system.service.impl;

import com.t3rik.common.enums.system.TimeRangeEnum;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.system.domain.dto.XYChartDTO;
import com.t3rik.system.mapper.DashboardMapper;
import com.t3rik.system.service.IDashboardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 仪表盘数据统计
 *
 * @author t3rik
 * @date 2025/4/9 22:12
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

    @Resource
    private DashboardMapper dashboardMapper;

    /**
     * 获取登录数据
     */
    @Override
    public XYChartDTO getLoginData(TimeRangeEnum timeRangeEnum) {

        // 从数据库查询登录数据
        List<XYChartDTO.XYSeries> dbResult = dashboardMapper.getLoginDataByTimeRange(timeRangeEnum);
        // 获取范围内的所有日期
        List<String> allDates = DateUtils.getAllDates(timeRangeEnum);

        return getXyChartDTO(dbResult, allDates);
    }

    /**
     * 将查询结果按日期映射
     */
    private XYChartDTO getXyChartDTO(List<XYChartDTO.XYSeries> dbResult, List<String> allDates) {
        Map<String, Integer> dateCountMap = new HashMap<>();
        for (XYChartDTO.XYSeries row : dbResult) {
            // 日期
            String date = row.getName();
            // 数值
            Integer count = row.getCount();
            dateCountMap.put(date, count);
        }
        // 补全日期，确保每个日期都出现，缺失的日期登录次数为 0
        List<String> xAxisData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (String date : allDates) {
            xAxisData.add(date);
            yData.add(dateCountMap.getOrDefault(date, 0)); // 如果日期缺失，登录次数为 0
        }
        // 创建一个 XYSeries 并设置它
        XYChartDTO.XYSeries series = new XYChartDTO.XYSeries();
        series.setName("访问量");
        series.setData(yData);
        series.setColor("#1abc9c");
        // 创建最终的 XYChartDTO
        XYChartDTO dto = new XYChartDTO();
        dto.setXAxisData(xAxisData);
        dto.setSeries(Collections.singletonList(series));
        return dto;
    }
}
