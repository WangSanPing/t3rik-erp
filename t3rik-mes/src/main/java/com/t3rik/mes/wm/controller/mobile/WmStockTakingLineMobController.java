package com.t3rik.mes.wm.controller.mobile;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.wm.domain.WmStockTakingLine;
import com.t3rik.mes.wm.service.IWmStockTakingLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存盘点明细Controller
 *
 * @author yinjinlu
 * @date 2023-08-17
 */
@Api("物资盘点明细")
@RestController
@RequestMapping("/mobile/wm/stocktakingline")
public class WmStockTakingLineMobController extends BaseController
{
    @Autowired
    private IWmStockTakingLineService wmStockTakingLineService;

    /**
     * 查询库存盘点明细列表
     */
    @ApiOperation("查询库存盘点明细列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTakingLine wmStockTakingLine)
    {
        startPage();
        List<WmStockTakingLine> list = wmStockTakingLineService.selectWmStockTakingLineList(wmStockTakingLine);
        return getDataTable(list);
    }


    /**
     * 获取库存盘点明细详细信息
     */
    @ApiOperation("查询库存盘点明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmStockTakingLineService.selectWmStockTakingLineByLineId(lineId));
    }

    /**
     * 新增库存盘点明细
     */
    @ApiOperation("新增库存盘点明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:add')")
    @Log(title = "库存盘点明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTakingLine wmStockTakingLine)
    {
        return toAjax(wmStockTakingLineService.insertWmStockTakingLine(wmStockTakingLine));
    }

    /**
     * 修改库存盘点明细
     */
    @ApiOperation("编辑库存盘点明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingline:edit')")
    @Log(title = "库存盘点明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTakingLine wmStockTakingLine)
    {
        return toAjax(wmStockTakingLineService.updateWmStockTakingLine(wmStockTakingLine));
    }

    /**
     * 删除库存盘点明细
     */
    @ApiOperation("删除库存盘点明细接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktakingline:remove')")
    @Log(title = "库存盘点明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmStockTakingLineService.deleteWmStockTakingLineByLineIds(lineIds));
    }
}
