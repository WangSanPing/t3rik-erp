drop table if exists print_client;
-- 打印客户端
CREATE TABLE IF NOT EXISTS `print_client` (
                                              `client_id` bigint NOT NULL AUTO_INCREMENT COMMENT '打印机客户端ID',
                                              `client_code` varchar(50) NOT NULL COMMENT '打印机客户端编码',
    `client_name` varchar(100) NOT NULL COMMENT '打印机客户端名称',
    `client_ip` varchar(45) DEFAULT NULL COMMENT '打印客户端IP',
    `client_port` bigint DEFAULT NULL COMMENT '打印客户端端口',
    `client_token` varchar(255) DEFAULT NULL COMMENT '打印客户端token',
    `status` varchar(50) DEFAULT NULL COMMENT '客户端状态',
    `workshop_id` bigint DEFAULT NULL COMMENT '所属车间ID',
    `workshop_code` varchar(50) DEFAULT NULL COMMENT '所属车间编码',
    `workshop_name` varchar(100) DEFAULT NULL COMMENT '所属车间名称',
    `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
    `workstation_code` varchar(50) DEFAULT NULL COMMENT '工作站编码',
    `workstation_name` varchar(100) DEFAULT NULL COMMENT '工作站名称',
    `attr1` varchar(255) DEFAULT NULL COMMENT '预留字段1',
    `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
    `attr3` bigint DEFAULT NULL COMMENT '预留字段3',
    `attr4` bigint DEFAULT NULL COMMENT '预留字段4',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` text COMMENT '备注',
    PRIMARY KEY (`client_id`),
    UNIQUE KEY `unique_client_code` (`client_code`) COMMENT '确保客户端编码唯一'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打印客户端表';

-- 打印机配置
drop table if exists print_printer_config;
CREATE TABLE `print_printer_config` (
                                        `printer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '打印机ID',
                                        `printer_type` varchar(64) DEFAULT 'LABEL' COMMENT '打印机类型',
                                        `printer_name` varchar(255) NOT NULL COMMENT '打印机名称',
                                        `brand` varchar(64) DEFAULT NULL COMMENT '品牌',
                                        `printer_model` varchar(64) DEFAULT NULL COMMENT '型号',
                                        `connection_type` varchar(64) DEFAULT NULL COMMENT '连接类型',
                                        `printer_url` varchar(255) DEFAULT NULL COMMENT '图片URL',
                                        `printer_ip` varchar(64) DEFAULT NULL COMMENT '打印机IP',
                                        `printer_port` int DEFAULT NULL COMMENT '打印机端口',
                                        `client_sid` varchar(32) DEFAULT NULL COMMENT '打印客户端SID',
                                        `client_ip` varchar(64) DEFAULT NULL COMMENT '打印客户端IP',
                                        `client_port` int DEFAULT NULL COMMENT '打印客户端端口',
                                        `enable_flag` char(1) DEFAULT 'Y' COMMENT '启用状态',
                                        `status` varchar(64) DEFAULT 'READY' COMMENT '打印机状态',
                                        `remark` varchar(500) DEFAULT '' COMMENT '备注',
                                        `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
                                        `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
                                        `attr3` int DEFAULT '0' COMMENT '预留字段3',
                                        `attr4` int DEFAULT '0' COMMENT '预留字段4',
                                        `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                        `deleted` int DEFAULT '0' COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                        `deleteAt` datetime DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                        `version` int DEFAULT '1' COMMENT '乐观锁',
                                        PRIMARY KEY (`printer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打印机配置';

drop table if exists print_template;
-- 打印模板配置
CREATE TABLE `print_template` (
                                  `template_id` bigint NOT NULL AUTO_INCREMENT COMMENT '模板ID',
                                  `template_code` varchar(64) NOT NULL COMMENT '模板编号',
                                  `template_name` varchar(255) DEFAULT NULL COMMENT '模板名称',
                                  `template_type` varchar(64) NOT NULL COMMENT '模板类型',
                                  `template_json` json DEFAULT NULL COMMENT '模板内容',
                                  `is_default` char(1) DEFAULT 'Y' COMMENT '是否默认',
                                  `enable_flag` char(1) DEFAULT 'Y' COMMENT '启用状态',
                                  `remark` varchar(500) DEFAULT '' COMMENT '备注',
                                  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
                                  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
                                  `attr3` int DEFAULT '0' COMMENT '预留字段3',
                                  `attr4` int DEFAULT '0' COMMENT '预留字段4',
                                  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `deleted` int DEFAULT '0' COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                  `deleteAt` datetime DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                  `version` int DEFAULT '1' COMMENT '乐观锁',
                                  `paper_type` varchar(64) DEFAULT NULL COMMENT '纸张类型',
                                  `template_width` int DEFAULT NULL COMMENT '模板宽度',
                                  `template_height` int DEFAULT NULL COMMENT '模板高度',
                                  `template_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '缩略图url',
                                  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打印模板配置';

-- 打印模版菜单增加


INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3000, '打印管理', 0, 12, 'print', null, null, 1, 0, 'M', '1', '0', '0', '', 'device', 'admin', null, '2025-04-09 16:19:40', 'admin', null, '2025-04-10 16:44:57', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3001, '打印机配置', 3000, 1, 'printerconfig', 'print/printerconfig/index', null, 1, 0, 'C', '1', '0', '0', '', 'system', 'admin', null, '2025-04-09 16:20:13', 'admin', null, '2025-04-09 19:05:51', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3002, '打印模板', 3000, 2, 'printtemplate', 'print/printtemplate/list', null, 1, 0, 'C', '1', '0', '0', '', 'documentation', 'admin', null, '2025-04-10 16:23:26', 'admin', null, '2025-04-10 16:40:47', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3003, '模版编辑', 3002, 1, '', null, null, 1, 0, 'F', '1', '0', '0', 'print:template:edit', '#', 'admin', null, '2025-04-10 16:24:14', 'admin', null, '2025-04-10 16:41:06', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3004, '模版新增', 3002, 2, '', null, null, 1, 0, 'F', '1', '0', '0', 'print:template:add', '#', 'admin', null, '2025-04-10 16:24:37', '', null, null, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3005, '模版删除', 3002, 3, '', null, null, 1, 0, 'F', '1', '0', '0', 'print:template:remove', '#', 'admin', null, '2025-04-10 16:24:57', '', null, null, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3006, '模版列表', 3002, 4, '', null, null, 1, 0, 'F', '1', '0', '0', 'print:template:list', '#', 'admin', null, '2025-04-10 16:25:19', '', null, null, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, module_type, visible, status, perms, icon, create_by, create_user_id, create_time, update_by, update_user_id, update_time, remark, deleted, deleteAt, version) VALUES (3007, '模版详情查看', 3002, 5, '', null, null, 1, 0, 'F', '1', '0', '0', 'print:template:query', '#', 'admin', null, '2025-04-10 16:25:44', '', null, null, '', 0, '1000-01-01 00:00:00', 1);


-- 打印模版 编码规则

INSERT INTO  sys_auto_code_rule (rule_id, rule_code, rule_name, rule_desc, max_length, is_padded, padded_char, padded_method, enable_flag, remark, attr1, attr2, attr3, attr4, create_by, create_time, update_by, update_time, deleted, deleteAt, version) VALUES (258, 'PRINT_TEMPLATE_CODE', '打印模板编号生成规则', null, 4, 'N', null, 'L', 'Y', null, null, null, 0, 0, 'admin', '2025-04-09 17:27:27', 'admin', '2025-04-09 17:36:16', 0, '1000-01-01 00:00:00', 1);
-- 编码组成规则
INSERT INTO  sys_auto_code_part (part_id, rule_id, part_index, part_type, part_code, part_name, part_length, date_format, input_character, fix_character, seria_start_no, seria_step, seria_now_no, cycle_flag, cycle_method, remark, attr1, attr2, attr3, attr4, create_by, create_time, update_by, update_time, deleted, deleteAt, version) VALUES (351, 258, 1, 'FIXCHAR', 'PRFIX', '前缀', 1, null, null, 'T', null, null, null, null, null, null, null, null, 0, 0, 'admin', '2025-04-09 17:28:15', '', null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_auto_code_part (part_id, rule_id, part_index, part_type, part_code, part_name, part_length, date_format, input_character, fix_character, seria_start_no, seria_step, seria_now_no, cycle_flag, cycle_method, remark, attr1, attr2, attr3, attr4, create_by, create_time, update_by, update_time, deleted, deleteAt, version) VALUES (352, 258, 2, 'SERIALNO', 'SERIAL', '流水号', 3, null, null, null, 1, 1, null, 'N', null, null, null, null, 0, 0, 'admin', '2025-04-09 17:28:49', '', null, 0, '1000-01-01 00:00:00', 1);

-- 字典表
INSERT INTO  sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (1909899532599926786, '模版类型', 'print_template_type', '0', 'admin', '2025-04-09 17:21:47', 'admin', '2025-04-09 17:21:47', null, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (1909899994598318081, '纸张类型', 'print_paper_type', '0', 'admin', '2025-04-09 17:23:38', 'admin', '2025-04-09 17:23:38', null, 1, 1, 0, '1000-01-01 00:00:00', 1);
-- 字典表数据

INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (500, 1, '生产工单', 'work_order', 'print_template_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:22:29', 'admin', '2025-04-09 17:23:15', null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (501, 2, '物料', 'item', 'print_template_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:22:47', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (502, 3, 'SN码', 'sn', 'print_template_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:23:04', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);

INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (503, 1, 'A4', 'A4', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:23:51', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (504, 2, 'A3', 'A3', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:24:00', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (505, 3, '30mmX20mm', '32', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:24:22', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (506, 4, '40mmX50mm', '45', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:24:36', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (507, 5, '40mmX60mm', '46', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:24:52', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (508, 6, '80mmX40mm', '84', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:25:06', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (509, 7, '80mmX50mm', '85', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:25:18', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (510, 8, '90mmX60mm', '96', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:25:33', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
INSERT INTO  sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark, create_user_id, update_user_id, deleted, deleteAt, version) VALUES (511, 9, '100mmX50mm', '105', 'print_paper_type', null, 'default', 'N', '0', 'admin', '2025-04-09 17:25:47', '', null, null, null, null, 0, '1000-01-01 00:00:00', 1);
