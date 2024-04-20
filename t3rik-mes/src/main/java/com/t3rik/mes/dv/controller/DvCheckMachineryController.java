package com.t3rik.mes.dv.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.dv.domain.DvCheckMachinery;
import com.t3rik.mes.dv.service.IDvCheckMachineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 点检设备Controller
 * 
 * @author yinjinlu
 * @date 2022-06-17
 */
@RestController
@RequestMapping("/mes/dv/checkmachinery")
public class DvCheckMachineryController extends BaseController
{
    @Autowired
    private IDvCheckMachineryService dvCheckMachineryService;

    /**
     * 查询点检设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DvCheckMachinery dvCheckMachinery)
    {
        startPage();
        List<DvCheckMachinery> list = dvCheckMachineryService.selectDvCheckMachineryList(dvCheckMachinery);
        return getDataTable(list);
    }

    /**
     * 导出点检设备列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:export')")
    @Log(title = "点检设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvCheckMachinery dvCheckMachinery)
    {
        List<DvCheckMachinery> list = dvCheckMachineryService.selectDvCheckMachineryList(dvCheckMachinery);
        ExcelUtil<DvCheckMachinery> util = new ExcelUtil<DvCheckMachinery>(DvCheckMachinery.class);
        util.exportExcel(response, list, "点检设备数据");
    }

    /**
     * 获取点检设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(dvCheckMachineryService.selectDvCheckMachineryByRecordId(recordId));
    }

    /**
     * 新增点检设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:add')")
    @Log(title = "点检设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvCheckMachinery dvCheckMachinery)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvCheckMachineryService.checkMachineryUnique(dvCheckMachinery))){
            return AjaxResult.error("设备已设置过点检计划！");
        }
        return toAjax(dvCheckMachineryService.insertDvCheckMachinery(dvCheckMachinery));
    }

    /**
     * 修改点检设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:edit')")
    @Log(title = "点检设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckMachinery dvCheckMachinery)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvCheckMachineryService.checkMachineryUnique(dvCheckMachinery))){
            return AjaxResult.error("设备已设置过点检计划！");
        }
        return toAjax(dvCheckMachineryService.updateDvCheckMachinery(dvCheckMachinery));
    }

    /**
     * 删除点检设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:remove')")
    @Log(title = "点检设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(dvCheckMachineryService.deleteDvCheckMachineryByRecordIds(recordIds));
    }
}
