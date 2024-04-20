package com.t3rik.mes.wm.service;

import java.util.List;
import com.t3rik.mes.wm.domain.WmProductSalseLine;

/**
 * 产品销售出库行Service接口
 * 
 * @author yinjinlu
 * @date 2022-10-05
 */
public interface IWmProductSalseLineService 
{
    /**
     * 查询产品销售出库行
     * 
     * @param lineId 产品销售出库行主键
     * @return 产品销售出库行
     */
    public WmProductSalseLine selectWmProductSalseLineByLineId(Long lineId);

    /**
     * 查询产品销售出库行列表
     * 
     * @param wmProductSalseLine 产品销售出库行
     * @return 产品销售出库行集合
     */
    public List<WmProductSalseLine> selectWmProductSalseLineList(WmProductSalseLine wmProductSalseLine);

    /**
     * 新增产品销售出库行
     * 
     * @param wmProductSalseLine 产品销售出库行
     * @return 结果
     */
    public int insertWmProductSalseLine(WmProductSalseLine wmProductSalseLine);

    /**
     * 修改产品销售出库行
     * 
     * @param wmProductSalseLine 产品销售出库行
     * @return 结果
     */
    public int updateWmProductSalseLine(WmProductSalseLine wmProductSalseLine);

    /**
     * 批量删除产品销售出库行
     * 
     * @param lineIds 需要删除的产品销售出库行主键集合
     * @return 结果
     */
    public int deleteWmProductSalseLineByLineIds(Long[] lineIds);

    /**
     * 删除产品销售出库行信息
     * 
     * @param lineId 产品销售出库行主键
     * @return 结果
     */
    public int deleteWmProductSalseLineByLineId(Long lineId);

    /**
     * 根据出库单头删除所有行
     * @param salseId
     * @return
     */
    public int deleteBySalseId(Long salseId);
}
