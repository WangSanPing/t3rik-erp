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
import com.t3rik.common.utils.StringUtils
import com.t3rik.mes.md.domain.MdWorkstation
import com.t3rik.mes.md.service.IMdWorkstationService
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProRouteProcess
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.service.IProFeedbackService
import com.t3rik.mes.pro.service.IProRouteProcessService
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.enums.CurrentIndexEnum
import com.t3rik.mobile.mes.dto.TaskAndFeedbackDto
import com.t3rik.mobile.mes.service.IFeedbackService
import com.t3rik.system.strategy.AutoCodeUtil
import isGreaterOrEqual
import jakarta.annotation.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import orZero
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


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
    override fun getPageByCurrentIndex(task: ProTask, page: Page<ProTask>): Page<ProTask> {
        // 获取要查询的单据状态
        val paramByCurrentIndex = this.getParamByCurrentIndex(task.currentIndex)
        return this.taskService.lambdaQuery()
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .like(StringUtils.isNotBlank(task.taskName), ProTask::getTaskName, task.taskName)
            .`in`(CollectionUtils.isNotEmpty(paramByCurrentIndex), ProTask::getStatus, paramByCurrentIndex)
            .orderByAsc(ProTask::getStatus)
            .orderByAsc(ProTask::getEndTime)
            .page(page)
    }

    /**
     * 新增报工
     */
    @Transactional
    override fun addFeedback(proFeedback: ProFeedback): String {
        // 工作站信息
        val (workstation, routeProcess) = this.validateWorkstationAndProcess(proFeedback)
        // 构建报工数据
        buildFeedback(proFeedback, workstation, routeProcess)
        // 保存更新
        this.proFeedbackService.save(proFeedback)
        // 回写任务，更新任务表中的数量数据
        // 计算合格品数量
        val task = this.taskService.getById(proFeedback.taskId).let { t ->
            ProTask().apply {
                // 主键
                taskId = t.taskId
                // 已生产数量
                quantityProduced = t.quantityProduced.orZero().add(proFeedback.quantityFeedback.orZero())
                // 合格品数量
                quantityQuanlify = t.quantityQuanlify.orZero().add(proFeedback.quantityQualified.orZero())
                // 不良品数量
                quantityUnquanlify = t.quantityUnquanlify.orZero().add(proFeedback.quantityUnquanlified.orZero())
                // 排产数量
                quantity = t.quantity
            }
        }
        // 判断更新后当前报工数量是否大于任务计划数量
        // 报工数量-计划数量
        // 报工数量-计划数量
        val subtract = task.quantityQuanlify.subtract(task.quantity)
        val msg: String
        // 如果报工大于排产，更新任务已完成
        if (subtract.isGreaterOrEqual(BigDecimal.ZERO)) {
            msg =
                "当前任务报工合格品总数量：「${task.quantityQuanlify}」 已大于排产数量 「${proFeedback.quantity}」，任务自动完成"
            // 状态改为已完成
            task.status = OrderStatusEnum.FINISHED.code
        } else {
            msg =
                "当前任务报工合格品总数量：「${task.quantityQuanlify}」, 排产数量 「${proFeedback.quantity}」, 距完成任务，还缺少数量: 「${subtract.abs()}」"
        }
        // 更新任务数据
        this.taskService.updateById(task)
        return msg
    }

    /**
     * 获取报工详细信息以及报工列表
     */
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getTaskAndFeedback(taskId: Long, status: OrderStatusEnum?): TaskAndFeedbackDto {
        val taskJob = GlobalScope.async {
            val task = async {
                taskService.lambdaQuery()
                    .eq(ProTask::getTaskId, taskId)
                    .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
                    .one()
            }
            val feedbackList = async {
                proFeedbackService.lambdaQuery()
                    .eq(ProFeedback::getTaskId, taskId)
                    .eq(ProFeedback::getUserId, SecurityUtils.getUserId())
                    .eq(status != null, ProFeedback::getStatus, status?.code)
                    .list()
            }
            TaskAndFeedbackDto(task.await(), feedbackList.await())
        }
        return taskJob.await()
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
        proFeedback.quantityFeedback = proFeedback.quantityQualified + proFeedback.quantityUnquanlified.orZero()
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
                    add(OrderStatusEnum.FINISHED.code)
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