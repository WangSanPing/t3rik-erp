DROP TABLE IF EXISTS `hrm_attendance_records`;
CREATE TABLE hrm_attendance_records (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '考勤记录ID',
                                        staff_id BIGINT NOT NULL COMMENT '员工ID',
                                        attendance_date DATE NOT NULL COMMENT '考勤日期',
                                        check_in_time DATETIME DEFAULT NULL COMMENT '签到时间',
                                        check_out_time DATETIME DEFAULT NULL COMMENT '签退时间',
                                        work_hours DECIMAL(5,2) DEFAULT NULL COMMENT '当天工作时长（小时）',
                                        location VARCHAR(255) DEFAULT NULL COMMENT '签到位置',
                                        status TINYINT NOT NULL COMMENT '考勤状态（字典表type=attendance_status）',
                                        create_user_id BIGINT COMMENT '创建人ID',
                                        create_by VARCHAR(64) COMMENT '创建人',
                                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        update_user_id BIGINT COMMENT '更新人ID',
                                        update_by VARCHAR(64) COMMENT '更新人',
                                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        remark VARCHAR(255) COMMENT '备注',
                                        deleted TINYINT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                        deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                        version INT DEFAULT 1 COMMENT '乐观锁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工考勤记录表';

DROP TABLE IF EXISTS `hrm_leave_records`;
CREATE TABLE hrm_leave_records (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '请假记录ID',
                                   staff_id BIGINT NOT NULL COMMENT '员工ID',
                                   leave_type TINYINT NOT NULL COMMENT '请假类型（字典表type=leave_type）',
                                   start_time DATETIME NOT NULL COMMENT '请假开始时间',
                                   end_time DATETIME NOT NULL COMMENT '请假结束时间',
                                   reason TEXT COMMENT '请假原因',
                                   status TINYINT NOT NULL COMMENT '考勤状态（字典表type=attendance_status）',
                                   create_user_id BIGINT COMMENT '创建人ID',
                                   create_by VARCHAR(64) COMMENT '创建人',
                                   create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   update_user_id BIGINT COMMENT '更新人ID',
                                   update_by VARCHAR(64) COMMENT '更新人',
                                   update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   remark VARCHAR(255) COMMENT '备注',
                                   deleted TINYINT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                   deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                   version INT DEFAULT 1 COMMENT '乐观锁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工请假记录表';

DROP TABLE IF EXISTS `hrm_overtime_records`;
CREATE TABLE hrm_overtime_records (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '加班记录ID',
                                      staff_id BIGINT NOT NULL COMMENT '员工ID',
                                      overtime_date DATE NOT NULL COMMENT '加班日期',
                                      start_time TIME NOT NULL COMMENT '加班开始时间',
                                      end_time TIME NOT NULL COMMENT '加班结束时间',
                                      duration DECIMAL(5,2) NOT NULL COMMENT '加班时长（小时）',
                                      reason TEXT COMMENT '加班原因',
                                      status TINYINT NOT NULL COMMENT '考勤状态（字典表type=attendance_status）',
                                      create_user_id BIGINT COMMENT '创建人ID',
                                      create_by VARCHAR(64) COMMENT '创建人',
                                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      update_user_id BIGINT COMMENT '更新人ID',
                                      update_by VARCHAR(64) COMMENT '更新人',
                                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      remark VARCHAR(255) COMMENT '备注',
                                      deleted TINYINT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                      deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                      version INT DEFAULT 1 COMMENT '乐观锁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工加班记录表';

DROP TABLE IF EXISTS `hrm_attendance_rules`;
CREATE TABLE hrm_attendance_rules (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '规则ID',
                                      name VARCHAR(100) NOT NULL COMMENT '规则名称',
                                      staff_id BIGINT DEFAULT NULL COMMENT '适用员工ID（NULL表示所有员工适用）',
                                      department_id BIGINT DEFAULT NULL COMMENT '适用部门ID（NULL表示所有部门适用）',
                                      location_name VARCHAR(255) NOT NULL COMMENT '打卡地点名称',
                                      latitude DECIMAL(10, 6) NOT NULL COMMENT '打卡地点纬度',
                                      longitude DECIMAL(10, 6) NOT NULL COMMENT '打卡地点经度',
                                      radius INT NOT NULL DEFAULT 100 COMMENT '有效打卡范围（米）',
                                      start_time TIME NOT NULL COMMENT '允许打卡开始时间',
                                      end_time TIME NOT NULL COMMENT '允许打卡结束时间',
                                      late_after TIME DEFAULT NULL COMMENT '超过此时间视为迟到',
                                      early_before TIME DEFAULT NULL COMMENT '早于此时间视为早退',
                                      status TINYINT DEFAULT 0 NOT NULL COMMENT '规则状态（0正常1停用）',
                                      create_user_id BIGINT COMMENT '创建人ID',
                                      create_by VARCHAR(64) COMMENT '创建人',
                                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      update_user_id BIGINT COMMENT '更新人ID',
                                      update_by VARCHAR(64) COMMENT '更新人',
                                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      remark VARCHAR(255) COMMENT '备注',
                                      deleted TINYINT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
                                      deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                                      version INT DEFAULT 1 COMMENT '乐观锁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤打卡规则表';

