package com.t3rik.mes.wm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.mes.wm.domain.WmWasteLine;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 废料头和行dto
 *
 * @author t3rik
 * @date 2024/9/10 17:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WasteHeaderAndLineDTO extends WmWasteLine {

    /**
     * 废料单编号
     */
    private String wasteCode;

    /**
     * 废料单名称
     */
    private String wasteName;

    /**
     * 领料单编号
     */
    private Long issueId;

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
     * 领料数量
     */
    private BigDecimal quantityIssued;

    /**
     * 废料次数
     */
    private Integer wasteIssueCount;

    /**
     * 已废料数量
     */
    private Double wasteIssueQty;

    /**
     * 废料日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date wasteDate;
}
