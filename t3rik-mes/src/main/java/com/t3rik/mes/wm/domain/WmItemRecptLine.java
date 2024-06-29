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
 * 物料入库单行对象 wm_item_recpt_line
 *
 * @author yinjinlu
 * @date 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WmItemRecptLine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行ID
     */
    @TableId
    private Long lineId;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    /**
     * 入库单ID
     */
    @Excel(name = "入库单ID")
    private Long recptId;

    /**
     * 产品物料ID
     */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /**
     * 产品物料编码
     */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /**
     * 产品物料名称
     */
    @Excel(name = "产品物料名称")
    private String itemName;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specification;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /**
     * 入库数量
     */
    @Excel(name = "入库数量")
    private BigDecimal quantityRecived;

    /**
     * 入库批次号
     */
    @Excel(name = "入库批次号")
    private String batchCode;

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
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 是否来料检验
     */
    private String iqcCheck;

    /**
     * 来料检验单ID
     */
    private Long iqcId;

    /**
     * 来料检验单编码
     */
    private String iqcCode;

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
