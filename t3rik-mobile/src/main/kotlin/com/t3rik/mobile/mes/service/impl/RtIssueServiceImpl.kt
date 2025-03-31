package com.t3rik.mobile.mes.service.impl

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.enums.mes.DefaultDataEnum
import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.DateUtils
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.domain.ProWorkorder
import com.t3rik.mes.pro.dto.TaskDTO
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mes.pro.service.IProWorkorderService
import com.t3rik.mes.wm.domain.*
import com.t3rik.mes.wm.dto.RtIssueHeaderAndLineDTO
import com.t3rik.mes.wm.service.*
import com.t3rik.mobile.mes.dto.RtIssueRequestDTO
import com.t3rik.mobile.mes.service.IRtIssueService
import com.t3rik.system.strategy.AutoCodeUtil
import jakarta.annotation.Resource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


/**
 * 退料Service
 * @author t3rik
 * @date 2025/3/9 22:45
 */
@Service
class RtIssueServiceImpl : IRtIssueService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RtIssueServiceImpl::class.java)
    }

    @Resource
    lateinit var proTaskService: IProTaskService

    @Resource
    lateinit var rtIssueHeaderService: IWmRtIssueService

    @Resource
    lateinit var workorderService: IProWorkorderService

    @Resource
    lateinit var autoCodeUtil: AutoCodeUtil

    @Resource
    lateinit var wmWarehouseService: IWmWarehouseService

    @Resource
    lateinit var wmRtIssueLineService: IWmRtIssueLineService

    @Resource
    lateinit var wmIssueLineService: IWmIssueLineService

    @Resource
    lateinit var wmWasteLineService: IWmWasteLineService

    /**
     * 新增退料
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun rtIssue(rtIssueRequestDTO: RtIssueRequestDTO, proTask: ProTask) {
        // 查询生产工单
        val workorder = this.workorderService.getById(rtIssueRequestDTO.workorderId) ?: throw BusinessException(MsgConstants.PARAM_ERROR)
        // 查询默认仓库信息
        // 退料全部退到默认仓库
        val warehouse = this.wmWarehouseService.lambdaQuery().eq(WmWarehouse::getWarehouseCode, DefaultDataEnum.WH00_DEFAULT.code).one()
        // 构造退料申请主单
        val header = this.buildHeader(rtIssueRequestDTO, workorder, proTask, warehouse)
        // 保存退料申请主单
        this.rtIssueHeaderService.save(header)
        log.info(header.toString())
        // 新增退料行
        // 构造退料行集合
        val rtIssueLines = this.buildLine(header.rtId, rtIssueRequestDTO, warehouse)
        // 校验数量
        checkQuantityRt(rtIssueLines)
        this.wmRtIssueLineService.saveBatch(rtIssueLines)
    }

    /**
     * 校验退料数量是否合规
     */
    fun checkQuantityRt(rtIssueLines: MutableList<WmRtIssueLine>) {
        // 退料数量分组
        val groupRtIssue = rtIssueLines.groupBy { it.itemCode }
        // 获取领料数量
        val groupIssueLines = this.wmIssueLineService.lambdaQuery()
            .`in`(WmIssueLine::getLineId, rtIssueLines.map { it.issueLineId })
            .list()
            .groupBy { it.itemCode }
        // 获取废料数量
        val groupWasteLines = this.wmWasteLineService.lambdaQuery()
            .`in`(WmWasteLine::getIssueLineId, rtIssueLines.map { it.issueLineId })
            .list()
            .groupBy { it.itemCode }

        for ((itemCode, rtRecords) in groupRtIssue) {
            // 退料
            val totalReturned = rtRecords.sumOf { it.quantityRt }
            // 废料
            val totalWaste = groupWasteLines[itemCode]?.sumOf { it.quantityWaste } ?: BigDecimal.ZERO
            // 领料
            val totalIssued = groupIssueLines[itemCode]?.sumOf { it.quantityIssued } ?: BigDecimal.ZERO

            if ((totalWaste + totalReturned) > totalIssued) {
                throw BusinessException("物料: ${rtRecords.first().itemName}, 退料总数: $totalReturned + 废料总数: $totalWaste {共计: ${totalReturned + totalWaste}}, 超过了领料总数: $totalIssued！")
            }
        }
    }

    /**
     * 构造领料申请主单
     */
    fun buildHeader(rtIssueRequestDTO: RtIssueRequestDTO, workorder: ProWorkorder, proTask: ProTask, warehouse: WmWarehouse): WmRtIssue {
        val wmRtIssue = WmRtIssue().apply {
            // copy前端属性
            BeanUtil.copyProperties(rtIssueRequestDTO, this)
            // 退料单 编码
            rtCode = autoCodeUtil.genSerialCode(UserConstants.RTISSUE_CODE, null)
            // 退料单 名称
            rtName = "${workorder.productName}---退料单"
            rtDate = DateUtils.getNowDate()
            // 订单状态
            status = OrderStatusEnum.PREPARE.code
            // 任务信息
            taskId = proTask.taskId
            taskName = proTask.taskName
            taskCode = proTask.taskCode
            // 仓库信息
            warehouseId = warehouse.warehouseId
            warehouseCode = warehouse.warehouseCode
            warehouseName = warehouse.warehouseName
        }
        return wmRtIssue
    }


    /**
     * 构造退料子单
     */
    fun buildLine(rtIssueHeaderId: Long, rtIssueRequestDTO: RtIssueRequestDTO, warehouse: WmWarehouse): MutableList<WmRtIssueLine> {
        // 只处理本次退料数量大于0的数据
        return rtIssueRequestDTO.issueLineList
            .filter { (it.quantityRt ?: BigDecimal.ZERO) > BigDecimal.ZERO }
            .map {
                it.apply {
                    issueLineId = lineId
                    lineId = null
                    rtId = rtIssueHeaderId
                    // 仓库信息
                    // 退到默认仓库
                    warehouseId = warehouse.warehouseId
                    warehouseCode = warehouse.warehouseCode
                    warehouseName = warehouse.warehouseName
                    // 因为前端数据有传入领料的仓库信息，这里要保存的退料仓库信息，所以全部赋值为null
                    locationId = null
                    locationCode = null
                    locationName = null
                    areaId = null
                    areaCode = null
                    areaName = null
                }
            }.toMutableList()
    }

    /**
     * 查询任务，并统计退料次数
     * @param query 查询条件
     */
    override fun getTaskListAndRtIssueCount(page: IPage<TaskDTO>, query: Wrapper<TaskDTO>): Page<TaskDTO> {
        return this.proTaskService.getTaskListAndRtIssueCount(page, query)
    }

    /**
     * 查询退料详情
     */
    override fun getRtIssueDetail(query: RtIssueHeaderAndLineDTO): MutableList<RtIssueHeaderAndLineDTO> {
        val wrapper = QueryWrapper<RtIssueHeaderAndLineDTO>()
        wrapper.eq("workorder_code", query.workorderCode)
        wrapper.eq("task_id", query.taskId)
        wrapper.eq("wih.status", OrderStatusEnum.FINISHED.code)
        return this.rtIssueHeaderService.getRtIssueDetail(wrapper)
    }

    /**
     * 查询退料详情
     */
    override fun getRtIssueDetailList(query: RtIssueHeaderAndLineDTO): MutableList<RtIssueHeaderAndLineDTO> {
        val wrapper = QueryWrapper<RtIssueHeaderAndLineDTO>()
        wrapper.eq("workorder_code", query.workorderCode)
        wrapper.eq("task_id", query.taskId)
        return this.rtIssueHeaderService.getRtIssueDetailList(wrapper)
    }
}