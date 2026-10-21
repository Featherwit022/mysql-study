# MySQL JDBC 示例项目

一个用于学习 Java JDBC 数据库开发的练习项目，包含基础 JDBC 操作演示和一套登录/注册的三层架构示例。

## 功能模块

### test — 基础 JDBC 演示

- `JdbcTest.java`：使用 `DriverManager` 直连数据库，通过 `PreparedStatement` 查询 `score` 表中高于指定分数的记录，并映射为 `Score` 对象输出。
- 演示内容：驱动加载、连接建立、参数化查询、`ResultSet` 逐行读取。

### jdbc — 登录注册三层架构

采用 Controller → Service → DAO 分层结构：

| 层 | 类 | 职责 |
| --- | --- | --- |
| 启动层 | `start.Launcher` | 从控制台读取用户名密码，发起登录 |
| 控制层 | `controller.UserController` | 接收请求，转发给 Service |
| 服务层 | `service.impl.UserServerImpl` | 登录校验、注册查重等业务逻辑 |
| 数据层 | `dao.impl.UserDaoImpl` | 拼接 SQL，调用通用工具执行增查 |
| 工具层 | `util.JdbcUtil` | 封装连接管理、通用更新与基于反射的通用查询映射 |
| 实体 | `entity.User` | 用户数据载体 |

## 环境要求

- JDK 8+
- MySQL 5.7+
- 驱动：mysql-connector-java 8.0.26
- IDE：IntelliJ IDEA（项目含 `.iml` 模块配置）

## 数据库初始化

创建库表（`id` 建议设为自增主键，否则插入会因无默认值报错）：

```sql
CREATE DATABASE study DEFAULT CHARSET utf8mb4;
USE study;

CREATE TABLE `user` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE score (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    course VARCHAR(30) NOT NULL,
    score DOUBLE NOT NULL
);
```

数据库连接参数（地址、账号、密码）硬编码在 `JdbcUtil.java` 与 `JdbcTest.java` 中，运行前请改为本地实际配置。

## 运行方式

- 登录示例：运行 `com.example.project.start.Launcher` 的 `main` 方法，按提示输入用户名和密码。
- 分数查询：运行 `JdbcTest` 的 `main` 方法，输入分数线查看结果。

## 学习笔记要点

1. **SQL 注入与防御**：提交历史中先演示了 `Statement` 字符串拼接被注入，再改为 `PreparedStatement` 参数化查询修复。
2. **反射通用映射**：`JdbcUtil.query` 通过 `ResultSetMetaData` 获取列名，反射调用实体 setter 完成结果集到对象的自动转换。
3. **分层解耦**：Controller 不感知 SQL，DAO 不感知业务，通过接口（`UserServer`、`UserDao`）隔离实现。

## 已知问题

- `JdbcUtil` 通用映射使用 `getField` 读取实体字段，对 `private` 字段无效，会导致查询结果映射为空，登录校验恒失败。
- `JdbcUtil.update` 未关闭连接与语句，存在资源泄漏。
- 密码为明文存储，仅作练习，生产环境需哈希处理。
