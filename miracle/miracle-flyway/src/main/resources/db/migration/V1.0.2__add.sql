create table sys_role_perm
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
  perm_code VARCHAR(50) NOT NULL COMMENT '权限编码'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';
create table sys_user_role
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL COMMENT '用户ID',
  role_code VARCHAR(50) NOT NULL COMMENT '角色编码'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';