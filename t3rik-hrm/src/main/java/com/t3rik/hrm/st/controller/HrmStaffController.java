package com.t3rik.hrm.st.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.hrm.st.domain.HrmStaff;
import com.t3rik.hrm.st.service.IHrmStaffService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 员工花名册Controller
 *
 * @author 施子安
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/hrm/st/hrm-staff")
public class HrmStaffController extends BaseController {
    @Autowired
    private IHrmStaffService hrmStaffService;

    /**
     * 查询员工花名册列表
     */
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:list')")
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
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:export')")
    @Log(title = "员工花名册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HrmStaff hrmStaff) {
        // 获取查询条件
        LambdaQueryWrapper<HrmStaff> queryWrapper = getQueryWrapper(hrmStaff);
        List<HrmStaff> list = this.hrmStaffService.list(queryWrapper);
        ExcelUtil<HrmStaff> util = new ExcelUtil<HrmStaff>(HrmStaff. class);
        util.exportExcel(response, list, "员工花名册数据");
    }

    /**
     * 获取员工花名册详细信息
     */
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(this.hrmStaffService.getById(staffId));
    }

    /**
     * 新增员工花名册
     */
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:add')")
    @Log(title = "员工花名册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HrmStaff hrmStaff) {
        return toAjax(this.hrmStaffService.save(hrmStaff));
    }

    /**
     * 修改员工花名册
     */
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:edit')")
    @Log(title = "员工花名册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HrmStaff hrmStaff) {
        return toAjax(this.hrmStaffService.updateById(hrmStaff));
    }

    /**
     * 删除员工花名册
     */
    @PreAuthorize("@ss.hasPermi('st:hrmstaff:remove')")
    @Log(title = "员工花名册", businessType = BusinessType.DELETE)
    @DeleteMapping("/{staffIds}")
    public AjaxResult remove(@PathVariable List<Long> staffIds) {
        return toAjax(this.hrmStaffService.removeByIds(staffIds));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<HrmStaff> getQueryWrapper(HrmStaff hrmStaff) {
        LambdaQueryWrapper<HrmStaff> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(hrmStaff.getStaffCode() != null, HrmStaff::getStaffCode, hrmStaff.getStaffCode());
        queryWrapper.like(StringUtils.isNotEmpty(hrmStaff.getStaffName()), HrmStaff::getStaffName, hrmStaff.getStaffName());
        queryWrapper.eq(hrmStaff.getContactPhone() != null, HrmStaff::getContactPhone, hrmStaff.getContactPhone());
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
        queryWrapper.eq(hrmStaff.getEmergencyContactPhone() != null, HrmStaff::getEmergencyContactPhone, hrmStaff.getEmergencyContactPhone());
        queryWrapper.eq(hrmStaff.getEducation() != null, HrmStaff::getEducation, hrmStaff.getEducation());
        queryWrapper.eq(hrmStaff.getJoinedTime() != null, HrmStaff::getJoinedTime, hrmStaff.getJoinedTime());
        queryWrapper.eq(hrmStaff.getLeaveTime() != null, HrmStaff::getLeaveTime, hrmStaff.getLeaveTime());
        queryWrapper.eq(hrmStaff.getCreateUserId() != null, HrmStaff::getCreateUserId, hrmStaff.getCreateUserId());
        queryWrapper.eq(hrmStaff.getUpdateUserId() != null, HrmStaff::getUpdateUserId, hrmStaff.getUpdateUserId());
        queryWrapper.eq(hrmStaff.getStaffStatus() != null, HrmStaff::getStaffStatus, hrmStaff.getStaffStatus());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(HrmStaff::getCreateTime);
        Map<String, Object> params = hrmStaff.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,HrmStaff::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
