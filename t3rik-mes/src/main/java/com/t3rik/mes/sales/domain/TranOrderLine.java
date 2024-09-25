package com.t3rik.mes.sales.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.enums.EnableFlagEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 销售送货单列对象 tran_order_line
 *
 * @author t3rik
 * @date 2024-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "tran_order_line")
public class TranOrderLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    @TableId
    private Long tranOrderLineId;


    /** 送货单id */
    @Excel(name = "送货单id")
    private Long tranOrderId;

    /** 主单号 */
    @Excel(name = "主单号")
    private String tranOrderCode;

    /** 客户id */
    @Excel(name = "客户id")
    private Long clientId;


    /** 客户编码 */
    @Excel(name = "客户编码")
    private String clientCode;


    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;


    /** 跟单 */
    @Excel(name = "跟单")
    private String followerMan;


    /** 业务员 */
    @Excel(name = "业务员")
    private String busMan;


    /** 送货类别 */
    @Excel(name = "送货类别")
    private String tranOrderType;


    /** 送货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tranDate;


    /** 送货单号 */
    @Excel(name = "送货单号")
    private String tranCode;


    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;


    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;


    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;


    /** 销售订单id */
    @Excel(name = "销售订单id")
    private Long salesOrderId;


    /** 销售订单编码 */
    @Excel(name = "销售订单编码")
    private String salesOrderCode;


    /** 产品id */
    @Excel(name = "产品id")
    private Long productId;


    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;


    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;


    /** 产品规格 */
    @Excel(name = "产品规格")
    private String productSpec;


    /** 材质 */
    @Excel(name = "材质")
    private String quality;


    /** 质量等级 */
    @Excel(name = "质量等级")
    private String level;


    /** 颜色 */
    @Excel(name = "颜色")
    private String colorName;


    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;


    /** 订单数 */
    @Excel(name = "订单数")
    private BigDecimal saleQty;


    /** 备品数 */
    @Excel(name = "备品数")
    private BigDecimal saleThqty;


    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal amount;


    /** 出货数 */
    @Excel(name = "出货数")
    private BigDecimal saleSgqty;


    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;


    /** 含税 */
    @Excel(name = "含税")
    private Long tax;


    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal totalAmount;


    /** 附加费 */
    @Excel(name = "附加费")
    private BigDecimal extra;


    /** 件数 */
    @Excel(name = "件数")
    private Long picNum;


    /** po */
    @Excel(name = "po")
    private String po;


    /** sku */
    @Excel(name = "sku")
    private String sku;


    /** 客户规格 */
    @Excel(name = "客户规格")
    private String clientSpec;


    /** 客户品名 */
    @Excel(name = "客户品名")
    private String clientProductName;


    /** 客户颜色 */
    @Excel(name = "客户颜色")
    private String clientColor;


    /** 工单ID */
    @Excel(name = "工单ID")
    private Long workorderId;


    /** 生产工单编码 */
    @Excel(name = "生产工单编码")
    private String workorderCode;


    /** 状态 */
    @Excel(name = "状态")
    private String status;



}
