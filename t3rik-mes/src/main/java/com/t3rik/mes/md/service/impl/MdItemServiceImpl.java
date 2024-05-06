package com.t3rik.mes.md.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.entity.ItemType;
import com.t3rik.common.enums.DefaultDataEnum;
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
    public Boolean addItemProduct(MdItem mdItem) {
        // 查询产品类型
        ItemType type = this.iItemTypeService.selectItemTypeById(DefaultDataEnum.PRODUCTS.getDataId());
        Assert.notNull(type, "数据有误,请联系管理员");
        mdItem.setItemTypeId(type.getItemTypeId());
        mdItem.setItemTypeCode(type.getItemTypeCode());
        mdItem.setItemTypeName(type.getItemTypeName());
        mdItem.setItemOrProduct(type.getItemOrProduct());
        boolean save = this.save(mdItem);
        barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM, mdItem.getItemId(), mdItem.getItemCode(), mdItem.getItemName());
        return save;
    }
}
