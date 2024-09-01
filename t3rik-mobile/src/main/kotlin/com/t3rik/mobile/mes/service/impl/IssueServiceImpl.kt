package com.t3rik.mobile.mes.service.impl

import com.t3rik.mes.wm.service.IWmIssueHeaderService
import com.t3rik.mes.wm.service.IWmIssueLineService
import com.t3rik.mobile.mes.dto.IssueRequestDTO
import com.t3rik.mobile.mes.service.IIssueService
import jakarta.annotation.Resource
import org.springframework.stereotype.Service


/**
 * 领料申请
 * @author t3rik
 * @date 2024/9/1 13:30
 */
@Service
class IssueServiceImpl : IIssueService {

    @Resource
    lateinit var issueHeaderService: IWmIssueHeaderService

    @Resource
    lateinit var issueLineService: IWmIssueLineService

    /**
     * 领料申请
     */
    override fun issue(issueRequestDTO: IssueRequestDTO) {
        TODO("Not yet implemented")
    }
}