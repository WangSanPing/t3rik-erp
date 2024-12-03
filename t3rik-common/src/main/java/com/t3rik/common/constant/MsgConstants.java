package com.t3rik.common.constant;

import java.text.MessageFormat;

/**
 * 消息通用常量
 *
 * @author t3rik
 * @date 2024/5/11 21:33
 */
public class MsgConstants {

    public static final String PARAM_ERROR = "参数有误,请联系管理员";

    public static final String SUCCESS = "操作成功!";

    public static final String CAN_NOT_BE_DELETE = "预置数据，不允许删除!";

    public static final String CAN_NOT_BE_ZERO = "数量不许为0!";

    public static final String SELECT_AT_LEAST_ONE = "请至少选择一项!";

    public static final String NOT_EXIST_WORKSTATION = "当前生产任务对应的工作站不存在!";

    public static final String ERROR_ROUTE = "当前任务对应的工艺和工序配置无效!";

    public static final String NO_OPERATION_AUTH = "当前用户没有操作该任务的权限!";

    public static final String CODE_ALREADY_EXISTS = "编号已存在!";

    public static final String SELECT_AT_ADD_ONE = "请至少添加一种物料!";

    public static final String ERROR_STATUS = "当前数据状态不允许执行此操作!";

    public static final String ERROR_DATA = "获取数据失败!";

    public static final String NOT_ALLOW_OPT_ADMIN = "不允许操作系统管理员";

    public static String CAN_ONLY_BE_DELETED_BY_PARAM(String status) {
        return MessageFormat.format("只能删除状态为: {0} 的单据", status);
    }
}
