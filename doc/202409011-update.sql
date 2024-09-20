-- wm表添加创建人和修改人id
ALTER TABLE wm_material_stock ADD COLUMN create_user_id BIGINT COMMENT '创建人id',ADD COLUMN update_user_id BIGINT COMMENT '修改人id';
ALTER TABLE wm_material_stock ADD COLUMN remark varchar(500) COMMENT '备注';

ALTER TABLE gen_table ADD COLUMN sub_system_name varchar(32) COMMENT '子系统名称';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试管理', '2385', '1', 'hrmInterviewRecord', 'mes/st/hrm-interview-record/index', 1, 0, 'C', '0', '0', 'st:hrminterviewrecord:list', '#', 'admin', sysdate(), '', null, '面试记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试记录查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'st:hrminterviewrecord:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试记录新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'st:hrminterviewrecord:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试记录修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'st:hrminterviewrecord:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试记录删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'st:hrminterviewrecord:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('面试记录导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'st:hrminterviewrecord:export',       '#', 'admin', sysdate(), '', null, '');