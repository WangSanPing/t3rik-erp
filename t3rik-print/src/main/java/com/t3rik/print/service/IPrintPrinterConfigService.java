package com.t3rik.print.service;

import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.print.domain.PrintPrinterConfig;

import java.util.List;

/**
 * 打印机配置Service接口
 *
 * @author skyyan
 */
public interface IPrintPrinterConfigService
{
    /**
     * 查询打印机配置
     *
     * @param printerId 打印机配置主键
     * @return 打印机配置
     */
    public PrintPrinterConfig selectPrintPrinterConfigByPrinterId(Long printerId);

    /**
     * 根据打印机编号查询打印机配置
     *
     * @param printerCode 打印机编号
     * @return 打印机配置
     */
    public PrintPrinterConfig selectPrintPrinterConfigByPrinterCode(String printerCode);

    /**
     * 查询打印机配置列表
     *
     * @param printPrinterConfig 打印机配置
     * @return 打印机配置集合
     */
    public List<PrintPrinterConfig> selectPrintPrinterConfigList(PrintPrinterConfig printPrinterConfig);


    /**
     * 检查编号是否唯一
     * @param config
     * @return
     */
    public String checkPrinterCodeUnique(PrintPrinterConfig config);

    /**
     * 新增打印机配置
     *
     * @param printPrinterConfig 打印机配置
     * @return 结果
     */
    public int insertPrintPrinterConfig(PrintPrinterConfig printPrinterConfig);

    /**
     * 修改打印机配置
     *
     * @param printPrinterConfig 打印机配置
     * @return 结果
     */
    public int updatePrintPrinterConfig(PrintPrinterConfig printPrinterConfig);

    /**
     * 批量删除打印机配置
     *
     * @param printerIds 需要删除的打印机配置主键集合
     * @return 结果
     */
    public int deletePrintPrinterConfigByPrinterIds(Long[] printerIds);

    /**
     * 删除打印机配置信息
     *
     * @param printerId 打印机配置主键
     * @return 结果
     */
    public int deletePrintPrinterConfigByPrinterId(Long printerId);

    /**
     * 获取默认打印机
     * @return
     */
    AjaxResult getDefaultPrint(Long clientId);

    AjaxResult editDefaultPrint(PrintPrinterConfig printPrinterConfig);
}
