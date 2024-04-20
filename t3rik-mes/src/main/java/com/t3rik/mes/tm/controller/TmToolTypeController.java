package com.t3rik.mes.tm.controller;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.tm.domain.TmToolType;
import com.t3rik.mes.tm.service.ITmToolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工装夹具类型Controller
 * 
 * @author yinjinlu
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/mes/tm/tooltype")
public class TmToolTypeController extends BaseController
{
    @Autowired
    private ITmToolTypeService tmToolTypeService;

    /**
     * 查询工装夹具类型列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TmToolType tmToolType)
    {
        startPage();
        List<TmToolType> list = tmToolTypeService.selectTmToolTypeList(tmToolType);
        return getDataTable(list);
    }

    @GetMapping("/listAll")
    public AjaxResult listAll(){
        TmToolType tmToolType = new TmToolType();
        List<TmToolType> list = tmToolTypeService.selectTmToolTypeList(tmToolType);
        return AjaxResult.success(list);
    }


    /**
     * 导出工装夹具类型列表
     */
    @PreAuthorize("@ss.hasPermi('mes:tm:tooltype:export')")
    @Log(title = "工装夹具类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TmToolType tmToolType)
    {
        List<TmToolType> list = tmToolTypeService.selectTmToolTypeList(tmToolType);
        ExcelUtil<TmToolType> util = new ExcelUtil<TmToolType>(TmToolType.class);
        util.exportExcel(response, list, "工装夹具类型数据");
    }

    /**
     * 获取工装夹具类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:tm:tooltype:query')")
    @GetMapping(value = "/{toolTypeId}")
    public AjaxResult getInfo(@PathVariable("toolTypeId") Long toolTypeId)
    {
        return AjaxResult.success(tmToolTypeService.selectTmToolTypeByToolTypeId(toolTypeId));
    }

    /**
     * 新增工装夹具类型
     */
    @PreAuthorize("@ss.hasPermi('mes:tm:tooltype:add')")
    @Log(title = "工装夹具类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TmToolType tmToolType)
    {
        if(UserConstants.NOT_UNIQUE.equals(tmToolTypeService.checkToolTypeCodeUnique(tmToolType))){
            return AjaxResult.error("类型编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(tmToolTypeService.checkToolTypeNameUnique(tmToolType))){
            return AjaxResult.error("类型名称已存在！");
        }
        return toAjax(tmToolTypeService.insertTmToolType(tmToolType));
    }

    /**
     * 修改工装夹具类型
     */
    @PreAuthorize("@ss.hasPermi('mes:tm:tooltype:edit')")
    @Log(title = "工装夹具类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TmToolType tmToolType)
    {
        if(UserConstants.NOT_UNIQUE.equals(tmToolTypeService.checkToolTypeCodeUnique(tmToolType))){
            return AjaxResult.error("类型编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(tmToolTypeService.checkToolTypeNameUnique(tmToolType))){
            return AjaxResult.error("类型名称已存在！");
        }
        return toAjax(tmToolTypeService.updateTmToolType(tmToolType));
    }

    /**
     * 删除工装夹具类型
     */
    @PreAuthorize("@ss.hasPermi('mes:tm:tooltype:remove')")
    @Log(title = "工装夹具类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{toolTypeIds}")
    public AjaxResult remove(@PathVariable Long[] toolTypeIds)
    {
        return toAjax(tmToolTypeService.deleteTmToolTypeByToolTypeIds(toolTypeIds));
    }
}
