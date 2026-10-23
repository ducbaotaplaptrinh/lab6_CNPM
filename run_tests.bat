@echo off
chcp 65001 > nul
echo ========================================================
echo   CHƯƠNG TRÌNH KIỂM THỬ TỰ ĐỘNG LAB 6 - SELENIUM DEMOQA
echo   Sinh viên: Nguyễn Đức Bảo
echo ========================================================

set "JAVA_HOME=C:\Users\ADMIN\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_21.0.12.v20260826-1216\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo [1/3] Đang kiểm tra phiên bản Java...
java -version

echo.
echo [2/3] Đang biên dịch và chạy kiểm thử tự động...
call .\mvnw.cmd test -Dheadless=false

echo.
echo [3/3] Đã hoàn thành toàn bộ kịch bản kiểm thử!
echo Ảnh chụp màn hình được lưu tại thư mục: screenshots\
pause
