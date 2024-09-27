package com.t3rik.mes.sales.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.enums.EnableFlagEnum;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 销售订单对象 sales_order
*
* @author 堇年
* @date 2024-08-29
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sales_order")
public class SalesOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售订单id */
    @TableId
    private Long salesOrderId;


    /** 订单编码 */
    @Excel(name = "订单编码")
    private String salesOrderCode;


    /** 订单名称 */
    @Excel(name = "订单名称")
    private String salesOrderName;


    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;


    /** 客户id */
    private Long clientId;


    /** 客户编码 */
    @Excel(name = "客户编码")
    private String clientCode;


    /** 客户PO号 */
    @Excel(name = "客户PO号")
    private String clientPoCode;


    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;


    /** 订货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salesOrderDate;


    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;



    /** 币别 */
    @Excel(name = "币别")
    private String currency;


    /** 结账方式 */
    @Excel(name = "结账方式")
    private String payUp;


    /** 订单类别 */
    @Excel(name = "订单类别")
    private String orderType;


    /** 跟单 */
    @Excel(name = "跟单")
    private String followerMan;


    /** 业务员 */
    @Excel(name = "业务员")
    private String salesMan;


    /** 质量要求 */
    @Excel(name = "质量要求")
    private String qualityRequirement;


    /** 预留字段1 */
    private String attr1;


    /** 预留字段2 */
    private String attr2;


    /** 预留字段3 */
    private Long attr3;


    /** 预留字段4 */
    private Long attr4;


    /** 创建人id */
    private Long createUserId;


    /** 更新人id */
    private Long updateUserId;


    /** 销售订单产品列信息 */
    @TableField(exist = false)
    private List<SalesOrderItem> salesOrderItemList;


}
