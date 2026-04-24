-- ==========================================================
-- ToC 家常菜分类测试菜谱数据
-- 分类ID: 101 (家常菜)
-- 包含: 菜谱主表、食材表、步骤表
-- ==========================================================

-- ==========================================================
-- 菜谱 1: 番茄炒蛋
-- ==========================================================
INSERT INTO toc.toc_recipe (recipe_id, title, description, image, category_id, likes, difficulty, time, servings, recommend_sort, deleted, create_by, create_time, update_by, update_time)
VALUES (
    2000000000000000001,
    '番茄炒蛋',
    '经典家常菜，酸甜可口，营养丰富，新手也能轻松搞定！',
    'https://picsum.photos/seed/fanqiechaodan/400',
    101,
    1258,
    '简单',
    '15分钟',
    2,
    1,
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 番茄炒蛋 - 食材
INSERT INTO toc.toc_recipe_ingredient (ingredient_id, recipe_id, name, amount, sort_order, deleted, create_by, create_time, update_by, update_time)
VALUES 
(3000000000000000001, 2000000000000000001, '番茄', '2个', 1, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000002, 2000000000000000001, '鸡蛋', '3个', 2, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000003, 2000000000000000001, '白糖', '1勺', 3, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000004, 2000000000000000001, '盐', '适量', 4, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000005, 2000000000000000001, '葱花', '少许', 5, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- 番茄炒蛋 - 步骤
INSERT INTO toc.toc_recipe_step (step_id, recipe_id, step_number, description, image, ingredients_used, deleted, create_by, create_time, update_by, update_time)
VALUES 
(4000000000000000001, 2000000000000000001, 1, '番茄洗净切块，鸡蛋打入碗中搅匀备用', 'https://picsum.photos/seed/step1-1/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000002, 2000000000000000001, 2, '锅中倒油烧热，倒入蛋液炒至凝固盛出', 'https://picsum.photos/seed/step1-2/300', '[{"name":"鸡蛋","amount":"3个"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000003, 2000000000000000001, 3, '锅中再加少许油，放入番茄块翻炒出汁', 'https://picsum.photos/seed/step1-3/300', '[{"name":"番茄","amount":"2个"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000004, 2000000000000000001, 4, '加入白糖、盐调味，倒入炒好的鸡蛋翻炒均匀', 'https://picsum.photos/seed/step1-4/300', '[{"name":"白糖","amount":"1勺"},{"name":"盐","amount":"适量"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000005, 2000000000000000001, 5, '撒上葱花即可出锅装盘', 'https://picsum.photos/seed/step1-5/300', '[{"name":"葱花","amount":"少许"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- ==========================================================
-- 菜谱 2: 麻婆豆腐
-- ==========================================================
INSERT INTO toc.toc_recipe (recipe_id, title, description, image, category_id, likes, difficulty, time, servings, recommend_sort, deleted, create_by, create_time, update_by, update_time)
VALUES (
    2000000000000000002,
    '麻婆豆腐',
    '麻辣鲜香，豆腐嫩滑，超级下饭的经典川菜家常菜！',
    'https://picsum.photos/seed/mapodoufu/400',
    101,
    2156,
    '中等',
    '20分钟',
    3,
    2,
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 麻婆豆腐 - 食材
INSERT INTO toc.toc_recipe_ingredient (ingredient_id, recipe_id, name, amount, sort_order, deleted, create_by, create_time, update_by, update_time)
VALUES 
(3000000000000000011, 2000000000000000002, '嫩豆腐', '1盒(约400g)', 1, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000012, 2000000000000000002, '猪肉末', '100g', 2, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000013, 2000000000000000002, '郫县豆瓣酱', '2勺', 3, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000014, 2000000000000000002, '花椒粉', '1勺', 4, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000015, 2000000000000000002, '蒜末', '适量', 5, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000016, 2000000000000000002, '姜末', '适量', 6, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000017, 2000000000000000002, '水淀粉', '适量', 7, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- 麻婆豆腐 - 步骤
INSERT INTO toc.toc_recipe_step (step_id, recipe_id, step_number, description, image, ingredients_used, deleted, create_by, create_time, update_by, update_time)
VALUES 
(4000000000000000011, 2000000000000000002, 1, '豆腐切成小块，放入开水中焯烫1分钟捞出沥干', 'https://picsum.photos/seed/step2-1/300', '[{"name":"嫩豆腐","amount":"1盒"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000012, 2000000000000000002, 2, '锅中倒油烧热，放入肉末炒至变色', 'https://picsum.photos/seed/step2-2/300', '[{"name":"猪肉末","amount":"100g"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000013, 2000000000000000002, 3, '加入郫县豆瓣酱、蒜末、姜末炒出红油', 'https://picsum.photos/seed/step2-3/300', '[{"name":"郫县豆瓣酱","amount":"2勺"},{"name":"蒜末","amount":"适量"},{"name":"姜末","amount":"适量"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000014, 2000000000000000002, 4, '加入适量清水煮开，放入豆腐块小火煮3分钟', 'https://picsum.photos/seed/step2-4/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000015, 2000000000000000002, 5, '淋入水淀粉勾芡，轻轻翻炒均匀', 'https://picsum.photos/seed/step2-5/300', '[{"name":"水淀粉","amount":"适量"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000016, 2000000000000000002, 6, '装盘后撒上花椒粉即可', 'https://picsum.photos/seed/step2-6/300', '[{"name":"花椒粉","amount":"1勺"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- ==========================================================
-- 菜谱 3: 红烧肉
-- ==========================================================
INSERT INTO toc.toc_recipe (recipe_id, title, description, image, category_id, likes, difficulty, time, servings, recommend_sort, deleted, create_by, create_time, update_by, update_time)
VALUES (
    2000000000000000003,
    '红烧肉',
    '色泽红亮，肥而不腻，入口即化，家常宴客必备硬菜！',
    'https://picsum.photos/seed/hongshaorou/400',
    101,
    3542,
    '困难',
    '90分钟',
    4,
    3,
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 红烧肉 - 食材
INSERT INTO toc.toc_recipe_ingredient (ingredient_id, recipe_id, name, amount, sort_order, deleted, create_by, create_time, update_by, update_time)
VALUES 
(3000000000000000021, 2000000000000000003, '五花肉', '500g', 1, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000022, 2000000000000000003, '冰糖', '30g', 2, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000023, 2000000000000000003, '生抽', '2勺', 3, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000024, 2000000000000000003, '老抽', '1勺', 4, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000025, 2000000000000000003, '料酒', '2勺', 5, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000026, 2000000000000000003, '葱姜', '适量', 6, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000027, 2000000000000000003, '八角', '2个', 7, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000028, 2000000000000000003, '桂皮', '1小块', 8, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- 红烧肉 - 步骤
INSERT INTO toc.toc_recipe_step (step_id, recipe_id, step_number, description, image, ingredients_used, deleted, create_by, create_time, update_by, update_time)
VALUES 
(4000000000000000021, 2000000000000000003, 1, '五花肉切块，冷水下锅焯水去血沫，捞出洗净沥干', 'https://picsum.photos/seed/step3-1/300', '[{"name":"五花肉","amount":"500g"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000022, 2000000000000000003, 2, '锅中不放油，放入冰糖小火炒至焦糖色', 'https://picsum.photos/seed/step3-2/300', '[{"name":"冰糖","amount":"30g"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000023, 2000000000000000003, 3, '倒入肉块快速翻炒上色', 'https://picsum.photos/seed/step3-3/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000024, 2000000000000000003, 4, '加入葱姜、八角、桂皮炒香，倒入料酒、生抽、老抽', 'https://picsum.photos/seed/step3-4/300', '[{"name":"葱姜","amount":"适量"},{"name":"八角","amount":"2个"},{"name":"桂皮","amount":"1小块"},{"name":"料酒","amount":"2勺"},{"name":"生抽","amount":"2勺"},{"name":"老抽","amount":"1勺"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000025, 2000000000000000003, 5, '加入开水没过肉块，大火烧开转小火炖60分钟', 'https://picsum.photos/seed/step3-5/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000026, 2000000000000000003, 6, '大火收汁至浓稠即可出锅', 'https://picsum.photos/seed/step3-6/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- ==========================================================
-- 菜谱 4: 青椒肉丝
-- ==========================================================
INSERT INTO toc.toc_recipe (recipe_id, title, description, image, category_id, likes, difficulty, time, servings, recommend_sort, deleted, create_by, create_time, update_by, update_time)
VALUES (
    2000000000000000004,
    '青椒肉丝',
    '青椒脆嫩，肉丝鲜香，简单快手的家常下饭菜！',
    'https://picsum.photos/seed/qingjiaorousi/400',
    101,
    986,
    '简单',
    '15分钟',
    2,
    4,
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 青椒肉丝 - 食材
INSERT INTO toc.toc_recipe_ingredient (ingredient_id, recipe_id, name, amount, sort_order, deleted, create_by, create_time, update_by, update_time)
VALUES 
(3000000000000000031, 2000000000000000004, '猪里脊肉', '200g', 1, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000032, 2000000000000000004, '青椒', '3个', 2, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000033, 2000000000000000004, '生抽', '1勺', 3, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000034, 2000000000000000004, '淀粉', '1勺', 4, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000035, 2000000000000000004, '盐', '适量', 5, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000036, 2000000000000000004, '蒜片', '少许', 6, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- 青椒肉丝 - 步骤
INSERT INTO toc.toc_recipe_step (step_id, recipe_id, step_number, description, image, ingredients_used, deleted, create_by, create_time, update_by, update_time)
VALUES 
(4000000000000000031, 2000000000000000004, 1, '猪肉切丝，加入生抽、淀粉抓匀腌制10分钟', 'https://picsum.photos/seed/step4-1/300', '[{"name":"猪里脊肉","amount":"200g"},{"name":"生抽","amount":"1勺"},{"name":"淀粉","amount":"1勺"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000032, 2000000000000000004, 2, '青椒洗净去籽切丝备用', 'https://picsum.photos/seed/step4-2/300', '[{"name":"青椒","amount":"3个"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000033, 2000000000000000004, 3, '锅中倒油烧热，放入肉丝滑炒至变色盛出', 'https://picsum.photos/seed/step4-3/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000034, 2000000000000000004, 4, '锅中留底油，放入蒜片爆香，加入青椒丝翻炒', 'https://picsum.photos/seed/step4-4/300', '[{"name":"蒜片","amount":"少许"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000035, 2000000000000000004, 5, '倒入肉丝，加盐调味，大火翻炒均匀即可出锅', 'https://picsum.photos/seed/step4-5/300', '[{"name":"盐","amount":"适量"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- ==========================================================
-- 菜谱 5: 蒜蓉西兰花
-- ==========================================================
INSERT INTO toc.toc_recipe (recipe_id, title, description, image, category_id, likes, difficulty, time, servings, recommend_sort, deleted, create_by, create_time, update_by, update_time)
VALUES (
    2000000000000000005,
    '蒜蓉西兰花',
    '清脆爽口，营养丰富的健康素菜，减脂期必备！',
    'https://picsum.photos/seed/suanrongxilanHua/400',
    101,
    756,
    '简单',
    '10分钟',
    2,
    5,
    0,
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    CURRENT_TIMESTAMP
);

-- 蒜蓉西兰花 - 食材
INSERT INTO toc.toc_recipe_ingredient (ingredient_id, recipe_id, name, amount, sort_order, deleted, create_by, create_time, update_by, update_time)
VALUES 
(3000000000000000041, 2000000000000000005, '西兰花', '1颗', 1, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000042, 2000000000000000005, '大蒜', '5瓣', 2, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000043, 2000000000000000005, '盐', '适量', 3, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(3000000000000000044, 2000000000000000005, '蚝油', '1勺', 4, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- 蒜蓉西兰花 - 步骤
INSERT INTO toc.toc_recipe_step (step_id, recipe_id, step_number, description, image, ingredients_used, deleted, create_by, create_time, update_by, update_time)
VALUES 
(4000000000000000041, 2000000000000000005, 1, '西兰花切小朵，洗净后放入开水中焯烫2分钟捞出沥干', 'https://picsum.photos/seed/step5-1/300', '[{"name":"西兰花","amount":"1颗"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000042, 2000000000000000005, 2, '大蒜切成蒜蓉备用', 'https://picsum.photos/seed/step5-2/300', '[{"name":"大蒜","amount":"5瓣"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000043, 2000000000000000005, 3, '锅中倒油烧热，放入蒜蓉爆香', 'https://picsum.photos/seed/step5-3/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000044, 2000000000000000005, 4, '倒入西兰花快速翻炒，加入盐和蚝油调味', 'https://picsum.photos/seed/step5-4/300', '[{"name":"盐","amount":"适量"},{"name":"蚝油","amount":"1勺"}]', 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
(4000000000000000045, 2000000000000000005, 5, '翻炒均匀即可出锅', 'https://picsum.photos/seed/step5-5/300', NULL, 0, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

COMMENT ON TABLE toc.toc_recipe IS '菜谱表 - 家常菜分类已插入5道测试菜谱';
