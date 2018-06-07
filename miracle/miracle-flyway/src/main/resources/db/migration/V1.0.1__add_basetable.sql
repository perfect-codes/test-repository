create table sys_user
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  phone varchar(50) NOT NULL COMMENT '手机号',
  email varchar(50) NULL COMMENT '邮箱',
  id_number varchar(50) NULL COMMENT '身份证号',
  password varchar(50) NOT NULL COMMENT '密码',
  truename varchar(50) NULL COMMENT '姓名',
  gender INT(1) NULL COMMENT '性别',
  status INT(1) NOT NULL DEFAULT 1 COMMENT '状态，0不可用 1可用 2删除',
  create_date datetime NULL COMMENT '创建时间',
  update_date datetime NULL COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';
create table sys_perm
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) NOT NULL COMMENT '名称',
  code varchar(50) NOT NULL COMMENT '编码',
  status INT(1) NOT NULL DEFAULT 1 COMMENT '状态，0不可用 1可用 2删除',
  type INT(1) NOT NULL DEFAULT 1 COMMENT '类型，1操作权限 2菜单',
  link varchar(100) NULL COMMENT '链接',
  parent_id BIGINT NULL COMMENT '父节点',
  level INT(1) NULL COMMENT '级别',
  create_date datetime NULL COMMENT '创建时间',
  update_date datetime NULL COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限';
create table sys_role
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) NOT NULL COMMENT '名称',
  code varchar(50) NOT NULL COMMENT '编码',
  status int NOT NULL DEFAULT 0 COMMENT '状态，0不可用 1可用 2删除',
  create_date datetime NULL COMMENT '创建时间',
  update_date datetime NULL COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';