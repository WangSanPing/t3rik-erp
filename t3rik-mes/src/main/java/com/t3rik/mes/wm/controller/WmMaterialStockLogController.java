package com.t3rik.mes.wm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmMaterialStockLog;
import com.t3rik.mes.wm.service.IWmMaterialStockLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 库存变化日志Controller
 *
 * @author t3rik
 * @date 2025-04-02
 */
@RestController
@RequestMapping("/wm/wm-material-stock-log")
public class WmMaterialStockLogController extends BaseController {
    @Resource
    private IWmMaterialStockLogService wmMaterialStockLogService;

    /**
     * 查询库存变化日志列表
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmMaterialStockLog wmMaterialStockLog) {
        // 获取查询条件
        LambdaQueryWrapper<WmMaterialStockLog> queryWrapper = getQueryWrapper(wmMaterialStockLog);
        // 组装分页
        Page<WmMaterialStockLog> page = getMPPage(wmMaterialStockLog);
        // 查询
        this.wmMaterialStockLogService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出库存变化日志列表
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:export')")
    @Log(title = "库存变化日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmMaterialStockLog wmMaterialStockLog) {
        // 获取查询条件
        LambdaQueryWrapper<WmMaterialStockLog> queryWrapper = getQueryWrapper(wmMaterialStockLog);
        List<WmMaterialStockLog> list = this.wmMaterialStockLogService.list(queryWrapper);
        ExcelUtil<WmMaterialStockLog> util = new ExcelUtil<WmMaterialStockLog>(WmMaterialStockLog.class);
        util.exportExcel(response, list, "库存变化日志数据");
    }

    /**
     * 获取库存变化日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId) {
        return AjaxResult.success(this.wmMaterialStockLogService.getById(logId));
    }

    /**
     * 新增库存变化日志
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:add')")
    @Log(title = "库存变化日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmMaterialStockLog wmMaterialStockLog) {
        return toAjax(this.wmMaterialStockLogService.save(wmMaterialStockLog));
    }

    /**
     * 修改库存变化日志
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:edit')")
    @Log(title = "库存变化日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmMaterialStockLog wmMaterialStockLog) {
        return toAjax(this.wmMaterialStockLogService.updateById(wmMaterialStockLog));
    }

    /**
     * 删除库存变化日志
     */
    @PreAuthorize("@ss.hasPermi('wm:wmmaterialstocklog:remove')")
    @Log(title = "库存变化日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable List<Long> logIds) {
        return toAjax(this.wmMaterialStockLogService.removeByIds(logIds));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<WmMaterialStockLog> getQueryWrapper(WmMaterialStockLog wmMaterialStockLog) {
        LambdaQueryWrapper<WmMaterialStockLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(wmMaterialStockLog.getItemCode()), WmMaterialStockLog::getItemCode, wmMaterialStockLog.getItemCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmMaterialStockLog.getItemName()), WmMaterialStockLog::getItemName, wmMaterialStockLog.getItemName());
        queryWrapper.eq(wmMaterialStockLog.getChangeType() != null, WmMaterialStockLog::getChangeType, wmMaterialStockLog.getChangeType());
        queryWrapper.eq(StringUtils.isNotEmpty(wmMaterialStockLog.getSourceDocCode()), WmMaterialStockLog::getSourceDocCode, wmMaterialStockLog.getSourceDocCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmMaterialStockLog.getSourceDocName()), WmMaterialStockLog::getSourceDocName, wmMaterialStockLog.getSourceDocName());
        queryWrapper.eq(StringUtils.isNotEmpty(wmMaterialStockLog.getSourceDocType()), WmMaterialStockLog::getSourceDocType, wmMaterialStockLog.getSourceDocType());
        queryWrapper.eq(StringUtils.isNotEmpty(wmMaterialStockLog.getWorkorderCode()), WmMaterialStockLog::getWorkorderCode, wmMaterialStockLog.getWorkorderCode());
        queryWrapper.like(StringUtils.isNotEmpty(wmMaterialStockLog.getWorkorderName()), WmMaterialStockLog::getWorkorderName, wmMaterialStockLog.getWorkorderName());
        queryWrapper.like(StringUtils.isNotEmpty(wmMaterialStockLog.getOperationBy()), WmMaterialStockLog::getOperationBy, wmMaterialStockLog.getOperationBy());
        queryWrapper.eq(wmMaterialStockLog.getOperationTime() != null, WmMaterialStockLog::getOperationTime, wmMaterialStockLog.getOperationTime());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(WmMaterialStockLog::getCreateTime);
        Map<String, Object> params = wmMaterialStockLog.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, WmMaterialStockLog::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
