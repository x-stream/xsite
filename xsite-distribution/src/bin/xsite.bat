@echo off
rem run XSite

set XSITE_CLASSPATH=
for %%i in (%XSITE_HOME%\lib\*.jar) do call %XSITE_HOME%\bin\cpappend.bat %%i

%JAVA_HOME%\bin\java -classpath %XSITE_CLASSPATH% org.codehaus.xsite.Main %*


