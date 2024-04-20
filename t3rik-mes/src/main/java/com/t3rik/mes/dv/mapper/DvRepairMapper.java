package com.t3rik.mes.dv.mapper;

import java.util.List;
import com.t3rik.mes.dv.domain.DvRepair;

/**
 * 设备维修单Mapper接口
 * 
 * @author yinjinlu
 * @date 2022-08-06
 */
public interface DvRepairMapper 
{
    /**
     * 查询设备维修单
     * 
     * @param repairId 设备维修单主键
     * @return 设备维修单
     */
    public DvRepair selectDvRepairByRepairId(Long repairId);

    /**
     * 查询设备维修单列表
     * 
     * @param dvRepair 设备维修单
     * @return 设备维修单集合
     */
    public List<DvRepair> selectDvRepairList(DvRepair dvRepair);

    public  DvRepair checkCodeUnique(DvRepair dvRepair);


    /**
     * 新增设备维修单
     * 
     * @param dvRepair 设备维修单
     * @return 结果
     */
    public int insertDvRepair(DvRepair dvRepair);

    /**
     * 修改设备维修单
     * 
     * @param dvRepair 设备维修单
     * @return 结果
     */
    public int updateDvRepair(DvRepair dvRepair);

    /**
     * 删除设备维修单
     * 
     * @param repairId 设备维修单主键
     * @return 结果
     */
    public int deleteDvRepairByRepairId(Long repairId);

    /**
     * 批量删除设备维修单
     * 
     * @param repairIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDvRepairByRepairIds(Long[] repairIds);
}
