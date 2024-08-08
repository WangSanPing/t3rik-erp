package com.t3rik.mes.dv.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.dv.domain.DvRepair;
import com.t3rik.mes.dv.service.IDvRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备维修单Controller
 * 
 * @author yinjinlu
 * @date 2022-08-06
 */
@RestController
@RequestMapping("/mes/dv/repair")
public class DvRepairController extends BaseController
{
    @Autowired
    private IDvRepairService dvRepairService;

    /**
     * 查询设备维修单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvRepair dvRepair)
    {
        startPage();
        List<DvRepair> list = dvRepairService.selectDvRepairList(dvRepair);
        return getDataTable(list);
    }

    /**
     * 导出设备维修单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:export')")
    @Log(title = "设备维修单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvRepair dvRepair)
    {
        List<DvRepair> list = dvRepairService.selectDvRepairList(dvRepair);
        ExcelUtil<DvRepair> util = new ExcelUtil<DvRepair>(DvRepair.class);
        util.exportExcel(response, list, "设备维修单数据");
    }

    /**
     * 获取设备维修单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:query')")
    @GetMapping(value = "/{repairId}")
    public AjaxResult getInfo(@PathVariable("repairId") Long repairId)
    {
        return AjaxResult.success(dvRepairService.selectDvRepairByRepairId(repairId));
    }

    /**
     * 新增设备维修单
     */
    @PreAuthorize("@ss.hasPermi('dv:repair:add')")
    @Log(title = "设备维修单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvRepair dvRepair)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(dvRepair))){
            return AjaxResult.error("维修单编号已存！");
        }
        return toAjax(dvRepairService.insertDvRepair(dvRepair));
    }

    /**
     * 修改设备维修单
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:edit')")
    @Log(title = "设备维修单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvRepair dvRepair)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(dvRepair))){
            return AjaxResult.error("维修单编号已存！");
        }
        return toAjax(dvRepairService.updateDvRepair(dvRepair));
    }

    /**
     * 删除设备维修单
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:remove')")
    @Log(title = "设备维修单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds)
    {
        return toAjax(dvRepairService.deleteDvRepairByRepairIds(repairIds));
    }
}
