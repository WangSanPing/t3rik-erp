package com.t3rik.mes.md.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.entity.ItemType;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.md.service.IItemTypeService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mes/md/itemtype")
public class ItemTypeController extends BaseController {

    @Resource
    private IItemTypeService iItemTypeService;
    @Resource
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询分类列表
     *
     * @param itemType
     * @return
     */
    @GetMapping("/list")
    public AjaxResult list(ItemType itemType) {
        List<ItemType> list = iItemTypeService.selectItemTypeList(itemType);
        return AjaxResult.success(list);
    }

    /**
     * 查询部门列表
     */
    @GetMapping("/list/exclude/{itemTypeId}")
    public AjaxResult excludeChild(@PathVariable(value = "itemTypeId", required = false) Long itemTypeId) {
        List<ItemType> list = iItemTypeService.selectItemTypeList(new ItemType());
        return AjaxResult.success(list);
    }

    /**
     * 查询部门详情
     *
     * @param itemTypeId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:query')")
    @GetMapping(value = "/{itemTypeId}")
    public AjaxResult getInfo(@PathVariable Long itemTypeId) {
        // 权限校验?
        return AjaxResult.success(iItemTypeService.selectItemTypeById(itemTypeId));
    }

    /**
     * 获取树形结构数据
     *
     * @param itemType
     * @return
     */
    @GetMapping("/treeselect")
    public AjaxResult treeSelect(ItemType itemType) {
        List<ItemType> list = iItemTypeService.selectItemTypeList(itemType);
        return AjaxResult.success(iItemTypeService.buildTreeSelect(list));
    }


    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:add')")
    @Log(title = "新增物料产品分类信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ItemType itemType) {

        if (UserConstants.NOT_UNIQUE.equals(iItemTypeService.checkItemTypeCodeUnique(itemType))) {
            return AjaxResult.error("分类" + itemType.getItemTypeCode() + "编码已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(iItemTypeService.checkItemTypeNameUnique(itemType))) {
            return AjaxResult.error("分类" + itemType.getItemTypeCode() + "名称已存在");
        }
        if (itemType.getParentTypeId() == null || itemType.getParentTypeId() == 0) {
            itemType.setParentTypeId(0L);
        }
        // 自动生成一个唯一编码
        itemType.setItemTypeCode(autoCodeUtil.genSerialCode(UserConstants.ITEM_TYPE_CODE, null));
        itemType.setCreateBy(getUsername());
        return AjaxResult.success(iItemTypeService.insertItemType(itemType));
    }

    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:update')")
    @Log(title = "更新物料产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated @RequestBody ItemType itemType) {
        if (UserConstants.NOT_UNIQUE.equals(iItemTypeService.checkItemTypeCodeUnique(itemType))) {
            return AjaxResult.error("分类" + itemType.getItemTypeCode() + "编码已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(iItemTypeService.checkItemTypeNameUnique(itemType))) {
            return AjaxResult.error("分类" + itemType.getItemTypeCode() + "名称已存在");
        }
        itemType.setUpdateBy(getUsername());
        return AjaxResult.success(iItemTypeService.updateItemType(itemType));
    }

    @PreAuthorize("@ss.hasPermi('mes:md:itemtype:remove')")
    @Log(title = "删除物料产品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemTypeId}")
    public AjaxResult del(@PathVariable Long itemTypeId) {

        // 预置数据不允许删除
        var dataEnum = DefaultDataEnum.getEnumByCode(String.valueOf(itemTypeId));
        Optional.ofNullable(dataEnum).ifPresent(v -> {
            throw new BusinessException(MsgConstants.CAN_NOT_BE_DELETE);
        });

        if (iItemTypeService.checkHasChild(itemTypeId)) {
            return AjaxResult.error("分类下有子分类，请先删除子分类");
        }

        if (iItemTypeService.checkHasItem(itemTypeId)) {
            return AjaxResult.error("分类下有物料，请先删除物料");
        }

        // 权限数据检查？
        return AjaxResult.success(iItemTypeService.removeItemType(itemTypeId));
    }

}
