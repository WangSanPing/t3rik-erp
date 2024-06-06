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
 * 库区设置对象 wm_storage_location
 *
 * @author yinjinlu
 * @date 2022-05-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WmStorageLocation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 库区ID
     */
    @TableId
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
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /**
     * 面积
     */
    @Excel(name = "面积")
    private BigDecimal area;

    /**
     * 是否开启库位管理
     */
    @Excel(name = "是否开启库位管理")
    private String areaFlag;

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
    private List<WmStorageArea> children;


}
