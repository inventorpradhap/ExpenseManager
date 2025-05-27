@echo off
title Mount Current Folder as I: Drive

:: Check if I: drive is already in use
if exist I:\ (
    echo I: drive is already in use.
    echo Unmounting I: drive first...
    subst I: /D
)

:: Mount current directory as I: drive
subst I: "%~dp0"

echo Current folder has been mounted as I: drive
echo.
echo Path: %~dp0
echo.