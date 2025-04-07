package com.t3rik.mes.wm.domain.tx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSalseTxBean extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long materialStockId;

    /**
     * 单据名称
     */
    private String sourceDocName;

    /** 物料ID */
    private Long itemId;

    /** 物料编码 */
    private String itemCode;

    /** 物料名称 */
    private String itemName;

    /** 规格型号 */
    private String specification;

    /** 单位 */
    private String unitOfMeasure;

    /** 入库批次号 */
    private String batchCode;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 库区ID */
    private Long locationId;

    /** 库区编码 */
    private String locationCode;

    /** 库区名称 */
    private String locationName;

    /** 库位ID */
    private Long areaId;

    /** 库位编码 */
    private String areaCode;

    /** 库位名称 */
    private String areaName;

    /** 客户ID */
    private Long clientId;

    /** 供应商编号 */
    private String clientCode;

    /** 供应商名称 */
    private String clientName;

    /** 供应商简称 */
    private String clientNick;

    /** 生产工单ID */
    private Long workorderId;

    /** 生产工单编号 */
    private String workorderCode;

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

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date salseDate;

}
