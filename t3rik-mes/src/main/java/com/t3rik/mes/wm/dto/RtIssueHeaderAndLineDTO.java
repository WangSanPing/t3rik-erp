package com.t3rik.mes.wm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.mes.wm.domain.WmRtIssueLine;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退料头和行dto
 *
 * @author t3rik
 * @date 2024/9/10 17:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RtIssueHeaderAndLineDTO extends WmRtIssueLine {



    /**
     * 退料单编码
     */
    private String rtCode;

    /**
     * 退料单名称
     */
    private String rtName;

    /**
     * 领料单编号
     */
    private String issueId;
    /**
     * 领料单编号
     */
    private String issueCode;

    /**
     * 领料单名称
     */
    private String issueName;

    /**
     * 生产工单ID
     */
    private Long workorderId;

    /**
     * 生产工单编码
     */
    private String workorderCode;

    /**
     * 生产任务ID
     */
    private Long taskId;

    /**
     * 生产任务编码
     */
    private String taskCode;

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 客户编码
     */
    private String clientCode;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户简称
     */
    private String clientNick;

    /**
     * 领料日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    /**
     * 退料日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rtDate;

    /**
     * 退料次数
     */
    private Integer rtCount;

    /**
     * 已退料数量
     */
    private Double returnedQty;

    /**
     * 领料数量
     */
    private BigDecimal quantityIssued;
}
