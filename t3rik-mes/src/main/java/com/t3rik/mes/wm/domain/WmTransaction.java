package com.t3rik.mes.wm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存事务对象 wm_transaction
 *
 * @author yinjinlu
 * @date 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WmTransaction extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 事务ID
     */
    private Long transactionId;

    /**
     * 事务类型
     */
    @Excel(name = "事务类型")
    private String transactionType;

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
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @Excel(name = "供应商编号")
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
     * 单据类型
     */
    @Excel(name = "单据类型")
    private String sourceDocType;

    /**
     * 单据ID
     */
    @Excel(name = "单据ID")
    private Long sourceDocId;

    /**
     * 单据编号
     */
    @Excel(name = "单据编号")
    private String sourceDocCode;

    /**
     * 单据名称
     */
    @Excel(name = "单据名称")
    private String sourceDocName;

    /**
     * 单据行ID
     */
    @Excel(name = "单据行ID")
    private Long sourceDocLineId;

    /**
     * 库存记录ID
     */
    @Excel(name = "库存记录ID")
    private Long materialStockId;

    /**
     * 库存方向
     */
    @Excel(name = "库存方向")
    private Integer transactionFlag;

    /**
     * 事务数量
     */
    @Excel(name = "事务数量")
    private BigDecimal transactionQuantity;

    /**
     * 事务日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "事务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transactionDate;

    /**
     * 关联的事务ID
     */
    @Excel(name = "关联的事务ID")
    private Long relatedTransactionId;

    /**
     * ERP账期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "ERP账期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date erpDate;

    /**
     * 生产工单ID
     */
    private Long workorderId;

    /**
     * 生产工单编号
     */
    @Excel(name = "生产工单编号")
    private String workorderCode;

    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date recptDate;

    /**
     * 库存有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "库存有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 是否检查库存量
     * 如果设置为True则库存不允许为负
     */
    private boolean storageCheckFlag;

    /**
     * 预留字段1
     */
    @Excel(name = "预留字段1")
    private String attr1;

    /**
     * 预留字段2
     */
    @Excel(name = "预留字段2")
    private String attr2;

    /**
     * 预留字段3
     */
    @Excel(name = "预留字段3")
    private Long attr3;

    /**
     * 预留字段4
     */
    @Excel(name = "预留字段4")
    private Long attr4;

}
