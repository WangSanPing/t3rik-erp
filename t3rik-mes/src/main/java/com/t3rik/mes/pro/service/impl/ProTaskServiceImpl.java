package com.t3rik.mes.pro.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.pro.mapper.ProTaskMapper;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.service.IProTaskService;

/**
 * 生产任务Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-14
 */
@Service
public class ProTaskServiceImpl extends ServiceImpl<ProTaskMapper, ProTask> implements IProTaskService
{
    @Autowired
    private ProTaskMapper proTaskMapper;

    /**
     * 查询生产任务
     *
     * @param taskId 生产任务主键
     * @return 生产任务
     */
    @Override
    public ProTask selectProTaskByTaskId(Long taskId)
    {
        return proTaskMapper.selectProTaskByTaskId(taskId);
    }

    /**
     * 查询生产任务列表
     *
     * @param proTask 生产任务
     * @return 生产任务
     */
    @Override
    public List<ProTask> selectProTaskList(ProTask proTask)
    {
        return proTaskMapper.selectProTaskList(proTask);
    }

    /**
     * 查询某个工单的各个工序生产进度
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
    public int insertProTask(ProTask proTask)
    {
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
    public int updateProTask(ProTask proTask)
    {
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
    public int deleteProTaskByTaskIds(Long[] taskIds)
    {
        return proTaskMapper.deleteProTaskByTaskIds(taskIds);
    }

    /**
     * 删除生产任务信息
     *
     * @param taskId 生产任务主键
     * @return 结果
     */
    @Override
    public int deleteProTaskByTaskId(Long taskId)
    {
        return proTaskMapper.deleteProTaskByTaskId(taskId);
    }
}
