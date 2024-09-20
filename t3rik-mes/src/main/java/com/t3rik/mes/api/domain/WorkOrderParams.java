package com.t3rik.mes.api.domain;


import lombok.Data;

import java.util.Date;

/**
 * 发送mes工单bean
 */
@Data
public class WorkOrderParams {
    //工单编号
    private String workorderNo;
    //销售单号
    private String salesorderNo;
    //工单类型
    private String workorderType;
    //紧急状态，1加急*，2加急 * *，3加急 * * *，9非紧急
    private int urgentLevel;
    //产品名称
    private String materialDesc;
    //产品编码
    private String materialNo;
    //规格
    private String materialSpec;
    //单位
    private String unit;
    //计划数量
    private int planQty;
    //要求开工日期，时间戳格式
    private Long startTime;
    //工单交期，时间戳格式
    private Long endTime;
    //客户代码
    private String customerNo;
    //客制特殊需求
    private String customerRequirement;
    //客制代码
    private String customizationNo;
    //跟单员
    private String orderMaster;
    //业务员
    private String sales;
    //计划跟踪单号
    private String planFollowWordNo;
    //工单备注
    private String remark;
    //工艺参考物料
    private String routingsMaterialNo;
}
