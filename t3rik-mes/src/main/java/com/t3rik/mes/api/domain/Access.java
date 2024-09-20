package com.t3rik.mes.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 密钥bean
 */
@Data
@TableName(value = "mes_access")
public class Access extends BaseEntity {

    @TableId
    private String id;
    //KEY
    private String accessKey;
    //密匙
    private String secret;
    //状态(0-启用，1-停用)
    private String status;

}
