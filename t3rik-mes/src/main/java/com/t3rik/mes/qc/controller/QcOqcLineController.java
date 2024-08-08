package com.t3rik.mes.qc.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcOqcLine;
import com.t3rik.mes.qc.service.IQcOqcLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出货检验单行Controller
 * 
 * @author yinjinlu
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/mes/qc/oqcline")
public class QcOqcLineController extends BaseController
{
    @Autowired
    private IQcOqcLineService qcOqcLineService;

    /**
     * 查询出货检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcOqcLine qcOqcLine)
    {
        startPage();
        List<QcOqcLine> list = qcOqcLineService.selectQcOqcLineList(qcOqcLine);
        return getDataTable(list);
    }

    /**
     * 导出出货检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:export')")
    @Log(title = "出货检验单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcOqcLine qcOqcLine)
    {
        List<QcOqcLine> list = qcOqcLineService.selectQcOqcLineList(qcOqcLine);
        ExcelUtil<QcOqcLine> util = new ExcelUtil<QcOqcLine>(QcOqcLine.class);
        util.exportExcel(response, list, "出货检验单行数据");
    }

    /**
     * 获取出货检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcOqcLineService.selectQcOqcLineByLineId(lineId));
    }

    /**
     * 新增出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:add')")
    @Log(title = "出货检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcOqcLine qcOqcLine)
    {
        return toAjax(qcOqcLineService.insertQcOqcLine(qcOqcLine));
    }

    /**
     * 修改出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:edit')")
    @Log(title = "出货检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcOqcLine qcOqcLine)
    {
        return toAjax(qcOqcLineService.updateQcOqcLine(qcOqcLine));
    }

    /**
     * 删除出货检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:oqc:remove')")
    @Log(title = "出货检验单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcOqcLineService.deleteQcOqcLineByLineIds(lineIds));
    }
}
