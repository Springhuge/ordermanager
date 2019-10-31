#sql语句

#2019.10.2 季虎 用户表
CREATE TABLE `sys_user` (
  `id` varchar(250) NOT NULL,
  `username` varchar(250) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#2019.10.21 季虎 为用户表添加性别
alter table sys_user
add column(sex varchar(2));

#2019.10.28 季虎 角色表
create table sys_role (
	id varchar(250) not null,
    role varchar(250) default null,
    description varchar(250)  default null,
	version int(11) DEFAULT NULL,
	deleted int(11) DEFAULT NULL,
    primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#2019.10.28 为权限表添加初始数据
insert into sys_role (id,role,description,deleted)
values
("50fb109c-a326-4151-a8a0-05048071d5d1","超级管理员","系统最高权限",0),
("5a84e32e-01c8-4131-8de6-f621281358e5","用户管理员","用户数据处理",0),
("e1b2ceb8-d0eb-490f-ac62-33d5eabd46d7","许可管理员","许可数据处理",0);

#2019.10.30 季虎 许可表
create table sys_permission(
	id varchar(250) not null,
	permission varchar(250) DEFAULT null ,
	description varchar(250) DEFAULT null,
	version int(11) DEFAULT null,
	deleted int(11) DEFAULT null,
	PRIMARY key(id)
)engine=INNODB default CHARSET=utf8;

#为许可表添加初始数据
INSERT into sys_permission (id,permission,description,deleted)
values 
("e756c450-4754-408f-bb74-9346f3538d37","http://localhost:8080/user/*","用户",0),
("fcfbc83d-36ce-44cb-a390-cf5b375d38a9","http://localhost:8080/role/*","角色",0),
("6c9f24eb-d11c-4dd7-8d28-18da489461c3","http://localhost:8080/permission/*","许可",0)


#2019.10.30 季虎 添加用户角色中间表
create table sys_user_role(
	id varchar(250) not null,
	userId varchar(250) DEFAULT null,
	roleId varchar(250) default null,
	version int(2) DEFAULT null,
	deleted int(2) default null
)ENGINE=INNODB CHARSET=utf8;
