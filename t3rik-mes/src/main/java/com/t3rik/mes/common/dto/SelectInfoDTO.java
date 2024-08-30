package com.t3rik.mes.common.dto;

import lombok.Data;

/**
 * 移动端选择仓库使用
 *
 * @author t3rik
 * @date 2024/8/23 18:25
 */
@Data
public class SelectInfoDTO {

    private String id;

    private String parentId;

    private String label;

    private String code;

    private String parentCode;

}
