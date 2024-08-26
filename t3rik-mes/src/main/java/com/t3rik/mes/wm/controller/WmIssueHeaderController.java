package com.t3rik.mes.wm.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmIssueHeader;
import com.t3rik.mes.wm.domain.WmIssueLine;
import com.t3rik.mes.wm.service.IWmIssueHeaderService;
import com.t3rik.mes.wm.service.IWmIssueLineService;
import com.t3rik.mes.wm.utils.WmWarehouseUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 生产领料单头Controller
 * (已重构)
 *
 * @author t3rik
 * @date 2024-08-26
 */
@RestController
@RequestMapping("/mes/wm/issueheader")
public class WmIssueHeaderController extends BaseController {
    @Resource
    private IWmIssueHeaderService wmIssueHeaderService;

    @Resource
    private IWmIssueLineService wmIssueLineService;

    @Resource
    private WmWarehouseUtil warehouseUtil;

    /**
     * 查询生产领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmIssueHeader wmIssueHeader) {
        startPage();
        List<WmIssueHeader> list = wmIssueHeaderService.selectWmIssueHeaderList(wmIssueHeader);
        return getDataTable(list);
    }

    /**
     * 导出生产领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:export')")
    @Log(title = "生产领料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmIssueHeader wmIssueHeader) {
        List<WmIssueHeader> list = wmIssueHeaderService.selectWmIssueHeaderList(wmIssueHeader);
        ExcelUtil<WmIssueHeader> util = new ExcelUtil<WmIssueHeader>(WmIssueHeader.class);
        util.exportExcel(response, list, "生产领料单头数据");
    }

    /**
     * 获取生产领料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId) {
        return AjaxResult.success(wmIssueHeaderService.selectWmIssueHeaderByIssueId(issueId));
    }

    /**
     * 新增生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:add')")
    @Log(title = "生产领料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmIssueHeader wmIssueHeader) {
        if (UserConstants.NOT_UNIQUE.equals(wmIssueHeaderService.checkIssueCodeUnique(wmIssueHeader))) {
            return AjaxResult.error(MsgConstants.CODE_ALREADY_EXISTS);
        }
        // 设置仓库相关信息
        warehouseUtil.setWarehouseInfo(wmIssueHeader);
        wmIssueHeader.setCreateBy(getUsername());
        return toAjax(wmIssueHeaderService.insertWmIssueHeader(wmIssueHeader));
    }

    /**
     * 修改生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmIssueHeader wmIssueHeader) {
        if (UserConstants.NOT_UNIQUE.equals(wmIssueHeaderService.checkIssueCodeUnique(wmIssueHeader))) {
            return AjaxResult.error(MsgConstants.CODE_ALREADY_EXISTS);
        }
        // 设置仓库相关信息
        warehouseUtil.setWarehouseInfo(wmIssueHeader);
        return toAjax(wmIssueHeaderService.updateWmIssueHeader(wmIssueHeader));
    }

    /**
     * 删除生产领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:remove')")
    @Log(title = "生产领料单头", businessType = BusinessType.DELETE)
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds) {
        if (issueIds.length == 0) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR);
        }
        List<Long> ids = Arrays.stream(issueIds).toList();
        // 校验 只能删除草稿状态的单据
        List<WmIssueHeader> deleteList = this.wmIssueHeaderService.lambdaQuery()
                .in(WmIssueHeader::getIssueId, ids)
                .ne(WmIssueHeader::getStatus, OrderStatusEnum.PREPARE.getCode())
                .list();
        if (CollectionUtils.isNotEmpty(deleteList)) {
            return AjaxResult.error("只能删除草稿状态的单据!");
        }
        // 批量删除
        this.wmIssueHeaderService.removeBatchByIds(ids);
        return toAjax(wmIssueHeaderService.deleteWmIssueHeaderByIssueIds(issueIds));
    }

    /**
     * 执行出库
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:issueheader:edit')")
    @Log(title = "生产领料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{issueId}")
    public AjaxResult execute(@PathVariable Long issueId) {
        List<WmIssueLine> lines = this.wmIssueLineService.lambdaQuery().eq(WmIssueLine::getIssueId, issueId).list();
        if (CollUtil.isEmpty(lines)) {
            return AjaxResult.error("请指定领出的物资");
        }
        this.wmIssueHeaderService.execute(issueId);
        return AjaxResult.success();
    }
}
