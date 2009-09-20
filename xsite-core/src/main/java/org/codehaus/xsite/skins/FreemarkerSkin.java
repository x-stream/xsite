package org.codehaus.xsite.skins;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xsite.Skin;
import org.codehaus.xsite.model.Page;
import org.codehaus.xsite.model.Sitemap;


/**
 * Provides HTML 'skin' around content, using a FreeMarker template.
 *
 * @author Joe Walnes
 */
public class FreemarkerSkin implements Skin {

    private Template template;  

    public void load(File skinFile) {
        try {
            Reader fileReader = new FileReader(skinFile);
            try {
                template = new Template(skinFile.getName(), fileReader, new Configuration());
            } finally {
                fileReader.close();
            }
        } catch (IOException e) {
            throw new CannotCreateSkinException(e);
        }
    }
    
    public void skin(Page page, Sitemap sitemap, File outputDirectory) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("title", page.getTitle());
        context.put("head", page.getHead());
        context.put("body", page.getBody());
        context.put("page", page);
        context.put("sitemap", sitemap);
        context.put("centerClass", page.isIndex() ? "Content3Column" : "Content2Column");

        try {
            Writer writer = new FileWriter(new File(outputDirectory, page.getFilename()));
            try {
                template.process(context, writer);
            } finally {
                writer.close();
            }
        } catch (TemplateException e) {
            throw new CannotApplySkinException(e);
        } catch (IOException e) {
            throw new CannotApplySkinException(e);
        }
    }

    // Exceptions ----------------------

    @SuppressWarnings("serial")
    public static class CannotCreateSkinException extends RuntimeException {
        public CannotCreateSkinException(Throwable throwable) {
            super(throwable);
        }
    }

    @SuppressWarnings("serial")
    public static class CannotApplySkinException extends RuntimeException {
        public CannotApplySkinException(Throwable throwable) {
            super(throwable);
        }
    }

}
