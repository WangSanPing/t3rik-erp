package com.t3rik.mes.wm.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import com.t3rik.mes.wm.utils.WmBarCodeUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 仓库设置Controller
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@RestController
@RequestMapping("/mes/wm/warehouse")
public class WmWarehouseController extends BaseController {
    @Resource
    private IWmWarehouseService wmWarehouseService;

    @Resource
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询仓库设置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WmWarehouse wmWarehouse) {
        startPage();
        List<WmWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmWarehouse);
        return getDataTable(list);
    }

    /**
     * 查询树型的列表
     *
     * @return
     */
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList() {
        return AjaxResult.success(wmWarehouseService.getTreeList());
    }

    /**
     * 导出仓库设置列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:warehouse:export')")
    @Log(title = "仓库设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmWarehouse wmWarehouse) {
        List<WmWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmWarehouse);
        ExcelUtil<WmWarehouse> util = new ExcelUtil<WmWarehouse>(WmWarehouse.class);
        util.exportExcel(response, list, "仓库设置数据");
    }

    /**
     * 获取仓库设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:warehouse:query')")
    @GetMapping(value = "/{warehouseId}")
    public AjaxResult getInfo(@PathVariable("warehouseId") Long warehouseId) {
        return AjaxResult.success(wmWarehouseService.selectWmWarehouseByWarehouseId(warehouseId));
    }

    /**
     * 新增仓库设置
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:warehouse:add')")
    @Log(title = "仓库设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmWarehouse wmWarehouse) {
        if (UserConstants.NOT_UNIQUE.equals(wmWarehouseService.checkWarehouseCodeUnique(wmWarehouse))) {
            return AjaxResult.error("仓库编码已存在！");
        }
        if (UserConstants.NOT_UNIQUE.equals(wmWarehouseService.checkWarehouseNameUnique(wmWarehouse))) {
            return AjaxResult.error("仓库名称已存在！");
        }

        wmWarehouseService.insertWmWarehouse(wmWarehouse);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_WAREHOUSE, wmWarehouse.getWarehouseId(), wmWarehouse.getWarehouseCode(), wmWarehouse.getWarehouseName());

        return AjaxResult.success(wmWarehouse.getWarehouseId());
    }

    /**
     * 修改仓库设置
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:warehouse:edit')")
    @Log(title = "仓库设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmWarehouse wmWarehouse) {
        return toAjax(wmWarehouseService.updateWmWarehouse(wmWarehouse));
    }

    /**
     * 删除仓库设置
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:warehouse:remove')")
    @Log(title = "仓库设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{warehouseIds}")
    public AjaxResult remove(@PathVariable Long[] warehouseIds) {
        if (warehouseIds == null || warehouseIds.length == 0) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR);
        }
        List<WmWarehouse> warehouseList = this.wmWarehouseService.listByIds(Arrays.asList(warehouseIds));
        if (CollectionUtils.isEmpty(warehouseList)) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR);
        }
        // 校验是否为预置数据，如果是预置数据，不允许删除
        List<String> warehouseCodes = warehouseList.stream().map(WmWarehouse::getWarehouseCode).toList();
        warehouseCodes.forEach(f -> {
            DefaultDataEnum defaultDataEnum = DefaultDataEnum.getEnumByCode(f);
            Assert.isNull(defaultDataEnum, () -> new BusinessException(MsgConstants.CAN_NOT_BE_DELETE));
        });
        return toAjax(wmWarehouseService.deleteWmWarehouseByWarehouseIds(warehouseIds));
    }
}
