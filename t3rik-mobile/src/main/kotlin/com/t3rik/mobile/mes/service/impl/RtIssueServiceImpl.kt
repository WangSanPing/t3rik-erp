package com.t3rik.mobile.mes.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.common.enums.mes.StatisticsTypeEnum
import com.t3rik.mes.pro.dto.TaskDTO
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mes.wm.domain.WmRtIssue
import com.t3rik.mobile.mes.service.IRtIssueService
import jakarta.annotation.Resource
import org.springframework.stereotype.Service


/**
 * 退料Service
 * @author t3rik
 * @date 2025/3/9 22:45
 */
@Service
class RtIssueServiceImpl : IRtIssueService {

    @Resource
    lateinit var proTaskService: IProTaskService;

    /**
     * 新增退料
     */
    override fun rtIssue(rtIssue: WmRtIssue) {
        TODO("Not yet implemented")
    }

    /**
     * 查询任务，根据工单分组展示数据，并统计领料和退料次数
     * @param query 查询条件
     */
    override fun getWorkOrderGroupAndSelectTypeCount(page: IPage<TaskDTO>, query: Wrapper<TaskDTO>): Page<TaskDTO> {
        return this.proTaskService.getWorkOrderGroupAndSelectTypeCount(page, query, StatisticsTypeEnum.RT_ISSUED_QUANTITY)
    }

    /**
     * 查询任务，并统计退料次数
     * @param query 查询条件
     */
    override fun getTaskListAndRtIssueCount(page: IPage<TaskDTO>, query: Wrapper<TaskDTO>): Page<TaskDTO> {
        return this.proTaskService.getTaskListAndRtIssueCount(page, query)
    }
}