package com.t3rik.mes.pro.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.dto.ClientOrderItemPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户订单材料
 * Mapper接口
 *
 * @author t3rik
 * @date 2024-05-07
 */
@Mapper
public interface ProClientOrderItemMapper extends BaseMapper<ProClientOrderItem> {

    /**
     * 查询当前客户订单最大级别数据
     */
    Integer selectLevelByClientOrderId(@Param("clientOrderId") Long clientOrderId);

    Page<ClientOrderItemPageDto> getClientOrderItemPage(IPage<ClientOrderItemPageDto> page, @Param("clientOrderId") Long clientOrderId,@Param("whCodes") List<String> whCodes);
}
