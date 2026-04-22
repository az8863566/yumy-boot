-- ==========================================================
-- Schema 初始化脚本
-- 创建 admin 和 toc 两个独立的 schema 实现表隔离
-- ==========================================================

-- 创建 admin schema（后台管理模块）
CREATE SCHEMA IF NOT EXISTS admin;
COMMENT ON SCHEMA admin IS '后台管理模块数据';

-- 创建 toc schema（ToC端模块）
CREATE SCHEMA IF NOT EXISTS toc;
COMMENT ON SCHEMA toc IS 'ToC端模块数据';

-- 设置默认 search_path（按优先级排序）
-- 当访问表时，会依次在 admin -> toc -> public 中查找
ALTER DATABASE "yumy-boot" SET search_path TO admin, toc, public;
