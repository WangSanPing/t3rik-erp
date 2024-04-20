package com.t3rik.mes.pro.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProProcessContent;
import com.t3rik.mes.pro.service.IProProcessContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生产工序内容Controller
 * 
 * @author yinjinlu
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/mes/pro/processcontent")
public class ProProcessContentController extends BaseController
{
    @Autowired
    private IProProcessContentService proProcessContentService;

    /**
     * 查询生产工序内容列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProProcessContent proProcessContent)
    {
        startPage();
        List<ProProcessContent> list = proProcessContentService.selectProProcessContentList(proProcessContent);
        return getDataTable(list);
    }

    /**
     * 导出生产工序内容列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:export')")
    @Log(title = "生产工序内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProProcessContent proProcessContent)
    {
        List<ProProcessContent> list = proProcessContentService.selectProProcessContentList(proProcessContent);
        ExcelUtil<ProProcessContent> util = new ExcelUtil<ProProcessContent>(ProProcessContent.class);
        util.exportExcel(response, list, "生产工序内容数据");
    }

    /**
     * 获取生产工序内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:query')")
    @GetMapping(value = "/{contentId}")
    public AjaxResult getInfo(@PathVariable("contentId") Long contentId)
    {
        return AjaxResult.success(proProcessContentService.selectProProcessContentByContentId(contentId));
    }

    /**
     * 新增生产工序内容
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:add')")
    @Log(title = "生产工序内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProProcessContent proProcessContent)
    {
        return toAjax(proProcessContentService.insertProProcessContent(proProcessContent));
    }

    /**
     * 修改生产工序内容
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:edit')")
    @Log(title = "生产工序内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProProcessContent proProcessContent)
    {
        return toAjax(proProcessContentService.updateProProcessContent(proProcessContent));
    }

    /**
     * 删除生产工序内容
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:process:remove')")
    @Log(title = "生产工序内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds)
    {
        return toAjax(proProcessContentService.deleteProProcessContentByContentIds(contentIds));
    }
}
