-- ==========================================================
-- ToC 测试用户数据
-- 密码统一为: 123456 (BCrypt 加密)
-- BCrypt Hash: $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
-- ==========================================================

-- 测试用户 1: 普通用户
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000001,
    'testuser01',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '测试用户01',
    'https://picsum.photos/seed/user01/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 2: 美食达人
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000002,
    'testuser02',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '美食达人',
    'https://picsum.photos/seed/user02/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 3: 厨房新手
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000003,
    'testuser03',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '厨房新手',
    'https://picsum.photos/seed/user03/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 4: 烘焙爱好者
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000004,
    'testuser04',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '烘焙爱好者',
    'https://picsum.photos/seed/user04/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 5: 素食主义者
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000005,
    'testuser05',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '素食主义者',
    'https://picsum.photos/seed/user05/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 6: 川菜大师
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, signature, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000006,
    'testuser06',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '川菜大师',
    'https://picsum.photos/seed/user06/200',
    '无辣不欢，川菜是我的信仰！',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 7: 甜品控
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, signature, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000007,
    'testuser07',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '甜品控',
    'https://picsum.photos/seed/user07/200',
    '生活已经很苦了，甜品必须甜！',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 8: 健身达人
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, signature, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000008,
    'testuser08',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '健身达人',
    'https://picsum.photos/seed/user08/200',
    '三分练，七分吃，健康饮食最重要',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 9: 家常菜爱好者
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000009,
    'testuser09',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '家常菜爱好者',
    'https://picsum.photos/seed/user09/200',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 测试用户 10: 异国料理探索者
INSERT INTO toc.toc_user (user_id, username, password, nickname, avatar, signature, deleted, create_by, create_time, update_by, update_time)
VALUES (
    1000000000000000010,
    'testuser10',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '异国料理探索者',
    'https://picsum.photos/seed/user10/200',
    '世界那么大，美食那么多，都想尝尝！',
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

COMMENT ON TABLE toc.toc_user IS 'C端用户表 - 已插入10个测试账号';
