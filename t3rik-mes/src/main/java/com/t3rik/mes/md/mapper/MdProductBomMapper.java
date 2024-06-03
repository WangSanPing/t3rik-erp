package com.t3rik.mes.md.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.md.domain.MdProductBom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品BOM关系Mapper接口
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
@Mapper
public interface MdProductBomMapper extends BaseMapper<MdProductBom> {
    /**
     * 查询产品BOM关系
     *
     * @param bomId 产品BOM关系主键
     * @return 产品BOM关系
     */
    public MdProductBom selectMdProductBomByBomId(Long bomId);

    /**
     * 查询产品BOM关系列表
     *
     * @param mdProductBom 产品BOM关系
     * @return 产品BOM关系集合
     */
    public List<MdProductBom> selectMdProductBomList(MdProductBom mdProductBom);

    /**
     * 新增产品BOM关系
     *
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    public int insertMdProductBom(MdProductBom mdProductBom);

    /**
     * 修改产品BOM关系
     *
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    public int updateMdProductBom(MdProductBom mdProductBom);

    /**
     * 删除产品BOM关系
     *
     * @param bomId 产品BOM关系主键
     * @return 结果
     */
    public int deleteMdProductBomByBomId(Long bomId);

    /**
     * 批量删除产品BOM关系
     *
     * @param bomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdProductBomByBomIds(Long[] bomIds);

    /**
     * 更新bom中的level级别
     */
    int updateLevelByItemIdAndBomItemId(@Param("productBomList") List<MdProductBom> productBomList);



}
