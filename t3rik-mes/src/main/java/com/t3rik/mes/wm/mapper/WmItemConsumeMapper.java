package com.t3rik.mes.wm.mapper;

import com.t3rik.mes.wm.domain.WmItemConsume;
import com.t3rik.mes.wm.domain.tx.ItemConsumeTxBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 物料消耗记录Mapper接口
 *
 * @author yinjinlu
 * @date 2022-09-19
 */
@Mapper
public interface WmItemConsumeMapper {
    /**
     * 查询物料消耗记录
     *
     * @param recordId 物料消耗记录主键
     * @return 物料消耗记录
     */
    public WmItemConsume selectWmItemConsumeByRecordId(Long recordId);

    /**
     * 查询物料消耗记录列表
     *
     * @param wmItemConsume 物料消耗记录
     * @return 物料消耗记录集合
     */
    public List<WmItemConsume> selectWmItemConsumeList(WmItemConsume wmItemConsume);

    /**
     * 新增物料消耗记录
     *
     * @param wmItemConsume 物料消耗记录
     * @return 结果
     */
    public int insertWmItemConsume(WmItemConsume wmItemConsume);

    /**
     * 修改物料消耗记录
     *
     * @param wmItemConsume 物料消耗记录
     * @return 结果
     */
    public int updateWmItemConsume(WmItemConsume wmItemConsume);

    /**
     * 删除物料消耗记录
     *
     * @param recordId 物料消耗记录主键
     * @return 结果
     */
    public int deleteWmItemConsumeByRecordId(Long recordId);

    /**
     * 批量删除物料消耗记录
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmItemConsumeByRecordIds(Long[] recordIds);

    public List<ItemConsumeTxBean> getTxBeans(Long recordId);
}
