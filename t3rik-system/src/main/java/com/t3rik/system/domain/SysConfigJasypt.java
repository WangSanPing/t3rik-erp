package com.t3rik.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.enums.EnableFlagEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 加密配置对象 sys_config_jasypt
*
* @author t3rik
* @date 2024-07-01
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_config_jasypt")
public class SysConfigJasypt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @TableId
    private Long configId;


    /** 参数名称 */
    @Excel(name = "参数名称")
    private String configName;


    /** 参数键名 */
    @Excel(name = "参数键名")
    private String configKey;


    /** 参数键值 */
    @Excel(name = "参数键值")
    private String configValue;


    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;



}
