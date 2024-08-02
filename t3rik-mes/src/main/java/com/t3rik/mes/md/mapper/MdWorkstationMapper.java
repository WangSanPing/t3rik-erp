package com.t3rik.mes.md.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.md.domain.MdWorkstation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工作站Mapper接口
 *
 * @author yinjinlu
 * @date 2022-05-10
 */
@Mapper
public interface MdWorkstationMapper extends BaseMapper<MdWorkstation> {
    /**
     * 查询工作站
     *
     * @param workstationId 工作站主键
     * @return 工作站
     */
    public MdWorkstation selectMdWorkstationByWorkstationId(Long workstationId);

    /**
     * 查询工作站列表
     *
     * @param mdWorkstation 工作站
     * @return 工作站集合
     */
    public List<MdWorkstation> selectMdWorkstationList(MdWorkstation mdWorkstation);

    public MdWorkstation checkWorkStationCodeUnique(MdWorkstation mdWorkstation);

    public MdWorkstation checkWorkStationNameUnique(MdWorkstation mdWorkstation);


    /**
     * 新增工作站
     *
     * @param mdWorkstation 工作站
     * @return 结果
     */
    public int insertMdWorkstation(MdWorkstation mdWorkstation);

    /**
     * 修改工作站
     *
     * @param mdWorkstation 工作站
     * @return 结果
     */
    public int updateMdWorkstation(MdWorkstation mdWorkstation);

    /**
     * 删除工作站
     *
     * @param workstationId 工作站主键
     * @return 结果
     */
    public int deleteMdWorkstationByWorkstationId(Long workstationId);

    /**
     * 批量删除工作站
     *
     * @param workstationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdWorkstationByWorkstationIds(Long[] workstationIds);
}
