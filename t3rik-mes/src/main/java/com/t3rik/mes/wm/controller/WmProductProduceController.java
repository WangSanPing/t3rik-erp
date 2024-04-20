package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmProductProduce;
import com.t3rik.mes.wm.service.IWmProductProduceLineService;
import com.t3rik.mes.wm.service.IWmProductProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品产出记录Controller
 * 
 * @author yinjinlu
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/mes/wm/productproduce")
public class WmProductProduceController extends BaseController
{
    @Autowired
    private IWmProductProduceService wmProductProduceService;

    @Autowired
    private IWmProductProduceLineService wmProductProduceLineService;
    /**
     * 查询产品产出记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductProduce wmProductProduce)
    {
        startPage();
        List<WmProductProduce> list = wmProductProduceService.selectWmProductProduceList(wmProductProduce);
        return getDataTable(list);
    }

    /**
     * 导出产品产出记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:export')")
    @Log(title = "产品产出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductProduce wmProductProduce)
    {
        List<WmProductProduce> list = wmProductProduceService.selectWmProductProduceList(wmProductProduce);
        ExcelUtil<WmProductProduce> util = new ExcelUtil<WmProductProduce>(WmProductProduce.class);
        util.exportExcel(response, list, "产品产出记录数据");
    }

    /**
     * 获取产品产出记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(wmProductProduceService.selectWmProductProduceByRecordId(recordId));
    }

    /**
     * 新增产品产出记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:add')")
    @Log(title = "产品产出记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductProduce wmProductProduce)
    {
        return toAjax(wmProductProduceService.insertWmProductProduce(wmProductProduce));
    }

    /**
     * 修改产品产出记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:edit')")
    @Log(title = "产品产出记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductProduce wmProductProduce)
    {
        return toAjax(wmProductProduceService.updateWmProductProduce(wmProductProduce));
    }

    /**
     * 删除产品产出记录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:remove')")
    @Log(title = "产品产出记录", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        for (Long recordId: recordIds
             ) {
            wmProductProduceLineService.deleteByRecordId(recordId);
        }
        return toAjax(wmProductProduceService.deleteWmProductProduceByRecordIds(recordIds));
    }
}
