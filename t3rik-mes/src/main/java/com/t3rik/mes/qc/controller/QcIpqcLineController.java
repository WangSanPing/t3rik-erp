package com.t3rik.mes.qc.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcIpqcLine;
import com.t3rik.mes.qc.service.IQcIpqcLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 过程检验单行Controller
 * 
 * @author yinjinlu
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/mes/qc/ipqcline")
public class QcIpqcLineController extends BaseController
{
    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    /**
     * 查询过程检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIpqcLine qcIpqcLine)
    {
        startPage();
        List<QcIpqcLine> list = qcIpqcLineService.selectQcIpqcLineList(qcIpqcLine);
        return getDataTable(list);
    }

    /**
     * 导出过程检验单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:export')")
    @Log(title = "过程检验单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcIpqcLine qcIpqcLine)
    {
        List<QcIpqcLine> list = qcIpqcLineService.selectQcIpqcLineList(qcIpqcLine);
        ExcelUtil<QcIpqcLine> util = new ExcelUtil<QcIpqcLine>(QcIpqcLine.class);
        util.exportExcel(response, list, "过程检验单行数据");
    }

    /**
     * 获取过程检验单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(qcIpqcLineService.selectQcIpqcLineByLineId(lineId));
    }

    /**
     * 新增过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:add')")
    @Log(title = "过程检验单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcIpqcLine qcIpqcLine)
    {
        return toAjax(qcIpqcLineService.insertQcIpqcLine(qcIpqcLine));
    }

    /**
     * 修改过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:edit')")
    @Log(title = "过程检验单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcIpqcLine qcIpqcLine)
    {
        return toAjax(qcIpqcLineService.updateQcIpqcLine(qcIpqcLine));
    }

    /**
     * 删除过程检验单行
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:remove')")
    @Log(title = "过程检验单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(qcIpqcLineService.deleteQcIpqcLineByLineIds(lineIds));
    }
}
