package com.t3rik.mes.qc.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProFeedback;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.service.IProFeedbackService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.mes.qc.domain.*;
import com.t3rik.mes.qc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 过程检验单Controller
 * 
 * @author yinjinlu
 * @date 2022-08-29
 */
@RestController
@RequestMapping("/mes/qc/ipqc")
public class QcIpqcController extends BaseController
{
    @Autowired
    private IQcIpqcService qcIpqcService;

    @Autowired
    private IProWorkorderService proWorkorderService;

    @Autowired
    private IQcTemplateIndexService qcTemplateIndexService;

    @Autowired
    private IQcTemplateService qcTemplateService;

    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    @Autowired
    private IQcDefectRecordService qcDefectRecordService;

    @Autowired
    private IProFeedbackService proFeedbackService;

    /**
     * 查询过程检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIpqc qcIpqc)
    {
        startPage();
        List<QcIpqc> list = qcIpqcService.selectQcIpqcList(qcIpqc);
        return getDataTable(list);
    }

    /**
     * 导出过程检验单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:export')")
    @Log(title = "过程检验单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcIpqc qcIpqc)
    {
        List<QcIpqc> list = qcIpqcService.selectQcIpqcList(qcIpqc);
        ExcelUtil<QcIpqc> util = new ExcelUtil<QcIpqc>(QcIpqc.class);
        util.exportExcel(response, list, "过程检验单数据");
    }

    /**
     * 获取过程检验单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:query')")
    @GetMapping(value = "/{ipqcId}")
    public AjaxResult getInfo(@PathVariable("ipqcId") Long ipqcId)
    {
        return AjaxResult.success(qcIpqcService.selectQcIpqcByIpqcId(ipqcId));
    }

    /**
     * 新增过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:add')")
    @Log(title = "过程检验单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody QcIpqc qcIpqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIpqcService.checkIpqcCodeUnique(qcIpqc))){
            return AjaxResult.error("检测单编码已存在！");
        }

        //根据工单获取产品信息
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(qcIpqc.getWorkorderId());
        qcIpqc.setWorkorderId(workorder.getWorkorderId());
        qcIpqc.setWorkorderCode(workorder.getWorkorderCode());
        qcIpqc.setWorkorderName(workorder.getWorkorderName());
        qcIpqc.setItemId(workorder.getProductId());
        qcIpqc.setItemCode(workorder.getProductCode());
        qcIpqc.setItemName(workorder.getProductName());
        qcIpqc.setSpecification(workorder.getProductSpc());
        qcIpqc.setUnitOfMeasure(workorder.getUnitOfMeasure());

        //查询工序相关信息
        List<QcIpqc> infos = qcIpqcService.getProcessInfo(qcIpqc);
        if(!CollectionUtils.isEmpty(infos)&&infos.size() ==1){
            qcIpqc.setProcessId(infos.get(0).getProcessId());
            qcIpqc.setProcessCode(infos.get(0).getProcessCode());
            qcIpqc.setProcessName(infos.get(0).getProcessName());
        }

        //根据产品和检测类型获取检测模板
        QcTemplate param = new QcTemplate();
        param.setQcTypes(qcIpqc.getIpqcType());
        param.setItemId(workorder.getProductId());
        QcTemplate template = qcTemplateService.selectQcTemplateByProductAndQcType(param);
        if(StringUtils.isNotNull(template)){
            qcIpqc.setTemplateId(template.getTemplateId());
        }else{
            return AjaxResult.error("当前工单生产的产品未配置此类型的检验模板！");
        }

        //先保存
        qcIpqcService.insertQcIpqc(qcIpqc);

        //生成行信息
        generateLine(qcIpqc);

        //将ID返回
        Long ipqcId = qcIpqc.getIpqcId();
        return AjaxResult.success(ipqcId);
    }

    /**
     * 修改过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:edit')")
    @Log(title = "过程检验单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody QcIpqc qcIpqc)
    {
        if(UserConstants.NOT_UNIQUE.equals(qcIpqcService.checkIpqcCodeUnique(qcIpqc))){
            return AjaxResult.error("检测单编码已存在！");
        }

        //对合格品和不合格品数量进行检查


        //根据工单获取产品信息
        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(qcIpqc.getWorkorderId());
        qcIpqc.setWorkorderId(workorder.getWorkorderId());
        qcIpqc.setWorkorderCode(workorder.getWorkorderCode());
        qcIpqc.setWorkorderName(workorder.getWorkorderName());
        qcIpqc.setItemId(workorder.getProductId());
        qcIpqc.setItemCode(workorder.getProductCode());
        qcIpqc.setItemName(workorder.getProductName());
        qcIpqc.setSpecification(workorder.getProductSpc());
        qcIpqc.setUnitOfMeasure(workorder.getUnitOfMeasure());

        //查询工序相关信息
        List<QcIpqc> infos = qcIpqcService.getProcessInfo(qcIpqc);
        if(!CollectionUtils.isEmpty(infos)&&infos.size() ==1){
            qcIpqc.setProcessId(infos.get(0).getProcessId());
            qcIpqc.setProcessCode(infos.get(0).getProcessCode());
            qcIpqc.setProcessName(infos.get(0).getProcessName());
        }

        //根据产品和检测类型获取检测模板
        QcTemplate param = new QcTemplate();
        param.setQcTypes(qcIpqc.getIpqcType());
        param.setItemId(workorder.getProductId());
        QcTemplate template = qcTemplateService.selectQcTemplateByProductAndQcType(param);
        if(StringUtils.isNotNull(template)){
            qcIpqc.setTemplateId(template.getTemplateId());
        }else{
            return AjaxResult.error("当前工单生产的产品未配置此类型的检验模板！");
        }

        //如果是完成单据则根据单据上的来源单据，更新对应的关联检验单信息
        if(UserConstants.ORDER_STATUS_FINISHED.equals(qcIpqc.getStatus())){
            if(StringUtils.isNotNull(qcIpqc.getSourceDocCode())){
                //这里默认更新生产报工单的数据
                ProFeedback feedback =  proFeedbackService.selectProFeedbackByRecordId(qcIpqc.getSourceDocId());
                if(StringUtils.isNotNull(feedback)){
                    feedback.setQuantityQualified(qcIpqc.getQuantityQualified());
                    feedback.setQuantityUnquanlified(qcIpqc.getQuantityUnqualified());
                    feedback.setQuantityUncheck(BigDecimal.ZERO);
                    proFeedbackService.updateProFeedback(feedback);
                }
            }
        }

        return toAjax(qcIpqcService.updateQcIpqc(qcIpqc));
    }

    /**
     * 删除过程检验单
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:ipqc:remove')")
    @Log(title = "过程检验单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{ipqcIds}")
    public AjaxResult remove(@PathVariable Long[] ipqcIds)
    {
        for (Long ipqcId: ipqcIds
             ) {
            QcIpqc ipqc = qcIpqcService.selectQcIpqcByIpqcId(ipqcId);
            if(!UserConstants.ORDER_STATUS_PREPARE.equals(ipqc.getStatus())){
                return AjaxResult.error("只能删除草稿状态的单据!");
            }

            qcIpqcLineService.deleteByIpqcId(ipqcId); //删除对应的行信息
            QcDefectRecord p2 = new QcDefectRecord();
            p2.setQcId(ipqcId);
            p2.setQcType(UserConstants.QC_TYPE_IPQC);
            qcDefectRecordService.deleteByQcIdAndType(p2);//删除对应的缺陷记录
        }

        return toAjax(qcIpqcService.deleteQcIpqcByIpqcIds(ipqcIds));
    }


    /**
     * 根据头信息生成行信息
     * @param iqc
     */
    private void generateLine(QcIpqc iqc){
        QcTemplateIndex param = new QcTemplateIndex();
        param.setTemplateId(iqc.getTemplateId());
        List<QcTemplateIndex > indexs = qcTemplateIndexService.selectQcTemplateIndexList(param);
        if(CollUtil.isNotEmpty(indexs)){
            for (QcTemplateIndex index:indexs
            ) {
                QcIpqcLine line = new QcIpqcLine();
                line.setIpqcId(iqc.getIpqcId());
                line.setIndexId(index.getIndexId());
                line.setIndexCode(index.getIndexCode());
                line.setIndexName(index.getIndexName());
                line.setIndexType(index.getIndexType());
                line.setQcTool(index.getQcTool());
                line.setCheckMethod(index.getCheckMethod());
                line.setStanderVal(index.getStanderVal());
                line.setUnitOfMeasure(index.getUnitOfMeasure());
                line.setThresholdMax(index.getThresholdMax());
                line.setThresholdMin(index.getThresholdMin());
                line.setCrQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                line.setMajQuantity(new BigDecimal(0L));
                qcIpqcLineService.insertQcIpqcLine(line);
            }
        }
    }
}
