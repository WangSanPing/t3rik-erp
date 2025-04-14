package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmOutsourceRecptLine;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmOutsourceRecptLineService;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 外协入库单行Controller
 *
 * @author yinjinlu
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/mes/wm/oursourcerecptline")
public class WmOutsourceRecptLineController extends BaseController {
    @Autowired
    private IWmOutsourceRecptLineService wmOutsourceRecptLineService;


    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 查询外协入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceRecptLine wmOutsourceRecptLine) {
        startPage();
        List<WmOutsourceRecptLine> list = wmOutsourceRecptLineService.selectWmOutsourceRecptLineList(wmOutsourceRecptLine);
        return getDataTable(list);
    }

    /**
     * 导出外协入库单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:export')")
    @Log(title = "外协入库单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmOutsourceRecptLine wmOutsourceRecptLine) {
        List<WmOutsourceRecptLine> list = wmOutsourceRecptLineService.selectWmOutsourceRecptLineList(wmOutsourceRecptLine);
        ExcelUtil<WmOutsourceRecptLine> util = new ExcelUtil<WmOutsourceRecptLine>(WmOutsourceRecptLine.class);
        util.exportExcel(response, list, "外协入库单行数据");
    }

    /**
     * 获取外协入库单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId) {
        return AjaxResult.success(wmOutsourceRecptLineService.selectWmOutsourceRecptLineByLineId(lineId));
    }

    /**
     * 新增外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:add')")
    @Log(title = "外协入库单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceRecptLine wmOutsourceRecptLine) {
        setWarehouseInfo(wmOutsourceRecptLine);
        wmOutsourceRecptLine.setCreateBy(getUsername());
        return toAjax(wmOutsourceRecptLineService.insertWmOutsourceRecptLine(wmOutsourceRecptLine));
    }

    /**
     * 设置仓库信息
     *
     */
    private void setWarehouseInfo(WmOutsourceRecptLine wmOutsourceRecptLine) {
        if (StringUtils.isNotNull(wmOutsourceRecptLine.getWarehouseId())) {
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmOutsourceRecptLine.getWarehouseId());
            wmOutsourceRecptLine.setWarehouseCode(warehouse.getWarehouseCode());
            wmOutsourceRecptLine.setWarehouseName(warehouse.getWarehouseName());
        }
        if (StringUtils.isNotNull(wmOutsourceRecptLine.getLocationId())) {
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmOutsourceRecptLine.getLocationId());
            wmOutsourceRecptLine.setLocationCode(location.getLocationCode());
            wmOutsourceRecptLine.setLocationName(location.getLocationName());
        }
        if (StringUtils.isNotNull(wmOutsourceRecptLine.getAreaId())) {
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmOutsourceRecptLine.getAreaId());
            wmOutsourceRecptLine.setAreaCode(area.getAreaCode());
            wmOutsourceRecptLine.setAreaName(area.getAreaName());
        }
    }

    /**
     * 修改外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:edit')")
    @Log(title = "外协入库单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceRecptLine wmOutsourceRecptLine) {
        setWarehouseInfo(wmOutsourceRecptLine);

        return toAjax(wmOutsourceRecptLineService.updateWmOutsourceRecptLine(wmOutsourceRecptLine));
    }

    /**
     * 删除外协入库单行
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:oursourcerecpt:remove')")
    @Log(title = "外协入库单行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds) {
        return toAjax(wmOutsourceRecptLineService.deleteWmOutsourceRecptLineByLineIds(lineIds));
    }
}
