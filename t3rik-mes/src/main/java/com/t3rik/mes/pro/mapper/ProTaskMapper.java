package com.t3rik.mes.pro.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.dto.TaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产任务Mapper接口
 *
 * @author yinjinlu
 * @date 2022-05-14
 */
@Mapper
public interface ProTaskMapper extends BaseMapper<ProTask> {
    /**
     * 查询生产任务
     *
     * @param taskId 生产任务主键
     * @return 生产任务
     */
    public ProTask selectProTaskByTaskId(Long taskId);

    /**
     * 查询生产任务列表
     *
     * @param proTask 生产任务
     * @return 生产任务集合
     */
    public List<ProTask> selectProTaskList(ProTask proTask);


    /**
     * 查询某个工单的各个工序生产进度
     *
     * @param workorderId
     * @return
     */
    public List<ProTask> selectProTaskProcessViewByWorkorder(Long workorderId);

    /**
     * 新增生产任务
     *
     * @param proTask 生产任务
     * @return 结果
     */
    public int insertProTask(ProTask proTask);

    /**
     * 修改生产任务
     *
     * @param proTask 生产任务
     * @return 结果
     */
    public int updateProTask(ProTask proTask);

    /**
     * 删除生产任务
     *
     * @param taskId 生产任务主键
     * @return 结果
     */
    public int deleteProTaskByTaskId(Long taskId);

    /**
     * 批量删除生产任务
     *
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProTaskByTaskIds(Long[] taskIds);

    /**
     * 查询任务，同时获取任务下的报工数量
     * @param query 查询条件
     * @return
     */
    Page<TaskDTO> getTaskListAndFeedbackCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，根据工单分组展示数据，并统计领料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getWorkOrderGroupAndIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，根据工单分组展示数据，并统计领料和退料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getWorkOrderGroupAndRtIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，根据工单分组展示数据，并统计领料和废料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getWorkOrderGroupAndWasteCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，根据工单数据，并统计领料和退料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getTaskListAndRtIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，根据工单示数据，并统计领料和废料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getTaskListAndWasteIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);
}
