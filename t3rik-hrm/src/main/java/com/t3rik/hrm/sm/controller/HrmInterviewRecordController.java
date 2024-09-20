package com.t3rik.hrm.sm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.service.IHrmInterviewRecordService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 面试记录Controller
 *
 * @author t3rik
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/sm/hrm-interview-record")
public class HrmInterviewRecordController extends BaseController {

    @Resource
    private IHrmInterviewRecordService hrmInterviewRecordService;

    /**
     * 查询面试记录列表
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(HrmInterviewRecord hrmInterviewRecord) {
        // 获取查询条件
        LambdaQueryWrapper<HrmInterviewRecord> queryWrapper = getQueryWrapper(hrmInterviewRecord);
        // 组装分页
        Page<HrmInterviewRecord> page = getMPPage(hrmInterviewRecord);
        // 查询
        this.hrmInterviewRecordService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出面试记录列表
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:export')")
    @Log(title = "面试记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HrmInterviewRecord hrmInterviewRecord) {
        // 获取查询条件
        LambdaQueryWrapper<HrmInterviewRecord> queryWrapper = getQueryWrapper(hrmInterviewRecord);
        List<HrmInterviewRecord> list = this.hrmInterviewRecordService.list(queryWrapper);
        ExcelUtil<HrmInterviewRecord> util = new ExcelUtil<HrmInterviewRecord>(HrmInterviewRecord.class);
        util.exportExcel(response, list, "面试记录数据");
    }

    /**
     * 获取面试记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:query')")
    @GetMapping(value = "/{interviewRecordId}")
    public AjaxResult getInfo(@PathVariable("interviewRecordId") Long interviewRecordId) {
        return AjaxResult.success(this.hrmInterviewRecordService.getById(interviewRecordId));
    }

    /**
     * 新增面试记录
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:add')")
    @Log(title = "面试记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HrmInterviewRecord hrmInterviewRecord) {
        return toAjax(this.hrmInterviewRecordService.save(hrmInterviewRecord));
    }

    /**
     * 修改面试记录
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:edit')")
    @Log(title = "面试记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HrmInterviewRecord hrmInterviewRecord) {
        return toAjax(this.hrmInterviewRecordService.updateById(hrmInterviewRecord));
    }

    /**
     * 删除面试记录
     */
    @PreAuthorize("@ss.hasPermi('sm:hrminterviewrecord:remove')")
    @Log(title = "面试记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{interviewRecordIds}")
    public AjaxResult remove(@PathVariable List<Long> interviewRecordIds) {
        return toAjax(this.hrmInterviewRecordService.removeByIds(interviewRecordIds));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<HrmInterviewRecord> getQueryWrapper(HrmInterviewRecord hrmInterviewRecord) {
        LambdaQueryWrapper<HrmInterviewRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(hrmInterviewRecord.getStaffCode() != null, HrmInterviewRecord::getStaffCode, hrmInterviewRecord.getStaffCode());
        queryWrapper.like(StringUtils.isNotEmpty(hrmInterviewRecord.getStaffName()), HrmInterviewRecord::getStaffName, hrmInterviewRecord.getStaffName());
        queryWrapper.like(StringUtils.isNotEmpty(hrmInterviewRecord.getInterviewerName()), HrmInterviewRecord::getInterviewerName, hrmInterviewRecord.getInterviewerName());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(HrmInterviewRecord::getCreateTime);
        Map<String, Object> params = hrmInterviewRecord.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, HrmInterviewRecord::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
