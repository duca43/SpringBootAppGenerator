package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaColumn;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EJB generator that now generates incomplete ejb classes based on MagicDraw
 * class model
 *
 * @ToDo: enhance resources/templates/ejbclass.ftl template and intermediate
 * data structure (@see myplugin.generator.fmmodel) in order to generate
 * complete ejb classes
 */

public class EmbeddedKeyGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;
    private final List<MetaColumn> metaColumns;

    public EmbeddedKeyGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity, List<MetaColumn> metaColumns) {
        super(generatorOptions);
        this.metaEntity = metaEntity;
        this.metaColumns = metaColumns;
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
            out = this.getWriter(this.metaEntity.getName() + "Id", modelFilePackage);
            if (out != null) {
                context.put("entity", this.metaEntity);
                context.put("properties", this.metaColumns);
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
