package com.t3rik.mobile.mes.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.dto.TaskDTO
import com.t3rik.mes.wm.domain.WmRtIssue
import org.apache.ibatis.annotations.Param


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

    /**
     * 查询任务，根据工单分组展示数据，并统计领料次数
     * @param query 查询条件
     */
    fun getWorkOrderGroupAndSelectTypeCount(page: IPage<TaskDTO>,@Param(Constants.WRAPPER) query: Wrapper<TaskDTO>): Page<TaskDTO>

    /**
     * 查询任务，并统计退料次数
     * @param query 查询条件
     */
    fun getTaskListAndRtIssueCount(page: IPage<TaskDTO>,@Param(Constants.WRAPPER) query: Wrapper<TaskDTO>): Page<TaskDTO>
}