insert
into
    sys_menu
( menu_name, order_num, path, is_frame, is_cache, menu_type, visible, status, icon, create_by, module_type, create_time)
values
    ( '员工管理', 14, 'hrm/st', '1', '0', 'M', '0', '0', 'user', 'admin', '1', sysdate());

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记', '2385', '1', 'hrmStaff', 'hrm/st/hrm-staff/index', 1, 0, 'C', '0', '0', 'st:hrmstaff:list', '#', 'admin', sysdate(), '', null, '员工登记菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'st:hrmstaff:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'st:hrmstaff:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'st:hrmstaff:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'st:hrmstaff:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('员工登记导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'st:hrmstaff:export',       '#', 'admin', sysdate(), '', null, '');
