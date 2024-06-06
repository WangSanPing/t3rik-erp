package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 仓库设置对象 wm_warehouse
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WmWarehouse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @TableId
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
     * 位置
     */
    @Excel(name = "位置")
    private String location;

    /**
     * 面积
     */
    @Excel(name = "面积")
    private BigDecimal area;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String charge;

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

    @TableField(exist = false)
    private List<WmStorageLocation> children;

}
