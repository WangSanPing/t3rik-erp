package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmStockTakingResult;
import com.t3rik.mes.wm.service.IWmStockTakingResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 库存盘点结果Controller
 * 
 * @author yinjinlu
 * @date 2023-08-22
 */
@RestController
@RequestMapping("/wm/stocktakingresult")
public class WmStockTakingResultController extends BaseController
{
    @Autowired
    private IWmStockTakingResultService wmStockTakingResultService;

    /**
     * 查询库存盘点结果列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTakingResult wmStockTakingResult)
    {
        startPage();
        List<WmStockTakingResult> list = wmStockTakingResultService.selectWmStockTakingResultList(wmStockTakingResult);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点结果列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:export')")
    @Log(title = "库存盘点结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmStockTakingResult wmStockTakingResult)
    {
        List<WmStockTakingResult> list = wmStockTakingResultService.selectWmStockTakingResultList(wmStockTakingResult);
        ExcelUtil<WmStockTakingResult> util = new ExcelUtil<WmStockTakingResult>(WmStockTakingResult.class);
        util.exportExcel(response, list, "库存盘点结果数据");
    }

    /**
     * 获取库存盘点结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:query')")
    @GetMapping(value = "/{resultId}")
    public AjaxResult getInfo(@PathVariable("resultId") Long resultId)
    {
        return AjaxResult.success(wmStockTakingResultService.selectWmStockTakingResultByResultId(resultId));
    }

    /**
     * 新增库存盘点结果
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:add')")
    @Log(title = "库存盘点结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTakingResult wmStockTakingResult)
    {
        return toAjax(wmStockTakingResultService.insertWmStockTakingResult(wmStockTakingResult));
    }

    /**
     * 修改库存盘点结果
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:edit')")
    @Log(title = "库存盘点结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTakingResult wmStockTakingResult)
    {
        return toAjax(wmStockTakingResultService.updateWmStockTakingResult(wmStockTakingResult));
    }

    /**
     * 删除库存盘点结果
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:remove')")
    @Log(title = "库存盘点结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resultIds}")
    public AjaxResult remove(@PathVariable Long[] resultIds)
    {
        return toAjax(wmStockTakingResultService.deleteWmStockTakingResultByResultIds(resultIds));
    }
}
