package com.t3rik.mes.cal.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.cal.domain.CalPlan;
import com.t3rik.mes.cal.domain.CalPlanTeam;
import com.t3rik.mes.cal.service.ICalPlanService;
import com.t3rik.mes.cal.service.ICalPlanTeamService;
import com.t3rik.mes.cal.service.ICalShiftService;
import com.t3rik.mes.cal.service.ICalTeamshiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 排班计划Controller
 * 
 * @author yinjinlu
 * @date 2022-06-06
 */
@RestController
@RequestMapping("/mes/cal/calplan")
public class CalPlanController extends BaseController
{
    @Autowired
    private ICalPlanService calPlanService;

    @Autowired
    private ICalShiftService calShiftService;

    @Autowired
    private ICalPlanTeamService calPlanTeamService;

    @Autowired
    private ICalTeamshiftService calTeamshiftService;

    /**
     * 查询排班计划列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(CalPlan calPlan)
    {
        startPage();
        List<CalPlan> list = calPlanService.selectCalPlanList(calPlan);
        return getDataTable(list);
    }

    /**
     * 导出排班计划列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:export')")
    @Log(title = "排班计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalPlan calPlan)
    {
        List<CalPlan> list = calPlanService.selectCalPlanList(calPlan);
        ExcelUtil<CalPlan> util = new ExcelUtil<CalPlan>(CalPlan.class);
        util.exportExcel(response, list, "排班计划数据");
    }

    /**
     * 获取排班计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(calPlanService.selectCalPlanByPlanId(planId));
    }

    /**
     * 新增排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:add')")
    @Log(title = "排班计划", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody CalPlan calPlan)
    {
        int ret = calPlanService.insertCalPlan(calPlan);
        //根据选择的轮班方式生成默认的班次
        calShiftService.addDefaultShift(calPlan.getPlanId(),calPlan.getShiftType());
        return toAjax(ret);
    }


    /**
     * 修改排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:edit')")
    @Log(title = "排班计划", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody CalPlan calPlan)
    {
        if(UserConstants.ORDER_STATUS_CONFIRMED.equals(calPlan.getStatus())){

            //检查班组配置
            List<CalPlanTeam> teams = calPlanTeamService.selectCalPlanTeamListByPlanId(calPlan.getPlanId());
            if(CollectionUtil.isEmpty(teams)){
                return AjaxResult.error("请配置班组!");
            } else if(teams.size() != 2 && UserConstants.CAL_SHIFT_TYPE_TWO.equals(calPlan.getShiftType())){
                return AjaxResult.error("两班倒请配置两个班组!");
            } else if(teams.size() !=3 && UserConstants.CAL_SHIFT_TYPE_THREE.equals(calPlan.getShiftType())){
                return AjaxResult.error("三倒请配置三个班组!");
            }

            calTeamshiftService.genRecords(calPlan.getPlanId());
        }
        return toAjax(calPlanService.updateCalPlan(calPlan));
    }

    /**
     * 删除排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:remove')")
    @Log(title = "排班计划", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        for (Long planId:planIds
             ) {
            //状态判断
            CalPlan plan = calPlanService.selectCalPlanByPlanId(planId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(plan.getStatus())){
                return AjaxResult.error("只能删除草稿状态单据！");
            }
            calShiftService.deleteByPlanId(planId);
            calPlanTeamService.deleteByPlanId(planId);
        }
        return toAjax(calPlanService.deleteCalPlanByPlanIds(planIds));
    }
}
