package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmProductRecptLine;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmProductRecptLineService;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("产品入库明细")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/productrecptline")
public class WmProductRectpLineMobController extends BaseController {

    @Autowired
    private IWmProductRecptLineService wmProductRecptLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询产品入库记录行列表
     */
    @ApiOperation("查询产品入库单明细清单接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductRecptLine wmProductRecptLine)
    {
        startPage();
        List<WmProductRecptLine> list = wmProductRecptLineService.selectWmProductRecptLineList(wmProductRecptLine);
        return getDataTable(list);
    }


    /**
     * 获取产品入库记录行详细信息
     */
    @ApiOperation("获取产品入库明细详情")
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductRecptLineService.selectWmProductRecptLineByLineId(lineId));
    }

    /**
     * 新增产品入库记录行
     */
    @ApiOperation("新增产品入库明细记录")
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:add')")
    @Log(title = "产品入库记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductRecptLine wmProductRecptLine)
    {
        if(StringUtils.isNotNull(wmProductRecptLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductRecptLine.getWarehouseId());
            wmProductRecptLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductRecptLine.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmProductRecptLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductRecptLine.getLocationId());
            wmProductRecptLine.setLocationCode(location.getLocationCode());
            wmProductRecptLine.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmProductRecptLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductRecptLine.getAreaId());
            wmProductRecptLine.setAreaCode(area.getAreaCode());
            wmProductRecptLine.setAreaName(area.getAreaName());
        }
        wmProductRecptLine.setCreateBy(getUsername());
        wmProductRecptLineService.insertWmProductRecptLine(wmProductRecptLine);
        return AjaxResult.success(wmProductRecptLine);
    }

    /**
     * 修改产品入库记录行
     */
    @ApiOperation("编辑产品入库明细记录")
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:edit')")
    @Log(title = "产品入库记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductRecptLine wmProductRecptLine)
    {
        if(StringUtils.isNotNull(wmProductRecptLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductRecptLine.getWarehouseId());
            wmProductRecptLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductRecptLine.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmProductRecptLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductRecptLine.getLocationId());
            wmProductRecptLine.setLocationCode(location.getLocationCode());
            wmProductRecptLine.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmProductRecptLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductRecptLine.getAreaId());
            wmProductRecptLine.setAreaCode(area.getAreaCode());
            wmProductRecptLine.setAreaName(area.getAreaName());
        }
        return toAjax(wmProductRecptLineService.updateWmProductRecptLine(wmProductRecptLine));
    }

    /**
     * 删除产品入库记录行
     */
    @ApiOperation("删除产品入库明细记录")
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:remove')")
    @Log(title = "产品入库记录行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductRecptLineService.deleteWmProductRecptLineByLineIds(lineIds));
    }
}
