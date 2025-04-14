package com.t3rik.print.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.print.domain.PrintPrinterConfig;
import com.t3rik.print.mapper.PrintPrinterConfigMapper;
import com.t3rik.print.service.IPrintPrinterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印机配置Service业务层处理
 *
 * @author skyyan
 */
@Service
public class PrintPrinterConfigServiceImpl implements IPrintPrinterConfigService
{
    @Autowired
    private PrintPrinterConfigMapper printPrinterConfigMapper;

    /**
     * 查询打印机配置
     *
     * @param printerId 打印机配置主键
     * @return 打印机配置
     */
    @Override
    public PrintPrinterConfig selectPrintPrinterConfigByPrinterId(Long printerId)
    {
        return printPrinterConfigMapper.selectPrintPrinterConfigByPrinterId(printerId);
    }

    @Override
    public PrintPrinterConfig selectPrintPrinterConfigByPrinterCode(String printerCode) {
        return printPrinterConfigMapper.selectPrintPrinterConfigByPrinterCode(printerCode);
    }

    /**
     * 查询打印机配置列表
     *
     * @param printPrinterConfig 打印机配置
     * @return 打印机配置
     */
    @Override
    public List<PrintPrinterConfig> selectPrintPrinterConfigList(PrintPrinterConfig printPrinterConfig)
    {
        return printPrinterConfigMapper.selectPrintPrinterConfigList(printPrinterConfig);
    }

    @Override
    public String checkPrinterCodeUnique(PrintPrinterConfig config) {

        PrintPrinterConfig p = printPrinterConfigMapper.checkPrinterCodeUnique(config);

        Long configId = config.getPrinterId() == null? -1L:config.getPrinterId();
        if(StringUtils.isNotNull(p) && configId.longValue() !=p.getPrinterId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }
    }

    /**
     * 新增打印机配置
     *
     * @param printPrinterConfig 打印机配置
     * @return 结果
     */
    @Override
    public int insertPrintPrinterConfig(PrintPrinterConfig printPrinterConfig)
    {
        printPrinterConfig.setCreateTime(DateUtils.getNowDate());
        printPrinterConfig.setDefaultFlag("N");
        return printPrinterConfigMapper.insertPrintPrinterConfig(printPrinterConfig);
    }

    /**
     * 修改打印机配置
     *
     * @param printPrinterConfig 打印机配置
     * @return 结果
     */
    @Override
    public int updatePrintPrinterConfig(PrintPrinterConfig printPrinterConfig)
    {
        printPrinterConfig.setUpdateTime(DateUtils.getNowDate());
        return printPrinterConfigMapper.updatePrintPrinterConfig(printPrinterConfig);
    }

    /**
     * 批量删除打印机配置
     *
     * @param printerIds 需要删除的打印机配置主键
     * @return 结果
     */
    @Override
    public int deletePrintPrinterConfigByPrinterIds(Long[] printerIds)
    {
        return printPrinterConfigMapper.deletePrintPrinterConfigByPrinterIds(printerIds);
    }

    /**
     * 删除打印机配置信息
     *
     * @param printerId 打印机配置主键
     * @return 结果
     */
    @Override
    public int deletePrintPrinterConfigByPrinterId(Long printerId)
    {
        return printPrinterConfigMapper.deletePrintPrinterConfigByPrinterId(printerId);
    }

    @Override
    public AjaxResult getDefaultPrint(Long clientId) {
        PrintPrinterConfig defaultPrint = printPrinterConfigMapper.getDefaultPrint(clientId);
        if (ObjectUtil.isNotEmpty(defaultPrint)) {
            return AjaxResult.success(defaultPrint);
        } else {
            return AjaxResult.error("默认打印机不存在");
        }

    }

    @Override
    public AjaxResult editDefaultPrint(PrintPrinterConfig printPrinterConfig) {
        // 将原来的默认打印机设置为非默认打印机
        PrintPrinterConfig defaultPrint = printPrinterConfigMapper.getDefaultPrint(printPrinterConfig.getClientId());
        defaultPrint.setDefaultFlag("N");
        printPrinterConfigMapper.updatePrintPrinterConfig(defaultPrint);
        // 将新的默认打印机设置为默认打印机
        printPrinterConfig.setDefaultFlag("Y");
        printPrinterConfigMapper.updatePrintPrinterConfig(printPrinterConfig);
        return AjaxResult.success();
    }
}
