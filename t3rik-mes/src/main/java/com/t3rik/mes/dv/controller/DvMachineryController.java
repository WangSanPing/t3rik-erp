package com.t3rik.mes.dv.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.SecurityUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.dv.domain.DvMachinery;
import com.t3rik.mes.dv.service.IDvMachineryService;
import com.t3rik.mes.wm.utils.WmBarCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备Controller
 * 
 * @author yinjinlu
 * @date 2022-05-08
 */
@RestController
@RequestMapping("/mes/dv/machinery")
public class DvMachineryController extends BaseController
{
    @Autowired
    private IDvMachineryService dvMachineryService;

    @Autowired
    private WmBarCodeUtil wmBarCodeUtil;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DvMachinery dvMachinery)
    {
        startPage();
        List<DvMachinery> list = dvMachineryService.selectDvMachineryList(dvMachinery);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:export')")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvMachinery dvMachinery)
    {
        List<DvMachinery> list = dvMachineryService.selectDvMachineryList(dvMachinery);
        ExcelUtil<DvMachinery> util = new ExcelUtil<DvMachinery>(DvMachinery.class);
        util.exportExcel(response, list, "设备数据");
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:query')")
    @GetMapping(value = "/{machineryId}")
    public AjaxResult getInfo(@PathVariable("machineryId") Long machineryId)
    {
        return AjaxResult.success(dvMachineryService.selectDvMachineryByMachineryId(machineryId));
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:add')")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvMachinery dvMachinery)
    {
        dvMachineryService.insertDvMachinery(dvMachinery);
        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_MACHINERY,dvMachinery.getMachineryId(),dvMachinery.getMachineryCode(),dvMachinery.getMachineryName());
        return AjaxResult.success(dvMachinery.getMachineryId());
    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvMachinery dvMachinery)
    {
        return toAjax(dvMachineryService.updateDvMachinery(dvMachinery));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:remove')")
    @Log(title = "设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{machineryIds}")
    public AjaxResult remove(@PathVariable Long[] machineryIds)
    {
        return toAjax(dvMachineryService.deleteDvMachineryByMachineryIds(machineryIds));
    }

    /**
     * 依据上传的文件批量更新或新增设备
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:machinery:import')")
    @Log(title = "设备", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file,
                                 @RequestParam(name = "updateSupport", defaultValue = "false") boolean updateSupport) throws Exception {
        ExcelUtil<DvMachinery> util = new ExcelUtil<>(DvMachinery.class);
        List<DvMachinery> dvMachineryList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = dvMachineryService.importMachinery(dvMachineryList, updateSupport, operName);
        return AjaxResult.success("导入成功，共导入 " + message + " 条数据");
    }

}
