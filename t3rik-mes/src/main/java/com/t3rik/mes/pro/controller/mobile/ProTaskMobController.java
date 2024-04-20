package com.t3rik.mes.pro.controller.mobile;

import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.md.domain.MdWorkstation;
import com.t3rik.mes.md.service.IMdWorkstationService;
import com.t3rik.mes.pro.domain.ProFeedback;
import com.t3rik.mes.pro.domain.ProTask;
import com.t3rik.mes.pro.domain.ProTaskIssue;
import com.t3rik.mes.pro.service.IProFeedbackService;
import com.t3rik.mes.pro.service.IProTaskIssueService;
import com.t3rik.mes.pro.service.IProTaskService;
import com.t3rik.mes.wm.service.IWmIssueHeaderService;
import com.t3rik.mes.wm.service.IWmIssueLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/mobile/pro/protask")
public class ProTaskMobController extends BaseController {

    @Autowired
    private IProTaskService proTaskService;

    @Autowired
    private IProFeedbackService proFeedbackService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IProTaskIssueService proTaskIssueService;

    @Autowired
    private IWmIssueHeaderService wmIssueHeaderService;

    @Autowired
    private IWmIssueLineService wmIssueLineService;


    @GetMapping("/getlist")
    public AjaxResult getIssueList(ProTaskIssue proTaskIssue) {
        List<ProTaskIssue> list = proTaskIssueService.selectProTaskIssueList(proTaskIssue);
        return AjaxResult.success(list);
    }

    /**
     * 查询工作站的生产任务
     */
    @ApiOperation("查询状态未完成的生产任务接口")
    @GetMapping("/getTaskList")
    public TableDataInfo list(ProTask proTask)
    {
        List<ProTask> list = proTaskService.selectProTaskList(proTask);
        List<ProTask> l = list.stream().filter(t ->!"FINISHED".equals(t.getStatus())).collect(Collectors.toList());
        return getDataTable(l);
    }


    /**
     * 获取生产任务详细信息
     */
    @ApiOperation("查询生产任务详情接口")
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(proTaskService.selectProTaskByTaskId(taskId));
    }


    /**
     * 修改生产任务状态
     */
    @ApiOperation("修改生产任务状态接口")
    @Log(title = "生产任务", businessType = BusinessType.UPDATE)
    @PostMapping("/change")
    @ResponseBody
    public AjaxResult changeStatus(ProTask proTask)
    {
        return toAjax(proTaskService.updateProTask(proTask));
    }

    @Log(title = "生产报工", businessType = BusinessType.INSERT)
    @PostMapping("/feedback")
    @ResponseBody
    public AjaxResult feedBack( ProFeedback feedback){

        ProTask task = proTaskService.selectProTaskByTaskId(feedback.getTaskId());
        feedback.setTaskCode(task.getTaskCode());
        feedback.setWorkorderId(task.getWorkorderId());
        feedback.setWorkorderCode(task.getWorkorderCode());
        feedback.setWorkorderName(task.getWorkorderName());
        feedback.setQuantity(task.getQuantity());
        feedback.setFeedbackTime(new Date());

        if(feedback.getWorkstationId() == null){
            feedback.setWorkstationId(task.getWorkstationId());
        }

        MdWorkstation workstation = mdWorkstationService.selectMdWorkstationByWorkstationId(feedback.getWorkstationId());
        feedback.setWorkstationCode(workstation.getWorkstationCode());
        feedback.setWorkstationName(workstation.getWorkstationName());

        task.setQuantityProduced(task.getQuantityProduced().add(feedback.getQuantityFeedback()));
        task.setQuantityQuanlify(task.getQuantityQuanlify().add(feedback.getQuantityQualified()));
        task.setQuantityUnquanlify(task.getQuantityUnquanlify().add(feedback.getQuantityUnquanlified()));
        proTaskService.updateProTask(task);
        return toAjax(proFeedbackService.insertProFeedback(feedback));
    }

}
