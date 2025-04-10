package com.t3rik.mes.md.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.t3rik.common.enums.system.StatusEnum;
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
import com.t3rik.mes.md.domain.MdProductColor;
import com.t3rik.mes.md.service.IMdProductColorService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 颜色Controller
 *
 * @author t3rik
 * @date 2024-08-29
 */
@RestController
@RequestMapping("/md_color/color")
public class MdProductColorController extends BaseController {
    @Autowired
    private IMdProductColorService mdProductColorService;

    /**
     * 查询颜色列表
     */
    // @PreAuthorize("@ss.hasPermi('md_color:color:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdProductColor mdProductColor) {
        // 获取查询条件
        LambdaQueryWrapper<MdProductColor> queryWrapper = getQueryWrapper(mdProductColor);
        // 组装分页
        Page<MdProductColor> page = getMPPage(mdProductColor);
        // 查询
        this.mdProductColorService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }
    /**
     * 查询颜色列表
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:totalList')")
    @GetMapping("/totalList")
    public AjaxResult totalList(MdProductColor mdProductColor) {
        mdProductColor.setStatus(StatusEnum.ENABLE);
        return AjaxResult.success(this.mdProductColorService.list());
    }

    /**
     * 导出颜色列表
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:export')")
    @Log(title = "颜色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProductColor mdProductColor) {
        // 获取查询条件
        LambdaQueryWrapper<MdProductColor> queryWrapper = getQueryWrapper(mdProductColor);
        List<MdProductColor> list = this.mdProductColorService.list(queryWrapper);
        ExcelUtil<MdProductColor> util = new ExcelUtil<MdProductColor>(MdProductColor. class);
        util.exportExcel(response, list, "颜色数据");
    }

    /**
     * 获取颜色详细信息
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(this.mdProductColorService.getById(id));
    }

    /**
     * 新增颜色
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:add')")
    @Log(title = "颜色", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProductColor mdProductColor) {
        return toAjax(this.mdProductColorService.save(mdProductColor));
    }

    /**
     * 修改颜色
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:edit')")
    @Log(title = "颜色", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProductColor mdProductColor) {
        return toAjax(this.mdProductColorService.updateById(mdProductColor));
    }

    /**
     * 删除颜色
     */
    @PreAuthorize("@ss.hasPermi('md_color:color:remove')")
    @Log(title = "颜色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<String> ids) {
        return toAjax(this.mdProductColorService.removeByIds(ids));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<MdProductColor> getQueryWrapper(MdProductColor mdProductColor) {
        LambdaQueryWrapper<MdProductColor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(mdProductColor.getColorCode()), MdProductColor::getColorCode, mdProductColor.getColorCode());
        queryWrapper.like(StringUtils.isNotEmpty(mdProductColor.getColorName()), MdProductColor::getColorName, mdProductColor.getColorName());
        queryWrapper.like(StringUtils.isNotEmpty(mdProductColor.getRemark()), MdProductColor::getRemark, mdProductColor.getRemark());
        Optional.ofNullable(mdProductColor.getStatus()).ifPresent(status -> queryWrapper.eq( MdProductColor::getStatus, mdProductColor.getStatus().getCode()));
        // 默认创建时间倒序
        queryWrapper.orderByDesc(MdProductColor::getCreateTime);
        Map<String, Object> params = mdProductColor.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,MdProductColor::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
