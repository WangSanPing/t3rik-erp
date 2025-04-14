package com.t3rik.print.service.impl;

import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.print.domain.PrintTemplate;
import com.t3rik.print.mapper.PrintTemplateMapper;
import com.t3rik.print.service.IPrintTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板配置Service业务层处理
 *
 * @author skyyan
 */
@Service
public class PrintTemplateServiceImpl implements IPrintTemplateService
{
    @Autowired
    private PrintTemplateMapper printTemplateMapper;

    /**
     * 查询打印模板配置
     *
     * @param templateId 打印模板配置主键
     * @return 打印模板配置
     */
    @Override
    public PrintTemplate selectPrintTemplateByTemplateId(Long templateId)
    {
        return printTemplateMapper.selectPrintTemplateByTemplateId(templateId);
    }

    /**
     * 查询打印模板配置列表
     *
     * @param printTemplate 打印模板配置
     * @return 打印模板配置
     */
    @Override
    public List<PrintTemplate> selectPrintTemplateList(PrintTemplate printTemplate)
    {
        return printTemplateMapper.selectPrintTemplateList(printTemplate);
    }

    /**
     * 新增打印模板配置
     *
     * @param printTemplate 打印模板配置
     * @return 结果
     */
    @Override
    public AjaxResult insertPrintTemplate(PrintTemplate printTemplate)
    {
        // 校验：根据模板类型作为一校验
        PrintTemplate params = new PrintTemplate();
        params.setTemplateType(printTemplate.getTemplateType());
        List<PrintTemplate> printTemplates = printTemplateMapper.selectPrintTemplateList(params);
        if (printTemplates != null && printTemplates.size() > 0) {
            return AjaxResult.error("模板类型已存在");
        }
        printTemplate.setCreateTime(DateUtils.getNowDate());
        printTemplateMapper.insertPrintTemplate(printTemplate);
        return AjaxResult.success(printTemplate);
    }

    /**
     * 修改打印模板配置
     *
     * @param printTemplate 打印模板配置
     * @return 结果
     */
    @Override
    public int updatePrintTemplate(PrintTemplate printTemplate)
    {
        printTemplate.setUpdateTime(DateUtils.getNowDate());
        return printTemplateMapper.updatePrintTemplate(printTemplate);
    }

    /**
     * 批量删除打印模板配置
     *
     * @param templateIds 需要删除的打印模板配置主键
     * @return 结果
     */
    @Override
    public int deletePrintTemplateByTemplateIds(Long[] templateIds)
    {
        return printTemplateMapper.deletePrintTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除打印模板配置信息
     *
     * @param templateId 打印模板配置主键
     * @return 结果
     */
    @Override
    public int deletePrintTemplateByTemplateId(Long templateId)
    {
        return printTemplateMapper.deletePrintTemplateByTemplateId(templateId);
    }
}
