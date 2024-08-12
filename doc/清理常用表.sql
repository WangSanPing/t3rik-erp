SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE sys_logininfor;
TRUNCATE TABLE wm_transaction;
TRUNCATE TABLE pro_task;
TRUNCATE TABLE pro_feedback;
TRUNCATE TABLE wm_material_stock;
TRUNCATE TABLE pro_client_order_item;
TRUNCATE TABLE pro_workorder_bom;
TRUNCATE TABLE wm_item_consume;
TRUNCATE TABLE wm_rt_issue;
TRUNCATE TABLE wm_waste_header;
TRUNCATE TABLE md_item;
TRUNCATE TABLE md_product_bom;
TRUNCATE TABLE pro_route_process;
TRUNCATE TABLE wm_rt_issue_line;
TRUNCATE TABLE wm_storage_area;
TRUNCATE TABLE wm_waste_line;
TRUNCATE TABLE pro_client_order;
TRUNCATE TABLE pro_workorder;
TRUNCATE TABLE wm_issue_line;
TRUNCATE TABLE md_workstation;
TRUNCATE TABLE pro_process;
TRUNCATE TABLE pro_route_product;
TRUNCATE TABLE wm_issue_header;
TRUNCATE TABLE wm_product_recpt;
TRUNCATE TABLE wm_storage_location;
TRUNCATE TABLE wm_item_recpt_line;
TRUNCATE TABLE wm_warehouse;
TRUNCATE TABLE pro_route;
TRUNCATE TABLE wm_item_recpt;
TRUNCATE TABLE wm_product_produce;
TRUNCATE TABLE wm_product_produce_line;
TRUNCATE TABLE wm_product_recpt_line;
TRUNCATE TABLE md_client;
TRUNCATE TABLE md_vendor;
TRUNCATE TABLE md_workshop;
TRUNCATE TABLE md_workstation_machine;
TRUNCATE TABLE wm_product_salse;
TRUNCATE TABLE wm_product_salse_line;
truncate table pro_client_order_item;

-- 工序
INSERT INTO pro_process (`process_id`, `process_code`, `process_name`, `attention`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'PROCESS006', '印刷', NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:34:10', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_process (`process_id`, `process_code`, `process_name`, `attention`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (2, 'PROCESS007', '复合', NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:34:30', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_process (`process_id`, `process_code`, `process_name`, `attention`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (3, 'PROCESS008', '分切', NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:34:53', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_process (`process_id`, `process_code`, `process_name`, `attention`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (4, 'PROCESS009', '制袋', NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:34:59', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);

-- 工艺
INSERT INTO pro_route (`route_id`, `route_code`, `route_name`, `route_desc`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'R1003', '印刷工艺', NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:35:10', '', '2024-08-05 16:46:18', 0, '1000-01-01 00:00:00', 1, NULL, NULL);

-- 工艺工序关联表
INSERT INTO pro_route_process (`record_id`, `route_id`, `process_id`, `process_code`, `process_name`, `order_num`, `next_process_id`, `next_process_code`, `next_process_name`, `link_type`, `default_pre_time`, `default_suf_time`, `color_code`, `key_flag`, `is_check`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 1, 1, 'PROCESS006', '印刷', 1, 2, 'PROCESS007', '复合', 'SS', 0, 0, '#00AEF3', 'N', 'N', '', NULL, NULL, 0, 0, '', '2024-06-29 15:35:47', '', '2024-06-29 15:35:58', 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_route_process (`record_id`, `route_id`, `process_id`, `process_code`, `process_name`, `order_num`, `next_process_id`, `next_process_code`, `next_process_name`, `link_type`, `default_pre_time`, `default_suf_time`, `color_code`, `key_flag`, `is_check`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (2, 1, 2, 'PROCESS007', '复合', 2, 3, 'PROCESS008', '分切', 'SS', 0, 0, '#00AEF3', 'N', 'N', '', NULL, NULL, 0, 0, '', '2024-06-29 15:35:58', '', '2024-06-29 15:36:09', 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_route_process (`record_id`, `route_id`, `process_id`, `process_code`, `process_name`, `order_num`, `next_process_id`, `next_process_code`, `next_process_name`, `link_type`, `default_pre_time`, `default_suf_time`, `color_code`, `key_flag`, `is_check`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (3, 1, 3, 'PROCESS008', '分切', 3, 4, 'PROCESS009', '制袋', 'SS', 0, 0, '#00AEF3', 'N', 'N', '', NULL, NULL, 0, 0, '', '2024-06-29 15:36:09', '', '2024-06-29 15:36:22', 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO pro_route_process (`record_id`, `route_id`, `process_id`, `process_code`, `process_name`, `order_num`, `next_process_id`, `next_process_code`, `next_process_name`, `link_type`, `default_pre_time`, `default_suf_time`, `color_code`, `key_flag`, `is_check`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (4, 1, 4, 'PROCESS009', '制袋', 4, 0, NULL, '无', 'SS', 0, 0, '#00AEF3', 'Y', 'N', '', NULL, NULL, 0, 0, '', '2024-06-29 15:36:22', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);

-- 供应商表
INSERT INTO md_vendor (`vendor_id`, `vendor_code`, `vendor_name`, `vendor_nick`, `vendor_en`, `vendor_des`, `vendor_logo`, `vendor_level`, `vendor_score`, `address`, `website`, `email`, `tel`, `contact1`, `contact1_tel`, `contact1_email`, `contact2`, `contact2_tel`, `contact2_email`, `credit_code`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'V00002', '北京昌平', NULL, NULL, NULL, NULL, 'B', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:33:57', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
-- 客户表
INSERT INTO `md_client` (`client_id`, `client_code`, `client_name`, `client_nick`, `client_en`, `client_des`, `client_logo`, `client_type`, `address`, `website`, `email`, `tel`, `contact1`, `contact1_tel`, `contact1_email`, `contact2`, `contact2_tel`, `contact2_email`, `credit_code`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'C00002', '金融街', NULL, NULL, NULL, NULL, 'ENTERPRISE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:33:46', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
-- 工作站
INSERT INTO `md_workshop` (`workshop_id`, `workshop_code`, `workshop_name`, `area`, `charge`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'WS002', '操作车间', 0.00, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:37:41', '', '2024-06-29 15:37:56', 0, '1000-01-01 00:00:00', 1, NULL, NULL);
-- 车间
INSERT INTO `md_workstation` (`workstation_id`, `workstation_code`, `workstation_name`, `workstation_address`, `workshop_id`, `workshop_code`, `workshop_name`, `process_id`, `process_code`, `process_name`, `warehouse_id`, `warehouse_code`, `warehouse_name`, `location_id`, `location_code`, `location_name`, `area_id`, `area_code`, `area_name`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (1, 'WS0007', '印刷工作站', NULL, 1, 'WS002', '操作车间', 1, 'PROCESS006', '印刷', 1, 'XBK_VIRTUAL', '线边库仓库-虚拟', 1, 'XBKKQ_VIRTUAL', '线边库库区-虚拟', 1, 'XBKKW_VIRTUAL', '线边库库位-虚拟', 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:38:12', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO `md_workstation` (`workstation_id`, `workstation_code`, `workstation_name`, `workstation_address`, `workshop_id`, `workshop_code`, `workshop_name`, `process_id`, `process_code`, `process_name`, `warehouse_id`, `warehouse_code`, `warehouse_name`, `location_id`, `location_code`, `location_name`, `area_id`, `area_code`, `area_name`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (2, 'WS0008', '复合工作站', NULL, 1, 'WS002', '操作车间', 2, 'PROCESS007', '复合', 1, 'XBK_VIRTUAL', '线边库仓库-虚拟', 1, 'XBKKQ_VIRTUAL', '线边库库区-虚拟', 1, 'XBKKW_VIRTUAL', '线边库库位-虚拟', 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:38:25', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO `md_workstation` (`workstation_id`, `workstation_code`, `workstation_name`, `workstation_address`, `workshop_id`, `workshop_code`, `workshop_name`, `process_id`, `process_code`, `process_name`, `warehouse_id`, `warehouse_code`, `warehouse_name`, `location_id`, `location_code`, `location_name`, `area_id`, `area_code`, `area_name`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (3, 'WS0009', '分切工作站', NULL, 1, 'WS002', '操作车间', 3, 'PROCESS008', '分切', 1, 'XBK_VIRTUAL', '线边库仓库-虚拟', 1, 'XBKKQ_VIRTUAL', '线边库库区-虚拟', 1, 'XBKKW_VIRTUAL', '线边库库位-虚拟', 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:38:41', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);
INSERT INTO `md_workstation` (`workstation_id`, `workstation_code`, `workstation_name`, `workstation_address`, `workshop_id`, `workshop_code`, `workshop_name`, `process_id`, `process_code`, `process_name`, `warehouse_id`, `warehouse_code`, `warehouse_name`, `location_id`, `location_code`, `location_name`, `area_id`, `area_code`, `area_name`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `deleteAt`, `version`, `create_user_id`, `update_user_id`) VALUES (4, 'WS0010', '制袋工作站', NULL, 1, 'WS002', '操作车间', 4, 'PROCESS009', '制袋', 1, 'XBK_VIRTUAL', '线边库仓库-虚拟', 1, 'XBKKQ_VIRTUAL', '线边库库区-虚拟', 1, 'XBKKW_VIRTUAL', '线边库库位-虚拟', 'Y', '', NULL, NULL, 0, 0, '', '2024-06-29 15:38:52', '', NULL, 0, '1000-01-01 00:00:00', 1, NULL, NULL);

INSERT INTO `wm_storage_area` (`area_id`, `area_code`, `area_name`, `location_id`, `area`, `max_loa`, `position_x`, `position_y`, `position_z`, `enable_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `update_user_id`, `create_user_id`, `deleted`, `deleteAt`, `version`) VALUES (3, 'A0006', '预置库位', 3, 0.00, 0.00, 0, 0, 0, 'Y', '', NULL, NULL, 0, 0, '', '2024-07-01 21:23:07', '', NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `wm_storage_location` (`location_id`, `location_code`, `location_name`, `warehouse_id`, `area`, `area_flag`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `update_user_id`, `create_user_id`, `deleted`, `deleteAt`, `version`) VALUES (3, 'L003', '预置库区', 4, 0.00, 'N', '', NULL, NULL, 0, 0, '', '2024-07-01 21:22:58', '', NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `wm_warehouse` (`warehouse_id`, `warehouse_code`, `warehouse_name`, `location`, `area`, `charge`, `remark`, `attr1`, `attr2`, `attr3`, `attr4`, `create_by`, `create_time`, `update_by`, `update_time`, `update_user_id`, `create_user_id`, `deleted`, `deleteAt`, `version`) VALUES (4, 'WH000', '预置仓库', NULL, NULL, NULL, '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-29 15:51:26', '', NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
SET FOREIGN_KEY_CHECKS = 1;