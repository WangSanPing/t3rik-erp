
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日历信息表';

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='已触发的触发器表';

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务详细信息表';

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='暂停的触发器表';

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='调度器状态表';

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='简单触发器的信息表';

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='同步机制的行锁表';

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='触发器详细信息表';

-- ----------------------------
-- Table structure for cal_holiday
-- ----------------------------
DROP TABLE IF EXISTS `cal_holiday`;
CREATE TABLE `cal_holiday` (
  `holiday_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `the_day` datetime DEFAULT NULL COMMENT '日期',
  `holiday_type` varchar(64) DEFAULT NULL COMMENT '日期类型',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`holiday_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='节假日设置';

-- ----------------------------
-- Table structure for cal_plan
-- ----------------------------
DROP TABLE IF EXISTS `cal_plan`;
CREATE TABLE `cal_plan` (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_code` varchar(64) NOT NULL COMMENT '计划编号',
  `plan_name` varchar(255) NOT NULL COMMENT '计划名称',
  `calendar_type` varchar(64) DEFAULT NULL COMMENT '班组类型',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `shift_type` varchar(64) DEFAULT NULL COMMENT '轮班方式',
  `shift_method` varchar(64) DEFAULT NULL COMMENT '倒班方式',
  `shift_count` int DEFAULT NULL COMMENT '数',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='排班计划表';

-- ----------------------------
-- Table structure for cal_plan_team
-- ----------------------------
DROP TABLE IF EXISTS `cal_plan_team`;
CREATE TABLE `cal_plan_team` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `plan_id` bigint NOT NULL COMMENT '计划ID',
  `team_id` bigint NOT NULL COMMENT '班组ID',
  `team_code` varchar(64) DEFAULT NULL COMMENT '班组编号',
  `team_name` varchar(64) DEFAULT NULL COMMENT '班组名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计划班组表';

-- ----------------------------
-- Table structure for cal_shift
-- ----------------------------
DROP TABLE IF EXISTS `cal_shift`;
CREATE TABLE `cal_shift` (
  `shift_id` bigint NOT NULL AUTO_INCREMENT COMMENT '班次ID',
  `plan_id` bigint NOT NULL COMMENT '计划ID',
  `order_num` int NOT NULL COMMENT '序号',
  `shift_name` varchar(64) NOT NULL COMMENT '班次名称',
  `start_time` varchar(10) NOT NULL COMMENT '开始时间',
  `end_time` varchar(10) NOT NULL COMMENT '结束时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shift_id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计划班次表';

-- ----------------------------
-- Table structure for cal_team
-- ----------------------------
DROP TABLE IF EXISTS `cal_team`;
CREATE TABLE `cal_team` (
  `team_id` bigint NOT NULL AUTO_INCREMENT COMMENT '班组ID',
  `team_code` varchar(64) NOT NULL COMMENT '班组编号',
  `team_name` varchar(255) NOT NULL COMMENT '班组名称',
  `calendar_type` varchar(64) DEFAULT NULL COMMENT '班组类型',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班组表';

-- ----------------------------
-- Table structure for cal_team_member
-- ----------------------------
DROP TABLE IF EXISTS `cal_team_member`;
CREATE TABLE `cal_team_member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT COMMENT '班组成员ID',
  `team_id` bigint NOT NULL COMMENT '班组ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `tel` varchar(64) DEFAULT NULL COMMENT '电话',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班组成员表';

-- ----------------------------
-- Table structure for cal_teamshift
-- ----------------------------
DROP TABLE IF EXISTS `cal_teamshift`;
CREATE TABLE `cal_teamshift` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `the_day` varchar(64) NOT NULL COMMENT '日期',
  `team_id` bigint NOT NULL COMMENT '班组ID',
  `team_name` varchar(255) DEFAULT NULL COMMENT '班组名称',
  `shift_id` bigint NOT NULL COMMENT '班次ID',
  `shift_name` varchar(255) DEFAULT NULL COMMENT '班次名称',
  `order_num` int DEFAULT NULL COMMENT '序号',
  `plan_id` bigint DEFAULT NULL COMMENT '计划ID',
  `calendar_type` varchar(64) DEFAULT NULL COMMENT '班组类型',
  `shift_type` varchar(64) DEFAULT NULL COMMENT '轮班方式',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=745 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班组排班表';

-- ----------------------------
-- Table structure for db_test
-- ----------------------------
DROP TABLE IF EXISTS `db_test`;
CREATE TABLE `db_test` (
  `area_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库位ID',
  `area_code` varchar(64) NOT NULL COMMENT '库位编码',
  `area_name` varchar(255) NOT NULL COMMENT '库位名称',
  `location_id` bigint NOT NULL COMMENT '库区ID',
  `area` double(8,2) DEFAULT NULL COMMENT '面积',
  `max_loa` double(8,2) DEFAULT NULL COMMENT '最大载重量',
  `position_x` int DEFAULT NULL COMMENT '库位位置X',
  `position_y` int DEFAULT NULL COMMENT '库位位置y',
  `position_z` int DEFAULT NULL COMMENT '库位位置z',
  `enable_flag` char(1) DEFAULT NULL COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_code` (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库位表';

-- ----------------------------
-- Table structure for dv_check_machinery
-- ----------------------------
DROP TABLE IF EXISTS `dv_check_machinery`;
CREATE TABLE `dv_check_machinery` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `plan_id` bigint NOT NULL COMMENT '计划ID',
  `machinery_id` bigint NOT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) NOT NULL COMMENT '设备编码',
  `machinery_name` varchar(255) NOT NULL COMMENT '设备名称',
  `machinery_brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `machinery_spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点检设备表';

-- ----------------------------
-- Table structure for dv_check_plan
-- ----------------------------
DROP TABLE IF EXISTS `dv_check_plan`;
CREATE TABLE `dv_check_plan` (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_code` varchar(64) NOT NULL COMMENT '计划编码',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '计划名称',
  `plan_type` varchar(64) NOT NULL COMMENT '计划类型',
  `start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '结束日期',
  `cycle_type` varchar(64) DEFAULT NULL COMMENT '频率',
  `cycle_count` int DEFAULT NULL COMMENT '次数',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备点检保养计划头表';

-- ----------------------------
-- Table structure for dv_check_record
-- ----------------------------
DROP TABLE IF EXISTS `dv_check_record`;
CREATE TABLE `dv_check_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_id` bigint DEFAULT NULL COMMENT '计划ID',
  `plan_code` varchar(64) DEFAULT NULL COMMENT '计划编码',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '计划名称',
  `plan_type` varchar(64) DEFAULT NULL COMMENT '计划类型',
  `machinery_id` bigint NOT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) NOT NULL COMMENT '设备编码',
  `machinery_name` varchar(255) NOT NULL COMMENT '设备名称',
  `machinery_brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `machinery_spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `check_time` datetime NOT NULL COMMENT '点检时间',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备点检记录表';

-- ----------------------------
-- Table structure for dv_check_record_line
-- ----------------------------
DROP TABLE IF EXISTS `dv_check_record_line`;
CREATE TABLE `dv_check_record_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `record_id` bigint NOT NULL COMMENT '计划ID',
  `subject_id` bigint NOT NULL COMMENT '项目ID',
  `subject_code` varchar(64) NOT NULL COMMENT '项目编码',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `subject_type` varchar(64) DEFAULT NULL COMMENT '项目类型',
  `subject_content` varchar(500) NOT NULL COMMENT '项目内容',
  `subject_standard` varchar(255) DEFAULT NULL COMMENT '标准',
  `check_status` varchar(64) NOT NULL COMMENT '点检结果',
  `check_result` varchar(500) DEFAULT NULL COMMENT '异常描述',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备点检记录行表';

-- ----------------------------
-- Table structure for dv_check_subject
-- ----------------------------
DROP TABLE IF EXISTS `dv_check_subject`;
CREATE TABLE `dv_check_subject` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `plan_id` bigint NOT NULL COMMENT '计划ID',
  `subject_id` bigint NOT NULL COMMENT '设备ID',
  `subject_code` varchar(64) NOT NULL COMMENT '项目编码',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `subject_type` varchar(64) DEFAULT NULL COMMENT '项目类型',
  `subject_content` varchar(500) NOT NULL COMMENT '项目内容',
  `subject_standard` varchar(255) DEFAULT NULL COMMENT '标准',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点检项目表';

-- ----------------------------
-- Table structure for dv_machinery
-- ----------------------------
DROP TABLE IF EXISTS `dv_machinery`;
CREATE TABLE `dv_machinery` (
  `machinery_id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备类型ID',
  `machinery_code` varchar(64) NOT NULL COMMENT '设备类型编码',
  `machinery_name` varchar(255) NOT NULL COMMENT '设备类型名称',
  `machinery_brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `machinery_spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `machinery_type_id` bigint NOT NULL COMMENT '设备类型ID',
  `machinery_type_code` varchar(64) DEFAULT NULL COMMENT '设备类型编码',
  `machinery_type_name` varchar(255) DEFAULT NULL COMMENT '设备类型名称',
  `workshop_id` bigint NOT NULL COMMENT '所属车间ID',
  `workshop_code` varchar(64) DEFAULT NULL COMMENT '所属车间编码',
  `workshop_name` varchar(255) DEFAULT NULL COMMENT '所属车间名称',
  `status` varchar(64) NOT NULL DEFAULT 'STOP' COMMENT '设备状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`machinery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';

-- ----------------------------
-- Table structure for dv_machinery_type
-- ----------------------------
DROP TABLE IF EXISTS `dv_machinery_type`;
CREATE TABLE `dv_machinery_type` (
  `machinery_type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备类型ID',
  `machinery_type_code` varchar(64) NOT NULL COMMENT '设备类型编码',
  `machinery_type_name` varchar(255) NOT NULL COMMENT '设备类型名称',
  `parent_type_id` bigint DEFAULT '0' COMMENT '父类型ID',
  `ancestors` varchar(255) NOT NULL COMMENT '所有父节点ID',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`machinery_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备类型表';

-- ----------------------------
-- Table structure for dv_mainten_record
-- ----------------------------
DROP TABLE IF EXISTS `dv_mainten_record`;
CREATE TABLE `dv_mainten_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_id` bigint DEFAULT NULL COMMENT '计划ID',
  `plan_code` varchar(64) DEFAULT NULL COMMENT '计划编码',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '计划名称',
  `plan_type` varchar(64) DEFAULT NULL COMMENT '计划类型',
  `machinery_id` bigint NOT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) NOT NULL COMMENT '设备编码',
  `machinery_name` varchar(255) NOT NULL COMMENT '设备名称',
  `machinery_brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `machinery_spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `mainten_time` datetime NOT NULL COMMENT '保养时间',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备保养记录表';

-- ----------------------------
-- Table structure for dv_mainten_record_line
-- ----------------------------
DROP TABLE IF EXISTS `dv_mainten_record_line`;
CREATE TABLE `dv_mainten_record_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `record_id` bigint NOT NULL COMMENT '计划ID',
  `subject_id` bigint NOT NULL COMMENT '项目ID',
  `subject_code` varchar(64) NOT NULL COMMENT '项目编码',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `subject_type` varchar(64) DEFAULT NULL COMMENT '项目类型',
  `subject_content` varchar(500) NOT NULL COMMENT '项目内容',
  `subject_standard` varchar(255) DEFAULT NULL COMMENT '标准',
  `mainten_status` varchar(64) NOT NULL COMMENT '保养结果',
  `mainten_result` varchar(500) DEFAULT NULL COMMENT '异常描述',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备保养记录行表';

-- ----------------------------
-- Table structure for dv_repair
-- ----------------------------
DROP TABLE IF EXISTS `dv_repair`;
CREATE TABLE `dv_repair` (
  `repair_id` bigint NOT NULL AUTO_INCREMENT COMMENT '维修单ID',
  `repair_code` varchar(64) NOT NULL COMMENT '维修单编号',
  `repair_name` varchar(255) DEFAULT NULL COMMENT '维修单名称',
  `machinery_id` bigint NOT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) NOT NULL COMMENT '设备编码',
  `machinery_name` varchar(255) NOT NULL COMMENT '设备名称',
  `machinery_brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `machinery_spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `machinery_type_id` bigint NOT NULL COMMENT '设备类型ID',
  `require_date` datetime DEFAULT NULL COMMENT '报修日期',
  `finish_date` datetime DEFAULT NULL COMMENT '维修完成日期',
  `confirm_date` datetime DEFAULT NULL COMMENT '验收日期',
  `repair_result` varchar(64) DEFAULT NULL COMMENT '维修结果',
  `accepted_by` varchar(64) DEFAULT NULL COMMENT '维修人员',
  `confirm_by` varchar(64) DEFAULT NULL COMMENT '验收人员',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`repair_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备维修单';

-- ----------------------------
-- Table structure for dv_repair_line
-- ----------------------------
DROP TABLE IF EXISTS `dv_repair_line`;
CREATE TABLE `dv_repair_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `repair_id` bigint NOT NULL COMMENT '维修单ID',
  `subject_id` bigint NOT NULL COMMENT '项目ID',
  `subject_code` varchar(64) NOT NULL COMMENT '项目编码',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `subject_type` varchar(64) DEFAULT NULL COMMENT '项目类型',
  `subject_content` varchar(500) NOT NULL COMMENT '项目内容',
  `subject_standard` varchar(255) DEFAULT NULL COMMENT '标准',
  `malfunction` varchar(500) DEFAULT NULL COMMENT '故障描述',
  `malfunction_url` varchar(255) DEFAULT NULL COMMENT '故障描述资源',
  `repair_des` varchar(500) DEFAULT NULL COMMENT '维修情况',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备维修单行';

-- ----------------------------
-- Table structure for dv_subject
-- ----------------------------
DROP TABLE IF EXISTS `dv_subject`;
CREATE TABLE `dv_subject` (
  `subject_id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `subject_code` varchar(64) NOT NULL COMMENT '项目编码',
  `subject_name` varchar(255) NOT NULL COMMENT '项目名称',
  `subject_type` varchar(64) DEFAULT '0' COMMENT '项目类型',
  `subject_content` varchar(500) NOT NULL COMMENT '项目内容',
  `subject_standard` varchar(255) DEFAULT NULL COMMENT '标准',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备点检保养项目表';

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
BEGIN;
INSERT INTO `gen_table` VALUES (114, 'wm_transfer_line', '转移单行表', NULL, NULL, 'WmTransferLine', 'crud', 'com.t3rik.mes.wm', 'wm', 'transferline', '转移单行', 'yinjinlu', '0', '/', '{}', 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49', NULL);
INSERT INTO `gen_table` VALUES (115, 'wm_sn', 'SN码表', NULL, NULL, 'WmSn', 'crud', 'com.t3rik.mes.wm', 'wm', 'sn', 'SN码', 'yinjinlu', '0', '/', '{}', 'admin', '2022-12-08 18:53:46', '', '2022-12-08 19:09:56', NULL);
INSERT INTO `gen_table` VALUES (116, 'sys_message', '消息表', NULL, NULL, 'SysMessage', 'crud', 'com.t3rik.system', 'system', 'message', '消息', 'yinjinlu', '0', '/', '{}', 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54', NULL);
INSERT INTO `gen_table` VALUES (117, 'wm_stock_taking_result', '库存盘点结果表', NULL, NULL, 'WmStockTakingResult', 'crud', 'com.t3rik.mes.wm', 'wm', 'stocktakingresult', '库存盘点结果', 'yinjinlu', '0', '/', '{}', 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54', NULL);
INSERT INTO `gen_table` VALUES (119, 'print_printer_config', '打印机配置', NULL, NULL, 'PrintPrinterConfig', 'crud', 'com.t3rik.mes.print', 'print', 'printerconfig', '打印机配置', 'yinjinlu', '0', '/', '{}', 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:52', NULL);
INSERT INTO `gen_table` VALUES (120, 'pro_outsource_order', '外协工单表', NULL, NULL, 'ProOutsourceOrder', 'crud', 'com.t3rik.mes.pro', 'pro', 'outsourceorder', '外协工单', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:01', NULL);
INSERT INTO `gen_table` VALUES (121, 'wm_outsource_issue', '外协领料单头表', NULL, NULL, 'WmOutsourceIssue', 'crud', 'com.t3rik.mes.wm', 'wm', 'outsourceissue', '外协领料单头', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:31', NULL);
INSERT INTO `gen_table` VALUES (122, 'wm_outsource_issue_line', '外协领料单行表', NULL, NULL, 'WmOutsourceIssueLine', 'crud', 'com.t3rik.mes.wm', 'wm', 'outsourceissueline', '外协领料单行', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43', NULL);
INSERT INTO `gen_table` VALUES (123, 'wm_outsource_recpt', '外协入库单表', NULL, NULL, 'WmOutsourceRecpt', 'crud', 'com.t3rik.mes.wm', 'wm', 'outsourcerecpt', '外协入库单', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54', NULL);
INSERT INTO `gen_table` VALUES (124, 'wm_outsource_recpt_line', '外协入库单行表', NULL, NULL, 'WmOutsourceRecptLine', 'crud', 'com.t3rik.mes.wm', 'wm', 'oursourcerecptline', '外协入库单行', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18', NULL);
INSERT INTO `gen_table` VALUES (127, 'md_product_sip', '产品SIP表', NULL, NULL, 'MdProductSip', 'crud', 'com.t3rik.mes.md', 'md', 'sip', '产品SIP', 'yinjinlu', '0', '/', '{}', 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30', NULL);
INSERT INTO `gen_table` VALUES (128, 'sys_dict_type', '字典类型表', NULL, NULL, 'SysDictType', 'crud', 'com.t3rik.mes.md', 'md', 'type', '字典类型', 't3rik', '0', '/', '{}', 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33', NULL);
INSERT INTO `gen_table` VALUES (129, 'pro_client_order', '客户订单', '', '', 'ProClientOrder', 'crud', 'com.t3rik.mes.pro', 'pro', 'client-order', '客户订单', 't3rik', '0', '/', '{\"parentMenuId\":\"2072\"}', 'admin', '2024-05-01 06:29:09', '', '2024-05-01 15:34:43', NULL);
INSERT INTO `gen_table` VALUES (130, 'pro_client_order_item', '客户订单材料表\n', NULL, NULL, 'ProClientOrderItem', 'crud', 'com.t3rik.mes.pro', 'pro', 'client-order-item', '客户订单材料\n', 't3rik', '0', '/', '{}', 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:26', NULL);
INSERT INTO `gen_table` VALUES (132, 'wm_waste_line', '生产废料单行表', NULL, NULL, 'WmWasteLine', 'crud', 'com.t3rik.mes.wm', 'mes', 'wm-waste-line', '生产废料单行', 't3rik', '0', '/', '{}', 'admin', '2024-05-10 05:40:49', '', '2024-05-11 03:24:03', NULL);
INSERT INTO `gen_table` VALUES (134, 'wm_waste_header', '生产废料单头表', NULL, NULL, 'WmWasteHeader', 'crud', 'com.t3rik.mes.md', 'md', 'header', '生产废料单头', 't3rik', '0', '/', NULL, 'admin', '2024-05-16 02:15:28', '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
BEGIN;
INSERT INTO `gen_table_column` VALUES (2617, '114', 'line_id', '明细行ID', 'bigint(20)', 'Long', 'lineId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2618, '114', 'transfer_id', '装箱单ID', 'bigint(20)', 'Long', 'transferId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2619, '114', 'material_stock_id', '库存记录ID', 'bigint(20)', 'Long', 'materialStockId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2620, '114', 'item_id', '产品物料ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2621, '114', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2622, '114', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2623, '114', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 7, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2624, '114', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2022-11-28 15:23:38', '', '2022-11-28 15:25:49');
INSERT INTO `gen_table_column` VALUES (2625, '114', 'quantity_transfer', '装箱数量', 'double(12,2)', 'BigDecimal', 'quantityTransfer', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2626, '114', 'workorder_id', '生产工单ID', 'bigint(20)', 'Long', 'workorderId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2627, '114', 'workorder_code', '生产工单编号', 'varchar(64)', 'String', 'workorderCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2628, '114', 'batch_code', '批次号', 'varchar(255)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2629, '114', 'from_warehouse_id', '移出仓库ID', 'bigint(20)', 'Long', 'fromWarehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2630, '114', 'from_warehouse_code', '移出仓库编码', 'varchar(64)', 'String', 'fromWarehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2631, '114', 'from_warehouse_name', '移出仓库名称', 'varchar(255)', 'String', 'fromWarehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2632, '114', 'from_location_id', '移出库区ID', 'bigint(20)', 'Long', 'fromLocationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2633, '114', 'from_location_code', '移出库区编码', 'varchar(64)', 'String', 'fromLocationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2634, '114', 'from_location_name', '移出库区名称', 'varchar(255)', 'String', 'fromLocationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 18, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2635, '114', 'from_area_id', '移出库位ID', 'bigint(20)', 'Long', 'fromAreaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 19, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2636, '114', 'from_area_code', '移出库位编码', 'varchar(64)', 'String', 'fromAreaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 20, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2637, '114', 'from_area_name', '移出库位名称', 'varchar(255)', 'String', 'fromAreaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 21, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2638, '114', 'to_warehouse_id', '移入仓库ID', 'bigint(20)', 'Long', 'toWarehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 22, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2639, '114', 'to_warehouse_code', '移入仓库编码', 'varchar(64)', 'String', 'toWarehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 23, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:50');
INSERT INTO `gen_table_column` VALUES (2640, '114', 'to_warehouse_name', '移入仓库名称', 'varchar(255)', 'String', 'toWarehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 24, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2641, '114', 'to_location_id', '移入库区ID', 'bigint(20)', 'Long', 'toLocationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 25, 'admin', '2022-11-28 15:23:39', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2642, '114', 'to_location_code', '移入库区编码', 'varchar(64)', 'String', 'toLocationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 26, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2643, '114', 'to_location_name', '移入库区名称', 'varchar(255)', 'String', 'toLocationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 27, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2644, '114', 'to_area_id', '移入库位ID', 'bigint(20)', 'Long', 'toAreaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 28, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2645, '114', 'to_area_code', '移入库位编码', 'varchar(64)', 'String', 'toAreaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 29, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2646, '114', 'to_area_name', '移入库位名称', 'varchar(255)', 'String', 'toAreaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 30, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2647, '114', 'expire_date', '有效期', 'datetime', 'Date', 'expireDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 31, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2648, '114', 'vendor_id', '供应商ID', 'bigint(20)', 'Long', 'vendorId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 32, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2649, '114', 'vendor_code', '供应商编码', 'varchar(64)', 'String', 'vendorCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 33, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2650, '114', 'vendor_name', '供应商名称', 'varchar(255)', 'String', 'vendorName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 34, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2651, '114', 'vendor_nick', '供应商简称', 'varchar(255)', 'String', 'vendorNick', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 35, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2652, '114', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 36, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2653, '114', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 37, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2654, '114', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 38, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:51');
INSERT INTO `gen_table_column` VALUES (2655, '114', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 39, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2656, '114', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 40, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2657, '114', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 41, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2658, '114', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 42, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2659, '114', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 43, 'admin', '2022-11-28 15:23:40', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2660, '114', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 44, 'admin', '2022-11-28 15:23:41', '', '2022-11-28 15:25:52');
INSERT INTO `gen_table_column` VALUES (2661, '115', 'sn_id', 'SN码ID', 'bigint(20)', 'Long', 'snId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 1, 'admin', '2022-12-08 18:53:46', NULL, '2022-12-08 19:09:56');
INSERT INTO `gen_table_column` VALUES (2662, '115', 'sn_code', 'SN码', 'varchar(64)', 'String', 'snCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:56');
INSERT INTO `gen_table_column` VALUES (2663, '115', 'item_id', '产品物料ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:56');
INSERT INTO `gen_table_column` VALUES (2664, '115', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2665, '115', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 5, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2666, '115', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 6, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2667, '115', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2668, '115', 'batch_code', '批次号', 'varchar(255)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2669, '115', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 9, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2670, '115', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 10, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2671, '115', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 11, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2672, '115', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 12, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2673, '115', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 13, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2674, '115', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 14, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2675, '115', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', NULL, 15, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2676, '115', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', NULL, 16, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2677, '115', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', NULL, 17, 'admin', '2022-12-08 18:53:47', NULL, '2022-12-08 19:09:57');
INSERT INTO `gen_table_column` VALUES (2678, '116', 'message_id', '附件ID', 'bigint(20)', 'Long', 'messageId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2679, '116', 'message_type', '消息类型', 'varchar(64)', 'String', 'messageType', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', 'sys_message_type', 2, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2680, '116', 'message_level', '消息级别', 'varchar(64)', 'String', 'messageLevel', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', 'sys_message_level', 3, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2681, '116', 'message_title', '标题', 'varchar(64)', 'String', 'messageTitle', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2682, '116', 'message_content', '内容', 'longblob', 'String', 'messageContent', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'editor', '', 5, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2683, '116', 'sender_id', '发送人ID', 'bigint(20)', 'Long', 'senderId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2684, '116', 'sender_name', '发送人名称', 'varchar(64)', 'String', 'senderName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 7, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2685, '116', 'sender_nick', '发送人昵称', 'varchar(64)', 'String', 'senderNick', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2686, '116', 'recipient_id', '接收人ID', 'bigint(20)', 'Long', 'recipientId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2687, '116', 'recipient_name', '接收人名称', 'varchar(64)', 'String', 'recipientName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 10, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2688, '116', 'recipient_nick', '接收人昵称', 'varchar(64)', 'String', 'recipientNick', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2689, '116', 'process_time', '处理时间', 'datetime', 'Date', 'processTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 12, 'admin', '2023-03-06 19:34:38', '', '2023-03-06 19:42:54');
INSERT INTO `gen_table_column` VALUES (2690, '116', 'call_back', '回调地址', 'varchar(255)', 'String', 'callBack', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2691, '116', 'status', '状态', 'varchar(64)', 'String', 'status', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'radio', 'sys_message_status', 14, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2692, '116', 'deleted_flag', '是否删除', 'char(1)', 'String', 'deletedFlag', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', 'sys_yes_no', 15, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2693, '116', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 16, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2694, '116', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 17, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2695, '116', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 18, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2696, '116', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 19, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2697, '116', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 20, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2698, '116', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 21, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2699, '116', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 22, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2700, '116', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 23, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2701, '116', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 24, 'admin', '2023-03-06 19:34:39', '', '2023-03-06 19:42:55');
INSERT INTO `gen_table_column` VALUES (2702, '117', 'result_id', '结果ID', 'bigint(20)', 'Long', 'resultId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2703, '117', 'taking_id', '盘点单ID', 'bigint(20)', 'Long', 'takingId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2704, '117', 'item_id', '产品物料ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2705, '117', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2706, '117', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 5, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2707, '117', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 6, 'admin', '2023-08-22 14:16:02', '', '2023-08-22 14:16:54');
INSERT INTO `gen_table_column` VALUES (2708, '117', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2709, '117', 'unit_name', '单位名称', 'varchar(64)', 'String', 'unitName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2710, '117', 'quantity', '数量', 'int(11)', 'Long', 'quantity', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2711, '117', 'taking_quantity', '盘点数量', 'int(11)', 'Long', 'takingQuantity', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2712, '117', 'taking_status', '盘点状态', 'varchar(64)', 'String', 'takingStatus', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'radio', '', 11, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2713, '117', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'textarea', '', 12, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2714, '117', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 13, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2715, '117', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 14, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2716, '117', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 15, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2717, '117', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 16, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2718, '117', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 17, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2719, '117', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 18, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2720, '117', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 19, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2721, '117', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 20, 'admin', '2023-08-22 14:16:03', '', '2023-08-22 14:16:55');
INSERT INTO `gen_table_column` VALUES (2745, '119', 'printer_id', '打印机ID', 'bigint(20)', 'Long', 'printerId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:52');
INSERT INTO `gen_table_column` VALUES (2746, '119', 'printer_type', '打印机类型', 'varchar(64)', 'String', 'printerType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', 'mes_printer_type', 2, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:52');
INSERT INTO `gen_table_column` VALUES (2747, '119', 'printer_name', '打印机名称', 'varchar(255)', 'String', 'printerName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:52');
INSERT INTO `gen_table_column` VALUES (2748, '119', 'brand', '品牌', 'varchar(64)', 'String', 'brand', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:52');
INSERT INTO `gen_table_column` VALUES (2749, '119', 'printer_model', '型号', 'varchar(64)', 'String', 'printerModel', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2750, '119', 'connection_type', '连接类型', 'varchar(64)', 'String', 'connectionType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', 'mes_conn_type', 6, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2751, '119', 'printer_url', '图片URL', 'varchar(255)', 'String', 'printerUrl', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2752, '119', 'printer_ip', '打印机IP', 'varchar(64)', 'String', 'printerIp', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2753, '119', 'printer_port', '打印机端口', 'int(11)', 'Long', 'printerPort', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2754, '119', 'client_sid', '打印客户端SID', 'varchar(32)', 'String', 'clientSid', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-09-01 11:20:34', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2755, '119', 'client_ip', '打印客户端IP', 'varchar(64)', 'String', 'clientIp', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2756, '119', 'client_port', '打印客户端端口', 'int(11)', 'Long', 'clientPort', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2757, '119', 'enable_flag', '启用状态', 'char(1)', 'String', 'enableFlag', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', 'sys_yes_no', 13, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2758, '119', 'status', '打印机状态', 'varchar(64)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', 'mes_printer_status', 14, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2759, '119', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 15, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2760, '119', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 16, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2761, '119', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 17, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2762, '119', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 18, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:53');
INSERT INTO `gen_table_column` VALUES (2763, '119', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 19, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:54');
INSERT INTO `gen_table_column` VALUES (2764, '119', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 20, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:54');
INSERT INTO `gen_table_column` VALUES (2765, '119', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 21, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:54');
INSERT INTO `gen_table_column` VALUES (2766, '119', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 22, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:54');
INSERT INTO `gen_table_column` VALUES (2767, '119', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 23, 'admin', '2023-09-01 11:20:35', '', '2023-09-01 11:21:54');
INSERT INTO `gen_table_column` VALUES (2768, '120', 'workorder_id', '工单ID', 'bigint(20)', 'Long', 'workorderId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2769, '120', 'workorder_code', '工单编码', 'varchar(64)', 'String', 'workorderCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2770, '120', 'workorder_name', '工单名称', 'varchar(255)', 'String', 'workorderName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2771, '120', 'order_source', '来源类型', 'varchar(64)', 'String', 'orderSource', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'radio', 'mes_workorder_sourcetype', 4, 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2772, '120', 'source_code', '来源单据', 'varchar(64)', 'String', 'sourceCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-10-29 21:39:32', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2773, '120', 'product_id', '产品ID', 'bigint(20)', 'Long', 'productId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2774, '120', 'product_code', '产品编号', 'varchar(64)', 'String', 'productCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2775, '120', 'product_name', '产品名称', 'varchar(255)', 'String', 'productName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2776, '120', 'product_spc', '规格型号', 'varchar(255)', 'String', 'productSpc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2777, '120', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2778, '120', 'quantity', '外协数量', 'double(14,2)', 'BigDecimal', 'quantity', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2779, '120', 'quantity_produced', '已生产数量', 'double(14,2)', 'BigDecimal', 'quantityProduced', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2780, '120', 'quantity_changed', '调整数量', 'double(14,2)', 'BigDecimal', 'quantityChanged', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2781, '120', 'quantity_scheduled', '已排产数量', 'double(14,2)', 'BigDecimal', 'quantityScheduled', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2782, '120', 'client_id', '客户ID', 'bigint(20)', 'Long', 'clientId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2783, '120', 'client_code', '客户编码', 'varchar(64)', 'String', 'clientCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:02');
INSERT INTO `gen_table_column` VALUES (2784, '120', 'client_name', '客户名称', 'varchar(255)', 'String', 'clientName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 17, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2785, '120', 'vendor_id', '供应商ID', 'bigint(20)', 'Long', 'vendorId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2786, '120', 'vendor_code', '供应商编号', 'varchar(64)', 'String', 'vendorCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 19, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2787, '120', 'vendor_name', '供应商名称', 'varchar(255)', 'String', 'vendorName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 20, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2788, '120', 'batch_code', '批次号', 'varchar(64)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 21, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2789, '120', 'request_date', '需求日期', 'datetime', 'Date', 'requestDate', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'datetime', '', 22, 'admin', '2023-10-29 21:39:33', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2790, '120', 'parent_id', '父工单', 'bigint(20)', 'Long', 'parentId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 23, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2791, '120', 'ancestors', '所有父节点ID', 'varchar(500)', 'String', 'ancestors', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'textarea', '', 24, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2792, '120', 'finish_date', '完成时间', 'datetime', 'Date', 'finishDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 25, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2793, '120', 'status', '单据状态', 'varchar(64)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 26, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2794, '120', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 27, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2795, '120', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 28, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2796, '120', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 29, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2797, '120', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 30, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2798, '120', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 31, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2799, '120', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 32, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2800, '120', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 33, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2801, '120', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 34, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:03');
INSERT INTO `gen_table_column` VALUES (2802, '120', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 35, 'admin', '2023-10-29 21:39:34', '', '2023-10-29 21:41:04');
INSERT INTO `gen_table_column` VALUES (2803, '121', 'issue_id', '领料单ID', 'bigint(20)', 'Long', 'issueId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:31');
INSERT INTO `gen_table_column` VALUES (2804, '121', 'issue_code', '领料单编号', 'varchar(64)', 'String', 'issueCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2805, '121', 'issue_name', '领料单名称', 'varchar(255)', 'String', 'issueName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2806, '121', 'workorder_id', '生产工单ID', 'bigint(20)', 'Long', 'workorderId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2807, '121', 'workorder_code', '生产工单编码', 'varchar(64)', 'String', 'workorderCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2808, '121', 'vendor_id', '供应商ID', 'bigint(20)', 'Long', 'vendorId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2809, '121', 'vendor_code', '供应商编码', 'varchar(64)', 'String', 'vendorCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2810, '121', 'vendor_name', '供应商名称', 'varchar(255)', 'String', 'vendorName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2811, '121', 'vendor_nick', '供应商简称', 'varchar(255)', 'String', 'vendorNick', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2812, '121', 'warehouse_id', '仓库ID', 'bigint(20)', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2813, '121', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2814, '121', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 12, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2815, '121', 'location_id', '库区ID', 'bigint(20)', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-10-30 11:16:50', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2816, '121', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2817, '121', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2818, '121', 'area_id', '库位ID', 'bigint(20)', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:32');
INSERT INTO `gen_table_column` VALUES (2819, '121', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2820, '121', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 18, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2821, '121', 'issue_date', '领料日期', 'datetime', 'Date', 'issueDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 19, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2822, '121', 'status', '单据状态', 'varchar(64)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 20, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2823, '121', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 21, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2824, '121', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 22, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2825, '121', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 23, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2826, '121', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 24, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2827, '121', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 25, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2828, '121', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 26, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2829, '121', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 27, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2830, '121', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 28, 'admin', '2023-10-30 11:16:51', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2831, '121', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 29, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:19:33');
INSERT INTO `gen_table_column` VALUES (2832, '122', 'line_id', '行ID', 'bigint(20)', 'Long', 'lineId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43');
INSERT INTO `gen_table_column` VALUES (2833, '122', 'issue_id', '领料单ID', 'bigint(20)', 'Long', 'issueId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43');
INSERT INTO `gen_table_column` VALUES (2834, '122', 'material_stock_id', '库存ID', 'bigint(20)', 'Long', 'materialStockId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43');
INSERT INTO `gen_table_column` VALUES (2835, '122', 'item_id', '产品物料ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43');
INSERT INTO `gen_table_column` VALUES (2836, '122', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:43');
INSERT INTO `gen_table_column` VALUES (2837, '122', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2838, '122', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 7, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2839, '122', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2840, '122', 'quantity_issued', '领料数量', 'double(12,2)', 'BigDecimal', 'quantityIssued', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2841, '122', 'batch_code', '领料批次号', 'varchar(255)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2842, '122', 'warehouse_id', '仓库ID', 'bigint(20)', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2843, '122', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2844, '122', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 13, 'admin', '2023-10-30 11:16:52', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2845, '122', 'location_id', '库区ID', 'bigint(20)', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2846, '122', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2847, '122', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 16, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2848, '122', 'area_id', '库位ID', 'bigint(20)', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2849, '122', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2850, '122', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 19, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2851, '122', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 20, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:44');
INSERT INTO `gen_table_column` VALUES (2852, '122', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 21, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2853, '122', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 22, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2854, '122', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 23, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2855, '122', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 24, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2856, '122', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 25, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2857, '122', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 26, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2858, '122', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 27, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2859, '122', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 28, 'admin', '2023-10-30 11:16:53', '', '2023-10-30 11:26:45');
INSERT INTO `gen_table_column` VALUES (2860, '123', 'recpt_id', '入库单ID', 'bigint(20)', 'Long', 'recptId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2861, '123', 'recpt_code', '入库单编号', 'varchar(64)', 'String', 'recptCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2862, '123', 'recpt_name', '入库单名称', 'varchar(255)', 'String', 'recptName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2863, '123', 'iqc_id', '来料检验单ID', 'bigint(20)', 'Long', 'iqcId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2864, '123', 'iqc_code', '来料检验单编号', 'varchar(64)', 'String', 'iqcCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2865, '123', 'workorder_id', '外协工单ID', 'bigint(20)', 'Long', 'workorderId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2866, '123', 'workorder_code', '外协工单编号', 'varchar(64)', 'String', 'workorderCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:54');
INSERT INTO `gen_table_column` VALUES (2867, '123', 'vendor_id', '供应商ID', 'bigint(20)', 'Long', 'vendorId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-10-30 17:08:57', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2868, '123', 'vendor_code', '供应商编码', 'varchar(64)', 'String', 'vendorCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2869, '123', 'vendor_name', '供应商名称', 'varchar(255)', 'String', 'vendorName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 10, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2870, '123', 'vendor_nick', '供应商简称', 'varchar(255)', 'String', 'vendorNick', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2871, '123', 'warehouse_id', '仓库ID', 'bigint(20)', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2872, '123', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2873, '123', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 14, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2874, '123', 'location_id', '库区ID', 'bigint(20)', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2875, '123', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2876, '123', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 17, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2877, '123', 'area_id', '库位ID', 'bigint(20)', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2878, '123', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 19, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2879, '123', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 20, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2880, '123', 'recpt_date', '入库日期', 'datetime', 'Date', 'recptDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 21, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2881, '123', 'status', '单据状态', 'varchar(64)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 22, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2882, '123', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 23, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2883, '123', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 24, 'admin', '2023-10-30 17:08:58', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2884, '123', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 25, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:55');
INSERT INTO `gen_table_column` VALUES (2885, '123', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 26, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2886, '123', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 27, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2887, '123', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 28, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2888, '123', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 29, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2889, '123', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 30, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2890, '123', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 31, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:09:56');
INSERT INTO `gen_table_column` VALUES (2891, '124', 'line_id', '行ID', 'bigint(20)', 'Long', 'lineId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2892, '124', 'recpt_id', '入库单ID', 'bigint(20)', 'Long', 'recptId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2893, '124', 'item_id', '产品物料ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2894, '124', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2895, '124', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 5, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2896, '124', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 6, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2897, '124', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-10-30 17:08:59', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2898, '124', 'quantity_recived', '入库数量', 'double(12,2)', 'BigDecimal', 'quantityRecived', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2899, '124', 'batch_code', '入库批次号', 'varchar(255)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2900, '124', 'warehouse_id', '仓库ID', 'bigint(20)', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2901, '124', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:18');
INSERT INTO `gen_table_column` VALUES (2902, '124', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 12, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2903, '124', 'location_id', '库区ID', 'bigint(20)', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2904, '124', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2905, '124', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2906, '124', 'area_id', '库位ID', 'bigint(20)', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2907, '124', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2908, '124', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 18, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2909, '124', 'expire_date', '有效期', 'datetime', 'Date', 'expireDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 19, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2910, '124', 'iqc_check', '是否来料检验', 'char(1)', 'String', 'iqcCheck', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 20, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2911, '124', 'iqc_id', '来料检验单ID', 'bigint(20)', 'Long', 'iqcId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 21, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2912, '124', 'iqc_code', '来料检验单编号', 'varchar(64)', 'String', 'iqcCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 22, 'admin', '2023-10-30 17:09:00', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2913, '124', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 23, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2914, '124', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 24, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2915, '124', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 25, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2916, '124', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 26, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2917, '124', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 27, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2918, '124', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 28, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:19');
INSERT INTO `gen_table_column` VALUES (2919, '124', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 29, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:20');
INSERT INTO `gen_table_column` VALUES (2920, '124', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 30, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:20');
INSERT INTO `gen_table_column` VALUES (2921, '124', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 31, 'admin', '2023-10-30 17:09:01', '', '2023-10-30 17:13:20');
INSERT INTO `gen_table_column` VALUES (2984, '127', 'sip_id', '记录ID', 'bigint(20)', 'Long', 'sipId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2985, '127', 'item_id', '物料产品ID', 'bigint(20)', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2986, '127', 'order_num', '排列顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2987, '127', 'process_id', '对应的工序', 'bigint(20)', 'Long', 'processId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2988, '127', 'process_code', '工序编号', 'varchar(64)', 'String', 'processCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2989, '127', 'process_name', '工序名称', 'varchar(255)', 'String', 'processName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:30');
INSERT INTO `gen_table_column` VALUES (2990, '127', 'sip_title', '标题', 'varchar(255)', 'String', 'sipTitle', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2991, '127', 'sip_description', '详细描述', 'varchar(500)', 'String', 'sipDescription', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 8, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2992, '127', 'sip_url', '图片地址', 'varchar(255)', 'String', 'sipUrl', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2993, '127', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 10, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2994, '127', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 11, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2995, '127', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 12, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2996, '127', 'attr3', '预留字段3', 'int(11)', 'Long', 'attr3', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 13, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2997, '127', 'attr4', '预留字段4', 'int(11)', 'Long', 'attr4', '0', '0', NULL, NULL, NULL, NULL, NULL, 'EQ', 'input', '', 14, 'admin', '2023-10-31 17:26:51', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2998, '127', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 15, 'admin', '2023-10-31 17:26:52', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (2999, '127', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 16, 'admin', '2023-10-31 17:26:52', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (3000, '127', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 17, 'admin', '2023-10-31 17:26:52', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (3001, '127', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 18, 'admin', '2023-10-31 17:26:52', '', '2023-10-31 17:27:31');
INSERT INTO `gen_table_column` VALUES (3002, '128', 'dict_id', '字典主键', 'bigint', 'Long', 'dictId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33');
INSERT INTO `gen_table_column` VALUES (3003, '128', 'dict_name', '字典名称', 'varchar(100)', 'String', 'dictName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33');
INSERT INTO `gen_table_column` VALUES (3004, '128', 'dict_type', '字典类型', 'varchar(100)', 'String', 'dictType', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'select', '', 3, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33');
INSERT INTO `gen_table_column` VALUES (3005, '128', 'status', '状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'radio', '', 4, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33');
INSERT INTO `gen_table_column` VALUES (3006, '128', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:33');
INSERT INTO `gen_table_column` VALUES (3007, '128', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 6, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:34');
INSERT INTO `gen_table_column` VALUES (3008, '128', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 7, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:34');
INSERT INTO `gen_table_column` VALUES (3009, '128', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 8, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:34');
INSERT INTO `gen_table_column` VALUES (3010, '128', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 9, 'admin', '2024-04-22 13:14:47', '', '2024-04-22 13:45:34');
INSERT INTO `gen_table_column` VALUES (3011, '129', 'client_order_id', '客户订单id', 'bigint', 'Long', 'clientOrderId', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 1, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:45');
INSERT INTO `gen_table_column` VALUES (3012, '129', 'client_order_code', '订单编码', 'varchar(32)', 'String', 'clientOrderCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:45');
INSERT INTO `gen_table_column` VALUES (3013, '129', 'client_id', '客户id', 'bigint', 'Long', 'clientId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:45');
INSERT INTO `gen_table_column` VALUES (3014, '129', 'client_code', '客户编码', 'varchar(64)', 'String', 'clientCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:45');
INSERT INTO `gen_table_column` VALUES (3015, '129', 'client_name', '客户名称', 'varchar(255)', 'String', 'clientName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3016, '129', 'order_date', '订货日期', 'datetime', 'Date', 'orderDate', '0', '0', '1', '1', '1', '1', '1', 'BETWEEN', 'datetime', '', 9, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3017, '129', 'delivery_date', '交货日期', 'datetime', 'Date', 'deliveryDate', '0', '0', '1', '1', '1', '1', '1', 'BETWEEN', 'datetime', '', 10, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3018, '129', 'spec', '规格型号', 'varchar(255)', 'String', 'spec', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3019, '129', 'order_quantity', '订货数量', 'int', 'Long', 'orderQuantity', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3020, '129', 'measure_id', '单位id，默认：个', 'bigint', 'Long', 'measureId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3021, '129', 'measure_code', '单位编码', 'varchar(64)', 'String', 'measureCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3022, '129', 'measure_name', '单位名称', 'varchar(255)', 'String', 'measureName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 15, 'admin', '2024-05-01 06:29:09', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3023, '129', 'quality_requirement', '质量要求', 'varchar(255)', 'String', 'qualityRequirement', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 16, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:46');
INSERT INTO `gen_table_column` VALUES (3024, '129', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3025, '129', 'attr2', '预留字段2', 'varchar(64)', 'String', 'attr2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3026, '129', 'attr3', '预留字段3', 'int', 'Long', 'attr3', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 19, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3027, '129', 'attr4', '预留字段4', 'int', 'Long', 'attr4', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 20, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3028, '129', 'create_user_id', '创建人id', 'bigint', 'Long', 'createUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 21, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3029, '129', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 22, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3030, '129', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', NULL, 23, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3031, '129', 'update_user_id', '更新人id', 'bigint', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 24, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3032, '129', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', NULL, 25, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3033, '129', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', NULL, 26, 'admin', '2024-05-01 06:29:10', NULL, '2024-05-03 12:26:47');
INSERT INTO `gen_table_column` VALUES (3034, '129', 'product_id', '产品id', 'bigint', 'Long', 'productId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, '', '2024-05-03 12:26:45', '', NULL);
INSERT INTO `gen_table_column` VALUES (3035, '129', 'product_code', '产品编码', 'varchar(64)', 'String', 'productCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, '', '2024-05-03 12:26:45', '', NULL);
INSERT INTO `gen_table_column` VALUES (3036, '129', 'product_name', '产品名称', 'varchar(255)', 'String', 'productName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 5, '', '2024-05-03 12:26:45', '', NULL);
INSERT INTO `gen_table_column` VALUES (3037, '130', 'order_item_id', '主键', 'bigint', 'Long', 'orderItemId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:26');
INSERT INTO `gen_table_column` VALUES (3038, '130', 'client_order_id', '客户订单表id', 'bigint', 'Long', 'clientOrderId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:26');
INSERT INTO `gen_table_column` VALUES (3039, '130', 'item_id', '物料产品ID', 'bigint', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:26');
INSERT INTO `gen_table_column` VALUES (3040, '130', 'item_or_product', '产品物料标识', 'varchar(20)', 'String', 'itemOrProduct', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:26');
INSERT INTO `gen_table_column` VALUES (3041, '130', 'quantity', '物料使用数量', 'double(12,4)', 'BigDecimal', 'quantity', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3042, '130', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2024-05-07 11:56:40', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3043, '130', 'level', '层级，字典表：mes_item_level', 'char(1)', 'String', 'level', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3044, '130', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 8, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3045, '130', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3046, '130', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3047, '130', 'attr3', '预留字段3', 'int', 'Long', 'attr3', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3048, '130', 'attr4', '预留字段4', 'int', 'Long', 'attr4', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:27');
INSERT INTO `gen_table_column` VALUES (3049, '130', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 13, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3050, '130', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 14, 'admin', '2024-05-07 11:56:41', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3051, '130', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 15, 'admin', '2024-05-07 11:56:42', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3052, '130', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 16, 'admin', '2024-05-07 11:56:42', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3053, '130', 'create_user_id', '创建人id', 'bigint', 'Long', 'createUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2024-05-07 11:56:42', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3054, '130', 'update_user_id', '更新人id', 'bigint', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2024-05-07 11:56:42', '', '2024-05-07 12:05:28');
INSERT INTO `gen_table_column` VALUES (3080, '132', 'line_id', '行ID', 'bigint', 'Long', 'lineId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 1, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3081, '132', 'waste_id', '废料单ID', 'bigint', 'Long', 'wasteId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3082, '132', 'material_stock_id', '库存ID', 'bigint', 'Long', 'materialStockId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3083, '132', 'item_id', '产品物料ID', 'bigint', 'Long', 'itemId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3084, '132', 'item_code', '产品物料编码', 'varchar(64)', 'String', 'itemCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3085, '132', 'item_name', '产品物料名称', 'varchar(255)', 'String', 'itemName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3086, '132', 'specification', '规格型号', 'varchar(500)', 'String', 'specification', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 7, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3087, '132', 'unit_of_measure', '单位', 'varchar(64)', 'String', 'unitOfMeasure', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3088, '132', 'quantity_waste', '废料数量', 'double(12,2)', 'BigDecimal', 'quantityWaste', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3089, '132', 'batch_code', '领料批次号', 'varchar(255)', 'String', 'batchCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3090, '132', 'warehouse_id', '仓库ID', 'bigint', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3091, '132', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3092, '132', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 13, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3093, '132', 'location_id', '库区ID', 'bigint', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3094, '132', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3095, '132', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 16, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3096, '132', 'area_id', '库位ID', 'bigint', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 17, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3097, '132', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3098, '132', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 19, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3099, '132', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 20, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3100, '132', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 21, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3101, '132', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 22, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3102, '132', 'attr3', '预留字段3', 'int', 'Long', 'attr3', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 23, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3103, '132', 'attr4', '预留字段4', 'int', 'Long', 'attr4', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 24, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3104, '132', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 25, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:25');
INSERT INTO `gen_table_column` VALUES (3105, '132', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', NULL, 26, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:26');
INSERT INTO `gen_table_column` VALUES (3106, '132', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', NULL, 27, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:26');
INSERT INTO `gen_table_column` VALUES (3107, '132', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', NULL, 28, 'admin', '2024-05-10 05:40:49', NULL, '2024-05-11 06:33:26');
INSERT INTO `gen_table_column` VALUES (3135, '132', 'update_by_id', '更新人id', 'bigint', 'Long', 'updateById', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 29, '', '2024-05-11 06:33:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (3136, '132', 'create_by_id', '创建人id', 'bigint', 'Long', 'createById', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 30, '', '2024-05-11 06:33:26', '', NULL);
INSERT INTO `gen_table_column` VALUES (3137, '134', 'waste_id', '废料单ID', 'bigint', 'Long', 'wasteId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3138, '134', 'waste_code', '废料单编号', 'varchar(64)', 'String', 'wasteCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3139, '134', 'waste_name', '废料单名称', 'varchar(255)', 'String', 'wasteName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3140, '134', 'workorder_id', '生产工单ID', 'bigint', 'Long', 'workorderId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3141, '134', 'workorder_code', '生产工单编码', 'varchar(64)', 'String', 'workorderCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3142, '134', 'warehouse_id', '仓库ID', 'bigint', 'Long', 'warehouseId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3143, '134', 'warehouse_code', '仓库编码', 'varchar(64)', 'String', 'warehouseCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3144, '134', 'warehouse_name', '仓库名称', 'varchar(255)', 'String', 'warehouseName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3145, '134', 'location_id', '库区ID', 'bigint', 'Long', 'locationId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3146, '134', 'location_code', '库区编码', 'varchar(64)', 'String', 'locationCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3147, '134', 'location_name', '库区名称', 'varchar(255)', 'String', 'locationName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 11, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3148, '134', 'area_id', '库位ID', 'bigint', 'Long', 'areaId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2024-05-16 02:15:28', '', NULL);
INSERT INTO `gen_table_column` VALUES (3149, '134', 'area_code', '库位编码', 'varchar(64)', 'String', 'areaCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3150, '134', 'area_name', '库位名称', 'varchar(255)', 'String', 'areaName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 14, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3151, '134', 'waste_date', '废料日期', 'datetime', 'Date', 'wasteDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 15, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3152, '134', 'status', '单据状态', 'varchar(64)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 16, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3153, '134', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 17, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3154, '134', 'create_user_id', '创建人id', 'bigint', 'Long', 'createUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3155, '134', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 19, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3156, '134', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 20, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3157, '134', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 21, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3158, '134', 'update_user_id', '更新人id', 'bigint', 'Long', 'updateUserId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 22, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3159, '134', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 23, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3160, '134', 'attr1', '预留字段1', 'varchar(64)', 'String', 'attr1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 24, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3161, '134', 'attr2', '预留字段2', 'varchar(255)', 'String', 'attr2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 25, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3162, '134', 'attr3', '预留字段3', 'int', 'Long', 'attr3', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 26, 'admin', '2024-05-16 02:15:29', '', NULL);
INSERT INTO `gen_table_column` VALUES (3163, '134', 'attr4', '预留字段4', 'int', 'Long', 'attr4', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 27, 'admin', '2024-05-16 02:15:29', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for md_client
-- ----------------------------
DROP TABLE IF EXISTS `md_client`;
CREATE TABLE `md_client` (
  `client_id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `client_code` varchar(64) NOT NULL COMMENT '客户编码',
  `client_name` varchar(255) NOT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `client_en` varchar(255) DEFAULT NULL COMMENT '客户英文名称',
  `client_des` varchar(500) DEFAULT NULL COMMENT '客户简介',
  `client_logo` varchar(255) DEFAULT NULL COMMENT '客户LOGO地址',
  `client_type` varchar(64) DEFAULT 'ENTERPRISE' COMMENT '客户类型',
  `address` varchar(500) DEFAULT NULL COMMENT '客户地址',
  `website` varchar(255) DEFAULT NULL COMMENT '客户官网地址',
  `email` varchar(255) DEFAULT NULL COMMENT '客户邮箱地址',
  `tel` varchar(64) DEFAULT NULL COMMENT '客户电话',
  `contact1` varchar(64) DEFAULT NULL COMMENT '联系人1',
  `contact1_tel` varchar(64) DEFAULT NULL COMMENT '联系人1-电话',
  `contact1_email` varchar(255) DEFAULT NULL COMMENT '联系人1-邮箱',
  `contact2` varchar(64) DEFAULT NULL COMMENT '联系人2',
  `contact2_tel` varchar(64) DEFAULT NULL COMMENT '联系人2-电话',
  `contact2_email` varchar(255) DEFAULT NULL COMMENT '联系人2-邮箱',
  `credit_code` varchar(64) DEFAULT NULL COMMENT '统一社会信用代码',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';

-- ----------------------------
-- Table structure for md_item
-- ----------------------------
DROP TABLE IF EXISTS `md_item`;
CREATE TABLE `md_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `item_or_product` varchar(20) NOT NULL COMMENT '产品物料标识',
  `item_type_id` bigint DEFAULT '0' COMMENT '物料类型ID',
  `item_type_code` varchar(64) DEFAULT '' COMMENT '物料类型编码',
  `item_type_name` varchar(255) DEFAULT '' COMMENT '物料类型名称',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `safe_stock_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否设置安全库存',
  `min_stock` double(12,4) DEFAULT '0.0000' COMMENT '最低库存量',
  `max_stock` double(12,4) DEFAULT '0.0000' COMMENT '最大库存量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(64) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料产品表';

-- ----------------------------
-- Table structure for md_item_type
-- ----------------------------
DROP TABLE IF EXISTS `md_item_type`;
CREATE TABLE `md_item_type` (
  `item_type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品物料类型ID',
  `item_type_code` varchar(64) NOT NULL COMMENT '产品物料类型编码',
  `item_type_name` varchar(255) NOT NULL COMMENT '产品物料类型名称',
  `parent_type_id` bigint NOT NULL DEFAULT '0' COMMENT '父类型ID',
  `ancestors` varchar(255) NOT NULL COMMENT '所有层级父节点',
  `item_or_product` varchar(20) NOT NULL COMMENT '产品物料标识',
  `order_num` int DEFAULT '1' COMMENT '排列顺序',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`item_type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10000006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料产品分类表';

-- ----------------------------
-- Records of md_item_type
-- ----------------------------
BEGIN;
INSERT INTO `md_item_type` VALUES (10000000, 'WLCPFL_DEFAULT', '物料产品分类', 0, '0', 'PRODUCT', 1, 'Y', '', NULL, NULL, 0, 0, 'admin', '2022-04-27 16:32:09', 'admin', '2022-08-24 21:33:00', NULL, NULL);
INSERT INTO `md_item_type` VALUES (10000001, 'YCL_DEFAULT', '原材料', 10000000, '0,10000000', 'ITEM', 1, 'Y', '', NULL, NULL, 0, 0, 'admin', '2022-08-24 21:33:18', '', NULL, NULL, NULL);
INSERT INTO `md_item_type` VALUES (10000002, 'CP_DEFAULT', '产品', 10000000, '0,10000000', 'PRODUCT', 2, 'Y', '', NULL, NULL, 0, 0, 'admin', '2022-08-24 21:33:36', '', NULL, NULL, NULL);
INSERT INTO `md_item_type` VALUES (10000003, 'BCP_DEFAULT', '半成品', 10000002, '0,10000000,10000002', 'PRODUCT', 1, 'Y', '', NULL, NULL, 0, 0, 'admin', '2022-08-24 21:43:06', '', NULL, NULL, NULL);
INSERT INTO `md_item_type` VALUES (10000004, 'CCP_DEFAULT', '产成品', 10000002, '0,10000000,10000002', 'PRODUCT', 2, 'Y', '', NULL, NULL, 0, 0, 'admin', '2022-08-24 21:43:16', '', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for md_product_bom
-- ----------------------------
DROP TABLE IF EXISTS `md_product_bom`;
CREATE TABLE `md_product_bom` (
  `bom_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `item_id` bigint NOT NULL COMMENT '物料产品ID',
  `bom_item_id` bigint NOT NULL COMMENT 'BOM物料ID',
  `bom_item_code` varchar(64) NOT NULL COMMENT 'BOM物料编码',
  `bom_item_name` varchar(255) NOT NULL COMMENT 'BOM物料名称',
  `bom_item_spec` varchar(500) DEFAULT NULL COMMENT 'BOM物料规格',
  `unit_of_measure` varchar(64) NOT NULL COMMENT 'BOM物料单位',
  `item_or_product` varchar(20) NOT NULL COMMENT '产品物料标识',
  `level` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '层级，字典表：mes_item_level',
  `quantity` double(12,4) NOT NULL DEFAULT '0.0000' COMMENT '物料使用比例',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`bom_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品BOM关系表';

-- ----------------------------
-- Table structure for md_product_sip
-- ----------------------------
DROP TABLE IF EXISTS `md_product_sip`;
CREATE TABLE `md_product_sip` (
  `sip_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `item_id` bigint NOT NULL COMMENT '物料产品ID',
  `order_num` int DEFAULT NULL COMMENT '排列顺序',
  `process_id` bigint DEFAULT NULL COMMENT '对应的工序',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `sip_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sip_description` varchar(500) DEFAULT NULL COMMENT '详细描述',
  `sip_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品SIP表';

-- ----------------------------
-- Table structure for md_product_sop
-- ----------------------------
DROP TABLE IF EXISTS `md_product_sop`;
CREATE TABLE `md_product_sop` (
  `sop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `item_id` bigint NOT NULL COMMENT '物料产品ID',
  `order_num` int DEFAULT NULL COMMENT '排列顺序',
  `process_id` bigint DEFAULT NULL COMMENT '对应的工序',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `sop_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sop_description` varchar(500) DEFAULT NULL COMMENT '详细描述',
  `sop_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品SOP表';

-- ----------------------------
-- Table structure for md_produt_sop
-- ----------------------------
DROP TABLE IF EXISTS `md_produt_sop`;
CREATE TABLE `md_produt_sop` (
  `sop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `item_id` bigint NOT NULL COMMENT '物料产品ID',
  `order_num` int DEFAULT NULL COMMENT '排列顺序',
  `process_id` bigint DEFAULT NULL COMMENT '对应的工序',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `sop_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sop_description` varchar(500) DEFAULT NULL COMMENT '详细描述',
  `sop_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品SOP表';

-- ----------------------------
-- Table structure for md_unit_measure
-- ----------------------------
DROP TABLE IF EXISTS `md_unit_measure`;
CREATE TABLE `md_unit_measure` (
  `measure_id` bigint NOT NULL AUTO_INCREMENT COMMENT '单位ID',
  `measure_code` varchar(64) NOT NULL COMMENT '单位编码',
  `measure_name` varchar(255) NOT NULL COMMENT '单位名称',
  `primary_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否是主单位',
  `primary_id` bigint DEFAULT NULL COMMENT '主单位ID',
  `change_rate` double(12,4) DEFAULT NULL COMMENT '与主单位换算比例',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`measure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='单位表';

-- ----------------------------
-- Records of md_unit_measure
-- ----------------------------
BEGIN;
INSERT INTO `md_unit_measure` VALUES (200, 'KG', '公斤', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-04-27 21:52:19', '', '2022-08-05 17:12:50');
INSERT INTO `md_unit_measure` VALUES (201, 'g', '克', 'N', 200, 0.1000, 'Y', '', NULL, NULL, 0, 0, '', '2022-04-27 21:53:29', '', '2022-08-18 15:44:30');
INSERT INTO `md_unit_measure` VALUES (202, 'PCS', '个', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-04-27 21:54:13', '', NULL);
INSERT INTO `md_unit_measure` VALUES (203, 'CASE', '箱', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-04-27 21:55:14', '', NULL);
INSERT INTO `md_unit_measure` VALUES (204, 'm', '米', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-05-18 15:03:21', '', NULL);
INSERT INTO `md_unit_measure` VALUES (205, 'cm', '厘米', 'N', 204, 100.0000, 'Y', '', NULL, NULL, 0, 0, '', '2022-05-18 15:07:23', '', NULL);
INSERT INTO `md_unit_measure` VALUES (206, 'mm', '毫米', 'N', 204, 1000.0000, 'Y', '', NULL, NULL, 0, 0, '', '2022-05-18 15:07:42', '', NULL);
INSERT INTO `md_unit_measure` VALUES (214, 'T', '吨', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-17 11:16:18', '', NULL);
INSERT INTO `md_unit_measure` VALUES (216, 'p', '瓶', 'N', 203, 10.0000, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-18 14:11:57', '', '2022-08-18 14:12:23');
INSERT INTO `md_unit_measure` VALUES (217, 'x', '箱', 'Y', NULL, 0.0000, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-18 14:12:12', '', NULL);
INSERT INTO `md_unit_measure` VALUES (219, 'Nm', '公支', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-21 18:49:28', '', NULL);
INSERT INTO `md_unit_measure` VALUES (220, 'Ne', '英支', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-21 18:49:55', '', NULL);
INSERT INTO `md_unit_measure` VALUES (221, '匹', '匹', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-21 18:59:57', '', NULL);
INSERT INTO `md_unit_measure` VALUES (222, '捆', '捆', 'Y', NULL, NULL, 'Y', '', NULL, NULL, 0, 0, '', '2022-08-21 19:05:50', '', NULL);
INSERT INTO `md_unit_measure` VALUES (223, 'mg', '毫克', 'Y', 200, 0.0000, 'Y', '', NULL, NULL, 0, 0, '', '2022-09-27 10:17:16', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for md_vendor
-- ----------------------------
DROP TABLE IF EXISTS `md_vendor`;
CREATE TABLE `md_vendor` (
  `vendor_id` bigint NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `vendor_code` varchar(64) NOT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) NOT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `vendor_en` varchar(255) DEFAULT NULL COMMENT '供应商英文名称',
  `vendor_des` varchar(500) DEFAULT NULL COMMENT '供应商简介',
  `vendor_logo` varchar(255) DEFAULT NULL COMMENT '供应商LOGO地址',
  `vendor_level` varchar(64) DEFAULT NULL COMMENT '供应商等级',
  `vendor_score` int DEFAULT NULL COMMENT '供应商评分',
  `address` varchar(500) DEFAULT NULL COMMENT '供应商地址',
  `website` varchar(255) DEFAULT NULL COMMENT '供应商官网地址',
  `email` varchar(255) DEFAULT NULL COMMENT '供应商邮箱地址',
  `tel` varchar(64) DEFAULT NULL COMMENT '供应商电话',
  `contact1` varchar(64) DEFAULT NULL COMMENT '联系人1',
  `contact1_tel` varchar(64) DEFAULT NULL COMMENT '联系人1-电话',
  `contact1_email` varchar(255) DEFAULT NULL COMMENT '联系人1-邮箱',
  `contact2` varchar(64) DEFAULT NULL COMMENT '联系人2',
  `contact2_tel` varchar(64) DEFAULT NULL COMMENT '联系人2-电话',
  `contact2_email` varchar(255) DEFAULT NULL COMMENT '联系人2-邮箱',
  `credit_code` varchar(64) DEFAULT NULL COMMENT '统一社会信用代码',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商表';

-- ----------------------------
-- Table structure for md_workshop
-- ----------------------------
DROP TABLE IF EXISTS `md_workshop`;
CREATE TABLE `md_workshop` (
  `workshop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '车间ID',
  `workshop_code` varchar(64) NOT NULL COMMENT '车间编码',
  `workshop_name` varchar(255) NOT NULL COMMENT '车间名称',
  `area` double(12,2) DEFAULT NULL COMMENT '面积',
  `charge` varchar(64) DEFAULT NULL COMMENT '负责人',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`workshop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='车间表';

-- ----------------------------
-- Table structure for md_workstation
-- ----------------------------
DROP TABLE IF EXISTS `md_workstation`;
CREATE TABLE `md_workstation` (
  `workstation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工作站ID',
  `workstation_code` varchar(64) NOT NULL COMMENT '工作站编码',
  `workstation_name` varchar(255) NOT NULL COMMENT '工作站名称',
  `workstation_address` varchar(255) DEFAULT NULL COMMENT '工作站地点',
  `workshop_id` bigint DEFAULT NULL COMMENT '所在车间ID',
  `workshop_code` varchar(64) DEFAULT NULL COMMENT '所在车间编码',
  `workshop_name` varchar(255) DEFAULT NULL COMMENT '所在车间名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `warehouse_id` bigint NOT NULL COMMENT '线边库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '线边库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '线边库名称',
  `location_id` bigint NOT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint NOT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`workstation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工作站表';

-- ----------------------------
-- Table structure for md_workstation_machine
-- ----------------------------
DROP TABLE IF EXISTS `md_workstation_machine`;
CREATE TABLE `md_workstation_machine` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `machinery_id` bigint NOT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) DEFAULT NULL COMMENT '设备编码',
  `machinery_name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `quantity` int DEFAULT '1' COMMENT '数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备资源表';

-- ----------------------------
-- Table structure for md_workstation_tool
-- ----------------------------
DROP TABLE IF EXISTS `md_workstation_tool`;
CREATE TABLE `md_workstation_tool` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `tool_type_id` bigint NOT NULL COMMENT '工装夹具类型ID',
  `tool_type_code` varchar(64) DEFAULT NULL COMMENT '类型编码',
  `tool_type_name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工装夹具资源表';

-- ----------------------------
-- Table structure for md_workstation_worker
-- ----------------------------
DROP TABLE IF EXISTS `md_workstation_worker`;
CREATE TABLE `md_workstation_worker` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  `post_code` varchar(64) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(255) DEFAULT NULL COMMENT '岗位名称',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人力资源表';

-- ----------------------------
-- Table structure for print_printer_config
-- ----------------------------
DROP TABLE IF EXISTS `print_printer_config`;
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
  PRIMARY KEY (`printer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打印机配置';

-- ----------------------------
-- Table structure for print_template
-- ----------------------------
DROP TABLE IF EXISTS `print_template`;
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
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打印模板配置';

-- ----------------------------
-- Table structure for pro_andon_record
-- ----------------------------
DROP TABLE IF EXISTS `pro_andon_record`;
CREATE TABLE `pro_andon_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(125) DEFAULT NULL COMMENT '工作站名称',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(125) DEFAULT NULL COMMENT '名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `andon_reason` varchar(500) NOT NULL COMMENT '呼叫原因',
  `andon_level` varchar(64) DEFAULT 'LEVEL3' COMMENT '级别',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `status` varchar(64) DEFAULT 'ACTIVE' COMMENT '激活中',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安灯呼叫记录';

-- ----------------------------
-- Table structure for pro_client_order
-- ----------------------------
DROP TABLE IF EXISTS `pro_client_order`;
CREATE TABLE `pro_client_order` (
  `client_order_id` bigint NOT NULL COMMENT '客户订单id',
  `client_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编码',
  `workorder_id` bigint DEFAULT NULL COMMENT '工单ID',
  `workorder_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单编码',
  `workorder_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单名称',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `product_code` varchar(64) NOT NULL COMMENT '产品编码',
  `product_name` varchar(255) NOT NULL COMMENT '产品名称',
  `product_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品规格',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '单据状态',
  `client_id` bigint NOT NULL COMMENT '客户id',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) NOT NULL COMMENT '客户名称',
  `order_date` datetime NOT NULL COMMENT '订货日期',
  `delivery_date` datetime NOT NULL COMMENT '交货日期',
  `spec` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `order_quantity` int NOT NULL COMMENT '订货数量',
  `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '单位',
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
  PRIMARY KEY (`client_order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户订单';

-- ----------------------------
-- Table structure for pro_client_order_item
-- ----------------------------
DROP TABLE IF EXISTS `pro_client_order_item`;
CREATE TABLE `pro_client_order_item` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_order_id` bigint NOT NULL COMMENT '客户订单表id',
  `workorder_id` bigint DEFAULT NULL COMMENT '工单ID',
  `workorder_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单编码',
  `workorder_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单名称',
  `item_id` bigint NOT NULL COMMENT '物料产品ID',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `quantity` double(12,4) NOT NULL DEFAULT '0.0000' COMMENT '物料使用数量',
  `level` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '层级，字典表：mes_item_level',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户订单材料表\n';

-- ----------------------------
-- Table structure for pro_feedback
-- ----------------------------
DROP TABLE IF EXISTS `pro_feedback`;
CREATE TABLE `pro_feedback` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `feedback_type` varchar(64) NOT NULL COMMENT '报工类型',
  `feedback_code` varchar(64) DEFAULT NULL COMMENT '报工单编号',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `workorder_id` bigint NOT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `route_id` bigint NOT NULL COMMENT '工艺流程ID',
  `route_code` varchar(64) DEFAULT NULL COMMENT '工艺流程编号',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `task_id` bigint DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '生产任务编号',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `quantity` double(14,2) DEFAULT NULL COMMENT '排产数量',
  `quantity_feedback` double(14,2) DEFAULT NULL COMMENT '本次报工数量',
  `quantity_qualified` double(14,2) DEFAULT NULL COMMENT '合格品数量',
  `quantity_unquanlified` double(14,2) DEFAULT NULL COMMENT '不良品数量',
  `quantity_uncheck` double(14,2) DEFAULT NULL COMMENT '待检测数量',
  `user_id` bigint DEFAULT NULL COMMENT '报工人id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '报工用户名',
  `client_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户订单编码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `feedback_channel` varchar(64) DEFAULT NULL COMMENT '报工途径',
  `feedback_time` datetime DEFAULT NULL COMMENT '报工时间',
  `record_user_id` bigint DEFAULT NULL COMMENT '审批人id',
  `record_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审批人',
  `record_nick` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审批人名称',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产报工记录表';

-- ----------------------------
-- Table structure for pro_process
-- ----------------------------
DROP TABLE IF EXISTS `pro_process`;
CREATE TABLE `pro_process` (
  `process_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工序ID',
  `process_code` varchar(64) NOT NULL COMMENT '工序编码',
  `process_name` varchar(255) NOT NULL COMMENT '工序名称',
  `attention` varchar(1000) DEFAULT NULL COMMENT '工艺要求',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工序表';

-- ----------------------------
-- Table structure for pro_process_content
-- ----------------------------
DROP TABLE IF EXISTS `pro_process_content`;
CREATE TABLE `pro_process_content` (
  `content_id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `order_num` int DEFAULT '0' COMMENT '顺序编号',
  `content_text` varchar(500) DEFAULT NULL COMMENT '内容说明',
  `device` varchar(255) DEFAULT NULL COMMENT '辅助设备',
  `material` varchar(255) DEFAULT NULL COMMENT '辅助材料',
  `doc_url` varchar(255) DEFAULT NULL COMMENT '材料URL',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工序内容表';

-- ----------------------------
-- Table structure for pro_route
-- ----------------------------
DROP TABLE IF EXISTS `pro_route`;
CREATE TABLE `pro_route` (
  `route_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺路线ID',
  `route_code` varchar(64) NOT NULL COMMENT '工艺路线编号',
  `route_name` varchar(255) NOT NULL COMMENT '工艺路线名称',
  `route_desc` varchar(500) DEFAULT NULL COMMENT '工艺路线说明',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺路线表';

-- ----------------------------
-- Table structure for pro_route_process
-- ----------------------------
DROP TABLE IF EXISTS `pro_route_process`;
CREATE TABLE `pro_route_process` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `route_id` bigint NOT NULL COMMENT '工艺路线ID',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `order_num` int DEFAULT '1' COMMENT '序号',
  `next_process_id` bigint NOT NULL COMMENT '工序ID',
  `next_process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `next_process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `link_type` varchar(64) DEFAULT 'SS' COMMENT '与下一道工序关系',
  `default_pre_time` int DEFAULT '0' COMMENT '准备时间',
  `default_suf_time` int DEFAULT '0' COMMENT '等待时间',
  `color_code` char(7) DEFAULT '#00AEF3' COMMENT '甘特图显示颜色',
  `key_flag` varchar(64) DEFAULT 'N' COMMENT '关键工序',
  `is_check` char(1) DEFAULT 'N' COMMENT '是否检验',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺组成表';

-- ----------------------------
-- Table structure for pro_route_product
-- ----------------------------
DROP TABLE IF EXISTS `pro_route_product`;
CREATE TABLE `pro_route_product` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `route_id` bigint NOT NULL COMMENT '工艺路线ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity` int DEFAULT '1' COMMENT '生产数量',
  `production_time` double(12,2) DEFAULT '1.00' COMMENT '生产用时',
  `time_unit_type` varchar(64) DEFAULT 'MINUTE' COMMENT '时间单位',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品制程';

-- ----------------------------
-- Table structure for pro_route_product_bom
-- ----------------------------
DROP TABLE IF EXISTS `pro_route_product_bom`;
CREATE TABLE `pro_route_product_bom` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `route_id` bigint NOT NULL COMMENT '工艺路线ID',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `product_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity` double(12,2) DEFAULT '1.00' COMMENT '用料比例',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品制程物料BOM表';

-- ----------------------------
-- Table structure for pro_shutdown_record
-- ----------------------------
DROP TABLE IF EXISTS `pro_shutdown_record`;
CREATE TABLE `pro_shutdown_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(125) DEFAULT NULL COMMENT '工作站名称',
  `machinery_id` bigint DEFAULT NULL COMMENT '设备ID',
  `machinery_code` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `machinery_name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `shutdown_reason` varchar(500) NOT NULL COMMENT '停机原因',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='停机记录记录';

-- ----------------------------
-- Table structure for pro_task
-- ----------------------------
DROP TABLE IF EXISTS `pro_task`;
CREATE TABLE `pro_task` (
  `task_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `task_code` varchar(64) NOT NULL COMMENT '任务编号',
  `task_name` varchar(255) NOT NULL COMMENT '任务名称',
  `workorder_id` bigint NOT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) NOT NULL COMMENT '生产工单编号',
  `workorder_name` varchar(255) NOT NULL COMMENT '工单名称',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) NOT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) NOT NULL COMMENT '工作站名称',
  `route_id` bigint NOT NULL COMMENT '工艺ID',
  `route_code` varchar(64) DEFAULT NULL COMMENT '工艺编号',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity` double(14,2) NOT NULL DEFAULT '1.00' COMMENT '排产数量',
  `quantity_produced` double(14,2) DEFAULT '0.00' COMMENT '已生产数量',
  `quantity_quanlify` double(14,2) DEFAULT '0.00' COMMENT '合格品数量',
  `quantity_unquanlify` double(14,2) DEFAULT '0.00' COMMENT '不良品数量',
  `quantity_changed` double(14,2) DEFAULT '0.00' COMMENT '调整数量',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始生产时间',
  `duration` int DEFAULT '1' COMMENT '生产时长',
  `end_time` datetime DEFAULT NULL COMMENT '完成生产时间',
  `color_code` char(7) DEFAULT '#00AEF3' COMMENT '甘特图显示颜色',
  `request_date` datetime DEFAULT NULL COMMENT '需求日期',
  `status` varchar(64) DEFAULT 'NORMARL' COMMENT '生产状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产任务表';

-- ----------------------------
-- Table structure for pro_task_issue
-- ----------------------------
DROP TABLE IF EXISTS `pro_task_issue`;
CREATE TABLE `pro_task_issue` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `task_id` bigint NOT NULL COMMENT '生产任务ID',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
  `source_doc_id` bigint NOT NULL COMMENT '单据ID',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '单据编号',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '单据类型',
  `batch_code` varchar(64) DEFAULT NULL COMMENT '投料批次',
  `source_line_id` bigint DEFAULT NULL COMMENT '行ID',
  `item_id` bigint DEFAULT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity_issued` double(12,2) DEFAULT NULL COMMENT '总的投料数量',
  `quantity_available` double(12,2) DEFAULT NULL COMMENT '当前可用数量',
  `quantity_used` double(12,2) DEFAULT NULL COMMENT '当前使用数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产任务投料表';

-- ----------------------------
-- Table structure for pro_trans_consume
-- ----------------------------
DROP TABLE IF EXISTS `pro_trans_consume`;
CREATE TABLE `pro_trans_consume` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `trans_order_id` bigint DEFAULT NULL COMMENT '流转单ID',
  `trans_order_code` varchar(64) DEFAULT NULL COMMENT '流转单编号',
  `task_id` bigint NOT NULL COMMENT '生产任务ID',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `batch_code` varchar(64) DEFAULT NULL COMMENT '批次号',
  `source_doc_id` bigint DEFAULT NULL COMMENT '被消耗单据ID',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '被消耗单据编号',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '被消耗单据类型',
  `source_line_id` bigint DEFAULT NULL COMMENT '被消耗单据行ID',
  `source_batch_code` varchar(64) DEFAULT NULL COMMENT '被消耗物料批次号',
  `item_id` bigint DEFAULT NULL COMMENT '被消耗产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '被消耗产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '被消耗产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity_consumed` double(12,2) DEFAULT NULL COMMENT '消耗数量',
  `consume_date` datetime DEFAULT NULL COMMENT '消耗时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料消耗记录表';

-- ----------------------------
-- Table structure for pro_trans_order
-- ----------------------------
DROP TABLE IF EXISTS `pro_trans_order`;
CREATE TABLE `pro_trans_order` (
  `trans_order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流转单ID',
  `trans_order_code` varchar(64) DEFAULT NULL COMMENT '流转单编号',
  `task_id` bigint NOT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '生产任务编号',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `batch_code` varchar(64) DEFAULT NULL COMMENT '批次号',
  `item_id` bigint DEFAULT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity_transfered` double(12,2) DEFAULT NULL COMMENT '流转数量',
  `produce_date` datetime DEFAULT NULL COMMENT '生产日期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `barcode_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`trans_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='流转单表';

-- ----------------------------
-- Table structure for pro_user_workstation
-- ----------------------------
DROP TABLE IF EXISTS `pro_user_workstation`;
CREATE TABLE `pro_user_workstation` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `nick_name` bigint DEFAULT NULL COMMENT '名称',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(125) DEFAULT NULL COMMENT '工作站名称',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户工作站绑定关系';

-- ----------------------------
-- Table structure for pro_workorder
-- ----------------------------
DROP TABLE IF EXISTS `pro_workorder`;
CREATE TABLE `pro_workorder` (
  `workorder_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `workorder_code` varchar(64) NOT NULL COMMENT '工单编码',
  `workorder_name` varchar(255) NOT NULL COMMENT '工单名称',
  `client_order_id` bigint DEFAULT NULL COMMENT '客户订单id',
  `client_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单编码',
  `order_source` varchar(64) NOT NULL COMMENT '来源类型',
  `source_code` varchar(64) DEFAULT NULL COMMENT '来源单据',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `product_code` varchar(64) NOT NULL COMMENT '产品编号',
  `product_name` varchar(255) NOT NULL COMMENT '产品名称',
  `product_spc` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `quantity` double(14,2) NOT NULL DEFAULT '0.00' COMMENT '生产数量',
  `quantity_produced` double(14,2) DEFAULT '0.00' COMMENT '已生产数量',
  `quantity_changed` double(14,2) DEFAULT '0.00' COMMENT '调整数量',
  `quantity_scheduled` double(14,2) DEFAULT '0.00' COMMENT '已排产数量',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `request_date` datetime NOT NULL COMMENT '需求日期',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父工单',
  `ancestors` varchar(500) NOT NULL COMMENT '所有父节点ID',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `batch_code` varchar(64) DEFAULT NULL,
  `finish_date` datetime DEFAULT NULL COMMENT '完成时间',
  `workorder_type` varchar(64) DEFAULT 'SELF',
  `vendor_id` bigint DEFAULT NULL,
  `vendor_code` varchar(64) DEFAULT NULL,
  `vendor_name` varchar(255) DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`workorder_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工单表';

-- ----------------------------
-- Table structure for pro_workorder_bom
-- ----------------------------
DROP TABLE IF EXISTS `pro_workorder_bom`;
CREATE TABLE `pro_workorder_bom` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'BOM行ID',
  `workorder_id` bigint NOT NULL COMMENT '生产工单ID',
  `item_id` bigint NOT NULL COMMENT 'BOM物料ID',
  `item_code` varchar(64) NOT NULL COMMENT 'BOM物料编号',
  `item_name` varchar(255) NOT NULL COMMENT 'BOM物料名称',
  `item_spc` varchar(255) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) NOT NULL COMMENT '单位',
  `item_or_product` varchar(20) NOT NULL COMMENT '物料产品标识',
  `quantity` double(14,2) NOT NULL DEFAULT '0.00' COMMENT '预计使用量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`line_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工单BOM组成表';

-- ----------------------------
-- Table structure for pro_workrecord
-- ----------------------------
DROP TABLE IF EXISTS `pro_workrecord`;
CREATE TABLE `pro_workrecord` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `nick_name` bigint DEFAULT NULL COMMENT '名称',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(125) DEFAULT NULL COMMENT '工作站名称',
  `operation_flag` char(1) NOT NULL COMMENT '操作类型',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='上下工记录表';

-- ----------------------------
-- Table structure for qc_defect
-- ----------------------------
DROP TABLE IF EXISTS `qc_defect`;
CREATE TABLE `qc_defect` (
  `defect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '缺陷ID',
  `defect_code` varchar(64) NOT NULL COMMENT '缺陷编码',
  `defect_name` varchar(500) NOT NULL COMMENT '缺陷描述',
  `index_type` varchar(64) NOT NULL COMMENT '检测项类型',
  `defect_level` varchar(64) NOT NULL COMMENT '缺陷等级',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`defect_id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='常见缺陷表';

-- ----------------------------
-- Table structure for qc_defect_record
-- ----------------------------
DROP TABLE IF EXISTS `qc_defect_record`;
CREATE TABLE `qc_defect_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '缺陷ID',
  `qc_type` varchar(64) NOT NULL COMMENT '检验单类型',
  `qc_id` bigint NOT NULL COMMENT '检验单ID',
  `line_id` bigint NOT NULL COMMENT '检验单行ID',
  `defect_name` varchar(500) NOT NULL COMMENT '缺陷描述',
  `defect_level` varchar(64) NOT NULL COMMENT '缺陷等级',
  `defect_quantity` int DEFAULT '1' COMMENT '缺陷数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检验单缺陷记录表';

-- ----------------------------
-- Table structure for qc_index
-- ----------------------------
DROP TABLE IF EXISTS `qc_index`;
CREATE TABLE `qc_index` (
  `index_id` bigint NOT NULL AUTO_INCREMENT COMMENT '检测项ID',
  `index_code` varchar(64) NOT NULL COMMENT '检测项编码',
  `index_name` varchar(255) NOT NULL COMMENT '检测项名称',
  `index_type` varchar(64) NOT NULL COMMENT '检测项类型',
  `qc_tool` varchar(255) DEFAULT NULL COMMENT '检测工具',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`index_id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检测项表';

-- ----------------------------
-- Table structure for qc_ipqc
-- ----------------------------
DROP TABLE IF EXISTS `qc_ipqc`;
CREATE TABLE `qc_ipqc` (
  `ipqc_id` bigint NOT NULL AUTO_INCREMENT COMMENT '检验单ID',
  `ipqc_code` varchar(64) NOT NULL COMMENT '检验单编号',
  `ipqc_name` varchar(255) DEFAULT NULL COMMENT '检验单名称',
  `ipqc_type` varchar(64) NOT NULL COMMENT '检验类型',
  `template_id` bigint NOT NULL COMMENT '检验模板ID',
  `source_doc_id` bigint DEFAULT NULL COMMENT '来源单据ID',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '来源单据类型',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '来源单据编号',
  `source_line_id` bigint DEFAULT NULL COMMENT '来源单据行ID',
  `workorder_id` bigint NOT NULL COMMENT '工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '工单编码',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '工单名称',
  `task_id` bigint DEFAULT NULL COMMENT '任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '任务编号',
  `task_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `workstation_id` bigint NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_check` double(12,4) DEFAULT '1.0000' COMMENT '检测数量',
  `quantity_unqualified` double(12,4) DEFAULT '0.0000' COMMENT '不合格数',
  `quantity_qualified` double(12,4) DEFAULT NULL COMMENT '合格品数量',
  `cr_rate` double(12,2) DEFAULT '0.00' COMMENT '致命缺陷率',
  `maj_rate` double(12,2) DEFAULT '0.00' COMMENT '严重缺陷率',
  `min_rate` double(12,2) DEFAULT '0.00' COMMENT '轻微缺陷率',
  `cr_quantity` double(12,4) DEFAULT '0.0000' COMMENT '致命缺陷数量',
  `maj_quantity` double(12,4) DEFAULT '0.0000' COMMENT '严重缺陷数量',
  `min_quantity` double(12,4) DEFAULT '0.0000' COMMENT '轻微缺陷数量',
  `check_result` varchar(64) DEFAULT NULL COMMENT '检测结果',
  `inspect_date` datetime DEFAULT NULL COMMENT '检测日期',
  `inspector` varchar(64) DEFAULT NULL COMMENT '检测人员',
  `status` varchar(64) DEFAULT NULL COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ipqc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='过程检验单表';

-- ----------------------------
-- Table structure for qc_ipqc_line
-- ----------------------------
DROP TABLE IF EXISTS `qc_ipqc_line`;
CREATE TABLE `qc_ipqc_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `ipqc_id` bigint NOT NULL COMMENT '检验单ID',
  `index_id` bigint NOT NULL COMMENT '检测项ID',
  `index_code` varchar(64) DEFAULT NULL COMMENT '检测项编码',
  `index_name` varchar(255) DEFAULT NULL COMMENT '检测项名称',
  `index_type` varchar(64) DEFAULT NULL COMMENT '检测项类型',
  `qc_tool` varchar(255) DEFAULT NULL COMMENT '检测工具',
  `check_method` varchar(500) DEFAULT NULL COMMENT '检测要求',
  `stander_val` double(12,4) DEFAULT NULL COMMENT '标准值',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `threshold_max` double(12,4) DEFAULT NULL COMMENT '误差上限',
  `threshold_min` double(12,4) DEFAULT NULL COMMENT '误差下限',
  `cr_quantity` double(12,4) DEFAULT '0.0000' COMMENT '致命缺陷数量',
  `maj_quantity` double(12,4) DEFAULT '0.0000' COMMENT '严重缺陷数量',
  `min_quantity` double(12,4) DEFAULT '0.0000' COMMENT '轻微缺陷数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='过程检验单行表';

-- ----------------------------
-- Table structure for qc_iqc
-- ----------------------------
DROP TABLE IF EXISTS `qc_iqc`;
CREATE TABLE `qc_iqc` (
  `iqc_id` bigint NOT NULL AUTO_INCREMENT COMMENT '来料检验单ID',
  `iqc_code` varchar(64) NOT NULL COMMENT '来料检验单编号',
  `iqc_name` varchar(500) NOT NULL COMMENT '来料检验单名称',
  `template_id` bigint NOT NULL COMMENT '检验模板ID',
  `source_doc_id` bigint DEFAULT NULL COMMENT '来源单据ID',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '来源单据类型',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '来源单据编号',
  `source_line_id` bigint DEFAULT NULL COMMENT '来源单据行ID',
  `vendor_id` bigint NOT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) NOT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) NOT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `vendor_batch` varchar(64) DEFAULT NULL COMMENT '供应商批次号',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_min_check` int DEFAULT '1' COMMENT '最低检测数',
  `quantity_max_unqualified` int DEFAULT '0' COMMENT '最大不合格数',
  `quantity_recived` double(12,2) NOT NULL COMMENT '本次接收数量',
  `quantity_check` int NOT NULL COMMENT '本次检测数量',
  `quantity_unqualified` int DEFAULT '0' COMMENT '不合格数',
  `cr_rate` double(12,2) DEFAULT '0.00' COMMENT '致命缺陷率',
  `maj_rate` double(12,2) DEFAULT '0.00' COMMENT '严重缺陷率',
  `min_rate` double(12,2) DEFAULT '0.00' COMMENT '轻微缺陷率',
  `cr_quantity` int DEFAULT '0' COMMENT '致命缺陷数量',
  `maj_quantity` int DEFAULT '0' COMMENT '严重缺陷数量',
  `min_quantity` int DEFAULT '0' COMMENT '轻微缺陷数量',
  `check_result` varchar(64) DEFAULT NULL COMMENT '检测结果',
  `recive_date` datetime DEFAULT NULL COMMENT '来料日期',
  `inspect_date` datetime DEFAULT NULL COMMENT '检测日期',
  `inspector` varchar(64) DEFAULT NULL COMMENT '检测人员',
  `status` varchar(64) DEFAULT NULL COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`iqc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='来料检验单表';

-- ----------------------------
-- Table structure for qc_iqc_defect
-- ----------------------------
DROP TABLE IF EXISTS `qc_iqc_defect`;
CREATE TABLE `qc_iqc_defect` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '缺陷ID',
  `iqc_id` bigint NOT NULL COMMENT '来料检验单ID',
  `line_id` bigint NOT NULL COMMENT '来料检验单行ID',
  `defect_name` varchar(500) NOT NULL COMMENT '缺陷描述',
  `defect_level` varchar(64) NOT NULL COMMENT '缺陷等级',
  `defect_quantity` int DEFAULT '1' COMMENT '缺陷数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='来料检验单缺陷记录表';

-- ----------------------------
-- Table structure for qc_iqc_line
-- ----------------------------
DROP TABLE IF EXISTS `qc_iqc_line`;
CREATE TABLE `qc_iqc_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `iqc_id` bigint NOT NULL COMMENT '检验单ID',
  `index_id` bigint NOT NULL COMMENT '检测项ID',
  `index_code` varchar(64) DEFAULT NULL COMMENT '检测项编码',
  `index_name` varchar(255) DEFAULT NULL COMMENT '检测项名称',
  `index_type` varchar(64) DEFAULT NULL COMMENT '检测项类型',
  `qc_tool` varchar(255) DEFAULT NULL COMMENT '检测工具',
  `check_method` varchar(500) DEFAULT NULL COMMENT '检测要求',
  `stander_val` double(12,4) DEFAULT NULL COMMENT '标准值',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `threshold_max` double(12,4) DEFAULT NULL COMMENT '误差上限',
  `threshold_min` double(12,4) DEFAULT NULL COMMENT '误差下限',
  `cr_quantity` int DEFAULT '0' COMMENT '致命缺陷数量',
  `maj_quantity` int DEFAULT '0' COMMENT '严重缺陷数量',
  `min_quantity` int DEFAULT '0' COMMENT '轻微缺陷数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='来料检验单行表';

-- ----------------------------
-- Table structure for qc_oqc
-- ----------------------------
DROP TABLE IF EXISTS `qc_oqc`;
CREATE TABLE `qc_oqc` (
  `oqc_id` bigint NOT NULL AUTO_INCREMENT COMMENT '出货检验单ID',
  `oqc_code` varchar(64) NOT NULL COMMENT '出货检验单编号',
  `oqc_name` varchar(500) DEFAULT NULL COMMENT '出货检验单名称',
  `template_id` bigint NOT NULL COMMENT '检验模板ID',
  `source_doc_id` bigint DEFAULT NULL COMMENT '来源单据ID',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '来源单据类型',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '来源单据编号',
  `source_line_id` bigint DEFAULT NULL COMMENT '来源单据行ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `client_code` varchar(64) NOT NULL COMMENT '客户编码',
  `client_name` varchar(255) NOT NULL COMMENT '客户名称',
  `batch_code` varchar(64) DEFAULT NULL COMMENT '批次号',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_min_check` double(12,4) DEFAULT '1.0000' COMMENT '最低检测数',
  `quantity_max_unqualified` double(12,4) DEFAULT '0.0000' COMMENT '最大不合格数',
  `quantity_out` double(12,4) NOT NULL COMMENT '发货数量',
  `quantity_check` double(12,4) NOT NULL COMMENT '本次检测数量',
  `quantity_unqualified` double(12,4) DEFAULT '0.0000' COMMENT '不合格数',
  `quantity_quanlified` double(12,4) DEFAULT '0.0000' COMMENT '合格数量',
  `cr_rate` double(12,4) DEFAULT '0.0000' COMMENT '致命缺陷率',
  `maj_rate` double(12,4) DEFAULT '0.0000' COMMENT '严重缺陷率',
  `min_rate` double(12,4) DEFAULT '0.0000' COMMENT '轻微缺陷率',
  `cr_quantity` double(12,4) DEFAULT '0.0000' COMMENT '致命缺陷数量',
  `maj_quantity` double(12,4) DEFAULT '0.0000' COMMENT '严重缺陷数量',
  `min_quantity` double(12,4) DEFAULT '0.0000' COMMENT '轻微缺陷数量',
  `check_result` varchar(64) DEFAULT NULL COMMENT '检测结果',
  `out_date` datetime DEFAULT NULL COMMENT '出货日期',
  `inspect_date` datetime DEFAULT NULL COMMENT '检测日期',
  `inspector` varchar(64) DEFAULT NULL COMMENT '检测人员',
  `status` varchar(64) DEFAULT NULL COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`oqc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货检验单表';

-- ----------------------------
-- Table structure for qc_oqc_line
-- ----------------------------
DROP TABLE IF EXISTS `qc_oqc_line`;
CREATE TABLE `qc_oqc_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `oqc_id` bigint NOT NULL COMMENT '检验单ID',
  `index_id` bigint NOT NULL COMMENT '检测项ID',
  `index_code` varchar(64) DEFAULT NULL COMMENT '检测项编码',
  `index_name` varchar(255) DEFAULT NULL COMMENT '检测项名称',
  `index_type` varchar(64) DEFAULT NULL COMMENT '检测项类型',
  `qc_tool` varchar(255) DEFAULT NULL COMMENT '检测工具',
  `check_method` varchar(500) DEFAULT NULL COMMENT '检测要求',
  `stander_val` double(12,4) DEFAULT NULL COMMENT '标准值',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `threshold_max` double(12,4) DEFAULT NULL COMMENT '误差上限',
  `threshold_min` double(12,4) DEFAULT NULL COMMENT '误差下限',
  `cr_quantity` double(12,4) DEFAULT '0.0000' COMMENT '致命缺陷数量',
  `maj_quantity` double(12,4) DEFAULT '0.0000' COMMENT '严重缺陷数量',
  `min_quantity` double(12,4) DEFAULT '0.0000' COMMENT '轻微缺陷数量',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货检验单行表';

-- ----------------------------
-- Table structure for qc_template
-- ----------------------------
DROP TABLE IF EXISTS `qc_template`;
CREATE TABLE `qc_template` (
  `template_id` bigint NOT NULL AUTO_INCREMENT COMMENT '检测模板ID',
  `template_code` varchar(64) NOT NULL COMMENT '检测模板编号',
  `template_name` varchar(255) NOT NULL COMMENT '检测模板名称',
  `qc_types` varchar(255) NOT NULL COMMENT '检测种类',
  `enable_flag` char(1) DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检测模板表';

-- ----------------------------
-- Table structure for qc_template_index
-- ----------------------------
DROP TABLE IF EXISTS `qc_template_index`;
CREATE TABLE `qc_template_index` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `template_id` bigint NOT NULL COMMENT '检测模板ID',
  `index_id` bigint NOT NULL COMMENT '检测项ID',
  `index_code` varchar(64) NOT NULL COMMENT '检测项编码',
  `index_name` varchar(255) NOT NULL COMMENT '检测项名称',
  `index_type` varchar(64) NOT NULL COMMENT '检测项类型',
  `qc_tool` varchar(255) DEFAULT NULL COMMENT '检测工具',
  `check_method` varchar(500) DEFAULT NULL COMMENT '检测方法',
  `stander_val` double(12,4) DEFAULT NULL COMMENT '标准值',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `threshold_max` double(12,4) DEFAULT NULL COMMENT '误差上限',
  `threshold_min` double(12,4) DEFAULT NULL COMMENT '误差下限',
  `doc_url` varchar(255) DEFAULT NULL COMMENT '说明图',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检测模板-检测项表';

-- ----------------------------
-- Table structure for qc_template_product
-- ----------------------------
DROP TABLE IF EXISTS `qc_template_product`;
CREATE TABLE `qc_template_product` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `template_id` bigint NOT NULL COMMENT '检测模板ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_check` int DEFAULT '1' COMMENT '最低检测数',
  `quantity_unqualified` int DEFAULT '0' COMMENT '最大不合格数',
  `cr_rate` double(12,2) DEFAULT '0.00' COMMENT '最大致命缺陷率',
  `maj_rate` double(12,2) DEFAULT '0.00' COMMENT '最大严重缺陷率',
  `min_rate` double(12,2) DEFAULT '100.00' COMMENT '最大轻微缺陷率',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检测模板-产品表';

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `attachment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `source_doc_id` bigint DEFAULT NULL COMMENT '关联的业务单据ID',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '业务单据类型',
  `file_url` varchar(255) NOT NULL COMMENT '访问URL',
  `base_path` varchar(64) DEFAULT NULL COMMENT '域名',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `orignal_name` varchar(255) DEFAULT NULL COMMENT '原来的文件名',
  `file_type` varchar(64) DEFAULT NULL COMMENT '文件类型',
  `file_size` double(12,2) DEFAULT NULL COMMENT '文件大小',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='附件表';

-- ----------------------------
-- Table structure for sys_auto_code_part
-- ----------------------------
DROP TABLE IF EXISTS `sys_auto_code_part`;
CREATE TABLE `sys_auto_code_part` (
  `part_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分段ID',
  `rule_id` bigint NOT NULL COMMENT '规则ID',
  `part_index` int NOT NULL COMMENT '分段序号',
  `part_type` varchar(20) NOT NULL COMMENT '分段类型，INPUTCHAR：输入字符，NOWDATE：当前日期时间，FIXCHAR：固定字符，SERIALNO：流水号',
  `part_code` varchar(64) DEFAULT NULL COMMENT '分段编号',
  `part_name` varchar(255) DEFAULT NULL COMMENT '分段名称',
  `part_length` int NOT NULL COMMENT '分段长度',
  `date_format` varchar(20) DEFAULT NULL,
  `input_character` varchar(64) DEFAULT NULL COMMENT '输入字符',
  `fix_character` varchar(64) DEFAULT NULL COMMENT '固定字符',
  `seria_start_no` int DEFAULT NULL COMMENT '流水号起始值',
  `seria_step` int DEFAULT NULL COMMENT '流水号步长',
  `seria_now_no` int DEFAULT NULL COMMENT '流水号当前值',
  `cycle_flag` char(1) DEFAULT NULL COMMENT '流水号是否循环',
  `cycle_method` varchar(20) DEFAULT NULL COMMENT '循环方式，YEAR：按年，MONTH：按月，DAY：按天，HOUR：按小时，MINITE：按分钟，OTHER：按传入字符变',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='编码生成规则组成表';

-- ----------------------------
-- Records of sys_auto_code_part
-- ----------------------------
BEGIN;
INSERT INTO `sys_auto_code_part` VALUES (0, 236, 2, 'SERIALNO', 'SERIAL', '流水号', 5, NULL, NULL, NULL, 1, 1, NULL, 'N', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-22 20:43:08', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (200, 205, 2, 'SERIALNO', 'P1', '流水号', 8, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-04-26 21:13:17', 'admin', '2022-04-26 22:47:49');
INSERT INTO `sys_auto_code_part` VALUES (201, 205, 1, 'FIXCHAR', 'P0', '前缀', 4, NULL, NULL, 'ITEM', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-04-26 22:44:03', 'admin', '2022-08-15 15:59:19');
INSERT INTO `sys_auto_code_part` VALUES (202, 206, 1, 'FIXCHAR', 'P1', '前缀', 10, NULL, NULL, 'ITEM_TYPE_', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-04-26 23:02:12', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (203, 206, 2, 'SERIALNO', 'P2', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-04-26 23:02:42', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (204, 207, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'C', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 21:21:04', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (205, 207, 2, 'SERIALNO', 'SERIAL', '流水号部分', 5, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 21:21:44', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (206, 208, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'V', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 22:50:38', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (207, 208, 2, 'SERIALNO', 'SERIAL', '流水号', 5, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 22:51:02', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (208, 209, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'WS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 17:49:16', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (209, 209, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 17:49:40', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (210, 210, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'WH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 22:00:17', 'admin', '2022-08-16 18:58:36');
INSERT INTO `sys_auto_code_part` VALUES (211, 210, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 22:00:40', 'admin', '2022-07-30 11:26:14');
INSERT INTO `sys_auto_code_part` VALUES (212, 211, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'L', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 14:50:29', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (213, 211, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 14:52:12', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (214, 212, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 18:38:29', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (215, 212, 2, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 18:38:51', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (216, 213, 1, 'FIXCHAR', 'PREFIX', '前缀', 7, NULL, NULL, 'M_TYPE_', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 19:46:42', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (217, 213, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 19:47:03', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (218, 214, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'M', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 21:26:59', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (219, 214, 2, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 21:27:18', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (220, 215, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'MO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-09 11:40:23', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (222, 215, 2, 'NOWDATE', 'DATEPART', '年月日部分', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-09 11:58:57', 'admin', '2022-05-09 12:46:34');
INSERT INTO `sys_auto_code_part` VALUES (223, 215, 3, 'SERIALNO', 'SERIAL', '流水号部分', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-09 11:59:31', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (224, 216, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'WS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-10 21:55:51', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (225, 216, 2, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-10 21:56:19', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (226, 217, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'TT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 00:22:02', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (227, 217, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 00:22:25', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (228, 218, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'T', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 22:07:44', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (229, 218, 2, 'SERIALNO', 'SERIAL', '流水号', 5, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 22:08:17', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (230, 219, 1, 'FIXCHAR', 'PREFIX', '前缀', 7, NULL, NULL, 'PROCESS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 00:10:13', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (231, 219, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 00:10:33', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (232, 220, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'R', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 23:07:01', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (233, 220, 2, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1000, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 23:07:23', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (234, 221, 1, 'FIXCHAR', 'PREFIX', '固定前缀', 4, NULL, NULL, 'TASK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-15 18:22:53', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (235, 221, 2, 'NOWDATE', 'YEAR', '年份', 4, 'yyyy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-15 18:23:39', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (236, 221, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'YEAR', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-15 18:24:03', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (237, 222, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'I', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 21:57:46', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (238, 222, 2, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 21:58:05', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (239, 223, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'QCT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 22:43:31', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (240, 223, 2, 'NOWDATE', 'YEAR', '年份', 4, 'yyyy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 22:44:04', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (241, 223, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'YEAR', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 22:44:25', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (242, 224, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'DF', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 11:33:52', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (243, 224, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 11:34:11', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (244, 225, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'IQC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 16:29:59', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (245, 225, 2, 'NOWDATE', 'DATE', '年月日', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 16:30:28', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (246, 225, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 16:31:00', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (247, 226, 1, 'FIXCHAR', 'PREFIX', '前缀', 1, NULL, NULL, 'R', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-22 20:51:47', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (248, 226, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-05-22 20:52:10', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (249, 226, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-22 20:52:58', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (250, 227, 1, 'FIXCHAR', 'PREFIX', '固定前缀', 1, NULL, NULL, 'T', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 19:54:45', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (251, 227, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 19:55:06', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (252, 228, 1, 'FIXCHAR', 'PREFIX', '前缀', 4, NULL, NULL, 'PLAN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 22:08:39', 'admin', '2022-07-31 16:42:59');
INSERT INTO `sys_auto_code_part` VALUES (253, 228, 2, 'NOWDATE', 'YEAR', '年', 4, 'yyyy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 22:08:59', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (254, 228, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'YEAR', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 22:09:24', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (255, 229, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'RTV', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-13 16:06:14', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (256, 229, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-13 16:06:42', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (257, 229, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-13 16:07:10', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (258, 230, 1, 'FIXCHAR', 'PREFIX', '固定前缀', 3, NULL, NULL, 'SUB', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 20:28:22', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (259, 230, 2, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 20:28:44', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (260, 231, 1, 'FIXCHAR', 'PREFIX', '前缀', 4, NULL, NULL, 'PLAN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 21:50:22', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (261, 231, 2, 'NOWDATE', 'YEAR', '年份', 4, 'yyyy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 21:50:43', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (262, 231, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'YEAR', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 21:51:07', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (263, 232, 1, 'FIXCHAR', '1', '1', 3, NULL, NULL, 'BAT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-07-14 12:02:54', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (264, 232, 2, 'NOWDATE', '2', '2', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-07-14 12:03:16', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (265, 233, 1, 'FIXCHAR', 'PREFIX', '前缀', 5, NULL, NULL, 'ISSUE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-07-17 19:32:46', 'admin', '2022-07-17 19:35:44');
INSERT INTO `sys_auto_code_part` VALUES (266, 233, 2, 'NOWDATE', 'DATE', '年月日', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-07-17 19:33:22', 'admin', '2022-07-17 19:35:57');
INSERT INTO `sys_auto_code_part` VALUES (267, 233, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-07-17 19:33:45', 'admin', '2022-07-17 19:36:05');
INSERT INTO `sys_auto_code_part` VALUES (271, 234, 1, 'INPUTCHAR', 'PREFIX', '1', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-07-30 14:20:49', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (275, 236, 1, 'FIXCHAR', '前缀', '固定字符', 3, NULL, NULL, 'YCL', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-08-19 10:48:20', 'admin', '2022-08-19 14:13:30');
INSERT INTO `sys_auto_code_part` VALUES (277, 237, 1, 'FIXCHAR', 'PREFIX', '前缀', 4, NULL, NULL, 'IPQC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-08-29 22:07:43', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (278, 237, 2, 'NOWDATE', 'DATE', '年月日', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-08-29 22:08:18', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (279, 237, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-08-29 22:08:46', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (280, 238, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'OQC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-01 20:30:53', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (281, 238, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-01 20:32:11', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (282, 238, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-01 20:32:38', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (283, 239, 1, 'FIXCHAR', '001', '前缀', 5, NULL, NULL, 'PBACK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-03 23:49:07', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (284, 239, 2, 'NOWDATE', '002', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-03 23:49:31', 'admin', '2022-09-03 23:49:44');
INSERT INTO `sys_auto_code_part` VALUES (285, 239, 3, 'SERIALNO', '003', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-03 23:50:10', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (286, 240, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'RT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-15 23:19:25', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (287, 240, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-15 23:19:47', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (288, 240, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-15 23:20:09', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (289, 241, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'PR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-23 10:58:17', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (290, 241, 2, 'NOWDATE', 'DATE', '年月日', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-23 10:58:44', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (291, 241, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-23 10:59:06', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (292, 242, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'REP', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-28 22:01:19', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (293, 242, 2, 'NOWDATE', 'DATE', '日期', 4, 'yyyy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-09-28 22:01:39', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (294, 242, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'YEAR', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-28 22:02:00', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (295, 243, 1, 'FIXCHAR', 'PERFIX', '前缀', 2, NULL, NULL, 'PS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-05 19:46:02', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (296, 243, 2, 'NOWDATE', 'DATA', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-05 19:46:24', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (297, 243, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-05 19:46:48', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (298, 244, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'RS', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-06 21:40:42', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (299, 244, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-06 21:41:03', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (300, 244, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-06 21:41:22', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (301, 245, 1, 'FIXCHAR', 'PREFIX', '前缀', 4, NULL, NULL, 'PACK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-11 01:22:38', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (302, 245, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-10-11 01:23:09', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (303, 245, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-11 01:23:35', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (304, 252, 2, 'SERIALNO', 'SERIAL', '流水号', 5, NULL, NULL, NULL, 1, 1, NULL, 'N', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-22 20:43:08', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (305, 246, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'TR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-11-30 21:58:57', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (306, 246, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-11-30 21:59:19', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (307, 246, 3, 'SERIALNO', 'SERIAL', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-11-30 21:59:39', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (308, 247, 1, 'FIXCHAR', 'PREFIX', '前缀', 3, NULL, NULL, 'SN-', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-12-09 11:22:36', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (309, 247, 2, 'INPUTCHAR', 'INPUT', '产品编码', 12, NULL, 'ITEM_CODE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-12-09 11:23:36', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (310, 247, 3, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-12-09 11:24:07', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (311, 247, 4, 'SERIALNO', 'SERIAL', '流水号', 6, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2022-12-09 11:25:00', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (312, 248, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'FB', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-17 11:09:49', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (313, 248, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-17 11:10:11', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (314, 248, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-17 11:10:38', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (315, 249, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'OI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 14:24:59', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (316, 249, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 14:25:17', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (317, 249, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 14:25:40', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (318, 250, 1, 'FIXCHAR', 'PREFIX', '前缀', 2, NULL, NULL, 'OR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 20:17:25', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (319, 250, 2, 'NOWDATE', 'DATE', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 20:17:45', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (320, 250, 3, 'SERIALNO', 'SERIAL', '流水号', 4, NULL, NULL, NULL, 1, 1, NULL, 'Y', 'DAY', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 20:18:18', '', NULL);
INSERT INTO `sys_auto_code_part` VALUES (321, 251, 1, 'FIXCHAR', '001', '前缀', 5, NULL, NULL, 'PWASTE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-05-13 05:34:18', 'admin', '2024-05-13 05:38:45');
INSERT INTO `sys_auto_code_part` VALUES (322, 251, 2, 'NOWDATE', '002', '日期', 8, 'yyyyMMdd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-05-13 05:36:24', 'admin', '2024-05-13 05:39:03');
INSERT INTO `sys_auto_code_part` VALUES (323, 251, 3, 'SERIALNO', '003', '流水号', 3, NULL, NULL, NULL, 1, 1, NULL, 'N', NULL, NULL, NULL, NULL, 0, 0, 'admin', '2024-05-13 05:37:50', 'admin', '2024-05-13 05:39:17');
INSERT INTO `sys_auto_code_part` VALUES (324, 252, 1, 'FIXCHAR', '前缀', '固定字符', 3, NULL, NULL, 'CP', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2022-08-19 10:48:20', 'admin', '2022-08-19 14:13:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_auto_code_result
-- ----------------------------
DROP TABLE IF EXISTS `sys_auto_code_result`;
CREATE TABLE `sys_auto_code_result` (
  `code_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `rule_id` bigint NOT NULL COMMENT '规则ID',
  `gen_date` varchar(20) NOT NULL COMMENT '生成日期时间',
  `gen_index` int DEFAULT NULL COMMENT '最后产生的序号',
  `last_result` varchar(64) DEFAULT NULL COMMENT '最后产生的值',
  `last_serial_no` int DEFAULT NULL COMMENT '最后产生的流水号',
  `last_input_char` varchar(64) DEFAULT NULL COMMENT '最后传入的参数',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=634 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='编码生成记录表';

-- ----------------------------
-- Records of sys_auto_code_result
-- ----------------------------
BEGIN;
INSERT INTO `sys_auto_code_result` VALUES (591, 240, '20240605222319', 7, 'RT202406050007', 7, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 08:22:58', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (592, 211, '20240605163349', 2, 'L002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 08:33:32', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (593, 212, '20240605163419', 1, 'A0001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 08:34:19', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (594, 212, '20240606141212', 4, 'A0005', 5, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 08:34:57', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (595, 236, '20240619135531', 3, 'YCL00003', 3, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:25:54', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (596, 207, '20240605212820', 1, 'C00001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:28:20', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (597, 208, '20240605212850', 1, 'V00001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:28:50', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (598, 219, '20240605213127', 5, 'PROCESS005', 5, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:29:06', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (599, 220, '20240619145126', 3, 'R1002', 1002, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:29:45', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (600, 209, '20240605213509', 1, 'WS001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:35:09', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (601, 216, '20240605213602', 2, 'WS0002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:35:26', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (602, 216, '20240619135853', 4, 'WS0006', 6, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:36:25', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (603, 252, '20240619140221', 3, 'CP00003', 3, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:37:35', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (604, 226, '20240605214034', 1, 'R20240605001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:40:34', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (605, 215, '20240605214347', 1, 'MO202406050001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:43:47', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (606, 221, '20240619141552', 17, 'TASK20240017', 17, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:45:54', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (607, 233, '20240605214733', 1, 'ISSUE20240605001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-05 13:47:33', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (608, 248, '20240605215004', 1, 'FB202406050001', 1, '', '', NULL, NULL, 0, 0, '', '2024-06-05 13:50:04', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (609, 240, '20240606224522', 2, 'RT202406060002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 06:03:23', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (610, 251, '20240619142524', 9, 'PWASTE20240619009', 9, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 06:04:32', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (611, 248, '20240606224449', 4, 'FB202406060004', 4, '', '', NULL, NULL, 0, 0, '', '2024-06-06 09:29:21', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (612, 241, '20240606225116', 2, 'PR20240606002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 09:49:27', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (613, 243, '20240606175029', 1, 'PS20240606001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 09:50:29', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (614, 215, '20240606224128', 1, 'MO202406060001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 14:41:28', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (615, 233, '20240606224557', 1, 'ISSUE20240606001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-06 14:45:57', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (616, 248, '20240607171714', 4, 'FB202406070004', 4, '', '', NULL, NULL, 0, 0, '', '2024-06-07 07:17:55', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (617, 240, '20240607171717', 2, 'RT202406070002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-07 07:18:09', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (618, 248, '20240608002150', 1, 'FB202406080001', 1, '', '', NULL, NULL, 0, 0, '', '2024-06-07 16:21:50', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (619, 240, '20240608003202', 2, 'RT202406080002', 2, NULL, '', NULL, NULL, 0, 0, '', '2024-06-07 16:21:53', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (620, 241, '20240608050140', 1, 'PR20240608001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-07 21:01:40', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (621, 248, '20240612073944', 1, 'FB202406120001', 1, '', '', NULL, NULL, 0, 0, '', '2024-06-11 23:39:44', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (622, 248, '20240613181015', 3, 'FB202406130003', 3, '', '', NULL, NULL, 0, 0, '', '2024-06-13 07:44:59', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (623, 215, '20240613175725', 1, 'MO202406130001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-13 09:57:25', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (624, 241, '20240613181209', 1, 'PR20240613001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-13 10:12:09', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (625, 215, '20240618161301', 1, 'MO202406180001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-18 08:13:02', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (626, 248, '20240618161844', 1, 'FB202406180001', 1, '', '', NULL, NULL, 0, 0, '', '2024-06-18 08:18:44', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (627, 240, '20240618161920', 1, 'RT202406180001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-18 08:19:20', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (628, 233, '20240618161951', 1, 'ISSUE20240618001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-18 08:19:52', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (629, 215, '20240619140903', 1, 'MO202406190001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-19 06:09:03', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (630, 248, '20240619141747', 1, 'FB202406190001', 1, '', '', NULL, NULL, 0, 0, '', '2024-06-19 06:17:47', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (631, 233, '20240619141901', 1, 'ISSUE20240619001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-19 06:19:01', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (632, 240, '20240619142350', 1, 'RT202406190001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-19 06:23:51', '', NULL);
INSERT INTO `sys_auto_code_result` VALUES (633, 226, '20240619143527', 1, 'R20240619001', 1, NULL, '', NULL, NULL, 0, 0, '', '2024-06-19 06:35:27', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_auto_code_rule
-- ----------------------------
DROP TABLE IF EXISTS `sys_auto_code_rule`;
CREATE TABLE `sys_auto_code_rule` (
  `rule_id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `rule_code` varchar(64) NOT NULL COMMENT '规则编码',
  `rule_name` varchar(255) NOT NULL COMMENT '规则名称',
  `rule_desc` varchar(500) DEFAULT NULL COMMENT '描述',
  `max_length` int DEFAULT NULL COMMENT '最大长度',
  `is_padded` char(1) NOT NULL COMMENT '是否补齐',
  `padded_char` varchar(20) DEFAULT NULL COMMENT '补齐字符',
  `padded_method` char(1) DEFAULT 'L' COMMENT '补齐方式',
  `enable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='编码生成规则表';

-- ----------------------------
-- Records of sys_auto_code_rule
-- ----------------------------
BEGIN;
INSERT INTO `sys_auto_code_rule` VALUES (206, 'ITEM_TYPE_CODE', '物料分类编码', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-04-26 23:01:09', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (207, 'CLIENT_CODE', '客户编码规则', NULL, 6, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 21:20:29', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (208, 'VENDOR_CODE', '供应商编码规则', NULL, 6, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-06 22:50:13', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (209, 'WORKSHOP_CODE', '车间编码生成规则', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 17:48:52', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (210, 'WAREHOUSE_CODE', '仓库编码规则', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-07 21:59:51', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (211, 'LOCATION_CODE', '库区编码生成规则', NULL, 4, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 14:49:56', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (212, 'AREA_CODE', '库位编码规则', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 18:38:08', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (213, 'MACHINERY_TYPE_CODE', '设备类型编码规则', NULL, 10, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 19:46:09', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (214, 'MACHINERY_CODE', '设备编码规则', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-08 21:26:39', 'admin', '2022-08-23 09:15:17');
INSERT INTO `sys_auto_code_rule` VALUES (215, 'WORKORDER_CODE', '生产工单编码规则1', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-09 11:39:59', 'admin', '2022-08-20 09:12:40');
INSERT INTO `sys_auto_code_rule` VALUES (216, 'WORKSTATION_CODE', '工作站编码规则', NULL, 6, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-10 21:55:24', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (217, 'TOOL_TYPE_CODE', '工装夹具类型编码', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 00:21:37', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (218, 'TOOL_CODE', '工装夹具编码规则', NULL, 6, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-11 22:07:17', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (219, 'PROCESS_CODE', '工序编码规则', NULL, 10, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 00:09:45', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (220, 'ROUTE_CODE', '工艺流程编码规则', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-12 23:06:36', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (221, 'TASK_CODE', '生产任务编码规则', NULL, 12, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-15 18:22:29', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (222, 'QC_INDEX_CODE', '检测项编码规则', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 21:57:23', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (223, 'QC_TEMPLATE_CODE', '检测模板编码规则', NULL, 10, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-17 22:43:08', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (224, 'DEFECT_CODE', '常见缺陷编码', NULL, 5, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 11:33:27', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (225, 'QC_IQC_CODE', '来料检验单编码规则', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-19 16:28:07', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (226, 'ITEMRECPT_CODE', '物料入库单编码规则', NULL, 12, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-05-22 20:51:29', 'admin', '2022-05-22 20:53:12');
INSERT INTO `sys_auto_code_rule` VALUES (227, 'CAL_TEAM_CODE', '班组编码规则', NULL, 4, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 19:54:22', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (228, 'CAL_PLAN_CODE', '排班计划编号', NULL, 11, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-06 22:08:10', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (229, 'WM_RTVENDOR_CODE', '供应商退货单编码', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-13 15:48:07', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (230, 'SUBJECT_CODE', '设备点检保养项目编码', NULL, 6, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 20:27:54', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (231, 'CHECKPLAN_CODE', '点检编码规则', NULL, 11, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-06-16 21:50:00', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (232, 'BATCH_CODE', '批次规则', NULL, 11, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-07-14 12:02:10', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (233, 'ISSUE_CODE', '生产领料单编码', NULL, 16, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-07-17 19:32:10', 'admin', '2022-07-17 19:32:57');
INSERT INTO `sys_auto_code_rule` VALUES (236, 'ITEM_CODE', '物料规则', NULL, 13, 'N', '32', 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-08-19 10:44:20', 'admin', '2022-10-22 20:42:33');
INSERT INTO `sys_auto_code_rule` VALUES (237, 'IPQC_CODE', '过程检验单编码', NULL, 16, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-08-29 22:07:13', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (238, 'OQC_CODE', '出货编码规则', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-01 20:30:31', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (239, 'PBACK_CODE', '生产退料单编码', '生产退料单编码', 16, 'N', '0', 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-03 23:47:11', 'admin', '2022-09-03 23:47:57');
INSERT INTO `sys_auto_code_rule` VALUES (240, 'RTISSUE_CODE', '生产退库单编号规则', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-15 23:18:40', 'admin', '2022-09-15 23:19:04');
INSERT INTO `sys_auto_code_rule` VALUES (241, 'PRODUCTRECPT_CODE', '产品入库单编码规则', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-23 10:57:47', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (242, 'REPAIR_CODE', '维修工单编号规则', NULL, 10, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-09-28 21:59:54', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (243, 'PRODUCTSALSE_CODE', '销售出库单编号', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-05 19:45:35', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (244, 'RTSALSE_CODE', '销售退货单编码规则', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-06 21:40:18', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (245, 'PACKAGE_CODE', '装箱单编码规则', NULL, 16, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-10-11 01:22:08', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (246, 'TRANSFER_CODE', '转移单编码', NULL, 13, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-11-30 21:58:37', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (247, 'SN_CODE', 'SN码', NULL, 30, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-12-09 11:22:03', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (248, 'FEEDBACK_CODE', '生产报工单编码', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-17 11:09:25', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (249, 'OUTSOURCE_ISSUE_CODE', '外协发料单编码', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 14:24:35', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (250, 'OUTSOURCE_RECPT_CODE', '外协入库单编码', NULL, 14, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2023-10-30 20:17:00', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (251, 'PWASTE_CODE', '生产废料单编号', '生产废料单编号', 16, 'N', NULL, 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2024-05-13 05:33:31', '', NULL);
INSERT INTO `sys_auto_code_rule` VALUES (252, 'PRODUCT_CODE', '产品规则', NULL, 13, 'N', '32', 'L', 'Y', NULL, NULL, NULL, 0, 0, 'admin', '2022-08-19 10:44:20', 'admin', '2022-10-22 20:42:33');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
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
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-red', 'Y', 'admin', '2022-04-07 00:29:32', 'admin', '2022-08-23 08:41:24', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2022-04-07 00:29:32', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-light', 'Y', 'admin', '2022-04-07 00:29:32', 'admin', '2022-08-23 08:41:41', '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaOnOff', 'false', 'Y', 'admin', '2022-04-07 00:29:32', 'admin', '2024-05-21 15:53:13', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2022-04-07 00:29:32', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (100, 0, '0', '沧州亦辰塑业有限公司', 0, '管理员', '15888888888', 'admin@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', 'admin', '2024-04-23 09:05:41');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '东光总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', 'admin', '2024-04-23 09:08:59');
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', 'admin', '2022-08-16 11:55:10');
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 108, '0,100,102,108', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', 'admin', '2022-08-15 09:28:23');
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-04-07 00:29:30', 'admin', '2022-08-05 09:08:33');
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', 'admin', '2022-08-18 18:48:28');
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-04-07 00:29:30', '', NULL);
INSERT INTO `sys_dept` VALUES (110, 100, '0,100', '学生', 1, NULL, NULL, NULL, '0', '2', 'admin', '2022-08-10 17:53:08', '', NULL);
INSERT INTO `sys_dept` VALUES (111, 104, '0,100,102,108,104', '1211', 2, '222', NULL, NULL, '0', '2', 'admin', '2022-08-17 08:50:16', '', NULL);
INSERT INTO `sys_dept` VALUES (112, 108, '0,100,102,108', '子部门', 2, 'www', NULL, NULL, '0', '0', 'admin', '2022-08-19 09:20:59', '', NULL);
INSERT INTO `sys_dept` VALUES (113, 103, '0,100,101,103', '测试部门', 1, '老李', '13433333333', NULL, '0', '0', 'admin', '2022-08-19 10:21:02', '', NULL);
INSERT INTO `sys_dept` VALUES (114, 100, '0,100', '财务部', 1, '吴雨', '15565478901', NULL, '0', '0', 'admin', '2022-08-19 11:00:52', '', NULL);
INSERT INTO `sys_dept` VALUES (115, 100, '0,100', '测试钻心部', 0, '王二狗', '13400000000', 'asdasdasd@asdsd.com', '0', '0', 'admin', '2022-08-22 11:27:06', 'admin', '2022-08-22 16:30:20');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', 'info', 'Y', '0', 'admin', '2022-04-07 00:29:32', 'admin', '2022-08-19 20:53:31', '性别男', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '性别女', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '性别未知', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '显示菜单', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '隐藏菜单', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '正常状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '停用状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '正常状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '停用状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '默认分组', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '系统分组', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '系统默认是', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '系统默认否', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '通知', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '公告', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '正常状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '关闭状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '新增操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '修改操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '删除操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '授权操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '导出操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '导入操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '强退操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '生成操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '清空操作', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '正常状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '停用状态', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (100, 1, '物料', 'ITEM', 'mes_item_product', NULL, 'default', 'N', '0', 'admin', '2022-04-16 16:34:46', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (101, 2, '产品', 'PRODUCT', 'mes_item_product', NULL, 'default', 'N', '0', 'admin', '2022-04-16 16:35:02', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (102, 1, '输入字符', 'INPUTCHAR', 'sys_autocode_parttype', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:33:47', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 2, '当前日期时间', 'NOWDATE', 'sys_autocode_parttype', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:34:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (104, 3, '固定字符', 'FIXCHAR', 'sys_autocode_parttype', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:34:27', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (105, 4, '流水号', 'SERIALNO', 'sys_autocode_parttype', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:34:53', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (106, 1, '按年', 'YEAR', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:35:17', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (107, 2, '按月', 'MONTH', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:35:29', 'admin', '2022-04-26 15:35:55', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (108, 3, '按天', 'DAY', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:36:13', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (109, 4, '按小时', 'HOUR', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:36:34', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (110, 5, '按分钟', 'MINUTE', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:36:59', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (111, 6, '按传入字符', 'OTHER', 'sys_autocode_cyclemethod', NULL, 'default', 'N', '0', 'admin', '2022-04-26 15:37:19', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (112, 1, '企业客户', 'ENTERPRISE', 'mes_client_type', NULL, 'default', 'N', '0', 'admin', '2022-05-06 20:54:37', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (113, 2, '个人', 'PERSON', 'mes_client_type', NULL, 'default', 'N', '0', 'admin', '2022-05-06 20:55:00', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (114, 1, '优质供应商', 'A', 'mes_vendor_level', NULL, 'default', 'N', '0', 'admin', '2022-05-06 22:45:52', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (115, 2, '正常', 'B', 'mes_vendor_level', NULL, 'default', 'N', '0', 'admin', '2022-05-06 22:46:02', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (116, 3, '重点关注', 'C', 'mes_vendor_level', NULL, 'default', 'N', '0', 'admin', '2022-05-06 22:46:15', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (117, 4, '劣质供应商', 'D', 'mes_vendor_level', NULL, 'default', 'N', '0', 'admin', '2022-05-06 22:46:41', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 5, '黑名单', 'E', 'mes_vendor_level', NULL, 'default', 'N', '0', 'admin', '2022-05-06 22:46:54', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (119, 1, '草稿', 'PREPARE', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2022-05-09 10:35:34', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (120, 2, '已确认', 'CONFIRMED', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2022-05-09 10:36:37', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (121, 3, '审批中', 'APPROVING', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2022-05-09 10:39:30', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (122, 4, '已审批', 'APPROVED', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2022-05-09 10:39:45', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (123, 5, '已完成', 'FINISHED', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2022-05-09 10:40:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (124, 1, '客户订单', 'ORDER', 'mes_workorder_sourcetype', NULL, 'default', 'N', '0', 'admin', '2022-05-09 11:23:47', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (125, 2, '库存备货', 'STORE', 'mes_workorder_sourcetype', NULL, 'default', 'N', '0', 'admin', '2022-05-09 11:24:04', 'admin', '2022-05-09 11:24:24', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (126, 1, '定期维护', 'REGULAR', 'mes_mainten_type', NULL, 'default', 'N', '0', 'admin', '2022-05-10 23:40:57', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (127, 2, '按使用次数维护', 'USAGE', 'mes_mainten_type', NULL, 'default', 'N', '0', 'admin', '2022-05-10 23:41:31', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (128, 1, '在库', 'STORE', 'mes_tool_status', NULL, 'default', 'N', '0', 'admin', '2022-05-11 20:51:13', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (129, 2, '被领用', 'ISSUE', 'mes_tool_status', NULL, 'default', 'N', '0', 'admin', '2022-05-11 20:51:36', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (130, 3, '报废', 'SCRAP', 'mes_tool_status', NULL, 'default', 'N', '0', 'admin', '2022-05-11 20:52:02', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (131, 4, '维修中', 'REPARE', 'mes_tool_status', NULL, 'default', 'N', '0', 'admin', '2022-05-11 20:52:20', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (132, 1, 'S-to-S', 'SS', 'mes_link_type', NULL, 'default', 'N', '0', 'admin', '2022-05-13 21:51:28', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (133, 2, 'F-to-F', 'FF', 'mes_link_type', NULL, 'default', 'N', '0', 'admin', '2022-05-13 21:51:51', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (134, 3, 'S-to-F', 'SF', 'mes_link_type', NULL, 'default', 'N', '0', 'admin', '2022-05-13 21:52:05', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (135, 4, 'F-to-S', 'FS', 'mes_link_type', NULL, 'default', 'N', '0', 'admin', '2022-05-13 21:52:21', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (136, 1, '秒', 'SECOND', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:41:14', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (137, 2, '分钟', 'MINUTE', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:41:25', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (138, 3, '小时', 'HOUR', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:41:46', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (139, 4, '天', 'DAY', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:41:57', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (140, 5, '周', 'WEEK', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:42:12', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (141, 6, '月', 'MONTH', 'mes_time_type', NULL, 'default', 'N', '0', 'admin', '2022-05-14 08:42:26', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (142, 1, '尺寸', 'SIZE', 'mes_index_type', NULL, 'default', 'N', '0', 'admin', '2022-05-17 21:26:34', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (143, 2, '外观', 'APPEARANCE', 'mes_index_type', NULL, 'default', 'N', '0', 'admin', '2022-05-17 21:28:19', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (144, 3, '重量', 'WEIGHT', 'mes_index_type', NULL, 'default', 'N', '0', 'admin', '2022-05-17 21:28:31', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (145, 4, '性能', 'PERFORMANCE', 'mes_index_type', NULL, 'default', 'N', '0', 'admin', '2022-05-17 21:29:28', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (146, 5, '成分', 'COMPONENT', 'mes_index_type', NULL, 'default', 'N', '0', 'admin', '2022-05-17 21:30:34', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (147, 1, '[来料检验]', 'IQC', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:38:58', 'admin', '2022-05-18 10:38:43', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (148, 3, '[首检]', 'FIRST', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:39:19', 'admin', '2023-10-10 17:08:09', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (149, 4, '[末检]', 'FINAL', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:39:46', 'admin', '2023-10-10 17:08:14', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (150, 5, '[巡检]', 'PATROL', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:40:05', 'admin', '2023-10-10 17:08:19', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (151, 7, '[成品检验]', 'FQC', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:40:27', 'admin', '2023-10-18 16:01:01', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (152, 8, '[发货检验]', 'OQC', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2022-05-18 09:40:52', 'admin', '2023-10-18 16:01:05', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (153, 1, '致命缺陷', 'CR', 'mes_defect_level', NULL, 'default', 'N', '0', 'admin', '2022-05-19 10:24:05', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (154, 2, '严重缺陷', 'MAJ', 'mes_defect_level', NULL, 'default', 'N', '0', 'admin', '2022-05-19 10:24:20', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (155, 3, '轻微缺陷', 'MIN', 'mes_defect_level', NULL, 'default', 'N', '0', 'admin', '2022-05-19 10:24:33', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (156, 1, '检验通过', 'ACCEPT', 'mes_qc_result', NULL, 'default', 'N', '0', 'admin', '2022-05-19 16:38:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (157, 2, '检验不通过', 'REJECT', 'mes_qc_result', NULL, 'default', 'N', '0', 'admin', '2022-05-19 16:38:22', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (159, 1, '白班', 'SINGLE', 'mes_shift_type', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:33:56', 'admin', '2022-06-06 21:35:05', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (160, 2, '两班倒', 'SHIFT_TWO', 'mes_shift_type', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:34:19', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (161, 3, '三班倒', 'SHIFT_THREE', 'mes_shift_type', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:34:38', 'admin', '2022-06-06 21:34:45', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (162, 1, '按天', 'DAY', 'mes_shift_method', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:39:51', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (163, 2, '按周', 'WEEK', 'mes_shift_method', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:40:14', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (164, 3, '按月', 'MONTH', 'mes_shift_method', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:40:31', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (165, 4, '按季度', 'QUARTER', 'mes_shift_method', NULL, 'default', 'N', '0', 'admin', '2022-06-06 21:40:55', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (166, 1, '注塑', 'ZS', 'mes_calendar_type', NULL, 'default', 'N', '0', 'admin', '2022-06-08 13:27:23', 'admin', '2022-06-11 21:05:57', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (167, 2, '机加工', 'CNC', 'mes_calendar_type', NULL, 'default', 'N', '0', 'admin', '2022-06-08 13:27:35', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (168, 3, '组装', 'ZZ', 'mes_calendar_type', NULL, 'default', 'N', '0', 'admin', '2022-06-08 13:27:55', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (169, 4, '仓库', 'CK', 'mes_calendar_type', NULL, 'default', 'N', '0', 'admin', '2022-06-08 13:28:24', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (170, 1, '设备点检', 'CHECK', 'mes_dvsubject_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 16:50:51', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (171, 2, '设备保养', 'MAINTEN', 'mes_dvsubject_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 16:51:19', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (172, 1, '小时', 'HOUR', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:47:54', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (173, 2, '天', 'DAY', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:49:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (174, 3, '周', 'WEEK', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:49:21', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (175, 4, '月', 'MONTH', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:49:32', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (176, 5, '季度', 'QUARTER', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:50:00', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (177, 6, '年', 'YEAR', 'mes_cycle_type', NULL, 'default', 'N', '0', 'admin', '2022-06-16 20:50:22', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (178, 1, '设备点检计划', 'CHECK', 'dv_plan_type', NULL, 'default', 'N', '0', 'admin', '2022-06-19 17:03:44', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (179, 2, '设备保养计划', 'MAINTEN', 'dv_plan_type', NULL, 'default', 'N', '0', 'admin', '2022-06-19 17:03:56', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (180, 1, 'QR二维码', 'QR_CODE', 'mes_barcode_formart', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:06:50', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (181, 2, 'EAN码', 'EAN_CODE', 'mes_barcode_formart', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:07:40', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (182, 3, 'UPC码', 'UPC_CODE', 'mes_barcode_formart', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:07:58', 'admin', '2022-08-01 11:08:03', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (183, 1, '物料产品条码', 'ITEM', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:09:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (184, 2, '包装条码', 'PACKAGE', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:10:19', 'admin', '2022-10-22 12:20:01', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (185, 3, '库存条码', 'STOCK', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-08-01 11:10:40', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (186, 1, '修复成功', 'SUCCESS', 'mes_repair_result', NULL, 'default', 'N', '0', 'admin', '2022-08-06 11:27:48', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (187, 2, '报废', 'SCRAP', 'mes_repair_result', NULL, 'default', 'N', '0', 'admin', '2022-08-06 11:28:18', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (188, 0, '汉族', '0', 'nation_type', NULL, 'default', 'N', '0', 'admin', '2022-08-15 14:26:52', 'admin', '2022-08-15 14:27:35', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (189, 1, '首检', 'FIRST', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:19:20', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (190, 2, '末检', 'FINAL', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:19:38', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (191, 3, '自检', 'SELF', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:19:52', 'admin', '2022-08-29 20:19:57', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (192, 4, '巡检', 'PATROL', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:20:13', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (193, 5, '点检', 'CHECK', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:20:28', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (194, 6, '终检', 'FQC', 'mes_ipqc_type', NULL, 'default', 'N', '0', 'admin', '2022-08-29 20:20:44', 'admin', '2022-08-29 20:58:07', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (195, 1, '自行报工', 'SELF', 'mes_feedback_type', NULL, 'default', 'N', '0', 'admin', '2022-10-02 15:52:45', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (196, 2, '统一报工', 'UNI', 'mes_feedback_type', NULL, 'default', 'N', '0', 'admin', '2022-10-02 15:53:03', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (197, 1, '停机', 'STOP', 'mes_machinery_status', NULL, 'default', 'N', '0', 'admin', '2022-10-09 19:24:34', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (198, 2, '生产中', 'WORKING', 'mes_machinery_status', NULL, 'default', 'N', '0', 'admin', '2022-10-09 19:24:54', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (199, 3, '维修中', 'REPAIR', 'mes_machinery_status', NULL, 'default', 'N', '0', 'admin', '2022-10-09 19:25:28', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (200, 4, '设备码', 'MACHINERY', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-10-22 12:20:35', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (201, 5, '工作站码', 'WORKSTATION', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-10-22 12:20:57', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (202, 6, '仓库码', 'WAREHOUSE', 'mes_barcode_type', NULL, 'default', 'N', '0', 'admin', '2022-10-22 12:21:15', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (203, 1, '内部转移', 'INNER', 'mes_transfer_type', NULL, 'default', 'N', '0', 'admin', '2022-11-28 14:50:18', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (204, 2, '外部转移', 'OUTER', 'mes_transfer_type', NULL, 'default', 'N', '0', 'admin', '2022-11-28 14:50:31', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (205, 1, '公告', 'NOTICE', 'sys_message_type', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:36:32', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (206, 2, '提示', 'PROMPT', 'sys_message_type', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:36:56', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (207, 3, '告警', 'WARNING', 'sys_message_type', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:37:19', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (208, 4, '聊天消息', 'CHAT', 'sys_message_type', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:37:42', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (209, 1, '普通', 'A', 'sys_message_level', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:38:25', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (210, 2, '警告', 'B', 'sys_message_level', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:38:35', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (211, 3, '紧急', 'C', 'sys_message_level', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:38:47', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (212, 1, '未读', 'UNREAD', 'sys_message_status', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:39:30', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (213, 2, '已读', 'READ', 'sys_message_status', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:39:42', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (214, 3, '已处理', 'PROCEED', 'sys_message_status', NULL, 'default', 'N', '0', 'admin', '2023-03-06 19:40:15', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (215, 1, '标签打印机', 'LABEL', 'mes_printer_type', NULL, 'default', 'N', '0', 'admin', '2023-09-01 10:57:25', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (216, 1, '网线连接', 'NET', 'mes_conn_type', NULL, 'default', 'N', '0', 'admin', '2023-09-01 10:59:15', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (217, 2, 'USB连接', 'USB', 'mes_conn_type', NULL, 'default', 'N', '0', 'admin', '2023-09-01 10:59:32', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (218, 3, 'WIFI连接', 'WIFI', 'mes_conn_type', NULL, 'default', 'N', '0', 'admin', '2023-09-01 10:59:54', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (219, 4, '蓝牙连接', 'BLUETOOTH', 'mes_conn_type', NULL, 'default', 'N', '0', 'admin', '2023-09-01 11:00:06', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (220, 1, '就绪', 'READY', 'mes_printer_status', NULL, 'default', 'N', '0', 'admin', '2023-09-01 11:01:24', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (221, 2, '离线', 'OFFLINE', 'mes_printer_status', NULL, 'default', 'N', '0', 'admin', '2023-09-01 11:01:42', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (222, 3, '故障', 'ERROR', 'mes_printer_status', NULL, 'default', 'N', '0', 'admin', '2023-09-01 11:01:55', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (223, 2, '[过程检验]', 'PQC', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2023-10-10 17:07:27', 'admin', '2023-10-10 17:08:03', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (224, 6, '[自检]', 'SELF', 'mes_qc_type', NULL, 'default', 'N', '0', 'admin', '2023-10-18 16:00:55', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (225, 1, '自产', 'SELF', 'mes_workorder_type', NULL, 'default', 'N', '0', 'admin', '2023-10-29 22:16:16', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (226, 2, '外协', 'OUTSOURCE', 'mes_workorder_type', NULL, 'default', 'N', '0', 'admin', '2023-10-29 22:16:33', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (227, 3, '外购', 'PURCHASE', 'mes_workorder_type', NULL, 'default', 'N', '0', 'admin', '2023-10-29 22:16:48', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (230, 0, '第一层', '1', 'mes_item_level', NULL, 'default', 'N', '0', 'admin', '2024-05-07 08:31:05', 'admin', '2024-05-07 08:31:19', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (231, 1, '第二层', '2', 'mes_item_level', NULL, 'default', 'N', '0', 'admin', '2024-05-07 08:31:27', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (232, 2, '第三层', '3', 'mes_item_level', NULL, 'default', 'N', '0', 'admin', '2024-05-07 08:31:34', 'admin', '2024-05-07 08:31:40', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (233, 3, '第四层', '4', 'mes_item_level', NULL, 'default', 'N', '0', 'admin', '2024-05-07 08:31:47', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (234, 0, '草稿', 'PREPARE', 'mes_client_order_status', NULL, 'default', 'N', '0', 'admin', '2024-05-26 16:04:55', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (235, 1, '已生成生产订单', 'WORK_ORDER_FINISHED', 'mes_client_order_status', NULL, 'default', 'N', '0', 'admin', '2024-05-26 16:05:10', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (236, 1, '管理后台', '1', 'sys_module_type', NULL, 'default', 'N', '0', 'admin', '2024-06-10 07:02:01', 'admin', '2024-06-10 09:03:02', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (237, 2, '移动端', '2', 'sys_module_type', NULL, 'default', 'N', '0', 'admin', '2024-06-10 07:02:13', 'admin', '2024-06-10 09:03:06', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (238, 3, '平板端', '3', 'sys_module_type', NULL, 'default', 'N', '0', 'admin', '2024-06-10 07:02:26', 'admin', '2024-06-10 09:03:09', NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (239, 6, '已拒绝', 'REFUSE', 'mes_order_status', NULL, 'default', 'N', '0', 'admin', '2024-06-12 15:02:21', '', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1800060729982246915 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '用户性别列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '菜单状态列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '系统开关列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '任务状态列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '任务分组列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '系统是否列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '通知类型列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '通知状态列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '操作类型列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2022-04-07 00:29:32', '', NULL, '登录状态列表', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (100, '物料or产品', 'mes_item_product', '0', 'admin', '2022-04-16 16:34:20', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (101, '编码规则组成类型', 'sys_autocode_parttype', '0', 'admin', '2022-04-26 15:32:42', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (102, '编码规则组成循环方式', 'sys_autocode_cyclemethod', '0', 'admin', '2022-04-26 15:33:02', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (103, '客户类型', 'mes_client_type', '0', 'admin', '2022-05-06 20:53:28', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (104, '供应商级别', 'mes_vendor_level', '0', 'admin', '2022-05-06 22:44:56', 'admin', '2022-05-06 22:45:35', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (105, '单据状态【通用】', 'mes_order_status', '0', 'admin', '2022-05-09 10:34:41', 'admin', '2022-05-09 10:35:10', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (106, '生产工单来源', 'mes_workorder_sourcetype', '0', 'admin', '2022-05-09 11:23:22', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (107, '维护类型表', 'mes_mainten_type', '0', 'admin', '2022-05-10 23:40:36', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (108, '工装夹具状态', 'mes_tool_status', '0', 'admin', '2022-05-11 20:50:46', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (109, '工序关系类型', 'mes_link_type', '0', 'admin', '2022-05-13 21:50:44', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (110, '时间单位', 'mes_time_type', '0', 'admin', '2022-05-14 08:40:53', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (111, '检测项类型', 'mes_index_type', '0', 'admin', '2022-05-17 21:26:05', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (112, '检测类型', 'mes_qc_type', '0', 'admin', '2022-05-18 09:38:32', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (113, '缺陷等级', 'mes_defect_level', '0', 'admin', '2022-05-19 10:23:38', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (114, '检验结果', 'mes_qc_result', '0', 'admin', '2022-05-19 16:37:45', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (116, '倒班方式', 'mes_shift_type', '0', 'admin', '2022-06-06 21:33:17', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (117, '轮班方式', 'mes_shift_method', '0', 'admin', '2022-06-06 21:39:26', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (118, '排班类型', 'mes_calendar_type', '0', 'admin', '2022-06-08 13:26:56', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (119, '设备点检保养项目类型', 'mes_dvsubject_type', '0', 'admin', '2022-06-16 16:50:14', 'admin', '2022-06-16 16:50:29', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (120, '设备点检频率', 'mes_cycle_type', '0', 'admin', '2022-06-16 20:47:19', 'admin', '2022-06-16 20:51:22', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (121, '设备点检保养计划类型', 'dv_plan_type', '0', 'admin', '2022-06-19 17:03:18', 'admin', '2022-06-19 17:04:17', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (122, '条码格式', 'mes_barcode_formart', '0', 'admin', '2022-08-01 11:05:54', 'admin', '2022-08-01 11:06:15', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (123, '条码类型', 'mes_barcode_type', '0', 'admin', '2022-08-01 11:08:27', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (124, '维修结果', 'mes_repair_result', '0', 'admin', '2022-08-06 11:27:05', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (125, '民族', 'nation_type', '0', 'admin', '2022-08-15 14:25:55', 'admin', '2022-08-15 14:26:34', '民族', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (126, '过程质量检验类型', 'mes_ipqc_type', '0', 'admin', '2022-08-29 20:18:48', 'admin', '2022-08-29 20:18:59', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (127, '生产报工类型', 'mes_feedback_type', '0', 'admin', '2022-10-02 15:51:21', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (128, '设备状态', 'mes_machinery_status', '0', 'admin', '2022-10-09 19:23:54', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (129, '转移单类型', 'mes_transfer_type', '0', 'admin', '2022-11-28 14:49:46', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (130, '消息类型', 'sys_message_type', '0', 'admin', '2023-03-06 19:35:12', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (131, '消息级别', 'sys_message_level', '0', 'admin', '2023-03-06 19:38:07', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (132, '消息状态', 'sys_message_status', '0', 'admin', '2023-03-06 19:39:16', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (133, '打印机类型', 'mes_printer_type', '0', 'admin', '2023-09-01 10:56:38', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (134, '打印机连接类型', 'mes_conn_type', '0', 'admin', '2023-09-01 10:58:47', 'admin', '2023-09-01 10:59:00', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (135, '打印机状态', 'mes_printer_status', '0', 'admin', '2023-09-01 11:00:59', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (136, '生产工单类型', 'mes_workorder_type', '0', 'admin', '2023-10-29 22:15:59', '', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1787760885981257729, '客户订单材料层级', 'mes_item_level', '0', 'admin', '2024-05-07 16:26:25', 'admin', '2024-05-07 16:26:25', NULL, 1, 1);
INSERT INTO `sys_dict_type` VALUES (1794761403068403713, '客户订单状态', 'mes_client_order_status', '0', 'admin', '2024-05-27 00:03:58', 'admin', '2024-05-27 00:03:58', NULL, 1, 1);
INSERT INTO `sys_dict_type` VALUES (1800060729982246914, '模块类型', 'sys_module_type', '0', 'admin', '2024-06-10 15:01:36', 'admin', '2024-06-10 15:39:55', NULL, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度表';

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=173347 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度日志表';

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
BEGIN;
INSERT INTO `sys_logininfor` VALUES (1, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Mac OS X', '0', '登录成功', '2024-06-24 08:17:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `module_type` char(1) DEFAULT '1' COMMENT '模块类型(1:管理后台2:移动端3:平板端)',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2347 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '1', '0', '0', '', 'system', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 1, 0, 'M', '1', '0', '0', '', 'monitor', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 1, 0, 'M', '1', '0', '0', '', 'tool', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 2210, 4, 'user', 'system/user/index', '', 1, 0, 'C', '1', '0', '0', 'system:user:list', 'user', 'admin', NULL, '2022-04-07 00:29:31', 'admin', NULL, '2022-08-13 21:41:42', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 2210, 3, 'role', 'system/role/index', '', 1, 0, 'C', '1', '0', '0', 'system:role:list', 'peoples', 'admin', NULL, '2022-04-07 00:29:31', 'admin', NULL, '2022-08-13 21:42:03', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '1', '0', '0', 'system:menu:list', 'tree-table', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 2210, 1, 'dept', 'system/dept/index', '', 1, 0, 'C', '1', '0', '0', 'system:dept:list', 'tree', 'admin', NULL, '2022-04-07 00:29:31', 'admin', NULL, '2022-08-13 21:42:27', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 2210, 2, 'post', 'system/post/index', '', 1, 0, 'C', '1', '0', '0', 'system:post:list', 'post', 'admin', NULL, '2022-04-07 00:29:31', 'admin', NULL, '2022-08-13 21:42:38', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '1', '0', '0', 'system:dict:list', 'dict', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '1', '0', '0', 'system:config:list', 'edit', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '1', '0', '0', 'system:notice:list', 'message', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '1', '0', '0', '', 'log', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:online:list', 'online', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:job:list', 'job', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:druid:list', 'druid', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:server:list', 'server', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:cache:list', 'redis', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '1', '0', '0', 'tool:build:list', 'build', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '1', '0', '0', 'tool:gen:list', 'code', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '1', '0', '0', 'tool:swagger:list', 'swagger', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:operlog:list', 'form', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '1', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:import', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:user:resetPwd', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:role:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:role:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:role:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:role:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:role:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:menu:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:menu:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:menu:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:menu:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:dept:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:dept:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:dept:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:dept:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:post:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:post:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:post:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:post:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '1', '0', '0', 'system:post:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:dict:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:dict:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:dict:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:dict:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:dict:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:config:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:config:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:config:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:config:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:config:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:notice:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:notice:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:notice:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'system:notice:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:operlog:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:operlog:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:operlog:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:logininfor:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:logininfor:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:logininfor:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:online:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:online:batchLogout', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:online:forceLogout', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:add', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:changeStatus', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 7, '#', '', '', 1, 0, 'F', '1', '0', '0', 'monitor:job:export', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 115, 1, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:query', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 115, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:edit', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 115, 3, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:remove', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 115, 2, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:import', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 115, 4, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:preview', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 115, 5, '#', '', '', 1, 0, 'F', '1', '0', '0', 'tool:gen:code', '#', 'admin', NULL, '2022-04-07 00:29:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '主数据', 0, 4, 'mes/md', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'build', 'admin', NULL, '2022-04-16 14:41:55', 'admin', NULL, '2022-08-18 11:44:29', '');
INSERT INTO `sys_menu` VALUES (2001, '物料产品管理', 2000, 3, 'mditem', 'mes/md/mditem/index', NULL, 1, 0, 'C', '1', '0', '0', 'ms:md:mditem', 'excel', 'admin', NULL, '2022-04-16 14:44:00', 'admin', NULL, '2024-04-23 15:24:14', '');
INSERT INTO `sys_menu` VALUES (2002, '物料产品分类', 2000, 1, 'itemtype', 'mes/md/itemtype/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:itemtype', 'nested', 'admin', NULL, '2022-04-16 16:03:18', 'admin', NULL, '2024-04-23 15:23:56', '');
INSERT INTO `sys_menu` VALUES (2003, '编码规则', 1, 10, 'autocodeRule', 'system/autocode/index', NULL, 1, 0, 'C', '1', '0', '0', 'system:autocode:rule', 'code', 'admin', NULL, '2022-04-24 21:43:28', 'admin', NULL, '2022-04-24 21:49:31', '');
INSERT INTO `sys_menu` VALUES (2004, '编码规则查询', 2003, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:autocode:rule:list', '#', 'admin', NULL, '2022-04-24 21:46:05', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '编码规则新增', 2003, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:autocode:rule:add', '#', 'admin', NULL, '2022-04-24 21:46:30', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '编码规则更新', 2003, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:autocode:rule:edit', '#', 'admin', NULL, '2022-04-24 21:46:59', 'admin', NULL, '2022-04-24 21:47:42', '');
INSERT INTO `sys_menu` VALUES (2007, '编码规则删除', 2003, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:autocode:rule:remove', '#', 'admin', NULL, '2022-04-24 21:47:20', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2008, '新增物料分类', 2002, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:itemtype:add', '#', 'admin', NULL, '2022-04-27 16:46:06', 'admin', NULL, '2022-08-16 16:15:39', '');
INSERT INTO `sys_menu` VALUES (2009, '更新物料分类', 2002, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:itemtype:edit', '#', 'admin', NULL, '2022-04-27 16:46:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2010, '删除物料分类', 2002, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:itemtype:remove', '#', 'admin', NULL, '2022-04-27 16:47:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2011, '新增物料产品', 2001, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:add', '#', 'admin', NULL, '2022-04-27 16:47:46', 'admin', NULL, '2022-08-17 08:56:36', '');
INSERT INTO `sys_menu` VALUES (2012, '更新物料产品', 2001, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:edit', '#', 'admin', NULL, '2022-04-27 16:48:11', 'admin', NULL, '2022-05-04 18:05:40', '');
INSERT INTO `sys_menu` VALUES (2013, '删除物料产品', 2001, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:remove', '#', 'admin', NULL, '2022-04-27 16:48:36', 'admin', NULL, '2022-05-04 18:05:55', '');
INSERT INTO `sys_menu` VALUES (2014, '计量单位', 2000, 2, 'unitmeasure', 'mes/md/unitmeasure/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:unitmeasure', 'tab', 'admin', NULL, '2022-04-27 18:08:08', 'admin', NULL, '2024-04-23 15:24:18', '');
INSERT INTO `sys_menu` VALUES (2015, '新增单位', 2014, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:unitmeasure:add', '#', 'admin', NULL, '2022-04-27 18:14:06', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2016, '更新单位', 2014, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:unitmeasure:edit', '#', 'admin', NULL, '2022-04-27 18:14:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2017, '删除单位', 2014, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:unitmeasure:remove', '#', 'admin', NULL, '2022-04-27 18:14:44', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2018, '物料明细查看', 2001, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:query', '#', 'admin', NULL, '2022-05-04 17:51:19', 'admin', NULL, '2022-05-04 18:06:04', '');
INSERT INTO `sys_menu` VALUES (2019, '物料分类明细', 2002, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:itemtype:query', '#', 'admin', NULL, '2022-05-04 17:52:22', 'admin', NULL, '2022-08-16 16:16:59', '');
INSERT INTO `sys_menu` VALUES (2020, '单位明细', 2014, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:unitmeasure:query', '#', 'admin', NULL, '2022-05-04 17:54:06', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '物料产品列表查询', 2001, 0, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:list', '#', 'admin', NULL, '2022-05-04 18:04:36', 'admin', NULL, '2022-05-04 18:05:23', '');
INSERT INTO `sys_menu` VALUES (2022, '物料分类列表查询', 2002, 0, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:itemtype:list', '#', 'admin', NULL, '2022-05-04 18:08:57', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2023, '单位列表查询', 2014, 0, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:unitmeasure:list', '#', 'admin', NULL, '2022-05-04 18:09:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '客户管理', 2000, 4, 'client', 'mes/md/client/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:client', 'people', 'admin', NULL, '2022-05-06 20:37:28', 'admin', NULL, '2022-05-06 20:43:03', '');
INSERT INTO `sys_menu` VALUES (2025, '客户列表查询', 2024, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:client:list', '#', 'admin', NULL, '2022-05-06 20:38:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '客户新增', 2024, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:client:add', '#', 'admin', NULL, '2022-05-06 20:38:33', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2027, '客户编辑', 2024, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:client:edit', '#', 'admin', NULL, '2022-05-06 20:38:55', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '客户明细', 2024, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:client:query', '#', 'admin', NULL, '2022-05-06 20:39:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '客户删除', 2024, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:client:remove', '#', 'admin', NULL, '2022-05-06 20:39:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2030, '供应商管理', 2000, 5, 'vendor', 'mes/md/vendor/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:vendor', 'peoples', 'admin', NULL, '2022-05-06 22:34:09', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2031, '供应商列表查询', 2030, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:vendor:list', '#', 'admin', NULL, '2022-05-06 22:34:34', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2032, '供应商查看', 2030, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:vendor:query', '#', 'admin', NULL, '2022-05-06 22:35:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '供应商新增', 2030, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:vendor:add', '#', 'admin', NULL, '2022-05-06 22:35:38', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2034, '供应商编辑', 2030, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:vendor:edit', '#', 'admin', NULL, '2022-05-06 22:35:59', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2035, '供应商删除', 2030, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:vendor:remove', '#', 'admin', NULL, '2022-05-06 22:36:21', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2036, '车间设置', 2000, 8, 'workshop', 'mes/md/workshop/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:workshop', 'zip', 'admin', NULL, '2022-05-07 16:40:48', 'admin', NULL, '2024-04-25 04:51:40', '');
INSERT INTO `sys_menu` VALUES (2037, '车间列表查询', 2036, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workshop:list', '#', 'admin', NULL, '2022-05-07 16:41:09', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2038, '车间查看', 2036, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workshop:query', '#', 'admin', NULL, '2022-05-07 16:41:36', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2039, '车间新增', 2036, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workshop:add', '#', 'admin', NULL, '2022-05-07 16:41:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2040, '车间修改', 2036, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workshop:edit', '#', 'admin', NULL, '2022-05-07 16:42:18', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2041, '车间删除', 2036, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workshop:remove', '#', 'admin', NULL, '2022-05-07 16:42:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2042, '仓储管理', 0, 6, 'mes/wm', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'zip', 'admin', NULL, '2022-05-07 21:06:28', 'admin', NULL, '2024-04-25 04:35:37', '');
INSERT INTO `sys_menu` VALUES (2043, '仓库设置', 2042, 1, 'warehouse', 'mes/wm/warehouse/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:warehouse', 'dict', 'admin', NULL, '2022-05-07 21:07:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2044, '仓库清单查询', 2043, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:warehouse:list', '#', 'admin', NULL, '2022-05-07 21:08:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2045, '仓库查看', 2043, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:warehouse:query', '#', 'admin', NULL, '2022-05-07 21:08:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2046, '仓库新增', 2043, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:warehouse:add', '#', 'admin', NULL, '2022-05-07 21:09:29', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2047, '仓库编辑', 2043, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:warehouse:edit', '#', 'admin', NULL, '2022-05-07 21:09:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2048, '仓库删除', 2043, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:warehouse:remove', '#', 'admin', NULL, '2022-05-07 21:10:13', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2049, '库区列表查询', 2043, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:location:list', '#', 'admin', NULL, '2022-05-08 18:19:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2050, '库区查看', 2043, 7, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:location:query', '#', 'admin', NULL, '2022-05-08 18:20:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2051, '库区新增', 2043, 8, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:location:add', '#', 'admin', NULL, '2022-05-08 18:20:57', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2052, '库区编辑', 2043, 9, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:location:edit', '#', 'admin', NULL, '2022-05-08 18:21:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2053, '库区删除', 2043, 10, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:location:remove', '#', 'admin', NULL, '2022-05-08 18:21:57', 'admin', NULL, '2022-05-08 18:24:34', '');
INSERT INTO `sys_menu` VALUES (2054, '库位列表查询', 2043, 11, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:area:list', '#', 'admin', NULL, '2022-05-08 18:22:30', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2055, '库位查看', 2043, 12, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:area:query', '#', 'admin', NULL, '2022-05-08 18:22:54', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2056, '库位新增', 2043, 13, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:area:add', '#', 'admin', NULL, '2022-05-08 18:23:17', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2057, '库位编辑', 2043, 14, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:area:edit', '#', 'admin', NULL, '2022-05-08 18:23:43', 'admin', NULL, '2022-05-08 18:23:53', '');
INSERT INTO `sys_menu` VALUES (2058, '库位删除', 2043, 15, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:area:remove', '#', 'admin', NULL, '2022-05-08 18:24:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2059, '设备管理', 0, 7, 'mes/dv', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'redis', 'admin', NULL, '2022-05-08 19:10:32', 'admin', NULL, '2024-04-25 04:57:52', '');
INSERT INTO `sys_menu` VALUES (2060, '设备类型设置', 2059, 1, 'machinerytype', 'mes/dv/machinerytype/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:dv:machinerytype', 'swagger', 'admin', NULL, '2022-05-08 19:11:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2061, '设备类型列表', 2060, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinerytype:list', '#', 'admin', NULL, '2022-05-08 19:12:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2062, '设备类型查看', 2060, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinerytype:query', '#', 'admin', NULL, '2022-05-08 19:12:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2063, '设备类型新增', 2060, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinerytype:add', '#', 'admin', NULL, '2022-05-08 19:13:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2064, '设备类型编辑', 2060, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinerytype:edit', '#', 'admin', NULL, '2022-05-08 19:13:33', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2065, '设备类型删除', 2060, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinerytype:remove', '#', 'admin', NULL, '2022-05-08 19:13:59', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2066, '设备台账', 2059, 2, 'machinery', 'mes/dv/machinery/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:dv:machinery', 'system', 'admin', NULL, '2022-05-08 21:28:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2067, '设备列表查询', 2066, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinery:list', '#', 'admin', NULL, '2022-05-08 21:29:14', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2068, '设备查看', 2066, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinery:query', '#', 'admin', NULL, '2022-05-08 21:29:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2069, '设备新增', 2066, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinery:add', '#', 'admin', NULL, '2022-05-08 21:30:00', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2070, '设备编辑', 2066, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinery:edit', '#', 'admin', NULL, '2022-05-08 21:30:16', 'admin', NULL, '2022-05-08 21:30:40', '');
INSERT INTO `sys_menu` VALUES (2071, '设备删除', 2066, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:machinery:remove', '#', 'admin', NULL, '2022-05-08 21:30:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2072, '生产管理', 0, 5, 'mes/pro', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'switch', 'admin', NULL, '2022-05-09 10:58:07', 'admin', NULL, '2024-04-25 04:57:58', '');
INSERT INTO `sys_menu` VALUES (2073, '生产工单', 2072, 2, 'workorder', 'mes/pro/workorder/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:pro:workorder', 'log', 'admin', NULL, '2022-05-09 10:59:26', 'admin', NULL, '2024-05-08 05:56:27', '');
INSERT INTO `sys_menu` VALUES (2074, '工单列表查询', 2073, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:list', '#', 'admin', NULL, '2022-05-09 11:00:02', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2075, '工单查看', 2073, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:query', '#', 'admin', NULL, '2022-05-09 11:00:24', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2076, '工单新增', 2073, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:add', '#', 'admin', NULL, '2022-05-09 11:00:55', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2077, '工单编辑', 2073, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:edit', '#', 'admin', NULL, '2022-05-09 11:01:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2078, '工单删除', 2073, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:remove', '#', 'admin', NULL, '2022-05-09 11:01:38', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2079, 'BOM列表查询', 2001, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:list', '#', 'admin', NULL, '2022-05-10 10:17:25', 'admin', NULL, '2023-11-07 21:36:31', '');
INSERT INTO `sys_menu` VALUES (2080, 'BOM查看', 2001, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:query', '#', 'admin', NULL, '2022-05-10 10:17:55', 'admin', NULL, '2023-11-07 21:36:37', '');
INSERT INTO `sys_menu` VALUES (2081, 'BOM新增', 2001, 7, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:add', '#', 'admin', NULL, '2022-05-10 10:18:22', 'admin', NULL, '2023-11-07 21:36:43', '');
INSERT INTO `sys_menu` VALUES (2082, 'BOM编辑', 2001, 8, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:edit', '#', 'admin', NULL, '2022-05-10 10:18:44', 'admin', NULL, '2023-11-07 21:36:50', '');
INSERT INTO `sys_menu` VALUES (2083, 'BOM删除', 2001, 9, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:mditem:remove', '#', 'admin', NULL, '2022-05-10 10:19:05', 'admin', NULL, '2023-11-07 21:36:57', '');
INSERT INTO `sys_menu` VALUES (2084, '工单BOM列表查询', 2073, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:list', '#', 'admin', NULL, '2022-05-10 16:17:25', 'admin', NULL, '2023-11-07 21:40:24', '');
INSERT INTO `sys_menu` VALUES (2085, '工单BOM查看', 2073, 7, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:query', '#', 'admin', NULL, '2022-05-10 16:17:58', 'admin', NULL, '2023-11-07 21:40:32', '');
INSERT INTO `sys_menu` VALUES (2086, '工单BOM编辑', 2073, 8, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:edit', '#', 'admin', NULL, '2022-05-10 16:18:28', 'admin', NULL, '2023-11-07 21:40:42', '');
INSERT INTO `sys_menu` VALUES (2087, '工单BOM删除', 2073, 9, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:workorder:remove', '#', 'admin', NULL, '2022-05-10 16:18:59', 'admin', NULL, '2023-11-07 21:40:49', '');
INSERT INTO `sys_menu` VALUES (2088, '工作站', 2000, 9, 'workstation', 'mes/md/workstation/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:md:workstation', 'job', 'admin', NULL, '2022-05-10 21:44:36', 'admin', NULL, '2024-04-25 04:51:44', '');
INSERT INTO `sys_menu` VALUES (2089, '工作站列表查询', 2088, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:list', '#', 'admin', NULL, '2022-05-10 21:45:11', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2090, '工作站查看', 2088, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:query', '#', 'admin', NULL, '2022-05-10 21:45:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2091, '工作站新增', 2088, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:add', '#', 'admin', NULL, '2022-05-10 21:45:48', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2092, '工作站编辑', 2088, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:edit', '#', 'admin', NULL, '2022-05-10 21:46:05', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2093, '工作站删除', 2088, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:remove', '#', 'admin', NULL, '2022-05-10 21:46:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2094, '工作站导出', 2088, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:md:workstation:export', '#', 'admin', NULL, '2022-05-10 21:47:04', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2095, '工装夹具管理', 0, 8, 'tm', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'lock', 'admin', NULL, '2022-05-11 00:07:13', 'admin', NULL, '2024-04-25 04:35:54', '');
INSERT INTO `sys_menu` VALUES (2096, '类型设置', 2095, 1, 'tooltype', 'mes/tm/tooltype/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:tm:tooltype', 'tree', 'admin', NULL, '2022-05-11 00:08:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2097, '类型列表查询', 2096, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tooltype:list', '#', 'admin', NULL, '2022-05-11 00:09:01', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2098, '类型查看', 2096, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tooltype:query', '#', 'admin', NULL, '2022-05-11 00:09:27', 'admin', NULL, '2022-05-11 00:09:48', '');
INSERT INTO `sys_menu` VALUES (2099, '类型新增', 2096, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tooltype:add', '#', 'admin', NULL, '2022-05-11 00:10:14', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2100, '类型编辑', 2096, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tooltype:edit', '#', 'admin', NULL, '2022-05-11 00:10:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2101, '类型删除', 2096, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tooltype:remove', '#', 'admin', NULL, '2022-05-11 00:10:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2102, '工装夹具台账', 2095, 2, 'tool', 'mes/tm/tool/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:tm:tool', 'date-range', 'admin', NULL, '2022-05-11 21:23:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2103, '工装夹具列表查询', 2102, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:list', '#', 'admin', NULL, '2022-05-11 21:23:53', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2104, '工装夹具查看', 2102, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:query', '#', 'admin', NULL, '2022-05-11 21:24:15', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2105, '工装夹具新增', 2102, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:add', '#', 'admin', NULL, '2022-05-11 21:24:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2106, '工装夹具编辑', 2102, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:edit', '#', 'admin', NULL, '2022-05-11 21:24:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2107, '工装夹具删除', 2102, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:remove', '#', 'admin', NULL, '2022-05-11 21:25:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2108, '工装夹具导出', 2102, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:tm:tool:export', '#', 'admin', NULL, '2022-05-11 21:25:46', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2109, '工序设置', 2000, 6, 'process', 'mes/pro/process/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:pro:process', 'druid', 'admin', NULL, '2022-05-12 00:03:58', 'admin', NULL, '2024-04-25 04:51:51', '');
INSERT INTO `sys_menu` VALUES (2110, '工序列表查询', 2109, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:list', '#', 'admin', NULL, '2022-05-12 00:04:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2111, '工序查看', 2109, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:query', '#', 'admin', NULL, '2022-05-12 00:04:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2112, '工序新增', 2109, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:add', '#', 'admin', NULL, '2022-05-12 00:04:54', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2113, '工序编辑', 2109, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:edit', '#', 'admin', NULL, '2022-05-12 00:05:10', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2114, '工序删除', 2109, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:remove', '#', 'admin', NULL, '2022-05-12 00:05:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2115, '工序导出', 2109, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:process:export', '#', 'admin', NULL, '2022-05-12 00:05:41', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2116, '工艺流程', 2000, 7, 'proroute', 'mes/pro/proroute/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:pro:proroute', 'nested', 'admin', NULL, '2022-05-12 23:08:26', 'admin', NULL, '2024-04-25 04:51:55', '');
INSERT INTO `sys_menu` VALUES (2117, '工艺流程列表查询', 2116, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:list', '#', 'admin', NULL, '2022-05-12 23:08:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2118, '工艺流程查看', 2116, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:query', '#', 'admin', NULL, '2022-05-12 23:09:13', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2119, '工艺流程新增', 2116, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:add', '#', 'admin', NULL, '2022-05-12 23:09:36', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2120, '工艺流程编辑', 2116, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:edit', '#', 'admin', NULL, '2022-05-12 23:09:53', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2121, '工艺流程删除', 2116, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:remove', '#', 'admin', NULL, '2022-05-12 23:10:11', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2122, '工艺流程导出', 2116, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:proroute:export', '#', 'admin', NULL, '2022-05-12 23:10:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2123, '生产排产', 2072, 4, 'proschedule', 'mes/pro/schedule/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:pro:protask', 'build', 'admin', NULL, '2022-05-15 15:01:21', 'admin', NULL, '2023-11-07 21:48:57', '');
INSERT INTO `sys_menu` VALUES (2124, '质量管理', 0, 9, 'mes/qc', NULL, NULL, 1, 0, 'M', '1', '0', '0', '', 'time-range', 'admin', NULL, '2022-05-17 21:51:23', 'admin', NULL, '2022-08-13 21:45:33', '');
INSERT INTO `sys_menu` VALUES (2125, '检测项设置', 2124, 2, 'qcindex', 'mes/qc/qcindex/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:qcindex', 'checkbox', 'admin', NULL, '2022-05-17 21:52:33', 'admin', NULL, '2022-05-19 11:26:35', '');
INSERT INTO `sys_menu` VALUES (2126, '检测项列表查询', 2125, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:list', '#', 'admin', NULL, '2022-05-17 21:53:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2127, '检测项查看', 2125, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:query', '#', 'admin', NULL, '2022-05-17 21:53:36', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2128, '检测项新增', 2125, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:add', '#', 'admin', NULL, '2022-05-17 21:54:04', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2129, '检测项编辑', 2125, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:edit', '#', 'admin', NULL, '2022-05-17 21:54:24', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2130, '检测项删除', 2125, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:remove', '#', 'admin', NULL, '2022-05-17 21:54:50', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2131, '检测项导出', 2125, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcindex:export', '#', 'admin', NULL, '2022-05-17 21:55:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2132, '检测模板', 2124, 3, 'qctemplate', 'mes/qc/qctemplate/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:qctemplate', 'example', 'admin', NULL, '2022-05-17 22:23:48', 'admin', NULL, '2022-05-19 11:26:41', '');
INSERT INTO `sys_menu` VALUES (2133, '检测模板列表查询', 2132, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qctemplate:list', '#', 'admin', NULL, '2022-05-17 22:24:05', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2134, '检测模板查看', 2132, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qctemplate:query', '#', 'admin', NULL, '2022-05-17 22:24:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2135, '检测模板新增', 2132, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qctemplate:add', '#', 'admin', NULL, '2022-05-17 22:24:50', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2136, '检测模板编辑', 2132, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qctemplate:edit', '#', 'admin', NULL, '2022-05-17 22:25:09', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2137, '检测模板删除', 2132, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qctemplate:remove', '#', 'admin', NULL, '2022-05-17 22:25:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2138, '常见缺陷', 2124, 1, 'qcdefect', 'mes/qc/qcdefect/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:qcdefect', 'bug', 'admin', NULL, '2022-05-19 11:26:15', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2139, '缺陷列表查询', 2138, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcdefect:list', '#', 'admin', NULL, '2022-05-19 11:27:04', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2140, '缺陷查看', 2138, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcdefect:query', '#', 'admin', NULL, '2022-05-19 11:27:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2141, '缺陷新增', 2138, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcdefect:add', '#', 'admin', NULL, '2022-05-19 11:27:47', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2142, '缺陷编辑', 2138, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcdefect:edit', '#', 'admin', NULL, '2022-05-19 11:28:11', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2143, '缺陷删除', 2138, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:qcdefect:remove', '#', 'admin', NULL, '2022-05-19 11:28:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2144, '来料检验', 2124, 5, 'iqc', 'mes/qc/iqc/iqc', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:iqc', 'edit', 'admin', NULL, '2022-05-19 16:32:24', 'admin', NULL, '2023-10-10 16:54:12', '');
INSERT INTO `sys_menu` VALUES (2145, '来料检验单列表查询', 2144, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:list', '#', 'admin', NULL, '2022-05-19 16:33:18', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2146, '来料检验单查看', 2144, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:query', '#', 'admin', NULL, '2022-05-19 16:33:41', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2147, '来料检验单新增', 2144, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:add', '#', 'admin', NULL, '2022-05-19 16:34:01', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2148, '来料检验单编辑', 2144, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:edit', '#', 'admin', NULL, '2022-05-19 16:34:19', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2149, '来料检验单删除', 2144, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:remove', '#', 'admin', NULL, '2022-05-19 16:34:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2150, '来料检验单导出', 2144, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:iqc:export', '#', 'admin', NULL, '2022-05-19 16:34:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2151, '采购入库', 2042, 3, 'itemrecpt', 'mes/wm/itemrecpt/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:itemrecpt', 'form', 'admin', NULL, '2022-05-22 17:35:11', 'admin', NULL, '2022-10-26 22:57:52', '');
INSERT INTO `sys_menu` VALUES (2152, '物料入库单列表查询', 2151, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:itemrecpt:list', '#', 'admin', NULL, '2022-05-22 17:35:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2153, '物料入库单查看', 2151, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:itemrecpt:query', '#', 'admin', NULL, '2022-05-22 17:35:53', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2154, '物料入库单新增', 2151, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:itemrecpt:add', '#', 'admin', NULL, '2022-05-22 17:36:09', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2155, '物料入库单编辑', 2151, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:itemrecpt:edit', '#', 'admin', NULL, '2022-05-22 17:36:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2156, '物料入库单删除', 2151, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:itemrecpt:remove', '#', 'admin', NULL, '2022-05-22 17:36:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2157, '库存现有量', 2042, 2, 'wmstock', 'mes/wm/wmstock/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:wmstock', 'chart', 'admin', NULL, '2022-05-30 23:36:40', 'admin', NULL, '2022-06-14 16:22:19', '');
INSERT INTO `sys_menu` VALUES (2158, '库存现有量列表查询', 2157, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:wmstock:list', '#', 'admin', NULL, '2022-05-30 23:37:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2159, '库存现有量导出', 2157, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:wmstock:export', '#', 'admin', NULL, '2022-05-30 23:37:30', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2160, '排班管理', 0, 10, 'cal', NULL, NULL, 1, 0, 'M', '1', '0', '0', NULL, 'peoples', 'admin', NULL, '2022-06-05 21:52:34', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2161, '班组设置    ', 2160, 1, 'team', 'mes/cal/team/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:cal:team', 'peoples', 'admin', NULL, '2022-06-05 21:53:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2162, '班组列表查看', 2161, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:team:list', '#', 'admin', NULL, '2022-06-05 21:56:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2163, '班组查看', 2161, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:team:query', '#', 'admin', NULL, '2022-06-05 21:57:20', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2164, '班组新增', 2161, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:team:add', '#', 'admin', NULL, '2022-06-05 21:57:40', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2165, '班组编辑', 2161, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:team:edit', '#', 'admin', NULL, '2022-06-05 21:57:59', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2168, '排班计划', 2160, 2, 'plan', 'mes/cal/plan/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:cal:calplan', 'build', 'admin', NULL, '2022-06-06 21:47:04', 'admin', NULL, '2023-11-07 22:04:31', '');
INSERT INTO `sys_menu` VALUES (2169, '排班计划列表查询', 2168, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calplan:list', '#', 'admin', NULL, '2022-06-06 21:47:27', 'admin', NULL, '2023-11-07 22:05:12', '');
INSERT INTO `sys_menu` VALUES (2170, '排班计划查看', 2168, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calplan:query', '#', 'admin', NULL, '2022-06-06 21:47:49', 'admin', NULL, '2023-11-07 22:05:21', '');
INSERT INTO `sys_menu` VALUES (2171, '排班计划新增', 2168, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calplan:add', '#', 'admin', NULL, '2022-06-06 21:48:13', 'admin', NULL, '2023-11-07 22:05:31', '');
INSERT INTO `sys_menu` VALUES (2172, '排班计划编辑', 2168, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calplan:edit', '#', 'admin', NULL, '2022-06-06 21:48:30', 'admin', NULL, '2023-11-07 22:05:39', '');
INSERT INTO `sys_menu` VALUES (2173, '排班计划删除', 2168, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calplan:remove', '#', 'admin', NULL, '2022-06-06 21:48:49', 'admin', NULL, '2023-11-07 22:05:48', '');
INSERT INTO `sys_menu` VALUES (2174, '节假日设置', 2160, 3, 'holiday', 'mes/cal/holiday/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:cal:calholiday', 'date', 'admin', NULL, '2022-06-07 23:25:52', 'admin', NULL, '2023-11-07 22:02:01', '');
INSERT INTO `sys_menu` VALUES (2175, '节假日查看', 2174, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calholiday:query', '#', 'admin', NULL, '2022-06-07 23:26:18', 'admin', NULL, '2023-11-07 22:03:13', '');
INSERT INTO `sys_menu` VALUES (2176, '节假日设置', 2174, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calholiday:edit', '#', 'admin', NULL, '2022-06-07 23:26:41', 'admin', NULL, '2023-11-07 22:03:34', '');
INSERT INTO `sys_menu` VALUES (2177, '排班日历', 2160, 4, 'calendar', 'mes/cal/calendar/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:cal:calendar', 'date-range', 'admin', NULL, '2022-06-09 21:08:06', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2178, '查看排班日历', 2177, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calendar:list', '#', 'admin', NULL, '2022-06-09 21:08:52', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2179, '供应商退货', 2042, 4, 'rtvendor', 'mes/wm/rtvendor/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:rtvendor', 'link', 'admin', NULL, '2022-06-13 15:29:43', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2180, '退货单列表查询', 2179, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtvendor:list', '#', 'admin', NULL, '2022-06-13 15:30:45', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2181, '退货单信息查看', 2179, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtvendor:query', '#', 'admin', NULL, '2022-06-13 15:31:13', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2182, '退货单新增', 2179, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtvendor:add', '#', 'admin', NULL, '2022-06-13 15:31:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2183, '退货单编辑', 2179, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtvendor:edit', '#', 'admin', NULL, '2022-06-13 15:31:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2184, '退货单删除', 2179, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtvendor:remove', '#', 'admin', NULL, '2022-06-13 15:32:06', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2185, '点检保养项目', 2059, 3, 'dvsubject', 'mes/dv/subject/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:dv:dvsubject', 'cascader', 'admin', NULL, '2022-06-16 20:17:37', 'admin', NULL, '2022-06-16 20:20:44', '');
INSERT INTO `sys_menu` VALUES (2186, '项目列表查询', 2185, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:dvsubject:list', '#', 'admin', NULL, '2022-06-16 20:18:10', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2187, '项目信息查看', 2185, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:dvsubject:query', '#', 'admin', NULL, '2022-06-16 20:18:37', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2188, '项目信息新增', 2185, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:dvsubject:add', '#', 'admin', NULL, '2022-06-16 20:18:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2189, '项目信息编辑', 2185, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:dvsubject:edit', '#', 'admin', NULL, '2022-06-16 20:19:18', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2190, '项目删除', 2185, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:dvsubject:remove', '#', 'admin', NULL, '2022-06-16 20:19:54', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2191, '点检保养计划', 2059, 4, 'checkplan', 'mes/dv/checkplan/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:dv:checkplan', 'build', 'admin', NULL, '2022-06-16 21:35:31', 'admin', NULL, '2022-09-01 22:17:50', '');
INSERT INTO `sys_menu` VALUES (2192, '点检计划列表查询', 2191, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:checkplan:list', '#', 'admin', NULL, '2022-06-16 21:36:03', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2193, '点检计划查看', 2191, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:checkplan:query', '#', 'admin', NULL, '2022-06-16 21:36:34', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2194, '点检计划新增', 2191, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:checkplan:add', '#', 'admin', NULL, '2022-06-16 21:36:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2195, '点检计划编辑', 2191, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:checkplan:edit', '#', 'admin', NULL, '2022-06-16 21:37:15', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2196, '点检计划删除', 2191, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:checkplan:remove', '#', 'admin', NULL, '2022-06-16 21:37:35', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2197, '生产领料', 2042, 5, 'issue', 'mes/wm/issue/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:issueheader', 'edit', 'admin', NULL, '2022-07-17 19:24:17', 'admin', NULL, '2022-08-22 13:52:30', '');
INSERT INTO `sys_menu` VALUES (2198, '生产领料新增', 2197, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:issueheader:add', '#', 'admin', NULL, '2022-07-17 19:24:43', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2199, '生产领料列表查询', 2197, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:issueheader:list', '#', 'admin', NULL, '2022-07-17 19:25:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2200, '生产领料信息查看', 2197, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:issueheader:query', '#', 'admin', NULL, '2022-07-17 19:25:37', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2201, '生产领料编辑', 2197, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:issueheader:edit', '#', 'admin', NULL, '2022-07-17 19:25:54', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2202, '生产领料删除', 2197, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:issueheader:remove', '#', 'admin', NULL, '2022-07-17 19:26:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2203, '赋码管理', 2042, 11, 'barcode', 'mes/wm/barcode/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:barcode', 'barcode', 'admin', NULL, '2022-08-01 11:19:01', 'admin', NULL, '2022-11-28 18:51:21', '');
INSERT INTO `sys_menu` VALUES (2204, '条码清单', 2203, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:barcode:list', '#', 'admin', NULL, '2022-08-01 11:19:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2205, '维修单', 2059, 5, 'repair', 'mes/dv/repair/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:dv:repair', 'system', 'admin', NULL, '2022-08-06 15:19:26', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2206, '维修单列表查询', 2205, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:repair:list', '#', 'admin', NULL, '2022-08-06 15:19:59', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2207, '维修单查看', 2205, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:repair:query', '#', 'admin', NULL, '2022-08-06 15:20:19', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2208, '维修单修改', 2205, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:repair:edit', '#', 'admin', NULL, '2022-08-06 15:20:41', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2209, '维修单删除', 2205, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:dv:repair:remove', '#', 'admin', NULL, '2022-08-06 15:20:58', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2210, '组织架构', 0, 0, 'user', NULL, NULL, 1, 0, 'M', '1', '0', '0', NULL, 'tree', 'admin', NULL, '2022-08-13 21:41:16', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2214, '过程检验', 2124, 6, 'ipqc', 'mes/qc/ipqc/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:ipqc', 'job', 'admin', NULL, '2022-08-29 20:46:27', 'admin', NULL, '2023-10-10 16:54:18', '');
INSERT INTO `sys_menu` VALUES (2215, '过程检验单列表查询', 2214, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:ipqc:list', '#', 'admin', NULL, '2022-08-29 20:47:26', 'admin', NULL, '2022-08-29 20:47:46', '');
INSERT INTO `sys_menu` VALUES (2216, '过程检验单详情查看', 2214, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:ipqc:query', '#', 'admin', NULL, '2022-08-29 20:48:10', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2217, '过程检验单新增', 2214, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:ipqc:add', '#', 'admin', NULL, '2022-08-29 20:48:39', 'admin', NULL, '2022-08-29 20:48:56', '');
INSERT INTO `sys_menu` VALUES (2218, '过程检验单编辑', 2214, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:ipqc:edit', '#', 'admin', NULL, '2022-08-29 20:49:22', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2219, '过程检验单删除', 2214, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:ipqc:remove', '#', 'admin', NULL, '2022-08-29 20:49:44', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2220, '出货检验', 2124, 7, 'oqc', 'mes/qc/oqc/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:oqc', 'guide', 'admin', NULL, '2022-08-31 22:17:39', 'admin', NULL, '2023-10-10 16:54:24', '');
INSERT INTO `sys_menu` VALUES (2221, '出货检验单列表查询', 2220, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:oqc:list', '#', 'admin', NULL, '2022-08-31 22:18:20', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2222, '出货检验单明细查看', 2220, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:oqc:query', '#', 'admin', NULL, '2022-08-31 22:18:47', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2223, '出货检验单新增', 2220, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:oqc:add', '#', 'admin', NULL, '2022-08-31 22:19:10', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2224, '出货检验单编辑', 2220, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:oqc:edit', '#', 'admin', NULL, '2022-08-31 22:19:27', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2225, '出货检验单删除', 2220, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:oqc:remove', '#', 'admin', NULL, '2022-08-31 22:19:45', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2226, '生产退料', 2042, 6, 'rtissue', 'mes/wm/rtissue/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:rtissue', 'logininfor', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2024-06-05 08:11:00', '生产退料单头菜单');
INSERT INTO `sys_menu` VALUES (2227, '生产退料单列表查询', 2226, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtissue:list', '#', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2022-09-15 22:59:42', '');
INSERT INTO `sys_menu` VALUES (2228, '生产退料单头新增', 2226, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtissue:add', '#', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2022-09-15 22:59:55', '');
INSERT INTO `sys_menu` VALUES (2229, '生产退料单头修改', 2226, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtissue:edit', '#', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2022-09-15 23:00:06', '');
INSERT INTO `sys_menu` VALUES (2230, '生产退料单头删除', 2226, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtissue:remove', '#', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2022-09-15 23:00:17', '');
INSERT INTO `sys_menu` VALUES (2231, '生产退料明细查看', 2226, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtissue:query', '#', 'admin', NULL, '2022-09-03 23:39:43', 'admin', NULL, '2022-09-15 23:00:44', '');
INSERT INTO `sys_menu` VALUES (2232, '产品入库', 2042, 7, 'productrecpt', 'mes/wm/productrecpt/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:productrecpt', 'shopping', 'admin', NULL, '2022-09-22 21:02:47', 'admin', NULL, '2022-10-06 18:20:41', '');
INSERT INTO `sys_menu` VALUES (2233, '产品入库单列表查询', 2232, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productrecpt:list', '#', 'admin', NULL, '2022-09-22 21:03:12', 'admin', NULL, '2023-11-13 22:09:51', '');
INSERT INTO `sys_menu` VALUES (2234, '产品入库单明细查看', 2232, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productrecpt:query', '#', 'admin', NULL, '2022-09-22 21:03:34', 'admin', NULL, '2023-11-13 22:10:00', '');
INSERT INTO `sys_menu` VALUES (2235, '产品入库单编辑', 2232, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productrecpt:edit', '#', 'admin', NULL, '2022-09-22 21:03:57', 'admin', NULL, '2023-11-13 22:10:09', '');
INSERT INTO `sys_menu` VALUES (2236, '产品入库单删除', 2232, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productrecpt:remove', '#', 'admin', NULL, '2022-09-22 21:04:15', 'admin', NULL, '2023-11-13 22:10:19', '');
INSERT INTO `sys_menu` VALUES (2237, '生产报工', 2072, 5, 'feedback', 'mes/pro/feedback/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:pro:feedback', 'chart', 'admin', NULL, '2022-10-03 20:13:20', 'admin', NULL, '2022-10-03 20:13:43', '');
INSERT INTO `sys_menu` VALUES (2238, '销售出库', 2042, 8, 'productsalse', 'mes/wm/productsalse/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:productsalse', 'guide', 'admin', NULL, '2022-10-05 18:54:42', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2239, '出库单列表查询', 2238, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productsalse:list', '#', 'admin', NULL, '2022-10-05 18:55:18', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2240, '销售出库单明细查看', 2238, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productsalse:query', '#', 'admin', NULL, '2022-10-05 18:55:43', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2241, '销售出库单新增', 2238, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productsalse:add', '#', 'admin', NULL, '2022-10-05 18:56:04', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2242, '销售出库单编辑', 2238, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productsalse:edit', '#', 'admin', NULL, '2022-10-05 18:56:21', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2243, '销售出库单删除', 2238, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:productsalse:remove', '#', 'admin', NULL, '2022-10-05 18:56:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2244, '销售退货', 2042, 9, 'rtsalse', 'mes/wm/rtsalse/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:rtsalse', 'link', 'admin', NULL, '2022-10-06 21:28:47', 'admin', NULL, '2022-10-06 22:02:18', '');
INSERT INTO `sys_menu` VALUES (2245, '退货单列表查询', 2244, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtsalse:list', '#', 'admin', NULL, '2022-10-06 21:29:27', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2246, '销售退货单明细查看', 2244, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtsalse:query', '#', 'admin', NULL, '2022-10-06 21:29:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2247, '销售退货单新增', 2244, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtsalse:add', '#', 'admin', NULL, '2022-10-06 21:30:18', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2248, '销售退货单编辑', 2244, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtsalse:edit', '#', 'admin', NULL, '2022-10-06 21:30:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2249, '销售退货单删除', 2244, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:rtsalse:remove', '#', 'admin', NULL, '2022-10-06 21:31:03', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2250, '装箱管理', 2042, 12, 'package', 'mes/wm/package/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:package', 'table', 'admin', NULL, '2022-10-10 12:48:22', 'admin', NULL, '2022-11-28 18:51:27', '');
INSERT INTO `sys_menu` VALUES (2251, '装箱单列表查询', 2250, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:package:list', '#', 'admin', NULL, '2022-10-10 12:49:03', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2252, '装箱单明细查看', 2250, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:package:query', '#', 'admin', NULL, '2022-10-10 12:49:26', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2253, '装箱单新增', 2250, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:package:add', '#', 'admin', NULL, '2022-10-10 12:49:42', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2254, '装箱单编辑', 2250, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:package:edit', '#', 'admin', NULL, '2022-10-10 12:50:01', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2255, '装箱单删除', 2250, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:package:remove', '#', 'admin', NULL, '2022-10-10 12:50:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2257, '报表管理', 0, 11, 'report', NULL, NULL, 1, 0, 'M', '1', '0', '0', NULL, 'table', 'admin', NULL, '2022-10-17 22:56:30', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2258, '报表清单', 2257, 1, 'report', 'mes/report/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:report', 'edit', 'admin', NULL, '2022-10-17 22:57:20', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2259, '报表设计', 2257, 2, 'designer', 'mes/report/designer/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:designer', 'edit', 'admin', NULL, '2022-10-17 22:58:47', 'admin', NULL, '2022-10-17 22:59:19', '');
INSERT INTO `sys_menu` VALUES (2260, '转移调拨', 2042, 10, 'transfer', 'mes/wm/transfer/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:transfer', 'swagger', 'admin', NULL, '2022-11-28 18:51:12', 'admin', NULL, '2022-12-04 16:35:45', '');
INSERT INTO `sys_menu` VALUES (2261, '转移单查询列表', 2260, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:transfer:list', '#', 'admin', NULL, '2022-11-28 18:52:03', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2262, '转移单明细查看', 2260, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:transfer:query', '#', 'admin', NULL, '2022-11-28 18:52:40', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2263, '转移单新增', 2260, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:transfer:add', '#', 'admin', NULL, '2022-11-28 18:53:00', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2264, '转移单更新', 2260, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:transfer:edit', '#', 'admin', NULL, '2022-11-28 18:58:31', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2265, '转移单删除', 2260, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:transfer:remove', '#', 'admin', NULL, '2022-11-28 18:58:53', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2266, 'SN码', 2042, 13, 'sn', 'mes/wm/sn/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:sn', 'icon', 'admin', NULL, '2022-12-09 09:10:20', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2267, 'SN生成列表', 2266, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:sn:list', '#', 'admin', NULL, '2022-12-09 09:10:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2268, 'SN明细查看', 2266, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:sn:query', '#', 'admin', NULL, '2022-12-09 09:11:29', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2269, 'SN生成', 2266, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:sn:add', '#', 'admin', NULL, '2022-12-09 09:12:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2270, 'SN码删除', 2266, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:sn:remove', '#', 'admin', NULL, '2022-12-09 09:12:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2271, '消息', 1, 11, 'message', 'system/message/index', NULL, 1, 0, 'C', '1', '0', '0', 'system:message', 'message', 'admin', NULL, '2023-03-06 19:51:24', 'admin', NULL, '2023-03-06 19:53:15', '');
INSERT INTO `sys_menu` VALUES (2272, '消息列表查看', 2271, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:message:list', '#', 'admin', NULL, '2023-03-06 19:52:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2273, '消息更新', 2271, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:message:edit', '#', 'admin', NULL, '2023-03-06 19:53:42', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2274, '消息详情查看', 2271, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:message:query', '#', 'admin', NULL, '2023-03-06 19:54:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2275, '消息删除', 2271, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'system:message:remove', '#', 'admin', NULL, '2023-03-06 19:54:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2276, '打印管理', 0, 12, 'print', NULL, NULL, 1, 0, 'M', '1', '0', '0', NULL, 'barcode', 'admin', NULL, '2023-09-01 11:51:52', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2277, '打印机配置', 2276, 1, 'printerconfig', 'print/printerconfig/index', NULL, 1, 0, 'C', '1', '0', '0', '', 'system', 'admin', NULL, '2023-09-01 11:52:50', 'admin', NULL, '2023-09-01 11:53:09', '');
INSERT INTO `sys_menu` VALUES (2278, '打印模板', 2276, 2, 'printtemplate', 'print/printtemplate/index', NULL, 1, 0, 'C', '1', '0', '0', '', 'documentation', 'admin', NULL, '2023-09-01 16:39:32', 'admin', NULL, '2023-09-01 18:48:47', '');
INSERT INTO `sys_menu` VALUES (2279, '待检任务', 2124, 4, 'pendinginspect', 'mes/qc/pendinginspect/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:qc:pending', 'form', 'admin', NULL, '2023-10-10 16:54:06', 'admin', NULL, '2023-10-10 16:54:47', '');
INSERT INTO `sys_menu` VALUES (2280, '外协发料', 2042, 15, 'outsourceissue', 'mes/wm/outsourceissue/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:outsourceissue', 'drag', 'admin', NULL, '2023-10-30 11:35:31', 'admin', NULL, '2023-10-31 16:57:17', '');
INSERT INTO `sys_menu` VALUES (2281, '外协入库', 2042, 16, 'outsourcerecpt', 'mes/wm/outsourcerecpt/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:outsourcerecpt', 'form', 'admin', NULL, '2023-10-30 11:36:40', 'admin', NULL, '2023-10-31 16:57:22', '');
INSERT INTO `sys_menu` VALUES (2282, '外协发料单列表查询', 2280, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourceissue:list', '#', 'admin', NULL, '2023-10-30 11:39:23', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2283, '外协发料单新增', 2280, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourceissue:add', '#', 'admin', NULL, '2023-10-30 11:42:10', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2284, '外协发料单编辑', 2280, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourceissue:edit', '#', 'admin', NULL, '2023-10-30 11:42:52', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2285, '外协发料单明细查看', 2280, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourceissue:query', '#', 'admin', NULL, '2023-10-30 11:43:21', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2286, '外协发料单删除', 2280, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourceissue:remove', '#', 'admin', NULL, '2023-10-30 11:43:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2287, '外协入库单列表查询', 2281, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourcerecpt:list', '#', 'admin', NULL, '2023-10-30 20:04:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2288, '外协入库单新增', 2281, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourcerecpt:add', '#', 'admin', NULL, '2023-10-30 20:05:28', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2289, '外协入库单编辑', 2281, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourcerecpt:edit', '#', 'admin', NULL, '2023-10-30 20:05:57', 'admin', NULL, '2023-10-30 20:06:10', '');
INSERT INTO `sys_menu` VALUES (2290, '外协入库单明细查看', 2281, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourcerecpt:query', '#', 'admin', NULL, '2023-10-30 20:06:42', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2291, '外协入库单删除', 2281, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:outsourcerecpt:remove', '#', 'admin', NULL, '2023-10-30 20:07:07', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2292, '库存盘点', 2042, 14, 'stocktaking', 'mes/wm/stocktaking/index', NULL, 1, 0, 'C', '1', '0', '0', 'mes:wm:stocktaking', 'list', 'admin', NULL, '2023-10-31 16:58:01', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2293, '库存盘点列表查询', 2292, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:stocktaking:list', '#', 'admin', NULL, '2023-10-31 16:58:25', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2294, '库存盘点明细查看', 2292, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:stocktaking:query', '#', 'admin', NULL, '2023-10-31 16:58:51', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2295, '排产情况查看', 2123, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:list', '#', 'admin', NULL, '2023-11-07 21:49:26', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2296, '排产编辑', 2123, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:edit', '#', 'admin', NULL, '2023-11-07 21:49:49', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2297, '排产详情查看', 2123, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:query', '#', 'admin', NULL, '2023-11-07 21:50:13', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2298, '新增生产任务', 2123, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:add', '#', 'admin', NULL, '2023-11-07 21:50:53', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2299, '修改生产任务', 2123, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:edit', '#', 'admin', NULL, '2023-11-07 21:51:26', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2300, '删除生产任务', 2123, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:protask:remove', '#', 'admin', NULL, '2023-11-07 21:51:50', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2301, '报工单列表查询', 2237, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:feedback:list', '#', 'admin', NULL, '2023-11-07 21:53:41', 'admin', NULL, '2023-11-13 22:10:44', '');
INSERT INTO `sys_menu` VALUES (2302, '报工单新增', 2237, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:feedback:add', '#', 'admin', NULL, '2023-11-07 21:54:00', 'admin', NULL, '2023-11-13 22:10:49', '');
INSERT INTO `sys_menu` VALUES (2303, '报工单编辑', 2237, 3, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:feedback:edit', '#', 'admin', NULL, '2023-11-07 21:54:16', 'admin', NULL, '2023-11-13 22:10:57', '');
INSERT INTO `sys_menu` VALUES (2304, '报工单查看', 2237, 4, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:feedback:query', '#', 'admin', NULL, '2023-11-07 21:54:34', 'admin', NULL, '2023-11-13 22:11:03', '');
INSERT INTO `sys_menu` VALUES (2305, '报工单删除', 2237, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:pro:feedback:remove', '#', 'admin', NULL, '2023-11-07 21:55:49', 'admin', NULL, '2023-11-13 22:11:10', '');
INSERT INTO `sys_menu` VALUES (2306, '节假日列表查询', 2174, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calholiday:list', '#', 'admin', NULL, '2023-11-07 22:02:30', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2307, '节假日新增', 2174, 2, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:cal:calholiday:add', '#', 'admin', NULL, '2023-11-07 22:03:04', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2308, '查询待检任务清单', 2279, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:pending:list', '#', 'admin', NULL, '2023-11-07 22:11:08', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2309, '缺陷记录列表查询', 2138, 6, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:defectrecord:list', '#', 'admin', NULL, '2023-11-07 22:21:21', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2310, '缺陷记录新增', 2138, 7, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:defectrecord:add', '#', 'admin', NULL, '2023-11-07 22:21:39', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2311, '缺陷记录编辑', 2138, 8, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:defectrecord:edit', '#', 'admin', NULL, '2023-11-07 22:21:56', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2312, '缺陷记录查看', 2138, 9, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:defectrecord:query', '#', 'admin', NULL, '2023-11-07 22:22:15', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2313, '缺陷记录删除', 2138, 10, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:qc:defectrecord:remove', '#', 'admin', NULL, '2023-11-07 22:22:32', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2314, 'SN导出', 2266, 5, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wm:sn:export', '#', 'admin', NULL, '2023-11-07 22:29:41', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2315, '客户订单', 2072, 1, 'clientOrder', 'mes/pro/client-order/index', NULL, 1, 0, 'C', '1', '0', '0', 'pro:clientorder:list', 'money', 'admin', NULL, '2024-05-01 15:43:42', 'admin', NULL, '2024-05-02 13:25:56', '客户订单菜单');
INSERT INTO `sys_menu` VALUES (2316, '客户订单查询', 2315, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorder:query', '#', 'admin', NULL, '2024-05-01 15:43:44', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2317, '客户订单新增', 2315, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorder:add', '#', 'admin', NULL, '2024-05-01 15:43:45', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2318, '客户订单修改', 2315, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorder:edit', '#', 'admin', NULL, '2024-05-01 15:43:46', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2319, '客户订单删除', 2315, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorder:remove', '#', 'admin', NULL, '2024-05-01 15:43:47', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2320, '客户订单导出', 2315, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorder:export', '#', 'admin', NULL, '2024-05-01 15:43:47', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2333, '客户订单材料\n', 3, 1, 'client-order-item', 'mes/pro/client-order-item/index', NULL, 1, 0, 'C', '1', '0', '1', 'pro:clientorderitem:list', '#', 'admin', NULL, '2024-05-07 12:12:12', 'admin', NULL, '2024-05-07 13:20:07', '客户订单材料\n菜单');
INSERT INTO `sys_menu` VALUES (2334, '客户订单材料查询', 2333, 1, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorderitem:query', '#', 'admin', NULL, '2024-05-07 12:12:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2335, '客户订单材料新增', 2333, 2, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorderitem:add', '#', 'admin', NULL, '2024-05-07 12:12:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2336, '客户订单材料修改', 2333, 3, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorderitem:edit', '#', 'admin', NULL, '2024-05-07 12:12:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2337, '客户订单材料删除', 2333, 4, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorderitem:remove', '#', 'admin', NULL, '2024-05-07 12:12:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2338, '客户订单材料导出', 2333, 5, '#', '', NULL, 1, 0, 'F', '1', '0', '0', 'pro:clientorderitem:export', '#', 'admin', NULL, '2024-05-07 12:12:12', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2339, '生产废料', 2042, 17, 'wmwaste', 'mes/wm/waste/index', NULL, 1, 0, 'C', '1', '1', '0', 'mes:wmwasteheader', 'monitor', 'admin', NULL, '2024-05-13 02:11:15', 'admin', NULL, '2024-05-28 07:01:13', '');
INSERT INTO `sys_menu` VALUES (2340, '生产废料单列表查询', 2339, 1, '', NULL, NULL, 1, 0, 'F', '1', '0', '0', 'mes:wmwasteline:list', '#', 'admin', NULL, '2024-05-13 02:14:03', '', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2341, '移动端目录', 0, 9999, 'mobile', NULL, NULL, 1, 0, 'M', '2', '0', '0', '', 'phone', 'admin', NULL, '2024-06-10 06:49:48', 'admin', NULL, '2024-06-10 09:04:24', '');
INSERT INTO `sys_menu` VALUES (2342, '废料上报', 2341, 3, 'mobile', '/pages/work/waste/index', NULL, 1, 0, 'C', '2', '0', '0', 'mobile:mes:waste', 'close', 'admin', NULL, '2024-06-10 13:01:56', 'admin', NULL, '2024-06-10 23:25:23', '');
INSERT INTO `sys_menu` VALUES (2343, '退料上报', 2341, 4, 'mobile', '/pages/work/rtIssue/index', NULL, 1, 0, 'C', '2', '0', '0', 'mobile:mes:rtissue', 'car', 'admin', NULL, '2024-06-10 13:03:46', 'admin', NULL, '2024-06-10 23:25:42', '');
INSERT INTO `sys_menu` VALUES (2344, '领料申请', 2341, 2, 'mobile', '/pages/work/issue/index', NULL, 1, 0, 'C', '2', '0', '0', 'mobile:mes:issue', 'shopping-cart', 'admin', NULL, '2024-06-10 13:04:25', 'admin', NULL, '2024-06-10 23:25:29', '');
INSERT INTO `sys_menu` VALUES (2345, '上报工时', 2341, 1, 'mobile', '/pages/work/feedback/index', NULL, 1, 0, 'C', '2', '0', '0', 'mobile:mes:feedback', 'man-add', 'admin', NULL, '2024-06-10 13:05:13', 'admin', NULL, '2024-06-10 23:25:15', '');
INSERT INTO `sys_menu` VALUES (2346, '审核', 2341, 5, 'mobile', '/pages/work/audit/index', NULL, 1, 0, 'C', '2', '0', '0', 'mobile:mes:audit', 'order', 'admin', NULL, '2024-06-10 13:05:50', '', NULL, NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `message_type` varchar(64) NOT NULL COMMENT '消息类型',
  `message_level` varchar(64) NOT NULL COMMENT '消息级别',
  `message_title` varchar(64) DEFAULT NULL COMMENT '标题',
  `message_content` longblob COMMENT '内容',
  `sender_id` bigint DEFAULT NULL COMMENT '发送人ID',
  `sender_name` varchar(64) DEFAULT NULL COMMENT '发送人名称',
  `sender_nick` varchar(64) DEFAULT NULL COMMENT '发送人昵称',
  `recipient_id` bigint NOT NULL COMMENT '接收人ID',
  `recipient_name` varchar(64) DEFAULT NULL COMMENT '接收人名称',
  `recipient_nick` varchar(64) DEFAULT NULL COMMENT '接收人昵称',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `call_back` varchar(255) DEFAULT NULL COMMENT '回调地址',
  `status` varchar(64) NOT NULL DEFAULT 'UNREAD' COMMENT '状态',
  `deleted_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice` VALUES (1, '测试公告内容', '2', 0x3C703EE6B58BE8AF95E585ACE5918AE58685E5AEB93C2F703E, '0', 'admin', '2022-04-07 00:29:32', 'admin', '2023-03-06 20:02:49', '管理员');
INSERT INTO `sys_notice` VALUES (2, '测试公告内容', '1', 0x3C703EE7BBB4E68AA4E58685E5AEB93C2F703E, '0', 'admin', '2022-04-07 00:29:32', 'admin', '2022-08-22 10:30:12', '管理员');
COMMIT;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6577 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2022-04-07 00:29:31', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2022-04-07 00:29:31', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2022-04-07 00:29:31', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'ZS_OPRATOR', '注塑机操作工', 6, '0', 'admin', '2022-04-07 00:29:31', 'admin', '2022-05-14 13:52:30', '');
INSERT INTO `sys_post` VALUES (5, 'DEPT_MANAGER', '部门经理', 4, '0', 'admin', '2022-05-14 13:51:40', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (6, 'QX_OPRATOR', '喷砂清洗操作工', 7, '0', 'admin', '2022-05-14 13:53:53', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (7, 'ZZ_OPRATOR', '组装机操作工', 8, '0', 'admin', '2022-05-14 13:54:28', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (8, 'CCD_OPRATOR', '检测员', 9, '0', 'admin', '2022-05-14 13:54:58', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (9, 'BZ_OPRATOR', '包装工', 10, '0', 'admin', '2022-05-14 13:55:18', '', NULL, NULL);
INSERT INTO `sys_post` VALUES (11, '123', '测试经理', 12, '0', 'admin', '2022-08-18 18:49:34', 'admin', '2022-08-19 10:21:49', '我测试');
INSERT INTO `sys_post` VALUES (13, 'FG001', '纺工', 1, '0', 'admin', '2022-08-21 19:45:33', '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2022-04-07 00:29:31', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 3, '2', 0, 0, '0', '0', 'admin', '2022-04-07 00:29:31', 'admin', '2022-08-21 23:17:22', '普通角色');
INSERT INTO `sys_role` VALUES (100, '测试员', 'tester', 6, '1', 1, 1, '0', '2', 'admin', '2022-05-04 17:57:40', 'admin', '2022-08-19 09:42:33', NULL);
INSERT INTO `sys_role` VALUES (101, '生产副总', 'visprod', 4, '1', 1, 1, '0', '0', 'admin', '2022-05-10 10:13:06', 'admin', '2022-05-10 16:26:50', NULL);
INSERT INTO `sys_role` VALUES (102, '生产主管', 'proManager', 5, '1', 0, 0, '0', '0', 'admin', '2022-05-10 16:29:13', 'admin', '2023-11-07 22:43:32', NULL);
INSERT INTO `sys_role` VALUES (103, 'test', 'test', 5, '1', 1, 1, '0', '2', 'admin', '2022-08-19 08:42:03', '', NULL, NULL);
INSERT INTO `sys_role` VALUES (104, '开发人员', 'engineer', 0, '1', 1, 1, '0', '0', 'admin', '2024-04-23 08:58:54', 'admin', '2024-06-12 07:55:04', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (2, 104);
INSERT INTO `sys_role_dept` VALUES (2, 108);
INSERT INTO `sys_role_dept` VALUES (2, 109);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);
INSERT INTO `sys_role_menu` VALUES (101, 2000);
INSERT INTO `sys_role_menu` VALUES (101, 2001);
INSERT INTO `sys_role_menu` VALUES (101, 2002);
INSERT INTO `sys_role_menu` VALUES (101, 2014);
INSERT INTO `sys_role_menu` VALUES (101, 2018);
INSERT INTO `sys_role_menu` VALUES (101, 2019);
INSERT INTO `sys_role_menu` VALUES (101, 2020);
INSERT INTO `sys_role_menu` VALUES (101, 2021);
INSERT INTO `sys_role_menu` VALUES (101, 2022);
INSERT INTO `sys_role_menu` VALUES (101, 2023);
INSERT INTO `sys_role_menu` VALUES (101, 2024);
INSERT INTO `sys_role_menu` VALUES (101, 2025);
INSERT INTO `sys_role_menu` VALUES (101, 2028);
INSERT INTO `sys_role_menu` VALUES (101, 2030);
INSERT INTO `sys_role_menu` VALUES (101, 2031);
INSERT INTO `sys_role_menu` VALUES (101, 2032);
INSERT INTO `sys_role_menu` VALUES (101, 2036);
INSERT INTO `sys_role_menu` VALUES (101, 2037);
INSERT INTO `sys_role_menu` VALUES (101, 2038);
INSERT INTO `sys_role_menu` VALUES (101, 2042);
INSERT INTO `sys_role_menu` VALUES (101, 2043);
INSERT INTO `sys_role_menu` VALUES (101, 2044);
INSERT INTO `sys_role_menu` VALUES (101, 2045);
INSERT INTO `sys_role_menu` VALUES (101, 2049);
INSERT INTO `sys_role_menu` VALUES (101, 2050);
INSERT INTO `sys_role_menu` VALUES (101, 2054);
INSERT INTO `sys_role_menu` VALUES (101, 2055);
INSERT INTO `sys_role_menu` VALUES (101, 2059);
INSERT INTO `sys_role_menu` VALUES (101, 2060);
INSERT INTO `sys_role_menu` VALUES (101, 2061);
INSERT INTO `sys_role_menu` VALUES (101, 2062);
INSERT INTO `sys_role_menu` VALUES (101, 2066);
INSERT INTO `sys_role_menu` VALUES (101, 2067);
INSERT INTO `sys_role_menu` VALUES (101, 2068);
INSERT INTO `sys_role_menu` VALUES (101, 2072);
INSERT INTO `sys_role_menu` VALUES (101, 2073);
INSERT INTO `sys_role_menu` VALUES (101, 2074);
INSERT INTO `sys_role_menu` VALUES (101, 2075);
INSERT INTO `sys_role_menu` VALUES (101, 2079);
INSERT INTO `sys_role_menu` VALUES (101, 2080);
INSERT INTO `sys_role_menu` VALUES (101, 2084);
INSERT INTO `sys_role_menu` VALUES (101, 2085);
INSERT INTO `sys_role_menu` VALUES (102, 2001);
INSERT INTO `sys_role_menu` VALUES (102, 2002);
INSERT INTO `sys_role_menu` VALUES (102, 2014);
INSERT INTO `sys_role_menu` VALUES (102, 2018);
INSERT INTO `sys_role_menu` VALUES (102, 2019);
INSERT INTO `sys_role_menu` VALUES (102, 2020);
INSERT INTO `sys_role_menu` VALUES (102, 2021);
INSERT INTO `sys_role_menu` VALUES (102, 2022);
INSERT INTO `sys_role_menu` VALUES (102, 2023);
INSERT INTO `sys_role_menu` VALUES (102, 2024);
INSERT INTO `sys_role_menu` VALUES (102, 2025);
INSERT INTO `sys_role_menu` VALUES (102, 2028);
INSERT INTO `sys_role_menu` VALUES (102, 2030);
INSERT INTO `sys_role_menu` VALUES (102, 2031);
INSERT INTO `sys_role_menu` VALUES (102, 2032);
INSERT INTO `sys_role_menu` VALUES (102, 2036);
INSERT INTO `sys_role_menu` VALUES (102, 2037);
INSERT INTO `sys_role_menu` VALUES (102, 2038);
INSERT INTO `sys_role_menu` VALUES (102, 2060);
INSERT INTO `sys_role_menu` VALUES (102, 2061);
INSERT INTO `sys_role_menu` VALUES (102, 2062);
INSERT INTO `sys_role_menu` VALUES (102, 2066);
INSERT INTO `sys_role_menu` VALUES (102, 2067);
INSERT INTO `sys_role_menu` VALUES (102, 2068);
INSERT INTO `sys_role_menu` VALUES (102, 2072);
INSERT INTO `sys_role_menu` VALUES (102, 2073);
INSERT INTO `sys_role_menu` VALUES (102, 2074);
INSERT INTO `sys_role_menu` VALUES (102, 2075);
INSERT INTO `sys_role_menu` VALUES (102, 2076);
INSERT INTO `sys_role_menu` VALUES (102, 2077);
INSERT INTO `sys_role_menu` VALUES (102, 2078);
INSERT INTO `sys_role_menu` VALUES (102, 2079);
INSERT INTO `sys_role_menu` VALUES (102, 2080);
INSERT INTO `sys_role_menu` VALUES (102, 2084);
INSERT INTO `sys_role_menu` VALUES (102, 2085);
INSERT INTO `sys_role_menu` VALUES (102, 2086);
INSERT INTO `sys_role_menu` VALUES (102, 2087);
INSERT INTO `sys_role_menu` VALUES (104, 3);
INSERT INTO `sys_role_menu` VALUES (104, 114);
INSERT INTO `sys_role_menu` VALUES (104, 115);
INSERT INTO `sys_role_menu` VALUES (104, 116);
INSERT INTO `sys_role_menu` VALUES (104, 1055);
INSERT INTO `sys_role_menu` VALUES (104, 1056);
INSERT INTO `sys_role_menu` VALUES (104, 1057);
INSERT INTO `sys_role_menu` VALUES (104, 1058);
INSERT INTO `sys_role_menu` VALUES (104, 1059);
INSERT INTO `sys_role_menu` VALUES (104, 1060);
INSERT INTO `sys_role_menu` VALUES (104, 2000);
INSERT INTO `sys_role_menu` VALUES (104, 2001);
INSERT INTO `sys_role_menu` VALUES (104, 2002);
INSERT INTO `sys_role_menu` VALUES (104, 2008);
INSERT INTO `sys_role_menu` VALUES (104, 2009);
INSERT INTO `sys_role_menu` VALUES (104, 2010);
INSERT INTO `sys_role_menu` VALUES (104, 2011);
INSERT INTO `sys_role_menu` VALUES (104, 2012);
INSERT INTO `sys_role_menu` VALUES (104, 2013);
INSERT INTO `sys_role_menu` VALUES (104, 2014);
INSERT INTO `sys_role_menu` VALUES (104, 2015);
INSERT INTO `sys_role_menu` VALUES (104, 2016);
INSERT INTO `sys_role_menu` VALUES (104, 2017);
INSERT INTO `sys_role_menu` VALUES (104, 2018);
INSERT INTO `sys_role_menu` VALUES (104, 2019);
INSERT INTO `sys_role_menu` VALUES (104, 2020);
INSERT INTO `sys_role_menu` VALUES (104, 2021);
INSERT INTO `sys_role_menu` VALUES (104, 2022);
INSERT INTO `sys_role_menu` VALUES (104, 2023);
INSERT INTO `sys_role_menu` VALUES (104, 2024);
INSERT INTO `sys_role_menu` VALUES (104, 2025);
INSERT INTO `sys_role_menu` VALUES (104, 2026);
INSERT INTO `sys_role_menu` VALUES (104, 2027);
INSERT INTO `sys_role_menu` VALUES (104, 2028);
INSERT INTO `sys_role_menu` VALUES (104, 2029);
INSERT INTO `sys_role_menu` VALUES (104, 2030);
INSERT INTO `sys_role_menu` VALUES (104, 2031);
INSERT INTO `sys_role_menu` VALUES (104, 2032);
INSERT INTO `sys_role_menu` VALUES (104, 2033);
INSERT INTO `sys_role_menu` VALUES (104, 2034);
INSERT INTO `sys_role_menu` VALUES (104, 2035);
INSERT INTO `sys_role_menu` VALUES (104, 2036);
INSERT INTO `sys_role_menu` VALUES (104, 2037);
INSERT INTO `sys_role_menu` VALUES (104, 2038);
INSERT INTO `sys_role_menu` VALUES (104, 2039);
INSERT INTO `sys_role_menu` VALUES (104, 2040);
INSERT INTO `sys_role_menu` VALUES (104, 2041);
INSERT INTO `sys_role_menu` VALUES (104, 2042);
INSERT INTO `sys_role_menu` VALUES (104, 2043);
INSERT INTO `sys_role_menu` VALUES (104, 2044);
INSERT INTO `sys_role_menu` VALUES (104, 2045);
INSERT INTO `sys_role_menu` VALUES (104, 2046);
INSERT INTO `sys_role_menu` VALUES (104, 2047);
INSERT INTO `sys_role_menu` VALUES (104, 2048);
INSERT INTO `sys_role_menu` VALUES (104, 2049);
INSERT INTO `sys_role_menu` VALUES (104, 2050);
INSERT INTO `sys_role_menu` VALUES (104, 2051);
INSERT INTO `sys_role_menu` VALUES (104, 2052);
INSERT INTO `sys_role_menu` VALUES (104, 2053);
INSERT INTO `sys_role_menu` VALUES (104, 2054);
INSERT INTO `sys_role_menu` VALUES (104, 2055);
INSERT INTO `sys_role_menu` VALUES (104, 2056);
INSERT INTO `sys_role_menu` VALUES (104, 2057);
INSERT INTO `sys_role_menu` VALUES (104, 2058);
INSERT INTO `sys_role_menu` VALUES (104, 2072);
INSERT INTO `sys_role_menu` VALUES (104, 2073);
INSERT INTO `sys_role_menu` VALUES (104, 2074);
INSERT INTO `sys_role_menu` VALUES (104, 2075);
INSERT INTO `sys_role_menu` VALUES (104, 2076);
INSERT INTO `sys_role_menu` VALUES (104, 2077);
INSERT INTO `sys_role_menu` VALUES (104, 2078);
INSERT INTO `sys_role_menu` VALUES (104, 2079);
INSERT INTO `sys_role_menu` VALUES (104, 2080);
INSERT INTO `sys_role_menu` VALUES (104, 2081);
INSERT INTO `sys_role_menu` VALUES (104, 2082);
INSERT INTO `sys_role_menu` VALUES (104, 2083);
INSERT INTO `sys_role_menu` VALUES (104, 2084);
INSERT INTO `sys_role_menu` VALUES (104, 2085);
INSERT INTO `sys_role_menu` VALUES (104, 2086);
INSERT INTO `sys_role_menu` VALUES (104, 2087);
INSERT INTO `sys_role_menu` VALUES (104, 2088);
INSERT INTO `sys_role_menu` VALUES (104, 2089);
INSERT INTO `sys_role_menu` VALUES (104, 2090);
INSERT INTO `sys_role_menu` VALUES (104, 2091);
INSERT INTO `sys_role_menu` VALUES (104, 2092);
INSERT INTO `sys_role_menu` VALUES (104, 2093);
INSERT INTO `sys_role_menu` VALUES (104, 2094);
INSERT INTO `sys_role_menu` VALUES (104, 2109);
INSERT INTO `sys_role_menu` VALUES (104, 2110);
INSERT INTO `sys_role_menu` VALUES (104, 2111);
INSERT INTO `sys_role_menu` VALUES (104, 2112);
INSERT INTO `sys_role_menu` VALUES (104, 2113);
INSERT INTO `sys_role_menu` VALUES (104, 2114);
INSERT INTO `sys_role_menu` VALUES (104, 2115);
INSERT INTO `sys_role_menu` VALUES (104, 2116);
INSERT INTO `sys_role_menu` VALUES (104, 2117);
INSERT INTO `sys_role_menu` VALUES (104, 2118);
INSERT INTO `sys_role_menu` VALUES (104, 2119);
INSERT INTO `sys_role_menu` VALUES (104, 2120);
INSERT INTO `sys_role_menu` VALUES (104, 2121);
INSERT INTO `sys_role_menu` VALUES (104, 2122);
INSERT INTO `sys_role_menu` VALUES (104, 2123);
INSERT INTO `sys_role_menu` VALUES (104, 2151);
INSERT INTO `sys_role_menu` VALUES (104, 2152);
INSERT INTO `sys_role_menu` VALUES (104, 2153);
INSERT INTO `sys_role_menu` VALUES (104, 2154);
INSERT INTO `sys_role_menu` VALUES (104, 2155);
INSERT INTO `sys_role_menu` VALUES (104, 2156);
INSERT INTO `sys_role_menu` VALUES (104, 2157);
INSERT INTO `sys_role_menu` VALUES (104, 2158);
INSERT INTO `sys_role_menu` VALUES (104, 2159);
INSERT INTO `sys_role_menu` VALUES (104, 2197);
INSERT INTO `sys_role_menu` VALUES (104, 2198);
INSERT INTO `sys_role_menu` VALUES (104, 2199);
INSERT INTO `sys_role_menu` VALUES (104, 2200);
INSERT INTO `sys_role_menu` VALUES (104, 2201);
INSERT INTO `sys_role_menu` VALUES (104, 2202);
INSERT INTO `sys_role_menu` VALUES (104, 2226);
INSERT INTO `sys_role_menu` VALUES (104, 2227);
INSERT INTO `sys_role_menu` VALUES (104, 2228);
INSERT INTO `sys_role_menu` VALUES (104, 2229);
INSERT INTO `sys_role_menu` VALUES (104, 2230);
INSERT INTO `sys_role_menu` VALUES (104, 2231);
INSERT INTO `sys_role_menu` VALUES (104, 2237);
INSERT INTO `sys_role_menu` VALUES (104, 2295);
INSERT INTO `sys_role_menu` VALUES (104, 2296);
INSERT INTO `sys_role_menu` VALUES (104, 2297);
INSERT INTO `sys_role_menu` VALUES (104, 2298);
INSERT INTO `sys_role_menu` VALUES (104, 2299);
INSERT INTO `sys_role_menu` VALUES (104, 2300);
INSERT INTO `sys_role_menu` VALUES (104, 2301);
INSERT INTO `sys_role_menu` VALUES (104, 2302);
INSERT INTO `sys_role_menu` VALUES (104, 2303);
INSERT INTO `sys_role_menu` VALUES (104, 2304);
INSERT INTO `sys_role_menu` VALUES (104, 2305);
INSERT INTO `sys_role_menu` VALUES (104, 2315);
INSERT INTO `sys_role_menu` VALUES (104, 2316);
INSERT INTO `sys_role_menu` VALUES (104, 2317);
INSERT INTO `sys_role_menu` VALUES (104, 2318);
INSERT INTO `sys_role_menu` VALUES (104, 2319);
INSERT INTO `sys_role_menu` VALUES (104, 2320);
INSERT INTO `sys_role_menu` VALUES (104, 2341);
INSERT INTO `sys_role_menu` VALUES (104, 2342);
INSERT INTO `sys_role_menu` VALUES (104, 2343);
INSERT INTO `sys_role_menu` VALUES (104, 2344);
INSERT INTO `sys_role_menu` VALUES (104, 2345);
INSERT INTO `sys_role_menu` VALUES (104, 2346);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '13299106672', '0', '/profile/avatar/2023/03/04/blob_20230304174312A001.jpeg', '$2a$10$11LdB7dZJ/SuL.zrLsbEve3ThqoMRj4gkFGELwMB50MmsYvXovjP.', '0', '0', '127.0.0.1', '2024-06-24 16:17:08', 'admin', '2022-04-07 00:29:31', '', '2024-06-24 08:17:07', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '1', '2', '127.0.0.1', '2022-04-07 00:29:31', 'admin', '2022-04-07 00:29:31', 'admin', '2022-08-12 23:58:29', '测试员');
INSERT INTO `sys_user` VALUES (100, 100, 'test', '测试员', '00', '', '', '0', '', '$2a$10$BKgldPn/ZBmRt5MZhZDiOuK96ba5HHIG4AUrho6Qdo6gS2n4EwHDi', '0', '0', '127.0.0.1', '2023-11-07 22:44:00', 'admin', '2022-05-04 17:47:50', 'admin', '2023-11-07 22:43:59', NULL);
INSERT INTO `sys_user` VALUES (101, 101, 'fz', '王副总', '00', '', '', '0', '', '$2a$10$I1hEX1zvBHcs/fq.Fagb1uhf8jbStUe8GAXWIn2cylmm08ewAs3Tm', '0', '2', '127.0.0.1', '2022-05-10 16:30:59', 'admin', '2022-05-10 10:14:10', 'admin', '2022-08-16 14:02:38', NULL);
INSERT INTO `sys_user` VALUES (102, 101, 'sg', '王主管', '00', '', '', '0', '', '$2a$10$2QFXeHfT0U5oHJgB9lf8I.ObPixvq1zCjElqlKwYyjIVbKbg/t2AK', '0', '2', '127.0.0.1', '2022-05-10 16:29:52', 'admin', '2022-05-10 16:29:41', 'admin', '2022-10-30 17:44:53', NULL);
INSERT INTO `sys_user` VALUES (103, 100, 'lionx', 'lion', '00', 'lion@qq.com', '13500000000', '0', '', '$2a$10$98k6GP/mF0GobFEn61FavOQKaHHS1l3x2DTDz9GHX8krJVAkbcZ4G', '0', '2', '113.87.118.79', '2022-08-11 15:39:26', 'admin', '2022-08-11 15:39:06', '', '2022-08-11 15:39:26', '生产');
INSERT INTO `sys_user` VALUES (104, NULL, 'demo', 'test', '00', '7@qq.com', '17565656565', '0', '', '$2a$10$O.heOn/hyVyDNuCP4KvDUOrAqbT.mGr25LndPWdhGuxwJ7DmPOqna', '0', '2', '58.58.205.114', '2022-08-19 14:33:46', 'admin', '2022-08-19 08:43:12', 'admin', '2022-08-19 14:33:45', 'test');
INSERT INTO `sys_user` VALUES (105, 112, 'xhran', 'xhran', '00', '', '', '0', '', '$2a$10$pZMpkxpj7RB6Ayf./CntpONHAzLLccD5r1eUi6pBQiZ2w2Dt0rsSy', '0', '2', '', NULL, 'admin', '2022-08-19 08:57:40', 'admin', '2022-08-19 20:52:09', NULL);
INSERT INTO `sys_user` VALUES (106, 100, 'qqq123', 'qqq123', '00', '', '13400000000', '0', '', '$2a$10$b/ld2D7pBHD8Mna2u0hvde/eFmXmdxCho2sop2JPK3WV2Yd3d9kla', '0', '2', '171.43.165.206', '2022-08-19 09:45:33', 'admin', '2022-08-19 09:44:56', '', '2022-08-19 09:45:33', NULL);
INSERT INTO `sys_user` VALUES (107, 113, 'test123', '老李', '00', '', '13433333333', '0', '', '$2a$10$HoWMFcH45QiaN2LEAWQ19.5ovv92FU43tfOoOw7rJe8o2ZujdGUEu', '0', '2', '', NULL, 'admin', '2022-08-19 10:23:55', '', NULL, '我测试');
INSERT INTO `sys_user` VALUES (108, NULL, 'testfz001', '张三三', '00', '', '', '0', '', '$2a$10$hnH1Qw443IwufvoEQ6.aquJSySGUbPJlpq.4Sx7Q15MMtVAC2ojwC', '0', '2', '1.203.117.226', '2022-08-21 20:28:24', 'admin', '2022-08-19 19:22:13', '', '2022-08-21 20:28:23', NULL);
INSERT INTO `sys_user` VALUES (109, 100, 'test1', 'test1', '00', '', '', '0', '', '$2a$10$eYg6GIToZwB76Gynerw84.QIFzAKVdL5v.NwcQH8in70NRRhq..UK', '0', '2', '58.34.81.229', '2022-08-23 12:48:15', 'admin', '2022-08-23 12:30:56', '', '2022-08-23 12:48:15', NULL);
INSERT INTO `sys_user` VALUES (110, 100, '开发1', 'kf1', '00', '', '', '0', '', '$2a$10$wAa/guo/XLuRdTdkOC4/ReqUQdCwuxMWWxLeB9E02BstICG7sjJSS', '0', '2', '', NULL, 'admin', '2024-04-23 09:10:13', '', NULL, NULL);
INSERT INTO `sys_user` VALUES (111, 100, 'kf1', '开发1', '00', '', '', '0', '', '$2a$10$11LdB7dZJ/SuL.zrLsbEve3ThqoMRj4gkFGELwMB50MmsYvXovjP.', '0', '0', '127.0.0.1', '2024-06-19 13:52:34', 'admin', '2024-04-23 09:11:07', '', '2024-06-19 05:52:33', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (100, 4);
INSERT INTO `sys_user_post` VALUES (111, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (100, 102);
INSERT INTO `sys_user_role` VALUES (111, 104);
COMMIT;

-- ----------------------------
-- Table structure for test_tab
-- ----------------------------
DROP TABLE IF EXISTS `test_tab`;
CREATE TABLE `test_tab` (
  `user_id` int DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `age` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tm_tool
-- ----------------------------
DROP TABLE IF EXISTS `tm_tool`;
CREATE TABLE `tm_tool` (
  `tool_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工装夹具ID',
  `tool_code` varchar(64) DEFAULT NULL COMMENT '工装夹具编码',
  `tool_name` varchar(255) NOT NULL COMMENT '工装夹具名称',
  `brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `spec` varchar(255) DEFAULT NULL COMMENT '型号',
  `tool_type_id` bigint NOT NULL COMMENT '工装夹具类型ID',
  `tool_type_code` varchar(64) DEFAULT NULL COMMENT '工装夹具类型编码',
  `tool_type_name` varchar(255) DEFAULT NULL COMMENT '工装夹具类型名称',
  `code_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否单独编码管理',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `quantity_avail` int DEFAULT '1' COMMENT '可用数量',
  `mainten_type` varchar(20) DEFAULT NULL COMMENT '保养维护类型',
  `next_mainten_period` int DEFAULT NULL COMMENT '下一次保养周期',
  `next_mainten_date` datetime DEFAULT NULL COMMENT '下一次保养日期',
  `status` varchar(64) DEFAULT 'STORE' COMMENT '状态[MES_TOOL_STATUS]',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tool_id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工装夹具清单表';

-- ----------------------------
-- Table structure for tm_tool_type
-- ----------------------------
DROP TABLE IF EXISTS `tm_tool_type`;
CREATE TABLE `tm_tool_type` (
  `tool_type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工装夹具类型ID',
  `tool_type_code` varchar(64) NOT NULL COMMENT '类型编码',
  `tool_type_name` varchar(255) NOT NULL COMMENT '类型名称',
  `code_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否编码管理',
  `mainten_type` varchar(20) DEFAULT NULL COMMENT '保养维护类型',
  `mainten_period` int DEFAULT NULL COMMENT '保养周期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tool_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工装夹具类型表';

-- ----------------------------
-- Table structure for ureport_file_tbl
-- ----------------------------
DROP TABLE IF EXISTS `ureport_file_tbl`;
CREATE TABLE `ureport_file_tbl` (
  `id_` int NOT NULL AUTO_INCREMENT,
  `name_` varchar(100) NOT NULL,
  `content_` mediumblob,
  `create_time_` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time_` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for wm_barcode
-- ----------------------------
DROP TABLE IF EXISTS `wm_barcode`;
CREATE TABLE `wm_barcode` (
  `barcode_id` bigint NOT NULL AUTO_INCREMENT COMMENT '条码ID',
  `barcode_formart` varchar(64) NOT NULL COMMENT '条码格式',
  `barcode_type` varchar(64) NOT NULL COMMENT '条码类型',
  `barcode_content` varchar(255) NOT NULL COMMENT '条码内容',
  `bussiness_id` bigint NOT NULL COMMENT '业务ID',
  `bussiness_code` varchar(64) DEFAULT NULL COMMENT '业务编码',
  `bussiness_name` varchar(255) DEFAULT NULL COMMENT '业务名称',
  `barcode_url` varchar(255) DEFAULT NULL COMMENT '条码地址',
  `enable_flag` char(1) DEFAULT 'Y' COMMENT '是否生效',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`barcode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='条码清单表';

-- ----------------------------
-- Table structure for wm_barcode_config
-- ----------------------------
DROP TABLE IF EXISTS `wm_barcode_config`;
CREATE TABLE `wm_barcode_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `barcode_formart` varchar(64) NOT NULL COMMENT '条码格式',
  `barcode_type` varchar(64) NOT NULL COMMENT '条码类型',
  `content_formart` varchar(255) NOT NULL COMMENT '内容格式',
  `content_example` varchar(255) DEFAULT NULL COMMENT '内容样例',
  `auto_gen_flag` char(1) DEFAULT 'Y' COMMENT '是否自动生成',
  `default_template` varchar(255) DEFAULT NULL COMMENT '默认的打印模板',
  `enable_flag` char(1) DEFAULT 'Y' COMMENT '是否生效',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='条码配置';

-- ----------------------------
-- Table structure for wm_issue_header
-- ----------------------------
DROP TABLE IF EXISTS `wm_issue_header`;
CREATE TABLE `wm_issue_header` (
  `issue_id` bigint NOT NULL AUTO_INCREMENT COMMENT '领料单ID',
  `issue_code` varchar(64) NOT NULL COMMENT '领料单编号',
  `issue_name` varchar(255) NOT NULL COMMENT '领料单名称',
  `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `task_id` bigint DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '生产任务编码',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `issue_date` datetime DEFAULT NULL COMMENT '领料日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`issue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产领料单头表';

-- ----------------------------
-- Table structure for wm_issue_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_issue_line`;
CREATE TABLE `wm_issue_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `issue_id` bigint DEFAULT NULL COMMENT '领料单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_issued` double(12,2) NOT NULL COMMENT '领料数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '领料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产领料单行表';

-- ----------------------------
-- Table structure for wm_item_consume
-- ----------------------------
DROP TABLE IF EXISTS `wm_item_consume`;
CREATE TABLE `wm_item_consume` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `task_id` bigint DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '生产任务编号',
  `task_name` varchar(255) DEFAULT NULL COMMENT '生产任务名称',
  `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `consume_date` datetime DEFAULT NULL COMMENT '消耗日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料消耗记录表';

-- ----------------------------
-- Table structure for wm_item_consume_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_item_consume_line`;
CREATE TABLE `wm_item_consume_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `record_id` bigint DEFAULT NULL COMMENT '消耗记录ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_consume` double(12,2) NOT NULL COMMENT '消耗数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '领料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料消耗记录行表';

-- ----------------------------
-- Table structure for wm_item_recpt
-- ----------------------------
DROP TABLE IF EXISTS `wm_item_recpt`;
CREATE TABLE `wm_item_recpt` (
  `recpt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库单ID',
  `recpt_code` varchar(64) NOT NULL COMMENT '入库单编号',
  `recpt_name` varchar(255) NOT NULL COMMENT '入库单名称',
  `iqc_id` bigint DEFAULT NULL COMMENT '来料检验单ID',
  `iqc_code` varchar(64) DEFAULT NULL COMMENT '来料检验单编号',
  `po_code` varchar(64) DEFAULT NULL COMMENT '采购订单编号',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `recpt_date` datetime DEFAULT NULL COMMENT '入库日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`recpt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料入库单表';

-- ----------------------------
-- Table structure for wm_item_recpt_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_item_recpt_line`;
CREATE TABLE `wm_item_recpt_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `recpt_id` bigint DEFAULT NULL COMMENT '入库单ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_recived` double(12,2) NOT NULL COMMENT '入库数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '入库批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `iqc_check` char(1) DEFAULT 'N',
  `iqc_id` bigint DEFAULT NULL,
  `iqc_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料入库单行表';

-- ----------------------------
-- Table structure for wm_material_stock
-- ----------------------------
DROP TABLE IF EXISTS `wm_material_stock`;
CREATE TABLE `wm_material_stock` (
  `material_stock_id` bigint NOT NULL AUTO_INCREMENT COMMENT '事务ID',
  `item_type_id` bigint DEFAULT NULL COMMENT '物料类型ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '入库批次号',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编号',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(64) DEFAULT NULL COMMENT '供应商简称',
  `quantity_onhand` double(12,2) DEFAULT NULL COMMENT '在库数量',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `recpt_date` datetime DEFAULT NULL COMMENT '入库时间',
  `expire_date` datetime DEFAULT NULL COMMENT '库存有效期',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`material_stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存记录表';

-- ----------------------------
-- Table structure for wm_outsource_issue
-- ----------------------------
DROP TABLE IF EXISTS `wm_outsource_issue`;
CREATE TABLE `wm_outsource_issue` (
  `issue_id` bigint NOT NULL AUTO_INCREMENT COMMENT '领料单ID',
  `issue_code` varchar(64) NOT NULL COMMENT '领料单编号',
  `issue_name` varchar(255) NOT NULL COMMENT '领料单名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `issue_date` datetime DEFAULT NULL COMMENT '领料日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`issue_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外协领料单头表';

-- ----------------------------
-- Table structure for wm_outsource_issue_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_outsource_issue_line`;
CREATE TABLE `wm_outsource_issue_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `issue_id` bigint DEFAULT NULL COMMENT '领料单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_issued` double(12,2) NOT NULL COMMENT '领料数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '领料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外协领料单行表';

-- ----------------------------
-- Table structure for wm_outsource_recpt
-- ----------------------------
DROP TABLE IF EXISTS `wm_outsource_recpt`;
CREATE TABLE `wm_outsource_recpt` (
  `recpt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库单ID',
  `recpt_code` varchar(64) NOT NULL COMMENT '入库单编号',
  `recpt_name` varchar(255) NOT NULL COMMENT '入库单名称',
  `iqc_id` bigint DEFAULT NULL COMMENT '来料检验单ID',
  `iqc_code` varchar(64) DEFAULT NULL COMMENT '来料检验单编号',
  `workorder_id` bigint DEFAULT NULL COMMENT '外协工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '外协工单编号',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `recpt_date` datetime DEFAULT NULL COMMENT '入库日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`recpt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外协入库单表';

-- ----------------------------
-- Table structure for wm_outsource_recpt_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_outsource_recpt_line`;
CREATE TABLE `wm_outsource_recpt_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `recpt_id` bigint DEFAULT NULL COMMENT '入库单ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_recived` double(12,2) NOT NULL COMMENT '入库数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '入库批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `iqc_check` char(1) DEFAULT NULL COMMENT '是否来料检验',
  `iqc_id` bigint DEFAULT NULL COMMENT '来料检验单ID',
  `iqc_code` varchar(64) DEFAULT NULL COMMENT '来料检验单编号',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外协入库单行表';

-- ----------------------------
-- Table structure for wm_package
-- ----------------------------
DROP TABLE IF EXISTS `wm_package`;
CREATE TABLE `wm_package` (
  `package_id` bigint NOT NULL AUTO_INCREMENT COMMENT '装箱单ID',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父箱ID',
  `ancestors` varchar(255) NOT NULL DEFAULT '0' COMMENT '所有父节点ID',
  `package_code` varchar(64) DEFAULT NULL COMMENT '装箱单编号',
  `barcode_id` bigint DEFAULT NULL COMMENT '条码ID',
  `barcode_content` varchar(255) DEFAULT NULL COMMENT '条码内容',
  `barcode_url` varchar(255) DEFAULT NULL COMMENT '条码地址',
  `package_date` datetime NOT NULL COMMENT '装箱日期',
  `so_code` varchar(64) DEFAULT NULL COMMENT '销售订单编号',
  `invoice_code` varchar(255) DEFAULT NULL COMMENT '发票编号',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `package_length` double(12,4) DEFAULT NULL COMMENT '箱长度',
  `package_width` double(12,4) DEFAULT NULL COMMENT '箱宽度',
  `package_height` double(12,4) DEFAULT NULL COMMENT '箱高度',
  `size_unit` varchar(64) DEFAULT NULL COMMENT '尺寸单位',
  `net_weight` double(12,4) DEFAULT NULL COMMENT '净重',
  `cross_weight` double(12,4) DEFAULT NULL COMMENT '毛重',
  `weight_unit` varchar(64) DEFAULT NULL COMMENT '重量单位',
  `inspector` varchar(64) DEFAULT NULL COMMENT '检查员用户名',
  `inspector_name` varchar(64) DEFAULT NULL COMMENT '检查员名称',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '状态',
  `enable_flag` char(1) DEFAULT 'Y' COMMENT '是否生效',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='装箱单表';

-- ----------------------------
-- Table structure for wm_package_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_package_line`;
CREATE TABLE `wm_package_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细行ID',
  `package_id` bigint NOT NULL COMMENT '装箱单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_package` double(12,2) NOT NULL COMMENT '装箱数量',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='装箱明细表';

-- ----------------------------
-- Table structure for wm_pback_header
-- ----------------------------
DROP TABLE IF EXISTS `wm_pback_header`;
CREATE TABLE `wm_pback_header` (
  `pback_id` bigint NOT NULL AUTO_INCREMENT COMMENT '生产退料单ID',
  `pback_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '生产退料单编号',
  `pback_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '生产退料单名称',
  `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '工作站名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '生产工单编码',
  `task_id` bigint DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '生产任务编码',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库位名称',
  `pback_date` datetime DEFAULT NULL COMMENT '退料日期',
  `status` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='生产退料单头表';

-- ----------------------------
-- Table structure for wm_pback_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_pback_line`;
CREATE TABLE `wm_pback_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `pback_id` bigint DEFAULT NULL COMMENT '生产退料单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '单位',
  `quantity_pback` double(12,2) NOT NULL COMMENT '退料数量',
  `batch_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '退料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='生产退料单行表';

-- ----------------------------
-- Table structure for wm_product_produce
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_produce`;
CREATE TABLE `wm_product_produce` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库单ID',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `task_id` bigint DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) DEFAULT NULL COMMENT '生产任务编号',
  `task_name` varchar(255) DEFAULT NULL COMMENT '生产任务名称',
  `workstation_id` bigint DEFAULT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) DEFAULT NULL COMMENT '工作站名称',
  `process_id` bigint DEFAULT NULL COMMENT '工序ID',
  `process_code` varchar(64) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  `produce_date` datetime DEFAULT NULL COMMENT '生产日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品产出记录表';

-- ----------------------------
-- Table structure for wm_product_produce_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_produce_line`;
CREATE TABLE `wm_product_produce_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `record_id` bigint DEFAULT NULL COMMENT '产出记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_produce` double(12,2) NOT NULL COMMENT '产出数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品产出记录表行表';

-- ----------------------------
-- Table structure for wm_product_recpt
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_recpt`;
CREATE TABLE `wm_product_recpt` (
  `recpt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库单ID',
  `recpt_code` varchar(64) NOT NULL COMMENT '入库单编号',
  `recpt_name` varchar(255) DEFAULT NULL COMMENT '入库单名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `recpt_date` datetime DEFAULT NULL COMMENT '入库日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`recpt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品入库录表';

-- ----------------------------
-- Table structure for wm_product_recpt_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_recpt_line`;
CREATE TABLE `wm_product_recpt_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `recpt_id` bigint DEFAULT NULL COMMENT '入库记录ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_recived` double(12,2) NOT NULL COMMENT '入库数量',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `workorder_name` varchar(255) DEFAULT NULL COMMENT '生产工单名称',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品入库记录表行表';

-- ----------------------------
-- Table structure for wm_product_salse
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_salse`;
CREATE TABLE `wm_product_salse` (
  `salse_id` bigint NOT NULL AUTO_INCREMENT COMMENT '出库单ID',
  `salse_code` varchar(64) NOT NULL COMMENT '出库单编号',
  `salse_name` varchar(255) NOT NULL COMMENT '出库单名称',
  `oqc_id` bigint DEFAULT NULL COMMENT '出货检验单ID',
  `oqc_code` varchar(64) DEFAULT NULL COMMENT '出货检验单编号',
  `so_code` varchar(64) DEFAULT NULL COMMENT '销售订单编号',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `salse_date` datetime DEFAULT NULL COMMENT '出库日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`salse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售出库单表';

-- ----------------------------
-- Table structure for wm_product_salse_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_product_salse_line`;
CREATE TABLE `wm_product_salse_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `salse_id` bigint DEFAULT NULL COMMENT '出库记录ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_salse` double(12,2) NOT NULL COMMENT '出库数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `oqc_check` char(1) DEFAULT 'N',
  `oqc_id` bigint DEFAULT NULL,
  `oqc_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品销售出库行表';

-- ----------------------------
-- Table structure for wm_rt_issue
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_issue`;
CREATE TABLE `wm_rt_issue` (
  `rt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退料单ID',
  `rt_code` varchar(64) NOT NULL COMMENT '退料单编号',
  `rt_name` varchar(255) DEFAULT NULL COMMENT '退料单名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编码',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `rt_date` datetime DEFAULT NULL COMMENT '退料日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `record_id` bigint NOT NULL COMMENT '报工记录ID',
  PRIMARY KEY (`rt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产退料单头表';

-- ----------------------------
-- Table structure for wm_rt_issue_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_issue_line`;
CREATE TABLE `wm_rt_issue_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `rt_id` bigint DEFAULT NULL COMMENT '退料单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_rt` double(12,2) NOT NULL COMMENT '退料数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '领料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产退料单行表';

-- ----------------------------
-- Table structure for wm_rt_salse
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_salse`;
CREATE TABLE `wm_rt_salse` (
  `rt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退货单ID',
  `rt_code` varchar(64) NOT NULL COMMENT '退货单编号',
  `rt_name` varchar(255) NOT NULL COMMENT '退货单名称',
  `so_code` varchar(64) DEFAULT NULL COMMENT '销售订单编号',
  `client_id` bigint DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `client_nick` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `rt_date` datetime DEFAULT NULL COMMENT '退货日期',
  `rt_reason` varchar(255) DEFAULT NULL COMMENT '退货原因',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品销售退货单表';

-- ----------------------------
-- Table structure for wm_rt_salse_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_salse_line`;
CREATE TABLE `wm_rt_salse_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `rt_id` bigint DEFAULT NULL COMMENT '退货单ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_rted` double(12,2) NOT NULL COMMENT '退货数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品销售退货行表';

-- ----------------------------
-- Table structure for wm_rt_vendor
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_vendor`;
CREATE TABLE `wm_rt_vendor` (
  `rt_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退货单ID',
  `rt_code` varchar(64) NOT NULL COMMENT '退货单编号',
  `rt_name` varchar(255) NOT NULL COMMENT '退货单名称',
  `po_code` varchar(64) DEFAULT NULL COMMENT '采购订单编号',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `rt_date` datetime DEFAULT NULL COMMENT '退货日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商退货表';

-- ----------------------------
-- Table structure for wm_rt_vendor_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_rt_vendor_line`;
CREATE TABLE `wm_rt_vendor_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `rt_id` bigint DEFAULT NULL COMMENT '退货单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_rted` double(12,2) NOT NULL COMMENT '退货数量',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商退货行表';

-- ----------------------------
-- Table structure for wm_sn
-- ----------------------------
DROP TABLE IF EXISTS `wm_sn`;
CREATE TABLE `wm_sn` (
  `sn_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SN码ID',
  `sn_code` varchar(64) NOT NULL COMMENT 'SN码',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `gen_date` datetime DEFAULT NULL COMMENT '生成时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SN码表';

-- ----------------------------
-- Table structure for wm_stock_taking
-- ----------------------------
DROP TABLE IF EXISTS `wm_stock_taking`;
CREATE TABLE `wm_stock_taking` (
  `taking_id` bigint NOT NULL AUTO_INCREMENT COMMENT '盘点单ID',
  `taking_code` varchar(64) NOT NULL COMMENT '盘点单编号',
  `taking_name` varchar(255) DEFAULT NULL COMMENT '盘点单名称',
  `taking_date` datetime NOT NULL COMMENT '盘点日期',
  `user_name` varchar(64) DEFAULT NULL COMMENT '盘点人用户名',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '盘点人',
  `taking_type` varchar(64) NOT NULL COMMENT '盘点类型',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`taking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存盘点记录表';

-- ----------------------------
-- Table structure for wm_stock_taking_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_stock_taking_line`;
CREATE TABLE `wm_stock_taking_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `taking_id` bigint DEFAULT NULL COMMENT '报废单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `unit_name` varchar(64) DEFAULT NULL COMMENT '单位名称',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `taking_quantity` int DEFAULT NULL COMMENT '盘点数量',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `taking_status` varchar(64) NOT NULL DEFAULT 'LOSS' COMMENT '盘点状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存盘点明细表';

-- ----------------------------
-- Table structure for wm_stock_taking_result
-- ----------------------------
DROP TABLE IF EXISTS `wm_stock_taking_result`;
CREATE TABLE `wm_stock_taking_result` (
  `result_id` bigint NOT NULL AUTO_INCREMENT COMMENT '结果ID',
  `taking_id` bigint DEFAULT NULL COMMENT '盘点单ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `unit_name` varchar(64) DEFAULT NULL COMMENT '单位名称',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `taking_quantity` int DEFAULT NULL COMMENT '盘点数量',
  `taking_status` varchar(64) NOT NULL DEFAULT 'LOSS' COMMENT '盘点状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存盘点结果表';

-- ----------------------------
-- Table structure for wm_storage_area
-- ----------------------------
DROP TABLE IF EXISTS `wm_storage_area`;
CREATE TABLE `wm_storage_area` (
  `area_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库位ID',
  `area_code` varchar(64) NOT NULL COMMENT '库位编码',
  `area_name` varchar(255) NOT NULL COMMENT '库位名称',
  `location_id` bigint NOT NULL COMMENT '库区ID',
  `area` double(8,2) DEFAULT NULL COMMENT '面积',
  `max_loa` double(8,2) DEFAULT NULL COMMENT '最大载重量',
  `position_x` int DEFAULT NULL COMMENT '库位位置X',
  `position_y` int DEFAULT NULL COMMENT '库位位置y',
  `position_z` int DEFAULT NULL COMMENT '库位位置z',
  `enable_flag` char(1) DEFAULT NULL COMMENT '是否启用',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`area_id`) USING BTREE,
  UNIQUE KEY `AREA_CODE_UNIQUE` (`area_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库位表';

-- ----------------------------
-- Records of wm_storage_area
-- ----------------------------
BEGIN;
INSERT INTO `wm_storage_area` VALUES (1, 'XBKKW_VIRTUAL', '线边库库位-虚拟', 1, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:19', '', NULL, NULL, NULL);
INSERT INTO `wm_storage_area` VALUES (2, 'WASTE_XBKKW_VIRTUAL', '废料线边库库位-虚拟', 2, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:19', '', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for wm_storage_location
-- ----------------------------
DROP TABLE IF EXISTS `wm_storage_location`;
CREATE TABLE `wm_storage_location` (
  `location_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库区ID',
  `location_code` varchar(64) NOT NULL COMMENT '库区编码',
  `location_name` varchar(255) NOT NULL COMMENT '库区名称',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `area` double(12,2) DEFAULT NULL COMMENT '面积',
  `area_flag` char(1) DEFAULT 'N' COMMENT '是否开启库位管理',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`location_id`) USING BTREE,
  UNIQUE KEY `LOCATION_CODE_UNIQUE` (`location_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库区表';

-- ----------------------------
-- Records of wm_storage_location
-- ----------------------------
BEGIN;
INSERT INTO `wm_storage_location` VALUES (1, 'XBKKQ_VIRTUAL', '线边库库区-虚拟', 1, NULL, 'Y', '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:19', '', NULL, NULL, NULL);
INSERT INTO `wm_storage_location` VALUES (2, 'WASTE_XBKKQ_VIRTUAL', '废料线边库库区-虚拟', 2, NULL, 'Y', '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:19', '', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for wm_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wm_transaction`;
CREATE TABLE `wm_transaction` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT COMMENT '事务ID',
  `transaction_type` varchar(64) NOT NULL COMMENT '事务类型',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '入库批次号',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '库位名称',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编号',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(64) DEFAULT NULL COMMENT '供应商简称',
  `source_doc_type` varchar(64) DEFAULT NULL COMMENT '单据类型',
  `source_doc_id` bigint DEFAULT NULL COMMENT '单据ID',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT '单据编号',
  `source_doc_line_id` bigint DEFAULT NULL COMMENT '单据行ID',
  `material_stock_id` bigint NOT NULL COMMENT '库存记录ID',
  `transaction_flag` int DEFAULT '1' COMMENT '库存方向',
  `transaction_quantity` double(12,2) DEFAULT NULL COMMENT '事务数量',
  `transaction_date` datetime DEFAULT NULL COMMENT '事务日期',
  `related_transaction_id` bigint DEFAULT NULL COMMENT '关联的事务ID',
  `erp_date` datetime DEFAULT NULL COMMENT 'ERP账期',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `recpt_date` datetime DEFAULT NULL COMMENT '接收日期',
  `expire_date` datetime DEFAULT NULL COMMENT '库存有效期',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存事务表';

-- ----------------------------
-- Table structure for wm_transfer
-- ----------------------------
DROP TABLE IF EXISTS `wm_transfer`;
CREATE TABLE `wm_transfer` (
  `transfer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '转移单ID',
  `transfer_code` varchar(64) NOT NULL COMMENT '转移单编号',
  `transfer_name` varchar(255) DEFAULT NULL COMMENT '转移单名称',
  `transfer_type` varchar(64) NOT NULL COMMENT '转移单类型',
  `destination` varchar(255) DEFAULT NULL COMMENT '目的地',
  `carrier` varchar(64) DEFAULT NULL COMMENT '承运商',
  `booking_note` varchar(64) DEFAULT NULL COMMENT '托运单号',
  `receiver` varchar(64) DEFAULT NULL COMMENT '收货人',
  `receiver_nick` varchar(64) DEFAULT NULL COMMENT '收货人名称',
  `from_warehouse_id` bigint DEFAULT NULL COMMENT '移出仓库ID',
  `from_warehouse_code` varchar(64) DEFAULT NULL COMMENT '移出仓库编码',
  `from_warehouse_name` varchar(255) DEFAULT NULL COMMENT '移出仓库名称',
  `to_warehouse_id` bigint DEFAULT NULL COMMENT '移入仓库ID',
  `to_warehouse_code` varchar(64) DEFAULT NULL COMMENT '移入仓库编码',
  `to_warehouse_name` varchar(255) DEFAULT NULL COMMENT '移入仓库名称',
  `transfer_date` datetime DEFAULT NULL COMMENT '转移日期',
  `status` varchar(64) DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`transfer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='转移单表';

-- ----------------------------
-- Table structure for wm_transfer_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_transfer_line`;
CREATE TABLE `wm_transfer_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细行ID',
  `transfer_id` bigint NOT NULL COMMENT '装箱单ID',
  `material_stock_id` bigint NOT NULL COMMENT '库存记录ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) DEFAULT NULL COMMENT '单位',
  `quantity_transfer` double(12,2) NOT NULL COMMENT '装箱数量',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) DEFAULT NULL COMMENT '生产工单编号',
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `from_warehouse_id` bigint DEFAULT NULL COMMENT '移出仓库ID',
  `from_warehouse_code` varchar(64) DEFAULT NULL COMMENT '移出仓库编码',
  `from_warehouse_name` varchar(255) DEFAULT NULL COMMENT '移出仓库名称',
  `from_location_id` bigint DEFAULT NULL COMMENT '移出库区ID',
  `from_location_code` varchar(64) DEFAULT NULL COMMENT '移出库区编码',
  `from_location_name` varchar(255) DEFAULT NULL COMMENT '移出库区名称',
  `from_area_id` bigint DEFAULT NULL COMMENT '移出库位ID',
  `from_area_code` varchar(64) DEFAULT NULL COMMENT '移出库位编码',
  `from_area_name` varchar(255) DEFAULT NULL COMMENT '移出库位名称',
  `to_warehouse_id` bigint DEFAULT NULL COMMENT '移入仓库ID',
  `to_warehouse_code` varchar(64) DEFAULT NULL COMMENT '移入仓库编码',
  `to_warehouse_name` varchar(255) DEFAULT NULL COMMENT '移入仓库名称',
  `to_location_id` bigint DEFAULT NULL COMMENT '移入库区ID',
  `to_location_code` varchar(64) DEFAULT NULL COMMENT '移入库区编码',
  `to_location_name` varchar(255) DEFAULT NULL COMMENT '移入库区名称',
  `to_area_id` bigint DEFAULT NULL COMMENT '移入库位ID',
  `to_area_code` varchar(64) DEFAULT NULL COMMENT '移入库位编码',
  `to_area_name` varchar(255) DEFAULT NULL COMMENT '移入库位名称',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `vendor_id` bigint DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) DEFAULT NULL COMMENT '供应商编码',
  `vendor_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `vendor_nick` varchar(255) DEFAULT NULL COMMENT '供应商简称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='转移单行表';

-- ----------------------------
-- Table structure for wm_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `wm_warehouse`;
CREATE TABLE `wm_warehouse` (
  `warehouse_id` bigint NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `warehouse_code` varchar(64) NOT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) NOT NULL COMMENT '仓库名称',
  `location` varchar(500) DEFAULT NULL COMMENT '位置',
  `area` double(12,2) DEFAULT NULL COMMENT '面积',
  `charge` varchar(64) DEFAULT NULL COMMENT '负责人',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`warehouse_id`) USING BTREE,
  UNIQUE KEY `WAREHOUSE_CODE_UNIQUE` (`warehouse_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库表';

-- ----------------------------
-- Records of wm_warehouse
-- ----------------------------
BEGIN;
INSERT INTO `wm_warehouse` VALUES (1, 'XBK_VIRTUAL', '线边库仓库-虚拟', NULL, NULL, NULL, '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:18', '', NULL, NULL, NULL);
INSERT INTO `wm_warehouse` VALUES (2, 'WASTE_XBK_VIRTUAL', '废料线边库仓库-虚拟', NULL, NULL, NULL, '', NULL, NULL, 0, 0, 'SYS_USER', '2024-06-24 16:16:18', '', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for wm_waste_header
-- ----------------------------
DROP TABLE IF EXISTS `wm_waste_header`;
CREATE TABLE `wm_waste_header` (
  `waste_id` bigint NOT NULL AUTO_INCREMENT COMMENT '废料单ID',
  `waste_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '废料单编号',
  `waste_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '废料单名称',
  `workorder_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '生产工单编码',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位名称',
  `waste_date` datetime DEFAULT NULL COMMENT '废料日期',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT NULL COMMENT '预留字段3',
  `attr4` int DEFAULT NULL COMMENT '预留字段4',
  `record_id` bigint NOT NULL COMMENT '报工记录ID',
  PRIMARY KEY (`waste_id`) USING BTREE,
  KEY `UNIQUE_INDEX_WASTE_CODE` (`waste_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='生产废料单头表';

-- ----------------------------
-- Table structure for wm_waste_line
-- ----------------------------
DROP TABLE IF EXISTS `wm_waste_line`;
CREATE TABLE `wm_waste_line` (
  `line_id` bigint NOT NULL AUTO_INCREMENT COMMENT '行ID',
  `waste_id` bigint DEFAULT NULL COMMENT '废料单ID',
  `material_stock_id` bigint DEFAULT NULL COMMENT '库存ID',
  `item_id` bigint NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '产品物料名称',
  `specification` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `quantity_waste` double(12,2) NOT NULL COMMENT '废料数量',
  `batch_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '领料批次号',
  `warehouse_id` bigint DEFAULT NULL COMMENT '仓库ID',
  `warehouse_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '仓库名称',
  `location_id` bigint DEFAULT NULL COMMENT '库区ID',
  `location_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区编码',
  `location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库区名称',
  `area_id` bigint DEFAULT NULL COMMENT '库位ID',
  `area_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位编码',
  `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库位名称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` int DEFAULT '0' COMMENT '预留字段3',
  `attr4` int DEFAULT '0' COMMENT '预留字段4',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`line_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='生产废料单行表';

SET FOREIGN_KEY_CHECKS = 1;
