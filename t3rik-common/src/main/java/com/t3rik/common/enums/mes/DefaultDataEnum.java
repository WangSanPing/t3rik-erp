package com.t3rik.common.enums.mes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认数据,code和数据库中数据id对应
 */
@Getter
@AllArgsConstructor
public enum DefaultDataEnum {
    /**
     * 原材料
     */
    MATERIAL("10000001L", "原材料", "md_item_type"),
    /**
     * 半成品
     */
    SEMI_FINISHED_PRODUCTS("10000003L", "半成品", "md_item_type"),
    /**
     * 产成品
     */
    PRODUCTS("10000004L", "产成品", "md_item_type"),
    /**
     * 预置仓库
     */
    WH01_DEFAULT("WH000", "预置仓库编码", "wm_warehouse"),
    /**
     * 默认线边库-仓库
     */
    VIRTUAL_WH("XBK_VIRTUAL", "默认线边库-仓库", "wm_warehouse"),
    /**
     * 默认线边库-库区
     */
    VIRTUAL_WS("XBKKQ_VIRTUAL", "默认线边库-库区", "wm_warehouse"),
    /**
     * 默认线边库-库位
     */
    VIRTUAL_WA("XBKKW_VIRTUAL", "默认线边库-库位", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WH("WASTE_XBK_VIRTUAL", "废料线边库-库位", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WS("WASTE_XBKKQ_VIRTUAL", "废料线边库-库位", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WA("WASTE_XBKKW_VIRTUAL", "废料线边库-库位", "wm_warehouse"),
     /**
     * 生产废料-入库事务
     */
    TRANSACTION_TYPE_ITEM_WM_WASTE_IN("ITEM_WM_WASTE_IN", "生产废料-入库事务", "wm_transaction"),
    /**
     * 生产废料-出库事务
     */
    TRANSACTION_TYPE_ITEM_WM_WASTE_OUT("ITEM_WM_WASTE_OUT", "生产废料-出库事务", "wm_transaction");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
    /**
     * 数据对应的表
     */
    private final String table;

    /**
     * 枚举集合
     */
    public static final Map<String, DefaultDataEnum> DEFAULT_DATA_ENUM_MAP;

    static {
        // 初始化加入集合
        DEFAULT_DATA_ENUM_MAP = new HashMap<>();
        for (DefaultDataEnum value : DefaultDataEnum.values()) {
            DEFAULT_DATA_ENUM_MAP.put(value.getCode(), value);
        }
    }

    /**
     * 根据code获取对应的枚举
     */
    public static DefaultDataEnum getEnumByCode(String code) {
        return DEFAULT_DATA_ENUM_MAP.get(code);
    }
}
