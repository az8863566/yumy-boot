-- 插入 admin_user 测试账号
-- 密码统一为: admin123 (BCrypt加密)
-- BCrypt哈希值: $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW (对应密码: admin123)

INSERT INTO admin.sys_user (user_id, username, password, nickname, email, phone, status, create_time, update_time, create_by, update_by, remark)
VALUES 
    (1001, 'admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '超级管理员', 'admin@yumy.com', '13800138000', 1, NOW(), NOW(), 'system', 'system', '超级管理员账号'),
    (1002, 'test', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '测试账号', 'test@yumy.com', '13800138001', 1, NOW(), NOW(), 'system', 'system', '测试账号'),
    (1003, 'demo', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '演示账号', 'demo@yumy.com', '13800138002', 1, NOW(), NOW(), 'system', 'system', '演示账号'),
    (1004, 'operator', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', '运营人员', 'operator@yumy.com', '13800138003', 1, NOW(), NOW(), 'system', 'system', '运营账号');
