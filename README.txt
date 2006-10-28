Before building:

mvn install:install-file -Dfile=lib/sitemesh-2.3.jar -DgroupId=com.opensymphony.sitemesh -DartifactId=sitemesh -Dversion=2.3 -Dpackaging=jar
mvn install:install-file -Dfile=lib/pom.xml -DgroupId=com.opensymphony.sitemesh -DartifactId=sitemesh -Dversion=2.3 -Dpackaging=pom

NOTE:  only until it's not uploaded to maven repo central 
http://jira.codehaus.org/browse/MAVENUPLOAD-1207






