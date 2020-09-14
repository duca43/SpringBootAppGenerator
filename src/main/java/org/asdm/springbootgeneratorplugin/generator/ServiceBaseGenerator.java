package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ServiceBaseGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;

    public ServiceBaseGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity) {
        super(generatorOptions);
        this.metaEntity = metaEntity;
    }

    @Override
    public void generate() {

        try {
            super.generate();
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        final Writer out;
        final Map<String, Object> context = new HashMap<>();
        try {
            final String serviceBaseFilePackage = MetaModel.getInstance().getMetaAppInfo().getName() + "/src/main/java/" + MetaModel.getInstance().getPackageBase() + "/service/base";
            out = this.getWriter(this.metaEntity.getName() + "ServiceBase", serviceBaseFilePackage);
            if (out != null) {
                context.put("entity", this.metaEntity);
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
