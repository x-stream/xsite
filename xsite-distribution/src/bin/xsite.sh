#!/bin/bash
# Run xsite

EXEC="$JAVA_HOME/bin/java -classpath ../classes  org.codehaus.xsite.Main $@"
$EXEC

