package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.mes.wm.domain.WmItemRecpt;
import com.t3rik.mes.wm.domain.WmItemRecptLine;
import com.t3rik.mes.wm.mapper.WmItemRecptLineMapper;
import com.t3rik.mes.wm.service.IWmItemRecptLineService;
import com.t3rik.mes.wm.service.IWmItemRecptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 物料入库单行Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-22
 */
@Service
public class WmItemRecptLineServiceImpl extends ServiceImpl<WmItemRecptLineMapper, WmItemRecptLine> implements IWmItemRecptLineService {
    @Resource
    private WmItemRecptLineMapper wmItemRecptLineMapper;
    @Resource
    private IWmItemRecptService itemRecptService;

    /**
     * 查询物料入库单行
     *
     * @param lineId 物料入库单行主键
     * @return 物料入库单行
     */
    @Override
    public WmItemRecptLine selectWmItemRecptLineByLineId(Long lineId) {
        return wmItemRecptLineMapper.selectWmItemRecptLineByLineId(lineId);
    }

    /**
     * 查询物料入库单行列表
     *
     * @param wmItemRecptLine 物料入库单行
     * @return 物料入库单行
     */
    @Override
    public List<WmItemRecptLine> selectWmItemRecptLineList(WmItemRecptLine wmItemRecptLine) {
        return wmItemRecptLineMapper.selectWmItemRecptLineList(wmItemRecptLine);
    }

    /**
     * 新增物料入库单行
     *
     * @param wmItemRecptLine 物料入库单行
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmItemRecptLine(WmItemRecptLine wmItemRecptLine) {
        wmItemRecptLine.setCreateTime(DateUtils.getNowDate());
        int count = wmItemRecptLineMapper.insertWmItemRecptLine(wmItemRecptLine);
        // 更新主表金额
        updateItemRecptTotalAmount(wmItemRecptLine);
        return count;
    }

    /**
     * 更新主表金额
     */
    private void updateItemRecptTotalAmount(WmItemRecptLine wmItemRecptLine) {
        // 计算当前入库单行总金额
        BigDecimal totalAmount = this.lambdaQuery()
                .eq(WmItemRecptLine::getRecptId, wmItemRecptLine.getRecptId())
                .list()
                .stream().map(WmItemRecptLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 更新到主单
        this.itemRecptService.lambdaUpdate()
                .set(WmItemRecpt::getTotalAmount, totalAmount)
                .eq(WmItemRecpt::getRecptId, wmItemRecptLine.getRecptId())
                .update();
    }

    /**
     * 修改物料入库单行
     *
     * @param wmItemRecptLine 物料入库单行
     * @return 结果
     */
    @Override
    public int updateWmItemRecptLine(WmItemRecptLine wmItemRecptLine) {
        wmItemRecptLine.setUpdateTime(DateUtils.getNowDate());
        int count = wmItemRecptLineMapper.updateWmItemRecptLine(wmItemRecptLine);
        // 更新主表金额
        updateItemRecptTotalAmount(wmItemRecptLine);
        return count;
    }

    /**
     * 批量删除物料入库单行
     *
     * @param lineIds 需要删除的物料入库单行主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmItemRecptLineByLineIds(Long[] lineIds) {
        WmItemRecptLine itemRecptLine = this.getById(lineIds[0]);
        int count = wmItemRecptLineMapper.deleteWmItemRecptLineByLineIds(lineIds);
        // 更新主表金额
        updateItemRecptTotalAmount(itemRecptLine);
        return count;
    }

    /**
     * 删除物料入库单行信息
     *
     * @param lineId 物料入库单行主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmItemRecptLineByLineId(Long lineId) {
        WmItemRecptLine itemRecptLine = this.getById(lineId);
        int count = wmItemRecptLineMapper.deleteWmItemRecptLineByLineId(lineId);
        // 更新主表金额
        updateItemRecptTotalAmount(itemRecptLine);
        return count;
    }

    @Override
    public int deleteByRecptId(Long recptId) {
        return wmItemRecptLineMapper.deleteByRecptId(recptId);
    }
}
