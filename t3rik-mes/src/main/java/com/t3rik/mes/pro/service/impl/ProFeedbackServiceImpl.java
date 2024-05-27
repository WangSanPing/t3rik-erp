package com.t3rik.mes.pro.service.impl;

import java.util.List;

import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.WmRtIssue;
import com.t3rik.mes.wm.domain.WmWasteHeader;
import com.t3rik.mes.wm.service.IWmRtIssueService;
import com.t3rik.mes.wm.service.IWmWasteHeaderService;
import com.t3rik.mes.wm.service.impl.WmWarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.pro.mapper.ProFeedbackMapper;
import com.t3rik.mes.pro.domain.ProFeedback;
import com.t3rik.mes.pro.service.IProFeedbackService;

/**
 * 生产报工记录Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-07-10
 */
@Service
public class ProFeedbackServiceImpl implements IProFeedbackService
{
    @Autowired
    private ProFeedbackMapper proFeedbackMapper;

    @Autowired
    private IWmRtIssueService wmRtIssueService;

    @Autowired
    private IWmWasteHeaderService wmWasteHeaderService;

    /**
     * 查询生产报工记录
     *
     * @param recordId 生产报工记录主键
     * @return 生产报工记录
     */
    @Override
    public ProFeedback selectProFeedbackByRecordId(Long recordId)
    {
        return proFeedbackMapper.selectProFeedbackByRecordId(recordId);
    }

    /**
     * 查询生产报工记录列表
     *
     * @param proFeedback 生产报工记录
     * @return 生产报工记录
     */
    @Override
    public List<ProFeedback> selectProFeedbackList(ProFeedback proFeedback)
    {
        return proFeedbackMapper.selectProFeedbackList(proFeedback);
    }

    /**
     * 新增生产报工记录
     *
     * @param proFeedback 生产报工记录
     * @return 结果
     */
    @Override
    public int insertProFeedback(ProFeedback proFeedback)
    {
        proFeedback.setCreateTime(DateUtils.getNowDate());
        return proFeedbackMapper.insertProFeedback(proFeedback);
    }

    /**
     * 修改生产报工记录
     *
     * @param proFeedback 生产报工记录
     * @return 结果
     */
    @Override
    public int updateProFeedback(ProFeedback proFeedback)
    {
        proFeedback.setUpdateTime(DateUtils.getNowDate());
        return proFeedbackMapper.updateProFeedback(proFeedback);
    }

    /**
     * 批量删除生产报工记录
     *
     * @param recordIds 需要删除的生产报工记录主键
     * @return 结果
     */
    @Override
    public int deleteProFeedbackByRecordIds(Long[] recordIds)
    {
        //废料数量
        Long wastCount = wmWasteHeaderService.lambdaQuery().in(WmWasteHeader::getRecordId, recordIds).count();
        //退料数量
        Long rtIssuecount = wmRtIssueService.lambdaQuery().in(WmRtIssue::getRecordId, recordIds).count();
        if (wastCount > 0 || rtIssuecount > 0)
            throw new BusinessException("该记录已关联废料或退料，不能删除!");
        //删除退料、废料记录
        wmRtIssueService.deleteWmRtIssueByRtIds(recordIds);
        wmWasteHeaderService.delWmWasteHeaderIds(List.of(recordIds));
        return proFeedbackMapper.deleteProFeedbackByRecordIds(recordIds);
    }

    /**
     * 删除生产报工记录信息
     *
     * @param recordId 生产报工记录主键
     * @return 结果
     */
    @Override
    public int deleteProFeedbackByRecordId(Long recordId)
    {
        return proFeedbackMapper.deleteProFeedbackByRecordId(recordId);
    }
}
