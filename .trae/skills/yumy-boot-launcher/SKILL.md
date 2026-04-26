---
name: yumy-boot-launcher
description: >
  专门用于 yumy-boot 项目的启动和管理。当用户提到"启动项目"、"运行项目"、"run project"、"start app"等但未明确指定环境时，
  主动提醒用户选择 dev、local 或 prod 环境。检测到 yumy-boot 项目根目录后，调用 .script/run.ps1 脚本执行启动。
  适用于需要启动、运行 yumy-boot Spring Boot 项目的场景。
---

# Yumy-Boot Launcher

专门用于 yumy-boot 项目的启动管理技能。

## 触发条件

当用户表达以下意图时自动触发：
- "启动项目" / "运行项目" / "跑一下项目"
- "start the project" / "run the app" / "launch"
- "dev 环境启动" / "用 local 跑" / "prod 环境"
- 任何与 yumy-boot 项目启动相关的表达

## 使用流程

### 1. 确认项目位置

首先确认当前工作目录是否在 yumy-boot 项目中：

```bash
# 检查是否存在关键文件
ls yumy-bootstrap/src/main/resources/application.yml
ls .script/run.ps1
```

**如果不在 yumy-boot 项目中**：
- 告知用户此技能仅适用于 yumy-boot 项目
- 建议用户切换到项目根目录：`cd d:\workSpace\yumy-boot`

### 2. 检查环境参数

**关键规则**：必须明确环境参数才能执行启动！

检查用户的请求中是否包含环境信息：
- ✅ `启动项目用 dev` → 环境 = dev
- ✅ `运行 local 环境` → 环境 = local  
- ✅ `启动 prod` → 环境 = prod
- ❌ `启动项目` → **缺少环境，需要提醒**

### 3. 环境选择提醒（当未指定环境时）

当用户没有明确指定环境时，**必须**主动提醒并提供选项：

```
🚀 准备启动 yumy-boot 项目！

请选择运行环境：
  [1] dev    - 开发环境（常用，连接开发数据库）
  [2] local  - 本地环境（本地调试，连接本地数据库）
  [3] prod   - 生产环境（谨慎使用！）

请回复环境名称（dev/local/prod）或数字（1/2/3）
```

**等待用户回复后**，继续执行启动流程。

### 4. 执行启动脚本

使用 `.script/run.ps1` 脚本启动项目：

```powershell
# 切换到项目根目录
cd d:\workSpace\yumy-boot

# 执行启动脚本（替换 <env> 为实际环境）
.\.script\run.ps1 -Env <env>

# 或使用位置参数
.\.script\run.ps1 <env>
```

### 5. 监控启动状态

脚本会自动执行以下流程：
- ✅ 检查 JDK 21 环境
- ✅ 检查 Maven 可用性
- ✅ 检测并关闭占用端口的进程
- ✅ Maven 打包（跳过测试）
- ✅ 启动 Java 应用

启动成功后会显示：
```
✅ 应用启动成功!
进程 ID: <pid>
环境配置: <env>
访问地址: http://localhost:<port>
API 文档: http://localhost:<port>/swagger-ui.html
```

## 注意事项

### 环境选择建议

| 环境 | 适用场景 | 数据库 | 端口 |
|------|---------|--------|------|
| **dev** | 日常开发、功能测试 | 开发服务器 PostgreSQL | 8080（默认） |
| **local** | 本地调试、断点调试 | 本地 PostgreSQL | 8080（可配置） |
| **prod** | 生产部署 | 生产数据库 | 根据配置 |

**推荐**：日常开发使用 `dev` 环境。

### 端口冲突处理

如果启动失败提示端口占用：
```powershell
# 查看占用端口的进程
Get-NetTCPConnection -LocalPort 8080

# 手动关闭进程（替换 <PID>）
Stop-Process -Id <PID> -Force
```

### 日志查看

应用启动后，日志输出到：
```
d:\workSpace\yumy-boot\.log\yumy-boot.log       # 主日志
d:\workSpace\yumy-boot\.log\yumy-boot-error.log # 错误日志
```

查看实时日志：
```powershell
Get-Content .log\yumy-boot.log -Wait -Tail 50
```

## 常见问题

**Q: 提示 JDK 路径不存在？**  
A: 确保 JDK 21 安装在 `D:\workTool\jdk-21.0.10`，或修改 `.script/run.ps1` 中的 `$JdkHome` 变量。

**Q: Maven 打包失败？**  
A: 检查网络连接（需要下载依赖），或确认所有模块编译通过。

**Q: 如何停止正在运行的项目？**  
A: 通过进程 ID 关闭：`Stop-Process -Id <pid> -Force`

## 快速参考

```powershell
# 一键启动（推荐）
.\.script\run.ps1 dev

# 查看帮助
.\.script\run.ps1 -?

# 查看日志
Get-Content .log\yumy-boot.log -Tail 100
```
