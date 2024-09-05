ALTER TABLE md_item
    ADD COLUMN mold_no varchar(32) COMMENT '模具号',
ADD COLUMN weight DOUBLE(12,4) COMMENT '单重',
ADD COLUMN metal varchar(32) COMMENT '材质';

DROP TABLE IF EXISTS `sales_order`;
CREATE TABLE `pro_sales_order` (
                                   `sales_order_id` bigint NOT NULL COMMENT '销售订单id',
                                   `sales_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编码',
                                   `sales_order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单名称',
                                   `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '订单状态',
                                   `client_id` bigint NOT NULL COMMENT '客户id',
                                   `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
                                   `client_po_code` varchar(64) DEFAULT NULL COMMENT '客户PO号',
                                   `client_name` varchar(255) NOT NULL COMMENT '客户名称',
                                   `sales_order_date` datetime NOT NULL COMMENT '订货日期',
                                   `delivery_date` datetime NOT NULL COMMENT '交货日期',
                                   `sales_order_quantity` int NOT NULL COMMENT '订货数量',
                                   `currency` varchar(64) DEFAULT NULL COMMENT '币别',
                                   `pay_up` varchar(64) DEFAULT NULL COMMENT '结账方式',
                                   `order_type` varchar(64) DEFAULT NULL COMMENT '订单类别',
                                   `follower_man` varchar(64) DEFAULT NULL COMMENT '跟单',
                                   `sales_man` varchar(64) DEFAULT NULL COMMENT '业务员',
                                   `quality_requirement` varchar(255) DEFAULT NULL COMMENT '质量要求',

                                   `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段1',
                                   `attr2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段2',
                                   `attr3` int DEFAULT '0' COMMENT '预留字段3',
                                   `attr4` int DEFAULT '0' COMMENT '预留字段4',
                                   `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                   `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
                                   `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                                   PRIMARY KEY (`sales_order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单';

DROP TABLE IF EXISTS `sales_order_item`;
CREATE TABLE `pro_sales_order_item` (
                                        `sales_order_id` bigint NOT NULL COMMENT '销售订单id',
                                        `product_id` bigint NOT NULL COMMENT '产品id',
                                        `product_code` varchar(64) NOT NULL COMMENT '产品编码',
                                        `product_name` varchar(64) NOT NULL COMMENT '产品名称',
                                        `product_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品规格',
                                        `quality` varchar(64)  COMMENT '材质',
                                        `color_code` varchar(255) NOT NULL COMMENT '色号',
                                        `color_name` varchar(255) NOT NULL COMMENT '颜色',
                                        `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单位',
                                        `delivery_date` datetime NOT NULL COMMENT '交货日期',
                                        `client_product_name` varchar(64)  COMMENT '客户产品名称',
                                        `stock_num` int  NOT NULL COMMENT '库存数量',

                                        `sale_qty` int   NULL COMMENT '已出货数',
                                        `sale_thqty` int   NULL COMMENT '已退货数',
                                        `sale_sgqty` int   NULL COMMENT '手工消数',
                                        `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
                                        `workorder_id` bigint DEFAULT NULL COMMENT '工单ID',

                                        `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                        `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
                                        `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单产品列表';
