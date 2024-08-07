-- 更新图标
UPDATE `sys_menu` SET  `icon` = '/static/icons/u1193.svg' WHERE `menu_id` = 2342;
UPDATE `sys_menu` SET  `icon` = '/static/icons/u1175.svg' WHERE `menu_id` = 2343;
UPDATE `sys_menu` SET  `icon` = '/static/icons/u1199.svg' WHERE `menu_id` = 2344;
UPDATE `sys_menu` SET  `icon` = '/static/icons/u1196.svg' WHERE `menu_id` = 2345;
UPDATE `sys_menu` SET  `icon` = '/static/icons/u1181.svg' WHERE `menu_id` = 2346;


-- 添加字段
ALTER TABLE wm_item_recpt ADD total_amount decimal(12,4) COMMENT '总金额';
ALTER TABLE wm_item_recpt_line ADD amount decimal(12,4) COMMENT '金额';