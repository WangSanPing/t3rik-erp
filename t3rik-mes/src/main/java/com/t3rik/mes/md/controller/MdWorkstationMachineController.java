package com.t3rik.mes.md.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.md.domain.MdWorkstation;
import com.t3rik.mes.md.domain.MdWorkstationMachine;
import com.t3rik.mes.md.service.IMdWorkstationMachineService;
import com.t3rik.mes.md.service.IMdWorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备资源Controller
 * 
 * @author yinjinlu
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/mes/md/workstationmachine")
public class MdWorkstationMachineController extends BaseController
{
    @Autowired
    private IMdWorkstationMachineService mdWorkstationMachineService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    /**
     * 查询设备资源列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdWorkstationMachine mdWorkstationMachine)
    {
        startPage();
        List<MdWorkstationMachine> list = mdWorkstationMachineService.selectMdWorkstationMachineList(mdWorkstationMachine);
        return getDataTable(list);
    }

    /**
     * 导出设备资源列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:export')")
    @Log(title = "设备资源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkstationMachine mdWorkstationMachine)
    {
        List<MdWorkstationMachine> list = mdWorkstationMachineService.selectMdWorkstationMachineList(mdWorkstationMachine);
        ExcelUtil<MdWorkstationMachine> util = new ExcelUtil<MdWorkstationMachine>(MdWorkstationMachine.class);
        util.exportExcel(response, list, "设备资源数据");
    }

    /**
     * 获取设备资源详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(mdWorkstationMachineService.selectMdWorkstationMachineByRecordId(recordId));
    }

    /**
     * 新增设备资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:add')")
    @Log(title = "设备资源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkstationMachine mdWorkstationMachine)
    {
        MdWorkstationMachine machine = mdWorkstationMachineService.checkMachineryExists(mdWorkstationMachine);
        if(StringUtils.isNotNull(machine)){
            MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(machine.getWorkstationId());
            return AjaxResult.error("设备已分配至工作站:"+workstation.getWorkstationName());
        }
        return toAjax(mdWorkstationMachineService.insertMdWorkstationMachine(mdWorkstationMachine));
    }

    /**
     * 修改设备资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:edit')")
    @Log(title = "设备资源", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkstationMachine mdWorkstationMachine)
    {
        return toAjax(mdWorkstationMachineService.updateMdWorkstationMachine(mdWorkstationMachine));
    }

    /**
     * 删除设备资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:remove')")
    @Log(title = "设备资源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(mdWorkstationMachineService.deleteMdWorkstationMachineByRecordIds(recordIds));
    }
}
