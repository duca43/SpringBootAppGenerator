package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApplicationGenerator extends BasicGenerator {

    public ApplicationGenerator(final GeneratorOptions generatorOptions, final String outputPath) {
        super(generatorOptions, outputPath);
    }

    @Override
    public void generate() throws IOException {

        try {
            super.generate();
        } catch (final IOException e) {
            throw new IOException(e.getMessage());
        }

        final Writer out;
        final Map<String, Object> context = new HashMap<>();
        try {
            final String appName =MetaModel.getInstance().getMetaAppInfo().getName();
            out = this.getWriter(appName + "Application");
            if (out != null) {
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                context.put("name", appName);
                log.info("Start generating {}Application.java...", appName);
                this.getTemplate().process(context, out);
                out.flush();
                log.info("End of generating {}Application.java...", appName);
            }
        } catch (final TemplateException | IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
