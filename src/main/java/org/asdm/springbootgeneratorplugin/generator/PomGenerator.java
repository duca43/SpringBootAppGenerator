package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaAppInfo;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PomGenerator extends BasicGenerator {

    private final MetaAppInfo metaAppInfo;

    public PomGenerator(final GeneratorOptions generatorOptions, final MetaAppInfo metaAppInfo) {
        super(generatorOptions);
        this.metaAppInfo = metaAppInfo;
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
            out = this.getWriter("pom", MetaModel.getInstance().getMetaAppInfo().getName());
            if (out != null) {
                context.put("appInfo", this.metaAppInfo);
                context.put("dependencies", this.metaAppInfo.getDependencies());
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
