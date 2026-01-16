@echo off
REM Script de compilation et lancement de PowerCity Manager

echo ======================================
echo PowerCity Manager - Compilation
echo ======================================

REM Créer le répertoire de sortie si nécessaire
if not exist "out" mkdir out

REM Compiler le projet
echo Compilation en cours...
javac -d out -encoding UTF-8 src\main\java\com\powercity\model\*.java src\main\java\com\powercity\controller\*.java src\main\java\com\powercity\view\*.java src\main\java\com\powercity\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Erreur de compilation!
    pause
    exit /b 1
)

echo Compilation réussie!
echo.
echo ======================================
echo Lancement du jeu...
echo ======================================
echo.

REM Lancer le jeu
java -cp out com.powercity.Main

pause
