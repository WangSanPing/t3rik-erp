drop table if exists wm_material_stock_log;
CREATE TABLE `wm_material_stock_log` (
                                         `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                                         `material_stock_id` bigint NOT NULL COMMENT '关联库存ID',
                                         `item_id` bigint NOT NULL COMMENT '物料ID',
                                         `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
                                         `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
                                         `change_type` tinyint NOT NULL COMMENT '变化类型（10:入库, 20:出库, 30:盘点等）',
                                         `before_quantity` double(12,2) DEFAULT NULL COMMENT '变化前库存数量',
                                         `after_quantity` double(12,2) DEFAULT NULL COMMENT '变化后库存数量',
                                         `change_quantity` double(12,2) DEFAULT NULL COMMENT '库存变化数量',
                                         `source_doc_id` bigint DEFAULT NULL COMMENT '被消耗单据ID',
                                         `source_doc_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被消耗单据编号',
                                         `source_doc_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被消耗单据名称',
                                         `source_doc_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被消耗单据类型',
                                         `source_line_id` bigint DEFAULT NULL COMMENT '被消耗单据行ID',
                                         `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
                                         `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库编码',
                                         `warehouse_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库名称',
                                         `location_id` bigint DEFAULT NULL COMMENT '库区ID',
                                         `location_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区编码',
                                         `location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区名称',
                                         `area_id` bigint DEFAULT NULL COMMENT '库位ID',
                                         `area_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位编码',
                                         `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位名称',
                                         `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
                                         `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
                                         `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
                                         `operation_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
                                         `operation_by` varchar(64) DEFAULT '' COMMENT '操作人姓名',
                                         `operation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                         `description` varchar(500) DEFAULT NULL COMMENT '变化描述',
                                         `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
                                         `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
                                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
                                         `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
                                         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                         `deleted` int DEFAULT '0' COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                         `deleteAt` datetime DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                         `version` int DEFAULT '1' COMMENT '乐观锁',
                                         PRIMARY KEY (`log_id`),
                                         KEY `idx_item_id` (`item_id`),
                                         KEY `idx_workorder_id` (`workorder_id`),
                                         KEY `idx_change_type` (`change_type`),
                                         KEY `idx_operation_time` (`operation_time`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存变化日志表';

drop table if exists wm_log_failure;
CREATE TABLE `wm_log_failure` (
                                  `failure_id` bigint NOT NULL AUTO_INCREMENT COMMENT '失败记录ID',
                                  `log_id` bigint DEFAULT NULL COMMENT '原始日志ID（若有）',
                                  `material_stock_id` bigint NOT NULL COMMENT '关联库存ID',
                                  `failure_reason` varchar(500) DEFAULT NULL COMMENT '日志写入失败原因',
                                  `failure_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '失败时间',
                                  `log_data` text COMMENT '失败时的原始日志数据',
                                  `create_user_id` bigint DEFAULT NULL COMMENT '创建人ID',
                                  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_user_id` bigint DEFAULT NULL COMMENT '更新人ID',
                                  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                  `deleted` int DEFAULT '0' COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                  `deleteAt` datetime DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                  `version` int DEFAULT '1' COMMENT '乐观锁',
                                  PRIMARY KEY (`failure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志写入失败记录表';

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2461, '日志写入失败记录', 2473, 2, 'wmLogFailure', 'mes/wm/wm-log-failure/index', NULL, 1, 0, 'C', '1', '0', '0', 'wm:wmlogfailure:list', '#', 'admin', NULL, '2025-04-02 14:49:34', 'admin', NULL, '2025-04-02 22:44:32', '日志写入失败记录菜单', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2462, '日志写入失败记录查询', 2461, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmlogfailure:query', '#', 'admin', NULL, '2025-04-02 14:49:34', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2463, '日志写入失败记录新增', 2461, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmlogfailure:add', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2464, '日志写入失败记录修改', 2461, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmlogfailure:edit', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2465, '日志写入失败记录删除', 2461, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmlogfailure:remove', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2466, '日志写入失败记录导出', 2461, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmlogfailure:export', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2467, '库存变化日志', 2473, 1, 'wmMaterialStockLog', 'mes/wm/wm-material-stock-log/index', NULL, 1, 0, 'C', '1', '0', '0', 'wm:wmmaterialstocklog:list', '#', 'admin', NULL, '2025-04-02 14:49:35', 'admin', NULL, '2025-04-02 22:44:25', '库存变化日志菜单', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2468, '库存变化日志查询', 2467, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmmaterialstocklog:query', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2469, '库存变化日志新增', 2467, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmmaterialstocklog:add', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2470, '库存变化日志修改', 2467, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmmaterialstocklog:edit', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2471, '库存变化日志删除', 2467, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmmaterialstocklog:remove', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2472, '库存变化日志导出', 2467, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'wm:wmmaterialstocklog:export', '#', 'admin', NULL, '2025-04-02 14:49:35', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, `path`, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES(2473, '库存信息', 2398, 6, '/log', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'log', 'admin', NULL, '2025-04-02 22:42:49', 'admin', NULL, '2025-04-02 22:44:03', '', 0, '1000-01-01 00:00:00', 1);
