package com.t3rik.mes.wm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.mes.wm.domain.WmIssueLine;
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
public class WasteHeaderAndLineDTO extends WmIssueLine {
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
}
