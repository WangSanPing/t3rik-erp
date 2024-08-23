package com.t3rik.mes.common.service.impl;

import com.t3rik.mes.common.dto.KeyValueDTO;
import com.t3rik.mes.common.mapper.CommonMapper;
import com.t3rik.mes.common.service.ICommonService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 共通service
 *
 * @author t3rik
 * @date 2024/8/23 18:37
 */
@Service
public class CommonServiceImpl implements ICommonService {


    @Resource
    private CommonMapper commonMapper;

    /**
     * 查询仓库不包含参数列表的仓库信息
     *
     * @param notInCodeList
     */
    @Override
    public List<KeyValueDTO> selectAllWarehouse(List<String> notInCodeList) {
        return this.commonMapper.selectAllWarehouse(notInCodeList);
    }

    /**
     * 查询库位不包含参数列表的仓库信息
     *
     * @param notInCodeList
     */
    @Override
    public List<KeyValueDTO> selectAllArea(List<String> notInCodeList) {
        return this.commonMapper.selectAllArea(notInCodeList);
    }

    /**
     * 查询库区不包含参数列表的仓库信息
     *
     * @param notInCodeList
     */
    @Override
    public List<KeyValueDTO> selectAllLocation(List<String> notInCodeList) {
        return this.commonMapper.selectAllLocation(notInCodeList);
    }
}
