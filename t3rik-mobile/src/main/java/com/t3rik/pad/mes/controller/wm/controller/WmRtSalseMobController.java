package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.domain.MdClient;
import com.t3rik.mes.md.service.IMdClientService;
import com.t3rik.mes.wm.domain.WmRtSalse;
import com.t3rik.mes.wm.domain.WmRtSalseLine;
import com.t3rik.mes.wm.domain.tx.RtSalseTxBean;
import com.t3rik.mes.wm.service.IStorageCoreService;
import com.t3rik.mes.wm.service.IWmRtSalseLineService;
import com.t3rik.mes.wm.service.IWmRtSalseService;
import com.t3rik.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("销售退货")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/rtsalse")
public class WmRtSalseMobController extends BaseController {

    @Autowired
    private IWmRtSalseService wmRtSalseService;

    @Autowired
    private IWmRtSalseLineService wmRtSalseLineService;

    @Autowired
    private IStorageCoreService storageCoreService;

    @Autowired
    private IMdClientService mdClientService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    /**
     * 查询产品销售退货单列表
     */
    @ApiOperation("查询销售退货单列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmRtSalse wmRtSalse)
    {
        startPage();
        List<WmRtSalse> list = wmRtSalseService.selectWmRtSalseList(wmRtSalse);
        return getDataTable(list);
    }


    /**
     * 获取产品销售退货单详细信息
     */
    @ApiOperation("获取销售退货单详细信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:query')")
    @GetMapping(value = "/{rtId}")
    public AjaxResult getInfo(@PathVariable("rtId") Long rtId)
    {
        return AjaxResult.success(wmRtSalseService.selectWmRtSalseByRtId(rtId));
    }

    /**
     * 新增产品销售退货单
     */
    @ApiOperation("新增销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:add')")
    @Log(title = "产品销售退货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmRtSalse wmRtSalse)
    {
        if(StringUtils.isNotNull(wmRtSalse.getRtCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmRtSalseService.checkUnique(wmRtSalse))){
                return AjaxResult.error("退货单号已存在!");
            }
        }else {
            wmRtSalse.setRtCode(autoCodeUtil.genSerialCode(UserConstants.RTSALSE_CODE,""));
        }

        if(StringUtils.isNotNull(wmRtSalse.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmRtSalse.getClientId());
            wmRtSalse.setClientCode(client.getClientCode());
            wmRtSalse.setClientName(client.getClientName());
            wmRtSalse.setClientNick(client.getClientNick());
        }

        wmRtSalse.setCreateBy(getUsername());
        wmRtSalseService.insertWmRtSalse(wmRtSalse);
        return AjaxResult.success(wmRtSalse);
    }

    /**
     * 修改产品销售退货单
     */
    @ApiOperation("编辑销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:edit')")
    @Log(title = "产品销售退货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmRtSalse wmRtSalse)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmRtSalseService.checkUnique(wmRtSalse))){
            return AjaxResult.error("退货单号已存在!");
        }

        if(StringUtils.isNotNull(wmRtSalse.getClientId())){
            MdClient client = mdClientService.selectMdClientByClientId(wmRtSalse.getClientId());
            wmRtSalse.setClientCode(client.getClientCode());
            wmRtSalse.setClientName(client.getClientName());
            wmRtSalse.setClientNick(client.getClientNick());
        }

        return toAjax(wmRtSalseService.updateWmRtSalse(wmRtSalse));
    }

    /**
     * 删除产品销售退货单
     */
    @ApiOperation("删除销售退货单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:remove')")
    @Log(title = "产品销售退货单", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{rtIds}")
    public AjaxResult remove(@PathVariable Long[] rtIds)
    {
        for (Long rtId: rtIds
        ) {
            WmRtSalse rtSalse = wmRtSalseService.selectWmRtSalseByRtId(rtId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(rtSalse.getStatus())){
                return  AjaxResult.error("只能删除草稿状态单据");
            }

            wmRtSalseLineService.deleteByRtId(rtId);
        }

        return toAjax(wmRtSalseService.deleteWmRtSalseByRtIds(rtIds));
    }

    /**
     * 执行退货
     * @param rtId
     * @return
     */
    @ApiOperation("执行销售退货接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:rtsalse:edit')")
    @Log(title = "产品销售退货单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{rtId}")
    public AjaxResult execute(@PathVariable Long rtId){
        WmRtSalse rtSalse = wmRtSalseService.selectWmRtSalseByRtId(rtId);
        WmRtSalseLine param = new WmRtSalseLine();
        param.setRtId(rtId);
        List<WmRtSalseLine> lines = wmRtSalseLineService.selectWmRtSalseLineList(param);
        if(CollectionUtils.isEmpty(lines)){
            return AjaxResult.error("请添加退货单行信息！");
        }

        List<RtSalseTxBean> beans = wmRtSalseService.getTxBeans(rtId);

        storageCoreService.processRtSalse(beans);

        rtSalse.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmRtSalseService.updateWmRtSalse(rtSalse);
        return AjaxResult.success();
    }
}
