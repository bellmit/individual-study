@echo off

echo Please select project:\n
echo [1]: springboot

set /p PROJECT_INDEX=

echo select %PROJECT_INDEX%
if "%PROJECT_INDEX%" == "1" (
	echo start build springboot jar...
	gradlew --offline :springboot:clean :springboot:buildTestPackage -x test
	pause
) else (
	echo project [%PROJECT_INDEX%] build failed
	pause
)