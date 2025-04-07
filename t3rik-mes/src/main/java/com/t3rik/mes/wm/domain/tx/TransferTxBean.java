package com.t3rik.mes.wm.domain.tx;

import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferTxBean extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long materialStockId;

    /**
     * 单据名称
     */
    private String sourceDocName;

    /** 产品物料ID */
    private Long itemId;

    /** 产品物料编码 */
    private String itemCode;

    /** 产品物料名称 */
    private String itemName;

    /** 规格型号 */
    private String specification;

    /** 单位 */
    private String unitOfMeasure;

    /** 入库批次号 */
    private String batchCode;

    private String workorderId;

    private String workorderCode;

    /** 供应商ID */
    private Long vendorId;

    /** 供应商编号 */
    private String vendorCode;

    /** 供应商名称 */
    private String vendorName;

    /** 供应商简称 */
    private String vendorNick;

    /** 移出仓库ID */
    private Long fromWarehouseId;

    /** 移出仓库编码 */
    private String fromWarehouseCode;

    /** 移出仓库名称 */
    private String fromWarehouseName;

    /** 移出库区ID */
    private Long fromLocationId;

    /** 移出库区编码 */
    private String fromLocationCode;

    /** 移出库区名称 */
    private String fromLocationName;

    /** 移出库位ID */
    private Long fromAreaId;

    /** 移出库位编码 */
    private String fromAreaCode;

    /** 移出库位名称 */
    private String fromAreaName;

    /** 移入仓库ID */
    private Long toWarehouseId;

    /** 移入仓库编码 */
    private String toWarehouseCode;

    /** 移入仓库名称 */
    private String toWarehouseName;

    /** 移入库区ID */
    private Long toLocationId;

    /** 移入库区编码 */
    private String toLocationCode;

    /** 移入库区名称 */
    private String toLocationName;

    /** 移入库位ID */
    private Long toAreaId;

    /** 移入库位编码 */
    private String toAreaCode;

    /** 移入库位名称 */
    private String toAreaName;

    private Date recptDate;

    /** 有效期 */
    private Date expireDate;

    /** 单据类型 */
    private String sourceDocType;

    /** 单据ID */
    private Long sourceDocId;

    /** 单据编号 */
    private String sourceDocCode;

    /** 单据行ID */
    private Long sourceDocLineId;

    /** 事务数量 */
    private BigDecimal transactionQuantity;

}
