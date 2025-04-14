package com.t3rik.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t3rik.common.core.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("md_item_type")
public class MdItemType extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableId
    private Long itemTypeId;
    @Size(min = 0, max = 64, message = "物料分类编码长度不能超过64个字符")
    private String itemTypeCode;
    @NotBlank(message = "物料分类名称不能为空")
    @Size(min = 0, max = 255, message = "物料分类名称长度不能超过255个字符")
    private String itemTypeName;
    private Long parentTypeId;
    private String ancestors;
    @NotBlank(message = "请指定是物料分类还是产品分类")
    private String itemOrProduct;
    private Integer orderNum;
    @NotBlank(message = "请设置是否启用")
    private String enableFlag;
    private String attr1;
    private String attr2;
    private Integer attr3;
    private Integer attr4;
    @TableField(exist = false)
    private List<MdItemType> children = new ArrayList<MdItemType>();
    private Integer version;


}
