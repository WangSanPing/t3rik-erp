package com.t3rik.mes.md.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;

/**
 * 设备资源对象 md_workstation_machine
 * 
 * @author yinjinlu
 * @date 2022-05-12
 */
public class MdWorkstationMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 工作站ID */
    @Excel(name = "工作站ID")
    private Long workstationId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long machineryId;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String machineryCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String machineryName;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setWorkstationId(Long workstationId) 
    {
        this.workstationId = workstationId;
    }

    public Long getWorkstationId() 
    {
        return workstationId;
    }
    public void setMachineryId(Long machineryId) 
    {
        this.machineryId = machineryId;
    }

    public Long getMachineryId() 
    {
        return machineryId;
    }
    public void setMachineryCode(String machineryCode) 
    {
        this.machineryCode = machineryCode;
    }

    public String getMachineryCode() 
    {
        return machineryCode;
    }
    public void setMachineryName(String machineryName) 
    {
        this.machineryName = machineryName;
    }

    public String getMachineryName() 
    {
        return machineryName;
    }
    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getAttr1() 
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("workstationId", getWorkstationId())
            .append("machineryId", getMachineryId())
            .append("machineryCode", getMachineryCode())
            .append("machineryName", getMachineryName())
            .append("quantity", getQuantity())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
