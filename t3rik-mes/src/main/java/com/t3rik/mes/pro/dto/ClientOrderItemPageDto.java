package com.t3rik.mes.pro.dto;

import com.t3rik.mes.pro.domain.ProClientOrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 客户订单数据扩展DTO
 *
 * @author t3rik
 * @date 2024/5/17 16:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientOrderItemPageDto extends ProClientOrderItem {

    /**
     * 库存量
     */
    private Double quantityOnhand;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
}
