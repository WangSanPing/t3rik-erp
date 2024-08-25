package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmIssueLine;
import com.t3rik.mes.wm.service.IWmIssueLineService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生产领料单行Controller
 *
 * @author yinjinlu
 * @date 2022-07-14
 */
@RestController
@RequestMapping("/mes/wm/issueline")
public class WmIssueLineController extends BaseController {
    @Resource
    private IWmIssueLineService wmIssueLineService;

    /**
     * 查询生产领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueLine wmIssueLine) {
        startPage();
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineList(wmIssueLine);
        return getDataTable(list);
    }

    /**
     * 导出生产领料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:export')")
    @Log(title = "生产领料单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmIssueLine wmIssueLine) {
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineList(wmIssueLine);
        ExcelUtil<WmIssueLine> util = new ExcelUtil<WmIssueLine>(WmIssueLine.class);
        util.exportExcel(response, list, "生产领料单行数据");
    }

    /**
     * 获取生产领料单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId) {
        return AjaxResult.success(wmIssueLineService.selectWmIssueLineByLineId(lineId));
    }

    /**
     * 新增生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueLine wmIssueLine) {
        wmIssueLine.setCreateBy(getUsername());
        return toAjax(wmIssueLineService.insertWmIssueLine(wmIssueLine));
    }

    /**
     * 修改生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueLine wmIssueLine) {
        return toAjax(wmIssueLineService.updateWmIssueLine(wmIssueLine));
    }

    /**
     * 删除生产领料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds) {
        return toAjax(wmIssueLineService.deleteWmIssueLineByLineIds(lineIds));
    }
}
