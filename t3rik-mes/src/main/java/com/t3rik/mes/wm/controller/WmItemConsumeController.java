package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmItemConsume;
import com.t3rik.mes.wm.service.IWmItemConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料消耗记录Controller
 * 
 * @author yinjinlu
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/mes/wm/itemconsume")
public class WmItemConsumeController extends BaseController
{
    @Autowired
    private IWmItemConsumeService wmItemConsumeService;

    /**
     * 查询物料消耗记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmItemConsume wmItemConsume)
    {
        startPage();
        List<WmItemConsume> list = wmItemConsumeService.selectWmItemConsumeList(wmItemConsume);
        return getDataTable(list);
    }

    /**
     * 导出物料消耗记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:export')")
    @Log(title = "物料消耗记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmItemConsume wmItemConsume)
    {
        List<WmItemConsume> list = wmItemConsumeService.selectWmItemConsumeList(wmItemConsume);
        ExcelUtil<WmItemConsume> util = new ExcelUtil<WmItemConsume>(WmItemConsume.class);
        util.exportExcel(response, list, "物料消耗记录数据");
    }

    /**
     * 获取物料消耗记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(wmItemConsumeService.selectWmItemConsumeByRecordId(recordId));
    }

    /**
     * 新增物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:add')")
    @Log(title = "物料消耗记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmItemConsume wmItemConsume)
    {
        return toAjax(wmItemConsumeService.insertWmItemConsume(wmItemConsume));
    }

    /**
     * 修改物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemConsume wmItemConsume)
    {
        return toAjax(wmItemConsumeService.updateWmItemConsume(wmItemConsume));
    }

    /**
     * 删除物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemconsume:remove')")
    @Log(title = "物料消耗记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(wmItemConsumeService.deleteWmItemConsumeByRecordIds(recordIds));
    }
}
