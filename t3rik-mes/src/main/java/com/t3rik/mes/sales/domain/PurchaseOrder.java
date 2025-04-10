package com.t3rik.mes.sales.domain;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采购订单对象 purchase_order
 *
 * @author t3rik
 * @date 2024-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "purchase_order")
public class PurchaseOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId
    private String id;


    /**
     * 单号
     */
    @Excel(name = "单号")
    private String poOrderNo;


    /**
     * 手工单号
     */
    @Excel(name = "手工单号")
    private String hwNo;


    /**
     * 供应商编号
     */
    @Excel(name = "供应商编号")
    private String vendorNo;

    /**
     * 供应商编号
     */
    @Excel(name = "供应商简称")
    private String vendorName;

    /**
     * 结账方式
     */
    @Excel(name = "结账方式")
    private String payUp;


    /**
     * 落单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "落单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date poOrderDate;


    /**
     * 采购员
     */
    @Excel(name = "采购员")
    private String poOrderUser;


    /**
     * 部门id
     */
    private String deptId;


    /**
     * 送货地址
     */
    @Excel(name = "送货地址")
    private String tranAddress;


    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tranDate;


    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remake;


    /**
     * 开票人
     */
    @Excel(name = "开票人")
    private String billUser;


    /**
     * 送审
     */
    @Excel(name = "送审")
    private String review;


    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;


    /**
     * 送审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送审日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date partDate;


    /**
     * 送审人
     */
    @Excel(name = "送审人")
    private String partUser;


    /**
     * 审核日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditDate;


    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String auditUser;


    /**
     * 结单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;


    /**
     * 结单人
     */
    @Excel(name = "结单人")
    private String endUser;


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
     * 逻辑删除字段 0:未删除 1:已删除
     */
    private Long deleted;


    /**
     * 逻辑删除辅助字段
     */
    private Date deleteat;


    /**
     * 乐观锁
     */
    private Long version;


    /**
     * 采购订单列表信息
     */
    private List<PurchaseOrderLine> purchaseOrderLineList;


}
