-- ==========================================================
-- ToC Schema - ToC端模块数据表（甄味 FlavorGuide 菜谱 App）
-- 适配项目规范：BIGINT 雪花ID、审计字段、PostgreSQL、toc_ 前缀
-- ==========================================================

-- ==========================================================
-- 1. C端用户表
-- ==========================================================
CREATE TABLE toc.toc_user (
    user_id     BIGINT       PRIMARY KEY,
    username    VARCHAR(64)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    nickname    VARCHAR(64),
    avatar      VARCHAR(500) DEFAULT 'https://picsum.photos/seed/default/200',
    deleted     INT2         DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_toc_user_username ON toc.toc_user(username) WHERE deleted = 0;
COMMENT ON TABLE toc.toc_user IS 'C端用户表';

-- ==========================================================
-- 2. 父分类表
-- ==========================================================
CREATE TABLE toc.toc_parent_category (
    category_id BIGINT      PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    sort_order  INT4        DEFAULT 0,
    deleted     INT2        DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE toc.toc_parent_category IS '父分类表';

-- ==========================================================
-- 3. 子分类表
-- ==========================================================
CREATE TABLE toc.toc_sub_category (
    category_id BIGINT       PRIMARY KEY,
    parent_id   BIGINT       NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    image       VARCHAR(500) NOT NULL,
    sort_order  INT4         DEFAULT 0,
    deleted     INT2         DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_sub_category_parent ON toc.toc_sub_category(parent_id);
COMMENT ON TABLE toc.toc_sub_category IS '子分类表';

-- ==========================================================
-- 4. 菜谱表
-- ==========================================================
CREATE TABLE toc.toc_recipe (
    recipe_id   BIGINT       PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    image       VARCHAR(500) NOT NULL,
    category_id BIGINT       NOT NULL,
    likes       INT4         DEFAULT 0,
    difficulty  VARCHAR(10)  NOT NULL CHECK (difficulty IN ('简单', '中等', '困难')),
    time        VARCHAR(20)  NOT NULL,
    servings    INT4         DEFAULT 1,
    recommend_sort INT4     DEFAULT 0,                     -- 推荐排序(越小越靠前,0不参与推荐)
    deleted     INT2         DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_recipe_category ON toc.toc_recipe(category_id);
CREATE INDEX idx_toc_recipe_likes ON toc.toc_recipe(likes DESC);
COMMENT ON TABLE toc.toc_recipe IS '菜谱表';

-- ==========================================================
-- 5. 菜谱食材表
-- ==========================================================
CREATE TABLE toc.toc_recipe_ingredient (
    ingredient_id BIGINT       PRIMARY KEY,
    recipe_id     BIGINT       NOT NULL,
    name          VARCHAR(50)  NOT NULL,
    amount        VARCHAR(50)  NOT NULL,
    sort_order    INT4         DEFAULT 0,
    deleted       INT2         DEFAULT 0,
    create_by     BIGINT,
    create_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by     BIGINT,
    update_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_ingredient_recipe ON toc.toc_recipe_ingredient(recipe_id);
COMMENT ON TABLE toc.toc_recipe_ingredient IS '菜谱食材表';

-- ==========================================================
-- 6. 菜谱步骤表
-- ==========================================================
CREATE TABLE toc.toc_recipe_step (
    step_id         BIGINT       PRIMARY KEY,
    recipe_id       BIGINT       NOT NULL,
    step_number     INT4         NOT NULL,
    description     TEXT         NOT NULL,
    image           VARCHAR(500),
    ingredients_used JSONB       DEFAULT NULL,
    deleted         INT2         DEFAULT 0,
    create_by       BIGINT,
    create_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by       BIGINT,
    update_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_step_recipe ON toc.toc_recipe_step(recipe_id);
COMMENT ON TABLE toc.toc_recipe_step IS '菜谱步骤表';

-- ==========================================================
-- 7. 评论表
-- ==========================================================
CREATE TABLE toc.toc_comment (
    comment_id  BIGINT       PRIMARY KEY,
    recipe_id   BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    text        TEXT         NOT NULL,
    images      JSONB        DEFAULT NULL,
    deleted     INT2         DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_comment_recipe ON toc.toc_comment(recipe_id);
CREATE INDEX idx_toc_comment_user ON toc.toc_comment(user_id);
COMMENT ON TABLE toc.toc_comment IS '评论表';

-- ==========================================================
-- 8. 用户点赞表
-- ==========================================================
CREATE TABLE toc.toc_user_like (
    like_id     BIGINT    PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    recipe_id   BIGINT    NOT NULL,
    deleted     INT2      DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_toc_user_like ON toc.toc_user_like(user_id, recipe_id) WHERE deleted = 0;
CREATE INDEX idx_toc_user_like_user ON toc.toc_user_like(user_id);
COMMENT ON TABLE toc.toc_user_like IS '用户点赞表';

-- ==========================================================
-- 9. 用户收藏表
-- ==========================================================
CREATE TABLE toc.toc_user_favorite (
    favorite_id BIGINT    PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    recipe_id   BIGINT    NOT NULL,
    deleted     INT2      DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_toc_user_favorite ON toc.toc_user_favorite(user_id, recipe_id) WHERE deleted = 0;
CREATE INDEX idx_toc_user_favorite_user ON toc.toc_user_favorite(user_id);
COMMENT ON TABLE toc.toc_user_favorite IS '用户收藏表';

-- ==========================================================
-- 10. 首页轮播图表
-- ==========================================================
CREATE TABLE toc.toc_banner (
    banner_id   BIGINT       PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,                  -- 轮播图标题
    subtitle    VARCHAR(200),                          -- 副标题/描述
    image       VARCHAR(500) NOT NULL,                  -- 轮播图图片URL
    link_type   INT2         DEFAULT 0,                 -- 跳转类型(0无跳转 1菜谱详情 2外部链接)
    link_value  VARCHAR(500),                          -- 跳转目标(菜谱ID或URL)
    sort_order  INT4         DEFAULT 0,                 -- 排序顺序(越小越靠前)
    status      INT2         DEFAULT 1,                 -- 状态(1启用 0停用)
    deleted     INT2         DEFAULT 0,
    create_by   BIGINT,
    create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    update_by   BIGINT,
    update_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_toc_banner_status ON toc.toc_banner(status);
COMMENT ON TABLE toc.toc_banner IS '首页轮播图表';

-- ==========================================================
-- 初始化数据：父分类
-- ==========================================================
INSERT INTO toc.toc_parent_category (category_id, name, sort_order) VALUES
(1, '热门分类', 1),
(2, '菜系', 2),
(3, '烘焙甜品', 3);

-- ==========================================================
-- 初始化数据：子分类
-- ==========================================================
INSERT INTO toc.toc_sub_category (category_id, parent_id, name, image, sort_order) VALUES
(101, 1, '家常菜', 'https://picsum.photos/seed/jiachangcai/200', 1),
(102, 1, '下饭菜', 'https://picsum.photos/seed/xiafancai/200', 2),
(103, 1, '蔬菜',   'https://picsum.photos/seed/shucai/200', 3),
(104, 1, '肉类',   'https://picsum.photos/seed/roulei/200', 4),
(105, 1, '减脂餐', 'https://picsum.photos/seed/jianzhican/200', 5),
(106, 2, '川菜',   'https://picsum.photos/seed/chuancai/200', 1),
(107, 2, '粤菜',   'https://picsum.photos/seed/yuecai/200', 2),
(108, 2, '西式简餐', 'https://picsum.photos/seed/xishijiancan/200', 3),
(109, 3, '蛋糕',   'https://picsum.photos/seed/dangao/200', 1),
(110, 3, '饮品',   'https://picsum.photos/seed/yinpin/200', 2);
