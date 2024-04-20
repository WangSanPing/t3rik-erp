package com.t3rik.mes.report.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.report.domain.UreportFileTbl;
import com.t3rik.mes.report.service.IUreportFileTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报表管理Controller
 *
 * @author yanshikui
 * @date 2022-10-07
 */
@RestController
@RequestMapping("/ureportM")
public class UreportFileTblController extends BaseController
{
    @Autowired
    private IUreportFileTblService ureportFileTblService;

    /**
     * 查询报表管理列表
     */
    @PreAuthorize("@ss.hasPermi('mes:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(UreportFileTbl ureportFileTbl)
    {
        startPage();
        List<UreportFileTbl> list = ureportFileTblService.selectUreportFileTblList(ureportFileTbl);
        return getDataTable(list);
    }

    /**
     * 导出报表管理列表
     */
    @PreAuthorize("@ss.hasPermi('mes:report:export')")
    @Log(title = "报表管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UreportFileTbl ureportFileTbl)
    {
        List<UreportFileTbl> list = ureportFileTblService.selectUreportFileTblList(ureportFileTbl);
        ExcelUtil<UreportFileTbl> util = new ExcelUtil<UreportFileTbl>(UreportFileTbl.class);
        util.exportExcel(response, list, "报表管理数据");
    }

    /**
     * 获取报表管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ureportFileTblService.selectUreportFileTblById(id));
    }

    /**
     * 新增报表管理
     */
    @PreAuthorize("@ss.hasPermi('mes:report:add')")
    @Log(title = "报表管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UreportFileTbl ureportFileTbl)
    {
        return toAjax(ureportFileTblService.insertUreportFileTbl(ureportFileTbl));
    }

    /**
     * 修改报表管理
     */
    @PreAuthorize("@ss.hasPermi('mes:report:edit')")
    @Log(title = "报表管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UreportFileTbl ureportFileTbl)
    {
        return toAjax(ureportFileTblService.updateUreportFileTbl(ureportFileTbl));
    }

    /**
     * 删除报表管理
     */
    @PreAuthorize("@ss.hasPermi('mes:report:remove')")
    @Log(title = "报表管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ureportFileTblService.deleteUreportFileTblByIds(ids));
    }
}
