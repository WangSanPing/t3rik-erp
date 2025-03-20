package com.t3rik.mobile.mes.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.t3rik.mes.wm.domain.WmRtIssue
import com.t3rik.mes.wm.domain.WmRtIssueLine
import com.t3rik.mes.wm.domain.WmWasteLine


/**
 * 退料DTO
 * @author t3rik
 * @date 2025/3/16 20:26
 */
data class WasteIssueRequestDTO @JsonCreator constructor(
    @JsonProperty("issueLineList") val issueLineList: MutableList<WmWasteLine>
) : WmRtIssue()