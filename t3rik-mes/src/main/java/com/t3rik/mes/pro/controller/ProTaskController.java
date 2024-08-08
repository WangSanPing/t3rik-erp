package com.t3rik.mes.pro.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.core.redis.RedisCache;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.*;
import com.t3rik.mes.pro.service.*;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 生产任务Controller
 *
 * @author yinjinlu
 * @date 2022-05-14
 */
@RestController
@RequestMapping("/mes/pro/protask")
public class ProTaskController extends BaseController {
    @Resource
    private IProTaskService proTaskService;

    @Resource
    private IProWorkorderService proWorkorderService;

    @Resource
    private IProRouteProductService proRouteProductService;

    @Resource
    private IProProcessService proProcessService;

    @Resource
    private IProRouteService proRouteService;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private RedisCache redisCache;

    /**
     * 查询生产任务列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProTask proTask) {
        startPage();
        List<ProTask> list = proTaskService.selectProTaskList(proTask);
        return getDataTable(list);
    }

    /**
     * 导出生产任务列表
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:export')")
    @Log(title = "生产任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProTask proTask) {
        List<ProTask> list = proTaskService.selectProTaskList(proTask);
        ExcelUtil<ProTask> util = new ExcelUtil<ProTask>(ProTask.class);
        util.exportExcel(response, list, "生产任务数据");
    }

    /**
     * 获取生产任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId) {
        return AjaxResult.success(proTaskService.selectProTaskByTaskId(taskId));
    }

    /**
     * 获取甘特图中需要显示的TASK，包括三种类型的内容：
     * 1.Project：基于时间范围搜索的生产工单转换而来的Project。
     * 搜索逻辑为：默认使用当前日期作为开始时间，搜索所有需求时间大于当前时间的生产工单
     * 2.Task：基于生产工单拆分到具体工作站后的生产任务转换而来的Task。
     * 3.Link：根据工序与工序之间的依赖关系转换而来的Link。
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:list')")
    @GetMapping("/listGanttTaskList")
    public AjaxResult getGanttTaskList(ProWorkorder proWorkorder) {
        GanttTask ganttTask = new GanttTask();
        List<GanttData> ganttData = new ArrayList<GanttData>();
        List<GanttLink> ganttLinks = new ArrayList<GanttLink>();

        // 查询所有的WorkOrder
        List<ProWorkorder> workorders = proWorkorderService.selectProWorkorderList(proWorkorder);

        // 为每个workOrder生成type=project的GanttData
        // 为每个proTask生产type=task的GanttData
        ProTask param = new ProTask();
        if (CollUtil.isNotEmpty(workorders)) {
            for (ProWorkorder workorder : workorders
            ) {
                // 先添加当前的生产工单TASK
                GanttData wdata = new GanttData();
                wdata.setId("MO" + workorder.getWorkorderId().toString());
                wdata.setText(new StringBuilder().append(workorder.getProductName()).append(workorder.getQuantity().stripTrailingZeros().toPlainString()).append(workorder.getUnitOfMeasure()).toString());// 默认使用“[产品]+[数量]+[单位]”格式。
                wdata.setProduct(workorder.getProductName());
                wdata.setQuantity(workorder.getQuantity());
                if (workorder.getParentId().longValue() != 0L) {
                    wdata.setParent("MO" + workorder.getParentId().toString());
                }
                BigDecimal produced = workorder.getQuantityProduced();
                BigDecimal quantitiy = workorder.getQuantity();
                wdata.setProgress(produced.divide(quantitiy, BigDecimal.ROUND_HALF_UP).floatValue());
                wdata.setDuration(0L);
                wdata.setType(UserConstants.GANTT_TASK_TYPE_PROJECT);
                ganttData.add(wdata);

                // 查询当前生产工单下所有的生产任务
                param.setWorkorderId(workorder.getWorkorderId());
                List<ProTask> proTasks = proTaskService.selectProTaskList(param);
                if (CollUtil.isNotEmpty(proTasks)) {
                    for (ProTask task : proTasks
                    ) {
                        GanttData data = new GanttData();
                        data.setId(task.getTaskId().toString());// 使用生产任务的ID作为甘特图TASK的ID
                        data.setText(new StringBuilder().append(task.getItemName()).append(task.getQuantity().stripTrailingZeros().toPlainString()).append(task.getUnitOfMeasure()).toString()); // 默认使用“[产品]+[数量]+[单位]”格式。
                        data.setColor(task.getColorCode());
                        data.setDuration(task.getDuration());
                        data.setStart_date(task.getStartTime());
                        data.setParent("MO" + workorder.getWorkorderId().toString());// 这里要设置为"MO+生产工单ID"的格式
                        data.setProduct(task.getItemName());
                        data.setQuantity(task.getQuantity());
                        data.setProcess(task.getProcessName());
                        data.setWorkstation(task.getWorkstationName());
                        BigDecimal taskproduced = task.getQuantityProduced();
                        BigDecimal taskquantitiy = task.getQuantity();
                        data.setProgress(taskproduced.divide(taskquantitiy, BigDecimal.ROUND_HALF_UP).floatValue());
                        data.setType(UserConstants.GANTT_TASK_TYPE_TASK);
                        ganttData.add(data);
                    }
                }
            }
        }

        ganttTask.setData(ganttData);
        ganttTask.setLinks(ganttLinks);
        return AjaxResult.success(ganttTask);
    }


    /**
     * 按照最新的模式只展示工序级别的生产进度
     *
     * @return
     */
    @GetMapping("/listTaskListByWorkorder")
    public AjaxResult getWorkorderProcessTypeTaskList(ProWorkorder proWorkorder) {
        if (!StringUtils.isNotNull(proWorkorder.getWorkorderId())) {
            return AjaxResult.error("请选择具体的生产工单!");
        }

        ProWorkorder workorder = proWorkorderService.selectProWorkorderByWorkorderId(proWorkorder.getWorkorderId());
        if (StringUtils.isNotNull(workorder)) {
            // 检查当前的产品是否配置了对应的工艺路线
            ProRouteProduct param = new ProRouteProduct();
            param.setItemId(workorder.getProductId());
            List<ProRouteProduct> routes = proRouteProductService.selectProRouteProductList(param);
            if (CollectionUtils.isEmpty(routes)) {
                return AjaxResult.error("当前工单生产的产品，未配置对应的生产工艺流程！");
            }
        }

        // 根据生产工单查询每个工序的生产情况
        List<ProTask> tasks = proTaskService.selectProTaskProcessViewByWorkorder(proWorkorder.getWorkorderId());
        return AjaxResult.success(tasks);
    }


    /**
     * 新增生产任务
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:add')")
    @Log(title = "生产任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProTask proTask) {
        ProWorkorder order = new ProWorkorder();
        ProRoute route = new ProRoute();
        ProProcess process = new ProProcess();
        AjaxResult ajaxResult = this.checkTask(proTask, order, route, process);
        if (ajaxResult != null) {
            return ajaxResult;
        }
        buildProTaskForInsert(proTask, order, route, process);
        return toAjax(proTaskService.save(proTask));
    }

    /**
     * 修改生产任务
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:edit')")
    @Log(title = "生产任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProTask proTask) {
        ProWorkorder order = new ProWorkorder();
        ProRoute route = new ProRoute();
        ProProcess process = new ProProcess();
        AjaxResult ajaxResult = this.checkTask(proTask, order, route, process);
        if (ajaxResult != null) {
            return ajaxResult;
        }
        // 要更新的数据
        ProTask newTask = new ProTask();
        // 数据清洗
        buildProTaskForUpdate(proTask, newTask);
        return toAjax(proTaskService.updateProTask(newTask));
    }

    /**
     * 删除生产任务
     */
    @PreAuthorize("@ss.hasPermi('mes:pro:protask:remove')")
    @Log(title = "生产任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds) {
        return toAjax(proTaskService.deleteProTaskByTaskIds(taskIds));
    }


    /**
     * 数据检查是否合规
     */
    private AjaxResult checkTask(ProTask proTask, ProWorkorder order, ProRoute route, ProProcess process) {

        if (proTask.getQuantity().compareTo(BigDecimal.ZERO) < 1) {
            return AjaxResult.error("排产数量必须大于0！");
        }
        if (StringUtils.isNull(proTask.getWorkstationId())) {
            return AjaxResult.error("请选择工作站！");
        }
        if (proTask.getDuration() <= 0) {
            return AjaxResult.error("生产时长必须大于0！");
        }
        // 生产工单
        ProWorkorder dbOrder = this.proWorkorderService.getById(proTask.getWorkorderId());
        if (dbOrder == null) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR);
        }
        // 工艺信息
        ProRoute dbRoute = proRouteService.selectProRouteByRouteId(proTask.getRouteId());
        if (dbRoute == null) {
            return AjaxResult.error("当前生产任务对应的工艺路线信息无效！" + proTask.getRouteId());
        }
        // 工序信息
        ProProcess dbProcess = proProcessService.selectProProcessByProcessId(proTask.getProcessId());
        if (dbProcess == null) {
            return AjaxResult.error("当前生产任务对应的工序信息无效！" + proTask.getProcessId());
        }
        BeanUtil.copyProperties(dbOrder, order);
        BeanUtil.copyProperties(dbRoute, route);
        BeanUtil.copyProperties(dbProcess, process);
        return null;
    }

    /**
     * 构建-更新-生产任务实体
     */
    private void buildProTaskForUpdate(ProTask proTask, ProTask newTask) {
        newTask.setTaskId(proTask.getTaskId());
        newTask.setWorkstationId(proTask.getWorkstationId());
        newTask.setWorkstationCode(proTask.getWorkstationCode());
        newTask.setWorkstationName(proTask.getWorkstationName());
        newTask.setStartTime(proTask.getStartTime());
        newTask.setQuantity(proTask.getQuantity());
        newTask.setDuration(proTask.getDuration());
        newTask.setEndTime(proTask.getEndTime());
        newTask.setColorCode(proTask.getColorCode());
    }

    /**
     * 构建-新增-生产任务实体
     */
    private void buildProTaskForInsert(ProTask proTask, ProWorkorder order, ProRoute route, ProProcess process) {
        proTask.setWorkorderCode(order.getWorkorderCode());
        proTask.setWorkorderName(order.getWorkorderName());
        proTask.setClientOrderCode(order.getClientOrderCode());
        proTask.setItemId(order.getProductId());
        proTask.setItemCode(order.getProductCode());
        proTask.setItemName(order.getProductName());
        proTask.setSpecification(order.getProductSpc());
        proTask.setUnitOfMeasure(order.getUnitOfMeasure());
        proTask.setClientId(order.getClientId());
        proTask.setClientCode(order.getClientCode());
        proTask.setClientName(order.getClientName());
        // 工艺信息
        proTask.setRouteCode(route.getRouteCode());
        // 工序信息
        proTask.setProcessId(process.getProcessId());
        proTask.setProcessCode(process.getProcessCode());
        proTask.setProcessName(process.getProcessName());
        // 自动生成任务编号和名称
        proTask.setTaskCode(autoCodeUtil.genSerialCode(UserConstants.TASK_CODE, null));
        Object cacheUnitMeasure = this.redisCache.getCacheObject(proTask.getUnitOfMeasure());
        String unitMeasureName = cacheUnitMeasure == null ? proTask.getUnitOfMeasure() : (String) cacheUnitMeasure;
        proTask.setTaskName(proTask.getItemName() + "【" + proTask.getQuantity().toString() + "】" + unitMeasureName);
        proTask.setStatus(OrderStatusEnum.PREPARE.getCode());
    }

}
