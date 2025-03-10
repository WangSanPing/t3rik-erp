package com.t3rik.mobile.mes.service

import com.t3rik.mes.wm.domain.WmRtIssue


/**
 * 退料service
 * @author t3rik
 * @date 2025/3/9 22:45
 */
interface IRtIssueService {

    /**
     * 新增退料
     */
    fun rtIssue(rtIssue: WmRtIssue)
}