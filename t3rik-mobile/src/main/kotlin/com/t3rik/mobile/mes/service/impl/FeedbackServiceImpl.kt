package com.t3rik.mobile.mes.service.impl

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.enums.ModuleTypeEnum
import com.t3rik.common.enums.YesOrNoEnum
import com.t3rik.common.enums.mes.DefaultDataEnum
import com.t3rik.common.enums.mes.FeedbackTypeEnum
import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.DateUtils
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.mes.md.domain.MdWorkstation
import com.t3rik.mes.md.service.IMdWorkstationService
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProRouteProcess
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.service.IProFeedbackService
import com.t3rik.mes.pro.service.IProRouteProcessService
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.enums.CurrentIndexEnum
import com.t3rik.mobile.mes.service.IFeedbackService
import com.t3rik.system.strategy.AutoCodeUtil
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.annotation.Resource


/**
 * 报工相关
 * @author t3rik
 * @date 2024/7/3 23:16
 */
@Service
class FeedbackServiceImpl : IFeedbackService {

    @Resource
    lateinit var autoCodeUtil: AutoCodeUtil

    /**
     * 生产任务
     */
    @Resource
    lateinit var taskService: IProTaskService

    /**
     * 报工
     */
    @Resource
    lateinit var proFeedbackService: IProFeedbackService

    /**
     * 工作站
     */
    @Resource
    lateinit var mdWorkstationService: IMdWorkstationService

    /**
     * 工艺工序
     */
    @Resource
    lateinit var proRouteProcessService: IProRouteProcessService


    /**
     * 根据传入的前端页码，返回数据
     */
    override fun getPageByCurrentIndex(currentIndex: String?, page: Page<ProTask>): Page<ProTask> {
        val paramByCurrentIndex = this.getParamByCurrentIndex(currentIndex)
        return this.taskService.lambdaQuery()
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .`in`(CollectionUtils.isNotEmpty(paramByCurrentIndex), ProTask::getStatus, paramByCurrentIndex)
            .orderByAsc(ProTask::getStatus)
            .orderByAsc(ProTask::getEndTime)
            .page(page)
    }

    /**
     * 新增报工
     */
    override fun addFeedback(proFeedback: ProFeedback): Long {
        // 工作站信息
        val (workstation, routeProcess) = this.validateWorkstationAndProcess(proFeedback)
        // 构建报工数据
        buildFeedback(proFeedback, workstation, routeProcess)
        this.proFeedbackService.save(proFeedback)
        return proFeedback.recordId
    }


    /**
     * 校验数据，如果通过返回工作站和流程信息
     */
    fun validateWorkstationAndProcess(proFeedback: ProFeedback): Pair<MdWorkstation, ProRouteProcess> {
        // 校验工作站是否存在
        val workstation = this.mdWorkstationService.getById(proFeedback.workstationId)
            ?: throw BusinessException(MsgConstants.NOT_EXIST_WORKSTATION)

        // 校验工作站是否配置相关工艺流程
        if (workstation.processId == null || proFeedback.routeId == null) {
            throw BusinessException(MsgConstants.ERROR_ROUTE)
        }

        // 校验工艺流程
        val routeProcesses = this.proRouteProcessService.lambdaQuery()
            .eq(ProRouteProcess::getRouteId, proFeedback.routeId)
            .eq(ProRouteProcess::getProcessId, workstation.processId)
            .one() ?: throw BusinessException(MsgConstants.ERROR_ROUTE)

        return Pair(workstation, routeProcesses)
    }

    /**
     * 构建报工数据
     */
    private fun buildFeedback(proFeedback: ProFeedback, workstation: MdWorkstation, routeProcess: ProRouteProcess) {
        proFeedback.processId = workstation.processId
        proFeedback.processCode = workstation.processCode
        proFeedback.processName = workstation.processName
        proFeedback.feedbackCode = autoCodeUtil.genSerialCode(UserConstants.FEEDBACK_CODE, "")
        // 报工数量 = 合格数量+不合格数量
        proFeedback.quantityFeedback = proFeedback.quantityQualified + proFeedback.quantityUnquanlified
        // 自行报工
        proFeedback.feedbackType = FeedbackTypeEnum.SELF.code
        // 报工人
        proFeedback.userId = SecurityUtils.getUserId()
        proFeedback.userName = SecurityUtils.getUsername()
        proFeedback.nickName = SecurityUtils.getUserNickname()
        // 审批人
        proFeedback.recordUserId = DefaultDataEnum.ADMIN.code.toLong()
        proFeedback.recordUser = DefaultDataEnum.ADMIN.desc
        proFeedback.recordNick = DefaultDataEnum.ADMIN.desc
        // 报工时间
        proFeedback.feedbackTime = DateUtils.getNowDate()
        // 状态 -> 审批中
        proFeedback.status = OrderStatusEnum.APPROVING.code
        // 报工途径 -> 移动端
        proFeedback.feedbackChannel = ModuleTypeEnum.MOBILE.code
        // 如果需要检测，写入待检测数量
        if (YesOrNoEnum.YES.code.equals(routeProcess.isCheck)) {
            proFeedback.quantityUncheck = proFeedback.quantityQualified
        } else {
            proFeedback.quantityUncheck = BigDecimal.ZERO
        }

    }

    /**
     * 根据传入的前端页码，返回不同的单据状态
     */
    override fun getParamByCurrentIndex(currentIndex: String?): List<String> {
        val statusList = mutableListOf<String>()
        // 类似switch
        when (currentIndex) {
            // 未处理查询草稿和已确认
            CurrentIndexEnum.UNPROCESSED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.PREPARE.code)
                    add(OrderStatusEnum.CONFIRMED.code)
                }
            }
            // 已处理查询审批中，审批通过，已拒绝
            CurrentIndexEnum.PROCESSED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.APPROVING.code)
                    add(OrderStatusEnum.APPROVED.code)
                    add(OrderStatusEnum.REFUSE.code)
                }
            }
            // 已完成的单据
            CurrentIndexEnum.FINISHED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.FINISHED.code)
                }
            }
            // 查询全部
            else -> return statusList
        }
    }
}