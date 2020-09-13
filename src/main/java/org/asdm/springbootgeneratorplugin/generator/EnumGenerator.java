package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaEnumeration;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class EnumGenerator extends BasicGenerator {

    private final MetaEnumeration metaEnumeration;

    public EnumGenerator(final GeneratorOptions generatorOptions, final MetaEnumeration metaEnumeration) {
        super(generatorOptions);
        this.metaEnumeration = metaEnumeration;
    }

    @Override
    public void generate() {

        try {
            super.generate();
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        final Writer out;
        final Map<String, Object> context = new HashMap<String, Object>();
        try {
            final String modelFilePackage = MetaModel.getInstance().getMetaAppInfo().getName() + "/src/main/java/" + MetaModel.getInstance().getPackageBase() + "/model";
            out = this.getWriter(this.metaEnumeration.getName() + "Enum", modelFilePackage);
            if (out != null) {
                context.put("enum", this.metaEnumeration);
                context.put("values", this.metaEnumeration.getValues());
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
