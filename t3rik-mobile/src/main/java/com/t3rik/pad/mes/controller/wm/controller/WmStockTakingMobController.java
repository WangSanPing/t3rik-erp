package com.t3rik.pad.mes.controller.wm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmStockTaking;
import com.t3rik.mes.wm.domain.WmStockTakingLine;
import com.t3rik.mes.wm.domain.WmWarehouse;
import com.t3rik.mes.wm.service.IWmStockTakingLineService;
import com.t3rik.mes.wm.service.IWmStockTakingResultService;
import com.t3rik.mes.wm.service.IWmStockTakingService;
import com.t3rik.mes.wm.service.IWmWarehouseService;
import com.t3rik.system.strategy.AutoCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存盘点记录Controller
 *
 * @author yinjinlu
 * @date 2023-08-17
 */
@Api("物资盘点")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/wm/stocktaking")
public class WmStockTakingMobController extends BaseController
{
    @Autowired
    private IWmStockTakingService wmStockTakingService;

    @Autowired
    private IWmStockTakingLineService wmStockTakingLineService;

    @Autowired
    private IWmStockTakingResultService wmStockTakingResultService;

    @Autowired
    private AutoCodeUtil autoCodeUtil;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    /**
     * 查询库存盘点记录列表
     */
    @ApiOperation("查询库存盘点单列表接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmStockTaking wmStockTaking)
    {
        startPage();
        List<WmStockTaking> list = wmStockTakingService.selectWmStockTakingList(wmStockTaking);
        return getDataTable(list);
    }

    /**
     * 获取库存盘点记录详细信息
     */
    @ApiOperation("获取库存盘点单详情接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:query')")
    @GetMapping(value = "/{takingId}")
    public AjaxResult getInfo(@PathVariable("takingId") Long takingId)
    {
        return AjaxResult.success(wmStockTakingService.selectWmStockTakingByTakingId(takingId));
    }

    /**
     * 新增库存盘点记录
     */
    @ApiOperation("新增库存盘点单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:add')")
    @Log(title = "库存盘点记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmStockTaking wmStockTaking)
    {
        if(StringUtils.isNotNull(wmStockTaking.getTakingCode())){
            if(UserConstants.NOT_UNIQUE.equals(wmStockTakingService.checkUnique(wmStockTaking))){
                return AjaxResult.error("单据编号已存在!");
            }
        }else {
            wmStockTaking.setTakingCode(autoCodeUtil.genSerialCode(UserConstants.STOCKTAKING_CODE,""));
        }

        if(StringUtils.isNotNull(wmStockTaking.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmStockTaking.getWarehouseId());
            wmStockTaking.setWarehouseCode(warehouse.getWarehouseCode());
            wmStockTaking.setWarehouseName(warehouse.getWarehouseName());
        }

        wmStockTakingService.insertWmStockTaking(wmStockTaking);
        wmStockTaking.setCreateBy(getUsername());
        return AjaxResult.success(wmStockTaking);
    }

    /**
     * 修改库存盘点记录
     */
    @ApiOperation("编辑库存盘点单基本信息接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:edit')")
    @Log(title = "库存盘点记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmStockTaking wmStockTaking)
    {
        if(StringUtils.isNotNull(wmStockTaking.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmStockTaking.getWarehouseId());
            wmStockTaking.setWarehouseCode(warehouse.getWarehouseCode());
            wmStockTaking.setWarehouseName(warehouse.getWarehouseName());
        }
        return toAjax(wmStockTakingService.updateWmStockTaking(wmStockTaking));
    }

    /**
     * 删除库存盘点记录
     */
    @ApiOperation("删除库存盘点单接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:remove')")
    @Log(title = "库存盘点记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{takingIds}")
    public AjaxResult remove(@PathVariable Long[] takingIds)
    {
        return toAjax(wmStockTakingService.deleteWmStockTakingByTakingIds(takingIds));
    }

    /**
     * 完成盘点，系统对比计算盘点结果
     */
    @ApiOperation("执行盘点接口")
    @PreAuthorize("@ss.hasPermi('mes:wm:stocktaking:edit')")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{takingId}")
    public AjaxResult execute(@PathVariable Long takingId){
        WmStockTaking taking = wmStockTakingService.selectWmStockTakingByTakingId(takingId);

        WmStockTakingLine param = new WmStockTakingLine();
        param.setTakingId(takingId);
        List<WmStockTakingLine> lines = wmStockTakingLineService.selectWmStockTakingLineList(param);
        if(CollectionUtils.isEmpty(lines)){
            return AjaxResult.error("未检测到盘点的物资！");
        }

        //先删除历史记录
        wmStockTakingResultService.deleteWmStockTakingResultByTakingId(takingId);

        if(UserConstants.WM_STOCK_TAKING_TYPE_OPEN.equals(taking.getTakingType())){
            //如果是明盘，则直接对比明细中的库存数量和盘点数量
            wmStockTakingResultService.calculateOpenWmStockTakingResult(takingId);
        }else {
            //如果是盲盘，则对比盘点明细中的盘点数量，和当前库存现有量的数量
            wmStockTakingResultService.calculateWmStockTakingResult(takingId);
        }

        taking.setStatus(UserConstants.ORDER_STATUS_APPROVED);
        wmStockTakingService.updateWmStockTaking(taking);
        return AjaxResult.success();
    }
}
