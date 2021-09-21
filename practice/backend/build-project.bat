@echo off

echo Please select project:\n
echo [1]: product
echo [2]: order
echo [3]: inventory

set /p PROJECT_INDEX=">>"

echo select %PROJECT_INDEX%
if "%PROJECT_INDEX%" == "1" (
	echo start build product-service jar...
	gradlew.bat --offline :product-service:clean :product-service:buildTestPackage -x test
) else if "%PROJECT_INDEX%" == "2" (
	echo start build order-service jar...
	gradlew.bat --offline :order-service:clean :order-service:buildTestPackage -x test
) else if "%PROJECT_INDEX%" == "3" (
	echo start build inventory-service jar...
	gradlew.bat --offline :inventory-service:clean :inventory-service:buildTestPackage -x test
) else (
	echo project [%PROJECT_INDEX%] build failed
)