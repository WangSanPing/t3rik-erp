package com.t3rik.mes.wm.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmOutsourceIssue;
import com.t3rik.mes.wm.domain.WmOutsourceIssueLine;
import com.t3rik.mes.wm.domain.tx.OutsourceIssueTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmOutsourceIssueLineService;
import com.t3rik.mes.wm.service.IWmOutsourceIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 外协领料单头Controller
 *
 * @author yinjinlu
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/mes/wm/outsourceissue")
public class WmOutsourceIssueController extends BaseController {
    @Autowired
    private IWmOutsourceIssueService wmOutsourceIssueService;

    @Autowired
    private IWmOutsourceIssueLineService wmOutsourceIssueLineService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询外协领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmOutsourceIssue wmOutsourceIssue) {
        startPage();
        List<WmOutsourceIssue> list = wmOutsourceIssueService.selectWmOutsourceIssueList(wmOutsourceIssue);
        return getDataTable(list);
    }

    /**
     * 导出外协领料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:export')")
    @Log(title = "外协领料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmOutsourceIssue wmOutsourceIssue) {
        List<WmOutsourceIssue> list = wmOutsourceIssueService.selectWmOutsourceIssueList(wmOutsourceIssue);
        ExcelUtil<WmOutsourceIssue> util = new ExcelUtil<WmOutsourceIssue>(WmOutsourceIssue.class);
        util.exportExcel(response, list, "外协领料单头数据");
    }

    /**
     * 获取外协领料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:query')")
    @GetMapping(value = "/{issueId}")
    public AjaxResult getInfo(@PathVariable("issueId") Long issueId) {
        return AjaxResult.success(wmOutsourceIssueService.selectWmOutsourceIssueByIssueId(issueId));
    }

    /**
     * 新增外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:add')")
    @Log(title = "外协领料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmOutsourceIssue wmOutsourceIssue) {
        return toAjax(wmOutsourceIssueService.insertWmOutsourceIssue(wmOutsourceIssue));
    }

    /**
     * 修改外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:edit')")
    @Log(title = "外协领料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmOutsourceIssue wmOutsourceIssue) {
        return toAjax(wmOutsourceIssueService.updateWmOutsourceIssue(wmOutsourceIssue));
    }

    /**
     * 删除外协领料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:remove')")
    @Log(title = "外协领料单头", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{issueIds}")
    public AjaxResult remove(@PathVariable Long[] issueIds) {
        for (Long issueId : issueIds) {
            wmOutsourceIssueLineService.deleteWmOutsourceIssueLineByIssueId(issueId);
        }
        return toAjax(wmOutsourceIssueService.deleteWmOutsourceIssueByIssueIds(issueIds));
    }

    /**
     * 执行出库
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:outsourceissue:edit')")
    @Log(title = "外协领料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{issueId}")
    public AjaxResult execute(@PathVariable Long issueId) {
        WmOutsourceIssue header = wmOutsourceIssueService.selectWmOutsourceIssueByIssueId(issueId);
        WmOutsourceIssueLine param = new WmOutsourceIssueLine();
        param.setIssueId(issueId);
        List<WmOutsourceIssueLine> lines = wmOutsourceIssueLineService.selectWmOutsourceIssueLineList(param);
        if (CollUtil.isEmpty(lines)) {
            return AjaxResult.error("请指定领出的物资");
        }

        List<OutsourceIssueTxBean> beans = wmOutsourceIssueService.getTxBeans(issueId);

        storageCoreService.processOutsourceIssue(beans);
        // 更新单据状态
        header.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmOutsourceIssueService.updateWmOutsourceIssue(header);
        return AjaxResult.success();
    }
}
