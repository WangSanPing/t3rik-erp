package com.t3rik.mes.common.mapper;

import com.t3rik.mes.common.dto.KeyValueDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author t3rik
 * @date 2024/8/23 18:28
 */
@Mapper
public interface CommonMapper {

    /**
     * 查询仓库不包含参数列表的仓库信息
     */
    List<KeyValueDTO> selectAllWarehouse(@Param("notInCodeList") List<String> notInCodeList);

    /**
     * 查询库位不包含参数列表的仓库信息
     */
    List<KeyValueDTO> selectAllArea(@Param("notInCodeList") List<String> notInCodeList);

    /**
     * 查询库区不包含参数列表的仓库信息
     */
    List<KeyValueDTO> selectAllLocation(@Param("notInCodeList") List<String> notInCodeList);
}
