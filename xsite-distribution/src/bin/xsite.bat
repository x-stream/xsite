rem run XSite

set XSITE_CLASSPATH=
for %%path in (%XSITE_HOME%\lib\*.jar) do call cpappend.bat %%path

EXEC="%JAVA_HOME%\bin\java -classpath %XSITE_CLASSPATH% org.codehaus.xsite.Main $@"
%EXEC%

