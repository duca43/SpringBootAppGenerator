package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServiceGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;

    public ServiceGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity, final String outputPath) {
        super(generatorOptions, outputPath);
        this.metaEntity = metaEntity;
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
            final String filename = this.metaEntity.getName() + "Service";
            out = this.getWriter(filename);
            if (out != null) {
                context.put("entity", this.metaEntity);
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                log.info("Start generating {}.java...", filename);
                this.getTemplate().process(context, out);
                out.flush();
                log.info("End of generating {}.java...", filename);
            }
        } catch (final TemplateException | IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
