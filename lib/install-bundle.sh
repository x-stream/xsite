#!/bin/sh

mkdir bundle
cd bundle
jar xvf ../sitemesh-2.3-bundle.jar

mvn install:install-file -Dfile=sitemesh-2.3.jar -DgroupId=com.opensymphony.sitemesh -DartifactId=sitemesh -Dversion=2.3 -Dpackaging=jar
mvn install:install-file -Dfile=pom.xml -DgroupId=com.opensymphony.sitemesh -DartifactId=sitemesh -Dversion=2.3 -Dpackaging=pom