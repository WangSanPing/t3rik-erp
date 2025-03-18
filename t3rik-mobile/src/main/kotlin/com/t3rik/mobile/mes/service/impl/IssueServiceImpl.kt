package com.t3rik.mobile.mes.service.impl

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.DateUtils
import com.t3rik.mes.pro.domain.ProWorkorder
import com.t3rik.mes.pro.service.IProWorkorderService
import com.t3rik.mes.wm.domain.WmIssueHeader
import com.t3rik.mes.wm.dto.IssueHeaderAndLineDTO
import com.t3rik.mes.wm.service.IWmIssueHeaderService
import com.t3rik.mes.wm.service.IWmIssueLineService
import com.t3rik.mobile.mes.dto.IssueRequestDTO
import com.t3rik.mobile.mes.service.IIssueService
import com.t3rik.system.strategy.AutoCodeUtil
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


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

    @Resource
    lateinit var workorderService: IProWorkorderService

    @Resource
    lateinit var autoCodeUtil: AutoCodeUtil

    /**
     * 领料申请
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun issue(issueRequestDTO: IssueRequestDTO) {
        // 查询生产工单
        val workorder = this.workorderService.getById(issueRequestDTO.workorderId) ?: throw BusinessException(
            MsgConstants.PARAM_ERROR
        )
        // 构造领料申请主单
        val header = this.buildHeader(issueRequestDTO, workorder)
        // 保存领料申请主单
        this.issueHeaderService.save(header)
        // 构造领料申请子单
        issueRequestDTO.issueLineList.forEach { f -> f.issueId = header.issueId }
        this.issueLineService.saveBatch(issueRequestDTO.issueLineList)
    }

    /**
     * 领料申请详情
     */
    override fun getIssueDetail(query: IssueHeaderAndLineDTO): MutableList<IssueHeaderAndLineDTO> {
        val wrapper = QueryWrapper<IssueHeaderAndLineDTO>()
        wrapper.eq("workorder_code", query.workorderCode)
        wrapper.eq("task_id", query.taskId)
        return this.issueHeaderService.getIssueDetail(wrapper)
    }

    /**
     * 构造领料申请主单
     */
    fun buildHeader(issueRequestDTO: IssueRequestDTO, workorder: ProWorkorder): WmIssueHeader {
        return WmIssueHeader().apply {
            // copy前端属性
            BeanUtil.copyProperties(issueRequestDTO, this)
            // 领料单 编码
            issueCode = autoCodeUtil.genSerialCode(UserConstants.ISSUE_CODE, null)
            // 领料单 名称
            issueName = "${workorder.productName}---领料单"
            issueDate = DateUtils.getNowDate()
            // 客户信息
            clientId = workorder.clientId
            clientCode = workorder.clientCode
            clientName = workorder.clientName
            // 订单状态
            status = OrderStatusEnum.PREPARE.code

        }
    }
}