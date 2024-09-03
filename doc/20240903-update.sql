-- wm表添加创建人和修改人id
ALTER TABLE wm_material_stock ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE wm_material_stock ADD COLUMN remark varchar(500) COMMENT '备注';
