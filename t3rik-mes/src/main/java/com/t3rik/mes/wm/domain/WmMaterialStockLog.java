package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存变化日志对象 wm_material_stock_log
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "wm_material_stock_log")
public class WmMaterialStockLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId
    private Long logId;


    /**
     * 关联库存ID
     */
    private Long materialStockId;


    /**
     * 物料ID
     */
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
     * 变化类型（10:入库, 20:出库, 30:盘点等）
     */
    @Excel(name = "变化类型", readConverterExp = "1=0:入库,,2=0:出库,,3=0:盘点等")
    private Integer changeType;


    /**
     * 变化前库存数量
     */
    @Excel(name = "变化前库存数量")
    private BigDecimal beforeQuantity;


    /**
     * 变化后库存数量
     */
    @Excel(name = "变化后库存数量")
    private BigDecimal afterQuantity;


    /**
     * 库存变化数量
     */
    @Excel(name = "库存变化数量")
    private BigDecimal changeQuantity;


    /**
     * 被消耗单据ID
     */
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
     * 单据类型
     */
    @Excel(name = "单据类型")
    private String sourceDocType;


    /**
     * 被消耗单据行ID
     */
    private Long sourceLineId;


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
     * 生产工单名称
     */
    @Excel(name = "生产工单名称")
    private String workorderName;


    /**
     * 操作人ID
     */
    private Long operationUserId;


    /**
     * 操作人姓名
     */
    @Excel(name = "操作人姓名")
    private String operationBy;


    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operationTime;


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
     * 变化描述
     */
    @Excel(name = "变化描述")
    private String description;

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


}
