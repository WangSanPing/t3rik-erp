package com.t3rik.mes.cal.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.cal.domain.CalPlanTeam;
import com.t3rik.mes.cal.service.ICalPlanTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计划班组Controller
 * 
 * @author yinjinlu
 * @date 2022-06-07
 */
@RestController
@RequestMapping("/mes/cal/planteam")
public class CalPlanTeamController extends BaseController
{
    @Autowired
    private ICalPlanTeamService calPlanTeamService;

    /**
     * 查询计划班组列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CalPlanTeam calPlanTeam)
    {
        startPage();
        List<CalPlanTeam> list = calPlanTeamService.selectCalPlanTeamList(calPlanTeam);
        return getDataTable(list);
    }

    /**
     * 导出计划班组列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:export')")
    @Log(title = "计划班组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalPlanTeam calPlanTeam)
    {
        List<CalPlanTeam> list = calPlanTeamService.selectCalPlanTeamList(calPlanTeam);
        ExcelUtil<CalPlanTeam> util = new ExcelUtil<CalPlanTeam>(CalPlanTeam.class);
        util.exportExcel(response, list, "计划班组数据");
    }

    /**
     * 获取计划班组详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(calPlanTeamService.selectCalPlanTeamByRecordId(recordId));
    }

    /**
     * 新增计划班组
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:add')")
    @Log(title = "计划班组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CalPlanTeam calPlanTeam)
    {
        if (UserConstants.NOT_UNIQUE.equals(calPlanTeamService.checkPlanTeamUnique(calPlanTeam))) {
            return AjaxResult.error("班组已添加，不能重复添加！");
        }
        return toAjax(calPlanTeamService.insertCalPlanTeam(calPlanTeam));
    }

    /**
     * 修改计划班组
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:edit')")
    @Log(title = "计划班组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CalPlanTeam calPlanTeam)
    {
        return toAjax(calPlanTeamService.updateCalPlanTeam(calPlanTeam));
    }

    /**
     * 删除计划班组
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:remove')")
    @Log(title = "计划班组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(calPlanTeamService.deleteCalPlanTeamByRecordIds(recordIds));
    }
}
