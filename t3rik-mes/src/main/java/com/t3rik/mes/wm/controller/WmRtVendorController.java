package com.t3rik.mes.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.wm.domain.WmRtVendor;
import com.t3rik.mes.wm.domain.tx.RtVendorTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmRtVendorLineService;
import com.t3rik.mes.wm.service.IWmRtVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 供应商退货Controller
 * 
 * @author yinjinlu
 * @date 2022-06-13
 */
@RestController
@RequestMapping("/mes/wm/rtvendor")
public class WmRtVendorController extends BaseController
{
    @Autowired
    private IWmRtVendorService wmRtVendorService;

    @Autowired
    private IWmRtVendorLineService wmRtVendorLineService;

    @Autowired
    private IStorageCoreService storageCoreService;
    /**
     * 查询供应商退货列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtVendor wmRtVendor)
    {
        startPage();
        List<WmRtVendor> list = wmRtVendorService.selectWmRtVendorList(wmRtVendor);
        return getDataTable(list);
    }

    /**
     * 导出供应商退货列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:export')")
    @Log(title = "供应商退货", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmRtVendor wmRtVendor)
    {
        List<WmRtVendor> list = wmRtVendorService.selectWmRtVendorList(wmRtVendor);
        ExcelUtil<WmRtVendor> util = new ExcelUtil<WmRtVendor>(WmRtVendor.class);
        util.exportExcel(response, list, "供应商退货数据");
    }

    /**
     * 获取供应商退货详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId)
    {
        return AjaxResult.success(wmRtVendorService.selectWmRtVendorByRtId(rtId));
    }

    /**
     * 新增供应商退货
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:add')")
    @Log(title = "供应商退货", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtVendor wmRtVendor)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtVendorService.checkCodeUnique(wmRtVendor))){
            return AjaxResult.error("退货单编号已经存在！");
        }
        wmRtVendor.setCreateBy(getUsername());
        return toAjax(wmRtVendorService.insertWmRtVendor(wmRtVendor));
    }

    /**
     * 修改供应商退货
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:edit')")
    @Log(title = "供应商退货", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtVendor wmRtVendor)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtVendorService.checkCodeUnique(wmRtVendor))){
            return AjaxResult.error("退货单编号已经存在！");
        }
        return toAjax(wmRtVendorService.updateWmRtVendor(wmRtVendor));
    }

    /**
     * 删除供应商退货
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:remove')")
    @Log(title = "供应商退货", businessType = BusinessType.DELETE)
	@Transactional
    @DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds)
    {
        for (Long rtId:rtIds
             ) {
            WmRtVendor rtVendor = wmRtVendorService.selectWmRtVendorByRtId(rtId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(rtVendor.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            wmRtVendorLineService.deleteByRtId(rtId);
        }
        return toAjax(wmRtVendorService.deleteWmRtVendorByRtIds(rtIds));
    }

    /**
     * 执行退货
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:rtvendor:edit')")
    @Log(title = "供应商退货单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{rtId}")
    public AjaxResult execute(@PathVariable Long rtId){
        //判断单据状态
        WmRtVendor wmRtVendor = wmRtVendorService.selectWmRtVendorByRtId(rtId);

        //构造事务Bean
        List<RtVendorTxBean> beans = wmRtVendorService.getTxBeans(rtId);

        //调用库存核心
        storageCoreService.processRtVendor(beans);

        //更新单据状态
        WmRtVendor rtVendor = wmRtVendorService.selectWmRtVendorByRtId(rtId);
        rtVendor.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmRtVendorService.updateWmRtVendor(rtVendor);

        return AjaxResult.success();
    }


}
