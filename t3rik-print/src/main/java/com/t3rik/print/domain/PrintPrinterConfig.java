package com.t3rik.print.domain;

import com.t3rik.common.annotation.Excel;
import com.t3rik.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 打印机配置对象 print_printer_config
 *
 * @author skyyan
 */
@Data
public class PrintPrinterConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 打印机ID */
    private Long printerId;

    /** 打印机客户端ID */
    private Long clientId;

    /**
     * 打印机编号
     */
    private String printerCode;

    /** 打印机类型 */
    @Excel(name = "打印机类型")
    private String printerType;

    /** 打印机名称 */
    @Excel(name = "打印机名称")
    private String printerName;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 型号 */
    @Excel(name = "型号")
    private String printerModel;

    /** 连接类型 */
    @Excel(name = "连接类型")
    private String connectionType;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String printerUrl;

    /** 打印机IP */
    @Excel(name = "打印机IP")
    private String printerIp;

    /** 打印机端口 */
    @Excel(name = "打印机端口")
    private Long printerPort;

    /** 启用状态 */
    @Excel(name = "启用状态")
    private String enableFlag;

    /** 打印机状态 */
    @Excel(name = "打印机状态")
    private String status;

    private String defaultFlag;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;
}
