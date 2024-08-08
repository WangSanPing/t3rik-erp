package com.t3rik.mes.pro.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProTaskIssue;
import com.t3rik.mes.pro.service.IProTaskIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生产任务投料Controller
 * 
 * @author yinjinlu
 * @date 2022-07-22
 */
@RestController
@RequestMapping("/mes/pro/taskissue")
public class ProTaskIssueController extends BaseController
{
    @Autowired
    private IProTaskIssueService proTaskIssueService;


    /**
     * 查询生产任务投料列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProTaskIssue proTaskIssue)
    {
        startPage();
        List<ProTaskIssue> list = proTaskIssueService.selectProTaskIssueList(proTaskIssue);
        return getDataTable(list);
    }

    /**
     * 导出生产任务投料列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:export')")
    @Log(title = "生产任务投料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProTaskIssue proTaskIssue)
    {
        List<ProTaskIssue> list = proTaskIssueService.selectProTaskIssueList(proTaskIssue);
        ExcelUtil<ProTaskIssue> util = new ExcelUtil<ProTaskIssue>(ProTaskIssue.class);
        util.exportExcel(response, list, "生产任务投料数据");
    }

    /**
     * 获取生产任务投料详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proTaskIssueService.selectProTaskIssueByRecordId(recordId));
    }

    /**
     * 新增生产任务投料
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:add')")
    @Log(title = "生产任务投料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProTaskIssue proTaskIssue)
    {
        return toAjax(proTaskIssueService.insertProTaskIssue(proTaskIssue));
    }

    /**
     * 修改生产任务投料
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:edit')")
    @Log(title = "生产任务投料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProTaskIssue proTaskIssue)
    {
        return toAjax(proTaskIssueService.updateProTaskIssue(proTaskIssue));
    }

    /**
     * 删除生产任务投料
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:remove')")
    @Log(title = "生产任务投料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proTaskIssueService.deleteProTaskIssueByRecordIds(recordIds));
    }
}
