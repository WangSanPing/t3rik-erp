package com.t3rik.mes.cal.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.cal.domain.CalCalendar;
import com.t3rik.mes.cal.domain.CalHoliday;
import com.t3rik.mes.cal.service.ICalCalendarService;
import com.t3rik.mes.cal.service.ICalHolidayService;
import com.t3rik.mes.cal.utils.CalendarUtil;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排班日历
 *
 * @author yinjinlu
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/mes/cal/calendar")
public class CalCalendarController extends BaseController {

    @Resource
    private ICalCalendarService calCalendarService;

    @Resource
    private ICalHolidayService calHolidayService;

    @PreAuthorize("@ss.hasPermi('mes:cal:calendar:list')")
    @GetMapping("/list")
    public AjaxResult getCalendars(CalCalendar calCalendar) {

        // 2025-6-11
        Date day = calCalendar.getDate() == null ? new Date() : calCalendar.getDate();
        List<CalCalendar> days =
                switch (calCalendar.getQueryType()) {
                    case UserConstants.CAL_QUERY_BY_TYPE ->
                            calCalendarService.getCalendarByType(day, calCalendar.getCalendarType());
                    case UserConstants.CAL_QUERY_BY_TEAM ->
                            calCalendarService.getCalendarByTeam(day, calCalendar.getTeamId());
                    default -> calCalendarService.getCalendarByUser(day, calCalendar.getUserId());
                };
        return AjaxResult.success(getCalendarsWithoutHoliday(days));
    }


    /**
     * 过滤掉节假日
     *
     * @param days
     * @return
     */
    private List<CalCalendar> getCalendarsWithoutHoliday(List<CalCalendar> days) {
        CalHoliday param = new CalHoliday();
        List<CalHoliday> holidays = calHolidayService.selectCalHolidayList(param);
        if (CollUtil.isNotEmpty(holidays)) {
            List<CalCalendar> results = days.stream().filter(
                    calCalendar -> holidays.stream().filter(calHoliday -> calCalendar.getTheDay().equals(CalendarUtil.getDateStr(calHoliday.getTheDay()))).collect(Collectors.toList()).size() == 0
            ).collect(Collectors.toList());
            return results;
        }
        return days;
    }
}
