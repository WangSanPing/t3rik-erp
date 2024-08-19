package com.t3rik.mes.pro.dto;

import com.t3rik.mes.pro.domain.ProTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务表扩展Dto
 *
 * @author t3rik
 * @date 2024/8/19 23:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskDto extends ProTask {
    /**
     * 已报工数量
     */
    private Integer feedbackCount;
    /**
     * 待审核数量
     */
    private Integer approvingCount;
}
