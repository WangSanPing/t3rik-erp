package com.t3rik.mes.pro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName AssignUsersDTO
 * @Description:
 * @Author: 施子安
 * @CreateDate: 2024/7/1 15:42
 * @UpdateUser: 更新人
 * @UpdateDate: 2024/7/1 15:42
 * @UpdateRemark:
 * @Version: 1.0
 */
@AllArgsConstructor
@Data
@Validated
@NoArgsConstructor
public class AssignUsersDTO {
    // 任务id
    @NotNull
    private List<String> taskIds;

    // 任务负责人id
    @NotNull
    private Long taskUserId;

    // 任务负责人姓名
    @NotNull
    private String taskBy;
}
