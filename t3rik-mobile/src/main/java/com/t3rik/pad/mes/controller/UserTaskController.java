package com.t3rik.pad.mes.controller;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.system.domain.UserTask;
import com.t3rik.system.service.IUserTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("用户的代办已办任务")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/usertask")
public class UserTaskController extends BaseController {

    @Autowired
    private IUserTaskService userTaskService;

    /**
     * 查询待处理的任务和单据
     * 1.所有自己起草的，草稿状态的单据（目前阶段只实现此条）
     * 2.根据具体的业务审批流程配置，需要自己审批的单据
     */
    @ApiOperation("查询待处理的任务和单据（分页）")
    @GetMapping("/listTodo")
    public TableDataInfo listTodoList(String userName)
    {
        startPage();
        List<UserTask> list = userTaskService.listTodoList(userName);
        return getDataTable(list);
    }

    /**
     * 查询已处理的任务和单据
     * 1.所有自己起草，非草稿状态的单据（目前阶段只实现此条）
     * 2.根据具体的业务审批流程配置，流经自己的单据
     */
    @ApiOperation("查询已处理的任务和单据（分页）")
    @GetMapping("/listFinished")
    public TableDataInfo listFinishedList(String userName)
    {
        startPage();
        List<UserTask> list = userTaskService.listFinishedList(userName);
        return getDataTable(list);
    }

}
