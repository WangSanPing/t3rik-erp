package com.t3rik.hrm.md.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 职级管理对象 hrm_rank
*
* @author t3rik
* @date 2024-07-23
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "hrm_rank")
public class HrmRank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职级主键id */
    @TableId
    private Long rankId;


    /** 职级编码 */
    @Excel(name = "职级编码")
    private String rankCode;

    /** 职级层次 */
    @Excel(name = "职级层次")
    private String rankType;

    /** 职级层次名称 */
    @Excel(name = "职级层次名称")
    private String rankName;


    /** 薪资范围下限 */
    @Excel(name = "薪资范围下限")
    private BigDecimal salaryRangeMin;


    /** 薪资范围上限 */
    @Excel(name = "薪资范围上限")
    private BigDecimal salaryRangeMax;


    /** 逻辑删除字段 0:未删除 1:已删除 */
    @Excel(name = "逻辑删除字段 0:未删除 1:已删除")
    @TableLogic
    private Long deleted;


    /** 逻辑删除辅助字段 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "逻辑删除辅助字段", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField("deleteAt")
    private Date deleteAt;


    /** 乐观锁 */
    @Excel(name = "乐观锁")
    @Version
    private Long version;



}
