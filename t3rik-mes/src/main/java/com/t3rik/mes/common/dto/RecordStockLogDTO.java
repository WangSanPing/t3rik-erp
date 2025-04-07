package com.t3rik.mes.common.dto;

import com.t3rik.common.enums.mes.LogChangeTypeEnum;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 记录日志
 *
 * @author t3rik
 * @date 2025/4/4 17:21
 */
@Data
public class RecordStockLogDTO {

    /**
     * 库存记录对象
     */
    private WmMaterialStock wmMaterialStock;
    /**
     * 原有库存数量
     */
    private BigDecimal oldQuantity;
    /**
     * 变化库存数量
     */
    private BigDecimal changeQuantity;
    /**
     * 库存变化类型
     */
    private LogChangeTypeEnum logChangeTypeEnum;

    /**
     * 单据ID
     */
    private Long sourceDocId;

    /**
     * 单据编号
     */
    private String sourceDocCode;

    /**
     * 单据类型
     */
    private String sourceDocType;

    /**
     * 单据名称
     */
    private String sourceDocName;

    /**
     * 单据行ID
     */
    private Long sourceLineId;
}
