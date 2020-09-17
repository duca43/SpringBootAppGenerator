package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.asdm.springbootgeneratorplugin.model.MetaModel;
import org.asdm.springbootgeneratorplugin.util.Constants;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PomGenerator extends BasicGenerator {

    public PomGenerator(final GeneratorOptions generatorOptions, final String outputPath) {
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
            out = this.getWriter(Constants.POM_FILENAME);
            if (out != null) {
                context.put("appInfo", MetaModel.getInstance().getMetaAppInfo());
                context.put("dependencies", MetaModel.getInstance().getMetaAppInfo().getDependencies());
                log.info("Start generating {}.java...", Constants.POM_FILENAME);
                this.getTemplate().process(context, out);
                out.flush();
                log.info("End of generating {}.java...", Constants.POM_FILENAME);
            }
        } catch (final TemplateException | IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
