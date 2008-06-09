package org.codehaus.xsite;

public class CustomXSite extends XSite {

    public CustomXSite(SitemapLoader loader, Skin skin, LinkValidator[] validators, FileSystem fileSystem,
            XSiteConfiguration configuration) {
        super(loader, skin, validators, fileSystem, configuration);
    }

}
