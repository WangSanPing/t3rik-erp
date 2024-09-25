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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单'

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单产品列表'

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售送货单'

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售送货单列表'


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin


ALTER TABLE md_item ADD COLUMN mold_no varchar(32) COMMENT '模具号',ADD COLUMN weight DOUBLE(12,4) COMMENT '单重',ADD COLUMN metal varchar(32) COMMENT '材质';
ALTER TABLE md_client ADD COLUMN currency varchar(32) COMMENT '币别' ,ADD COLUMN payUp varchar(32) COMMENT '结账方式' ,
ADD COLUMN followerMan varchar(32) COMMENT '跟单' ,ADD COLUMN salesMan varchar(32) COMMENT '业务员' ;

ALTER TABLE tran_order_line ADD COLUMN tran_order_code varchar(32) COMMENT '销售送货单编码' ;

INSERT INTO `sys_dict_type` VALUES (1833053825115680770, '送货类别', 'tran_type', '0', 'admin', '2024-09-09 16:04:23', 'admin', '2024-09-09 16:04:23', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054685090607105, '订单类型', 'order_type', '0', 'admin', '2024-09-09 16:07:48', 'admin', '2024-09-09 16:14:07', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054750651772929, '结账方式', 'pay_up_type', '0', 'admin', '2024-09-09 16:08:04', 'admin', '2024-09-09 16:08:04', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833054809674018817, '币别', 'currency_type', '0', 'admin', '2024-09-09 16:08:18', 'admin', '2024-09-09 16:12:39', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_type` VALUES (1833057216776695810, '质量等级', 'level', '0', 'admin', '2024-09-09 16:17:52', 'admin', '2024-09-09 16:17:52', NULL, 1, 1, 0, '1000-01-01 00:00:00', 1);


INSERT INTO `sys_dict_data` VALUES (256, 2, '月结30天', '02', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:44', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (257, 3, '月结45天', '03', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:51', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (258, 4, '月结60天', '04', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:10:58', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (259, 5, '代收货款', '05', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:13', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (260, 6, '转贝宝', '06', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:22', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (261, 7, '店面收现金', '07', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:29', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (262, 8, '转账', '08', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:37', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (263, 9, '月结90天', '09', 'pay_up_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:11:46', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (264, 1, '客户订单', '0', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:15', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (265, 2, '备货订单', '1', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:27', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (266, 3, '样品订单', '2', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:37', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (267, 4, '退货订单', '3', 'order_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:15:43', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (268, 0, 'A', '0', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:00', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (269, 1, 'AA', '1', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:08', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (270, 2, 'AAA', '2', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:13', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (271, 3, 'AAAA', '3', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:18', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (272, 4, 'AAAAA', '4', 'level', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:18:29', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (273, 0, 'RMB', '01', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:27', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (274, 1, 'HKD', '02', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:35', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (275, 2, 'USD', '03', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:43', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_dict_data` VALUES (276, 3, 'EUR', '04', 'currency_type', NULL, 'default', 'N', '0', 'admin', '2024-09-11 15:13:52', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `create_user_id`, `update_user_id`, `deleted`, `deleteAt`, `version`) VALUES (277, 1, '销售送货单', '0', 'tran_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:05:29', 'admin', '2024-09-09 16:05:43', NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `create_user_id`, `update_user_id`, `deleted`, `deleteAt`, `version`) VALUES (278, 2, '退货补数单', '1', 'tran_type', NULL, 'default', 'N', '0', 'admin', '2024-09-09 16:05:58', '', NULL, NULL, NULL, NULL, 0, '1000-01-01 00:00:00', 1);


INSERT INTO `sys_menu` VALUES (2427, '销售管理', 0, 4, 'mes/sales-order', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'build', 'admin', NULL, '2024-09-04 16:20:03', 'admin', NULL, '2024-09-05 11:11:08', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2428, '颜色资料', 2000, 10, 'color', 'mes/md/color/index', NULL, 1, 0, 'C', '1', '0', '0', NULL, 'color', 'admin', NULL, '2024-09-05 10:58:36', '', NULL, NULL, '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2429, '销售订单', 2354, 1, 'sales-order', 'mes/sales/sales-order/index', NULL, 1, 0, 'C', '1', '0', '0', '', 'build', 'admin', NULL, '2024-09-05 11:00:50', 'admin', NULL, '2024-09-10 09:08:31', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2430, '销售列表', 2354, 2, '/sales-order/item', 'mes/sales/sales-order/item/salesItem', NULL, 1, 0, 'C', '1', '0', '0', '', 'tab', 'admin', NULL, '2024-09-05 11:02:40', 'admin', NULL, '2024-09-10 09:08:50', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2431, '销售送货单', 2354, 3, 'tran-order', 'mes/sales/tran-order/index', NULL, 1, 0, 'C', '1', '0', '0', 'sales:tranOrder:list', 'build', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 13:58:31', '销售送货单菜单', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2432, '销售送货单查询', 2360, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:query', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:11', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2433, '销售送货单新增', 2360, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:add', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:24', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2434, '销售送货单修改', 2360, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:edit', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:37', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2435, '销售送货单删除', 2360, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:remove', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:58', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2436, '销售送货单导出', 2360, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:export', '#', 'admin', NULL, '2024-09-09 16:38:50', 'admin', NULL, '2024-09-11 11:52:53', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2437, '销售送货单列', 2354, 4, 'line', 'mes/sales/tran-order/line/index', NULL, 1, 0, 'C', '1', '0', '0', 'sales:tranOrder:line:list', 'tab', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-23 16:02:35', '销售送货单列菜单', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2438, '销售送货单列查询', 2366, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:query', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:33', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2439, '销售送货单列新增', 2366, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:add', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:45', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2440, '销售送货单列修改', 2366, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:edit', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:55:52', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2441, '销售送货单列删除', 2366, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:remove', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:56:00', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2442, '销售送货单列导出', 2366, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:line:export', '#', 'admin', NULL, '2024-09-13 10:22:19', 'admin', NULL, '2024-09-13 14:56:05', '', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `sys_menu` VALUES (2443, '显示金额', 2360, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'sales:tranOrder:money', '#', 'admin', NULL, '2024-09-18 16:05:10', 'admin', NULL, '2024-09-18 16:05:20', '', 0, '1000-01-01 00:00:00', 1);


INSERT INTO `mes`.`sys_auto_code_rule`  VALUES (255, 'SALES_ORDER_CODE', '销售订单编号', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-11 09:44:04', 'admin', '2024-09-11 09:44:56', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_rule`  VALUES (256, 'TRAN_CODE', '送货单据编码', '送货单据编码', 11, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-14 10:02:46', '', NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_rule`  VALUES (257, 'SALES_ITEM_CODE', '销售明细编码', NULL, 12, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-18 17:22:51', 'admin', '2024-09-18 17:27:33', 0, '1000-01-01 00:00:00', 1);

INSERT INTO `mes`.`sys_auto_code_part` VALUES (340, 255, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'SA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-13 09:41:27', '', NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (338, 255, 2, 'NOWDATE', 'DATEPART', '年月日部分', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-11 09:47:09', '', NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (339, 255, 3, 'SERIALNO', 'SERIAL', '流水号部分', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-11 09:48:38', 'admin', '2024-09-11 09:55:03', 0, '1000-01-01 00:00:00', 1);

INSERT INTO `mes`.`sys_auto_code_part` VALUES (345, 256, 0, 'FIXCHAR', 'P1', '前缀', 2, NULL, NULL, 'KH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-18 17:26:22', '', NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (346, 256, 1, 'NOWDATE', 'P2', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-18 17:26:46', '', NULL, 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (347, 256, 2, 'SERIALNO', 'P3', '流水号', 2, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-18 17:27:10', 'admin', '2024-09-18 17:27:22', 0, '1000-01-01 00:00:00', 1);

INSERT INTO `mes`.`sys_auto_code_part` VALUES (341, 257, 1, 'FIXCHAR', 'P1', '前缀', 1, NULL, NULL, 'P0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-14 10:03:45', 'admin', '2024-09-14 10:05:21', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (342, 257, 3, 'NOWDATE', 'P2', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-09-14 10:05:14', 'admin', '2024-09-14 10:06:04', 0, '1000-01-01 00:00:00', 1);
INSERT INTO `mes`.`sys_auto_code_part` VALUES (343, 257, 3, 'SERIALNO', 'P3', '流水', 2, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2024-09-14 10:05:53', 'admin', '2024-09-14 10:06:10', 0, '1000-01-01 00:00:00', 1);
