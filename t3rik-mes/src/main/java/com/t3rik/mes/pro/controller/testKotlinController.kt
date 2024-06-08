package com.t3rik.mes.pro.controller

import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.mes.pro.domain.ProRoute
import com.t3rik.mes.pro.domain.ProTask
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test/kotlin")
class testKotlinController {

    /**
     * 查询生产任务列表
     */
    @GetMapping("/list")
    fun list(proTask: ProTask?): String {
        return "123"
    }

}