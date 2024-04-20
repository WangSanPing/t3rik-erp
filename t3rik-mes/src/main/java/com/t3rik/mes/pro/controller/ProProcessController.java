package com.t3rik.mes.pro.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProProcess;
import com.t3rik.mes.pro.service.IProProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生产工序Controller
 * 
 * @author yinjinlu
 * @date 2022-05-11
 */
@RestController
@RequestMapping("/mes/pro/process")
public class ProProcessController extends BaseController
{
    @Autowired
    private IProProcessService proProcessService;

    /**
     * 查询生产工序列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProProcess proProcess)
    {
        startPage();
        List<ProProcess> list = proProcessService.selectProProcessList(proProcess);
        return getDataTable(list);
    }

    /**
     * 查询所有可用工序的清单
     * @return
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(){
        ProProcess process = new ProProcess();
        process.setEnableFlag("Y");
        List<ProProcess> list =proProcessService.selectProProcessList(process);
        return AjaxResult.success(list);
    }

    /**
     * 导出生产工序列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:export')")
    @Log(title = "生产工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProProcess proProcess)
    {
        List<ProProcess> list = proProcessService.selectProProcessList(proProcess);
        ExcelUtil<ProProcess> util = new ExcelUtil<ProProcess>(ProProcess.class);
        util.exportExcel(response, list, "生产工序数据");
    }

    /**
     * 获取生产工序详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:query')")
    @GetMapping(value = "/{processId}")
    public AjaxResult getInfo(@PathVariable("processId") Long processId)
    {
        return AjaxResult.success(proProcessService.selectProProcessByProcessId(processId));
    }

    /**
     * 新增生产工序
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:add')")
    @Log(title = "生产工序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProProcess proProcess)
    {
        if(UserConstants.NOT_UNIQUE.equals(proProcessService.checkProcessCodeUnique(proProcess))){
            return AjaxResult.error("工序编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(proProcessService.checkProcessNameUnique(proProcess))){
            return AjaxResult.error("工序名称已存在！");
        }
        return toAjax(proProcessService.insertProProcess(proProcess));
    }

    /**
     * 修改生产工序
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:edit')")
    @Log(title = "生产工序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProProcess proProcess)
    {
        if(UserConstants.NOT_UNIQUE.equals(proProcessService.checkProcessCodeUnique(proProcess))){
            return AjaxResult.error("工序编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(proProcessService.checkProcessNameUnique(proProcess))){
            return AjaxResult.error("工序名称已存在！");
        }
        return toAjax(proProcessService.updateProProcess(proProcess));
    }

    /**
     * 删除生产工序
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:remove')")
    @Log(title = "生产工序", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processIds}")
    public AjaxResult remove(@PathVariable Long[] processIds)
    {
        return toAjax(proProcessService.deleteProProcessByProcessIds(processIds));
    }
}
