package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmRtIssue;
import com.t3rik.mes.wm.domain.tx.RtIssueTxBean;
import com.t3rik.mes.wm.dto.RtIssueHeaderAndLineDTO;
import com.t3rik.mes.wm.dto.WasteHeaderAndLineDTO;
import com.t3rik.mes.wm.mapper.WmRtIssueMapper;
import com.t3rik.mes.wm.service.IWmRtIssueService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产退料单头Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-09-15
 */
@Service
public class WmRtIssueServiceImpl extends ServiceImpl<WmRtIssueMapper, WmRtIssue> implements IWmRtIssueService {

    @Resource
    private WmRtIssueMapper wmRtIssueMapper;

    /**
     * 查询生产退料单头
     *
     * @param rtId 生产退料单头主键
     * @return 生产退料单头
     */
    @Override
    public WmRtIssue selectWmRtIssueByRtId(Long rtId) {
        return wmRtIssueMapper.selectWmRtIssueByRtId(rtId);
    }

    /**
     * 查询生产退料单头列表
     *
     * @param wmRtIssue 生产退料单头
     * @return 生产退料单头
     */
    @Override
    public List<WmRtIssue> selectWmRtIssueList(WmRtIssue wmRtIssue) {
        return wmRtIssueMapper.selectWmRtIssueList(wmRtIssue);
    }

    @Override
    public String checkUnique(WmRtIssue wmRtIssue) {
        WmRtIssue issue = wmRtIssueMapper.checkUnique(wmRtIssue);
        Long rtId = wmRtIssue.getRtId() == null ? -1L : wmRtIssue.getRtId();
        if (StringUtils.isNotNull(issue) && issue.getRtId().longValue() != rtId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增生产退料单头
     *
     * @param wmRtIssue 生产退料单头
     * @return 结果
     */
    @Override
    public int insertWmRtIssue(WmRtIssue wmRtIssue) {
        wmRtIssue.setCreateTime(DateUtils.getNowDate());
        return wmRtIssueMapper.insertWmRtIssue(wmRtIssue);
    }

    /**
     * 修改生产退料单头
     *
     * @param wmRtIssue 生产退料单头
     * @return 结果
     */
    @Override
    public int updateWmRtIssue(WmRtIssue wmRtIssue) {
        wmRtIssue.setUpdateTime(DateUtils.getNowDate());
        return wmRtIssueMapper.updateWmRtIssue(wmRtIssue);
    }

    /**
     * 批量删除生产退料单头
     *
     * @param rtIds 需要删除的生产退料单头主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueByRtIds(Long[] rtIds) {
        return wmRtIssueMapper.deleteWmRtIssueByRtIds(rtIds);
    }

    /**
     * 删除生产退料单头信息
     *
     * @param rtId 生产退料单头主键
     * @return 结果
     */
    @Override
    public int deleteWmRtIssueByRtId(Long rtId) {
        return wmRtIssueMapper.deleteWmRtIssueByRtId(rtId);
    }

    @Override
    public List<RtIssueTxBean> getTxBeans(Long rtId) {
        return wmRtIssueMapper.getTxBeans(rtId);
    }

    /**
     * 查询退料详情
     *
     * @param query
     */
    @Override
    public List<RtIssueHeaderAndLineDTO> getRtIssueDetail(Wrapper<RtIssueHeaderAndLineDTO> query) {
        return this.wmRtIssueMapper.getRtIssueDetail(query);
    }

    /**
     * 查询退料详情列表
     *
     * @param query
     */
    @Override
    public List<RtIssueHeaderAndLineDTO> getRtIssueDetailList(Wrapper<RtIssueHeaderAndLineDTO> query) {
        return this.wmRtIssueMapper.getRtIssueDetailList(query);
    }


}
