package com.t3rik.mes.qc.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcIndex;
import com.t3rik.mes.qc.service.IQcIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 检测项Controller
 * 
 * @author yinjinlu
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/mes/qc/qcindex")
public class QcIndexController extends BaseController
{
    @Autowired
    private IQcIndexService qcIndexService;

    /**
     * 查询检测项列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcIndex qcIndex)
    {
        startPage();
        List<QcIndex> list = qcIndexService.selectQcIndexList(qcIndex);
        return getDataTable(list);
    }

    /**
     * 导出检测项列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcindex:export')")
    @Log(title = "检测项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcIndex qcIndex)
    {
        List<QcIndex> list = qcIndexService.selectQcIndexList(qcIndex);
        ExcelUtil<QcIndex> util = new ExcelUtil<QcIndex>(QcIndex.class);
        util.exportExcel(response, list, "检测项数据");
    }

    /**
     * 获取检测项详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcindex:query')")
    @GetMapping(value = "/{indexId}")
    public AjaxResult getInfo(@PathVariable("indexId") Long indexId)
    {
        return AjaxResult.success(qcIndexService.selectQcIndexByIndexId(indexId));
    }

    /**
     * 新增检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcindex:add')")
    @Log(title = "检测项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcIndex qcIndex)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIndexService.checkIndexCodeUnique(qcIndex))){
            return AjaxResult.error("检测项编号已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(qcIndexService.checkIndexNameUnique(qcIndex))){
            return AjaxResult.error("检测项名称已存在！");
        }
        return toAjax(qcIndexService.insertQcIndex(qcIndex));
    }

    /**
     * 修改检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcindex:edit')")
    @Log(title = "检测项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcIndex qcIndex)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIndexService.checkIndexCodeUnique(qcIndex))){
            return AjaxResult.error("检测项编号已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(qcIndexService.checkIndexNameUnique(qcIndex))){
            return AjaxResult.error("检测项名称已存在！");
        }
        return toAjax(qcIndexService.updateQcIndex(qcIndex));
    }

    /**
     * 删除检测项
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcindex:remove')")
    @Log(title = "检测项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{indexIds}")
    public AjaxResult remove(@PathVariable Long[] indexIds)
    {
        return toAjax(qcIndexService.deleteQcIndexByIndexIds(indexIds));
    }
}
