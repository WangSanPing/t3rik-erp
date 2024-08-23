package com.t3rik.mes.common.service;

import com.t3rik.mes.common.dto.SelectInfoDTO;

import java.util.List;

/**
 * 共通service
 *
 * @author t3rik
 * @date 2024/8/23 18:36
 */
public interface ICommonService {

    /**
     * 查询仓库不包含参数列表的仓库信息
     */
    List<SelectInfoDTO> selectAllWarehouse(List<String> notInCodeList);

    /**
     * 查询库位不包含参数列表的仓库信息
     */
    List<SelectInfoDTO> selectAllArea(List<String> notInCodeList);

    /**
     * 查询库区不包含参数列表的仓库信息
     */
    List<SelectInfoDTO> selectAllLocation(List<String> notInCodeList);
}
