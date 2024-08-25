package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 生产领料单行对象 wm_issue_line
 *
 * @author yinjinlu
 * @date 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WmIssueLine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行ID
     */
    @TableId
    private Long lineId;

    /**
     * 领料单ID
     */
    @Excel(name = "领料单ID")
    private Long issueId;

    private Long materialStockId;

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
     * 领料数量
     */
    @Excel(name = "领料数量")
    private BigDecimal quantityIssued;

    /**
     * 领料批次号
     */
    @Excel(name = "领料批次号")
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
