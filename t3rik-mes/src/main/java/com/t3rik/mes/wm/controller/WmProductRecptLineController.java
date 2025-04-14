package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmProductRecptLine;
import com.t3rik.mes.wm.service.IWmProductRecptLineService;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import com.t3rik.mes.wm.utils.WmWarehouseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品入库记录行Controller
 *
 * @author yinjinlu
 * @date 2022-09-22
 */
@RestController
@RequestMapping("/mes/wm/productrecptline")
public class WmProductRecptLineController extends BaseController {
    @Autowired
    private IWmProductRecptLineService wmProductRecptLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private WmWarehouseUtil warehouseUtil;

    /**
     * 查询产品入库记录行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductRecptLine wmProductRecptLine) {
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
    public void export(HttpServletResponse response, WmProductRecptLine wmProductRecptLine) {
        List<WmProductRecptLine> list = wmProductRecptLineService.selectWmProductRecptLineList(wmProductRecptLine);
        ExcelUtil<WmProductRecptLine> util = new ExcelUtil<WmProductRecptLine>(WmProductRecptLine.class);
        util.exportExcel(response, list, "产品入库记录行数据");
    }

    /**
     * 获取产品入库记录行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId) {
        return AjaxResult.success(wmProductRecptLineService.selectWmProductRecptLineByLineId(lineId));
    }

    /**
     * 新增产品入库记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:add')")
    @Log(title = "产品入库记录行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductRecptLine wmProductRecptLine) {
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmProductRecptLine);
        wmProductRecptLine.setCreateBy(getUsername());
        return toAjax(wmProductRecptLineService.insertWmProductRecptLine(wmProductRecptLine));
    }

    /**
     * 修改产品入库记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:edit')")
    @Log(title = "产品入库记录行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductRecptLine wmProductRecptLine) {
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmProductRecptLine);
        return toAjax(wmProductRecptLineService.updateWmProductRecptLine(wmProductRecptLine));
    }

    /**
     * 删除产品入库记录行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:remove')")
    @Log(title = "产品入库记录行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds) {
        return toAjax(wmProductRecptLineService.deleteWmProductRecptLineByLineIds(lineIds));
    }
}
