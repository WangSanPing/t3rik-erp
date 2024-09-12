package com.t3rik.hrm.sm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.hrm.common.HrmCheckUtils;
import com.t3rik.hrm.sm.domain.HrmStaff;
import com.t3rik.hrm.sm.service.IHrmStaffService;
import com.t3rik.hrm.sm.state.StaffState;
import com.t3rik.hrm.sm.vo.HrmStaffVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 员工花名册Controller
 *
 * @author 施子安
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/hrm/sm/hrm-staff")
public class HrmStaffController extends BaseController {
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

    /**
     * 查询员工花名册列表
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:list')")
    @GetMapping("/list")
    public TableDataInfo list(HrmStaff hrmStaff) {
        // 获取查询条件
        LambdaQueryWrapper<HrmStaff> queryWrapper = getQueryWrapper(hrmStaff);
        // 组装分页
        Page<HrmStaff> page = getMPPage(hrmStaff);
        // 查询
        this.hrmStaffService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出员工花名册列表
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:export')")
    @Log(title = "员工花名册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HrmStaff hrmStaff) {
        // 获取查询条件
        LambdaQueryWrapper<HrmStaff> queryWrapper = getQueryWrapper(hrmStaff);
        List<HrmStaff> list = this.hrmStaffService.list(queryWrapper);
        ExcelUtil<HrmStaff> util = new ExcelUtil<HrmStaff>(HrmStaff.class);
        util.exportExcel(response, list, "员工花名册数据");
    }


    /**
     * 获取员工花名册详细信息
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(this.hrmStaffService.getById(staffId));
    }

    /**
     * 获取人才详情信息
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:getTalent')")
    @GetMapping(value = "/getTalent/{staffId}")
    public AjaxResult getTalents(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(this.hrmStaffService.getTalents(staffId));
    }

    /**
     * 新增员工花名册
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:add')")
    @Log(title = "员工花名册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HrmStaff hrmStaff) {
        return toAjax(this.hrmStaffService.save(hrmStaff));
    }

    /**
     * 修改员工花名册
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:edit')")
    @Log(title = "员工花名册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HrmStaff hrmStaff) {
        return toAjax(this.hrmStaffService.updateById(hrmStaff));
    }


    /**
     * 邀请面试
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:edit')")
    @Log(title = "邀请面试", businessType = BusinessType.UPDATE)
    @PutMapping("/interview/{staffId}")
    public AjaxResult interview(@PathVariable Long staffId) {
        HrmStaff staff = this.hrmStaffService.getById(staffId);
        Optional.ofNullable(staff).orElseThrow(() -> new BusinessException(MsgConstants.PARAM_ERROR));
        StaffState nextState = StaffState.INTERVIEW;
        // 校验状态
        HrmCheckUtils.checkStaffStatus(nextState, staff.getStatus()).throwMsg(MsgConstants.ERROR_STATUS);
        // 邀请面试
        this.hrmStaffService.lambdaUpdate()
                .eq(HrmStaff::getStaffId, staffId)
                .set(HrmStaff::getStatus, nextState.getCurrentStatus())
                .update(new HrmStaff());
        return AjaxResult.success();
    }

    /**
     * 删除员工花名册
     */
    @PreAuthorize("@ss.hasPermi('sm:hrmstaff:remove')")
    @Log(title = "员工花名册", businessType = BusinessType.DELETE)
    @DeleteMapping("/{staffIds}")
    public AjaxResult remove(@PathVariable List<Long> staffIds) {
        return toAjax(this.hrmStaffService.lambdaUpdate().in(HrmStaff::getStaffId, staffIds).set(HrmStaff::getDeleted, Boolean.TRUE).set(HrmStaff::getDeleteAt, new Date()).update());
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<HrmStaff> getQueryWrapper(HrmStaff hrmStaff) {
        LambdaQueryWrapper<HrmStaff> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(hrmStaff.getStaffCode()), HrmStaff::getStaffCode, hrmStaff.getStaffCode());
        queryWrapper.like(StringUtils.isNotEmpty(hrmStaff.getStaffName()), HrmStaff::getStaffName, hrmStaff.getStaffName());
/*        queryWrapper.eq(hrmStaff.getContactPhone() != null, HrmStaff::getContactPhone, hrmStaff.getContactPhone());
        queryWrapper.eq(hrmStaff.getSex() != null, HrmStaff::getSex, hrmStaff.getSex());
        queryWrapper.eq(hrmStaff.getEthnicity() != null, HrmStaff::getEthnicity, hrmStaff.getEthnicity());
        queryWrapper.eq(hrmStaff.getBirthDate() != null, HrmStaff::getBirthDate, hrmStaff.getBirthDate());
        queryWrapper.eq(hrmStaff.getEmail() != null, HrmStaff::getEmail, hrmStaff.getEmail());
        queryWrapper.eq(hrmStaff.getMaritalStatus() != null, HrmStaff::getMaritalStatus, hrmStaff.getMaritalStatus());
        queryWrapper.eq(hrmStaff.getPoliticalOutlook() != null, HrmStaff::getPoliticalOutlook, hrmStaff.getPoliticalOutlook());
        queryWrapper.eq(hrmStaff.getIdCard() != null, HrmStaff::getIdCard, hrmStaff.getIdCard());
        queryWrapper.eq(hrmStaff.getOrigin() != null, HrmStaff::getOrigin, hrmStaff.getOrigin());
        queryWrapper.eq(hrmStaff.getHouseholdAddress() != null, HrmStaff::getHouseholdAddress, hrmStaff.getHouseholdAddress());
        queryWrapper.eq(hrmStaff.getCurrentAddress() != null, HrmStaff::getCurrentAddress, hrmStaff.getCurrentAddress());
        queryWrapper.eq(hrmStaff.getEmergencyContact() != null, HrmStaff::getEmergencyContact, hrmStaff.getEmergencyContact());
        queryWrapper.eq(hrmStaff.getRelationship() != null, HrmStaff::getRelationship, hrmStaff.getRelationship());
        queryWrapper.eq(hrmStaff.getEmergencyContactPhone() != null, HrmStaff::getEmergencyContactPhone, hrmStaff.getEmergencyContactPhone());*/
        queryWrapper.eq(hrmStaff.getEducation() != null, HrmStaff::getEducation, hrmStaff.getEducation());
/*        queryWrapper.eq(hrmStaff.getJoinedTime() != null, HrmStaff::getJoinedTime, hrmStaff.getJoinedTime());
        queryWrapper.eq(hrmStaff.getLeaveTime() != null, HrmStaff::getLeaveTime, hrmStaff.getLeaveTime());*/
        queryWrapper.eq(hrmStaff.getStatus() != null, HrmStaff::getStatus, hrmStaff.getStatus());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(HrmStaff::getCreateTime);
        Map<String, Object> params = hrmStaff.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, HrmStaff::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
