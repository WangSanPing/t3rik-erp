package com.t3rik.mes.wm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.wm.domain.WmMaterialStock;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库存记录Service接口
 *
 * @author yinjinlu
 * @date 2022-05-24
 */
public interface IWmMaterialStockService extends IService<WmMaterialStock> {
    /**
     * 查询库存记录
     *
     * @param materialStockId 库存记录主键
     * @return 库存记录
     */
    public WmMaterialStock selectWmMaterialStockByMaterialStockId(Long materialStockId);


    /**
     * 查询库存记录列表
     *
     * @param wmMaterialStock 库存记录
     * @return 库存记录集合
     */
    public List<WmMaterialStock> selectWmMaterialStockList(WmMaterialStock wmMaterialStock);


    /**
     * 模糊查询库存记录列表
     *
     * @param wmMaterialStock 库存记录
     * @return 库存记录集合
     */
    public List<WmMaterialStock> queryWmMaterialStockList(WmMaterialStock wmMaterialStock);


    /**
     * 新增库存记录
     *
     * @param wmMaterialStock 库存记录
     * @return 结果
     */
    public int insertWmMaterialStock(WmMaterialStock wmMaterialStock);

    /**
     * 修改库存记录
     *
     * @param wmMaterialStock 库存记录
     * @return 结果
     */
    public int updateWmMaterialStock(WmMaterialStock wmMaterialStock);

    /**
     * 批量删除库存记录
     *
     * @param materialStockIds 需要删除的库存记录主键集合
     * @return 结果
     */
    public int deleteWmMaterialStockByMaterialStockIds(Long[] materialStockIds);

    /**
     * 删除库存记录信息
     *
     * @param materialStockId 库存记录主键
     * @return 结果
     */
    public int deleteWmMaterialStockByMaterialStockId(Long materialStockId);


    /**
     * 统计库存数量 根据物料id和年月
     *
     * @param itemCode 物资编码
     * @param dateTime 时间
     * @return
     */
    List<Map> selectMaterielCount(String itemCode, Date dateTime);
}
