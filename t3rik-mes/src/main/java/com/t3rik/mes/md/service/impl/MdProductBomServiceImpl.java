package com.t3rik.mes.md.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.md.mapper.MdProductBomMapper;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;

/**
 * 产品BOM关系Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-09
 */
@Service
public class MdProductBomServiceImpl extends ServiceImpl<MdProductBomMapper,MdProductBom> implements IMdProductBomService
{
    @Autowired
    private MdProductBomMapper mdProductBomMapper;

    /**
     * 查询产品BOM关系
     * 
     * @param bomId 产品BOM关系主键
     * @return 产品BOM关系
     */
    @Override
    public MdProductBom selectMdProductBomByBomId(Long bomId)
    {
        return mdProductBomMapper.selectMdProductBomByBomId(bomId);
    }

    /**
     * 查询产品BOM关系列表
     * 
     * @param mdProductBom 产品BOM关系
     * @return 产品BOM关系
     */
    @Override
    public List<MdProductBom> selectMdProductBomList(MdProductBom mdProductBom)
    {
        return mdProductBomMapper.selectMdProductBomList(mdProductBom);
    }

    /**
     * 新增产品BOM关系
     * 
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    @Override
    public int insertMdProductBom(MdProductBom mdProductBom)
    {
        mdProductBom.setCreateTime(DateUtils.getNowDate());
        return mdProductBomMapper.insertMdProductBom(mdProductBom);
    }

    /**
     * 修改产品BOM关系
     * 
     * @param mdProductBom 产品BOM关系
     * @return 结果
     */
    @Override
    public int updateMdProductBom(MdProductBom mdProductBom)
    {
        mdProductBom.setUpdateTime(DateUtils.getNowDate());
        return mdProductBomMapper.updateMdProductBom(mdProductBom);
    }

    /**
     * 批量删除产品BOM关系
     * 
     * @param bomIds 需要删除的产品BOM关系主键
     * @return 结果
     */
    @Override
    public int deleteMdProductBomByBomIds(Long[] bomIds)
    {
        return mdProductBomMapper.deleteMdProductBomByBomIds(bomIds);
    }

    /**
     * 删除产品BOM关系信息
     * 
     * @param bomId 产品BOM关系主键
     * @return 结果
     */
    @Override
    public int deleteMdProductBomByBomId(Long bomId)
    {
        return mdProductBomMapper.deleteMdProductBomByBomId(bomId);
    }

    @Override
    public int updateItemBomAndLevel(List<MdProductBom> productBomList) {
        return this.mdProductBomMapper.updateLevelByItemIdAndBomItemId(productBomList);
    }
}
