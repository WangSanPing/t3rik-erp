SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hrm_interview_record
-- ----------------------------
DROP TABLE IF EXISTS `hrm_interview_record`;
CREATE TABLE `hrm_interview_record` (
                                        `interview_record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '面试记录主键',
                                        `staff_id` bigint NOT NULL COMMENT '员工主键id',
                                        `staff_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '员工编码',
                                        `staff_name` varchar(64) NOT NULL COMMENT '员工名称',
                                        `rank_id` bigint DEFAULT NULL COMMENT '定级职级主键id',
                                        `rank_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '定级职级编码',
                                        `rank_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '定级职级层次',
                                        `rank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '定级职级层次名称',
                                        `actual_salary` decimal(12,2) DEFAULT NULL COMMENT '定级薪资',
                                        `salary_expectation` decimal(12,2) DEFAULT NULL COMMENT '期望薪资',
                                        `interview_feedback` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '面试反馈',
                                        `times` tinyint DEFAULT NULL COMMENT '面试次数',
                                        `time_for_interview` datetime DEFAULT NULL COMMENT '面试时间',
                                        `interviewer_id` bigint DEFAULT NULL COMMENT '面试官id',
                                        `interviewer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '面试官姓名',
                                        `create_user_id` bigint DEFAULT NULL COMMENT '创建者id',
                                        `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_user_id` bigint DEFAULT NULL COMMENT '修改者id',
                                        `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
                                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                        `status` tinyint DEFAULT NULL COMMENT '状态',
                                        `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                        `deleted` int DEFAULT '0' COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                        `deleteAt` datetime DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                        `version` int DEFAULT '1' COMMENT '乐观锁',
                                        PRIMARY KEY (`interview_record_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1837469421027217411 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='面试记录表';

SET FOREIGN_KEY_CHECKS = 1;
