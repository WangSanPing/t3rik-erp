package com.t3rik.mes.md.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.md.domain.MdProductSop;
import com.t3rik.mes.md.service.IMdProductSopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品SOPController
 * 
 * @author yinjinlu
 * @date 2022-07-26
 */
@RestController
@RequestMapping("/mes/md/sop")
public class MdProductSopController extends BaseController
{
    @Autowired
    private IMdProductSopService mdProductSopService;

    /**
     * 查询产品SOP列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdProductSop mdProdutSop)
    {
        startPage();
        List<MdProductSop> list = mdProductSopService.selectMdProductSopList(mdProdutSop);
        return getDataTable(list);
    }

    /**
     * 导出产品SOP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品SOP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProductSop mdProdutSop)
    {
        List<MdProductSop> list = mdProductSopService.selectMdProductSopList(mdProdutSop);
        ExcelUtil<MdProductSop> util = new ExcelUtil<MdProductSop>(MdProductSop.class);
        util.exportExcel(response, list, "产品SOP数据");
    }

    /**
     * 获取产品SOP详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{sopId}")
    public AjaxResult getInfo(@PathVariable("sopId") Long sopId)
    {
        return AjaxResult.success(mdProductSopService.selectMdProductSopBySopId(sopId));
    }

    /**
     * 新增产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品SOP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProductSop mdProdutSop)
    {
        return toAjax(mdProductSopService.insertMdProductSop(mdProdutSop));
    }

    /**
     * 修改产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品SOP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProductSop mdProdutSop)
    {
        return toAjax(mdProductSopService.updateMdProductSop(mdProdutSop));
    }

    /**
     * 删除产品SOP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品SOP", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sopIds}")
    public AjaxResult remove(@PathVariable Long[] sopIds)
    {
        return toAjax(mdProductSopService.deleteMdProductSopBySopIds(sopIds));
    }
}
