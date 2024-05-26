package com.t3rik.mes.pro.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.validated.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户订单对象 pro_client_order
 *
 * @author t3rik
 * @date 2024-05-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "pro_client_order")
public class ProClientOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户订单id
     */
    @TableId
    @NotNull(message = "订单id不能为空", groups = UpdateGroup.class)
    private Long clientOrderId;

    /**
     * 订单编码
     */
    @Excel(name = "订单编码")
    @NotBlank(message = "订单编码不能为空")
    private String clientOrderCode;

    /**
     * 产品id
     */
    @Excel(name = "产品id")
    @NotNull(message = "产品不能为空")
    private Long productId;


    /**
     * 产品编码
     */
    @Excel(name = "产品编码")
    @NotBlank(message = "产品编码不能为空")
    private String productCode;


    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    /**
     * 产品规格
     */
    @Excel(name = "产品规格")
    private String productSpec;

    /**
     * 客户id
     */
    private Long clientId;


    /**
     * 客户编码
     */
    private String clientCode;


    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String clientName;


    /**
     * 订货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;


    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;


    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String spec;


    /**
     * 订货数量
     */
    @Excel(name = "订货数量")
    @NotNull(message = "订货数量不能为空")
    private BigDecimal orderQuantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    @NotBlank(message = "单位不能为空")
    private String unitOfMeasure;


    /**
     * 质量要求
     */
    @Excel(name = "质量要求")
    private String qualityRequirement;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
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
     * 工单ID
     */
    private Long workorderId;

    /**
     * 工单编码
     */
    @Excel(name = "工单编码")
    private String workorderCode;

    /**
     * 工单名称
     */
    @Excel(name = "工单名称")
    private String workorderName;

}
