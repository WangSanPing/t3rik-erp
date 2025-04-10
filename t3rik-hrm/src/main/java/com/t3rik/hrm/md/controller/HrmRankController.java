package com.t3rik.hrm.md.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.hrm.md.domain.HrmRank;
import com.t3rik.hrm.md.service.IHrmRankService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 职级管理Controller
 *
 * @author t3rik
 * @date 2024-07-23
 */
@RestController
@RequestMapping("/md/hrm-rank")
public class HrmRankController extends BaseController {
    @Autowired
    private IHrmRankService hrmRankService;

    /**
     * 查询职级管理列表
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:list')")
    @GetMapping("/list")
    public TableDataInfo list(HrmRank hrmRank) {
        // 获取查询条件
        LambdaQueryWrapper<HrmRank> queryWrapper = getQueryWrapper(hrmRank);
        // 组装分页
        Page<HrmRank> page = getMPPage(hrmRank);
        // 查询
        this.hrmRankService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出职级管理列表
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:export')")
    @Log(title = "职级管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HrmRank hrmRank) {
        // 获取查询条件
        LambdaQueryWrapper<HrmRank> queryWrapper = getQueryWrapper(hrmRank);
        List<HrmRank> list = this.hrmRankService.list(queryWrapper);
        ExcelUtil<HrmRank> util = new ExcelUtil<HrmRank>(HrmRank. class);
        util.exportExcel(response, list, "职级管理数据");
    }

    /**
     * 获取职级管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:query')")
    @GetMapping(value = "/{rankId}")
    public AjaxResult getInfo(@PathVariable("rankId") Long rankId) {
        return AjaxResult.success(this.hrmRankService.getById(rankId));
    }

    /**
     * 新增职级管理
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:add')")
    @Log(title = "职级管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HrmRank hrmRank) {
        return toAjax(this.hrmRankService.save(hrmRank));
    }

    /**
     * 修改职级管理
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:edit')")
    @Log(title = "职级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HrmRank hrmRank) {
        return toAjax(this.hrmRankService.updateById(hrmRank));
    }

    /**
     * 删除职级管理
     */
    @PreAuthorize("@ss.hasPermi('md:hrmrank:remove')")
    @Log(title = "职级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{rankIds}")
    public AjaxResult remove(@PathVariable List<Long> rankIds) {
        return toAjax(this.hrmRankService.lambdaUpdate().in(HrmRank::getRankId,rankIds).set(HrmRank::getDeleted,Boolean.TRUE).set(HrmRank::getDeleteAt,new Date()).update());
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<HrmRank> getQueryWrapper(HrmRank hrmRank) {
        LambdaQueryWrapper<HrmRank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(hrmRank.getRankCode() != null, HrmRank::getRankCode, hrmRank.getRankCode());
        queryWrapper.like(StringUtils.isNotEmpty(hrmRank.getRankType()),HrmRank::getRankType,hrmRank.getRankType());
        queryWrapper.like(StringUtils.isNotEmpty(hrmRank.getRankName()), HrmRank::getRankName, hrmRank.getRankName());
        queryWrapper.ge(hrmRank.getSalaryRangeMin() != null,HrmRank::getSalaryRangeMin,hrmRank.getSalaryRangeMin());
        queryWrapper.le(hrmRank.getSalaryRangeMax() != null,HrmRank::getSalaryRangeMax, hrmRank.getSalaryRangeMax());
        // 默认创建时间倒序
        queryWrapper.orderByAsc(HrmRank::getRankCode);
        Map<String, Object> params = hrmRank.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,HrmRank::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
