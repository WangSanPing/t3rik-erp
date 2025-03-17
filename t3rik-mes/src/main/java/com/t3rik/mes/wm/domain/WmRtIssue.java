package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 生产退料单头对象 wm_rt_issue
 *
 * @author t3rik
 * @date 2024-09-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wm_rt_issue ")
public class WmRtIssue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId
    /** 退料单ID */
    private Long rtId;

    /**
     * 退料单编号
     */
    @Excel(name = "退料单编号")
    private String rtCode;

    /**
     * 退料单名称
     */
    @Excel(name = "退料单名称")
    private String rtName;

    /**
     * 领料单ID
     */
    private Long issueId;

    /**
     * 报工记录ID
     */
    @Excel(name = "报工记录ID")
    @TableField("record_id")
    private Long recordId;

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
     * 退料日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rtDate;

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
     * 生产任务名称
     */
    @Excel(name = "生产任务名称")
    private String taskName;
}
