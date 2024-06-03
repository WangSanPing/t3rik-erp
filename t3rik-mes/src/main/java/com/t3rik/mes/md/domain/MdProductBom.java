package com.t3rik.mes.md.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 产品BOM关系对象 md_product_bom
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MdProductBom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    @TableId
    private Long bomId;

    /**
     * 物料产品ID
     */
    @Excel(name = "物料产品ID")
    private Long itemId;

    /**
     * BOM物料ID
     */
    @Excel(name = "BOM物料ID")
    private Long bomItemId;

    /**
     * BOM物料编码
     */
    @Excel(name = "BOM物料编码")
    private String bomItemCode;

    /**
     * BOM物料名称
     */
    @Excel(name = "BOM物料名称")
    private String bomItemName;

    /**
     * BOM物料规格
     */
    @Excel(name = "BOM物料规格")
    private String bomItemSpec;

    /**
     * BOM物料单位
     */
    @Excel(name = "BOM物料单位")
    private String unitOfMeasure;

    /**
     * 产品物料标识
     */
    @Excel(name = "产品物料标识")
    private String itemOrProduct;

    /**
     * 物料使用比例
     */
    @Excel(name = "物料使用比例")
    private BigDecimal quantity;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private String enableFlag;

    /**
     * 级别-塑料袋行业用
     */
    private String level;

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
