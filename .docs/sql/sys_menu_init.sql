-- ==========================================
-- 系统菜单初始化脚本
-- 表名: admin.sys_menu
-- ==========================================

-- 清空现有数据（可选）
-- TRUNCATE TABLE admin.sys_menu RESTART IDENTITY CASCADE;

-- ==========================================
-- 一级菜单：系统管理 (sort_order: 1)
-- ==========================================
INSERT INTO admin.sys_menu (menu_id, parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, visible, status, deleted, create_time, update_time, create_by, update_by, remark)
VALUES 
(1000, 0, '系统管理', 1, '/system', NULL, NULL, 'settings', 1, 1, 1, 0, NOW(), NOW(), 1, 1, '系统管理目录'),

-- 系统管理 - 子菜单
(1100, 1000, '用户管理', 2, '/system/user', 'system/user/index', 'system:user:list', 'user', 1, 1, 1, 0, NOW(), NOW(), 1, 1, '用户管理菜单'),
(1200, 1000, '角色管理', 2, '/system/role', 'system/role/index', 'system:role:list', 'shield', 2, 1, 1, 0, NOW(), NOW(), 1, 1, '角色管理菜单'),
(1300, 1000, '菜单管理', 2, '/system/menu', 'system/menu/index', 'system:menu:list', 'menu', 3, 1, 1, 0, NOW(), NOW(), 1, 1, '菜单管理菜单'),
(1400, 1000, '操作日志', 2, '/system/operlog', 'system/operlog/index', 'system:operlog:list', 'document', 4, 1, 1, 0, NOW(), NOW(), 1, 1, '操作日志菜单'),
(1500, 1000, '数据字典', 2, '/system/dict', 'system/dict/index', 'system:dict:list', 'dictionary', 5, 1, 1, 0, NOW(), NOW(), 1, 1, '数据字典菜单'),
(1600, 1000, '系统配置', 2, '/system/config', 'system/config/index', 'system:config:list', 'setting', 6, 1, 1, 0, NOW(), NOW(), 1, 1, '系统配置菜单'),

-- 用户管理 - 按钮权限
(1101, 1100, '用户新增', 3, NULL, NULL, 'system:user:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '用户新增按钮'),
(1102, 1100, '用户编辑', 3, NULL, NULL, 'system:user:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '用户编辑按钮'),
(1103, 1100, '用户删除', 3, NULL, NULL, 'system:user:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '用户删除按钮'),
(1104, 1100, '用户导出', 3, NULL, NULL, 'system:user:export', NULL, 4, 1, 1, 0, NOW(), NOW(), 1, 1, '用户导出按钮'),
(1105, 1100, '用户导入', 3, NULL, NULL, 'system:user:import', NULL, 5, 1, 1, 0, NOW(), NOW(), 1, 1, '用户导入按钮'),
(1106, 1100, '用户详情', 3, NULL, NULL, 'system:user:detail', NULL, 6, 1, 1, 0, NOW(), NOW(), 1, 1, '用户详情按钮'),

-- 角色管理 - 按钮权限
(1201, 1200, '角色新增', 3, NULL, NULL, 'system:role:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '角色新增按钮'),
(1202, 1200, '角色编辑', 3, NULL, NULL, 'system:role:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '角色编辑按钮'),
(1203, 1200, '角色删除', 3, NULL, NULL, 'system:role:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '角色删除按钮'),
(1204, 1200, '角色导出', 3, NULL, NULL, 'system:role:export', NULL, 4, 1, 1, 0, NOW(), NOW(), 1, 1, '角色导出按钮'),

-- 菜单管理 - 按钮权限
(1301, 1300, '菜单新增', 3, NULL, NULL, 'system:menu:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '菜单新增按钮'),
(1302, 1300, '菜单编辑', 3, NULL, NULL, 'system:menu:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '菜单编辑按钮'),
(1303, 1300, '菜单删除', 3, NULL, NULL, 'system:menu:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '菜单删除按钮'),

-- 操作日志 - 按钮权限
(1401, 1400, '日志删除', 3, NULL, NULL, 'system:operlog:delete', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '日志删除按钮'),
(1402, 1400, '日志导出', 3, NULL, NULL, 'system:operlog:export', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '日志导出按钮'),

-- 数据字典 - 按钮权限
(1501, 1500, '字典新增', 3, NULL, NULL, 'system:dict:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '字典新增按钮'),
(1502, 1500, '字典编辑', 3, NULL, NULL, 'system:dict:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '字典编辑按钮'),
(1503, 1500, '字典删除', 3, NULL, NULL, 'system:dict:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '字典删除按钮'),
(1504, 1500, '字典导出', 3, NULL, NULL, 'system:dict:export', NULL, 4, 1, 1, 0, NOW(), NOW(), 1, 1, '字典导出按钮'),

-- 系统配置 - 按钮权限
(1601, 1600, '配置新增', 3, NULL, NULL, 'system:config:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '配置新增按钮'),
(1602, 1600, '配置编辑', 3, NULL, NULL, 'system:config:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '配置编辑按钮'),
(1603, 1600, '配置删除', 3, NULL, NULL, 'system:config:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '配置删除按钮'),
(1604, 1600, '配置导出', 3, NULL, NULL, 'system:config:export', NULL, 4, 1, 1, 0, NOW(), NOW(), 1, 1, '配置导出按钮'),

-- ==========================================
-- 一级菜单：TOC管理 (sort_order: 2)
-- ==========================================
(2000, 0, 'TOC管理', 1, '/toc', NULL, NULL, 'shop', 2, 1, 1, 0, NOW(), NOW(), 1, 1, 'TOC管理目录'),

-- TOC管理 - 子菜单
(2100, 2000, '分类管理', 2, '/toc/category', 'toc/category/index', 'toc:category:list', 'category', 1, 1, 1, 0, NOW(), NOW(), 1, 1, '分类管理菜单'),
(2200, 2000, '菜谱管理', 2, '/toc/recipe', 'toc/recipe/index', 'toc:recipe:list', 'notebook', 2, 1, 1, 0, NOW(), NOW(), 1, 1, '菜谱管理菜单'),
(2300, 2000, '用户管理', 2, '/toc/user', 'toc/user/index', 'toc:user:list', 'user-filled', 3, 1, 1, 0, NOW(), NOW(), 1, 1, 'TOC用户管理菜单'),
(2400, 2000, '评论管理', 2, '/toc/comment', 'toc/comment/index', 'toc:comment:list', 'chat-dot-round', 4, 1, 1, 0, NOW(), NOW(), 1, 1, '评论管理菜单'),
(2500, 2000, '轮播图管理', 2, '/toc/banner', 'toc/banner/index', 'toc:banner:list', 'picture', 5, 1, 1, 0, NOW(), NOW(), 1, 1, '轮播图管理菜单'),

-- 分类管理 - 按钮权限
(2101, 2100, '分类新增', 3, NULL, NULL, 'toc:category:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '分类新增按钮'),
(2102, 2100, '分类编辑', 3, NULL, NULL, 'toc:category:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '分类编辑按钮'),
(2103, 2100, '分类删除', 3, NULL, NULL, 'toc:category:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '分类删除按钮'),

-- 菜谱管理 - 按钮权限
(2201, 2200, '菜谱新增', 3, NULL, NULL, 'toc:recipe:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '菜谱新增按钮'),
(2202, 2200, '菜谱编辑', 3, NULL, NULL, 'toc:recipe:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '菜谱编辑按钮'),
(2203, 2200, '菜谱删除', 3, NULL, NULL, 'toc:recipe:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '菜谱删除按钮'),

-- TOC用户管理 - 按钮权限
(2301, 2300, '用户编辑', 3, NULL, NULL, 'toc:user:edit', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, 'TOC用户编辑按钮'),
(2302, 2300, '用户禁用', 3, NULL, NULL, 'toc:user:disable', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, 'TOC用户禁用按钮'),

-- 评论管理 - 按钮权限
(2401, 2400, '评论删除', 3, NULL, NULL, 'toc:comment:delete', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '评论删除按钮'),
(2402, 2400, '评论审核', 3, NULL, NULL, 'toc:comment:audit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '评论审核按钮'),

-- 轮播图管理 - 按钮权限
(2501, 2500, '轮播图新增', 3, NULL, NULL, 'toc:banner:add', NULL, 1, 1, 1, 0, NOW(), NOW(), 1, 1, '轮播图新增按钮'),
(2502, 2500, '轮播图编辑', 3, NULL, NULL, 'toc:banner:edit', NULL, 2, 1, 1, 0, NOW(), NOW(), 1, 1, '轮播图编辑按钮'),
(2503, 2500, '轮播图删除', 3, NULL, NULL, 'toc:banner:delete', NULL, 3, 1, 1, 0, NOW(), NOW(), 1, 1, '轮播图删除按钮');

-- ==========================================
-- 说明
-- ==========================================
-- 菜单类型: 1=目录, 2=菜单, 3=按钮
-- 显示状态: 1=显示, 0=隐藏
-- 可用状态: 1=正常, 0=停用
-- 删除标记: 0=正常, 1=删除
-- ==========================================
