# QQ 机器人后端项目 qq-bot

这是一个基于 Spring Boot 和 Simbot 框架开发的 QQ 机器人后端项目。通过 NapCat 作为中间件与 QQ 机器人进行交互，同时提供独立的 API 接口，并集成了用户认证与授权功能。

---

## ✨ 技术栈

- **核心框架**:
  - **Spring Boot**: 快速开发、简化配置。
  - **Simbot**: QQ 机器人开发框架。
- **中间件**:
  - **NapCat**: 连接 QQ 机器人与后端服务的中间件。
- **数据存储**:
  - **MySQL**: 关系型数据库，用于持久化存储数据。
  - **Redis**: 内存数据库，主要用于缓存。
- **ORM 框架**:
  - **MyBatis-Plus**: 常用的 ORM 框架，简化 CRUD 操作。
- **安全框架**:
  - **Spring Security**: 提供认证和授权功能。
  - **JWT**: 用于无状态认证和授权。

---

## 📂 项目结构

项目主要的两个核心入口：

1.  **`controller` (API 接口)**:

    - 提供 RESTful API 接口。
    - 这部分功能通过 **Spring Security** 进行保护，使用 **JWT** 进行认证和授权。
    - 与数据库进行交互，处理业务逻辑。

2.  **`listener` (QQ 消息监听)**:
    - 负责监听 QQ 消息，并根据消息内容触发相应的机器人功能。
    - 通过 **Simbot** 框架实现消息的接收、解析和响应。
    - 与数据库进行交互，处理业务逻辑。

---

## 💡 功能模块

### 开发中...

---

## 🌐 外部 API

目前项目集成了以下外部 API 以增强机器人功能：

- **Codeforces API**:
  - **URL**: `https://codeforces.com/api`
  - **用途**: 获取 Codeforces 平台的比赛信息、用户数据等。
- **Lolicon API**:
  - **URL**: `https://api.lolicon.app/setu`
  - **用途**: 获取随机图片。

---

## 🛠️ 部署

1.  **环境准备**:

    - JDK 17+
    - Maven
    - MySQL 数据库
    - Redis 数据库
    - NapCat 客户端 (或兼容 Simbot 的 QQ 机器人客户端)

2.  **数据库配置**:

    - 创建 MySQL 数据库。
    - 在 `src/main/resources/application.yml` 中配置数据库连接信息。

3.  **Redis 配置**:

    - 在 `src/main/resources/application.yml` 中配置 Redis 连接信息。

4.  **NapCat 配置**:

    - 确保 NapCat 客户端已正确安装并运行。
    - 在 `simbot-bot/robot.bot.json` 中配置 Simbot 与 NapCat 的连接信息（可配置多个机器人）。

---

## 🚀 快速开始

1.  **配置 `application.yml`**:

    - 根据你的环境修改 `src/main/resources/application.yml` 中的 MySQL、Redis 等配置。

    ```yaml
    # 服务器配置
    server:
      port: 8080 # 应用服务监听的端口号
      address: 0.0.0.0 # 应用服务绑定的IP地址，0.0.0.0表示监听所有可用网络接口
      servlet:
        context-path: /api # 应用的上下文路径，所有API请求都将以此路径开头

    # Spring 框架核心配置
    spring:
      profiles:
        active: dev # 当前激活的Spring配置文件（例如：dev, prod），用于区分不同环境的配置
      mail:
        host: smtp.qq.com # 邮件服务器主机地址
        port: 587 # 邮件服务器端口号
        username: # 发送邮件的QQ邮箱地址，例如：1234567890@qq.com
        password: # QQ邮箱的授权码，而非登录密码
        protocol: smtp # 邮件传输协议
        properties:
          mail:
            smtp:
              auth: true # 启用SMTP认证
              starttls:
                enable: true # 启用STARTTLS加密，用于安全连接
      servlet:
        multipart:
          max-file-size: 2MB # 单个文件上传的最大大小
          max-request-size: 5MB # 单次HTTP请求的最大大小（包含所有文件和表单数据）
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver # 数据库驱动类名
        url: # MySQL数据库连接URL，例如：jdbc:mysql://127.0.0.1:3306/qq_robot?characterEncoding=utf8&serverTimezone=Asia/Shanghai
        username: # MySQL数据库用户名，例如：root
        password: # MySQL数据库用户密码，例如：123456
      cache:
        type: redis # 缓存类型，此处配置为Redis
      data:
        redis:
          host: # Redis服务器主机地址，例如：127.0.0.1
          port: 6379 # Redis服务器端口号
          password: # Redis服务器连接密码（如果设置了密码），例如：123456
      jackson:
        time-zone: Asia/Shanghai # JSON序列化/反序列化时使用的时区
        date-time-format: yyyy-MM-dd HH:mm:ss # 日期时间格式化模式
        date-format: yyyy-MM-dd # 日期格式化模式
        time-format: HH:mm:ss # 时间格式化模式
      security:
        jwt:
          key: # JWT签名密钥，用于生成和验证JWT，请替换为足够长且安全的随机字符串，例如：c3VwZXJTZWNyZXRLZXlGb3JKV1RhdXRoZW50aWNhdGlvblRoaXNJc0FSYW5kb21TdHJpbmc=
          expire: 86400000 # JWT过期时间，单位毫秒（86400000毫秒 = 24小时）
          issuer: admin # JWT的签发者
          jwt-algorithm: HS256 # JWT使用的签名算法，例如HS256
          jca-algorithm: HmacSHA256 # JCA（Java Cryptography Architecture）中对应的算法名称

    # 自定义配置
    custom:
      redis:
        redisson-address: # Redisson连接Redis的地址，例如：redis://127.0.0.1:6379
        redisson-database: 0 # Redisson连接的Redis数据库索引

    # MyBatis-Plus 配置
    mybatis-plus:
      configuration:
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # MyBatis日志实现，输出到控制台
      global-config:
        db-config:
          logic-delete-field: isDeleted # 逻辑删除字段名
          logic-delete-value: true # 逻辑删除字段的已删除值
          logic-not-delete-value: false # 逻辑删除字段的未删除值
          id-type: auto # 主键ID生成策略，例如：AUTO（数据库自增）
      mapper-locations: classpath*:/mapper/**/*.xml # MyBatis Mapper XML文件位置

    # 外部API基础URL配置
    base-url:
      codeforces: https://codeforces.com/api # Codeforces API基础URL
      lolicon: https://api.lolicon.app/setu # Lolicon API基础URL
    ```

    - 根据你的环境修改 `src/main/resources/simbot-robot/robot.json` 中的 simbot 配置。

    ```json
    {
      "component": "simbot.onebot11", // 固定值
      "authorization": {
        "botUniqueId": "1234567890", // 唯一ID，作为组件内 Bot 的 id，用于组件内去重。可以随便编，但建议是bot的qq号
        "apiServerHost": "http://127.0.0.1:3000", // api地址，是个http/https服务器的路径，默认127.0.0.1:3000，如果使用napcat请在napcat的网络配置中查看
        "eventServerHost": "ws://127.0.0.1:3001", // 订阅事件的服务器地址，是个ws/wss路径，默认 `null`，如果使用napcat请在napcat的网络配置中查看
        "accessToken": null // 配置的 token，可以是null, 代表同时配置 apiAccessToken 和 eventAccessToken
      }
    }
    ```

    > tips: JSON 配置文件不允许使用注释，这里只是方便展示，详见[simbot 官方文档](https://simbot.forte.love/component-onebot-v11-bot-config.html)查看配置。

2.  **启动 NapCat (或你的 QQ 机器人客户端)**:

    - 确保 QQ 机器人客户端已登录并连接到 NapCat。

---

## 🔐 认证与授权

本项目使用 **Spring Security** 结合 **JWT** 实现认证和授权：

- **登录**: 用户通过用户名和密码登录，成功后后端生成一个 JWT 并返回给客户端。
- **访问受保护资源**: 客户端在后续请求中将 JWT 放入 `Authorization` 请求头（通常是 `Bearer <token>` 格式）。
- **JWT 验证**: Spring Security 会拦截请求，验证 JWT 的有效性，并根据 JWT 中的信息进行用户身份认证和权限检查。

---

## 🤝 贡献

欢迎对本项目进行贡献！如果你有任何建议或发现 Bug，请提交 Issue 或 Pull Request。
