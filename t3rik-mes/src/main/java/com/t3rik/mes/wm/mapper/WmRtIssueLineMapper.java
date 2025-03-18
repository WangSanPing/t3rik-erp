package com.t3rik.mes.wm.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.wm.domain.WmRtIssueLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 生产退料单行Mapper接口
 *
 * @author yinjinlu
 * @date 2022-09-15
 */
@Mapper
public interface WmRtIssueLineMapper extends BaseMapper<WmRtIssueLine> {
    /**
     * 查询生产退料单行
     *
     * @param lineId 生产退料单行主键
     * @return 生产退料单行
     */
    public WmRtIssueLine selectWmRtIssueLineByLineId(Long lineId);

    /**
     * 查询生产退料单行列表
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 生产退料单行集合
     */
    public List<WmRtIssueLine> selectWmRtIssueLineList(WmRtIssueLine wmRtIssueLine);

    /**
     * 新增生产退料单行
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 结果
     */
    public int insertWmRtIssueLine(WmRtIssueLine wmRtIssueLine);

    /**
     * 修改生产退料单行
     *
     * @param wmRtIssueLine 生产退料单行
     * @return 结果
     */
    public int updateWmRtIssueLine(WmRtIssueLine wmRtIssueLine);

    /**
     * 删除生产退料单行
     *
     * @param lineId 生产退料单行主键
     * @return 结果
     */
    public int deleteWmRtIssueLineByLineId(Long lineId);

    /**
     * 批量删除生产退料单行
     *
     * @param lineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmRtIssueLineByLineIds(Long[] lineIds);

    /**
     * 根据退料单ID删除所有行
     *
     * @param rtId
     * @return
     */
    public int deleteByRtId(Long rtId);
}
