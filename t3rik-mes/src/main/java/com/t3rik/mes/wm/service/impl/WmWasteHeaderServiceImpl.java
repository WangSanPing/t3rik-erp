package com.t3rik.mes.wm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.wm.domain.WmWasteHeader;
import com.t3rik.mes.wm.domain.tx.WmWasteTxBean;
import com.t3rik.mes.wm.dto.WasteHeaderAndLineDTO;
import com.t3rik.mes.wm.mapper.WmWasteHeaderMapper;
import com.t3rik.mes.wm.service.IWmWasteHeaderService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产废料单头Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-11
 */
@Service
public class WmWasteHeaderServiceImpl extends ServiceImpl<WmWasteHeaderMapper, WmWasteHeader> implements IWmWasteHeaderService {
    @Resource
    private WmWasteHeaderMapper wmWasteHeaderMapper;

    /**
     * 检查编号是否重复
     *
     * @param wmWasteHeader
     * @return
     */
    @Override
    public String checkUnique(WmWasteHeader wmWasteHeader) {
        // 根据编号查询
        WmWasteHeader wasteHeader = this.lambdaQuery().eq(WmWasteHeader::getWasteCode, wmWasteHeader.getWasteCode()).one();
        Long wastId = wmWasteHeader.getWasteId() == null ? -1L : wmWasteHeader.getWasteId();
        if (StringUtils.isNotNull(wasteHeader) && wasteHeader.getWasteId().longValue() != wastId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 批量删除生产废料单头
     *
     * @param wasteIds 需要删除的生产废料单头主键集合
     * @return 结果
     */
    @Override
    public int delWmWasteHeaderIds(List<Long> wasteIds) {
        return wmWasteHeaderMapper.delWmWasteHeaderIds(wasteIds);
    }

    /**
     * 关联查询废料信息所对应的库存记录
     *
     * @param wasteId 废料id
     * @return
     */
    @Override
    public List<WmWasteTxBean> getTxBeans(Long wasteId) {
        return wmWasteHeaderMapper.getTxBeans(wasteId);
    }

    /**
     * 查询废料详情
     *
     * @param query
     */
    @Override
    public List<WasteHeaderAndLineDTO> getWasteIssueDetail(Wrapper<WasteHeaderAndLineDTO> query) {
        return this.wmWasteHeaderMapper.getWasteIssueDetail(query);
    }

    /**
     * 查询废料详情列表
     *
     * @param query
     */
    @Override
    public List<WasteHeaderAndLineDTO> getWasteIssueDetailList(Wrapper<WasteHeaderAndLineDTO> query) {
        return this.wmWasteHeaderMapper.getWasteIssueDetailList(query);
    }
}
