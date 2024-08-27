package com.t3rik.mes.sales.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
//import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.t3rik.common.annotation.Excel;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.dto.SalesOrderItemDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单产品列对象 sales_order_item
 * 
 * @author t3rik
 * @date 2024-08-29
 */
@Data
public class SalesOrderItemBo extends BasePrintDataBo<SalesOrderItemDto>
{

    /** 销售订单id */
    private Long salesOrderId;

    /** 销售订单产品id */
    @TableId
    private Long salesOrderItemId;
    /** 产品id */
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

    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;

    /** 颜色 */
    @Excel(name = "颜色")
    private String colorName;

    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /** 订货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salesOrderDate;

    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /** 客户产品名称 */
    @Excel(name = "客户产品名称")
    private String clientProductName;



    /** 订货数量 */
    @Excel(name = "订货数量")
    private BigDecimal salesOrderQuantity;


    /** 库存数量 */
    @Excel(name = "库存数量")
    private BigDecimal stockNum;

    /** 已出货数 */
    @Excel(name = "已出货数")
    private BigDecimal saleQty;

    /** 已退货数 */
    @Excel(name = "已退货数")
    private BigDecimal saleThqty;

    /** 手工消数 */
    @Excel(name = "手工消数")
    private BigDecimal saleSgqty;

    /** 工单ID */
    @Excel(name = "工单ID")
    private Long workorderId;

    /**
     * 工单编码
     */
    @Excel(name = "工单编码")
    private String workorderCode;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;

    /**
     * 工单名称
     */
    @Excel(name = "工单名称")
    private String workorderName;


    /** 创建人id */
    private Long createUserId;

    /** 更新人id */
    private Long updateUserId;


}