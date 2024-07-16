create table hrm_staff
(
    staff_id                bigint auto_increment primary key comment '员工主键id',
    staff_code              varchar(64) comment '员工编码',
    staff_name              varchar(64) not null comment '员工名称',
    contact_phone           varchar(20) comment '联系电话',
    sex                     tinyint(2) default 0 comment '用户性别（0男 1女 2未知）',
    ethnicity               varchar(64) comment '民族',
    birth_date              date comment '出生年月',
    email                   varchar(128) comment '邮箱',
    marital_status          tinyint(2) default 0 comment '婚姻状况（0-未婚，1-已婚）',
    political_outlook       tinyint(2) default 0 comment '政治面貌（0-群众，1-党员）',
    id_card                 varchar(18) comment '身份证号',
    origin                  varchar(128) comment '籍贯',
    household_address       varchar(200) comment '户口详细地址',
    current_address         varchar(200) comment '现住址',
    emergency_contact       varchar(64) comment '紧急联系人',
    relationship            varchar(64) comment '关系',
    emergency_contact_phone varchar(20) comment '紧急联系电话',
    education               tinyint(2) default 0 comment '学历（0-初中，1-高中，2-专科，4-本科，5-研究生，6-博士）',
    joined_time             datetime comment '入职日期',
    leave_time              datetime comment '离职日期',
    create_user_id          bigint comment '创建者id',
    create_by               varchar(64) comment '创建者',
    update_user_id          bigint comment '修改者id',
    update_by               varchar(64) comment '修改者',
    create_time             datetime comment '创建时间',
    update_time             datetime comment '修改时间',
    status                  tinyint(2) default 0 comment '状态（0-备选，1-面试，2-面试通过，3-入职申请，4-入职通过，5-面试未通过）',
    remark                  varchar(255) comment '备注'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='员工花名册表';
