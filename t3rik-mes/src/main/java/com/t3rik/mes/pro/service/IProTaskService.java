package com.t3rik.mes.pro.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.common.enums.mes.StatisticsTypeEnum;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.dto.TaskDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产任务Service接口
 *
 * @author yinjinlu
 * @date 2022-05-14
 */
public interface IProTaskService extends IService<ProTask> {
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
     * 批量删除生产任务
     *
     * @param taskIds 需要删除的生产任务主键集合
     * @return 结果
     */
    public int deleteProTaskByTaskIds(Long[] taskIds);

    /**
     * 删除生产任务信息
     *
     * @param taskId 生产任务主键
     * @return 结果
     */
    public int deleteProTaskByTaskId(Long taskId);

    /**
     * 批量新增/修改分配用户
     *
     * @param proTaskList    任务列表
     * @param taskUserId 指派用户id
     * @param taskBy     指派用户名称
     * @return
     */
    public String addAssignUsers(List<ProTask> proTaskList, Long taskUserId, String taskBy);

    /**
     * 根据工单分组展示
     *
     * @param page         分页对象
     * @param queryWrapper 条件
     * @return
     */
    public void listGroupByWorkOrder(LambdaQueryWrapper<ProTask> queryWrapper, Page<ProTask> page);

    /**
     * 查询任务，同时获取任务下的报工数量
     *
     * @param query 查询条件
     * @return
     */
    Page<TaskDTO> getTaskListAndFeedbackCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);


    /**
     * 查询任务，根据工单分组展示数据，并统计相关类型的次数
     *
     * @param query 查询条件
     * @param type  类型
     */
    Page<TaskDTO> getWorkOrderGroupAndSelectTypeCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query, StatisticsTypeEnum type);

    /**
     * 查询任务，并统计退料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getTaskListAndRtIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);

    /**
     * 查询任务，并统计废料次数
     * @param query 查询条件
     */
    Page<TaskDTO> getTaskListAndWasteIssueCount(IPage<TaskDTO> page, @Param(Constants.WRAPPER) Wrapper<TaskDTO> query);
}
