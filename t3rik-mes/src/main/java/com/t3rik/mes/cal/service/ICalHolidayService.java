package com.t3rik.mes.cal.service;

import java.util.List;
import com.t3rik.mes.cal.domain.CalHoliday;

/**
 * 节假日设置Service接口
 * 
 * @author yinjinlu
 * @date 2022-06-08
 */
public interface ICalHolidayService 
{
    /**
     * 查询节假日设置
     * 
     * @param holidayId 节假日设置主键
     * @return 节假日设置
     */
    public CalHoliday selectCalHolidayByHolidayId(Long holidayId);

    /**
     * 查询节假日设置列表
     * 
     * @param calHoliday 节假日设置
     * @return 节假日设置集合
     */
    public List<CalHoliday> selectCalHolidayList(CalHoliday calHoliday);

    /**
     * 新增节假日设置
     * 
     * @param calHoliday 节假日设置
     * @return 结果
     */
    public int insertCalHoliday(CalHoliday calHoliday);

    /**
     * 修改节假日设置
     * 
     * @param calHoliday 节假日设置
     * @return 结果
     */
    public int updateCalHoliday(CalHoliday calHoliday);

    /**
     * 批量删除节假日设置
     * 
     * @param holidayIds 需要删除的节假日设置主键集合
     * @return 结果
     */
    public int deleteCalHolidayByHolidayIds(Long[] holidayIds);

    /**
     * 删除节假日设置信息
     * 
     * @param holidayId 节假日设置主键
     * @return 结果
     */
    public int deleteCalHolidayByHolidayId(Long holidayId);

}
