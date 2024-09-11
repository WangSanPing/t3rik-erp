package com.t3rik.hrm.sm.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工花名册对象 hrm_staff
 *
 * @author 施子安
 * @date 2024-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "hrm_staff")
public class HrmStaff extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 员工主键id
     */
    @TableId
    private Long staffId;


    /**
     * 员工编码
     */
    @Excel(name = "员工编码")
    private String staffCode;


    /**
     * 员工名称
     */
    @Excel(name = "员工名称")
    private String staffName;


    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactPhone;


    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别", readConverterExp = "0-男,1-女,2-未知")
    private Long sex;


    /**
     * 民族
     */
    @Excel(name = "民族")
    private String ethnicity;


    /**
     * 出生年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;


    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;


    /**
     * 婚姻状况（0-未婚，1-已婚）
     */
    @Excel(name = "婚姻状况", readConverterExp = "0-未婚，1-已婚")
    private Long maritalStatus;


    /**
     * 政治面貌（0-群众，1-党员）
     */
    @Excel(name = "政治面貌", readConverterExp = "0-群众，1-党员")
    private Long politicalOutlook;


    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;


    /**
     * 籍贯
     */
    @Excel(name = "籍贯")
    private String origin;


    /**
     * 户口详细地址
     */
    @Excel(name = "户口详细地址")
    private String householdAddress;


    /**
     * 现住址
     */
    @Excel(name = "现住址")
    private String currentAddress;


    /**
     * 紧急联系人
     */
    @Excel(name = "紧急联系人")
    private String emergencyContact;


    /**
     * 关系
     */
    @Excel(name = "关系")
    private String relationship;


    /**
     * 紧急联系电话
     */
    @Excel(name = "紧急联系电话")
    private String emergencyContactPhone;


    /**
     * 学历（0-初中，1-高中，2-专科，4-本科，5-研究生，6-博士）
     */
    @Excel(name = "学历", readConverterExp = "0-初中，1-高中，2-专科，4-本科，5-研究生，6-博士")
    private Long education;


    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinedTime;


    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveTime;


    /**
     * 状态（0-备选，1-面试，2-面试通过，3-入职申请，4-入职通过）
     */
    @Excel(name = "状态", readConverterExp = "0-备选，1-面试，2-面试通过，3-入职申请，4-入职通过")
    private Long status;

    /**
     * 逻辑删除字段 0:未删除 1:已删除
     */
    @TableLogic
    private Long deleted;

    /**
     * 逻辑删除辅助字段
     */
    @TableField("deleteAt")
    private Date deleteAt;

    /**
     * 乐观锁
     */
    @Version
    private Long version;

    /**
     * 实际薪资
     */
    @Excel(name = "实际薪资")
    private BigDecimal actualSalary;


    /**
     * 职级主键id
     */
    @Excel(name = "职级主键id")
    private Long rankId;


    /**
     * 职级编码
     */
    @Excel(name = "职级编码")
    private String rankCode;


    /**
     * 职级层次名称
     */
    @Excel(name = "职级层次名称")
    private String rankName;

}
