package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料入库单对象 wm_item_recpt
 *
 * @author yinjinlu
 * @date 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WmItemRecpt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 入库单ID
     */
    @TableId
    private Long recptId;

    /**
     * 总金额
     */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /**
     * 入库单编号
     */
    @Excel(name = "入库单编号")
    private String recptCode;

    /**
     * 入库单名称
     */
    @Excel(name = "入库单名称")
    private String recptName;

    /**
     * 来料检验单ID
     */
    @Excel(name = "来料检验单ID")
    private Long iqcId;

    /**
     * 来料检验单编号
     */
    @Excel(name = "来料检验单编号")
    private String iqcCode;

    /**
     * 采购订单编号
     */
    @Excel(name = "采购订单编号")
    private String poCode;

    /**
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String vendorName;

    /**
     * 供应商简称
     */
    @Excel(name = "供应商简称")
    private String vendorNick;

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
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recptDate;

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
