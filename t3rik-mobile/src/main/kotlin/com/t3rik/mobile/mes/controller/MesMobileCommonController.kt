package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.enums.EnableFlagEnum
import com.t3rik.common.enums.YesOrNoEnum
import com.t3rik.common.enums.mes.DefaultDataEnum
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.support.ItemTypeSupport
import com.t3rik.mes.md.domain.MdItem
import com.t3rik.mes.md.domain.MdUnitMeasure
import com.t3rik.mes.md.service.IMdItemService
import com.t3rik.mes.md.service.IMdUnitMeasureService
import com.t3rik.mes.wm.domain.WmMaterialStock
import com.t3rik.mes.wm.service.IWmMaterialStockService
import com.t3rik.mobile.mes.service.IMesMobileService
import jakarta.annotation.Resource
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/common")
class MesMobileCommonController : BaseController() {

    @Resource
    lateinit var mdUnitMeasureService: IMdUnitMeasureService

    @Resource
    lateinit var mesMobileService: IMesMobileService

    @Resource
    lateinit var mdItemService: IMdItemService

    @Resource
    lateinit var materialStockService: IWmMaterialStockService


    /**
     * 获取所有单位
     */
    @GetMapping("/selectAll")
    fun selectAll(): AjaxResult {
        return AjaxResult.success(
            this.mdUnitMeasureService.lambdaQuery().eq(MdUnitMeasure::getEnableFlag, YesOrNoEnum.YES.code).list()
        )
    }

    /**
     * 获取仓库信息
     */
    @GetMapping("/getWarehouseInfo")
    fun getWarehouseInfo(): AjaxResult {
        return AjaxResult.success(
            runBlocking { mesMobileService.getWarehouseInfo() }
        )
    }

    /**
     * 获取仓库信息
     */
    @GetMapping("/listMdItemProduct/{type}")
    fun listProduct(mdItem: MdItem, @PathVariable("type") type: String?): AjaxResult {
        // 根据类型查询
        val itemTypeId =
            ItemTypeSupport.getDefaultDataIdByItemType(type) ?: throw BusinessException(MsgConstants.PARAM_ERROR)
        mdItem.itemTypeId = itemTypeId.toLong()
        mdItem.enableFlag = EnableFlagEnum.YES.code
        return AjaxResult.success(
            mdItemService.selectMdItemList(mdItem)
        )
    }

    /**
     * 获取当前物料信息及库存
     */
    @GetMapping("/materialStockList")
    fun materialStockList(): AjaxResult {
        return AjaxResult.success(
            materialStockService.lambdaQuery().notIn(
                WmMaterialStock::getWarehouseCode,
                mutableListOf(DefaultDataEnum.WASTE_VIRTUAL_WH.code, DefaultDataEnum.VIRTUAL_WH.code)
            ).list()
        )
    }
}