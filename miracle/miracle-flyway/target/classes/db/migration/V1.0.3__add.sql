create table sys_dictionary
(
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50) NOT NULL COMMENT '编码',
  name VARCHAR(50) NOT NULL COMMENT '名称',
  status INT(1) NOT NULL DEFAULT 1 COMMENT '状态，0不可用 1可用 2删除',
  parent_id BIGINT NULL COMMENT '父节点',
  level INT(1) NULL COMMENT '级别',
  is_leaf TINYINT(1) NULL COMMENT '叶子节点',
  create_date datetime NULL COMMENT '创建时间',
  update_date datetime NULL COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';