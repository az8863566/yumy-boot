-- ==========================================================
-- 修复 toc_user 密码
-- 问题: password 字段存储了 $2a$2a$10$... (BCrypt前缀重复)，导致登录失败
-- 修复: 更新为正确的 BCrypt 哈希值
-- 密码: 123456
-- 正确哈希: $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
-- ==========================================================

UPDATE toc.toc_user
SET password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG';
