CREATE TABLE `wm_material_stock_log` (
                                         `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                                         `material_stock_id` bigint NOT NULL COMMENT '关联库存ID',
                                         `item_id` bigint NOT NULL COMMENT '物料ID',
                                         `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
                                         `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
                                         `change_type` TINYINT NOT NULL COMMENT '变化类型（1:入库, 2:出库, 3:盘点等）',
                                         `before_quantity` double(12,2) DEFAULT NULL COMMENT '变化前库存数量',
                                         `after_quantity` double(12,2) DEFAULT NULL COMMENT '变化后库存数量',
                                         `change_quantity` double(12,2) DEFAULT NULL COMMENT '库存变化数量',
                                         `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
                                         `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
                                         `task_id` bigint DEFAULT NULL COMMENT '任务ID',
                                         `task_code` varchar(64) DEFAULT NULL COMMENT '任务编号',
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
                                         PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存变化日志表';

CREATE TABLE `wm_material_stock_log_failure` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存日志写入失败表';


ALTER TABLE wm_material_stock_log
    ADD INDEX idx_item_id (item_id),
    ADD INDEX idx_change_type (change_type),
    ADD INDEX idx_operation_time (operation_time);