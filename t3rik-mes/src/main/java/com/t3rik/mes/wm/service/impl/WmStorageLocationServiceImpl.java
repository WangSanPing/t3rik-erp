package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmStorageLocation;
import com.t3rik.mes.wm.mapper.WmStorageLocationMapper;
import com.t3rik.mes.wm.service.IWmStorageLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库区设置Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@Service
public class WmStorageLocationServiceImpl extends ServiceImpl<WmStorageLocationMapper, WmStorageLocation> implements IWmStorageLocationService {
    @Resource
    private WmStorageLocationMapper wmStorageLocationMapper;

    /**
     * 查询库区设置
     *
     * @param locationId 库区设置主键
     * @return 库区设置
     */
    @Override
    public WmStorageLocation selectWmStorageLocationByLocationId(Long locationId) {
        return wmStorageLocationMapper.selectWmStorageLocationByLocationId(locationId);
    }

    @Override
    public WmStorageLocation selectWmStorageLocationByLocationCode(String locationCode) {
        return wmStorageLocationMapper.selectWmStorageLocationByLocationCode(locationCode);
    }

    /**
     * 查询库区设置列表
     *
     * @param wmStorageLocation 库区设置
     * @return 库区设置
     */
    @Override
    public List<WmStorageLocation> selectWmStorageLocationList(WmStorageLocation wmStorageLocation) {
        return wmStorageLocationMapper.selectWmStorageLocationList(wmStorageLocation);
    }

    @Override
    public String checkLocationCodeUnique(WmStorageLocation wmStorageLocation) {
        WmStorageLocation location = wmStorageLocationMapper.checkLocationCodeUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getLocationId() == null ? -1L : wmStorageLocation.getLocationId();
        if (StringUtils.isNotNull(location) && location.getLocationId().longValue() != locationId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkLocationNameUnique(WmStorageLocation wmStorageLocation) {
        WmStorageLocation location = wmStorageLocationMapper.checkLocationNameUnique(wmStorageLocation);
        Long locationId = wmStorageLocation.getLocationId() == null ? -1L : wmStorageLocation.getLocationId();
        if (StringUtils.isNotNull(location) && location.getLocationId().longValue() != locationId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增库区设置
     *
     * @param wmStorageLocation 库区设置
     * @return 结果
     */
    @Override
    public int insertWmStorageLocation(WmStorageLocation wmStorageLocation) {
        wmStorageLocation.setCreateTime(DateUtils.getNowDate());
        return wmStorageLocationMapper.insertWmStorageLocation(wmStorageLocation);
    }

    /**
     * 修改库区设置
     *
     * @param wmStorageLocation 库区设置
     * @return 结果
     */
    @Override
    public int updateWmStorageLocation(WmStorageLocation wmStorageLocation) {
        wmStorageLocation.setUpdateTime(DateUtils.getNowDate());
        return wmStorageLocationMapper.updateWmStorageLocation(wmStorageLocation);
    }

    /**
     * 批量删除库区设置
     *
     * @param locationIds 需要删除的库区设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageLocationByLocationIds(Long[] locationIds) {
        return wmStorageLocationMapper.deleteWmStorageLocationByLocationIds(locationIds);
    }

    /**
     * 删除库区设置信息
     *
     * @param locationId 库区设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageLocationByLocationId(Long locationId) {
        return wmStorageLocationMapper.deleteWmStorageLocationByLocationId(locationId);
    }

    @Override
    public int deleteByWarehouseId(Long warehouseId) {
        return wmStorageLocationMapper.deleteByWarehouseId(warehouseId);
    }
}
