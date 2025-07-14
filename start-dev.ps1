# start-dev.ps1

# 检查 .env 文件是否存在
if (-not (Test-Path ".env")) {
    Write-Host ".env file not found. Please create one."
    exit 1
}

# 读取 .env 文件并设置环境变量
Get-Content .env | ForEach-Object {
    $line = $_.Trim()
    # 忽略注释和空行
    if ($line -and $line -notlike '#*') {
        $key, $value = $line -split '=', 2
        # 设置环境变量，仅对当前 PowerShell 进程有效
        [System.Environment]::SetEnvironmentVariable($key.Trim(), $value.Trim(), "Process")
        Write-Host "Loaded env var: $($key.Trim())"
    }
}

Write-Host "Starting Spring Boot application..."
# 运行 Gradle 任务
./gradlew bootrun
