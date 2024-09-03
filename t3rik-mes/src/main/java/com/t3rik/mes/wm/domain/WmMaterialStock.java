package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存记录对象 wm_material_stock
 *
 * @author yinjinlu
 * @date 2022-05-24
 */
public class WmMaterialStock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 事务ID
     */
    @TableId
    private Long materialStockId;

    /**
     * 物料类型ID
     */
    @Excel(name = "物料类型ID")
    private Long itemTypeId;

    /**
     * 产品物料ID
     */
    @Excel(name = "产品物料ID")
    private Long itemId;

    /**
     * 产品物料编码
     */
    @Excel(name = "产品物料编码")
    private String itemCode;

    /**
     * 产品物料名称
     */
    @Excel(name = "产品物料名称")
    private String itemName;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specification;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitOfMeasure;

    /**
     * 入库批次号
     */
    @Excel(name = "入库批次号")
    private String batchCode;

    /**
     * 仓库ID
     */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /**
     * 库区ID
     */
    @Excel(name = "库区ID")
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
     * 库位ID
     */
    @Excel(name = "库位ID")
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
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @Excel(name = "供应商编号")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String vendorName;

    /**
     * 供应商简称
     */
    @Excel(name = "供应商简称")
    private String vendorNick;

    /**
     * 在库数量
     */
    @Excel(name = "在库数量")
    private BigDecimal quantityOnhand;

    /**
     * 生产工单ID
     */
    private Long workorderId;

    /**
     * 生产工单编号
     */
    @Excel(name = "生产工单编号")
    private String workorderCode;

    /**
     * 库存有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date recptDate;

    /**
     * 库存有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "库存有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 预留字段1
     */
    @Excel(name = "预留字段1")
    private String attr1;

    /**
     * 预留字段2
     */
    @Excel(name = "预留字段2")
    private String attr2;

    /**
     * 预留字段3
     */
    @Excel(name = "预留字段3")
    private Long attr3;

    /**
     * 预留字段4
     */
    @Excel(name = "预留字段4")
    private Long attr4;

    public void setMaterialStockId(Long materialStockId) {
        this.materialStockId = materialStockId;
    }

    public Long getMaterialStockId() {
        return materialStockId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorNick(String vendorNick) {
        this.vendorNick = vendorNick;
    }

    public String getVendorNick() {
        return vendorNick;
    }

    public void setQuantityOnhand(BigDecimal quantityOnhand) {
        this.quantityOnhand = quantityOnhand;
    }

    public BigDecimal getQuantityOnhand() {
        return quantityOnhand;
    }

    public Date getRecptDate() {
        return recptDate;
    }

    public void setRecptDate(Date recptDate) {
        this.recptDate = recptDate;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public String getWorkorderCode() {
        return workorderCode;
    }

    public void setWorkorderCode(String workorderCode) {
        this.workorderCode = workorderCode;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
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

    @Override
    public String toString() {
        return "WmMaterialStock{" +
                "materialStockId=" + materialStockId +
                ", itemTypeId=" + itemTypeId +
                ", itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specification='" + specification + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", locationId=" + locationId +
                ", locationCode='" + locationCode + '\'' +
                ", locationName='" + locationName + '\'' +
                ", areaId=" + areaId +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", vendorId=" + vendorId +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorNick='" + vendorNick + '\'' +
                ", quantityOnhand=" + quantityOnhand +
                ", workorderId=" + workorderId +
                ", workorderCode='" + workorderCode + '\'' +
                ", recptDate=" + recptDate +
                ", expireDate=" + expireDate +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                '}';
    }
}
