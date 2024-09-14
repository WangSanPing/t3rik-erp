package com.t3rik.hrm.sm.controller;

import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import com.t3rik.hrm.sm.vo.HrmStaffVo;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 面试管理
 *
 * @author t3rik
 * @date 2024/9/11 13:34
 */
@RestController
@RequestMapping("/hrm/sm/interview")
public class InterviewController extends BaseController {

    @Resource
    private IHrmStaffService hrmStaffService;

    /**
     * 查询人才花名册列表
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:listTalents')")
    @GetMapping("/listTalents")
    public TableDataInfo listTalents(HrmStaff hrmStaff) {
        startPage();
        List<HrmStaffVo> list = hrmStaffService.listTalents(hrmStaff);
        return getDataTable(list);
    }
}
