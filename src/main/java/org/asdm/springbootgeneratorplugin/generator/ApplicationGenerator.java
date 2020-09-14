package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaAppInfo;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ApplicationGenerator extends BasicGenerator {

    private final MetaAppInfo metaAppInfo;

    public ApplicationGenerator(final GeneratorOptions generatorOptions, final MetaAppInfo metaAppInfo) {
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
            final String serviceFilePackage = MetaModel.getInstance().getMetaAppInfo().getName() + "/src/main/java/" + MetaModel.getInstance().getPackageBase();
            out = this.getWriter(modifyName(this.metaAppInfo.getInfo().getArtifactId()) + "Application", serviceFilePackage);
            if (out != null) {
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                context.put("name", modifyName(this.metaAppInfo.getInfo().getArtifactId()));
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private String modifyName(String artifactId) {
        StringBuilder builder = new StringBuilder();
        if (artifactId.contains("_")) {
            String[] words = artifactId.split("_");
            for (String word : words) {
                builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
            }
        } else if (artifactId.contains("-")) {
            String[] words = artifactId.split("-");
            for (String word : words) {
                builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
            }
        } else {
            builder.append(artifactId.substring(0, 1).toUpperCase()).append(artifactId.substring(1));
        }
        return builder.toString();
    }
}
