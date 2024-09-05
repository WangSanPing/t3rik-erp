package com.t3rik.mes.md.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.enums.EnableFlagEnum;
import com.t3rik.common.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
* 颜色对象 md_product_color
*
* @author t3rik
* @date 2024-08-29
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "md_product_color")
public class MdProductColor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId
    private String id;


    /** 色号 */
    @Excel(name = "色号")
    private String colorCode;


    /** 颜色 */
    @Excel(name = "颜色")
    private String colorName;


    /**
     * 状态（0正常 1停用）
     */
    @Getter
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private StatusEnum status;



}
