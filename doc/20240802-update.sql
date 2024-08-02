-- md 添加创建人id和修改人id
ALTER TABLE md_workstation ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_workshop ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_vendor ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_workstation_tool ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_workstation_worker ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_client ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_product_sip ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_product_sop ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE md_produt_sop ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';


-- pro_process和pro_route相关 添加创建人id和修改人id
ALTER TABLE pro_process ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE pro_process_content ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE pro_route ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE pro_route_process ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE pro_route_product ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE pro_route_product_bom ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
