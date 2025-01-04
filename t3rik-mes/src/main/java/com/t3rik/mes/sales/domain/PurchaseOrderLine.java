package com.t3rik.mes.sales.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.t3rik.common.annotation.Excel;

/**
 * 采购订单列表对象 purchase_order_line
 * 
 * @author t3rik
 * @date 2024-11-02
 */
public class PurchaseOrderLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 明细单号 */
    @Excel(name = "明细单号")
    private String poOrderLineNo;

    /** 采购单号 */
    @Excel(name = "采购单号")
    private Long poOrderNo;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String productNo;

    /** 供应商编号 */
    @Excel(name = "供应商简称")
    private String vendorName;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String productName;

    /** 色号 */
    @Excel(name = "色号")
    private String colorNo;

    /** 颜色名称 */
    @Excel(name = "颜色名称")
    private String colorName;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 重量 */
    @Excel(name = "重量")
    private Long weight;

    /** 单价 */
    @Excel(name = "单价")
    private Long price;

    /** 折扣 */
    @Excel(name = "折扣")
    private Long discount;

    /** 折扣总价 */
    @Excel(name = "折扣总价")
    private Long addMoney;

    /** 总价 */
    @Excel(name = "总价")
    private Long money;

    /** 送货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tranDate;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private Long shQty;

    /** 退货数量 */
    @Excel(name = "退货数量")
    private Long thQty;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Long inQty;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 结账方式 */
    @Excel(name = "结账方式")
    private String payUp;

    /** 客户代号 */
    @Excel(name = "客户代号")
    private String cusNo;

    /** 实收数量 */
    @Excel(name = "实收数量")
    private Long ssQty;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    /** 逻辑删除字段 0:未删除 1:已删除 */
    @Excel(name = "逻辑删除字段 0:未删除 1:已删除")
    private Long deleted;

    /** 逻辑删除辅助字段 */
    private Date deleteat;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Long version;

    /** 总重量 */
    @Excel(name = "总重量")
    private Long totalWeight;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setPoOrderLineNo(String poOrderLineNo) 
    {
        this.poOrderLineNo = poOrderLineNo;
    }

    public String getPoOrderLineNo() 
    {
        return poOrderLineNo;
    }
    public void setPoOrderNo(Long poOrderNo) 
    {
        this.poOrderNo = poOrderNo;
    }

    public Long getPoOrderNo() 
    {
        return poOrderNo;
    }
    public void setProductNo(String productNo) 
    {
        this.productNo = productNo;
    }

    public String getProductNo() 
    {
        return productNo;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setColorNo(String colorNo) 
    {
        this.colorNo = colorNo;
    }

    public String getColorNo() 
    {
        return colorNo;
    }
    public void setColorName(String colorName) 
    {
        this.colorName = colorName;
    }

    public String getColorName() 
    {
        return colorName;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setQty(Long qty) 
    {
        this.qty = qty;
    }

    public Long getQty() 
    {
        return qty;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setDiscount(Long discount) 
    {
        this.discount = discount;
    }

    public Long getDiscount() 
    {
        return discount;
    }
    public void setAddMoney(Long addMoney) 
    {
        this.addMoney = addMoney;
    }

    public Long getAddMoney() 
    {
        return addMoney;
    }
    public void setMoney(Long money) 
    {
        this.money = money;
    }

    public Long getMoney() 
    {
        return money;
    }
    public void setTranDate(Date tranDate) 
    {
        this.tranDate = tranDate;
    }

    public Date getTranDate() 
    {
        return tranDate;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }
    public void setShQty(Long shQty) 
    {
        this.shQty = shQty;
    }

    public Long getShQty() 
    {
        return shQty;
    }
    public void setThQty(Long thQty) 
    {
        this.thQty = thQty;
    }

    public Long getThQty() 
    {
        return thQty;
    }
    public void setInQty(Long inQty) 
    {
        this.inQty = inQty;
    }

    public Long getInQty() 
    {
        return inQty;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setPayUp(String payUp) 
    {
        this.payUp = payUp;
    }

    public String getPayUp() 
    {
        return payUp;
    }
    public void setCusNo(String cusNo) 
    {
        this.cusNo = cusNo;
    }

    public String getCusNo() 
    {
        return cusNo;
    }
    public void setSsQty(Long ssQty) 
    {
        this.ssQty = ssQty;
    }

    public Long getSsQty() 
    {
        return ssQty;
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
    public void setDeleted(Long deleted) 
    {
        this.deleted = deleted;
    }

    public Long getDeleted() 
    {
        return deleted;
    }
    public void setDeleteat(Date deleteat) 
    {
        this.deleteat = deleteat;
    }

    public Date getDeleteat() 
    {
        return deleteat;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }
    public void setTotalWeight(Long totalWeight) 
    {
        this.totalWeight = totalWeight;
    }

    public Long getTotalWeight() 
    {
        return totalWeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("poOrderLineNo", getPoOrderLineNo())
            .append("poOrderNo", getPoOrderNo())
            .append("productNo", getProductNo())
            .append("productName", getProductName())
            .append("colorNo", getColorNo())
            .append("colorName", getColorName())
            .append("unit", getUnit())
            .append("qty", getQty())
            .append("weight", getWeight())
            .append("price", getPrice())
            .append("discount", getDiscount())
            .append("addMoney", getAddMoney())
            .append("money", getMoney())
            .append("tranDate", getTranDate())
            .append("memo", getMemo())
            .append("shQty", getShQty())
            .append("thQty", getThQty())
            .append("inQty", getInQty())
            .append("status", getStatus())
            .append("payUp", getPayUp())
            .append("cusNo", getCusNo())
            .append("ssQty", getSsQty())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .append("deleteat", getDeleteat())
            .append("version", getVersion())
            .append("totalWeight", getTotalWeight())
            .toString();
    }
}
