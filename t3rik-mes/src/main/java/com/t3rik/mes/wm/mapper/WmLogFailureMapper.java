package com.t3rik.mes.wm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t3rik.mes.wm.domain.WmLogFailure;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志写入失败记录Mapper接口
 *
 * @author t3rik
 * @date 2025-04-02
 */
@Mapper
public interface WmLogFailureMapper extends BaseMapper<WmLogFailure> {

}
