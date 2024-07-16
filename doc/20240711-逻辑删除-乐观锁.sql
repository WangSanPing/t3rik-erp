-- cal_holiday
ALTER TABLE cal_holiday
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_plan
ALTER TABLE cal_plan
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_plan_team
ALTER TABLE cal_plan_team
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_shift
ALTER TABLE cal_shift
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_team
ALTER TABLE cal_team
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_team_member
ALTER TABLE cal_team_member
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- cal_teamshift
ALTER TABLE cal_teamshift
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- db_test
ALTER TABLE db_test
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_check_machinery
ALTER TABLE dv_check_machinery
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_check_plan
ALTER TABLE dv_check_plan
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_check_record
ALTER TABLE dv_check_record
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_check_record_line
ALTER TABLE dv_check_record_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_check_subject
ALTER TABLE dv_check_subject
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_machinery
ALTER TABLE dv_machinery
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_machinery_type
ALTER TABLE dv_machinery_type
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_mainten_record
ALTER TABLE dv_mainten_record
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_mainten_record_line
ALTER TABLE dv_mainten_record_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_repair
ALTER TABLE dv_repair
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_repair_line
ALTER TABLE dv_repair_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- dv_subject
ALTER TABLE dv_subject
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- gen_table
ALTER TABLE gen_table
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- gen_table_column
ALTER TABLE gen_table_column
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- hrm_staff
ALTER TABLE hrm_staff
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_client
ALTER TABLE md_client
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_item
ALTER TABLE md_item
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_item_type
ALTER TABLE md_item_type
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_product_bom
ALTER TABLE md_product_bom
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_product_sip
ALTER TABLE md_product_sip
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_product_sop
ALTER TABLE md_product_sop
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_produt_sop
ALTER TABLE md_produt_sop
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_unit_measure
ALTER TABLE md_unit_measure
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_vendor
ALTER TABLE md_vendor
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_workshop
ALTER TABLE md_workshop
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_workstation
ALTER TABLE md_workstation
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_workstation_machine
ALTER TABLE md_workstation_machine
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_workstation_tool
ALTER TABLE md_workstation_tool
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- md_workstation_worker
ALTER TABLE md_workstation_worker
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- print_printer_config
ALTER TABLE print_printer_config
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- print_template
ALTER TABLE print_template
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_andon_record
ALTER TABLE pro_andon_record
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_client_order
ALTER TABLE pro_client_order
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_client_order_item
ALTER TABLE pro_client_order_item
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_feedback
ALTER TABLE pro_feedback
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_process
ALTER TABLE pro_process
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_process_content
ALTER TABLE pro_process_content
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_route
ALTER TABLE pro_route
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_route_process
ALTER TABLE pro_route_process
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_route_product
ALTER TABLE pro_route_product
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_route_product_bom
ALTER TABLE pro_route_product_bom
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_shutdown_record
ALTER TABLE pro_shutdown_record
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_task
ALTER TABLE pro_task
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_task_issue
ALTER TABLE pro_task_issue
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';


-- pro_trans_consume
ALTER TABLE pro_trans_consume
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_trans_order
ALTER TABLE pro_trans_order
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_user_workstation
ALTER TABLE pro_user_workstation
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_workorder
ALTER TABLE pro_workorder
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_workorder_bom
ALTER TABLE pro_workorder_bom
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- pro_workrecord
ALTER TABLE pro_workrecord
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_defect
ALTER TABLE qc_defect
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_defect_record
ALTER TABLE qc_defect_record
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- qc_index
ALTER TABLE qc_index
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_ipqc
ALTER TABLE qc_ipqc
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_ipqc_line
ALTER TABLE qc_ipqc_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_iqc
ALTER TABLE qc_iqc
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_iqc_defect
ALTER TABLE qc_iqc_defect
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_iqc_line
ALTER TABLE qc_iqc_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- qc_oqc
ALTER TABLE qc_oqc
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_oqc_line
ALTER TABLE qc_oqc_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_template
ALTER TABLE qc_template
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_template_index
ALTER TABLE qc_template_index
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- qc_template_product
ALTER TABLE qc_template_product
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_attachment
ALTER TABLE sys_attachment
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_auto_code_part
ALTER TABLE sys_auto_code_part
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_auto_code_result
ALTER TABLE sys_auto_code_result
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_auto_code_rule
ALTER TABLE sys_auto_code_rule
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_config
ALTER TABLE sys_config
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_config_jasypt
ALTER TABLE sys_config_jasypt
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_dept
ALTER TABLE sys_dept
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_dict_data
ALTER TABLE sys_dict_data
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_dict_type
ALTER TABLE sys_dict_type
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_job
ALTER TABLE sys_job
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_job_log
ALTER TABLE sys_job_log
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_logininfor
ALTER TABLE sys_logininfor
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_menu
ALTER TABLE sys_menu
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_message
ALTER TABLE sys_message
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_notice
ALTER TABLE sys_notice
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_oper_log
ALTER TABLE sys_oper_log
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_post
ALTER TABLE sys_post
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_role
ALTER TABLE sys_role
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_role_dept
ALTER TABLE sys_role_dept
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_role_menu
ALTER TABLE sys_role_menu
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_user
ALTER TABLE sys_user
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- sys_user_post
ALTER TABLE sys_user_post
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- sys_user_role
ALTER TABLE sys_user_role
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- test_tab
ALTER TABLE test_tab
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- tm_tool
ALTER TABLE tm_tool
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- tm_tool_type
ALTER TABLE tm_tool_type
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- ureport_file_tbl
ALTER TABLE ureport_file_tbl
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- wm_barcode
ALTER TABLE wm_barcode
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_barcode_config
ALTER TABLE wm_barcode_config
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_issue_header
ALTER TABLE wm_issue_header
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_issue_line
ALTER TABLE wm_issue_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- wm_item_consume
ALTER TABLE wm_item_consume
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_item_consume_line
ALTER TABLE wm_item_consume_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_item_recpt
ALTER TABLE wm_item_recpt
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_item_recpt_line
ALTER TABLE wm_item_recpt_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';
-- wm_material_stock
ALTER TABLE wm_material_stock
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_outsource_issue
ALTER TABLE wm_outsource_issue
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_outsource_issue_line
ALTER TABLE wm_outsource_issue_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_outsource_recpt
ALTER TABLE wm_outsource_recpt
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_outsource_recpt_line
ALTER TABLE wm_outsource_recpt_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_package
ALTER TABLE wm_package
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_package_line
ALTER TABLE wm_package_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_pback_header
ALTER TABLE wm_pback_header
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_pback_line
ALTER TABLE wm_pback_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_produce
ALTER TABLE wm_product_produce
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_produce_line
ALTER TABLE wm_product_produce_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_recpt
ALTER TABLE wm_product_recpt
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_recpt_line
ALTER TABLE wm_product_recpt_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_salse
ALTER TABLE wm_product_salse
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_product_salse_line
ALTER TABLE wm_product_salse_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_issue
ALTER TABLE wm_rt_issue
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_issue_line
ALTER TABLE wm_rt_issue_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_salse
ALTER TABLE wm_rt_salse
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_salse_line
ALTER TABLE wm_rt_salse_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_vendor
ALTER TABLE wm_rt_vendor
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_rt_vendor_line
ALTER TABLE wm_rt_vendor_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_sn
ALTER TABLE wm_sn
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_stock_taking
ALTER TABLE wm_stock_taking
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_stock_taking_line
ALTER TABLE wm_stock_taking_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_stock_taking_result
ALTER TABLE wm_stock_taking_result
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_storage_area
ALTER TABLE wm_storage_area
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_storage_location
ALTER TABLE wm_storage_location
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_transaction
ALTER TABLE wm_transaction
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_transfer
ALTER TABLE wm_transfer
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_transfer_line
ALTER TABLE wm_transfer_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_warehouse
ALTER TABLE wm_warehouse
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_waste_header
ALTER TABLE wm_waste_header
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';

-- wm_waste_line
ALTER TABLE wm_waste_line
    ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除字段 0:未删除 1:已删除',
    ADD COLUMN deleteAt DATETIME DEFAULT '1000-01-01 00:00:00' COMMENT '逻辑删除辅助字段',
    ADD COLUMN version INT DEFAULT 1 COMMENT '乐观锁';