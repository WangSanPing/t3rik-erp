package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 库位设置对象 wm_storage_area
 *
 * @author yinjinlu
 * @date 2022-05-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WmStorageArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 库位ID
     */
    @TableId
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
     * 库区ID
     */
    @Excel(name = "库区ID")
    private Long locationId;

    /**
     * 面积
     */
    @Excel(name = "面积")
    private BigDecimal area;

    /**
     * 最大载重量
     */
    @Excel(name = "最大载重量")
    private BigDecimal maxLoa;

    /**
     * 库位位置X
     */
    @Excel(name = "库位位置X")
    private Long positionX;

    /**
     * 库位位置y
     */
    @Excel(name = "库位位置y")
    private Long positionY;

    /**
     * 库位位置z
     */
    @Excel(name = "库位位置z")
    private Long positionZ;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private String enableFlag;

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


}
