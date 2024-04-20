package com.t3rik.mes.md.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品BOM关系Controller
 * 
 * @author yinjinlu
 * @date 2022-05-09
 */
@RestController
@RequestMapping("/mes/md/bom")
public class MdProductBomController extends BaseController
{
    @Autowired
    private IMdProductBomService mdProductBomService;

    /**
     * 查询产品BOM关系列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdProductBom mdProductBom)
    {
        startPage();
        List<MdProductBom> list = mdProductBomService.selectMdProductBomList(mdProductBom);
        return getDataTable(list);
    }

    /**
     * 导出产品BOM关系列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品BOM关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProductBom mdProductBom)
    {
        List<MdProductBom> list = mdProductBomService.selectMdProductBomList(mdProductBom);
        ExcelUtil<MdProductBom> util = new ExcelUtil<MdProductBom>(MdProductBom.class);
        util.exportExcel(response, list, "产品BOM关系数据");
    }

    /**
     * 获取产品BOM关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{bomId}")
    public AjaxResult getInfo(@PathVariable("bomId") Long bomId)
    {
        return AjaxResult.success(mdProductBomService.selectMdProductBomByBomId(bomId));
    }

    /**
     * 新增产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品BOM关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProductBom mdProductBom)
    {
        if(mdProductBom.getBomItemId() == mdProductBom.getItemId()){
            return  AjaxResult.error("产品不能作为自身的BOM物料！");
        }

        return toAjax(mdProductBomService.insertMdProductBom(mdProductBom));
    }

    /**
     * 修改产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品BOM关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProductBom mdProductBom)
    {
        return toAjax(mdProductBomService.updateMdProductBom(mdProductBom));
    }

    /**
     * 删除产品BOM关系
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品BOM关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bomIds}")
    public AjaxResult remove(@PathVariable Long[] bomIds)
    {
        return toAjax(mdProductBomService.deleteMdProductBomByBomIds(bomIds));
    }
}
