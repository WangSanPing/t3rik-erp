package com.t3rik.mes.sales.domain;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 销售送货单对象 tran_orde
*
* @author 堇年
* @date 2024-09-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "tran_order")
public class TranOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 送货单id */
    @TableId
    private Long tranOrderId;


    /** 订单编码 */
    @Excel(name = "订单编码")
    private String tranOrderCode;


    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;


    /** 送货类别 */
    @Excel(name = "送货类别")
    private String tranOrderType;

    /** 日期 */
    @Excel(name = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private String tranDate;


    /** 状态 */
    @Excel(name = "状态")
    private String status;


    /** 客户id */
    @Excel(name = "客户id")
    private Long clientId;


    /** 客户编码 */
    @Excel(name = "客户编码")
    private String clientCode;


    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;

    /** 送货地址 */
    @Excel(name = "送货地址")
    private String address;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;


    /** 总重量 */
    @Excel(name = "总重量")
    private BigDecimal weight;


    /** 总件数 */
    @Excel(name = "总件数")
    private Long totalPic;

    /** 总数量 */
    @Excel(name = "总数量")
    private BigDecimal total;

    /** 币别 */
    @Excel(name = "币别")
    private String currency;


    /** 结账方式 */
    @Excel(name = "结账方式")
    private String payUp;


    /** 跟单 */
    @Excel(name = "跟单")
    private String followerMan;


    /** 业务员 */
    @Excel(name = "业务员")
    private String busMan;


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


    /** 销售送货单列信息 */
    @TableField(exist = false)
    private List<TranOrderLine> tranOrderLineList;


}
