
# 实体表
CREATE TABLE IF NOT EXISTS `user` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 用户ID

    `qq_email`                  VARCHAR(256) UNIQUE NOT NULL,                                                           -- 用户邮箱
    `username`                  VARCHAR(128) UNIQUE NOT NULL,                                                           -- 用户名(qq号)（唯一）
    `nickname`                  VARCHAR(128),                                                                           -- 昵称
    `password`                  VARCHAR(128),                                                                           -- 用户密码
    `avatar`                    VARCHAR(256),                                                                           -- 用户头像
    `gender`                    BOOLEAN,                                                                                    -- 用户性别（0女，1男）
    `introduction`              TEXT,                                                                                   -- 用户简介
    `last_login_time`           DATETIME,                                                                               -- 最后在线时间

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 更新时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 实体表
CREATE TABLE IF NOT EXISTS `role` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 角色ID

    `role_name`                 VARCHAR(128) UNIQUE NOT NULL,                                                           -- 角色名（如 USER / ADMIN / SYSTEM）
    `description`               VARCHAR(256),                                                                           -- 角色描述

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 更新时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `user_role` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,

    `user_id`                   BIGINT NOT NULL,                                                                        -- 用户ID
    `role_id`                   BIGINT NOT NULL,                                                                        -- 角色ID

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 实体表
CREATE TABLE IF NOT EXISTS `permission` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 菜单/权限ID

    `symbol`                    VARCHAR(128) UNIQUE NOT NULL,                                                           -- 权限标识（如 article:read、user:update）
    `description`               VARCHAR(256),                                                                           -- 菜单描述（例如：文章管理）
    `url`                       VARCHAR(512),                                                                           -- 接口url或前端路径
    `http_method`               VARCHAR(128),                                                                           -- HTTP方法（GET、POST、PUT、DELETE）
    `point`                     VARCHAR(128),                                                                           -- 类型：菜单 or 按钮（权限点）
    `order_num`                 INT,                                                                                    -- 排序值

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 更新时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `role_permission` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,

    `role_id`                   BIGINT NOT NULL,                                                                        -- 角色ID
    `permission_id`             BIGINT NOT NULL,                                                                        -- 权限ID

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 实体表
CREATE TABLE IF NOT EXISTS `picture` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 图片ID

    `pid`                       BIGINT UNIQUE NOT NULL,                                                                 -- 获取的图片ID
    `uid`                       BIGINT UNIQUE NOT NULL,                                                                 -- 获取的唯一ID
    `p`                         INT,                                                                                    -- 作品所在页
    `title`                     VARCHAR(256),                                                                           -- 标题
    `author`                    VARCHAR(256),                                                                           -- 作者
    `r18`                       BOOLEAN,                                                                                -- 是否r18
    `width`                     INT,                                                                                    -- 宽度px
    `height`                    INT,                                                                                    -- 高度px
    `ext`                       VARCHAR(256),                                                                           -- 作品扩展名
    `ai_type`                   INT,                                                                                    -- ai类型
    `upload_date`               BIGINT,                                                                                 -- 上传时间戳（毫秒）
    `original_url`              VARCHAR(512),                                                                           -- 图片url

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 更新时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `user_picture` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,

    `user_id`                   BIGINT NOT NULL,                                                                        -- 收藏者ID
    `picture_id`                BIGINT NOT NULL,                                                                        -- 收藏的图片ID

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 实体表
CREATE TABLE IF NOT EXISTS `tag` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 标签ID

    `tag_name`                  VARCHAR(128) UNIQUE NOT NULL,                                                           -- 标签名（唯一）

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 修改时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `picture_tag` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,

    `picture_id`                BIGINT NOT NULL,                                                                        -- 图片ID
    `tag_id`                    BIGINT NOT NULL,                                                                        -- 权限ID

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 实体表
CREATE TABLE IF NOT EXISTS `action` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 标签id

    `action_name`               VARCHAR(128) UNIQUE NOT NULL,                                                           -- 行为名称（唯一）

    `enabled`                   BOOLEAN DEFAULT true,                                                                   -- 是否可用
    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `update_time`               DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,                         -- 修改时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `qq_id_action` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,                                                      -- 主键

    `qq_id`                     VARCHAR(128) NOT NULL,                                                                  -- qqID
    `action_name`               VARCHAR(128) NOT NULL,                                                                  -- 行为名称

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



# 关系表
CREATE TABLE IF NOT EXISTS `qq_user_id_action` (
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,

    `qq_user_id`                VARCHAR(128) NOT NULL,                                                                  -- qq用户Id
    `action_name`               VARCHAR(128) NOT NULL,                                                                  -- 行为名称

    `create_time`               DATETIME DEFAULT CURRENT_TIMESTAMP,                                                     -- 创建时间
    `is_deleted`                BOOLEAN DEFAULT false                                                                   -- 是否被删除（软删）

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



