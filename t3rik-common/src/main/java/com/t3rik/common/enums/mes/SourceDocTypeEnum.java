package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 库存操作类型
 *
 * @author t3rik
 * @date 2025/4/3 19:11
 */
@Getter
@AllArgsConstructor
public enum SourceDocTypeEnum {
    ISSUE("ISSUE", "wm_issue_header", "生产领料"),
    RTISSUE("RTISSUE", "wm_rt_issue", "生产退料"),
    WMWASTE("WMWASTE", "wm_waste_header", "生产废料"),
    ITEM_RECPT("ITEM_RECPT", "wm_item_recpt", "物料入库"),
    ITEM_CONSUME("ITEM_CONSUME", "wm_item_consume", "物料消耗"),
    PRODUCT_RECPT("PRODUCT_RECPT", "wm_product_recpt", "产品入库"),
    PRODUCT_SALE("PRODUCT_SALE", "wm_product_salse", "销售出库"),
    PRODUCT_PRODUCE("PRODUCT_PRODUCE", "wm_product_produce", "产品生产"),
    TRANSFER("TRANSFER", "wm_transfer", "转移单"),
    PRODUCT_RT("PRODUCT_RT", "wm_rt_salse", "销售退货"),
    TRAN_SALES("TRAN_SALES", "tran_order", "销售送货出库"),
    ITEM_RTV("ITEM_RTV", "wm_rt_vendor", "原材料退回供应商"),
    OUTSOURCE_ISSUE("OUTSOURCE_ISSUE", "wm_outsource_issue", "外协领料");

    private final String code;
    private final String table;
    private final String desc;

    public static final Map<String, SourceDocTypeEnum> SOURCE_DOC_TYPE_ENUM_MAP;

    static {
        SOURCE_DOC_TYPE_ENUM_MAP = new HashMap<>();
        for (SourceDocTypeEnum value : SourceDocTypeEnum.values()) {
            SOURCE_DOC_TYPE_ENUM_MAP.put(value.getCode(), value);
        }
    }

    public static SourceDocTypeEnum getEnumByCode(String code) {
        return SOURCE_DOC_TYPE_ENUM_MAP.get(code);
    }
}
