package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.enums.YesOrNoEnum;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmStorageArea;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.mapper.WmWarehouseMapper;
import com.t3rik.mes.wm.service.IWmStorageAreaService;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 仓库设置Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@Service
public class WmWarehouseServiceImpl extends ServiceImpl<WmWarehouseMapper, WmWarehouse> implements IWmWarehouseService {
    @Resource
    private WmWarehouseMapper wmWarehouseMapper;

    @Resource
    private IWmStorageLocationService storageLocationService;

    @Resource
    private IWmStorageAreaService storageAreaService;

    @Resource
    private IWmStorageLocationService wmStorageLocationService;

    @Resource
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 项目启动时初始化数据
     */
    @PostConstruct
    void initData() {
        this.initVirtualWarehouse();
    }

    /**
     * 查询仓库设置
     *
     * @param warehouseId 仓库设置主键
     * @return 仓库设置
     */
    @Override
    public WmWarehouse selectWmWarehouseByWarehouseId(Long warehouseId) {
        return wmWarehouseMapper.selectWmWarehouseByWarehouseId(warehouseId);
    }

    @Override
    public WmWarehouse selectWmWarehouseByWarehouseCode(String warehouseCdoe) {
        return wmWarehouseMapper.selectWmWarehouseByWarehouseCode(warehouseCdoe);
    }

    /**
     * 查询仓库设置列表
     *
     * @param wmWarehouse 仓库设置
     * @return 仓库设置
     */
    @Override
    public List<WmWarehouse> selectWmWarehouseList(WmWarehouse wmWarehouse) {
        return wmWarehouseMapper.selectWmWarehouseList(wmWarehouse);
    }

    @Override
    public List<WmWarehouse> getTreeList() {
        return wmWarehouseMapper.getTreeList();
    }

    @Override
    public String checkWarehouseCodeUnique(WmWarehouse wmWarehouse) {
        WmWarehouse warehouse = wmWarehouseMapper.checkWarehouseCodeUnique(wmWarehouse);
        Long warehouseId = wmWarehouse.getWarehouseId() == null ? -1L : wmWarehouse.getWarehouseId();
        if (StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId().longValue() != warehouseId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkWarehouseNameUnique(WmWarehouse wmWarehouse) {
        WmWarehouse warehouse = wmWarehouseMapper.checkWarehouseNameUnique(wmWarehouse);
        Long warehouseId = wmWarehouse.getWarehouseId() == null ? -1L : wmWarehouse.getWarehouseId();
        if (StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId().longValue() != warehouseId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增仓库设置
     *
     * @param wmWarehouse 仓库设置
     * @return 结果
     */
    @Override
    public int insertWmWarehouse(WmWarehouse wmWarehouse) {
        wmWarehouse.setCreateTime(DateUtils.getNowDate());
        return wmWarehouseMapper.insertWmWarehouse(wmWarehouse);
    }

    /**
     * 修改仓库设置
     *
     * @param wmWarehouse 仓库设置
     * @return 结果
     */
    @Override
    public int updateWmWarehouse(WmWarehouse wmWarehouse) {
        wmWarehouse.setUpdateTime(DateUtils.getNowDate());
        return wmWarehouseMapper.updateWmWarehouse(wmWarehouse);
    }

    /**
     * 批量删除仓库设置
     *
     * @param warehouseIds 需要删除的仓库设置主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmWarehouseByWarehouseIds(Long[] warehouseIds) {
        // 删除库位
        List<Long> locationIds = this.wmStorageLocationService
                .lambdaQuery()
                .in(WmStorageLocation::getWarehouseId, Arrays.asList(warehouseIds))
                .list()
                .stream().map(WmStorageLocation::getLocationId)
                .toList();
        this.wmStorageAreaService.remove(
                new LambdaQueryWrapper<WmStorageArea>()
                        .in(WmStorageArea::getLocationId, locationIds));

        // 删除库区
        this.wmStorageLocationService.remove(
                new LambdaQueryWrapper<WmStorageLocation>()
                        .in(WmStorageLocation::getWarehouseId, Arrays.asList(warehouseIds)));

        return wmWarehouseMapper.deleteWmWarehouseByWarehouseIds(warehouseIds);
    }

    /**
     * 删除仓库设置信息
     *
     * @param warehouseId 仓库设置主键
     * @return 结果
     */
    @Override
    public int deleteWmWarehouseByWarehouseId(Long warehouseId) {
        return wmWarehouseMapper.deleteWmWarehouseByWarehouseId(warehouseId);
    }

    @Override
    public WmWarehouse initVirtualWarehouse() {

        // 初始化虚拟线边库
        WmWarehouse warehouse = initWarehouse(DefaultDataEnum.VIRTUAL_WH);
        // 初始化  [废料]  虚拟线边库
        WmWarehouse wasteWarehouse = initWarehouse(DefaultDataEnum.WASTE_VIRTUAL_WH);
        // 初始化虚拟线边库库区
        WmStorageLocation location = initStorageLocation(warehouse, DefaultDataEnum.VIRTUAL_WS);
        // 初始化  [废料]  虚拟线边库库区
        WmStorageLocation wasteLocation = initStorageLocation(wasteWarehouse, DefaultDataEnum.WASTE_VIRTUAL_WS);
        // 初始化虚拟线边库库位
        initStorageArea(location, DefaultDataEnum.VIRTUAL_WA);
        // 初始化  [废料]  虚拟线边库库位
        initStorageArea(wasteLocation, DefaultDataEnum.WASTE_VIRTUAL_WA);
        // 初始化  预置  仓库
        initWarehouse(DefaultDataEnum.WH00_DEFAULT);


        return warehouse;
    }

    /**
     * 初始化库位
     */
    private void initStorageArea(WmStorageLocation location, DefaultDataEnum dataEnum) {
        WmStorageArea area = this.storageAreaService.lambdaQuery().eq(WmStorageArea::getAreaCode, dataEnum.getCode()).one();
        if (StringUtils.isNull(area)) {
            area = new WmStorageArea();
            area.setLocationId(location.getLocationId());
            area.setAreaCode(dataEnum.getCode());
            area.setAreaName(dataEnum.getDesc());
            area.setCreateTime(new Date());
            area.setCreateBy(UserConstants.SYS_USER);
            area.setCreateUserId(UserConstants.SYS_USER_ID);
            this.storageAreaService.insertWmStorageArea(area);
        }
    }

    /**
     * 初始化库区
     */
    private WmStorageLocation initStorageLocation(WmWarehouse warehouse, DefaultDataEnum dataEnum) {
        WmStorageLocation location = this.storageLocationService.lambdaQuery().eq(WmStorageLocation::getLocationCode, dataEnum.getCode()).one();
        if (StringUtils.isNull(location)) {
            location = new WmStorageLocation();
            location.setWarehouseId(warehouse.getWarehouseId());
            location.setLocationCode(dataEnum.getCode());
            location.setLocationName(dataEnum.getDesc());
            location.setAreaFlag(YesOrNoEnum.YES.getCode());
            location.setCreateTime(new Date());
            location.setCreateBy(UserConstants.SYS_USER);
            location.setCreateUserId(UserConstants.SYS_USER_ID);
            this.storageLocationService.insertWmStorageLocation(location);
        }
        return location;
    }


    /**
     * 初始化仓库
     */
    private WmWarehouse initWarehouse(DefaultDataEnum dataEnum) {
        WmWarehouse warehouse = this.lambdaQuery().eq(WmWarehouse::getWarehouseCode, dataEnum.getCode()).one();
        if (StringUtils.isNull(warehouse)) {
            warehouse = new WmWarehouse();
            warehouse.setWarehouseCode(dataEnum.getCode());
            warehouse.setWarehouseName(dataEnum.getDesc());
            warehouse.setCreateTime(new Date());
            warehouse.setCreateBy(UserConstants.SYS_USER);
            warehouse.setCreateUserId(UserConstants.SYS_USER_ID);
            this.wmWarehouseMapper.insertWmWarehouse(warehouse);
        }
        return warehouse;
    }
}
