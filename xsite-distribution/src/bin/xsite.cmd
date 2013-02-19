@echo off
rem run XSite

if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

set XSITE_CLASSPATH=
for %%i in (%XSITE_HOME%\lib\*.jar) do call :CP_append %%i

%JAVA_HOME%\bin\java -classpath %XSITE_CLASSPATH% org.codehaus.xsite.Main %*

if "%OS%"=="Windows_NT" @endlocal
if "%OS%"=="WINNT" @endlocal
goto :EOF


:CP_append
	set XSITE_CLASSPATH=%XSITE_CLASSPATH%;%1
	goto :EOF
