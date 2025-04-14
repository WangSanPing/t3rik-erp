package com.t3rik.print.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.print.domain.PrintPrinterConfig;
import com.t3rik.print.service.IPrintPrinterConfigService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 打印机配置
 * @author skyyan
 */
@RestController
@RequestMapping("/print/printerconfig")
public class PrintPrinterConfigController extends BaseController
{
    @Autowired
    private IPrintPrinterConfigService printPrinterConfigService;

    /**
     * 查询打印机配置列表
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PrintPrinterConfig printPrinterConfig)
    {
        startPage();
        List<PrintPrinterConfig> list = printPrinterConfigService.selectPrintPrinterConfigList(printPrinterConfig);
        return getDataTable(list);
    }

    /**
     * 导出打印机配置列表
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:export')")
    @Log(title = "打印机配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PrintPrinterConfig printPrinterConfig)
    {
        List<PrintPrinterConfig> list = printPrinterConfigService.selectPrintPrinterConfigList(printPrinterConfig);
        ExcelUtil<PrintPrinterConfig> util = new ExcelUtil<PrintPrinterConfig>(PrintPrinterConfig.class);
        util.exportExcel(response, list, "客户订单数据");
    }

    /**
     * 获取打印机配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:query')")
    @GetMapping(value = "/{printerId}")
    public AjaxResult getInfo(@PathVariable("printerId") Long printerId)
    {
        return AjaxResult.success(printPrinterConfigService.selectPrintPrinterConfigByPrinterId(printerId));
    }

    /**
     * 新增打印机配置
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:add')")
    @Log(title = "打印机配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PrintPrinterConfig printPrinterConfig)
    {
        if(UserConstants.NOT_UNIQUE.equals(printPrinterConfigService.checkPrinterCodeUnique(printPrinterConfig))){
            return AjaxResult.error("打印机编号已存在！");
        }

        return toAjax(printPrinterConfigService.insertPrintPrinterConfig(printPrinterConfig));
    }

    /**
     * 修改打印机配置
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:edit')")
    @Log(title = "打印机配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PrintPrinterConfig printPrinterConfig)
    {
        if(UserConstants.NOT_UNIQUE.equals(printPrinterConfigService.checkPrinterCodeUnique(printPrinterConfig))){
            return AjaxResult.error("打印机编号已存在！");
        }

        return toAjax(printPrinterConfigService.updatePrintPrinterConfig(printPrinterConfig));
    }

    /**
     * 删除打印机配置
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:remove')")
    @Log(title = "打印机配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{printerIds}")
    public AjaxResult remove(@PathVariable Long[] printerIds)
    {
        return toAjax(printPrinterConfigService.deletePrintPrinterConfigByPrinterIds(printerIds));
    }

    /**
     * 查询默认打印机
     * @return
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:query')")
    @GetMapping("/getDefaultPrint/{clientId}")
    public AjaxResult getDefaultPrint(@PathVariable Long clientId) {
        return printPrinterConfigService.getDefaultPrint(clientId);
    }

    /**
     * 设置默认打印机
     * @param printPrinterConfig
     * @return
     */
    @PreAuthorize("@ss.hasPermi('print:printerconfig:query')")
    @PostMapping("/editDefaultPrint")
    public AjaxResult editDefaultPrint(@RequestBody PrintPrinterConfig printPrinterConfig) {
        return printPrinterConfigService.editDefaultPrint(printPrinterConfig);
    }
}
