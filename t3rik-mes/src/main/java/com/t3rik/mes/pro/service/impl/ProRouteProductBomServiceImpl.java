package com.t3rik.mes.pro.service.impl;

import java.util.List;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.pro.mapper.ProRouteProductBomMapper;
import com.t3rik.mes.pro.domain.ProRouteProductBom;
import com.t3rik.mes.pro.service.IProRouteProductBomService;

/**
 * 产品制程物料BOMService业务层处理
 * 
 * @author yinjinlu
 * @date 2022-09-12
 */
@Service
public class ProRouteProductBomServiceImpl implements IProRouteProductBomService 
{
    @Autowired
    private ProRouteProductBomMapper proRouteProductBomMapper;

    /**
     * 查询产品制程物料BOM
     * 
     * @param recordId 产品制程物料BOM主键
     * @return 产品制程物料BOM
     */
    @Override
    public ProRouteProductBom selectProRouteProductBomByRecordId(Long recordId)
    {
        return proRouteProductBomMapper.selectProRouteProductBomByRecordId(recordId);
    }

    /**
     * 查询产品制程物料BOM列表
     * 
     * @param proRouteProductBom 产品制程物料BOM
     * @return 产品制程物料BOM
     */
    @Override
    public List<ProRouteProductBom> selectProRouteProductBomList(ProRouteProductBom proRouteProductBom)
    {
        return proRouteProductBomMapper.selectProRouteProductBomList(proRouteProductBom);
    }

    @Override
    public String checkUnique(ProRouteProductBom proRouteProductBom) {
        ProRouteProductBom bom = proRouteProductBomMapper.checkUnique(proRouteProductBom);
        Long recordId = proRouteProductBom.getRecordId() == null? -1L: proRouteProductBom.getRecordId();
        if(StringUtils.isNotNull(bom) && bom.getRecordId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增产品制程物料BOM
     * 
     * @param proRouteProductBom 产品制程物料BOM
     * @return 结果
     */
    @Override
    public int insertProRouteProductBom(ProRouteProductBom proRouteProductBom)
    {
        proRouteProductBom.setCreateTime(DateUtils.getNowDate());
        return proRouteProductBomMapper.insertProRouteProductBom(proRouteProductBom);
    }

    /**
     * 修改产品制程物料BOM
     * 
     * @param proRouteProductBom 产品制程物料BOM
     * @return 结果
     */
    @Override
    public int updateProRouteProductBom(ProRouteProductBom proRouteProductBom)
    {
        proRouteProductBom.setUpdateTime(DateUtils.getNowDate());
        return proRouteProductBomMapper.updateProRouteProductBom(proRouteProductBom);
    }

    /**
     * 批量删除产品制程物料BOM
     * 
     * @param recordIds 需要删除的产品制程物料BOM主键
     * @return 结果
     */
    @Override
    public int deleteProRouteProductBomByRecordIds(Long[] recordIds)
    {
        return proRouteProductBomMapper.deleteProRouteProductBomByRecordIds(recordIds);
    }

    /**
     * 删除产品制程物料BOM信息
     * 
     * @param recordId 产品制程物料BOM主键
     * @return 结果
     */
    @Override
    public int deleteProRouteProductBomByRecordId(Long recordId)
    {
        return proRouteProductBomMapper.deleteProRouteProductBomByRecordId(recordId);
    }

    @Override
    public int deleteByRouteId(Long routeId) {
        return proRouteProductBomMapper.deleteByRouteId(routeId);
    }

    @Override
    public int deleteByRouteIdAndProductId(ProRouteProductBom proRouteProductBom) {
        return proRouteProductBomMapper.deleteByRouteIdAndProductId(proRouteProductBom);
    }
}
