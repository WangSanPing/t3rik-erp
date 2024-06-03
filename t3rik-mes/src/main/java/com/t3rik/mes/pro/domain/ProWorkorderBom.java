package com.t3rik.mes.pro.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 生产工单BOM组成对象 pro_workorder_bom
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProWorkorderBom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * BOM行ID
     */
    @TableId
    private Long lineId;

    /**
     * 生产工单ID
     */
    @Excel(name = "生产工单ID")
    private Long workorderId;

    /**
     * BOM物料ID
     */
    @Excel(name = "BOM物料ID")
    private Long itemId;

    /**
     * BOM物料编号
     */
    @Excel(name = "BOM物料编号")
    private String itemCode;

    /**
     * BOM物料名称
     */
    @Excel(name = "BOM物料名称")
    private String itemName;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String itemSpc;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /**
     * 物料产品标识
     */
    @Excel(name = "物料产品标识")
    private String itemOrProduct;

    /**
     * 预计使用量
     */
    @Excel(name = "预计使用量")
    private BigDecimal quantity;

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
