package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/work")
class WorkController : BaseController() {

}