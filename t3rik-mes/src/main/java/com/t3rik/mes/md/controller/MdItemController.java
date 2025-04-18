package com.t3rik.mes.md.controller;

import cn.hutool.core.lang.Assert;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.entity.MdItemType;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.enums.system.EnableFlagEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.support.ItemTypeSupport;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.service.IItemTypeService;
import com.t3rik.mes.md.service.IMdItemService;
import com.t3rik.mes.wm.aspect.BarcodeGen;
import com.t3rik.mes.wm.utils.WmBarCodeUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mes/md/mditem")
public class MdItemController extends BaseController {

    @Autowired
    private IMdItemService mdItemService;

    @Autowired
    private IItemTypeService iItemTypeService;

    @Autowired
    private WmBarCodeUtil barcodeUtil;

    /**
     * 列表查询
     *
     * @param mdItem
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(MdItem mdItem) {
        startPage();
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }


    /**
     * 主键查询
     *
     * @param itemId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable Long itemId) {
        return AjaxResult.success(mdItemService.selectMdItemById(itemId));
    }

    /**
     * 新增
     *
     * @param mdItem
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "物料管理", businessType = BusinessType.INSERT)
    @BarcodeGen(barcodeType = UserConstants.BARCODE_TYPE_ITEM)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody MdItem mdItem) {
        if (UserConstants.NOT_UNIQUE.equals(mdItemService.checkItemCodeUnique(mdItem))) {
            return AjaxResult.error("新增物料" + mdItem.getItemCode() + "失败，物料编码已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(mdItemService.checkItemNameUnique(mdItem))) {
            return AjaxResult.error("新增物料" + mdItem.getItemCode() + "失败，物料名称已存在");
        }

        MdItemType type = iItemTypeService.selectItemTypeById(mdItem.getItemTypeId());
        if (StringUtils.isNotNull(type)) {
            mdItem.setItemTypeCode(type.getItemTypeCode());
            mdItem.setItemTypeName(type.getItemTypeName());
            mdItem.setItemOrProduct(type.getItemOrProduct());
        }
        mdItem.setCreateBy(getUsername());
        mdItemService.insertMdItem(mdItem);
        barcodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_ITEM, mdItem.getItemId(), mdItem.getItemCode(), mdItem.getItemName());
        return AjaxResult.success(mdItem.getItemId());
    }

    /**
     * 列表查询-只查询产品列表
     *
     * @param mdItem
     * @return
     */
    @Log(title = "物料管理", businessType = BusinessType.SEARCH)
    @GetMapping("/list/product/{type}")
    public TableDataInfo listProduct(MdItem mdItem, @PathVariable("type") String type) {
        startPage();
        // 根据类型查询
        String itemTypeId = ItemTypeSupport.getDefaultDataIdByItemType(type);
        Assert.notNull(itemTypeId, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        mdItem.setItemTypeId(Long.valueOf(itemTypeId));
        mdItem.setEnableFlag(EnableFlagEnum.YES.getCode());
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }

    /**
     * 新增-只新增产品品类
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "物料管理", businessType = BusinessType.INSERT)
    @BarcodeGen(barcodeType = UserConstants.BARCODE_TYPE_ITEM)
    @PostMapping("/product/{type}")
    public AjaxResult addProduct(@Validated @RequestBody MdItem mdItem, @PathVariable("type") String type) {
        if (UserConstants.NOT_UNIQUE.equals(mdItemService.checkItemCodeUnique(mdItem))) {
            return AjaxResult.error("新增物料" + mdItem.getItemCode() + "失败，物料编码已存在");
        }
        this.mdItemService.addItemOrProduct(mdItem, type);
        return AjaxResult.success(mdItem.getItemId());
    }

    /**
     * 更新
     *
     * @param mdItem
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "物料管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody MdItem mdItem) {
        if (UserConstants.NOT_UNIQUE.equals(mdItemService.checkItemCodeUnique(mdItem))) {
            return AjaxResult.error("新增物料" + mdItem.getItemCode() + "失败，物料编码已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(mdItemService.checkItemNameUnique(mdItem))) {
            return AjaxResult.error("新增物料" + mdItem.getItemCode() + "失败，物料名称已存在");
        }
        MdItemType type = iItemTypeService.selectItemTypeById(mdItem.getItemTypeId());
        if (StringUtils.isNotNull(type)) {
            mdItem.setItemTypeCode(type.getItemTypeCode());
            mdItem.setItemTypeName(type.getItemTypeName());
            mdItem.setItemOrProduct(type.getItemOrProduct());
        }
        if (StringUtils.isNotNull(mdItem.getSafeStockFlag()) && "N".equals(mdItem.getSafeStockFlag())) {
            mdItem.setMinStock(0D);
            mdItem.setMaxStock(0D);
        }

        mdItem.setUpdateBy(getUsername());
        return toAjax(mdItemService.updateMdItem(mdItem));
    }

    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "物料管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds) {
        return toAjax(mdItemService.deleteByItemIds(itemIds));
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<MdItem> util = new ExcelUtil<MdItem>(MdItem.class);
        util.importTemplateExcel(response, "产品数据");
    }

    @Log(title = "物料管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<MdItem> util = new ExcelUtil<MdItem>(MdItem.class);
        List<MdItem> mdItemList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = mdItemService.importVendor(mdItemList, updateSupport, operName);
        return AjaxResult.success(message);
    }
}
