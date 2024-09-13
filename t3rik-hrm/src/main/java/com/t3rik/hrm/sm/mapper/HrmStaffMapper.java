package com.t3rik.hrm.sm.mapper;
import com.t3rik.hrm.sm.vo.HrmStaffVo;

import com.t3rik.hrm.sm.domain.HrmStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工花名册Mapper接口
 *
 * @author 施子安
 * @date 2024-07-11
 */
@Mapper
public interface HrmStaffMapper extends BaseMapper<HrmStaff> {

    /**
     * 人才登记列表查询
     * @param hrmStaff
     * @return
     */
    List<HrmStaff> listAll();


    List<HrmStaffVo> listTalents(HrmStaff hrmStaff);

    /**
     * 获取人才详情信息
     */
    HrmStaffVo getTalents(@Param("staffId") Long staffId);
}
