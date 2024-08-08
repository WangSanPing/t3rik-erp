package com.t3rik.mes.wm.utils;

import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.helper.LambdaExHelper;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@Component
public class WmWarehouseUtil {

    @Resource
    private IWmWarehouseService wmWarehouseService;

    @Resource
    private IWmStorageLocationService wmStorageLocationService;

    @Resource
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 设置仓库、库区、库位信息
     */
    public <T extends BaseEntity> void setWarehouseInfo(T entity) {
        try {
            // 处理仓库
            Long warehouseId = (Long) this.getNoParamMethod("getWarehouseId", entity).invoke(entity);
            Optional.ofNullable(warehouseId).ifPresent(
                    LambdaExHelper.wrap(id -> {
                        WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(id);
                        this.getMethod("setWarehouseCode", entity, String.class).invoke(entity, warehouse.getWarehouseCode());
                        this.getMethod("setWarehouseName", entity, String.class).invoke(entity, warehouse.getWarehouseName());
                    })
            );
            // 处理库区
            Long locationId = (Long) this.getNoParamMethod("getLocationId", entity).invoke(entity);
            Optional.ofNullable(locationId).ifPresent(
                    LambdaExHelper.wrap(id -> {
                        WmStorageLocation storageLocation = wmStorageLocationService.selectWmStorageLocationByLocationId(id);
                        this.getMethod("setLocationCode", entity, String.class).invoke(entity, storageLocation.getLocationCode());
                        this.getMethod("setLocationName", entity, String.class).invoke(entity, storageLocation.getLocationName());
                    })
            );
            // 处理库位
            Long areaId = (Long) this.getNoParamMethod("getAreaId", entity).invoke(entity);
            Optional.ofNullable(areaId).ifPresent(
                    LambdaExHelper.wrap(id -> {
                        WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(id);
                        this.getMethod("setAreaCode", entity, String.class).invoke(entity, area.getAreaCode());
                        this.getMethod("setAreaName", entity, String.class).invoke(entity, area.getAreaName());
                    })
            );
        } catch (Exception e) {
            log.error("赋值仓库信息失败，失败异常: {}", e.getMessage());
        }
    }

    private <T> Method getNoParamMethod(String methodName, T entity) throws NoSuchMethodException {
        return this.getMethod(methodName, entity, null);
    }


    private <T> Method getMethod(String methodName, T entity, Class<?> clz) throws NoSuchMethodException {
        if (clz == null) {
            return entity.getClass().getDeclaredMethod(methodName);
        } else {
            return entity.getClass().getDeclaredMethod(methodName, clz);
        }
    }
}
