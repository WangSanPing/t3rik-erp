package com.t3rik.mes.pro.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProTransConsume;
import com.t3rik.mes.pro.service.IProTransConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料消耗记录Controller
 * 
 * @author yinjinlu
 * @date 2022-07-24
 */
@RestController
@RequestMapping("/mes/pro/transconsume")
public class ProTransConsumeController extends BaseController
{
    @Autowired
    private IProTransConsumeService proTransConsumeService;

    /**
     * 查询物料消耗记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProTransConsume proTransConsume)
    {
        startPage();
        List<ProTransConsume> list = proTransConsumeService.selectProTransConsumeList(proTransConsume);
        return getDataTable(list);
    }

    /**
     * 导出物料消耗记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:transconsume:export')")
    @Log(title = "物料消耗记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProTransConsume proTransConsume)
    {
        List<ProTransConsume> list = proTransConsumeService.selectProTransConsumeList(proTransConsume);
        ExcelUtil<ProTransConsume> util = new ExcelUtil<ProTransConsume>(ProTransConsume.class);
        util.exportExcel(response, list, "物料消耗记录数据");
    }

    /**
     * 获取物料消耗记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:transconsume:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proTransConsumeService.selectProTransConsumeByRecordId(recordId));
    }

    /**
     * 新增物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:transconsume:add')")
    @Log(title = "物料消耗记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProTransConsume proTransConsume)
    {
        return toAjax(proTransConsumeService.insertProTransConsume(proTransConsume));
    }

    /**
     * 修改物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:transconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProTransConsume proTransConsume)
    {
        return toAjax(proTransConsumeService.updateProTransConsume(proTransConsume));
    }

    /**
     * 删除物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:transconsume:remove')")
    @Log(title = "物料消耗记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proTransConsumeService.deleteProTransConsumeByRecordIds(recordIds));
    }
}
