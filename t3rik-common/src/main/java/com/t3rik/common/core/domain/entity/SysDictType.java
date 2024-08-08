package com.t3rik.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.annotation.Excel.ColumnType;
import com.t3rik.common.core.domain.BaseEntity;
import com.t3rik.common.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Getter
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    @TableId
    private Long dictId;

    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Getter
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private StatusEnum status;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictId", getDictId())
                .append("dictName", getDictName())
                .append("dictType", getDictType())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
