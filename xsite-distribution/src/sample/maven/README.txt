XSite with Doxia/Maven integration:

- Install Maven 3
- Run "mvn clean package"

Generated site will be located in target/site

Note: If you like to use a newer version of XSite with this example, you do
not only have to update the version number of the XSite Maven plugin in the
pom.xml, but also in the file src/site/site.xml. Therefore we define a property
in the pom.xml that is used in both locations.
