package com.t3rik.mes.md.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.common.core.domain.TreeSelect;
import com.t3rik.common.core.domain.entity.ItemType;

import java.util.List;

public interface IItemTypeService extends IService<ItemType> {

    public List<ItemType> selectItemTypeList(ItemType itemType);

    public ItemType selectItemTypeById(Long itemTypeId);

    public List<TreeSelect> buildTreeSelect(List<ItemType> list);

    public String checkItemTypeCodeUnique(ItemType itemType);

    public String checkItemTypeNameUnique(ItemType itemType);

    public Integer insertItemType(ItemType itemType);

    public Integer updateItemType(ItemType itemType);

    public Integer removeItemType(Long itemTypeId);

    public boolean checkHasChild(Long itemTypeId);

    public boolean checkHasItem(Long itemTypeId);

    /**
     * 查询所有子类
     */
    List<ItemType> selectChildrenByAncestor(Long ancestorId);
}
