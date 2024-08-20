package com.t3rik.mes.pro.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产报工记录对象 pro_feedback
 *
 * @author yinjinlu
 * @date 2022-07-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProFeedback extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId
    private Long recordId;
    /**
     * 客户订单号
     */
    private String clientOrderCode;

    /**
     * 报工单编号
     */
    private String feedbackCode;

    /**
     * 报工类型
     */
    private String feedbackType;

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

    @TableField(exist = false)
    private String isCheck;

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
     * 生产工单名称
     */
    @Excel(name = "生产工单名称")
    private String workorderName;

    /**
     * 生产任务ID
     */
    @Excel(name = "生产任务ID")
    private Long taskId;

    /**
     * 生产任务编号
     */
    @Excel(name = "生产任务编号")
    private String taskCode;

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
     * 本次报工数量
     */
    @Excel(name = "本次报工数量")
    private BigDecimal quantityFeedback;

    /**
     * 合格品数量
     */
    @Excel(name = "合格品数量")
    private BigDecimal quantityQualified;

    /**
     * 不良品数量
     */
    @Excel(name = "不良品数量")
    private BigDecimal quantityUnquanlified;

    @Excel(name = "待检测数量")
    private BigDecimal quantityUncheck;

    /**
     * 报工用户名
     */
    @Excel(name = "报工用户名")
    private String userName;

    /**
     * 报工用户id
     */
    private Long userId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 报工途径
     */
    @Excel(name = "报工途径")
    private String feedbackChannel;

    /**
     * 报工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedbackTime;

    /**
     * 审批人员
     */
    @Excel(name = "审批人员")
    private String recordUser;

    /**
     * 审批人员id
     */
    private Long recordUserId;

    /**
     * 审批人员名称
     */
    @Excel(name = "审批人员名称")
    private String recordNick;

    /**
     * 状态
     */
    @Excel(name = "状态")
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

    @TableField(exist = false)
    private Boolean checked;
}
