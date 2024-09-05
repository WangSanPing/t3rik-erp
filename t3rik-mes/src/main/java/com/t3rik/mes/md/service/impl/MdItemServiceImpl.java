package com.t3rik.mes.md.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.entity.ItemType;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.support.ItemTypeSupport;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.bean.BeanValidators;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.domain.MdVendor;
import com.t3rik.mes.md.mapper.MdItemMapper;
import com.t3rik.mes.md.service.IItemTypeService;
import com.t3rik.mes.md.service.IMdItemService;
import com.t3rik.mes.wm.utils.WmBarCodeUtil;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MdItemServiceImpl extends ServiceImpl<MdItemMapper, MdItem> implements IMdItemService {

    @Autowired
    private MdItemMapper mdItemMapper;

    @Resource
    private IItemTypeService iItemTypeService;

    @Resource
    private WmBarCodeUtil barcodeUtil;

    @Autowired
    protected Validator validator;

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
        String itemTypeId = ItemTypeSupport.getDefaultDataIdByItemType(type);
        Assert.notNull(itemTypeId, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        // 查询类型具体信息
        ItemType itemType = this.iItemTypeService.getById(itemTypeId);
        Assert.notNull(itemType, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        mdItem.setItemTypeId(itemType.getItemTypeId());
        mdItem.setItemTypeCode(itemType.getItemTypeCode());
        mdItem.setItemTypeName(itemType.getItemTypeName());
        mdItem.setItemOrProduct(itemType.getItemOrProduct());
        boolean save = this.save(mdItem);
        barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM, mdItem.getItemId(), mdItem.getItemCode(), mdItem.getItemName());
        return save;
    }
    @Override
    public String importVendor(List<MdItem> mdItemList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(mdItemList) || mdItemList.size() == 0)
        {
            throw new BusinessException("导入产品数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        List<ItemType> typeList = iItemTypeService.list();
        Map<String,ItemType> typeMap=new HashMap();
        for (ItemType li:typeList){
            typeMap.put(li.getItemTypeName(),li);
        }


        for (MdItem mdItem : mdItemList)
        {
            try{
                //是否存在
                MdItem v = mdItemMapper.checkMdItemCodeUnique(mdItem);


                ItemType type = typeMap.get(mdItem.getItemOrProduct());
                if (StringUtils.isNotNull(type)) {
                    mdItem.setItemTypeId(type.getItemTypeId());
                    mdItem.setItemTypeCode(type.getItemTypeCode());
                    mdItem.setItemTypeName(type.getItemTypeName());
                    mdItem.setItemOrProduct(type.getItemOrProduct());
                }
                if (StringUtils.isNotNull(mdItem.getSafeStockFlag()) && "N".equals(mdItem.getSafeStockFlag())) {

                    mdItem.setMinStock(0D);
                    mdItem.setMaxStock(0D);
                }
                mdItem.setSafeStockFlag("N");

                if(StringUtils.isNull(v)){
                    BeanValidators.validateWithException(validator, mdItem);
                    this.insertMdItem(mdItem);
                    mdItem.setCreateBy(operName);
                    barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM, mdItem.getItemId(), mdItem.getItemCode(), mdItem.getItemName());
                    successNum++;
                }else if (isUpdateSupport){
                    BeanValidators.validateWithException(validator, mdItem);
                    mdItem.setUpdateBy(operName);
                    mdItem.setItemId(v.getItemId());
                    this.updateMdItem(mdItem);
                    successNum++;
                }else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、产品 " + mdItem.getItemCode() + " 已存在");
                }

            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、产品 " + mdItem.getItemName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
