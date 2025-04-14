package com.t3rik.mes.md.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.common.core.domain.entity.MdItemType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemTypeMapper extends BaseMapper<MdItemType> {

    /**
     * 根据条件查询物料分类
     *
     * @param itemType
     * @return
     */
    public List<MdItemType> selectItemTypeList(MdItemType itemType);

    /**
     * 根据主键查询物料分类
     *
     * @param itemTypeId
     * @return
     */
    public MdItemType selectItemTypeById(Long itemTypeId);

    /**
     * 根据父类ID查询是否有子类
     *
     * @param parentTypeId
     * @return
     */
    public Integer hasChildByItemTypeId(Long parentTypeId);


    /**
     * 根据物料分类查询是否有对应的物料和产品
     *
     * @param itemTypeId
     * @return
     */
    public Integer hasItemByItemTypeId(Long itemTypeId);

    /**
     * 根据父类查询所有子类
     *
     * @param parentTypeId
     * @return
     */
    public List<MdItemType> selectChildrenItemTypeById(Long parentTypeId);


    /**
     * 查询是否有可用的子类
     *
     * @param parentTypeId
     * @return
     */
    public Integer selectNormalChildrenItemTypeById(Long parentTypeId);

    /**
     * 检查同一个父类下子类名称是否重复
     *
     * @param itemTypeName
     * @param parentTypeId
     * @return
     */
    public MdItemType checkItemTypeNameUnique(@Param("itemTypeName") String itemTypeName, @Param("parentTypeId") Long parentTypeId);

    /**
     * 检查同一个父类下子类编码是否重复
     *
     * @param itemTypeCode
     * @param parentTypeId
     * @return
     */
    public MdItemType checkItemTypeCodeUnique(@Param("itemTypeCode") String itemTypeCode, @Param("parentTypeId") Long parentTypeId);

    /**
     * 新增物料分类
     *
     * @param itemType
     * @return
     */
    public Integer insertItemType(MdItemType itemType);

    /**
     * 更新物料分类
     *
     * @param itemType
     * @return
     */
    public Integer updateItemType(MdItemType itemType);

    /**
     * 设置物料分类不可用
     *
     * @param itemTypeIds
     */
    public void updateItemTypeStatusNormal(Long[] itemTypeIds);

    /**
     * 根据主键删除物料分类
     *
     * @param itemTypeId
     * @return
     */
    public Integer deleteItemTypeById(Long itemTypeId);

    /**
     * 批量删除物料分类
     *
     * @param itemTypeIds
     * @return
     */
    public Integer deleteItemTypeByIds(Long[] itemTypeIds);

    /**
     * 初始化默认数据
     */
    void initDefaultItemType();

    /**
     * 查询所有子类
     */
    List<MdItemType> selectChildrenByAncestor(@Param("ancestorId") Long ancestorId);
}
