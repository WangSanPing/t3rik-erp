package com.t3rik.mes.pro.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProProcess;
import com.t3rik.mes.pro.domain.ProRouteProcess;
import com.t3rik.mes.pro.domain.ProRouteProduct;
import com.t3rik.mes.pro.service.IProProcessService;
import com.t3rik.mes.pro.service.IProRouteProcessService;
import com.t3rik.mes.pro.service.IProRouteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工艺组成Controller
 * 
 * @author yinjinlu
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/mes/pro/routeprocess")
public class ProRouteProcessController extends BaseController
{
    @Autowired
    private IProRouteProcessService proRouteProcessService;

    @Autowired
    private IProRouteProductService proRouteProductService;

    @Autowired
    private IProProcessService proProcessService;

    /**
     * 查询工艺组成列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProRouteProcess proRouteProcess)
    {
        startPage();
        List<ProRouteProcess> list = proRouteProcessService.selectProRouteProcessList(proRouteProcess);
        return getDataTable(list);
    }


    /**
     * 查询指定产品的工艺组成
     * @return
     */
    @GetMapping("/listProductProcess/{productId}")
    public AjaxResult listProductProcess(@PathVariable("productId") Long productId){
        ProRouteProduct proRouteProduct = new ProRouteProduct();
        proRouteProduct.setItemId(productId);
        List<ProRouteProduct> products = proRouteProductService.selectProRouteProductList(proRouteProduct);
        if(CollUtil.isNotEmpty(products)){
            ProRouteProduct product = products.get(0);
            ProRouteProcess param = new ProRouteProcess();
            param.setRouteId(product.getRouteId());
            return AjaxResult.success(proRouteProcessService.selectProRouteProcessList(param));
        }else {
            return AjaxResult.error("当前产品未配置工艺路线！");
        }
    }


    /**
     * 导出工艺组成列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:export')")
    @Log(title = "工艺组成", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProRouteProcess proRouteProcess)
    {
        List<ProRouteProcess> list = proRouteProcessService.selectProRouteProcessList(proRouteProcess);
        ExcelUtil<ProRouteProcess> util = new ExcelUtil<ProRouteProcess>(ProRouteProcess.class);
        util.exportExcel(response, list, "工艺组成数据");
    }

    /**
     * 获取工艺组成详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(proRouteProcessService.selectProRouteProcessByRecordId(recordId));
    }

    /**
     * 新增工艺组成
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:add')")
    @Log(title = "工艺组成", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProRouteProcess proRouteProcess)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkOrderNumExists(proRouteProcess))){
            return AjaxResult.error("序号已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkProcessExists(proRouteProcess))){
            return AjaxResult.error("不能重复添加工序！");
        }
        if(UserConstants.YES.equals(proRouteProcess.getKeyFlag()) && UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkUpdateFlagUnique(proRouteProcess))){
            return AjaxResult.error("当前工艺路线已经指定过关键工序");
        }
        ProProcess process = proProcessService.selectProProcessByProcessId(proRouteProcess.getProcessId());
        proRouteProcess.setProcessCode(process.getProcessCode());
        proRouteProcess.setProcessName(process.getProcessName());

        //更新上一个工序的nextProcess
        ProRouteProcess preProcess = proRouteProcessService.findPreProcess(proRouteProcess);
        if(StringUtils.isNotNull(preProcess)){
            preProcess.setNextProcessId(proRouteProcess.getProcessId());
            preProcess.setNextProcessCode(proRouteProcess.getProcessCode());
            preProcess.setNextProcessName(proRouteProcess.getProcessName());
            proRouteProcessService.updateProRouteProcess(preProcess);
        }

        //设置当前工序的nextProcess
        ProRouteProcess nextProcess = proRouteProcessService.findNextProcess(proRouteProcess);
        if(StringUtils.isNotNull(nextProcess)){
            proRouteProcess.setNextProcessId(nextProcess.getProcessId());
            proRouteProcess.setNextProcessCode(nextProcess.getProcessCode());
            proRouteProcess.setNextProcessName(nextProcess.getProcessName());
        }else{
            proRouteProcess.setNextProcessId(0L);
            proRouteProcess.setNextProcessName("无");
        }

        return toAjax(proRouteProcessService.insertProRouteProcess(proRouteProcess));
    }

    /**
     * 修改工艺组成
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:edit')")
    @Log(title = "工艺组成", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProRouteProcess proRouteProcess)
    {
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkOrderNumExists(proRouteProcess))){
            return AjaxResult.error("序号已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkProcessExists(proRouteProcess))){
            return AjaxResult.error("不能重复添加工序！");
        }
        if(UserConstants.YES.equals(proRouteProcess.getKeyFlag()) && UserConstants.NOT_UNIQUE.equals(proRouteProcessService.checkUpdateFlagUnique(proRouteProcess))){
            return AjaxResult.error("当前工艺路线已经指定过关键工序");
        }
        ProProcess process = proProcessService.selectProProcessByProcessId(proRouteProcess.getProcessId());
        proRouteProcess.setProcessCode(process.getProcessCode());
        proRouteProcess.setProcessName(process.getProcessName());

        //更新上一个工序的nextProcess
        ProRouteProcess preProcess = proRouteProcessService.findPreProcess(proRouteProcess);
        if(StringUtils.isNotNull(preProcess)){
            preProcess.setNextProcessId(proRouteProcess.getProcessId());
            preProcess.setNextProcessCode(proRouteProcess.getProcessCode());
            preProcess.setNextProcessName(proRouteProcess.getProcessName());
            proRouteProcessService.updateProRouteProcess(preProcess);
        }

        //设置当前工序的nextProcess
        ProRouteProcess nextProcess = proRouteProcessService.findNextProcess(proRouteProcess);
        if(StringUtils.isNotNull(nextProcess)){
            proRouteProcess.setNextProcessId(nextProcess.getProcessId());
            proRouteProcess.setNextProcessCode(nextProcess.getProcessCode());
            proRouteProcess.setNextProcessName(nextProcess.getProcessName());
        }else{
            proRouteProcess.setNextProcessId(0L);
            proRouteProcess.setNextProcessName("无");
        }

        return toAjax(proRouteProcessService.updateProRouteProcess(proRouteProcess));
    }

    /**
     * 删除工艺组成
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:proroute:remove')")
    @Log(title = "工艺组成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(proRouteProcessService.deleteProRouteProcessByRecordIds(recordIds));
    }
}
