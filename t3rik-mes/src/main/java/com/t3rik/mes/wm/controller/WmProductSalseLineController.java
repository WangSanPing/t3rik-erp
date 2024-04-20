package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmProductSalseLine;
import com.t3rik.mes.wm.service.IWmProductSalseLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品销售出库行Controller
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
@RestController
@RequestMapping("/mes/wm/productsalseline")
public class WmProductSalseLineController extends BaseController
{
    @Autowired
    private IWmProductSalseLineService wmProductSalseLineService;

    /**
     * 查询产品销售出库行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSalseLine wmProductSalseLine)
    {
        startPage();
        List<WmProductSalseLine> list = wmProductSalseLineService.selectWmProductSalseLineList(wmProductSalseLine);
        return getDataTable(list);
    }

    /**
     * 导出产品销售出库行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:export')")
    @Log(title = "产品销售出库行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductSalseLine wmProductSalseLine)
    {
        List<WmProductSalseLine> list = wmProductSalseLineService.selectWmProductSalseLineList(wmProductSalseLine);
        ExcelUtil<WmProductSalseLine> util = new ExcelUtil<WmProductSalseLine>(WmProductSalseLine.class);
        util.exportExcel(response, list, "产品销售出库行数据");
    }

    /**
     * 获取产品销售出库行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductSalseLineService.selectWmProductSalseLineByLineId(lineId));
    }

    /**
     * 新增产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:add')")
    @Log(title = "产品销售出库行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSalseLine wmProductSalseLine)
    {
        wmProductSalseLine.setCreateBy(getUsername());
        return toAjax(wmProductSalseLineService.insertWmProductSalseLine(wmProductSalseLine));
    }

    /**
     * 修改产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:edit')")
    @Log(title = "产品销售出库行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSalseLine wmProductSalseLine)
    {
        return toAjax(wmProductSalseLineService.updateWmProductSalseLine(wmProductSalseLine));
    }

    /**
     * 删除产品销售出库行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:remove')")
    @Log(title = "产品销售出库行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductSalseLineService.deleteWmProductSalseLineByLineIds(lineIds));
    }
}
