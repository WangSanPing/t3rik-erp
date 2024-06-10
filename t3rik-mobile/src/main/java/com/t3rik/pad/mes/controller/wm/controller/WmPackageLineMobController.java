package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.wm.domain.WmPackageLine;
import com.t3rik.mes.wm.service.IWmPackageLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("装箱单明细")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/packageline")
public class WmPackageLineMobController extends BaseController {
    @Autowired
    private IWmPackageLineService wmPackageLineService;

    /**
     * 查询装箱明细列表
     */
    @ApiOperation("查询装箱单明细信息列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:package:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmPackageLine wmPackageLine)
    {
        startPage();
        List<WmPackageLine> list = wmPackageLineService.selectWmPackageLineList(wmPackageLine);
        return getDataTable(list);
    }


    /**
     * 获取装箱明细详细信息
     */
    @ApiOperation("获取装箱单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:package:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(wmPackageLineService.selectWmPackageLineByLineId(lineId));
    }

    /**
     * 新增装箱明细
     */
    @ApiOperation("新增装箱单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:package:add')")
    @Log(title = "装箱明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmPackageLine wmPackageLine)
    {
        return toAjax(wmPackageLineService.insertWmPackageLine(wmPackageLine));
    }

    /**
     * 修改装箱明细
     */
    @ApiOperation("修改装箱单明细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:package:edit')")
    @Log(title = "装箱明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmPackageLine wmPackageLine)
    {
        return toAjax(wmPackageLineService.updateWmPackageLine(wmPackageLine));
    }

    /**
     * 删除装箱明细
     */
    @ApiOperation("删除装箱单明细")
    @PreAuthorize("@ss.hasPermi('mes:wm:package:remove')")
    @Log(title = "装箱明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(wmPackageLineService.deleteWmPackageLineByLineIds(lineIds));
    }
}
