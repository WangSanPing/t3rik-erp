package com.t3rik.mes.md.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.md.domain.MdItem;

import java.util.List;

public interface IMdItemService extends IService<MdItem> {

    /**
     * 根据条件查询物料编码
     *
     * @param mdItem
     * @return
     */
    public List<MdItem> selectMdItemList(MdItem mdItem);

    /**
     * 查询所有物料编码
     *
     * @return
     */
    public List<MdItem> selectMdItemAll();


    /**
     * 根据主键查询物料编码
     *
     * @param itemId
     * @return
     */
    public MdItem selectMdItemById(Long itemId);

    /**
     * 检查物料编码是否唯一
     *
     * @param mdItem
     * @return
     */
    public String checkItemCodeUnique(MdItem mdItem);

    /**
     * 检查物料名称是否唯一
     *
     * @param mdItem
     * @return
     */
    public String checkItemNameUnique(MdItem mdItem);

    /**
     * 新增物料编码
     *
     * @param mdItem
     * @return
     */
    public int insertMdItem(MdItem mdItem);

    /**
     * 更新物料编码
     *
     * @param mdItem
     * @return
     */
    public int updateMdItem(MdItem mdItem);

    /**
     * 批量删除物料编码
     *
     * @param itemIds
     * @return
     */
    public int deleteByItemIds(Long[] itemIds);

    /**
     * 根据主键删除物料编码
     *
     * @param itemId
     * @return
     */
    public int deleteByItemId(Long itemId);

    /**
     * 添加产品
     */
    Boolean addItemOrProduct(MdItem mdItem,String type);

    /**
     * 导入产品信息
     * @param vendorList
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    public String importVendor(List<MdItem> vendorList, Boolean isUpdateSupport, String operName);

}

