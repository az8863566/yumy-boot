-- ==========================================================
-- 1. 用户管理表 (去除了 dept_id)
-- ==========================================================
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(64) NOT NULL,
    avatar VARCHAR(255),
    email VARCHAR(128),
    phone VARCHAR(32),
    status INT2 DEFAULT 1, -- 状态(1正常 0停用)
    deleted INT2 DEFAULT 0, -- 逻辑删除(0未删 1已删)
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
CREATE UNIQUE INDEX uk_sys_user_username ON sys_user(username) WHERE deleted = 0;
COMMENT ON TABLE sys_user IS '用户信息表';

-- ==========================================================
-- 2. 角色管理表 (去除了 data_scope)
-- ==========================================================
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY,
    role_name VARCHAR(64) NOT NULL, -- 角色名称(如: 超级管理员)
    role_code VARCHAR(64) NOT NULL, -- 角色权限字符串(如: admin)
    status INT2 DEFAULT 1,
    deleted INT2 DEFAULT 0,
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_role IS '角色信息表';

-- sys_menu, sys_user_role, sys_role_menu 保持不变，它们构成了功能权限的核心

-- ==========================================================
-- 3. 菜单/权限管理表
-- ==========================================================
CREATE TABLE sys_menu (
    menu_id BIGINT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0, -- 父菜单ID
    menu_name VARCHAR(64) NOT NULL,
    menu_type INT2 NOT NULL, -- 菜单类型(1目录 2菜单 3按钮)
    path VARCHAR(255), -- 路由地址
    component VARCHAR(255), -- 组件路径(Vue/React)
    perms VARCHAR(128), -- 权限标识(如: sys:user:add)
    icon VARCHAR(128), -- 菜单图标
    sort_order INT4 DEFAULT 0, -- 显示顺序
    visible INT2 DEFAULT 1, -- 菜单显示状态(1显示 0隐藏)
    status INT2 DEFAULT 1, -- 菜单可用状态(1正常 0停用)
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_menu IS '菜单权限表';

-- ==========================================================
-- 4. 关联表：用户-角色 / 角色-菜单
-- ==========================================================
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
COMMENT ON TABLE sys_user_role IS '用户和角色关联表';

CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
);
COMMENT ON TABLE sys_role_menu IS '角色和菜单关联表';

-- ==========================================================
-- 5. 操作日志表 (安全审计)
-- ==========================================================
CREATE TABLE sys_oper_log (
    oper_id BIGINT PRIMARY KEY,
    title VARCHAR(64), -- 模块标题
    business_type INT2, -- 业务类型(1新增 2修改 3删除 4导出等)
    method VARCHAR(255), -- 方法名称
    request_method VARCHAR(10), -- 请求方式 (GET, POST等)
    oper_name VARCHAR(64), -- 操作人员
    oper_url VARCHAR(255), -- 请求URL
    oper_ip VARCHAR(128), -- 主机地址
    oper_param TEXT, -- 请求参数 (PostgreSQL中TEXT性能很好，也可用JSONB)
    json_result TEXT, -- 返回参数
    status INT2, -- 操作状态(1正常 0异常)
    error_msg TEXT, -- 错误消息
    oper_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 操作时间
    cost_time BIGINT -- 消耗时间(毫秒)
);
CREATE INDEX idx_sys_oper_log_time ON sys_oper_log(oper_time);
COMMENT ON TABLE sys_oper_log IS '操作日志记录';

-- ==========================================================
-- 6. 字典类型表
-- ==========================================================
CREATE TABLE sys_dict_type (
    dict_id BIGINT PRIMARY KEY,
    dict_name VARCHAR(128) DEFAULT '', -- 字典名称
    dict_type VARCHAR(128) DEFAULT '', -- 字典类型(如: sys_user_sex)
    status INT2 DEFAULT 1,
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
CREATE UNIQUE INDEX uk_sys_dict_type ON sys_dict_type(dict_type);
COMMENT ON TABLE sys_dict_type IS '字典类型表';

-- ==========================================================
-- 7. 字典数据表
-- ==========================================================
CREATE TABLE sys_dict_data (
    dict_code BIGINT PRIMARY KEY,
    dict_sort INT4 DEFAULT 0, -- 字典排序
    dict_label VARCHAR(128) DEFAULT '', -- 字典标签 (展示值)
    dict_value VARCHAR(128) DEFAULT '', -- 字典键值 (实际值)
    dict_type VARCHAR(128) DEFAULT '', -- 字典类型 (关联sys_dict_type)
    css_class VARCHAR(128), -- 样式属性 (前端用)
    list_class VARCHAR(128), -- 表格回显样式 (如: primary, danger)
    is_default INT2 DEFAULT 0, -- 是否默认(1是 0否)
    status INT2 DEFAULT 1,
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
COMMENT ON TABLE sys_dict_data IS '字典数据表';

-- ==========================================================
-- 8. 参数配置表
-- ==========================================================
CREATE TABLE sys_config (
    config_id BIGINT PRIMARY KEY,
    config_name VARCHAR(128) DEFAULT '', -- 参数名称
    config_key VARCHAR(128) DEFAULT '', -- 参数键名 (如: sys.user.initPassword)
    config_value TEXT, -- 参数键值
    config_type INT2 DEFAULT 2, -- 系统内置(1是 2否) - 内置参数不可删除
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark VARCHAR(500)
);
CREATE UNIQUE INDEX uk_sys_config_key ON sys_config(config_key);
COMMENT ON TABLE sys_config IS '参数配置表';