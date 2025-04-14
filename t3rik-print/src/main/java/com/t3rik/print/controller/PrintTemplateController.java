package com.t3rik.print.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.print.domain.PrintTemplate;
import com.t3rik.print.service.IPrintTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 打印模板配置Controller
 *
 * @author skyyan
 */
@RestController
@RequestMapping("/print/template")
public class PrintTemplateController extends BaseController
{
    @Autowired
    private IPrintTemplateService printTemplateService;

    /**
     * 查询打印模板配置列表
     */
    @PreAuthorize("@ss.hasPermi('print:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(PrintTemplate printTemplate)
    {
        startPage();
        List<PrintTemplate> list = printTemplateService.selectPrintTemplateList(printTemplate);
        return getDataTable(list);
    }

    /**
     * 导出打印模板配置列表
     */
    @PreAuthorize("@ss.hasPermi('print:template:export')")
    @Log(title = "打印模板配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PrintTemplate printTemplate)
    {
        List<PrintTemplate> list = printTemplateService.selectPrintTemplateList(printTemplate);
        ExcelUtil<PrintTemplate> util = new ExcelUtil<PrintTemplate>(PrintTemplate.class);
        util.exportExcel(response, list, "打印模板配置数据");
    }

    /**
     * 获取打印模板配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('print:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return AjaxResult.success(printTemplateService.selectPrintTemplateByTemplateId(templateId));
    }

    /**
     * 新增打印模板配置
     */
    @PreAuthorize("@ss.hasPermi('print:template:add')")
    @Log(title = "打印模板配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PrintTemplate printTemplate)
    {
        return printTemplateService.insertPrintTemplate(printTemplate);
    }

    /**
     * 修改打印模板配置
     */
    @PreAuthorize("@ss.hasPermi('print:template:edit')")
    @Log(title = "打印模板配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PrintTemplate printTemplate)
    {
        return toAjax(printTemplateService.updatePrintTemplate(printTemplate));
    }

    /**
     * 删除打印模板配置
     */
    @PreAuthorize("@ss.hasPermi('print:template:remove')")
    @Log(title = "打印模板配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(printTemplateService.deletePrintTemplateByTemplateIds(templateIds));
    }

    /**
     * 根据打印模板类型获取打印模板配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('print:template:query')")
    @GetMapping(value = "/templateType/{templateType}")
    public AjaxResult getInfo(@PathVariable("templateType") String templateType)
    {
        PrintTemplate printTemplate = new PrintTemplate();
        printTemplate.setTemplateType(templateType);
        List<PrintTemplate> printTemplates = printTemplateService.selectPrintTemplateList(printTemplate);
        if (printTemplates != null && printTemplates.size() > 0) {
            return AjaxResult.success(printTemplates.get(0));
        } else {
            return AjaxResult.error("打印模板不存在");
        }
    }
}
