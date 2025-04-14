package com.t3rik.print.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.print.domain.PrintClient;
import com.t3rik.print.service.IPrintClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author skyyan
 */
@RestController
@RequestMapping("/print/client")
public class PrintClientController extends BaseController {

    @Autowired
    private IPrintClientService clientService;

    /**
     * 查询打印机配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PrintClient client) {
        startPage();
        List<PrintClient> list = clientService.getClientList(client);
        return getDataTable(list);
    }

    /**
     * 打印机客户端新增
     * @param client
     * @return
     */
    @Log(title = "打印机客户端配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PrintClient client) {
        return clientService.insertClient(client);
    }

    /**
     * 打印机客户端详情
     * @param clientId
     * @return
     */
    @GetMapping("/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") Long clientId) {
        return clientService.selectClientById(clientId);
    }

    /**
     * 打印机客户端修改
     * @param client
     * @return
     */
    @Log(title = "客户端配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PrintClient client) {
        return clientService.updateClient(client);
    }

    /**
     * 删除客户端配置
     * @param clientIds
     * @return
     */
    @Log(title = "客户端配置", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{clientIds}")
    public AjaxResult remove(@PathVariable String clientIds) {
        return clientService.remove(clientIds);
    }

    @GetMapping("/getWorkshopAndWorkstation")
    public AjaxResult getWorkshopAndWorkstation() {
        return clientService.getWorkshopAndWorkstation();
    }

    @GetMapping("/gatAll")
    public AjaxResult gatAll() {
        return clientService.getAll();
    }
}
