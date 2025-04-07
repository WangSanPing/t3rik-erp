package com.t3rik.mes.wm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日志写入失败记录对象 wm_log_failure
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "wm_log_failure")
public class WmLogFailure extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 失败记录ID
     */
    @TableId
    private Long failureId;


    /**
     * 原始日志ID（若有）
     */
    private Long logId;


    /**
     * 关联库存ID
     */
    private Long materialStockId;


    /**
     * 日志写入失败原因
     */
    @Excel(name = "日志写入失败原因")
    private String failureReason;


    /**
     * 失败时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失败时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date failureTime;


    /**
     * 失败时的原始日志数据
     */
    @Excel(name = "失败时的原始日志数据")
    private String logData;


    /**
     * 创建人ID
     */
    @Excel(name = "创建人ID")
    private Long createUserId;


    /**
     * 更新人ID
     */
    private Long updateUserId;


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
