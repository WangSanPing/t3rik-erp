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
     * 使用的是数据id,为了兼容表字段的ancestors字段,保存的是所有节点id,便于查找所有节点
     */
    MATERIAL("10000001", "原材料", "md_item_type"),
    /**
     * 半成品
     * 使用的是数据id,为了兼容表字段的ancestors字段,保存的是所有节点id,便于查找所有节点
     */
    SEMI_FINISHED_PRODUCTS("10000003", "半成品", "md_item_type"),
    /**
     * 产成品
     * 使用的是数据id,为了兼容表字段的ancestors字段,保存的是所有节点id,便于查找所有节点
     */
    PRODUCTS("10000004", "产成品", "md_item_type"),
    /**
     * 预置仓库
     */
    WH01_DEFAULT("WH000", "预置仓库编码", "wm_warehouse"),
    /**
     * 默认线边库-仓库
     */
    VIRTUAL_WH("XBK_VIRTUAL", "线边库仓库-虚拟", "wm_warehouse"),
    /**
     * 默认线边库-库区
     */
    VIRTUAL_WS("XBKKQ_VIRTUAL", "线边库库区-虚拟", "wm_warehouse"),
    /**
     * 默认线边库-库位
     */
    VIRTUAL_WA("XBKKW_VIRTUAL", "线边库库位-虚拟", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WH("WASTE_XBK_VIRTUAL", "废料线边库仓库-虚拟", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WS("WASTE_XBKKQ_VIRTUAL", "废料线边库库区-虚拟", "wm_warehouse"),
    /**
     * 废料线边库-库位
     */
    WASTE_VIRTUAL_WA("WASTE_XBKKW_VIRTUAL", "废料线边库库位-虚拟", "wm_warehouse"),
     /**
     * 生产废料-入库事务
     */
    TRANSACTION_TYPE_ITEM_WM_WASTE_IN("ITEM_WM_WASTE_IN", "生产废料-入库事务", "wm_transaction"),
    /**
     * 生产废料-出库事务
     */
    TRANSACTION_TYPE_ITEM_WM_WASTE_OUT("ITEM_WM_WASTE_OUT", "生产废料-出库事务", "wm_transaction"),
    /**
     * 生产退料-出库事务
     */
    TRANSACTION_TYPE_ITEM_RT_ISSUE_OUT("ITEM_RT_ISSUE_OUT", "生产退料-出库事务", "wm_transaction");



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
