package com.t3rik.mes.pro.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产任务对象 pro_task
 *
 * @author yinjinlu
 * @date 2022-05-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId
    private Long taskId;

    /**
     * 客户订单号
     */
    @Excel(name = "客户订单号")
    private String clientOrderCode;

    /**
     * 查询页
     * 0:全部 1:待处理 2:已处理
     */
    @TableField(exist = false)
    private String currentIndex;

    /**
     * 任务编号
     */
    @Excel(name = "任务编号")
    private String taskCode;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String taskName;

    /**
     * 生产工单ID
     */
    @Excel(name = "生产工单ID")
    private Long workorderId;

    /**
     * 生产工单编号
     */
    @Excel(name = "生产工单编号")
    private String workorderCode;

    /**
     * 工单名称
     */
    @Excel(name = "工单名称")
    private String workorderName;

    /**
     * 工作站ID
     */
    @Excel(name = "工作站ID")
    private Long workstationId;

    /**
     * 工作站编号
     */
    @Excel(name = "工作站编号")
    private String workstationCode;

    /**
     * 工作站名称
     */
    @Excel(name = "工作站名称")
    private String workstationName;

    private Long routeId;

    private String routeCode;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long processId;

    /**
     * 工序编码
     */
    @Excel(name = "工序编码")
    private String processCode;

    /**
     * 工序名称
     */
    @Excel(name = "工序名称")
    private String processName;

    private String isCheck;

    /**
     * 产品物料ID
     */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /**
     * 产品物料编码
     */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /**
     * 产品物料名称
     */
    @Excel(name = "产品物料名称")
    private String itemName;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specification;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /**
     * 排产数量
     */
    @Excel(name = "排产数量")
    private BigDecimal quantity;

    /**
     * 已生产数量
     */
    @Excel(name = "已生产数量")
    private BigDecimal quantityProduced;

    private BigDecimal quantityQuanlify;

    private BigDecimal quantityUnquanlify;

    /**
     * 调整数量
     */
    @Excel(name = "调整数量")
    private BigDecimal quantityChanged;

    /**
     * 客户ID
     */
    @Excel(name = "客户ID")
    private Long clientId;

    /**
     * 客户编码
     */
    @Excel(name = "客户编码")
    private String clientCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String clientName;

    /**
     * 客户简称
     */
    @Excel(name = "客户简称")
    private String clientNick;

    /**
     * 开始生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始生产时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 生产时长
     */
    @Excel(name = "生产时长")
    private Long duration;

    /**
     * 完成生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成生产时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 甘特图显示颜色
     */
    @Excel(name = "甘特图显示颜色")
    private String colorCode;

    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requestDate;

    private String status;

    /**
     * 预留字段1
     */
    private String attr1;

    /**
     * 预留字段2
     */
    private String attr2;

    /**
     * 预留字段3
     */
    private Long attr3;

    /**
     * 预留字段4
     */
    private Long attr4;

    /**
     * 分配任务人id
     */
    private Long taskUserId;

    /**
     * 分配任务人名称
     */
    private String taskBy;

    /**
     * 用于组装生产工单下所有子任务
     */
    @TableField(exist = false)
    List<ProTask> childTasks;

    /**
     * 用于组装生产工单下所有子任务的父id
     */
    @TableField(exist = false)
    private Long parentId;
}
