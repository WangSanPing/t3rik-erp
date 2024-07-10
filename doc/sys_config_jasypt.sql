SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config_jasypt
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_jasypt`;
CREATE TABLE `sys_config_jasypt` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='加密配置表';

-- ----------------------------
-- Records of sys_config_jasypt
-- ----------------------------
BEGIN;
INSERT INTO `sys_config_jasypt` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `create_user_id`, `update_user_id`) VALUES (6, '加密盐串', 'jasypt.salt', 'rootsza', 'N', '1', '2024-07-01 16:32:58', '1', '2024-07-01 16:33:02', NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
