package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmPosition;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("仓库存储位置信息")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/warehouse")
public class WmWarehouseMobController extends BaseController {

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    /**
     * 查询库存盘点记录列表
     */
    @ApiOperation("查询仓库列表接口")
    @GetMapping("/list")
    public TableDataInfo list(WmWarehouse warehouse) {
        startPage();
        List<WmWarehouse> list = wmWarehouseService.selectWmWarehouseList(warehouse);
        return getDataTable(list);
    }

    /**
     * 查询树型的列表
     *
     * @return
     */
    @ApiOperation("查询树形结构的完整仓库信息接口")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList() {
        return AjaxResult.success(wmWarehouseService.getTreeList());
    }


    @ApiOperation("根据库位编码/ID查询完整的储位信息")
    @GetMapping("/getAreaFullInfo")
    public AjaxResult getAreaFullInfo(WmPosition position) {
        WmPosition thePosition = null;
        WmStorageArea area = null;

        if (StringUtils.isNotNull(position.getAreaId())) {
            area = wmStorageAreaService.selectWmStorageAreaByAreaId(area.getAreaId());
        }

        if (StringUtils.isNotNull(position.getAreaCode())) {
            area = wmStorageAreaService.selectWmStorageAreaByAreaCode(area.getAreaCode());
        }

        if (area != null) {
            thePosition.setAreaId(area.getAreaId());
            thePosition.setAreaCode(area.getAreaCode());
            thePosition.setAreaName(area.getAreaName());

            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(area.getLocationId());
            thePosition.setLocationCode(location.getLocationCode());
            thePosition.setLocationName(location.getLocationName());

            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(location.getWarehouseId());
            thePosition.setWarehouseId(location.getWarehouseId());
            thePosition.setWarehouseCode(warehouse.getWarehouseCode());
            thePosition.setWarehouseName(warehouse.getWarehouseName());
        }

        return AjaxResult.success(thePosition);
    }

}
