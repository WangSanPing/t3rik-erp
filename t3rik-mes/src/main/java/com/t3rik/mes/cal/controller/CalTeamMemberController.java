package com.t3rik.mes.cal.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.cal.domain.CalTeamMember;
import com.t3rik.mes.cal.service.ICalTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 班组成员Controller
 * 
 * @author yinjinlu
 * @date 2022-06-05
 */
@RestController
@RequestMapping("/mes/cal/teammember")
public class CalTeamMemberController extends BaseController
{
    @Autowired
    private ICalTeamMemberService calTeamMemberService;

    /**
     * 查询班组成员列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CalTeamMember calTeamMember)
    {
        startPage();
        List<CalTeamMember> list = calTeamMemberService.selectCalTeamMemberList(calTeamMember);
        return getDataTable(list);
    }

    /**
     * 导出班组成员列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:team:export')")
    @Log(title = "班组成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalTeamMember calTeamMember)
    {
        List<CalTeamMember> list = calTeamMemberService.selectCalTeamMemberList(calTeamMember);
        ExcelUtil<CalTeamMember> util = new ExcelUtil<CalTeamMember>(CalTeamMember.class);
        util.exportExcel(response, list, "班组成员数据");
    }

    /**
     * 获取班组成员详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:team:query')")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId)
    {
        return AjaxResult.success(calTeamMemberService.selectCalTeamMemberByMemberId(memberId));
    }

    /**
     * 新增班组成员
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:team:add')")
    @Log(title = "班组成员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CalTeamMember calTeamMember)
    {
        if(UserConstants.NOT_UNIQUE.equals(calTeamMemberService.checkUserUnique(calTeamMember))){
            return AjaxResult.error("用户"+calTeamMember.getNickName()+"已分配过班组！");
        }

        return toAjax(calTeamMemberService.insertCalTeamMember(calTeamMember));
    }

    /**
     * 删除班组成员
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:team:remove')")
    @Log(title = "班组成员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds)
    {
        return toAjax(calTeamMemberService.deleteCalTeamMemberByMemberIds(memberIds));
    }
}
