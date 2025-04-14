package com.t3rik.mes.md.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.md.domain.MdProductSip;
import com.t3rik.mes.md.service.IMdProductSipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品SIPController
 * 
 * @author yinjinlu
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/mes/md/sip")
public class MdProductSipController extends BaseController
{
    @Autowired
    private IMdProductSipService mdProductSipService;

    /**
     * 查询产品SIP列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdProductSip mdProductSip)
    {
        startPage();
        List<MdProductSip> list = mdProductSipService.selectMdProductSipList(mdProductSip);
        return getDataTable(list);
    }

    /**
     * 导出产品SIP列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:export')")
    @Log(title = "产品SIP", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProductSip mdProductSip)
    {
        List<MdProductSip> list = mdProductSipService.selectMdProductSipList(mdProductSip);
        ExcelUtil<MdProductSip> util = new ExcelUtil<MdProductSip>(MdProductSip.class);
        util.exportExcel(response, list, "产品SIP数据");
    }

    /**
     * 获取产品SIP详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:query')")
    @GetMapping(value = "/{sipId}")
    public AjaxResult getInfo(@PathVariable("sipId") Long sipId)
    {
        return AjaxResult.success(mdProductSipService.selectMdProductSipBySipId(sipId));
    }

    /**
     * 新增产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:add')")
    @Log(title = "产品SIP", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProductSip mdProductSip)
    {
        return toAjax(mdProductSipService.insertMdProductSip(mdProductSip));
    }

    /**
     * 修改产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:edit')")
    @Log(title = "产品SIP", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProductSip mdProductSip)
    {
        return toAjax(mdProductSipService.updateMdProductSip(mdProductSip));
    }

    /**
     * 删除产品SIP
     */
    @PreAuthorize("@ss.hasPermi('mes:md:mditem:remove')")
    @Log(title = "产品SIP", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sipIds}")
    public AjaxResult remove(@PathVariable Long[] sipIds)
    {
        return toAjax(mdProductSipService.deleteMdProductSipBySipIds(sipIds));
    }
}
