package com.t3rik.mes.wm.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmRtIssue;
import com.t3rik.mes.wm.domain.WmRtIssueLine;
import com.t3rik.mes.wm.domain.tx.RtIssueTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmRtIssueLineService;
import com.t3rik.mes.wm.service.IWmRtIssueService;
import com.t3rik.mes.wm.utils.WmWarehouseUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生产退料单头Controller
 *
 * @author yinjinlu
 * @date 2022-09-15
 */
@RestController
@RequestMapping("/mes/wm/rtissue")
public class WmRtIssueController extends BaseController {
    @Resource
    private IWmRtIssueService wmRtIssueService;

    @Resource
    private IWmRtIssueLineService wmRtIssueLineService;

    @Resource
    private IStorageCoreService storageCoreService;

    @Resource
    private WmWarehouseUtil warehouseUtil;

    /**
     * 查询生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtIssue wmRtIssue) {
        startPage();
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        return getDataTable(list);
    }

    /**
     * 导出生产退料单头列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:export')")
    @Log(title = "生产退料单头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtIssue wmRtIssue) {
        List<WmRtIssue> list = wmRtIssueService.selectWmRtIssueList(wmRtIssue);
        ExcelUtil<WmRtIssue> util = new ExcelUtil<WmRtIssue>(WmRtIssue.class);
        util.exportExcel(response, list, "生产退料单头数据");
    }

    /**
     * 获取生产退料单头详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId) {
        return AjaxResult.success(wmRtIssueService.selectWmRtIssueByRtId(rtId));
    }

    /**
     * 新增生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:add')")
    @Log(title = "生产退料单头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtIssue wmRtIssue) {
        if (UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))) {
            return AjaxResult.error("退料单编号已存在");
        }
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmRtIssue);
        wmRtIssue.setCreateBy(getUsername());
        return toAjax(wmRtIssueService.insertWmRtIssue(wmRtIssue));
    }

    /**
     * 修改生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtIssue wmRtIssue) {
        if (UserConstants.NOT_UNIQUE.equals(wmRtIssueService.checkUnique(wmRtIssue))) {
            return AjaxResult.error("退料单编号已存在");
        }
        // 设置仓库信息
        this.warehouseUtil.setWarehouseInfo(wmRtIssue);
        return toAjax(wmRtIssueService.updateWmRtIssue(wmRtIssue));
    }

    /**
     * 删除生产退料单头
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:remove')")
    @Log(title = "生产退料单头", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds) {
        for (Long rtId : rtIds) {
            wmRtIssueLineService.deleteByRtId(rtId);
        }
        return toAjax(wmRtIssueService.deleteWmRtIssueByRtIds(rtIds));
    }

    /**
     * 执行退料
     *
     * @param rtId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtissue:edit')")
    @Log(title = "生产退料单头", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{rtId}")
    public AjaxResult execute(@PathVariable Long rtId) {
        // 查询生产退料单头
        WmRtIssue rtIssue = wmRtIssueService.selectWmRtIssueByRtId(rtId);
        WmRtIssueLine param = new WmRtIssueLine();
        param.setRtId(rtId);
        // 查询生产退料行信息
        List<WmRtIssueLine> lines = wmRtIssueLineService.selectWmRtIssueLineList(param);
        if (CollUtil.isEmpty(lines)) {
            return AjaxResult.error("请选择要退料的物资");
        }
        // 查询退料信息所对应的库存记录信息
        List<RtIssueTxBean> beans = wmRtIssueService.getTxBeans(rtId);

        // 执行生产退料
        storageCoreService.processRtIssue(beans);

        // 修改状态成已完成
        rtIssue.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmRtIssueService.updateWmRtIssue(rtIssue);
        return AjaxResult.success();
    }

}
