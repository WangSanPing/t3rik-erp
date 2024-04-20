package com.t3rik.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.wm.domain.WmProductRecptLine;
import com.t3rik.mes.wm.service.IWmProductRecptLineService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 产品入库记录行Controller
 * 
 * @author yinjinlu
 * @date 2022-09-22
 */
@RestController
@RequestMapping("/mes/wm/productrecptline")
public class WmProductRecptLineController extends BaseController
{
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
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductRecptLine wmProductRecptLine)
    {
        startPage();
        List<WmProductRecptLine> list = wmProductRecptLineService.selectWmProductRecptLineList(wmProductRecptLine);
        return getDataTable(list);
    }

    /**
     * 导出产品入库记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:export')")
    @Log(title = "产品入库记录行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductRecptLine wmProductRecptLine)
    {
        List<WmProductRecptLine> list = wmProductRecptLineService.selectWmProductRecptLineList(wmProductRecptLine);
        ExcelUtil<WmProductRecptLine> util = new ExcelUtil<WmProductRecptLine>(WmProductRecptLine.class);
        util.exportExcel(response, list, "产品入库记录行数据");
    }

    /**
     * 获取产品入库记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmProductRecptLineService.selectWmProductRecptLineByLineId(lineId));
    }

    /**
     * 新增产品入库记录行
     */
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
        return toAjax(wmProductRecptLineService.insertWmProductRecptLine(wmProductRecptLine));
    }

    /**
     * 修改产品入库记录行
     */
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
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:remove')")
    @Log(title = "产品入库记录行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmProductRecptLineService.deleteWmProductRecptLineByLineIds(lineIds));
    }
}
