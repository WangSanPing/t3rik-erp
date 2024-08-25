package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 生产领料单头对象 wm_issue_header
 *
 * @author yinjinlu
 * @date 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WmIssueHeader extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 领料单ID
     */
    @TableId
    private Long issueId;

    /**
     * 领料单编号
     */
    @Excel(name = "领料单编号")
    private String issueCode;

    /**
     * 领料单名称
     */
    @Excel(name = "领料单名称")
    private String issueName;

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

    private String workstationName;

    /**
     * 生产工单ID
     */
    @Excel(name = "生产工单ID")
    private Long workorderId;

    /**
     * 生产工单编码
     */
    @Excel(name = "生产工单编码")
    private String workorderCode;

    /**
     * 生产任务ID
     */
    @Excel(name = "生产任务ID")
    private Long taskId;

    /**
     * 生产任务编码
     */
    @Excel(name = "生产任务编码")
    private String taskCode;

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
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /**
     * 库区ID
     */
    @Excel(name = "库区ID")
    private Long locationId;

    /**
     * 库区编码
     */
    @Excel(name = "库区编码")
    private String locationCode;

    /**
     * 库区名称
     */
    @Excel(name = "库区名称")
    private String locationName;

    /**
     * 库位ID
     */
    @Excel(name = "库位ID")
    private Long areaId;

    /**
     * 库位编码
     */
    @Excel(name = "库位编码")
    private String areaCode;

    /**
     * 库位名称
     */
    @Excel(name = "库位名称")
    private String areaName;

    /**
     * 领料日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

    /**
     * 单据状态
     */
    @Excel(name = "单据状态")
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
}
