package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmProductProduceLine;
import com.t3rik.mes.wm.service.IWmProductProduceLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品产出记录行Controller
 * 
 * @author yinjinlu
 * @date 2022-09-22
 */
@RestController
@RequestMapping("/mes/wm/productproduceline")
public class WmProductProduceLineController extends BaseController
{
    @Autowired
    private IWmProductProduceLineService wmProductProduceLineService;

    /**
     * 查询产品产出记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductProduceLine wmProductProduceLine)
    {
        startPage();
        List<WmProductProduceLine> list = wmProductProduceLineService.selectWmProductProduceLineList(wmProductProduceLine);
        return getDataTable(list);
    }

    /**
     * 导出产品产出记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:export')")
    @Log(title = "产品产出记录行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductProduceLine wmProductProduceLine)
    {
        List<WmProductProduceLine> list = wmProductProduceLineService.selectWmProductProduceLineList(wmProductProduceLine);
        ExcelUtil<WmProductProduceLine> util = new ExcelUtil<WmProductProduceLine>(WmProductProduceLine.class);
        util.exportExcel(response, list, "产品产出记录行数据");
    }

    /**
     * 获取产品产出记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductProduceLineService.selectWmProductProduceLineByLineId(lineId));
    }

    /**
     * 新增产品产出记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:add')")
    @Log(title = "产品产出记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductProduceLine wmProductProduceLine)
    {
        return toAjax(wmProductProduceLineService.insertWmProductProduceLine(wmProductProduceLine));
    }

    /**
     * 修改产品产出记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:edit')")
    @Log(title = "产品产出记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductProduceLine wmProductProduceLine)
    {
        return toAjax(wmProductProduceLineService.updateWmProductProduceLine(wmProductProduceLine));
    }

    /**
     * 删除产品产出记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productproduce:remove')")
    @Log(title = "产品产出记录行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductProduceLineService.deleteWmProductProduceLineByLineIds(lineIds));
    }
}
