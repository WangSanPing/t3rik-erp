package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.WmRtIssueLine;
import com.t3rik.mes.wm.mapper.WmRtIssueLineMapper;
import com.t3rik.mes.wm.service.IWmRtIssueLineService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产退料单行Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-09-15
 */
@Service
public class WmRtIssueLineServiceImpl extends ServiceImpl<WmRtIssueLineMapper,WmRtIssueLine> implements IWmRtIssueLineService {
    @Resource
    private WmRtIssueLineMapper wmRtIssueLineMapper;

    /**
     * 查询生产退料单行
     *
     * @param lineId 生产退料单行主键
     * @return 生产退料单行
     */
    @Override
    public WmRtIssueLine selectWmRtIssueLineByLineId(Long lineId) {
        return wmRtIssueLineMapper.selectWmRtIssueLineByLineId(lineId);
    }

    /**
     * 查询生产退料单行列表
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 生产退料单行
     */
    @Override
    public List<WmRtIssueLine> selectWmRtIssueLineList(WmRtIssueLine wmRtIssueLine) {
        return wmRtIssueLineMapper.selectWmRtIssueLineList(wmRtIssueLine);
    }

    /**
     * 新增生产退料单行
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 结果
     */
    @Override
    public int insertWmRtIssueLine(WmRtIssueLine wmRtIssueLine) {
        wmRtIssueLine.setCreateTime(DateUtils.getNowDate());
        return wmRtIssueLineMapper.insertWmRtIssueLine(wmRtIssueLine);
    }

    /**
     * 修改生产退料单行
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 结果
     */
    @Override
    public int updateWmRtIssueLine(WmRtIssueLine wmRtIssueLine) {
        wmRtIssueLine.setUpdateTime(DateUtils.getNowDate());
        return wmRtIssueLineMapper.updateWmRtIssueLine(wmRtIssueLine);
    }

    /**
     * 批量删除生产退料单行
     *
     * @param lineIds 需要删除的生产退料单行主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueLineByLineIds(Long[] lineIds) {
        return wmRtIssueLineMapper.deleteWmRtIssueLineByLineIds(lineIds);
    }

    /**
     * 删除生产退料单行信息
     *
     * @param lineId 生产退料单行主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueLineByLineId(Long lineId) {
        return wmRtIssueLineMapper.deleteWmRtIssueLineByLineId(lineId);
    }

    @Override
    public int deleteByRtId(Long rtId) {
        return wmRtIssueLineMapper.deleteByRtId(rtId);
    }
}
