package com.t3rik.mes.wm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.mapper.WmMaterialStockMapper;
import com.t3rik.mes.wm.service.IWmMaterialStockService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 库存记录Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-24
 */
@Service
public class WmMaterialStockServiceImpl extends ServiceImpl<WmMaterialStockMapper,WmMaterialStock> implements IWmMaterialStockService
{
    @Resource
    private WmMaterialStockMapper wmMaterialStockMapper;

    /**
     * 查询库存记录
     *
     * @param materialStockId 库存记录主键
     * @return 库存记录
     */
    @Override
    public WmMaterialStock selectWmMaterialStockByMaterialStockId(Long materialStockId)
    {
        return wmMaterialStockMapper.selectWmMaterialStockByMaterialStockId(materialStockId);
    }


    /**
     * 查询库存记录列表
     *
     * @param wmMaterialStock 库存记录
     * @return 库存记录
     */
    @Override
    public List<WmMaterialStock> selectWmMaterialStockList(WmMaterialStock wmMaterialStock)
    {
        return wmMaterialStockMapper.selectWmMaterialStockList(wmMaterialStock);
    }

    @Override
    public List<WmMaterialStock> queryWmMaterialStockList(WmMaterialStock wmMaterialStock) {
        return wmMaterialStockMapper.queryWmMaterialStockList(wmMaterialStock);
    }

    /**
     * 新增库存记录
     *
     * @param wmMaterialStock 库存记录
     * @return 结果
     */
    @Override
    public int insertWmMaterialStock(WmMaterialStock wmMaterialStock)
    {
        wmMaterialStock.setCreateTime(DateUtils.getNowDate());
        return wmMaterialStockMapper.insertWmMaterialStock(wmMaterialStock);
    }

    /**
     * 修改库存记录
     *
     * @param wmMaterialStock 库存记录
     * @return 结果
     */
    @Override
    public int updateWmMaterialStock(WmMaterialStock wmMaterialStock)
    {
        wmMaterialStock.setUpdateTime(DateUtils.getNowDate());
        return wmMaterialStockMapper.updateWmMaterialStock(wmMaterialStock);
    }

    /**
     * 批量删除库存记录
     *
     * @param materialStockIds 需要删除的库存记录主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialStockByMaterialStockIds(Long[] materialStockIds)
    {
        return wmMaterialStockMapper.deleteWmMaterialStockByMaterialStockIds(materialStockIds);
    }

    /**
     * 删除库存记录信息
     *
     * @param materialStockId 库存记录主键
     * @return 结果
     */
    @Override
    public int deleteWmMaterialStockByMaterialStockId(Long materialStockId)
    {
        return wmMaterialStockMapper.deleteWmMaterialStockByMaterialStockId(materialStockId);
    }

    /**
     * 统计库存数量 根据物料id和年月
     * @param itemCode 物资编码
     * @param dateTime 时间
     * @return
     */
    @Override
    public List<Map> selectMaterielCount(String itemCode, Date dateTime) {
        return wmMaterialStockMapper.selectMaterielCount(itemCode, dateTime);
    }


}
