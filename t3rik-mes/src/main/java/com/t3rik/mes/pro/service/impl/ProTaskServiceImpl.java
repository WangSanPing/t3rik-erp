package com.t3rik.mes.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.enums.mes.StatisticsTypeEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.dto.TaskDTO;
import com.t3rik.mes.pro.mapper.ProTaskMapper;
import com.t3rik.mes.pro.service.IProTaskService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * 生产任务Service业务层处理
 *
 * @author t3rik
 * @date 2024-10-14
 */
@Service
public class ProTaskServiceImpl extends ServiceImpl<ProTaskMapper, ProTask> implements IProTaskService {
    @Resource
    private ProTaskMapper proTaskMapper;

    /**
     * 查询生产任务
     *
     * @param taskId 生产任务主键
     * @return 生产任务
     */
    @Override
    public ProTask selectProTaskByTaskId(Long taskId) {
        return proTaskMapper.selectProTaskByTaskId(taskId);
    }

    /**
     * 查询生产任务列表
     *
     * @param proTask 生产任务
     * @return 生产任务
     */
    @Override
    public List<ProTask> selectProTaskList(ProTask proTask) {
        return proTaskMapper.selectProTaskList(proTask);
    }

    /**
     * 查询某个工单的各个工序生产进度
     *
     * @param workorderId
     * @return
     */
    @Override
    public List<ProTask> selectProTaskProcessViewByWorkorder(Long workorderId) {
        return proTaskMapper.selectProTaskProcessViewByWorkorder(workorderId);
    }

    /**
     * 新增生产任务
     *
     * @param proTask 生产任务
     * @return 结果
     */
    @Override
    public int insertProTask(ProTask proTask) {
        proTask.setCreateTime(DateUtils.getNowDate());
        return proTaskMapper.insertProTask(proTask);
    }

    /**
     * 修改生产任务
     *
     * @param proTask 生产任务
     * @return 结果
     */
    @Override
    public int updateProTask(ProTask proTask) {
        proTask.setUpdateTime(DateUtils.getNowDate());
        return proTaskMapper.updateProTask(proTask);
    }

    /**
     * 批量删除生产任务
     *
     * @param taskIds 需要删除的生产任务主键
     * @return 结果
     */
    @Override
    public int deleteProTaskByTaskIds(Long[] taskIds) {
        return proTaskMapper.deleteProTaskByTaskIds(taskIds);
    }

    /**
     * 删除生产任务信息
     *
     * @param taskId 生产任务主键
     * @return 结果
     */
    @Override
    public int deleteProTaskByTaskId(Long taskId) {
        return proTaskMapper.deleteProTaskByTaskId(taskId);
    }

    /**
     * 批量新增/修改分配用户
     *
     * @param taskIds    主键id
     * @param taskUserId 指派用户id
     * @param taskBy     指派用户名称
     * @return
     */
    @Override
    public String addAssignUsers(List<String> taskIds, Long taskUserId, String taskBy) {
        Date nowDate = DateUtils.getNowDate();
        // 符合条件的任务id
        List<Long> taskIdList = this.lambdaQuery()
                .in(ProTask::getTaskId, taskIds)
                .list()
                .stream()
                .filter(proTask -> !(proTask.getTaskUserId() != null && proTask.getEndTime().compareTo(nowDate) < 1))
                .map(ProTask::getTaskId)
                .toList();
        // 更新任务指派用户
        if (!taskIdList.isEmpty()) {
            ////派单已确认
            this.lambdaUpdate()
                    .in(ProTask::getTaskId, taskIdList)
                    .set(ProTask::getTaskUserId, taskUserId)
                    .set(ProTask::getTaskBy, taskBy)
                    .set(ProTask::getStatus, OrderStatusEnum.CONFIRMED.getCode())
                    .update(new ProTask());
        }
        // 任务已超过设定完成生产时间编号
        List<String> codeList = this.lambdaQuery()
                .in(ProTask::getTaskId, taskIds)
                .list()
                .stream()
                .filter(proTask -> proTask.getTaskUserId() != null && proTask.getEndTime().compareTo(nowDate) < 1)
                .map(ProTask::getTaskCode)
                .toList();
        // 返回提示信息
        StringBuilder sb = new StringBuilder();
        if (!codeList.isEmpty()) {
            sb.append("编号为：").append(String.join(",", codeList)).append("的任务已超过设定完成生产时间，不能再指派！");
        }
        return sb.toString();
    }

    /**
     * 根据工单分组展示
     *
     * @param page         分页对象
     * @param queryWrapper 条件
     * @return
     */
    @Override
    public void listGroupByWorkOrder(LambdaQueryWrapper<ProTask> queryWrapper, Page<ProTask> page) {
        // 查询
        this.page(page, queryWrapper);
        // 分组
        Map<Long, List<ProTask>> listMap = page.getRecords().stream().collect(groupingBy(ProTask::getWorkorderId));
        // 构建 ProTask返回值列表
        List<ProTask> proTaskList = listMap.values().stream()
                .map(proTasks -> {
                    long parentId = IdWorker.getId();
                    ProTask proTask = new ProTask();
                    proTask.setTaskId(parentId);
                    proTask.setWorkorderCode(proTasks.get(0).getWorkorderCode());
                    proTask.setWorkorderName(proTasks.get(0).getWorkorderName());
                    proTasks.forEach(task -> task.setParentId(parentId));
                    proTask.setChildTasks(proTasks);
                    return proTask;
                })
                .collect(Collectors.toList());

        // 更新分页对象的记录为 ProTask 列表
        page.setRecords(proTaskList);
        page.setTotal(proTaskList.size());
    }

    /**
     * 查询任务，同时获取任务下的报工数量
     *
     * @param page
     * @param query 查询条件
     */
    @Override
    public Page<TaskDTO> getTaskListAndFeedbackCount(IPage<TaskDTO> page, Wrapper<TaskDTO> query) {
        return this.proTaskMapper.getTaskListAndFeedbackCount(page, query);
    }

    /**
     * 查询任务，根据工单分组展示数据，并统计相关类型的次数
     *
     * @param page
     * @param query 查询条件
     * @param type  类型
     */
    @Override
    public Page<TaskDTO> getWorkOrderGroupAndSelectTypeCount(IPage<TaskDTO> page, Wrapper<TaskDTO> query, StatisticsTypeEnum type) {
        switch (type) {
            case ISSUED_QUANTITY -> {
                return this.proTaskMapper.getWorkOrderGroupAndIssueCount(page, query);
            }
            case RT_ISSUED_QUANTITY -> {
                return this.proTaskMapper.getWorkOrderGroupAndRtIssueCount(page, query);
            }
            case WASTE_QUANTITY -> {
                return this.proTaskMapper.getWorkOrderGroupAndWasteCount(page, query);
            }
            default -> throw new BusinessException(MsgConstants.PARAM_ERROR);
        }
    }

    /**
     * 查询任务，并统计退料次数
     *
     * @param page
     * @param query 查询条件
     */
    @Override
    public Page<TaskDTO> getTaskListAndRtIssueCount(IPage<TaskDTO> page, Wrapper<TaskDTO> query) {
        return this.proTaskMapper.getTaskListAndRtIssueCount(page, query);
    }
}
