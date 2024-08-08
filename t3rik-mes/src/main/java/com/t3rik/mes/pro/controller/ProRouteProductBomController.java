package com.t3rik.mes.pro.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProRouteProductBom;
import com.t3rik.mes.pro.service.IProRouteProductBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品制程物料BOMController
 * 
 * @author yinjinlu
 * @date 2022-09-12
 */
@RestController
@RequestMapping("/mes/pro/routeproductbom")
public class ProRouteProductBomController extends BaseController
{
    @Autowired
    private IProRouteProductBomService proRouteProductBomService;

    /**
     * 查询产品制程物料BOM列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProRouteProductBom proRouteProductBom)
    {
        startPage();
        List<ProRouteProductBom> list = proRouteProductBomService.selectProRouteProductBomList(proRouteProductBom);
        return getDataTable(list);
    }

    /**
     * 导出产品制程物料BOM列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:export')")
    @Log(title = "产品制程物料BOM", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProRouteProductBom proRouteProductBom)
    {
        List<ProRouteProductBom> list = proRouteProductBomService.selectProRouteProductBomList(proRouteProductBom);
        ExcelUtil<ProRouteProductBom> util = new ExcelUtil<ProRouteProductBom>(ProRouteProductBom.class);
        util.exportExcel(response, list, "产品制程物料BOM数据");
    }

    /**
     * 获取产品制程物料BOM详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proRouteProductBomService.selectProRouteProductBomByRecordId(recordId));
    }

    /**
     * 新增产品制程物料BOM
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:add')")
    @Log(title = "产品制程物料BOM", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProRouteProductBom proRouteProductBom)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProductBomService.checkUnique(proRouteProductBom))){
            return AjaxResult.error("当前BOM物料在此工序已经配置过！");
        }
        return toAjax(proRouteProductBomService.insertProRouteProductBom(proRouteProductBom));
    }

    /**
     * 修改产品制程物料BOM
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:edit')")
    @Log(title = "产品制程物料BOM", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProRouteProductBom proRouteProductBom)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProductBomService.checkUnique(proRouteProductBom))){
            return AjaxResult.error("当前BOM物料在此工序已经配置过！");
        }
        return toAjax(proRouteProductBomService.updateProRouteProductBom(proRouteProductBom));
    }

    /**
     * 删除产品制程物料BOM
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:remove')")
    @Log(title = "产品制程物料BOM", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proRouteProductBomService.deleteProRouteProductBomByRecordIds(recordIds));
    }
}
