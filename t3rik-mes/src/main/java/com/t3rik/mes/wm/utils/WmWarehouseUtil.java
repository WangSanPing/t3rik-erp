package com.t3rik.mes.wm.utils;

import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class WmWarehouseUtil {

    @Resource
    private IWmWarehouseService wmWarehouseService;

    @Resource
    private IWmStorageLocationService wmStorageLocationService;

    @Resource
    private IWmStorageAreaService wmStorageAreaService;


    public <T> T setWarehouseInfo(T entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object warehouseId = entity.getClass().getDeclaredMethod("getWarehouseId", Long.class).invoke(entity,null);

        // if (StringUtils.isNotNull(wmItemRecpt.getWarehouseId())) {
        //     WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecpt.getWarehouseId());
        //     wmItemRecpt.setWarehouseCode(warehouse.getWarehouseCode());
        //     wmItemRecpt.setWarehouseName(warehouse.getWarehouseName());
        // }
        // if (StringUtils.isNotNull(wmItemRecpt.getLocationId())) {
        //     WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecpt.getLocationId());
        //     wmItemRecpt.setLocationCode(location.getLocationCode());
        //     wmItemRecpt.setLocationName(location.getLocationName());
        // }
        // if (StringUtils.isNotNull(wmItemRecpt.getAreaId())) {
        //     WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecpt.getAreaId());
        //     wmItemRecpt.setAreaCode(area.getAreaCode());
        //     wmItemRecpt.setAreaName(area.getAreaName());
        // }

        return entity;
    }

}
