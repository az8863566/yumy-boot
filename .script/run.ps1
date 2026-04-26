#Requires -Version 5.1
<#
.SYNOPSIS
    甄味App 一键打包运行脚本
.DESCRIPTION
    根据传入的环境参数（dev/local/prod）自动打包并运行 yumy-boot 项目
    运行前自动检测并关闭占用端口的进程
.PARAMETER Env
    运行环境：dev、local 或 prod
.EXAMPLE
    .\run.ps1 -Env dev
    .\run.ps1 dev
#>

param(
    [Parameter(Mandatory = $true, Position = 0)]
    [ValidateSet("dev", "local", "prod")]
    [string]$Env
)

# 设置输出编码为 UTF-8（支持中文）
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

# ==================== 配置区域 ====================
$ProjectRoot = Split-Path -Parent $PSScriptRoot
$BootstrapModule = Join-Path $ProjectRoot "yumy-bootstrap"
$JarFile = Join-Path $BootstrapModule "target\yumy-bootstrap-1.0.0-SNAPSHOT.jar"
$DefaultPort = 8080  # 默认端口，根据 application.yml 配置
$JdkHome = "F:\Tool\jdk21"
$MavenCommand = "mvn"

# ==================== 日志函数 ====================
function Write-Log {
    param(
        [string]$Message,
        [ValidateSet("INFO", "WARN", "ERROR", "SUCCESS")]
        [string]$Level = "INFO"
    )
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $colorMap = @{
        "INFO"    = "White"
        "WARN"    = "Yellow"
        "ERROR"   = "Red"
        "SUCCESS" = "Green"
    }
    Write-Host "[$timestamp] [$Level] $Message" -ForegroundColor $colorMap[$Level]
}

# ==================== 步骤 1：环境校验 ====================
Write-Log "==========================================" "INFO"
Write-Log "🚀 甄味App 启动脚本开始执行" "SUCCESS"
Write-Log "==========================================" "INFO"
Write-Log "目标环境: $Env" "INFO"
Write-Log "项目根目录: $ProjectRoot" "INFO"

# ==================== 步骤 2：检查 JDK ====================
Write-Log "检查 JDK 环境..." "INFO"
if (-not (Test-Path $JdkHome)) {
    Write-Log "❌ JDK 路径不存在: $JdkHome" "ERROR"
    Write-Log "请检查 AGENTS.md 中的 JDK 配置" "ERROR"
    exit 1
}
$env:JAVA_HOME = $JdkHome
$env:PATH = "$JdkHome\bin;$env:PATH"
Write-Log "JDK 路径已设置: $JdkHome" "SUCCESS"

# ==================== 步骤 3：检查 Maven ====================
Write-Log "检查 Maven 环境..." "INFO"
try {
    $mavenVersion = & $MavenCommand -version 2>&1
    Write-Log "Maven 可用" "SUCCESS"
} catch {
    Write-Log "Maven 未找到,请确保已安装并配置环境变量" "ERROR"
    exit 1
}

# ==================== 步骤 4：读取配置端口 ====================
Write-Log "读取配置文件端口..." "INFO"
$configFile = Join-Path $BootstrapModule "src\main\resources\application-$Env.yml"
if (-not (Test-Path $configFile)) {
    Write-Log "❌ 配置文件不存在: $configFile" "ERROR"
    exit 1
}

# 尝试从配置文件读取端口（支持 server.port 配置）
try {
    $configContent = Get-Content $configFile -Raw -Encoding UTF8
    if ($configContent -match 'server\.port:\s*(\d+)') {
        $DefaultPort = [int]$Matches[1]
    }
} catch {
    Write-Log "⚠️ 无法读取端口配置，使用默认端口: $DefaultPort" "WARN"
}
Write-Log "📡 服务端口: $DefaultPort" "INFO"

# ==================== 步骤 5：检查并关闭占用端口的进程 ====================
Write-Log "检查端口 $DefaultPort 占用情况..." "INFO"
$portProcess = Get-NetTCPConnection -LocalPort $DefaultPort -ErrorAction SilentlyContinue | Where-Object { $_.State -eq 'Listen' }

if ($portProcess) {
    $processId = $portProcess.OwningProcess
    $processName = (Get-Process -Id $processId -ErrorAction SilentlyContinue).ProcessName
    Write-Log "端口 $DefaultPort 已被占用 (PID: $processId, 进程: $processName)" "WARN"
    Write-Log "正在关闭占用端口的进程..." "INFO"
    
    try {
        Stop-Process -Id $processId -Force -ErrorAction Stop
        Start-Sleep -Seconds 2
        
        # 验证端口是否已释放
        $verifyProcess = Get-NetTCPConnection -LocalPort $DefaultPort -ErrorAction SilentlyContinue | Where-Object { $_.State -eq 'Listen' }
        if (-not $verifyProcess) {
            Write-Log "成功关闭进程 (PID: $processId)" "SUCCESS"
        } else {
            Write-Log "端口仍被占用,请手动处理" "ERROR"
            exit 1
        }
    } catch {
        Write-Log "关闭进程失败: $_" "ERROR"
        exit 1
    }
} else {
    Write-Log "端口 $DefaultPort 空闲" "SUCCESS"
}

# ==================== 步骤 6：Maven 打包 ====================
Write-Log "==========================================" "INFO"
Write-Log "📦 开始 Maven 打包..." "INFO"
Write-Log "==========================================" "INFO"

Set-Location $ProjectRoot
Write-Log "执行命令: mvn clean package -Dmaven.test.skip=true" "INFO"

try {
    $buildOutput = & $MavenCommand clean package "-Dmaven.test.skip=true" 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Log "打包失败,退出码: $LASTEXITCODE" "ERROR"
        Write-Log "详细错误信息:" "ERROR"
        $buildOutput | ForEach-Object { Write-Log "  $_" "ERROR" }
        exit 1
    }
    Write-Log "Maven 打包成功" "SUCCESS"
} catch {
    Write-Log "打包异常: $_" "ERROR"
    exit 1
}

# 验证 JAR 文件是否存在
if (-not (Test-Path $JarFile)) {
    Write-Log "JAR 文件未生成: $JarFile" "ERROR"
    exit 1
}
Write-Log "JAR 文件: $JarFile" "SUCCESS"

# ==================== 步骤 7：启动应用 ====================
Write-Log "==========================================" "INFO"
Write-Log "🚀 启动应用 (环境: $Env)..." "INFO"
Write-Log "==========================================" "INFO"

$javaArgs = @(
    "-Dspring.profiles.active=$Env",
    "-jar",
    "`"$JarFile`""
)

Write-Log "执行命令: java $($javaArgs -join ' ')" "INFO"
Write-Log "日志输出路径: $(Join-Path $ProjectRoot '.log')" "INFO"
Write-Log "==========================================" "SUCCESS"

# 启动 Java 进程(后台运行)
try {
    $process = Start-Process -FilePath (Join-Path $JdkHome "bin\java.exe") `
        -ArgumentList $javaArgs `
        -WorkingDirectory $ProjectRoot `
        -NoNewWindow `
        -PassThru `
        -ErrorAction Stop
    
    Write-Log "应用启动成功!" "SUCCESS"
    Write-Log "进程 ID: $($process.Id)" "INFO"
    Write-Log "环境配置: $Env" "INFO"
    Write-Log "访问地址: http://localhost:$DefaultPort" "INFO"
    Write-Log "API 文档: http://localhost:$DefaultPort/swagger-ui.html" "INFO"
    Write-Log "" "INFO"
    Write-Log "提示: 查看日志文件获取详细运行信息" "INFO"
    Write-Log "   日志路径: $(Join-Path $ProjectRoot '.log\yumy-boot.log')" "INFO"
    Write-Log "   错误日志: $(Join-Path $ProjectRoot '.log\yumy-boot-error.log')" "INFO"
} catch {
    Write-Log "应用启动失败: $_" "ERROR"
    exit 1
}
