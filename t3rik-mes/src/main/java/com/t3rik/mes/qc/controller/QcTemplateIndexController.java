package com.t3rik.mes.qc.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcIndex;
import com.t3rik.mes.qc.domain.QcTemplateIndex;
import com.t3rik.mes.qc.service.IQcIndexService;
import com.t3rik.mes.qc.service.IQcTemplateIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 检测模板-检测项Controller
 * 
 * @author yinjinlu
 * @date 2022-05-18
 */
@RestController
@RequestMapping("/mes/qc/templateindex")
public class QcTemplateIndexController extends BaseController
{
    @Autowired
    private IQcTemplateIndexService qcTemplateIndexService;

    @Autowired
    private IQcIndexService qcIndexService;

    /**
     * 查询检测模板-检测项列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcTemplateIndex qcTemplateIndex)
    {
        startPage();
        List<QcTemplateIndex> list = qcTemplateIndexService.selectQcTemplateIndexList(qcTemplateIndex);
        return getDataTable(list);
    }

    /**
     * 导出检测模板-检测项列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:export')")
    @Log(title = "检测模板-检测项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcTemplateIndex qcTemplateIndex)
    {
        List<QcTemplateIndex> list = qcTemplateIndexService.selectQcTemplateIndexList(qcTemplateIndex);
        ExcelUtil<QcTemplateIndex> util = new ExcelUtil<QcTemplateIndex>(QcTemplateIndex.class);
        util.exportExcel(response, list, "检测模板-检测项数据");
    }

    /**
     * 获取检测模板-检测项详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(qcTemplateIndexService.selectQcTemplateIndexByRecordId(recordId));
    }

    /**
     * 新增检测模板-检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:add')")
    @Log(title = "检测模板-检测项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcTemplateIndex qcTemplateIndex)
    {
        QcIndex index =qcIndexService.selectQcIndexByIndexId(qcTemplateIndex.getIndexId());
        qcTemplateIndex.setIndexCode(index.getIndexCode());
        qcTemplateIndex.setIndexName(index.getIndexName());
        qcTemplateIndex.setIndexType(index.getIndexType());
        qcTemplateIndex.setQcTool(index.getQcTool());
        return toAjax(qcTemplateIndexService.insertQcTemplateIndex(qcTemplateIndex));
    }

    /**
     * 修改检测模板-检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:edit')")
    @Log(title = "检测模板-检测项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcTemplateIndex qcTemplateIndex)
    {
        QcIndex index =qcIndexService.selectQcIndexByIndexId(qcTemplateIndex.getIndexId());
        qcTemplateIndex.setIndexCode(index.getIndexCode());
        qcTemplateIndex.setIndexName(index.getIndexName());
        qcTemplateIndex.setIndexType(index.getIndexType());
        qcTemplateIndex.setQcTool(index.getQcTool());
        return toAjax(qcTemplateIndexService.updateQcTemplateIndex(qcTemplateIndex));
    }

    /**
     * 删除检测模板-检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:remove')")
    @Log(title = "检测模板-检测项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(qcTemplateIndexService.deleteQcTemplateIndexByRecordIds(recordIds));
    }
}
