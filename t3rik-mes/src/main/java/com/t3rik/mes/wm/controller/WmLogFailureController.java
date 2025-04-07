package com.t3rik.mes.wm.controller;

import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.wm.domain.WmLogFailure;
import com.t3rik.mes.wm.service.IWmLogFailureService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 日志写入失败记录Controller
 *
 * @author t3rik
 * @date 2025-04-02
 */
@RestController
@RequestMapping("/wm/wm-log-failure")
public class WmLogFailureController extends BaseController {
    @Resource
    private IWmLogFailureService wmLogFailureService;

    /**
     * 查询日志写入失败记录列表
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmLogFailure wmLogFailure) {
        // 获取查询条件
        LambdaQueryWrapper<WmLogFailure> queryWrapper = getQueryWrapper(wmLogFailure);
        // 组装分页
        Page<WmLogFailure> page = getMPPage(wmLogFailure);
        // 查询
        this.wmLogFailureService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出日志写入失败记录列表
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:export')")
    @Log(title = "日志写入失败记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmLogFailure wmLogFailure) {
        // 获取查询条件
        LambdaQueryWrapper<WmLogFailure> queryWrapper = getQueryWrapper(wmLogFailure);
        List<WmLogFailure> list = this.wmLogFailureService.list(queryWrapper);
        ExcelUtil<WmLogFailure> util = new ExcelUtil<WmLogFailure>(WmLogFailure. class);
        util.exportExcel(response, list, "日志写入失败记录数据");
    }

    /**
     * 获取日志写入失败记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:query')")
    @GetMapping(value = "/{failureId}")
    public AjaxResult getInfo(@PathVariable("failureId") Long failureId) {
        return AjaxResult.success(this.wmLogFailureService.getById(failureId));
    }

    /**
     * 新增日志写入失败记录
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:add')")
    @Log(title = "日志写入失败记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmLogFailure wmLogFailure) {
        return toAjax(this.wmLogFailureService.save(wmLogFailure));
    }

    /**
     * 修改日志写入失败记录
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:edit')")
    @Log(title = "日志写入失败记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmLogFailure wmLogFailure) {
        return toAjax(this.wmLogFailureService.updateById(wmLogFailure));
    }

    /**
     * 删除日志写入失败记录
     */
    @PreAuthorize("@ss.hasPermi('wm:wmlogfailure:remove')")
    @Log(title = "日志写入失败记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{failureIds}")
    public AjaxResult remove(@PathVariable List<Long> failureIds) {
        return toAjax(this.wmLogFailureService.removeByIds(failureIds));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<WmLogFailure> getQueryWrapper(WmLogFailure wmLogFailure) {
        LambdaQueryWrapper<WmLogFailure> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(wmLogFailure.getFailureTime() != null, WmLogFailure::getFailureTime, wmLogFailure.getFailureTime());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(WmLogFailure::getCreateTime);
        Map<String, Object> params = wmLogFailure.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,WmLogFailure::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
