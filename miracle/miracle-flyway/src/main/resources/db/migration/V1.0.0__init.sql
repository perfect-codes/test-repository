create table sys_role
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code varchar(255) NOT NULL COMMENT '编码',
  create_date datetime NULL COMMENT '创建时间',
  name varchar(255) NOT NULL COMMENT '名称',
  status int NOT NULL DEFAULT 0 COMMENT '状态，0可用 1不可用 2删除',
  update_date datetime NULL COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';