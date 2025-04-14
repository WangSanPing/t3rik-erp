package com.t3rik.print.service;

import com.t3rik.print.domain.PrintBarcodeModel;

import java.util.Map;

/**
 * 打印模板配置Service接口
 *
 * @author skyyan
 */
public interface IPrintService
{
    /**
     * 标签打印
     * @param printBarcodeModel
     * @return
     */
    public Map<String,Object> printLabel(PrintBarcodeModel printBarcodeModel);

}
