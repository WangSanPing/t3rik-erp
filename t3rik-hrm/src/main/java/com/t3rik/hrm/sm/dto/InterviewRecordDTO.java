package com.t3rik.hrm.sm.dto;

import com.t3rik.common.annotation.Excel;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author t3rik
 * @date 2024/9/17 21:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterviewRecordDTO extends HrmInterviewRecord {

    /**
     * 员工id
     */
    private Long parentId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系电话
     */
    private String contactPhone;


    /**
     * 用户性别（0男 1女 2未知）
     */
    private Long sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 当前员工状态
     */
    private Integer currentStatus;

    /**
     * 实际薪资
     */
    private BigDecimal actualSalary;

    /**
     * 子集数据
     */
    List<InterviewRecordDTO> children;
}
