package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmIssueLine;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.service.IWmIssueLineService;
import com.t3rik.mes.wm.service.IWmMaterialStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("生产领料明细")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/issueline")
public class WmIssueLineMobController extends BaseController {

    @Autowired
    private IWmIssueLineService wmIssueLineService;

    @Autowired
    private IWmMaterialStockService wmMaterialStockService;

    /**
     * 查询生产领料单行列表
     */
    @ApiOperation("查询生产领导单行信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueLine wmIssueLine) {
        startPage();
        List<WmIssueLine> list = wmIssueLineService.selectWmIssueLineList(wmIssueLine);
        return getDataTable(list);
    }

    /**
     * 获取生产领料单行详细信息
     */
    @ApiOperation("获取生产领料单行明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId) {
        return AjaxResult.success(wmIssueLineService.selectWmIssueLineByLineId(lineId));
    }

    /**
     * 新增生产领料单行
     */
    @ApiOperation("新增生产领料单行信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueLine wmIssueLine) {
        if (StringUtils.isNotNull(wmIssueLine.getMaterialStockId())) {
            WmMaterialStock stock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmIssueLine.getMaterialStockId());
            wmIssueLine.setItemId(stock.getItemId());
            wmIssueLine.setItemCode(stock.getItemCode());
            wmIssueLine.setItemName(stock.getItemName());
            wmIssueLine.setUnitOfMeasure(stock.getUnitOfMeasure());
            wmIssueLine.setSpecification(stock.getSpecification());
            wmIssueLine.setBatchCode(stock.getBatchCode());
            wmIssueLine.setWarehouseId(stock.getWarehouseId());
            wmIssueLine.setWarehouseCode(stock.getWarehouseCode());
            wmIssueLine.setWarehouseName(stock.getWarehouseName());
            wmIssueLine.setLocationId(stock.getLocationId());
            wmIssueLine.setLocationCode(stock.getLocationCode());
            wmIssueLine.setLocationName(stock.getLocationName());
            wmIssueLine.setAreaId(stock.getAreaId());
            wmIssueLine.setAreaCode(stock.getAreaCode());
            wmIssueLine.setAreaName(stock.getAreaName());
        }

        wmIssueLine.setCreateBy(getUsername());
        wmIssueLineService.insertWmIssueLine(wmIssueLine);
        return AjaxResult.success(wmIssueLine);
    }

    /**
     * 修改生产领料单行
     */
    @ApiOperation("更新生产领料单行信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueLine wmIssueLine) {
        if (StringUtils.isNotNull(wmIssueLine.getMaterialStockId())) {
            WmMaterialStock stock = wmMaterialStockService.selectWmMaterialStockByMaterialStockId(wmIssueLine.getMaterialStockId());
            wmIssueLine.setItemId(stock.getItemId());
            wmIssueLine.setItemCode(stock.getItemCode());
            wmIssueLine.setItemName(stock.getItemName());
            wmIssueLine.setUnitOfMeasure(stock.getUnitOfMeasure());
            wmIssueLine.setSpecification(stock.getSpecification());
            wmIssueLine.setBatchCode(stock.getBatchCode());
            wmIssueLine.setWarehouseId(stock.getWarehouseId());
            wmIssueLine.setWarehouseCode(stock.getWarehouseCode());
            wmIssueLine.setWarehouseName(stock.getWarehouseName());
            wmIssueLine.setLocationId(stock.getLocationId());
            wmIssueLine.setLocationCode(stock.getLocationCode());
            wmIssueLine.setLocationName(stock.getLocationName());
            wmIssueLine.setAreaId(stock.getAreaId());
            wmIssueLine.setAreaCode(stock.getAreaCode());
            wmIssueLine.setAreaName(stock.getAreaName());
        }

        return toAjax(wmIssueLineService.updateWmIssueLine(wmIssueLine));
    }

    /**
     * 删除生产领料单行
     */
    @ApiOperation("删除生产领料单行信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds) {
        return toAjax(wmIssueLineService.deleteWmIssueLineByLineIds(lineIds));
    }

}
