CREATE TABLE hrm_rank (
                          rank_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '职级主键id',
                          rank_code VARCHAR ( 64 ) NOT NULL COMMENT '职级编码',
                          rank_type VARCHAR ( 64 ) NOT NULL COMMENT '职级层次',
                          rank_name VARCHAR ( 64 ) NOT NULL COMMENT '职级层次名称',
                          salary_range_min DECIMAL ( 10, 2 ) NOT NULL COMMENT '薪资范围下限',
                          salary_range_max DECIMAL ( 10, 2 ) NOT NULL COMMENT '薪资范围上限',
                          create_user_id BIGINT COMMENT '创建者id',
                          create_by VARCHAR ( 64 ) COMMENT '创建者',
                          update_user_id BIGINT COMMENT '修改者id',
                          update_by VARCHAR ( 64 ) COMMENT '修改者',
                          create_time DATETIME COMMENT '创建时间',
                          update_time DATETIME COMMENT '修改时间',
                          deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
                          deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
                          version INT DEFAULT 1 COMMENT '乐观锁',
                          remark VARCHAR ( 255 ) COMMENT '备注'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职级表';
ALTER TABLE hrm_staff
    ADD COLUMN actual_salary DECIMAL ( 10, 2 ) COMMENT '实际薪资',
    ADD COLUMN rank_id BIGINT COMMENT '职级主键id',
    ADD COLUMN rank_code VARCHAR ( 64 ) COMMENT '职级编码',
    ADD COLUMN rank_name VARCHAR ( 64 ) COMMENT '职级层次名称';
