-- 销售订单
DROP TABLE IF EXISTS `sales_order`;
CREATE TABLE `sales_order` (
                               `sales_order_id` bigint(20) NOT NULL COMMENT '销售订单id',
                               `sales_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编码',
                               `sales_order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单名称',
                               `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '订单状态',
                               `client_id` bigint(20) NOT NULL COMMENT '客户id',
                               `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
                               `client_po_code` varchar(64) DEFAULT NULL COMMENT '客户PO号',
                               `client_name` varchar(255) NOT NULL COMMENT '客户名称',
                               `sales_order_date` datetime NOT NULL COMMENT '订货日期',
                               `delivery_date` datetime NOT NULL COMMENT '交货日期',
                               `sales_order_quantity` int(11) DEFAULT NULL COMMENT '订货数量',
                               `currency` varchar(64) DEFAULT NULL COMMENT '币别',
                               `pay_up` varchar(64) DEFAULT NULL COMMENT '结账方式',
                               `order_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '订单类别',
                               `follower_man` varchar(64) DEFAULT NULL COMMENT '跟单',
                               `sales_man` varchar(64) DEFAULT NULL COMMENT '业务员',
                               `quality_requirement` varchar(255) DEFAULT NULL COMMENT '质量要求',
                               `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段1',
                               `attr2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段2',
                               `attr3` int(11) DEFAULT '0' COMMENT '预留字段3',
                               `attr4` int(11) DEFAULT '0' COMMENT '预留字段4',
                               `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                               `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
                               `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                               PRIMARY KEY (`sales_order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单';

-- 销售订单产品列表
DROP TABLE IF EXISTS `sales_order_item`;
CREATE TABLE `sales_order_item` (
                                    `sales_order_item_id` bigint(20) NOT NULL COMMENT '销售订单产品id',
                                    `sales_order_item_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '销售列表编码',
                                    `sales_order_code` varchar(32) NOT NULL COMMENT '销售订单编码',
                                    `sales_order_id` bigint(20) NOT NULL COMMENT '销售订单id',
                                    `product_id` bigint(20) NOT NULL COMMENT '产品id',
                                    `product_code` varchar(64) NOT NULL COMMENT '产品编码',
                                    `product_name` varchar(64) NOT NULL COMMENT '产品名称',
                                    `product_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品规格',
                                    `client_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户编码',
                                    `client_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户名称',
                                    `quality` varchar(64) DEFAULT NULL COMMENT '材质',
                                    `color_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '色号',
                                    `color_name` varchar(255) NOT NULL COMMENT '颜色',
                                    `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单位',
                                    `sales_order_date` datetime NOT NULL COMMENT '订货日期',
                                    `delivery_date` datetime NOT NULL COMMENT '交货日期',
                                    `client_product_name` varchar(64) DEFAULT NULL COMMENT '客户产品名称',
                                    `sales_order_quantity` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订货数量',
                                    `stock_num` varchar(255) DEFAULT NULL COMMENT '库存数量',
                                    `sale_qty` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已出货数',
                                    `sale_thqty` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已退货数',
                                    `sale_sgqty` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '手工消数',
                                    `owe_qty` decimal(11,2) DEFAULT NULL COMMENT '欠数',
                                    `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
                                    `total_amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
                                    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                                    `workorder_id` bigint(20) DEFAULT NULL COMMENT '工单ID',
                                    `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
                                    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                    `workorder_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单编码',
                                    `workorder_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单名称',
                                    `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '单据状态',
                                    `client_po_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户PO号',
                                    PRIMARY KEY (`sales_order_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单产品列表';

-- 销售送货单
DROP TABLE IF EXISTS `tran_order`;
CREATE TABLE `tran_order` (
                              `tran_order_id` bigint(20) NOT NULL COMMENT '送货单id',
                              `tran_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编码',
                              `warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
                              `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
                              `tran_order_type` varchar(64) DEFAULT NULL COMMENT '送货类别',
                              `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '状态',
                              `client_id` bigint(20) NOT NULL COMMENT '客户id',
                              `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
                              `client_name` varchar(255) NOT NULL COMMENT '客户名称',
                              `tran_date` datetime DEFAULT NULL COMMENT '送货日期',
                              `address` varchar(255) DEFAULT NULL COMMENT '送货地址',
                              `total_amount` decimal(12,2) unsigned DEFAULT '0.00' COMMENT '总金额',
                              `weight` decimal(11,2) unsigned DEFAULT '0.00' COMMENT '总重量',
                              `total` decimal(11,2) unsigned DEFAULT '0.00' COMMENT '总数量',
                              `total_pic` int(11) DEFAULT '0' COMMENT '总件数',
                              `currency` varchar(64) DEFAULT NULL COMMENT '币别',
                              `pay_up` varchar(64) DEFAULT NULL COMMENT '结账方式',
                              `follower_man` varchar(64) DEFAULT NULL COMMENT '跟单',
                              `bus_man` varchar(64) DEFAULT NULL COMMENT '业务员',
                              `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段1',
                              `attr2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段2',
                              `attr3` int(11) DEFAULT '0' COMMENT '预留字段3',
                              `attr4` int(11) DEFAULT '0' COMMENT '预留字段4',
                              `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                              `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
                              `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '更新者',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                              PRIMARY KEY (`tran_order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售送货单';

-- 销售送货单列表
DROP TABLE IF EXISTS `tran_order_line`;
CREATE TABLE `tran_order_line` (
                                   `tran_order_line_id` bigint(20) NOT NULL COMMENT 'id',
                                   `tran_order_id` bigint(20) NOT NULL COMMENT '送货单id',
                                   `client_id` bigint(20) NOT NULL COMMENT '客户id',
                                   `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
                                   `client_name` varchar(255) NOT NULL COMMENT '客户名称',
                                   `follower_man` varchar(64) DEFAULT NULL COMMENT '跟单',
                                   `bus_man` varchar(64) DEFAULT NULL COMMENT '业务员',
                                   `tran_order_type` varchar(64) DEFAULT NULL COMMENT '送货类别',
                                   `tran_date` datetime DEFAULT NULL COMMENT '送货日期',
                                   `tran_code` varchar(64) DEFAULT NULL COMMENT '送货单号',
                                   `warehouse_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
                                   `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
                                   `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
                                   `sales_order_id` bigint(20) NOT NULL COMMENT '销售订单id',
                                   `sales_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '销售订单编码',
                                   `product_id` bigint(20) NOT NULL COMMENT '产品id',
                                   `product_code` varchar(64) NOT NULL COMMENT '产品编码',
                                   `product_name` varchar(64) NOT NULL COMMENT '产品名称',
                                   `product_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品规格',
                                   `quality` varchar(64) DEFAULT NULL COMMENT '材质',
                                   `level` varchar(64) DEFAULT NULL COMMENT '质量等级',
                                   `color_name` varchar(255) NOT NULL COMMENT '颜色',
                                   `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单位',
                                   `sale_qty` decimal(11,2) DEFAULT NULL COMMENT '出货数',
                                   `sale_thqty` decimal(11,2) DEFAULT NULL COMMENT '备品数',
                                   `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
                                   `total_amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
                                   `sale_sgqty` decimal(11,2) DEFAULT NULL COMMENT '手工消数',
                                   `weight` decimal(11,2) DEFAULT NULL COMMENT '重量',
                                   `tax` int(11) DEFAULT NULL COMMENT '含税',
                                   `extra` decimal(12,4) DEFAULT NULL COMMENT '附加费',
                                   `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                                   `pic_num` int(11) DEFAULT NULL COMMENT '件数',
                                   `po` varchar(32) DEFAULT NULL COMMENT 'po',
                                   `sku` varchar(32) DEFAULT NULL COMMENT 'sku',
                                   `client_spec` varchar(500) DEFAULT NULL COMMENT '客户规格',
                                   `client_product_name` varchar(64) DEFAULT NULL COMMENT '客户品名',
                                   `client_color` varchar(64) DEFAULT NULL COMMENT '客户颜色',
                                   `workorder_id` bigint(20) DEFAULT NULL COMMENT '工单ID',
                                   `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
                                   `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '状态',
                                   `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                   `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                   `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售送货单列表';


DROP TABLE IF EXISTS `md_product_color`;
CREATE TABLE `md_product_color` (
                                    `id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                    `color_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                    `color_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                    `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    `status` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    `create_time` datetime DEFAULT NULL,
                                    `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    `update_time` datetime DEFAULT NULL,
                                    `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                    `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


ALTER TABLE md_item ADD COLUMN mold_no varchar(32) COMMENT '模具号',ADD COLUMN weight DOUBLE(12,4) COMMENT '单重',ADD COLUMN metal varchar(32) COMMENT '材质';

INSERT INTO `sys_dict_data` VALUES (null,2, '月结30天', '02', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:44', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,3, '月结45天', '03', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:51', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,4, '月结60天', '04', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:58', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,5, '代收货款', '05', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:13', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,6, '转贝宝', '06', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:22', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,7, '店面收现金', '07', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:29', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,8, '转账', '08', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:37', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,9, '月结90天', '09', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:46', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,1, '客户订单', '0', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:15', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,2, '备货订单', '1', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:27', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,3, '样品订单', '2', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:37', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,4, '退货订单', '3', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:43', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,0, 'A', '0', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:00', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,1, 'AA', '1', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:08', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,2, 'AAA', '2', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:13', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,3, 'AAAA', '3', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:18', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,4, 'AAAAA', '4', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:29', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,0, 'RMB', '01', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:27', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,1, 'HKD', '02', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:35', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,2, 'USD', '03', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:43', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (null,3, 'EUR', '04', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:52', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);

INSERT INTO `sys_dict_type` VALUES (1833053825115680770, '送货类别', 'tran_type', '0', 'admin', '2024-09-09 16:04:23', 'admin', '2024-09-09 16:04:23', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054685090607105, '订单类型', 'order_type', '0', 'admin', '2024-09-09 16:07:48', 'admin', '2024-09-09 16:14:07', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054750651772929, '结账方式', 'pay_up_type', '0', 'admin', '2024-09-09 16:08:04', 'admin', '2024-09-09 16:08:04', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054809674018817, '币别', 'currency_type', '0', 'admin', '2024-09-09 16:08:18', 'admin', '2024-09-09 16:12:39', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833057216776695810, '质量等级', 'level', '0', 'admin', '2024-09-09 16:17:52', 'admin', '2024-09-09 16:17:52', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);

INSERT INTO `sys_menu` VALUES (null,'颜色资料', 2000, 10, 'color', 'mes/md/color/index', NULL, 1, 0, 'C', '1', '0', '0', NULL, 'color', 'admin', NULL, '2024-09-05 10:58:36', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);

INSERT INTO `sys_menu` VALUES (null, '销售管理', 0, 4, 'mes/sales-order', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'build', 'admin', NULL, '2024-09-04 16:20:03', 'admin', NULL, '2024-09-05 11:11:08', '', 0, '1000-01-01 00:00:00', 1);
-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

INSERT INTO `sys_menu` VALUES (null, '销售订单', @parentId, 1, 'sales-order', 'mes/sales/sales-order/index', NULL, 1, 0, 'C', '1', '0', '0', '', 'build', 'admin', NULL, '2024-09-05 11:00:50', 'admin', NULL, '2024-09-10 09:08:31', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售列表', @parentId, 2, '/sales-order/item', 'mes/sales/sales-order/item/salesItem', NULL, 1, 0, 'C', '1', '0', '0', '', 'tab', 'admin', NULL, '2024-09-05 11:02:40', 'admin', NULL, '2024-09-10 09:08:50', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单列', @parentId, 4, 'line', 'mes/sales/tran-order/line/index', NULL, 1, 0, 'C', '1', '0', '0', 'sales:tranOrder:line:list', 'tab', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-23 16:02:35', '销售送货单列菜单', 0, '1000-01-01 00:00:00', 1);
-- 销售送货单列
INSERT INTO `sys_menu` VALUES (null, '销售送货单列查询', 2366, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:query', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:33', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单列新增', 2366, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:add', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:45', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单列修改', 2366, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:edit', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:52', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单列删除', 2366, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:remove', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:56:00', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单列导出', 2366, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:export', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:56:05', '', 0, '1000-01-01 00:00:00', 1);
-- 销售送货单
INSERT INTO `sys_menu` VALUES (null, '销售送货单', @parentId, 3, 'tran-order', 'mes/sales/tran-order/index', NULL, 1, 0, 'C', '1', '0', '0', 'sales:tranOrder:list', 'build', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 13:58:31', '销售送货单菜单', 0, '1000-01-01 00:00:00', 1);
-- 销售送货单Id
SELECT @salesId := LAST_INSERT_ID();
INSERT INTO `sys_menu` VALUES (null, '显示金额', @salesId, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:money', '#', 'admin', NULL, '2024-09-18 16:05:10', 'admin', NULL, '2024-09-18 16:05:20', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单查询', @salesId, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:query', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:11', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单新增', @salesId, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:add', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:24', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单修改', @salesId, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:edit', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:37', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单删除', @salesId, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:remove', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:58', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (null, '销售送货单导出', @salesId, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:export', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:53', '', 0, '1000-01-01 00:00:00', 1);


