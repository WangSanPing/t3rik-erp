package com.t3rik.hrm.sm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.t3rik.common.enums.hrm.StaffStatusEnum;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.hrm.sm.domain.HrmInterviewRecord;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.dto.InterviewRecordDTO;
import com.t3rik.hrm.sm.mapper.HrmInterviewRecordMapper;
import com.t3rik.hrm.sm.service.IHrmInterviewRecordService;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 面试记录Service业务层处理
 *
 * @author t3rik
 * @date 2024-09-15
 */

@Service
public class HrmInterviewRecordServiceImpl extends ServiceImpl<HrmInterviewRecordMapper, HrmInterviewRecord> implements IHrmInterviewRecordService {

    @Resource
    private HrmInterviewRecordMapper interviewRecordMapper;

    @Resource
    private IHrmStaffService staffService;

    /**
     * 按员工分组查询列表
     */
    @Override
    public Page<InterviewRecordDTO> pageGroupByStaff(HrmInterviewRecord query) {
        return this.pageGroupByStaffWithStatus(query,
                List.of(StaffStatusEnum.ALTERNATE.getCode(),
                        StaffStatusEnum.INTERVIEW.getCode(),
                        StaffStatusEnum.REEXAMINATION.getCode(),
                        StaffStatusEnum.PASS_THE_INTERVIEW.getCode(),
                        StaffStatusEnum.INTERVIEW_FAIL.getCode(),
                        StaffStatusEnum.BE_PENDING.getCode())
        );
    }

    /**
     * 按员工分组查询列表-可以添加状态条件
     */
    @Override
    public Page<InterviewRecordDTO> pageGroupByStaffWithStatus(HrmInterviewRecord query, List<Integer> statusList) {
        return this.getGroupByStaffList(query, statusList);
    }

    private Page<InterviewRecordDTO> getGroupByStaffList(HrmInterviewRecord query, List<Integer> statusList) {
        Page<InterviewRecordDTO> staffList = this.interviewRecordMapper.pageGroupByStaff(query, statusList);
        // 没有数据直接返回
        if (CollectionUtils.isEmpty(staffList)) {
            return staffList;
        }
        // 重组数据
        try (Page<InterviewRecordDTO> result = new Page<>()) {
            staffList.stream()
                    .collect(Collectors.groupingBy(HrmInterviewRecord::getStaffId))
                    .forEach((k, v) -> {
                        InterviewRecordDTO dto = new InterviewRecordDTO();
                        BeanUtil.copyProperties(v.get(0), dto);
                        // 用户前端判断是否显示操作按钮，主数据置空
                        dto.setInterviewRecordId(null);
                        dto.setParentId(k);
                        // 当前员工状态
                        dto.setStatus(v.get(0).getCurrentStatus().longValue());
                        // 取最新的面试定级
                        v.stream()
                                .filter(f -> StringUtils.isNotBlank(f.getRankType()))
                                .max(Comparator.comparing(HrmInterviewRecord::getTimeForInterview))
                                .ifPresent(maxRank -> {
                                    dto.setRankId(maxRank.getRankId());
                                    dto.setRankType(maxRank.getRankType());
                                    dto.setRankName(maxRank.getRankName());
                                    dto.setRankCode(maxRank.getRankCode());
                                });
                        // 子集
                        List<InterviewRecordDTO> recordDTOList = v.stream()
                                .filter(f -> f.getInterviewRecordId() != null)
                                .filter(f -> f.getTimeForInterview() != null)
                                .sorted(Comparator.comparing(HrmInterviewRecord::getTimeForInterview).reversed())
                                .toList();
                        // 面试次数
                        dto.setTimes(recordDTOList.size());
                        dto.setChildren(recordDTOList);
                        // 最新的面试反馈
                        if (CollectionUtils.isNotEmpty(recordDTOList)) {
                            dto.setInterviewFeedback(
                                    StringUtils.isNotBlank(recordDTOList.get(0).getInterviewFeedback()) ?
                                            recordDTOList.get(0).getInterviewFeedback() :
                                            "最新一轮面试反馈：暂无文字反馈");
                        }
                        result.add(dto);
                    });
            result.setTotal(staffList.getTotal());
            result.setPageNum(staffList.getPageNum());
            result.setPageSize(staffList.getPageSize());
            result.setPages(staffList.getPages());
            return result;
        }
    }

    /**
     * 更新面试结果和员工状态
     */
    @Transactional
    @Override
    public Boolean updateWithStaff(HrmInterviewRecord hrmInterviewRecord) {
        // 更新人员表状态
        this.staffService.lambdaUpdate()
                .set(HrmStaff::getStatus, hrmInterviewRecord.getStatus())
                .eq(HrmStaff::getStaffId, hrmInterviewRecord.getStaffId())
                .update(new HrmStaff());
        // 更新子表
        return this.updateById(hrmInterviewRecord);
    }
}
