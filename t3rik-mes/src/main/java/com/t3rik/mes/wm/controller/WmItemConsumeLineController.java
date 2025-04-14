package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmItemConsumeLine;
import com.t3rik.mes.wm.service.IWmItemConsumeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料消耗记录行Controller
 * 
 * @author yinjinlu
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/mes/wm/itemconsumeline")
public class WmItemConsumeLineController extends BaseController
{
    @Autowired
    private IWmItemConsumeLineService wmItemConsumeLineService;

    /**
     * 查询物料消耗记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmItemConsumeLine wmItemConsumeLine)
    {
        startPage();
        List<WmItemConsumeLine> list = wmItemConsumeLineService.selectWmItemConsumeLineList(wmItemConsumeLine);
        return getDataTable(list);
    }

    /**
     * 导出物料消耗记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:export')")
    @Log(title = "物料消耗记录行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmItemConsumeLine wmItemConsumeLine)
    {
        List<WmItemConsumeLine> list = wmItemConsumeLineService.selectWmItemConsumeLineList(wmItemConsumeLine);
        ExcelUtil<WmItemConsumeLine> util = new ExcelUtil<WmItemConsumeLine>(WmItemConsumeLine.class);
        util.exportExcel(response, list, "物料消耗记录行数据");
    }

    /**
     * 获取物料消耗记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmItemConsumeLineService.selectWmItemConsumeLineByLineId(lineId));
    }

    /**
     * 新增物料消耗记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:add')")
    @Log(title = "物料消耗记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmItemConsumeLine wmItemConsumeLine)
    {
        return toAjax(wmItemConsumeLineService.insertWmItemConsumeLine(wmItemConsumeLine));
    }

    /**
     * 修改物料消耗记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:edit')")
    @Log(title = "物料消耗记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemConsumeLine wmItemConsumeLine)
    {
        return toAjax(wmItemConsumeLineService.updateWmItemConsumeLine(wmItemConsumeLine));
    }

    /**
     * 删除物料消耗记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:remove')")
    @Log(title = "物料消耗记录行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmItemConsumeLineService.deleteWmItemConsumeLineByLineIds(lineIds));
    }
}
