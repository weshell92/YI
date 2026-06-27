# YI 本地运行说明

## 项目结构
- `yijing-server`
  Spring Boot 后端，提供内容查询、起卦、AI 结构化解释和后台内容管理接口。
- `yijing-web`
  Vue 3 前端，包含首页、学习模块、八卦、六十四卦、案例库、在线起卦、AI 解卦、用户中心和后台管理页面。

## 后端启动
要求：
- `Java 17+`
- `Maven 3.9+`

如果本机 `mvn` 默认指向 Java 8，请先切到 Java 17 后再运行。

PowerShell 示例：

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
cd yijing-server
#mvn spring-boot:run
mvn spring-boot:run -s settings.local.xml

```

默认地址：
- `http://localhost:8080`

## 前端启动
要求：
- `Node.js`
- 一个可正常工作的包管理器环境

```powershell
cd yijing-web
npm install
npm run dev
```

默认地址：
- `http://localhost:5173`

## 已实现范围
- Phase 1 主体骨架
- 首页、学习页、八卦列表/详情
- 六十四卦列表/详情
- 案例库列表/详情
- 在线起卦与结果展示
- AI 解卦结构化输出
- 用户中心起卦记录与复盘提交
- 后台内容管理基础页

## 当前实现说明
- 为了让仓库在空白状态下先完成 MVP 闭环，后端当前使用内存内容仓库承载首批样例数据。
- `yijing-server/src/main/resources/db/schema.sql` 已提供 MySQL 结构草案，后续可替换成 MyBatis Plus + MySQL 持久化实现。
- 用户认证、JWT、Redis、真正的大模型接入和完整 CMS 流程仍属于后续阶段工作。
