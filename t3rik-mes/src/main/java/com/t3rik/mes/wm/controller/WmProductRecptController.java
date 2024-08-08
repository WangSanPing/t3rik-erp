package com.t3rik.mes.wm.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmProductRecpt;
import com.t3rik.mes.wm.domain.WmProductRecptLine;
import com.t3rik.mes.wm.domain.tx.ProductRecptTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmProductRecptLineService;
import com.t3rik.mes.wm.service.IWmProductRecptService;
import com.t3rik.mes.wm.utils.WmWarehouseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品入库录Controller
 *
 * @author yinjinlu
 * @date 2022-09-22
 */
@RestController
@RequestMapping("/mes/wm/productrecpt")
public class WmProductRecptController extends BaseController {
    @Autowired
    private IWmProductRecptService wmProductRecptService;

    @Resource
    private IWmProductRecptLineService wmProductRecptLineService;

    @Resource
    private IStorageCoreService storageCoreService;

    @Resource
    private WmWarehouseUtil warehouseUtil;

    /**
     * 查询产品入库录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmProductRecpt wmProductRecpt) {
        startPage();
        List<WmProductRecpt> list = wmProductRecptService.selectWmProductRecptList(wmProductRecpt);
        return getDataTable(list);
    }

    /**
     * 导出产品入库录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:export')")
    @Log(title = "产品入库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmProductRecpt wmProductRecpt) {
        List<WmProductRecpt> list = wmProductRecptService.selectWmProductRecptList(wmProductRecpt);
        ExcelUtil<WmProductRecpt> util = new ExcelUtil<WmProductRecpt>(WmProductRecpt.class);
        util.exportExcel(response, list, "产品入库录数据");
    }

    /**
     * 获取产品入库录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:query')")
    @GetMapping(value = "/{recptId}")
    public AjaxResult getInfo(@PathVariable("recptId") Long recptId) {
        return AjaxResult.success(wmProductRecptService.selectWmProductRecptByRecptId(recptId));
    }

    /**
     * 新增产品入库录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:add')")
    @Log(title = "产品入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmProductRecpt wmProductRecpt) {
        if (UserConstants.NOT_UNIQUE.equals(wmProductRecptService.checkUnique(wmProductRecpt))) {
            return AjaxResult.error("入库单编号已存在！");
        }
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmProductRecpt);
        wmProductRecpt.setCreateBy(getUsername());
        return toAjax(wmProductRecptService.insertWmProductRecpt(wmProductRecpt));
    }

    /**
     * 修改产品入库录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:edit')")
    @Log(title = "产品入库记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmProductRecpt wmProductRecpt) {
        if (UserConstants.NOT_UNIQUE.equals(wmProductRecptService.checkUnique(wmProductRecpt))) {
            return AjaxResult.error("入库单编号已存在！");
        }
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmProductRecpt);
        return toAjax(wmProductRecptService.updateWmProductRecpt(wmProductRecpt));
    }

    /**
     * 删除产品入库录
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:remove')")
    @Log(title = "产品入库记录", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{recptIds}")
    public AjaxResult remove(@PathVariable Long[] recptIds) {
        for (Long recptId : recptIds) {
            wmProductRecptLineService.deleteByRecptId(recptId);
        }
        return toAjax(wmProductRecptService.deleteWmProductRecptByRecptIds(recptIds));
    }

    /**
     * 执行入库
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:productrecpt:edit')")
    @Log(title = "产品入库记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{recptId}")
    public AjaxResult execute(@PathVariable Long recptId) {
        WmProductRecpt recpt = wmProductRecptService.selectWmProductRecptByRecptId(recptId);

        WmProductRecptLine param = new WmProductRecptLine();
        param.setRecptId(recptId);
        List<WmProductRecptLine> lines = wmProductRecptLineService.selectWmProductRecptLineList(param);
        if (CollUtil.isEmpty(lines)) {
            return AjaxResult.error("请添加要入库的产品");
        }

        List<ProductRecptTxBean> beans = wmProductRecptService.getTxBean(recptId);
        storageCoreService.processProductRecpt(beans);

        recpt.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmProductRecptService.updateWmProductRecpt(recpt);

        return AjaxResult.success();
    }

}
