package org.codehaus.xsite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.nanocontainer.script.ScriptedContainerBuilder;
import org.nanocontainer.script.xml.XMLContainerBuilder;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.ObjectReference;
import org.picocontainer.defaults.SimpleReference;

/**
 * Command line entry point for building XSite.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 * @author Mauro Talevi
 */
public class Main {

    private static final String XSITE_COMPOSITION = "xsite.xml";
    private static final String XSITE_PROPERTIES = "xsite.properties";
    private static final String CLASSNAME = Main.class.getName();
    private static final char HELP_OPT = 'h';
    private static final char VERSION_OPT = 'v';
    private static final char SITEMAP_OPT = 'm';
    private static final char SKIN_OPT = 's';
    private static final char FILE_OPT = 'f';
    private static final char RESOURCE_OPT = 'r';
    private static final char OUTPUT_OPT = 'o';
    
    public static final void main(String[] args) throws Exception {
        new Main(args);
    }
    
    public Main(String[] args) throws Exception {
        Options options = createOptions();        
        CommandLine cl = null;
        try {
            cl = getCommandLine(args, options);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse arguments: ",e);
        }

        if (cl.hasOption(HELP_OPT)) {
	    printUsage(options);
	} else if (cl.hasOption(VERSION_OPT)) {
	    Properties properties = createProperties();
	    printVersion(properties);
	} else {
	    if ( !validateOptions(cl) ){
		printUsage(options);
		throw new IllegalArgumentException("Invalid arguments "+cl.getArgList());
	    }
	    try {
		URL compositionURL = getCompositionURL(cl);
		XSite xsite = instantiateXSite(compositionURL);
		xsite.build(new File(cl.getOptionValue(SITEMAP_OPT)), new File(
			cl.getOptionValue(SKIN_OPT)), new File(cl
			.getOptionValue(OUTPUT_OPT)));

	    } catch ( Exception e) {
		e.printStackTrace();
		throw new IllegalStateException("Failed to build XSite", e);
	    }
	}        
    }
    
    private boolean validateOptions(CommandLine cl) {
	if ( cl.hasOption(SITEMAP_OPT) && cl.hasOption(SKIN_OPT) && cl.hasOption(OUTPUT_OPT)){
	    return true;
	}
	return false;
    }

    static URL getCompositionURL(CommandLine cl) throws MalformedURLException {
	URL url = null;
	Thread.currentThread().setContextClassLoader(
		Main.class.getClassLoader());
	if (cl.hasOption(FILE_OPT)) {
	    File file = new File(cl.getOptionValue(FILE_OPT));
	    if (file.exists()) {
		url = file.toURL();
	    }
	} else if (cl.hasOption(RESOURCE_OPT)) {
	    url = Thread.currentThread().getContextClassLoader()
		    .getResource(cl.getOptionValue(RESOURCE_OPT));
	} else {
	    url = Main.class.getResource(XSITE_COMPOSITION);
	}
	return url;
    }

    static final Properties createProperties() throws IOException {
	Properties properties = new Properties();
	properties.load(Main.class.getResourceAsStream(XSITE_PROPERTIES));
	return properties;
    }

    static final Options createOptions() {
        Options options = new Options();
        options.addOption(String.valueOf(HELP_OPT), "help", false,
                "print this message and exit");
        options.addOption(String.valueOf(VERSION_OPT), "version", false,
                "print the version information and exit");
        options.addOption(String.valueOf(FILE_OPT), "file", true,
                "specify the composition file");
        options.addOption(String.valueOf(RESOURCE_OPT), "resource", true,
                "specify the composition resource in classpath");
        options.addOption(String.valueOf(SITEMAP_OPT), "sitemap", true,
                "specify the sitemap file");
        options.addOption(String.valueOf(SKIN_OPT), "skin", true,
                "specify the skin file");
        options.addOption(String.valueOf(OUTPUT_OPT), "output", true,
                "specify the output dir");
        return options;
    }
    
    static CommandLine getCommandLine(String[] args, Options options) throws ParseException {
        CommandLineParser parser = new PosixParser();
        return parser.parse(options, args);
    }
    
    private static XSite instantiateXSite(URL url) {
        ScriptedContainerBuilder builder = new XMLContainerBuilder(url, Main.class.getClassLoader());
        ObjectReference reference = new SimpleReference();
        builder.buildContainer(reference, null, null, false);
        PicoContainer pico = (PicoContainer)reference.get();
        return (XSite)pico.getComponentInstanceOfType(XSite.class);        
    }

    private static void printUsage(Options options) {
        final String lineSeparator = System.getProperty("line.separator");
        final StringBuffer usage = new StringBuffer();
        usage.append(lineSeparator);
        usage.append(CLASSNAME + ": -m<path-to-sitemap> -s<path-to-skin> -o<output-dir> "+
                     "[-f<filesystem-path-to-xsite.xml>|-r<classpath-path-to-xsite.xml>] "+
                     "[-h|-v]");
        usage.append(lineSeparator);
        usage.append("Options: "+options.getOptions());
        System.out.println(usage.toString());
    }

    private static void printVersion(Properties properties) {
        System.out.println(XSite.class.getName()+" version: "+properties.getProperty("version"));
    }

}
