package com.sadeghi;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ali Sadeghi
 * at 2016/07/07 - 02:54
 */
public class FreeMarkerEngine {

    public static void fillTemplate(String templateName, TableMetaData tableMetaData) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        // Where do we load the templates from:
        cfg.setDirectoryForTemplateLoading(new File("C:\\projects\\code-generator\\src\\main\\resources\\ftl"));
        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
//        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("tableMetaData", tableMetaData);
        // 2.2. Get the template
        Template template = cfg.getTemplate(templateName);
        // 2.3. Generate the output
        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
//        Writer fileWriter = new FileWriter(new File("output.html"));
        /*PrintWriter writer = new PrintWriter(System.out);
        try {
            template.process(input, writer);
        } finally {
            writer.close();
        }*/
    }
}
