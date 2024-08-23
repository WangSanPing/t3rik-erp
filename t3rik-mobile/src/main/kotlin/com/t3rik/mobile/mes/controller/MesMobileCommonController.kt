package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.enums.YesOrNoEnum
import com.t3rik.mes.md.domain.MdUnitMeasure
import com.t3rik.mes.md.service.IMdUnitMeasureService
import com.t3rik.mobile.mes.service.IMesMobileService
import jakarta.annotation.Resource
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/common")
class MesMobileCommonController : BaseController() {

    @Resource
    lateinit var mdUnitMeasureService: IMdUnitMeasureService

    @Resource
    lateinit var mesMobileService: IMesMobileService


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
}