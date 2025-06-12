package com.t3rik.mes.cal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.mes.cal.domain.CalCalendar;
import com.t3rik.mes.cal.domain.CalTeamMember;
import com.t3rik.mes.cal.domain.CalTeamshift;
import com.t3rik.mes.cal.mapper.CalTeamMemberMapper;
import com.t3rik.mes.cal.mapper.CalTeamshiftMapper;
import com.t3rik.mes.cal.service.ICalCalendarService;
import com.t3rik.mes.cal.utils.CalendarUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CalCalendarServiceImpl implements ICalCalendarService {

    @Resource
    private CalTeamshiftMapper calTeamshiftMapper;

    @Resource
    private CalTeamMemberMapper calTeamMemberMapper;


    /**
     * 1.循环生成当前月份每一天的CalCalendar
     * 2.根据月份和班组类型查询合适的plan
     * 3.根据plan上的Shift_type和shift_method计算每个班组的班次
     * 4.设置CalShiftTeamBean
     *
     * @param day
     * @param calenderType
     * @return
     */
    @Override
    public List<CalCalendar> getCalendarByType(Date day, String calenderType) {
        List<CalCalendar> calendars = null;
        calendars = CalendarUtil.getDays(day);
        for (CalCalendar cal : calendars
        ) {
            CalTeamshift param2 = new CalTeamshift();
            param2.setTheDay(cal.getTheDay());
            param2.setCalendarType(calenderType);
            List<CalTeamshift> teamshifts = calTeamshiftMapper.selectCalTeamshiftList(param2);
            cal.setTeamShifts(teamshifts);
            if (CollUtil.isNotEmpty(teamshifts)) {
                cal.setShiftType(teamshifts.get(0).getShiftType());
            }
        }
        return calendars;
    }

    @Override
    public List<CalCalendar> getCalendarByTeam(Date day, Long teamId) {
        List<CalCalendar> calendars = null;
        calendars = CalendarUtil.getDays(day);
        for (CalCalendar cal : calendars
        ) {
            CalTeamshift param2 = new CalTeamshift();
            param2.setTheDay(cal.getTheDay());
            param2.setTeamId(teamId);
            List<CalTeamshift> teamshifts = calTeamshiftMapper.selectCalTeamshiftList(param2);
            cal.setTeamShifts(teamshifts);
            if (CollUtil.isNotEmpty(teamshifts)) {
                cal.setShiftType(teamshifts.get(0).getShiftType());
            }
        }
        return calendars;
    }

    @Override
    public List<CalCalendar> getCalendarByUser(Date day, Long userId) {
        List<CalCalendar> calendars = null;

        CalTeamMember param = new CalTeamMember();
        param.setUserId(userId);
        List<CalTeamMember> members = calTeamMemberMapper.selectCalTeamMemberList(param);
        if (CollUtil.isNotEmpty(members)) {
            Long teamId = members.get(0).getTeamId();
            calendars = CalendarUtil.getDays(day);
            for (CalCalendar cal : calendars
            ) {
                CalTeamshift param2 = new CalTeamshift();
                param2.setTheDay(cal.getTheDay());
                param2.setTeamId(teamId);
                List<CalTeamshift> teamshifts = calTeamshiftMapper.selectCalTeamshiftList(param2);
                cal.setTeamShifts(teamshifts);
                if (CollUtil.isNotEmpty(teamshifts)) {
                    cal.setShiftType(teamshifts.get(0).getShiftType());
                }
            }
        }

        return calendars;
    }
}
