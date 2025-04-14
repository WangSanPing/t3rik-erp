package com.t3rik.mes.md.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.TreeSelect;
import com.t3rik.common.core.domain.entity.MdItemType;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.mapper.ItemTypeMapper;
import com.t3rik.mes.md.service.IItemTypeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemTypeServiceImpl extends ServiceImpl<ItemTypeMapper, MdItemType> implements IItemTypeService {

    @Resource
    private ItemTypeMapper itemTypeMapper;

    @PostConstruct
    void initDefaultItemType() {
        var idList = Arrays.asList(
                DefaultDataEnum.WLCPFL_DEFAULT.getCode(),
                DefaultDataEnum.MATERIAL.getCode(),
                DefaultDataEnum.CP_DEFAULT.getCode(),
                DefaultDataEnum.SEMI_FINISHED_PRODUCTS.getCode(),
                DefaultDataEnum.PRODUCTS.getCode());
        var dataCount = this.lambdaQuery().in(MdItemType::getItemTypeId, idList).count();
        // 如果数据小于内置的5条数据，先删除再新增
        if (dataCount < idList.size()) {
            this.removeByIds(idList);
            // 新增初始化默认数据
            this.itemTypeMapper.initDefaultItemType();
        }
    }


    @Override
    public List<MdItemType> selectItemTypeList(MdItemType itemType) {
        return itemTypeMapper.selectItemTypeList(itemType);
    }

    @Override
    public MdItemType selectItemTypeById(Long itemTypeId) {
        return itemTypeMapper.selectItemTypeById(itemTypeId);
    }

    @Override
    public List<TreeSelect> buildTreeSelect(List<MdItemType> list) {
        List<MdItemType> itemTypes = buildTree(list);
        return itemTypes.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    private List<MdItemType> buildTree(List<MdItemType> itemTypes) {
        List<MdItemType> returnList = new ArrayList<MdItemType>();
        List<Long> tempList = new ArrayList<Long>();
        for (MdItemType it : itemTypes) {
            tempList.add(it.getItemTypeId());
        }

        for (MdItemType it : itemTypes) {
            if (!tempList.contains(it.getParentTypeId())) {
                recursionFn(itemTypes, it);
                returnList.add(it);
            }
        }
        if (returnList.isEmpty()) {
            returnList = itemTypes;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<MdItemType> list, MdItemType t) {
        // 得到子节点列表
        List<MdItemType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (MdItemType tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<MdItemType> getChildList(List<MdItemType> list, MdItemType t) {
        List<MdItemType> tlist = new ArrayList<MdItemType>();
        Iterator<MdItemType> it = list.iterator();
        while (it.hasNext()) {
            MdItemType n = (MdItemType) it.next();
            if (StringUtils.isNotNull(n.getParentTypeId()) && n.getParentTypeId().longValue() == t.getItemTypeId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<MdItemType> list, MdItemType t) {
        return getChildList(list, t).size() > 0;
    }


    @Override
    public String checkItemTypeCodeUnique(MdItemType itemType) {
        MdItemType itemType1 = itemTypeMapper.checkItemTypeCodeUnique(itemType.getItemTypeCode(), itemType.getParentTypeId());
        Long itemTypeId = itemType.getItemTypeId() == null ? -1L : itemType.getItemTypeId();
        if (StringUtils.isNotNull(itemType1) && itemTypeId.longValue() != itemType1.getItemTypeId().longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkItemTypeNameUnique(MdItemType itemType) {
        MdItemType itemType1 = itemTypeMapper.checkItemTypeNameUnique(itemType.getItemTypeName(), itemType.getParentTypeId());
        Long itemTypeId = itemType.getItemTypeId() == null ? -1L : itemType.getItemTypeId();
        if (StringUtils.isNotNull(itemType1) && itemTypeId.longValue() != itemType1.getItemTypeId().longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer insertItemType(MdItemType itemType) {
        if (itemType.getParentTypeId() != null) {
            MdItemType parent = itemTypeMapper.selectItemTypeById(itemType.getParentTypeId());
            if (StringUtils.isNotNull(parent)) {
                itemType.setAncestors(parent.getAncestors() + "," + parent.getItemTypeId());
            }
        }
        return itemTypeMapper.insertItemType(itemType);
    }

    @Override
    public Integer updateItemType(MdItemType itemType) {
        return itemTypeMapper.updateItemType(itemType);
    }

    @Override
    public Integer removeItemType(Long itemTypeId) {
        return itemTypeMapper.deleteItemTypeById(itemTypeId);
    }

    @Override
    public boolean checkHasChild(Long itemTypeId) {
        int num = itemTypeMapper.hasChildByItemTypeId(itemTypeId);
        return num > 0;
    }

    @Override
    public boolean checkHasItem(Long itemTypeId) {
        int num = itemTypeMapper.hasItemByItemTypeId(itemTypeId);
        return num > 0;
    }

    /**
     * 查询所有子类
     *
     */
    @Override
    public List<MdItemType> selectChildrenByAncestor(Long ancestorId) {
        return this.itemTypeMapper.selectChildrenByAncestor(ancestorId);
    }

}
