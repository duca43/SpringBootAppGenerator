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
public class ModelGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;

    public ModelGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity, final String outputPath) {
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
            final String name = this.metaEntity.getName();
            out = this.getWriter(name);
            if (out != null) {
                context.put("entity", this.metaEntity);
                context.put("libraries", this.metaEntity.getImports());
                context.put("properties", this.metaEntity.getColumns());
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                log.info("Start generating {}.java...", name);
                this.getTemplate().process(context, out);
                out.flush();
                log.info("End of generating {}.java...", name);
            }
        } catch (final TemplateException | IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
