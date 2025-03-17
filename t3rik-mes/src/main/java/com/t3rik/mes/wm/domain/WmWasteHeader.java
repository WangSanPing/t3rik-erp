package com.t3rik.mes.wm.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.enums.StatusEnum;

/**
 * 生产废料单头对象 wm_waste_header
 *
 * @author t3rik
 * @date 2024-05-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wm_waste_header")
public class WmWasteHeader extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 废料单ID */
    @TableId
    private Long wasteId;


    /** 废料单编号 */
    @Excel(name = "废料单编号")
    @TableField("waste_code") // 使用 @TableField 注解指定数据库字段名
    private String wasteCode;

    /** 废料单名称 */
    @Excel(name = "废料单名称")
    @TableField("waste_name")
    private String wasteName;

    /**
     * 领料单ID
     */
    private Long issueId;

    /** 报工记录ID */
    @Excel(name = "报工记录ID")
    @TableField("record_id")
    private Long recordId;

    /** 生产工单ID */
    @Excel(name = "生产工单ID")
    @TableField("workorder_id")
    private Long workorderId;

    /** 生产工单编码 */
    @Excel(name = "生产工单编码")
    @TableField("workorder_code")
    private String workorderCode;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    @TableField("warehouse_id")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    @TableField("warehouse_code")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    @TableField("warehouse_name")
    private String warehouseName;

    /** 库区ID */
    @Excel(name = "库区ID")
    @TableField("location_id")
    private Long locationId;

    /** 库区编码 */
    @Excel(name = "库区编码")
    @TableField("location_code")
    private String locationCode;

    /** 库区名称 */
    @Excel(name = "库区名称")
    @TableField("location_name")
    private String locationName;

    /** 库位ID */
    @Excel(name = "库位ID")
    @TableField("area_id")
    private Long areaId;

    /** 库位编码 */
    @Excel(name = "库位编码")
    @TableField("area_code")
    private String areaCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    @TableField("area_name")
    private String areaName;

    /** 废料日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "废料日期", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("waste_date")
    private Date wasteDate;

    /** 单据状态 */
    @Excel(name = "单据状态")
    @TableField("status")
    private String status;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    @TableField("attr1")
    private String attr1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    @TableField("attr2")
    private String attr2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    @TableField("attr3")
    private Long attr3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    @TableField("attr4")
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

