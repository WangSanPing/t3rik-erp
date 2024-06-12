package com.t3rik.mes.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProFeedback;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.domain.ProWorkorder;

import java.util.List;

/**
 * 生产工单Service接口
 *
 * @author yinjinlu
 * @date 2022-05-09
 */
public interface IProWorkorderService extends IService<ProWorkorder> {
    /**
     * 查询生产工单
     *
     * @param workorderId 生产工单主键
     * @return 生产工单
     */
    public ProWorkorder selectProWorkorderByWorkorderId(Long workorderId);

    /**
     * 查询生产工单列表
     *
     * @param proWorkorder 生产工单
     * @return 生产工单集合
     */
    public List<ProWorkorder> selectProWorkorderList(ProWorkorder proWorkorder);


    public String checkWorkorderCodeUnique(ProWorkorder proWorkorder);

    /**
     * 新增生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    public int insertProWorkorder(ProWorkorder proWorkorder);

    /**
     * 修改生产工单
     *
     * @param proWorkorder 生产工单
     * @return 结果
     */
    public int updateProWorkorder(ProWorkorder proWorkorder);

    /**
     * 批量删除生产工单
     *
     * @param workorderIds 需要删除的生产工单主键集合
     * @return 结果
     */
    public int deleteProWorkorderByWorkorderIds(Long[] workorderIds);

    /**
     * 删除生产工单信息
     *
     * @param workorderId 生产工单主键
     * @return 结果
     */
    public int deleteProWorkorderByWorkorderId(Long workorderId);

}
