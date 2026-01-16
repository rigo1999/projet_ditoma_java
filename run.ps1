# Script de compilation et lancement de PowerCity Manager (PowerShell)

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "PowerCity Manager - Compilation" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

# Créer le répertoire de sortie si nécessaire
if (-not (Test-Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}

# Compiler le projet
Write-Host "`nCompilation en cours..." -ForegroundColor Yellow
$compileResult = javac -d out -encoding UTF-8 `
    src\main\java\com\powercity\model\*.java `
    src\main\java\com\powercity\controller\*.java `
    src\main\java\com\powercity\view\*.java `
    src\main\java\com\powercity\*.java 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nErreur de compilation!" -ForegroundColor Red
    Write-Host $compileResult
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}

Write-Host "Compilation réussie!" -ForegroundColor Green
Write-Host "`n======================================" -ForegroundColor Cyan
Write-Host "Lancement du jeu..." -ForegroundColor Cyan
Write-Host "======================================`n" -ForegroundColor Cyan

# Lancer le jeu
java -cp out com.powercity.Main
