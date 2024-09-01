package com.t3rik.mobile.mes.service

import com.t3rik.mobile.mes.dto.IssueRequestDTO


/**
 * 领料申请
 * @author t3rik
 * @date 2024/9/1 13:30
 */
interface IIssueService {
    /**
     * 领料申请
     */
    fun issue(issueRequestDTO: IssueRequestDTO)
}