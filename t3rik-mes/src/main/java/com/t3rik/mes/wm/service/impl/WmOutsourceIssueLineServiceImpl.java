package com.t3rik.mes.wm.service.impl;

import java.util.List;
import com.t3rik.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.wm.mapper.WmOutsourceIssueLineMapper;
import com.t3rik.mes.wm.domain.WmOutsourceIssueLine;
import com.t3rik.mes.wm.service.IWmOutsourceIssueLineService;

/**
 * 外协领料单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
@Service
public class WmOutsourceIssueLineServiceImpl implements IWmOutsourceIssueLineService 
{
    @Autowired
    private WmOutsourceIssueLineMapper wmOutsourceIssueLineMapper;

    /**
     * 查询外协领料单行
     * 
     * @param lineId 外协领料单行主键
     * @return 外协领料单行
     */
    @Override
    public WmOutsourceIssueLine selectWmOutsourceIssueLineByLineId(Long lineId)
    {
        return wmOutsourceIssueLineMapper.selectWmOutsourceIssueLineByLineId(lineId);
    }

    /**
     * 查询外协领料单行列表
     * 
     * @param wmOutsourceIssueLine 外协领料单行
     * @return 外协领料单行
     */
    @Override
    public List<WmOutsourceIssueLine> selectWmOutsourceIssueLineList(WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        return wmOutsourceIssueLineMapper.selectWmOutsourceIssueLineList(wmOutsourceIssueLine);
    }

    /**
     * 根据发货单ID查询所有行信息
     * @param issueId
     * @return
     */
    @Override
    public List<WmOutsourceIssueLine> selectWmOutsourceIssueLineByIssueId(Long issueId){
        return wmOutsourceIssueLineMapper.selectWmOutsourceIssueLineByIssueId(issueId);
    }

    /**
     * 新增外协领料单行
     * 
     * @param wmOutsourceIssueLine 外协领料单行
     * @return 结果
     */
    @Override
    public int insertWmOutsourceIssueLine(WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        wmOutsourceIssueLine.setCreateTime(DateUtils.getNowDate());
        return wmOutsourceIssueLineMapper.insertWmOutsourceIssueLine(wmOutsourceIssueLine);
    }

    /**
     * 修改外协领料单行
     * 
     * @param wmOutsourceIssueLine 外协领料单行
     * @return 结果
     */
    @Override
    public int updateWmOutsourceIssueLine(WmOutsourceIssueLine wmOutsourceIssueLine)
    {
        wmOutsourceIssueLine.setUpdateTime(DateUtils.getNowDate());
        return wmOutsourceIssueLineMapper.updateWmOutsourceIssueLine(wmOutsourceIssueLine);
    }

    /**
     * 批量删除外协领料单行
     * 
     * @param lineIds 需要删除的外协领料单行主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceIssueLineByLineIds(Long[] lineIds)
    {
        return wmOutsourceIssueLineMapper.deleteWmOutsourceIssueLineByLineIds(lineIds);
    }

    /**
     * 删除外协领料单行信息
     * 
     * @param lineId 外协领料单行主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceIssueLineByLineId(Long lineId)
    {
        return wmOutsourceIssueLineMapper.deleteWmOutsourceIssueLineByLineId(lineId);
    }

    /**
     * 根据发料单ID删除所有行
     * @param issueId
     * @return
     */
    public int deleteWmOutsourceIssueLineByIssueId(Long issueId){
        return wmOutsourceIssueLineMapper.deleteWmOutsourceIssueLineByIssueId(issueId);
    }
}
