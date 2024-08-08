package com.t3rik.mes.qc.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcTemplate;
import com.t3rik.mes.qc.service.IQcTemplateIndexService;
import com.t3rik.mes.qc.service.IQcTemplateProductService;
import com.t3rik.mes.qc.service.IQcTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 检测模板Controller
 * 
 * @author yinjinlu
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/mes/qc/qctemplate")
public class QcTemplateController extends BaseController
{
    @Autowired
    private IQcTemplateService qcTemplateService;

    @Autowired
    private IQcTemplateIndexService qcTemplateIndexService;

    @Autowired
    private IQcTemplateProductService qcTemplateProductService;

    /**
     * 查询检测模板列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcTemplate qcTemplate)
    {
        startPage();
        List<QcTemplate> list = qcTemplateService.selectQcTemplateList(qcTemplate);
        if(CollUtil.isNotEmpty(list)){
            for (int i=0;i<list.size();i++)
            {
                QcTemplate template = list.get(i);
                template.setQcTypesParam(template.getQcTypes().split(","));
                list.set(i,template);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出检测模板列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:export')")
    @Log(title = "检测模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcTemplate qcTemplate)
    {
        List<QcTemplate> list = qcTemplateService.selectQcTemplateList(qcTemplate);
        ExcelUtil<QcTemplate> util = new ExcelUtil<QcTemplate>(QcTemplate.class);
        util.exportExcel(response, list, "检测模板数据");
    }

    /**
     * 获取检测模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        QcTemplate template = qcTemplateService.selectQcTemplateByTemplateId(templateId);
        if(StringUtils.isNotNull(template)){
            template.setQcTypesParam(template.getQcTypes().split(","));
        }
        return AjaxResult.success(template);
    }

    /**
     * 新增检测模板
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:add')")
    @Log(title = "检测模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcTemplate qcTemplate)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcTemplateService.checkTemplateCodeUnique(qcTemplate))){
            return AjaxResult.error("检测模板编号已存在！");
        }

        if(ArrayUtil.isNotEmpty(qcTemplate.getQcTypesParam())){
            qcTemplate.setQcTypes(null); //先置空
            String[] types = qcTemplate.getQcTypesParam();
            //根据输入参数重新生成
            for (String type:types
                 ) {
                if(StringUtils.isNotNull(qcTemplate.getQcTypes())){
                    qcTemplate.setQcTypes(qcTemplate.getQcTypes()+','+type);
                }else{
                    qcTemplate.setQcTypes(type);
                }
            }
        }

        return toAjax(qcTemplateService.insertQcTemplate(qcTemplate));
    }

    /**
     * 修改检测模板
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:edit')")
    @Log(title = "检测模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcTemplate qcTemplate)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcTemplateService.checkTemplateCodeUnique(qcTemplate))){
            return AjaxResult.error("检测模板编号已存在！");
        }
        if(ArrayUtil.isNotEmpty(qcTemplate.getQcTypesParam())){
            qcTemplate.setQcTypes(null); //先置空
            String[] types = qcTemplate.getQcTypesParam();
            for (String type:types
                    ) {
                if(StringUtils.isNotNull(qcTemplate.getQcTypes())){
                    qcTemplate.setQcTypes(qcTemplate.getQcTypes()+','+type);
                }else{
                    qcTemplate.setQcTypes(type);
                }
            }
        }
        return toAjax(qcTemplateService.updateQcTemplate(qcTemplate));
    }

    /**
     * 删除检测模板
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:qctemplate:remove')")
    @Log(title = "检测模板", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {

        for (Long id:templateIds
             ) {
            //删除当前模板下所有检测项数据
            qcTemplateIndexService.deleteByTemplateId(id);
            //删除当前模板下所有检测产品
            qcTemplateProductService.deleteByTemplateId(id);
        }
        return toAjax(qcTemplateService.deleteQcTemplateByTemplateIds(templateIds));
    }
}
