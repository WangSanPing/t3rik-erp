package com.t3rik.mes.pro.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProRouteProduct;
import com.t3rik.mes.pro.domain.ProRouteProductBom;
import com.t3rik.mes.pro.service.IProRouteProductBomService;
import com.t3rik.mes.pro.service.IProRouteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品制程Controller
 * 
 * @author yinjinlu
 * @date 2022-05-14
 */
@RestController
@RequestMapping("/mes/pro/routeproduct")
public class ProRouteProductController extends BaseController
{
    @Autowired
    private IProRouteProductService proRouteProductService;

    @Autowired
    private IProRouteProductBomService proRouteProductBomService;

    /**
     * 查询产品制程列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProRouteProduct proRouteProduct)
    {
        startPage();
        List<ProRouteProduct> list = proRouteProductService.selectProRouteProductList(proRouteProduct);
        return getDataTable(list);
    }


    /**
     * 导出产品制程列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:export')")
    @Log(title = "产品制程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProRouteProduct proRouteProduct)
    {
        List<ProRouteProduct> list = proRouteProductService.selectProRouteProductList(proRouteProduct);
        ExcelUtil<ProRouteProduct> util = new ExcelUtil<ProRouteProduct>(ProRouteProduct.class);
        util.exportExcel(response, list, "产品制程数据");
    }

    /**
     * 获取产品制程详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proRouteProductService.selectProRouteProductByRecordId(recordId));
    }

    /**
     * 新增产品制程
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:add')")
    @Log(title = "产品制程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProRouteProduct proRouteProduct)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProductService.checkItemUnique(proRouteProduct))){
            return AjaxResult.error("此产品已配置了工艺路线！");
        }
        return toAjax(proRouteProductService.insertProRouteProduct(proRouteProduct));
    }

    /**
     * 修改产品制程
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:edit')")
    @Log(title = "产品制程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProRouteProduct proRouteProduct)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProductService.checkItemUnique(proRouteProduct))){
            return AjaxResult.error("此产品已配置了工艺路线！");
        }
        return toAjax(proRouteProductService.updateProRouteProduct(proRouteProduct));
    }

    /**
     * 更改产品的生产路线
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:edit')")
    @Log(title = "产品制程", businessType = BusinessType.UPDATE)
    @PutMapping("/move")
    public AjaxResult move(@RequestBody ProRouteProduct proRouteProduct){
        ProRouteProduct param = new ProRouteProduct();
        param.setItemId(proRouteProduct.getItemId());
        param.setRouteId(proRouteProduct.getRouteId());
        List<ProRouteProduct> products = proRouteProductService.selectProRouteProductList(param);
        int ret =1;
        if(CollUtil.isNotEmpty(products)){
            ProRouteProduct product = products.get(0);
            product.setRouteId(proRouteProduct.getRouteId());
            ret =proRouteProductService.updateProRouteProduct(product);
        }
        return toAjax(ret);
    }


    /**
     * 删除产品制程
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:remove')")
    @Log(title = "产品制程", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        for (Long recordId:recordIds
             ) {
            ProRouteProduct product = proRouteProductService.selectProRouteProductByRecordId(recordId);
            ProRouteProductBom bom = new ProRouteProductBom();
            bom.setRouteId(product.getRouteId());
            bom.setProductId(product.getItemId());
            proRouteProductBomService.deleteByRouteIdAndProductId(bom);
        }

        return toAjax(proRouteProductService.deleteProRouteProductByRecordIds(recordIds));
    }
}
