CREATE TABLE sys_user
(
  id bigint(20) auto_increment primary key,
	true_name varchar(30) null comment '姓名',
	phone varchar(20) null comment '手机号',
	email varchar(255) null comment '邮箱',
	user_name varchar(50) null comment '用户名',
	avatar varchar(255) null comment '头像',
	gender int(2) null comment '性别',
	password varchar(50) null comment '密码',
	status int(2) not null default 1 comment '状态 1正常 0禁用',
	create_date datetime null comment '创建时间',
	update_date datetime null comment '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

create table sys_role
(
	id bigint(20) auto_increment primary key,
	code varchar(50) not null comment '编码',
	name varchar(50) not null comment '名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

create table sys_user_role
(
	id bigint(20) auto_increment primary key,
	user_id bigint(20) not null comment '用户ID',
	role_id bigint(20) not null comment '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

create table sys_perm
(
  id bigint(20) auto_increment primary key,
  code varchar(100) not null comment '编码',
  name varchar(50) not null comment '名称',
  link varchar(100) null comment '菜单路径',
  parent_id bigint null comment '父级名称',
  type int(2) not null default 1 comment '类型 1菜单 2权限'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单权限';

create table sys_role_perm
(
	id bigint(20) auto_increment primary key,
	role_id bigint(20) not null comment '角色ID',
	perm_id bigint(20) not null comment '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

create table sys_dictionary
(
	id bigint(20) auto_increment primary key,
	name varchar(50) not null comment '名称',
	parent_id bigint(20) not null comment '父级ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';