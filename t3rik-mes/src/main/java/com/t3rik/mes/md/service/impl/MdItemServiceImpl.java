package com.t3rik.mes.md.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.entity.ItemType;
import com.t3rik.common.exception.BussinessException;
import com.t3rik.common.support.ItemTypeSupport;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.mapper.MdItemMapper;
import com.t3rik.mes.md.service.IItemTypeService;
import com.t3rik.mes.md.service.IMdItemService;
import com.t3rik.mes.wm.utils.WmBarCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MdItemServiceImpl extends ServiceImpl<MdItemMapper, MdItem> implements IMdItemService {

    @Autowired
    private MdItemMapper mdItemMapper;

    @Resource
    private IItemTypeService iItemTypeService;

    @Resource
    private WmBarCodeUtil barcodeUtil;

    @Override
    public List<MdItem> selectMdItemList(MdItem mdItem) {
        return mdItemMapper.selectMdItemList(mdItem);
    }

    @Override
    public List<MdItem> selectMdItemAll() {
        return mdItemMapper.selectMdItemAll();
    }


    @Override
    public MdItem selectMdItemById(Long itemId) {
        return mdItemMapper.selectMdItemById(itemId);
    }

    @Override
    public String checkItemCodeUnique(MdItem mdItem) {
        MdItem item = mdItemMapper.checkItemCodeUnique(mdItem);
        Long itemId = mdItem.getItemId() == null ? -1L : mdItem.getItemId();
        if (StringUtils.isNotNull(item) && item.getItemId().longValue() != itemId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        } else {
            return UserConstants.UNIQUE;
        }
    }

    @Override
    public String checkItemNameUnique(MdItem mdItem) {
        MdItem item = mdItemMapper.checkItemNameUnique(mdItem);
        Long itemId = mdItem.getItemId() == null ? -1L : mdItem.getItemId();
        if (StringUtils.isNotNull(item) && item.getItemId().longValue() != itemId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        } else {
            return UserConstants.UNIQUE;
        }
    }

    @Override
    public int insertMdItem(MdItem mdItem) {
        return mdItemMapper.insertMdItem(mdItem);
    }

    @Override
    public int updateMdItem(MdItem mdItem) {
        return mdItemMapper.updateMdItem(mdItem);
    }

    @Override
    public int deleteByItemIds(Long[] itemIds) {
        return mdItemMapper.deleteMdItemByIds(itemIds);
    }

    @Override
    public int deleteByItemId(Long itemId) {
        return mdItemMapper.deleteMdItemById(itemId);
    }

    @Override
    public Boolean addItemOrProduct(MdItem mdItem, String type) {
        // 根据类型查询
        Long itemTypeId = ItemTypeSupport.getDefaultDataIdByItemType(type);
        Assert.notNull(itemTypeId, () -> new BussinessException(MsgConstants.PARAM_ERROR));
        // 查询类型具体信息
        ItemType itemType = this.iItemTypeService.selectItemTypeById(itemTypeId);
        Assert.notNull(itemType, () -> new BussinessException(MsgConstants.PARAM_ERROR));
        mdItem.setItemTypeId(itemType.getItemTypeId());
        mdItem.setItemTypeCode(itemType.getItemTypeCode());
        mdItem.setItemTypeName(itemType.getItemTypeName());
        mdItem.setItemOrProduct(itemType.getItemOrProduct());
        boolean save = this.save(mdItem);
        barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM, mdItem.getItemId(), mdItem.getItemCode(), mdItem.getItemName());
        return save;
    }
}
