package com.t3rik.mes.pro.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工艺组成对象 pro_route_process
 *
 * @author yinjinlu
 * @date 2022-05-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProRouteProcess extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId
    private Long recordId;

    /**
     * 工艺路线ID
     */
    @Excel(name = "工艺路线ID")
    private Long routeId;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long processId;

    /**
     * 工序编码
     */
    @Excel(name = "工序编码")
    private String processCode;

    /**
     * 工序名称
     */
    @Excel(name = "工序名称")
    private String processName;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer orderNum;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long nextProcessId;

    /**
     * 工序编码
     */
    @Excel(name = "工序编码")
    private String nextProcessCode;

    /**
     * 工序名称
     */
    @Excel(name = "工序名称")
    private String nextProcessName;

    /**
     * 与下一道工序关系
     */
    @Excel(name = "与下一道工序关系")
    private String linkType;

    /**
     * 准备时间
     */
    @Excel(name = "准备时间")
    private Long defaultPreTime;

    /**
     * 等待时间
     */
    @Excel(name = "等待时间")
    private Long defaultSufTime;

    /**
     * 甘特图显示颜色
     */
    @Excel(name = "甘特图显示颜色")
    private String colorCode;

    /**
     * 是否关键工序
     */
    private String keyFlag;

    /**
     * 是否质检
     */
    private String isCheck;

    /**
     * 预留字段1
     */
    private String attr1;

    /**
     * 预留字段2
     */
    private String attr2;

    /**
     * 预留字段3
     */
    private Long attr3;

    /**
     * 预留字段4
     */
    private Long attr4;

}
