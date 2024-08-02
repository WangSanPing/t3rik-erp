package com.t3rik.mes.md.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作站对象 md_workstation
 *
 * @author yinjinlu
 * @date 2022-05-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MdWorkstation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 工作站ID
     */
    @TableId
    private Long workstationId;

    /**
     * 工作站编码
     */
    @Excel(name = "工作站编码")
    private String workstationCode;

    /**
     * 工作站名称
     */
    @Excel(name = "工作站名称")
    private String workstationName;

    /**
     * 工作站地点
     */
    @Excel(name = "工作站地点")
    private String workstationAddress;

    /**
     * 所在车间ID
     */
    @Excel(name = "所在车间ID")
    private Long workshopId;

    /**
     * 所在车间编码
     */
    @Excel(name = "所在车间编码")
    private String workshopCode;

    /**
     * 所在车间名称
     */
    @Excel(name = "所在车间名称")
    private String workshopName;

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
     * 线边库ID
     */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /**
     * 线边库编码
     */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /**
     * 线边库名称
     */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /**
     * 库区ID
     */
    @Excel(name = "库区ID")
    private Long locationId;

    /**
     * 库区编码
     */
    @Excel(name = "库区编码")
    private String locationCode;

    /**
     * 库区名称
     */
    @Excel(name = "库区名称")
    private String locationName;

    /**
     * 库位ID
     */
    @Excel(name = "库位ID")
    private Long areaId;

    /**
     * 库位编码
     */
    @Excel(name = "库位编码")
    private String areaCode;

    /**
     * 库位名称
     */
    @Excel(name = "库位名称")
    private String areaName;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private String enableFlag;

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
