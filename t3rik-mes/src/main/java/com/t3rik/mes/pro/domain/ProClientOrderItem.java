package com.t3rik.mes.pro.domain;

import java.math.BigDecimal;

import com.t3rik.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.enums.StatusEnum;

/**
* 客户订单材料
对象 pro_client_order_item
*
* @author t3rik
* @date 2024-05-07
*/
@TableName(value = "pro_client_order_item")
public class ProClientOrderItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private Long orderItemId;


    /** 客户订单表id */
    @Excel(name = "客户订单表id")
    private Long clientOrderId;


    /** 物料产品ID */
    @Excel(name = "物料产品ID")
    private Long itemId;


    /** 产品物料标识 */
    @Excel(name = "产品物料标识")
    private String itemOrProduct;


    /** 物料使用数量 */
    @Excel(name = "物料使用数量")
    private BigDecimal quantity;


    /** 单位 */
    @Excel(name = "单位")
    private String unitOfMeasure;


    /** 层级，字典表：mes_item_level */
    @Excel(name = "层级，字典表：mes_item_level")
    private String level;


    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String attr1;


    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String attr2;


    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private Long attr3;


    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private Long attr4;


    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createUserId;


    /** 更新人id */
    @Excel(name = "更新人id")
    private Long updateUserId;


    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }
    public void setClientOrderId(Long clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public Long getClientOrderId() {
        return clientOrderId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }
    public void setItemOrProduct(String itemOrProduct) {
        this.itemOrProduct = itemOrProduct;
    }

    public String getItemOrProduct() {
        return itemOrProduct;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr1() {
        return attr1;
    }
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr2() {
        return attr2;
    }
    public void setAttr3(Long attr3) {
        this.attr3 = attr3;
    }

    public Long getAttr3() {
        return attr3;
    }
    public void setAttr4(Long attr4) {
        this.attr4 = attr4;
    }

    public Long getAttr4() {
        return attr4;
    }
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("orderItemId", getOrderItemId())
            .append("clientOrderId", getClientOrderId())
            .append("itemId", getItemId())
            .append("itemOrProduct", getItemOrProduct())
            .append("quantity", getQuantity())
            .append("unitOfMeasure", getUnitOfMeasure())
            .append("level", getLevel())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("createUserId", getCreateUserId())
            .append("updateUserId", getUpdateUserId())
            .toString();
    }
}
