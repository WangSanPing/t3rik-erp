package com.t3rik.mobile.mes.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.t3rik.mes.wm.domain.WmIssueHeader
import com.t3rik.mes.wm.domain.WmIssueLine
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor


/**
 * 领料单
 * @author t3rik
 * @date 2024/8/30 15:31
 */
data class IssueRequestDTO @JsonCreator constructor(@JsonProperty("issueLineList") val issueLineList: MutableList<WmIssueLine>)  : WmIssueHeader()