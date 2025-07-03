# WebSocket-Chat 实时聊天系统

基于WebSocket技术的实时聊天系统，支持用户注册、登录、实时消息发送、历史记录查询等功能。

## 项目介绍

WebSocket-Chat是一个基于Spring Boot和WebSocket技术开发的实时聊天应用，为用户提供即时通讯功能。系统支持用户注册、登录、好友列表显示、实时消息发送与接收、聊天记录保存等功能，同时提供管理员后台对用户和聊天记录进行管理。

## 功能特性

- **用户管理**：支持用户注册、登录功能
- **实时通讯**：基于WebSocket的即时消息发送与接收
- **在线状态**：显示用户在线状态，支持好友列表管理
- **消息存储**：自动保存聊天记录到数据库
- **表情支持**：内置多种表情，丰富聊天体验
- **未读消息提醒**：显示未读消息数量
- **管理员后台**：管理用户信息、查看聊天记录、统计消息数据

## 技术栈

- **后端**：
  - Spring Boot 2.x
  - WebSocket
  - MyBatis
  - MySQL

- **前端**：
  - HTML/CSS/JavaScript
  - Vue.js
  - Axios
  - Bootstrap

## 系统架构

系统主要由以下模块组成：
- 用户管理模块：负责用户注册、登录等功能
- WebSocket通信模块：处理实时消息的发送与接收
- 聊天记录模块：保存和查询历史聊天记录
- 管理员后台模块：提供系统管理功能

## 安装部署

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE websocket_chat;
```

2. 执行SQL脚本：
```bash
mysql -u username -p websocket_chat < src/sql/user.sql
mysql -u username -p websocket_chat < src/sql/chat_record.sql
mysql -u username -p websocket_chat < src/sql/admin.sql
```

3. 修改数据库配置（application.properties）：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/websocket_chat?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 编译运行

1. 克隆项目：
```bash
git clone https://github.com/yourusername/websocket-chat.git
cd websocket-chat
```

2. 编译打包：
```bash
mvn clean package
```

3. 运行应用：
```bash
java -jar target/lxx_chat-0.0.1-SNAPSHOT.jar
```

4. 访问应用：
浏览器访问 http://localhost:80

## 使用说明

1. 注册账号：访问首页，点击"立即注册"，填写用户名和密码
2. 登录系统：使用注册的账号密码登录
3. 聊天功能：
   - 右侧显示在线用户列表
   - 点击用户名开始聊天
   - 在输入框中输入消息，点击发送或按Enter键发送
   - 支持发送表情
4. 管理员功能：
   - 访问 http://localhost:80/admin_login.html 进入管理员登录页面
   - 登录后可查看用户列表、聊天记录、统计数据等

## 项目结构

```
websocket-chat/
  ├── src/
  │   ├── main/
  │   │   ├── java/com/lxx/
  │   │   │   ├── config/         # 配置类
  │   │   │   ├── controller/     # 控制器
  │   │   │   ├── mapper/         # MyBatis映射接口
  │   │   │   ├── pojo/           # 实体类
  │   │   │   ├── service/        # 服务接口及实现
  │   │   │   ├── utils/          # 工具类
  │   │   │   └── ws/             # WebSocket相关类
  │   │   └── resources/
  │   │       ├── mapper/         # MyBatis映射XML
  │   │       └── static/         # 前端资源
  │   └── sql/                    # 数据库脚本
  └── pom.xml                     # Maven配置
```

## 贡献指南

欢迎提交问题和功能需求，也欢迎提交Pull Request贡献代码。

## 许可证

本项目采用MIT许可证，详情请参阅LICENSE文件。 