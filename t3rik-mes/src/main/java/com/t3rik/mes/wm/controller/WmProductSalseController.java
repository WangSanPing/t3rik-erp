package com.t3rik.mes.wm.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.*;
import com.t3rik.mes.wm.domain.tx.ProductSalseTxBean;
import com.t3rik.mes.wm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 销售出库单Controller
 * 
 * @author yinjinlu
 * @date 2022-10-04
 */
@RestController
@RequestMapping("/mes/wm/productsalse")
public class WmProductSalseController extends BaseController
{
    @Autowired
    private IWmProductSalseService wmProductSalseService;

    @Autowired
    private IWmProductSalseLineService wmProductSalseLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询销售出库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductSalse wmProductSalse)
    {
        startPage();
        List<WmProductSalse> list = wmProductSalseService.selectWmProductSalseList(wmProductSalse);
        return getDataTable(list);
    }

    /**
     * 导出销售出库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:export')")
    @Log(title = "销售出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductSalse wmProductSalse)
    {
        List<WmProductSalse> list = wmProductSalseService.selectWmProductSalseList(wmProductSalse);
        ExcelUtil<WmProductSalse> util = new ExcelUtil<WmProductSalse>(WmProductSalse.class);
        util.exportExcel(response, list, "销售出库单数据");
    }

    /**
     * 获取销售出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:query')")
    @GetMapping(value = "/{salseId}")
    public AjaxResult getInfo(@PathVariable("salseId") Long salseId)
    {
        return AjaxResult.success(wmProductSalseService.selectWmProductSalseBySalseId(salseId));
    }

    /**
     * 新增销售出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:add')")
    @Log(title = "销售出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductSalse wmProductSalse)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmProductSalseService.checkUnique(wmProductSalse))){
            return AjaxResult.error("出库单编号已存在！");
        }
        if(StringUtils.isNotNull(wmProductSalse.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductSalse.getWarehouseId());
            wmProductSalse.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductSalse.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmProductSalse.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductSalse.getLocationId());
            wmProductSalse.setLocationCode(location.getLocationCode());
            wmProductSalse.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmProductSalse.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductSalse.getAreaId());
            wmProductSalse.setAreaCode(area.getAreaCode());
            wmProductSalse.setAreaName(area.getAreaName());
        }
        wmProductSalse.setCreateBy(getUsername());
        return toAjax(wmProductSalseService.insertWmProductSalse(wmProductSalse));
    }

    /**
     * 修改销售出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:edit')")
    @Log(title = "销售出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductSalse wmProductSalse)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmProductSalseService.checkUnique(wmProductSalse))){
            return AjaxResult.error("出库单编号已存在！");
        }
        if(StringUtils.isNotNull(wmProductSalse.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmProductSalse.getWarehouseId());
            wmProductSalse.setWarehouseCode(warehouse.getWarehouseCode());
            wmProductSalse.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmProductSalse.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmProductSalse.getLocationId());
            wmProductSalse.setLocationCode(location.getLocationCode());
            wmProductSalse.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmProductSalse.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmProductSalse.getAreaId());
            wmProductSalse.setAreaCode(area.getAreaCode());
            wmProductSalse.setAreaName(area.getAreaName());
        }
        return toAjax(wmProductSalseService.updateWmProductSalse(wmProductSalse));
    }

    /**
     * 删除销售出库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:remove')")
    @Log(title = "销售出库单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{salseIds}")
    public AjaxResult remove(@PathVariable Long[] salseIds)
    {
        for (Long salseId: salseIds
             ) {
            wmProductSalseLineService.deleteBySalseId(salseId);
        }
        return toAjax(wmProductSalseService.deleteWmProductSalseBySalseIds(salseIds));
    }

    /**
     * 执行出库
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productsalse:edit')")
    @Log(title = "销售出库单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{salseId}")
    public AjaxResult execute(@PathVariable Long salseId){
        WmProductSalse salse = wmProductSalseService.selectWmProductSalseBySalseId(salseId);

        WmProductSalseLine param = new WmProductSalseLine();
        param.setSalseId(salseId);
        List<WmProductSalseLine> lines = wmProductSalseLineService.selectWmProductSalseLineList(param);
        if(CollectionUtil.isEmpty(lines)){
            return AjaxResult.error("出库物资不能为空");
        }

        List<ProductSalseTxBean> beans = wmProductSalseService.getTxBeans(salseId);
        storageCoreService.processProductSalse(beans);

        salse.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmProductSalseService.updateWmProductSalse(salse);
        return AjaxResult.success();
    }
}
