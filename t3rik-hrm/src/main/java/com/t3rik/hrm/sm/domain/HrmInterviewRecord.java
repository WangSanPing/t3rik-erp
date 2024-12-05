package com.t3rik.hrm.sm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 面试记录对象 hrm_interview_record
 *
 * @author t3rik
 * @date 2024-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "hrm_interview_record")
public class HrmInterviewRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 面试记录主键
     */
    @TableId
    private Long interviewRecordId;

    /**
     * 角色组
     */
    private Long roleId;

    /**
     * 岗位组
     */
    private Long postId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 员工主键id
     */
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
     * 定级职级主键id
     */
    private Long rankId;


    /**
     * 定级职级编码
     */
    private String rankCode;


    /**
     * 定级职级层次
     */
    @Excel(name = "定级职级层次")
    private String rankType;


    /**
     * 定级职级层次名称
     */
    @Excel(name = "定级职级层次名称")
    private String rankName;


    /**
     * 定级薪资
     */
    private BigDecimal actualSalary;


    /**
     * 期望薪资
     */
    private BigDecimal salaryExpectation;


    /**
     * 面试反馈
     */
    @Excel(name = "面试反馈")
    private String interviewFeedback;


    /**
     * 面试次数
     */
    @Excel(name = "面试次数")
    private Integer times;


    /**
     * 面试时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "面试时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeForInterview;


    /**
     * 面试官id
     */
    @Excel(name = "面试官id")
    private Long interviewerId;


    /**
     * 面试官姓名
     */
    @Excel(name = "面试官姓名")
    private String interviewerName;


    /**
     * 创建者id
     */
    private Long createUserId;


    /**
     * 修改者id
     */
    private Long updateUserId;


    /**
     * 状态
     */
    private Long status;


    /**
     * 逻辑删除字段 0:未删除 1:已删除
     */
    private Long deleted;


    /**
     * 逻辑删除辅助字段
     */
    private Date deleteat;


    /**
     * 乐观锁
     */
    private Long version;

}
