package com.t3rik.mes.qc.controller;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.qc.domain.QcDefectRecord;
import com.t3rik.mes.qc.domain.ValidList;
import com.t3rik.mes.qc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 检验单缺陷记录Controller
 * 
 * @author yinjinlu
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/mes/qc/defectrecord")
public class QcDefectRecordController extends BaseController
{
    @Autowired
    private IQcDefectRecordService qcDefectRecordService;

    @Autowired
    private IQcIqcLineService qcIqcLineService;

    @Autowired
    private IQcIqcService qcIqcService;

    @Autowired
    private IQcIpqcLineService qcIpqcLineService;

    @Autowired
    private IQcIpqcService qcIpqcService;

    @Autowired
    private IQcOqcService qcOqcService;

    @Autowired
    private IQcOqcLineService qcOqcLineService;


    /**
     * 查询检验单缺陷记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QcDefectRecord qcDefectRecord)
    {
        startPage();
        List<QcDefectRecord> list = qcDefectRecordService.selectQcDefectRecordList(qcDefectRecord);
        return getDataTable(list);
    }

    /**
     * 导出检验单缺陷记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:defectrecord:export')")
    @Log(title = "检验单缺陷记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcDefectRecord qcDefectRecord)
    {
        List<QcDefectRecord> list = qcDefectRecordService.selectQcDefectRecordList(qcDefectRecord);
        ExcelUtil<QcDefectRecord> util = new ExcelUtil<QcDefectRecord>(QcDefectRecord.class);
        util.exportExcel(response, list, "检验单缺陷记录数据");
    }

    /**
     * 获取检验单缺陷记录详细信息
     */
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(qcDefectRecordService.selectQcDefectRecordByRecordId(recordId));
    }

    /**
     * 新增检验单缺陷记录
     */
    @Log(title = "检验单缺陷记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcDefectRecord qcDefectRecord)
    {
        if(!StringUtils.isNotNull(qcDefectRecord.getDefectName())){
            return AjaxResult.error("请填写缺陷内容");
        }

        if(!StringUtils.isNotNull(qcDefectRecord.getDefectLevel())){
            return AjaxResult.error("请选择缺陷级别");
        }

        return toAjax(qcDefectRecordService.insertQcDefectRecord(qcDefectRecord));
    }

    /**
     * 修改来料检验单缺陷记录
     */
    @Log(title = "检验单缺陷记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult updateList(@Validated @RequestBody ValidList<QcDefectRecord> defects){
        Long qcId = -1L;
        String qcType = "";
        Long lineId = -1L;
        if(CollUtil.isNotEmpty(defects)){
            for (QcDefectRecord defect: defects
            ) {
                if(!StringUtils.isNotNull(defect.getDefectName())){
                    return AjaxResult.error("请填写缺陷内容");
                }

                if(!StringUtils.isNotNull(defect.getDefectLevel())){
                    return AjaxResult.error("请选择缺陷级别");
                }

                if(StringUtils.isNotNull(defect.getRecordId())){
                    qcDefectRecordService.updateQcDefectRecord(defect);
                }else {
                    qcDefectRecordService.insertQcDefectRecord(defect);
                }
                qcId = defect.getQcId();
                qcType = defect.getQcType();
                lineId = defect.getLineId();
            }

            if(UserConstants.QC_TYPE_IQC.equals(qcType)){
                //更新来料检验单行上的cr,maj,min数量
                qcIqcLineService.updateCrMajMinQuantity(qcId,lineId);
                //更新来料检验单头上的cr,maj,min数量和比例
                qcIqcService.updateCrMajMinQuaAndRate(qcId);

            }else if(UserConstants.QC_TYPE_IPQC.equals(qcType)){
                //更新过程检验单行上的cr,maj,min数量
                qcIpqcLineService.updateCrMajMinQuantity(qcId,lineId);
                //更新过程检验单头上的cr,maj,min数量和比例
                qcIpqcService.updateCrMajMinQuaAndRate(qcId);
            }else {
                //更新出货检验单行上的cr,maj,min数量
                qcOqcLineService.updateCrMajMinQuantity(qcId,lineId);
                //更新出货检验单头上的cr,maj,min数量和比例
                qcOqcService.updateCrMajMinQuaAndRate(qcId);
            }

        }
        return AjaxResult.success();
    }

    /**
     * 删除检验单缺陷记录
     */
    @Log(title = "检验单缺陷记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(qcDefectRecordService.deleteQcDefectRecordByRecordIds(recordIds));
    }
}
