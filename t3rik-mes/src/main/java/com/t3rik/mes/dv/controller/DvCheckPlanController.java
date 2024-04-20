package com.t3rik.mes.dv.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.dv.domain.DvCheckMachinery;
import com.t3rik.mes.dv.domain.DvCheckPlan;
import com.t3rik.mes.dv.domain.DvCheckSubject;
import com.t3rik.mes.dv.service.IDvCheckMachineryService;
import com.t3rik.mes.dv.service.IDvCheckPlanService;
import com.t3rik.mes.dv.service.IDvCheckSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备点检计划头Controller
 * 
 * @author yinjinlu
 * @date 2022-06-16
 */
@RestController
@RequestMapping("/mes/dv/checkplan")
public class DvCheckPlanController extends BaseController
{
    @Autowired
    private IDvCheckPlanService dvCheckPlanService;

    @Autowired
    IDvCheckMachineryService dvCheckMachineryService;

    @Autowired
    IDvCheckSubjectService dvCheckSubjectService;

    /**
     * 查询设备点检计划头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvCheckPlan dvCheckPlan)
    {
        startPage();
        List<DvCheckPlan> list = dvCheckPlanService.selectDvCheckPlanList(dvCheckPlan);
        return getDataTable(list);
    }

    /**
     * 导出设备点检计划头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:export')")
    @Log(title = "设备点检计划头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvCheckPlan dvCheckPlan)
    {
        List<DvCheckPlan> list = dvCheckPlanService.selectDvCheckPlanList(dvCheckPlan);
        ExcelUtil<DvCheckPlan> util = new ExcelUtil<DvCheckPlan>(DvCheckPlan.class);
        util.exportExcel(response, list, "设备点检计划头数据");
    }

    /**
     * 获取设备点检计划头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(dvCheckPlanService.selectDvCheckPlanByPlanId(planId));
    }

    /**
     * 新增设备点检计划头
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:add')")
    @Log(title = "设备点检计划头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvCheckPlan dvCheckPlan)
    {
        return toAjax(dvCheckPlanService.insertDvCheckPlan(dvCheckPlan));
    }

    /**
     * 修改设备点检计划头
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:edit')")
    @Log(title = "设备点检计划头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckPlan dvCheckPlan)
    {
        if(UserConstants.ORDER_STATUS_FINISHED.equals(dvCheckPlan.getStatus())){
            DvCheckMachinery para1 = new DvCheckMachinery();
            para1.setPlanId(dvCheckPlan.getPlanId());
            List<DvCheckMachinery> machinerys = dvCheckMachineryService.selectDvCheckMachineryList(para1);
            if(!CollUtil.isNotEmpty(machinerys)){
                return AjaxResult.error("请指定设备!");
            }

            DvCheckSubject para2 = new DvCheckSubject();
            para2.setPlanId(dvCheckPlan.getPlanId());
            List<DvCheckSubject> subjects = dvCheckSubjectService.selectDvCheckSubjectList(para2);
            if(!CollUtil.isNotEmpty(subjects)){
                return AjaxResult.error("请指定项目!");
            }
        }
        return toAjax(dvCheckPlanService.updateDvCheckPlan(dvCheckPlan));
    }

    /**
     * 删除设备点检计划头
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:remove')")
    @Log(title = "设备点检计划头", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        for (Long planId:planIds
             ) {
            DvCheckPlan plan = dvCheckPlanService.selectDvCheckPlanByPlanId(planId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(plan.getStatus())){
                return AjaxResult.error("只能删除草稿状态单据！");
            }

            dvCheckMachineryService.deleteByPlanId(planId);
            dvCheckSubjectService.deleteByPlanId(planId);
        }



        return toAjax(dvCheckPlanService.deleteDvCheckPlanByPlanIds(planIds));
    }
}
