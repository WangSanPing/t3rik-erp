package com.t3rik.mes.qc.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcDefect;
import com.t3rik.mes.qc.service.IQcDefectService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 常见缺陷Controller
 * 
 * @author yinjinlu
 * @date 2022-05-19
 */
@RestController
@RequestMapping("/mes/qc/qcdefect")
public class QcDefectController extends BaseController
{
    @Autowired
    private IQcDefectService qcDefectService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询常见缺陷列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcDefect qcDefect)
    {
        startPage();
        List<QcDefect> list = qcDefectService.selectQcDefectList(qcDefect);
        return getDataTable(list);
    }

    /**
     * 导出常见缺陷列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcdefect:export')")
    @Log(title = "常见缺陷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcDefect qcDefect)
    {
        List<QcDefect> list = qcDefectService.selectQcDefectList(qcDefect);
        ExcelUtil<QcDefect> util = new ExcelUtil<QcDefect>(QcDefect.class);
        util.exportExcel(response, list, "常见缺陷数据");
    }

    /**
     * 获取常见缺陷详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcdefect:query')")
    @GetMapping(value = "/{defectId}")
    public AjaxResult getInfo(@PathVariable("defectId") Long defectId)
    {
        return AjaxResult.success(qcDefectService.selectQcDefectByDefectId(defectId));
    }

    /**
     * 新增常见缺陷
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcdefect:add')")
    @Log(title = "常见缺陷", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcDefect qcDefect)
    {
        qcDefect.setDefectCode(autoCodeUtil.genSerialCode(UserConstants.DEFECT_CODE,null));
        return toAjax(qcDefectService.insertQcDefect(qcDefect));
    }

    /**
     * 修改常见缺陷
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcdefect:edit')")
    @Log(title = "常见缺陷", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcDefect qcDefect)
    {
        return toAjax(qcDefectService.updateQcDefect(qcDefect));
    }

    /**
     * 删除常见缺陷
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qcdefect:remove')")
    @Log(title = "常见缺陷", businessType = BusinessType.DELETE)
	@DeleteMapping("/{defectIds}")
    public AjaxResult remove(@PathVariable Long[] defectIds)
    {
        return toAjax(qcDefectService.deleteQcDefectByDefectIds(defectIds));
    }
}
