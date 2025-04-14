package com.t3rik.print.domain;

import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class PrintClient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 打印机客户端ID */
    private Long clientId;

    /** 打印机客户端编码 */
    private String clientCode;

    /** 打印机客户端名称 */
    private String clientName;

    /** 打印客户端IP */
    @Excel(name = "打印客户端IP")
    private String clientIp;

    /** 打印客户端端口 */
    @Excel(name = "打印客户端端口")
    private Long clientPort;

    /** 打印客户端token */
    private String clientToken;

    /** 客户端状态 */
    private String status;

    /** 所属车间ID */
    private Long workshopId;

    /** 所属车间编码 */
    private String workshopCode;

    /** 所属车间名称 */
    private String workshopName;

    /** 工作站ID */
    private Long workstationId;

    /** 工作站编码 */
    private String workstationCode;

    /** 工作站名称 */
    private String workstationName;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;
}
