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

COMMENT ON TABLE toc.toc_user IS 'C端用户表 - 已插入5个测试账号';
