package com.t3rik.mes.pro.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.enums.StatusEnum;

/**
* 客户订单对象 pro_client_order
*
* @author t3rik
* @date 2024-05-01
*/
@TableName(value = "pro_client_order")
public class ProClientOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户订单id */
    @TableId
    private Long clientOrderId;


    /** 订单编码 */
    @Excel(name = "订单编码")
    private String clientOrderCode;


    /** 客户id */
    private Long clientId;


    /** 客户编码 */
    private String clientCode;


    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;


    /** 订货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;


    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;


    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;


    /** 订货数量 */
    @Excel(name = "订货数量")
    private Long orderQuantity;


    /** 单位id，默认：个 */
    private Long measureId;


    /** 单位编码 */
    private String measureCode;


    /** 单位 */
    @Excel(name = "单位")
    private String measureName;


    /** 质量要求 */
    @Excel(name = "质量要求")
    private String qualityRequirement;


    /** 预留字段1 */
    private String attr1;


    /** 预留字段2 */
    private String attr2;


    /** 预留字段3 */
    private Long attr3;


    /** 预留字段4 */
    private Long attr4;


    /** 创建人id */
    private Long createUserId;


    /** 更新人id */
    private Long updateUserId;


    public void setClientOrderId(Long clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public Long getClientOrderId() {
        return clientOrderId;
    }
    public void setClientOrderCode(String clientOrderCode) {
        this.clientOrderCode = clientOrderCode;
    }

    public String getClientOrderCode() {
        return clientOrderCode;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientCode() {
        return clientCode;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }
    public void setOrderQuantity(Long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Long getOrderQuantity() {
        return orderQuantity;
    }
    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public Long getMeasureId() {
        return measureId;
    }
    public void setMeasureCode(String measureCode) {
        this.measureCode = measureCode;
    }

    public String getMeasureCode() {
        return measureCode;
    }
    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getMeasureName() {
        return measureName;
    }
    public void setQualityRequirement(String qualityRequirement) {
        this.qualityRequirement = qualityRequirement;
    }

    public String getQualityRequirement() {
        return qualityRequirement;
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
            .append("clientOrderId", getClientOrderId())
            .append("clientOrderCode", getClientOrderCode())
            .append("clientId", getClientId())
            .append("clientCode", getClientCode())
            .append("clientName", getClientName())
            .append("orderDate", getOrderDate())
            .append("deliveryDate", getDeliveryDate())
            .append("spec", getSpec())
            .append("orderQuantity", getOrderQuantity())
            .append("measureId", getMeasureId())
            .append("measureCode", getMeasureCode())
            .append("measureName", getMeasureName())
            .append("qualityRequirement", getQualityRequirement())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createUserId", getCreateUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateUserId", getUpdateUserId())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
