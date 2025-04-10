package com.t3rik.mes.dv.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.dv.domain.DvCheckSubject;
import com.t3rik.mes.dv.service.IDvCheckSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 点检项目Controller
 * 
 * @author yinjinlu
 * @date 2022-06-18
 */
@RestController
@RequestMapping("/mes/dv/checksubject")
public class DvCheckSubjectController extends BaseController
{
    @Autowired
    private IDvCheckSubjectService dvCheckSubjectService;

    /**
     * 查询点检项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DvCheckSubject dvCheckSubject)
    {
        startPage();
        List<DvCheckSubject> list = dvCheckSubjectService.selectDvCheckSubjectList(dvCheckSubject);
        return getDataTable(list);
    }

    /**
     * 导出点检项目列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:export')")
    @Log(title = "点检项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvCheckSubject dvCheckSubject)
    {
        List<DvCheckSubject> list = dvCheckSubjectService.selectDvCheckSubjectList(dvCheckSubject);
        ExcelUtil<DvCheckSubject> util = new ExcelUtil<DvCheckSubject>(DvCheckSubject.class);
        util.exportExcel(response, list, "点检项目数据");
    }

    /**
     * 获取点检项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(dvCheckSubjectService.selectDvCheckSubjectByRecordId(recordId));
    }

    /**
     * 新增点检项目
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:add')")
    @Log(title = "点检项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvCheckSubject dvCheckSubject)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvCheckSubjectService.checkSubjectUnique(dvCheckSubject))){
            return AjaxResult.error("点检项目已经添加过！");
        }

        return toAjax(dvCheckSubjectService.insertDvCheckSubject(dvCheckSubject));
    }

    /**
     * 修改点检项目
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:edit')")
    @Log(title = "点检项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvCheckSubject dvCheckSubject)
    {
        if(UserConstants.NOT_UNIQUE.equals(dvCheckSubjectService.checkSubjectUnique(dvCheckSubject))){
            return AjaxResult.error("点检项目已经添加过！");
        }
        return toAjax(dvCheckSubjectService.updateDvCheckSubject(dvCheckSubject));
    }

    /**
     * 删除点检项目
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:checkplan:remove')")
    @Log(title = "点检项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(dvCheckSubjectService.deleteDvCheckSubjectByRecordIds(recordIds));
    }
}
