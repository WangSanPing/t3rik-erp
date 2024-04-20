package com.t3rik.mes.wm.service.impl;

import java.util.List;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.tx.OutsourceRecptTxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.wm.mapper.WmOutsourceRecptMapper;
import com.t3rik.mes.wm.domain.WmOutsourceRecpt;
import com.t3rik.mes.wm.service.IWmOutsourceRecptService;

/**
 * 外协入库单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
@Service
public class WmOutsourceRecptServiceImpl implements IWmOutsourceRecptService 
{
    @Autowired
    private WmOutsourceRecptMapper wmOutsourceRecptMapper;

    /**
     * 查询外协入库单
     * 
     * @param recptId 外协入库单主键
     * @return 外协入库单
     */
    @Override
    public WmOutsourceRecpt selectWmOutsourceRecptByRecptId(Long recptId)
    {
        return wmOutsourceRecptMapper.selectWmOutsourceRecptByRecptId(recptId);
    }

    /**
     * 查询外协入库单列表
     * 
     * @param wmOutsourceRecpt 外协入库单
     * @return 外协入库单
     */
    @Override
    public List<WmOutsourceRecpt> selectWmOutsourceRecptList(WmOutsourceRecpt wmOutsourceRecpt)
    {
        return wmOutsourceRecptMapper.selectWmOutsourceRecptList(wmOutsourceRecpt);
    }

    /**
     * 新增外协入库单
     * 
     * @param wmOutsourceRecpt 外协入库单
     * @return 结果
     */
    @Override
    public int insertWmOutsourceRecpt(WmOutsourceRecpt wmOutsourceRecpt)
    {
        wmOutsourceRecpt.setCreateTime(DateUtils.getNowDate());
        return wmOutsourceRecptMapper.insertWmOutsourceRecpt(wmOutsourceRecpt);
    }

    /**
     * 修改外协入库单
     * 
     * @param wmOutsourceRecpt 外协入库单
     * @return 结果
     */
    @Override
    public int updateWmOutsourceRecpt(WmOutsourceRecpt wmOutsourceRecpt)
    {
        wmOutsourceRecpt.setUpdateTime(DateUtils.getNowDate());
        return wmOutsourceRecptMapper.updateWmOutsourceRecpt(wmOutsourceRecpt);
    }

    /**
     * 批量删除外协入库单
     * 
     * @param recptIds 需要删除的外协入库单主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceRecptByRecptIds(Long[] recptIds)
    {
        return wmOutsourceRecptMapper.deleteWmOutsourceRecptByRecptIds(recptIds);
    }

    /**
     * 删除外协入库单信息
     * 
     * @param recptId 外协入库单主键
     * @return 结果
     */
    @Override
    public int deleteWmOutsourceRecptByRecptId(Long recptId)
    {
        return wmOutsourceRecptMapper.deleteWmOutsourceRecptByRecptId(recptId);
    }

    @Override
    public List<OutsourceRecptTxBean> getTxBeans(Long recptId) {
        return wmOutsourceRecptMapper.getTxBeans(recptId);
    }
}
