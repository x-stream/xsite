#!/bin/bash

# Run xsite

EXEC="$JAVA_HOME/bin/java -classpath ../lib/*.jar -jar ../lib/xsite-1.0-SNAPSHOT.jar $@"
echo $EXEC
$EXEC

