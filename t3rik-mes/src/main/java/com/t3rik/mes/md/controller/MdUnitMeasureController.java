package com.t3rik.mes.md.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.SecurityUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdUnitMeasure;
import com.t3rik.mes.md.service.IMdUnitMeasureService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 单位Controller
 *
 * @author ruoyi
 * @date 2022-04-27
 */
@RestController
@RequestMapping("/mes/md/unitmeasure")
public class MdUnitMeasureController extends BaseController {
    @Resource
    private IMdUnitMeasureService mdUnitMeasureService;
    @Resource
    private IAsyncService asyncService;

    /**
     * 查询单位列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdUnitMeasure mdUnitMeasure) {
        startPage();
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        return getDataTable(list);
    }

    @GetMapping("/listprimary")
    public AjaxResult listPrimary() {
        MdUnitMeasure mdUnitMeasure = new MdUnitMeasure();
        mdUnitMeasure.setPrimaryFlag("Y");
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        return AjaxResult.success(list);
    }

    @GetMapping("/selectall")
    public AjaxResult selectAll() {
        MdUnitMeasure mdUnitMeasure = new MdUnitMeasure();
        mdUnitMeasure.setEnableFlag("Y");
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        return AjaxResult.success(list);
    }

    /**
     * 导出单位列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:unitmeasure:export')")
    @Log(title = "单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdUnitMeasure mdUnitMeasure) {
        List<MdUnitMeasure> list = mdUnitMeasureService.selectMdUnitMeasureList(mdUnitMeasure);
        ExcelUtil<MdUnitMeasure> util = new ExcelUtil<MdUnitMeasure>(MdUnitMeasure.class);
        util.exportExcel(response, list, "单位数据");
    }

    /**
     * 获取单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:unitmeasure:query')")
    @GetMapping(value = "/{measureId}")
    public AjaxResult getInfo(@PathVariable("measureId") Long measureId) {
        return AjaxResult.success(mdUnitMeasureService.selectMdUnitMeasureByMeasureId(measureId));
    }

    /**
     * 新增单位
     */
    @PreAuthorize("@ss.hasPermi('mes:md:unitmeasure:add')")
    @Log(title = "单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdUnitMeasure mdUnitMeasure) {
        int i = mdUnitMeasureService.insertMdUnitMeasure(mdUnitMeasure);
        // 异步更新缓存
        this.asyncService.updateUnitMeasuresToRedis();
        return toAjax(i);
    }

    /**
     * 修改单位
     */
    @PreAuthorize("@ss.hasPermi('mes:md:unitmeasure:edit')")
    @Log(title = "单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdUnitMeasure mdUnitMeasure) {
        // 非管理员不允许操作
        if (!DefaultDataEnum.ADMIN.getCode().equals(SecurityUtils.getUserId().toString())) {
            throw new BusinessException(MsgConstants.NO_OPERATION_AUTH);
        }
        MdUnitMeasure unitMeasure = this.mdUnitMeasureService.getById(mdUnitMeasure.getMeasureId());
        Optional.ofNullable(unitMeasure).orElseThrow(() -> new BusinessException(MsgConstants.PARAM_ERROR));
        int i = mdUnitMeasureService.updateMdUnitMeasure(mdUnitMeasure);
        // 异步更新缓存
        this.asyncService.updateUnitMeasuresToRedis(Boolean.TRUE, List.of(unitMeasure.getMeasureName()));
        return toAjax(i);
    }

    /**
     * 删除单位
     */
    @PreAuthorize("@ss.hasPermi('mes:md:unitmeasure:remove')")
    @Log(title = "单位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{measureIds}")
    public AjaxResult remove(@PathVariable Long[] measureIds) {
        // 非管理员不允许操作
        if (!DefaultDataEnum.ADMIN.getCode().equals(SecurityUtils.getUserId().toString())) {
            throw new BusinessException(MsgConstants.NO_OPERATION_AUTH);
        }
        List<MdUnitMeasure> mdUnitMeasures = this.mdUnitMeasureService.listByIds(List.of(measureIds));
        if (CollectionUtils.isEmpty(mdUnitMeasures)) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR);
        }
        int i = mdUnitMeasureService.deleteMdUnitMeasureByMeasureIds(measureIds);
        // 异步更新缓存
        List<String> keys = mdUnitMeasures.stream().map(MdUnitMeasure::getMeasureCode).toList();
        this.asyncService.updateUnitMeasuresToRedis(Boolean.TRUE, keys);
        return toAjax(i);
    }
}
