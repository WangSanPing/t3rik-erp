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
import com.t3rik.mes.wm.dto.WasteHeaderAndLineDTO
import com.t3rik.mes.wm.service.*
import com.t3rik.mobile.mes.dto.WasteIssueRequestDTO
import com.t3rik.mobile.mes.service.IWasteIssueService
import com.t3rik.system.strategy.AutoCodeUtil
import jakarta.annotation.Resource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


/**
 * 领料申请
 * @author t3rik
 * @date 2024/9/1 13:30
 */
@Service
class WasteServiceImpl : IWasteIssueService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RtIssueServiceImpl::class.java)
    }

    @Resource
    lateinit var proTaskService: IProTaskService

    @Resource
    lateinit var wasteHeaderService: IWmWasteHeaderService

    @Resource
    lateinit var workorderService: IProWorkorderService

    @Resource
    lateinit var autoCodeUtil: AutoCodeUtil

    @Resource
    lateinit var wmWarehouseService: IWmWarehouseService

    @Resource
    lateinit var wmWasteLineService: IWmWasteLineService

    @Resource
    lateinit var wmIssueLineService: IWmIssueLineService

    @Resource
    lateinit var wmRtIssueLineService: IWmRtIssueLineService

    /**
     * 新增退料
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun addWasteIssue(wasteIssueRequestDTO: WasteIssueRequestDTO, proTask: ProTask) {
        // 查询生产工单
        val workorder = this.workorderService.getById(wasteIssueRequestDTO.workorderId) ?: throw BusinessException(MsgConstants.PARAM_ERROR)
        // 查询默认仓库信息
        // 废料全部退到废料库
        val warehouse = this.wmWarehouseService.lambdaQuery().eq(WmWarehouse::getWarehouseCode, DefaultDataEnum.WASTE_VIRTUAL_WH.code).one()
        // 构造退料申请主单
        val header = this.buildHeader(wasteIssueRequestDTO, workorder, proTask, warehouse)
        // 保存退料申请主单
        this.wasteHeaderService.save(header)
        log.info(header.toString())
        // 新增退料行
        // 构造废料行集合
        val wasteLineList = this.buildLine(header.wasteId, wasteIssueRequestDTO, warehouse)
        // 校验废料数量
        checkWasteQt(wasteLineList)
        this.wmWasteLineService.saveBatch(wasteLineList)
    }

    /**
     * 校验退料数量是否合规
     */
    fun checkWasteQt(wasteLineList: MutableList<WmWasteLine>) {
        // 废料
        val groupWaste = wasteLineList.groupBy { it.itemCode }
        // 获取领料数量
        val groupIssueLines = this.wmIssueLineService.lambdaQuery()
            .`in`(WmIssueLine::getLineId, wasteLineList.map { it.issueLineId })
            .list()
            .groupBy { it.itemCode }
        // 获取退料数量
        val groupRtIssueLines = this.wmRtIssueLineService.lambdaQuery()
            .`in`(WmRtIssueLine::getIssueLineId, wasteLineList.map { it.issueLineId })
            .list()
            .groupBy { it.itemCode }

        for ((itemCode, wasteRecords) in groupWaste) {
            // 废料
            val totalWaste = wasteRecords.sumOf { it.quantityWaste }
            // 退料
            val totalReturned = groupRtIssueLines[itemCode]?.sumOf { it.quantityRt } ?: BigDecimal.ZERO
            // 领料
            val totalIssued = groupIssueLines[itemCode]?.sumOf { it.quantityIssued } ?: BigDecimal.ZERO

            if ((totalWaste + totalReturned) > totalIssued) {
                throw BusinessException("物料: ${wasteRecords.first().itemName}, 废料总数: $totalWaste + 退料总数: $totalReturned {共计: ${totalReturned + totalWaste}}, 超过了领料总数: $totalIssued！")
            }
        }
    }

    /**
     * 构造废料申请主单
     */
    fun buildHeader(wasteIssueRequestDTO: WasteIssueRequestDTO, workorder: ProWorkorder, proTask: ProTask, warehouse: WmWarehouse): WmWasteHeader {
        val wmWasteHeader = WmWasteHeader().apply {
            // copy前端属性
            BeanUtil.copyProperties(wasteIssueRequestDTO, this)
            // 废料单 编码
            wasteCode = autoCodeUtil.genSerialCode(UserConstants.PWASTE_CODE, null)
            // 退料单 名称
            wasteName = "${workorder.productName}---废料单"
            wasteDate = DateUtils.getNowDate()
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
        return wmWasteHeader
    }


    /**
     * 构造废料子单
     */
    fun buildLine(rtIssueHeaderId: Long, wasteIssueRequestDTO: WasteIssueRequestDTO, warehouse: WmWarehouse): MutableList<WmWasteLine> {
        // 只处理本次退料数量大于0的数据
        return wasteIssueRequestDTO.issueLineList
            .filter { (it.quantityWaste ?: BigDecimal.ZERO) > BigDecimal.ZERO }
            .map {
                it.apply {
                    issueLineId = lineId
                    lineId = null
                    wasteId = rtIssueHeaderId
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
    override fun getTaskListAndWasteIssueCount(page: IPage<TaskDTO>, query: Wrapper<TaskDTO>): Page<TaskDTO> {
        return this.proTaskService.getTaskListAndWasteIssueCount(page, query)
    }

    /**
     * 查询废料详情
     */
    override fun getWasteIssueDetail(query: WasteHeaderAndLineDTO): MutableList<WasteHeaderAndLineDTO> {
        val wrapper = QueryWrapper<WasteHeaderAndLineDTO>()
        wrapper.eq("workorder_code", query.workorderCode)
        wrapper.eq("task_id", query.taskId)
        wrapper.eq("wih.status", OrderStatusEnum.FINISHED.code)
        return this.wasteHeaderService.getWasteIssueDetail(wrapper)
    }

    /**
     * 查询退料详情
     */
    override fun getWasteIssueDetailList(query: WasteHeaderAndLineDTO): MutableList<WasteHeaderAndLineDTO> {
        val wrapper = QueryWrapper<WasteHeaderAndLineDTO>()
        wrapper.eq("workorder_code", query.workorderCode)
        wrapper.eq("task_id", query.taskId)
        return this.wasteHeaderService.getWasteIssueDetailList(wrapper)
    }
}