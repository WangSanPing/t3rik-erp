package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmTransfer;
import com.t3rik.mes.wm.domain.WmTransferLine;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.domain.tx.TransferTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmTransferLineService;
import com.t3rik.mes.wm.service.IWmTransferService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 转移单Controller
 * 
 * @author yinjinlu
 * @date 2022-11-28
 */
@RestController
@RequestMapping("/mes/wm/transfer")
public class WmTransferController extends BaseController
{
    @Autowired
    private IWmTransferService wmTransferService;

    @Autowired
    private IWmTransferLineService wmTransferLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询转移单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmTransfer wmTransfer)
    {
        startPage();
        List<WmTransfer> list = wmTransferService.selectWmTransferList(wmTransfer);
        return getDataTable(list);
    }

    /**
     * 导出转移单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:export')")
    @Log(title = "转移单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmTransfer wmTransfer)
    {
        List<WmTransfer> list = wmTransferService.selectWmTransferList(wmTransfer);
        ExcelUtil<WmTransfer> util = new ExcelUtil<WmTransfer>(WmTransfer.class);
        util.exportExcel(response, list, "转移单数据");
    }

    /**
     * 获取转移单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:query')")
    @GetMapping(value = "/{transferId}")
    public AjaxResult getInfo(@PathVariable("transferId") Long transferId)
    {
        return AjaxResult.success(wmTransferService.selectWmTransferByTransferId(transferId));
    }

    /**
     * 新增转移单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:add')")
    @Log(title = "转移单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmTransfer wmTransfer)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmTransferService.checkUnique(wmTransfer))){
            return AjaxResult.error("转移单编号已存在");
        }
        if(StringUtils.isNotNull(wmTransfer.getFromWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransfer.getFromWarehouseId());
            wmTransfer.setFromWarehouseCode(warehouse.getWarehouseCode());
            wmTransfer.setFromWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmTransfer.getToWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransfer.getToWarehouseId());
            wmTransfer.setToWarehouseCode(warehouse.getWarehouseCode());
            wmTransfer.setToWarehouseName(warehouse.getWarehouseName());
        }

        wmTransfer.setCreateBy(getUsername());
        return toAjax(wmTransferService.insertWmTransfer(wmTransfer));
    }

    /**
     * 修改转移单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:edit')")
    @Log(title = "转移单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmTransfer wmTransfer)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmTransferService.checkUnique(wmTransfer))){
            return AjaxResult.error("转移单编号已存在");
        }
        if(StringUtils.isNotNull(wmTransfer.getFromWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransfer.getFromWarehouseId());
            wmTransfer.setFromWarehouseCode(warehouse.getWarehouseCode());
            wmTransfer.setFromWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmTransfer.getToWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmTransfer.getToWarehouseId());
            wmTransfer.setToWarehouseCode(warehouse.getWarehouseCode());
            wmTransfer.setToWarehouseName(warehouse.getWarehouseName());
        }
        return toAjax(wmTransferService.updateWmTransfer(wmTransfer));
    }

    /**
     * 删除转移单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:remove')")
    @Log(title = "转移单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{transferIds}")
    @Transactional
    public AjaxResult remove(@PathVariable Long[] transferIds)
    {
        for (Long transferId:transferIds
             ) {
            wmTransferLineService.deleteByTransferId(transferId);
        }
        return toAjax(wmTransferService.deleteWmTransferByTransferIds(transferIds));
    }

    /**
     * 执行退货
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:transfer:edit')")
    @Log(title = "转移单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{transferId}")
    public AjaxResult execute(@PathVariable Long transferId){
        WmTransfer transfer = wmTransferService.selectWmTransferByTransferId(transferId);

        WmTransferLine param = new WmTransferLine();
        param.setTransferId(transferId);
        List<WmTransferLine> lines = wmTransferLineService.selectWmTransferLineList(param);
        if(CollectionUtils.isEmpty(lines)){
           return AjaxResult.error("请添加需要转移的物资！");
        }

        List<TransferTxBean> beans = wmTransferService.getTxBeans(transferId);

        if(CollectionUtils.isEmpty(beans)){
            return AjaxResult.error("请添加转移单行信息！");
        }

        storageCoreService.processTransfer(beans);


        transfer.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmTransferService.updateWmTransfer(transfer);
        return AjaxResult.success();
    }
}
