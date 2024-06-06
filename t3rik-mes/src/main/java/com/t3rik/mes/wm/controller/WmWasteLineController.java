package com.t3rik.mes.wm.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.wm.domain.WmWasteLine;
import com.t3rik.mes.wm.service.IWmWasteLineService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 生产废料单行Controller
 *
 * @author t3rik
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/mes/wm-waste-line")
public class WmWasteLineController extends BaseController {
    @Autowired
    private IWmWasteLineService wmWasteLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;
    /**
     * 查询生产废料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmWasteLine wmWasteLine) {
        // 获取查询条件
        LambdaQueryWrapper<WmWasteLine> queryWrapper = getQueryWrapper(wmWasteLine);
        // 组装分页
        Page<WmWasteLine> page = getMPPage(wmWasteLine);
        // 查询
        this.wmWasteLineService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出生产废料单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:export')")
    @Log(title = "生产废料单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmWasteLine wmWasteLine) {
        // 获取查询条件
        LambdaQueryWrapper<WmWasteLine> queryWrapper = getQueryWrapper(wmWasteLine);
        List<WmWasteLine> list = this.wmWasteLineService.list(queryWrapper);
        ExcelUtil<WmWasteLine> util = new ExcelUtil<WmWasteLine>(WmWasteLine. class);
        util.exportExcel(response, list, "生产废料单行数据");
    }

    /**
     * 获取生产废料单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId) {
        return AjaxResult.success(this.wmWasteLineService.getById(lineId));
    }

    /**
     * 新增生产废料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:add')")
    @Log(title = "生产废料单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmWasteLine wmWasteLine) {
        if(StringUtils.isNull(wmWasteLine.getMaterialStockId())){
            return AjaxResult.error("请从库存现有量中选择退料的物资！");
        }

        if(StringUtils.isNotNull(wmWasteLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmWasteLine.getWarehouseId());
            wmWasteLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmWasteLine.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmWasteLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmWasteLine.getLocationId());
            wmWasteLine.setLocationCode(location.getLocationCode());
            wmWasteLine.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmWasteLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmWasteLine.getAreaId());
            wmWasteLine.setAreaCode(area.getAreaCode());
            wmWasteLine.setAreaName(area.getAreaName());
        }
        return toAjax(this.wmWasteLineService.save(wmWasteLine));
    }

    /**
     * 修改生产废料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:edit')")
    @Log(title = "生产废料单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmWasteLine wmWasteLine) {
        if(StringUtils.isNotNull(wmWasteLine.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmWasteLine.getWarehouseId());
            wmWasteLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmWasteLine.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmWasteLine.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmWasteLine.getLocationId());
            wmWasteLine.setLocationCode(location.getLocationCode());
            wmWasteLine.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmWasteLine.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmWasteLine.getAreaId());
            wmWasteLine.setAreaCode(area.getAreaCode());
            wmWasteLine.setAreaName(area.getAreaName());
        }
        return toAjax(this.wmWasteLineService.updateById(wmWasteLine));
    }

    /**
     * 删除生产废料单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:remove')")
    @Log(title = "生产废料单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable List<Long> lineIds) {
        return toAjax(this.wmWasteLineService.removeBatchByIds(lineIds));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<WmWasteLine> getQueryWrapper(WmWasteLine wmWasteLine) {
        LambdaQueryWrapper<WmWasteLine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(wmWasteLine.getWasteId() != null, WmWasteLine::getWasteId, wmWasteLine.getWasteId());
        queryWrapper.eq(wmWasteLine.getMaterialStockId() != null, WmWasteLine::getMaterialStockId, wmWasteLine.getMaterialStockId());
        queryWrapper.eq(wmWasteLine.getItemId() != null, WmWasteLine::getItemId, wmWasteLine.getItemId());
        queryWrapper.eq(wmWasteLine.getItemCode() != null, WmWasteLine::getItemCode, wmWasteLine.getItemCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteLine.getItemName()), WmWasteLine::getItemName, wmWasteLine.getItemName());
        queryWrapper.eq(wmWasteLine.getSpecification() != null, WmWasteLine::getSpecification, wmWasteLine.getSpecification());
        queryWrapper.eq(wmWasteLine.getUnitOfMeasure() != null, WmWasteLine::getUnitOfMeasure, wmWasteLine.getUnitOfMeasure());
        queryWrapper.eq(wmWasteLine.getQuantityWaste() != null, WmWasteLine::getQuantityWaste, wmWasteLine.getQuantityWaste());
        queryWrapper.eq(wmWasteLine.getBatchCode() != null, WmWasteLine::getBatchCode, wmWasteLine.getBatchCode());
        queryWrapper.eq(wmWasteLine.getWarehouseId() != null, WmWasteLine::getWarehouseId, wmWasteLine.getWarehouseId());
        queryWrapper.eq(wmWasteLine.getWarehouseCode() != null, WmWasteLine::getWarehouseCode, wmWasteLine.getWarehouseCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteLine.getWarehouseName()), WmWasteLine::getWarehouseName, wmWasteLine.getWarehouseName());
        queryWrapper.eq(wmWasteLine.getLocationId() != null, WmWasteLine::getLocationId, wmWasteLine.getLocationId());
        queryWrapper.eq(wmWasteLine.getLocationCode() != null, WmWasteLine::getLocationCode, wmWasteLine.getLocationCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteLine.getLocationName()), WmWasteLine::getLocationName, wmWasteLine.getLocationName());
        queryWrapper.eq(wmWasteLine.getAreaId() != null, WmWasteLine::getAreaId, wmWasteLine.getAreaId());
        queryWrapper.eq(wmWasteLine.getAreaCode() != null, WmWasteLine::getAreaCode, wmWasteLine.getAreaCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteLine.getAreaName()), WmWasteLine::getAreaName, wmWasteLine.getAreaName());
        queryWrapper.eq(wmWasteLine.getAttr1() != null, WmWasteLine::getAttr1, wmWasteLine.getAttr1());
        queryWrapper.eq(wmWasteLine.getAttr2() != null, WmWasteLine::getAttr2, wmWasteLine.getAttr2());
        queryWrapper.eq(wmWasteLine.getAttr3() != null, WmWasteLine::getAttr3, wmWasteLine.getAttr3());
        queryWrapper.eq(wmWasteLine.getAttr4() != null, WmWasteLine::getAttr4, wmWasteLine.getAttr4());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(WmWasteLine::getCreateTime);
        Map<String, Object> params = wmWasteLine.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,WmWasteLine::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
