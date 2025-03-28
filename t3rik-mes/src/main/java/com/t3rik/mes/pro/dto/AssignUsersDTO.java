package com.t3rik.mes.pro.dto;

import com.t3rik.common.constant.MsgConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
@NoArgsConstructor
public class AssignUsersDTO {
    // 任务id
    @NotNull(message = MsgConstants.PARAM_ERROR)
    @Size(min = 1, message = MsgConstants.PARAM_ERROR)
    private List<String> taskIds;

    // 任务负责人id
    @NotNull(message = MsgConstants.PARAM_ERROR)
    @Min(value = 1, message = MsgConstants.PARAM_ERROR)
    private Long taskUserId;

    // 任务负责人姓名
    @NotBlank(message = MsgConstants.PARAM_ERROR)
    private String taskBy;
}
