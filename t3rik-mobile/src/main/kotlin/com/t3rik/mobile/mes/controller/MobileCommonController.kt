package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.enums.YesOrNoEnum
import com.t3rik.mes.md.domain.MdUnitMeasure
import com.t3rik.mes.md.service.IMdUnitMeasureService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/common")
class MobileCommonController : BaseController() {

    @Resource
    lateinit var mdUnitMeasureService: IMdUnitMeasureService


    @GetMapping("/selectAll")
    fun selectAll(): AjaxResult {
        return AjaxResult.success(
            this.mdUnitMeasureService.lambdaQuery().eq(MdUnitMeasure::getEnableFlag, YesOrNoEnum.YES.code).list()
        )
    }
}