package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

@Slf4j
public class ApplicationPropertiesGenerator extends BasicGenerator {

    public ApplicationPropertiesGenerator(final GeneratorOptions generatorOptions, final String outputPath) {
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
        try {
            out = this.getWriter("application");
            if (out != null) {
                log.info("Start generating application.properties...");
                this.getTemplate().process(null, out);
                out.flush();
                log.info("End of generating application.properties...");
            }
        } catch (final TemplateException | IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
