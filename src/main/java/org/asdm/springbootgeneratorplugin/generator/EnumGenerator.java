package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.asdm.springbootgeneratorplugin.model.MetaEnumeration;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EnumGenerator extends BasicGenerator {

    private final MetaEnumeration metaEnumeration;

    public EnumGenerator(final GeneratorOptions generatorOptions, final MetaEnumeration metaEnumeration, final String outputPath) {
        super(generatorOptions, outputPath);
        this.metaEnumeration = metaEnumeration;
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
            final String filename = this.metaEnumeration.getName();
            out = this.getWriter(filename);
            if (out != null) {
                context.put("enum", this.metaEnumeration);
                context.put("values", this.metaEnumeration.getValues());
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
