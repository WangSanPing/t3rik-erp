package com.t3rik.mes.wm.domain;

import java.util.Date;
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
@TableName(value = "wm_waste_header")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WmWasteHeader extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 废料单ID */
    @TableId
    private Long wasteId;


    /** 废料单编号 */
    @Excel(name = "废料单编号")
    private String wasteCode;


    /** 废料单名称 */
    @Excel(name = "废料单名称")
    private String wasteName;


    /** 生产工单ID */
    @Excel(name = "生产工单ID")
    private Long workorderId;


    /** 生产工单编码 */
    @Excel(name = "生产工单编码")
    private String workorderCode;


    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;


    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;


    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;


    /** 库区ID */
    @Excel(name = "库区ID")
    private Long locationId;


    /** 库区编码 */
    @Excel(name = "库区编码")
    private String locationCode;


    /** 库区名称 */
    @Excel(name = "库区名称")
    private String locationName;


    /** 库位ID */
    @Excel(name = "库位ID")
    private Long areaId;


    /** 库位编码 */
    @Excel(name = "库位编码")
    private String areaCode;


    /** 库位名称 */
    @Excel(name = "库位名称")
    private String areaName;


    /** 废料日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "废料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date wasteDate;


    /** 单据状态 */
    @Excel(name = "单据状态")
    private StatusEnum status;


    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String attr1;


    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String attr2;


    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private Long attr3;


    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private Long attr4;

    /** 更新人id */
    @Excel(name = "更新人id")
    private Long updateById;


    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createById;


}
