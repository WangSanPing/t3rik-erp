package com.t3rik.mes.wm.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.t3rik.mes.wm.domain.WmWasteHeader;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 生产废料单头Controller
 *
 * @author t3rik
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/mes/wm-waste-header")
public class WmWasteHeaderController extends BaseController {
    @Autowired
    private IWmWasteHeaderService wmWasteHeaderService;

    @Autowired
    private IWmWasteLineService wmWasteLineService;

	@Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询生产废料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmWasteHeader wmWasteHeader) {
        // 获取查询条件
        LambdaQueryWrapper<WmWasteHeader> queryWrapper = getQueryWrapper(wmWasteHeader);
        // 组装分页
        Page<WmWasteHeader> page = getMPPage(wmWasteHeader);
        // 查询
        this.wmWasteHeaderService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出生产废料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:export')")
    @Log(title = "生产废料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmWasteHeader wmWasteHeader) {
        // 获取查询条件
        LambdaQueryWrapper<WmWasteHeader> queryWrapper = getQueryWrapper(wmWasteHeader);
        List<WmWasteHeader> list = this.wmWasteHeaderService.list(queryWrapper);
        ExcelUtil<WmWasteHeader> util = new ExcelUtil<WmWasteHeader>(WmWasteHeader. class);
        util.exportExcel(response, list, "生产废料单头数据");
    }

    /**
     * 获取生产废料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:query')")
    @GetMapping(value = "/{wasteId}")
    public AjaxResult getInfo(@PathVariable("wasteId") Long wasteId) {
        return AjaxResult.success(this.wmWasteHeaderService.getById(wasteId));
    }

    /**
     * 新增生产废料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:add')")
    @Log(title = "生产废料单头", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody WmWasteHeader wmWasteHeader) {
        //查询退料单号是否已存在
        if(UserConstants.NOT_UNIQUE.equals(wmWasteHeaderService.checkUnique(wmWasteHeader))){
            return AjaxResult.error("退料单编号已存在");
        }

        if(StringUtils.isNotNull(wmWasteHeader.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmWasteHeader.getWarehouseId());
            wmWasteHeader.setWarehouseCode(warehouse.getWarehouseCode());
            wmWasteHeader.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmWasteHeader.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmWasteHeader.getLocationId());
            wmWasteHeader.setLocationCode(location.getLocationCode());
            wmWasteHeader.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmWasteHeader.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmWasteHeader.getAreaId());
            wmWasteHeader.setAreaCode(area.getAreaCode());
            wmWasteHeader.setAreaName(area.getAreaName());
        }
        return toAjax(this.wmWasteHeaderService.save(wmWasteHeader));
    }

    /**
     * 修改生产废料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:edit')")
    @Log(title = "生产废料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmWasteHeader wmWasteHeader) {
        //查询退料单号是否已存在
        if(UserConstants.NOT_UNIQUE.equals(wmWasteHeaderService.checkUnique(wmWasteHeader))){
            return AjaxResult.error("退料单编号已存在");
        }

        if(StringUtils.isNotNull(wmWasteHeader.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmWasteHeader.getWarehouseId());
            wmWasteHeader.setWarehouseCode(warehouse.getWarehouseCode());
            wmWasteHeader.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmWasteHeader.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmWasteHeader.getLocationId());
            wmWasteHeader.setLocationCode(location.getLocationCode());
            wmWasteHeader.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmWasteHeader.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmWasteHeader.getAreaId());
            wmWasteHeader.setAreaCode(area.getAreaCode());
            wmWasteHeader.setAreaName(area.getAreaName());
        }
        //修改人、时间
//        wmWasteHeader.setUpdateBy(getUsername());
//        wmWasteHeader.setUpdateById(getUserId());
        wmWasteHeader.setUpdateTime(DateUtils.getNowDate());
        return toAjax(this.wmWasteHeaderService.updateById(wmWasteHeader));
    }

    /**
     * 删除生产废料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wmwasteheader:remove')")
    @Log(title = "生产废料单头", businessType = BusinessType.DELETE)
    @DeleteMapping("/{wasteIds}")
    public AjaxResult remove(@PathVariable List<Long> wasteIds) {
        //删除行信息
        wmWasteLineService.delWmWasteLineIds(wasteIds);
        //删除头数据
        return toAjax(wmWasteHeaderService.delWmWasteHeaderIds(wasteIds));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<WmWasteHeader> getQueryWrapper(WmWasteHeader wmWasteHeader) {
        LambdaQueryWrapper<WmWasteHeader> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(wmWasteHeader.getWasteCode() != null, WmWasteHeader::getWasteCode, wmWasteHeader.getWasteCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteHeader.getWasteName()), WmWasteHeader::getWasteName, wmWasteHeader.getWasteName());
        queryWrapper.eq(wmWasteHeader.getWorkorderId() != null, WmWasteHeader::getWorkorderId, wmWasteHeader.getWorkorderId());
        queryWrapper.eq(wmWasteHeader.getWorkorderCode() != null, WmWasteHeader::getWorkorderCode, wmWasteHeader.getWorkorderCode());
        queryWrapper.eq(wmWasteHeader.getWarehouseId() != null, WmWasteHeader::getWarehouseId, wmWasteHeader.getWarehouseId());
        queryWrapper.eq(wmWasteHeader.getWarehouseCode() != null, WmWasteHeader::getWarehouseCode, wmWasteHeader.getWarehouseCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteHeader.getWarehouseName()), WmWasteHeader::getWarehouseName, wmWasteHeader.getWarehouseName());
        queryWrapper.eq(wmWasteHeader.getLocationId() != null, WmWasteHeader::getLocationId, wmWasteHeader.getLocationId());
        queryWrapper.eq(wmWasteHeader.getLocationCode() != null, WmWasteHeader::getLocationCode, wmWasteHeader.getLocationCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteHeader.getLocationName()), WmWasteHeader::getLocationName, wmWasteHeader.getLocationName());
        queryWrapper.eq(wmWasteHeader.getAreaId() != null, WmWasteHeader::getAreaId, wmWasteHeader.getAreaId());
        queryWrapper.eq(wmWasteHeader.getAreaCode() != null, WmWasteHeader::getAreaCode, wmWasteHeader.getAreaCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmWasteHeader.getAreaName()), WmWasteHeader::getAreaName, wmWasteHeader.getAreaName());
        queryWrapper.eq(wmWasteHeader.getWasteDate() != null, WmWasteHeader::getWasteDate, wmWasteHeader.getWasteDate());
        Optional.ofNullable(wmWasteHeader.getStatus()).ifPresent(status -> queryWrapper.eq( WmWasteHeader::getStatus, wmWasteHeader.getStatus()));
        queryWrapper.eq(wmWasteHeader.getAttr1() != null, WmWasteHeader::getAttr1, wmWasteHeader.getAttr1());
        queryWrapper.eq(wmWasteHeader.getAttr2() != null, WmWasteHeader::getAttr2, wmWasteHeader.getAttr2());
        queryWrapper.eq(wmWasteHeader.getAttr3() != null, WmWasteHeader::getAttr3, wmWasteHeader.getAttr3());
        queryWrapper.eq(wmWasteHeader.getAttr4() != null, WmWasteHeader::getAttr4, wmWasteHeader.getAttr4());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(WmWasteHeader::getCreateTime);
        Map<String, Object> params = wmWasteHeader.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,WmWasteHeader::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
