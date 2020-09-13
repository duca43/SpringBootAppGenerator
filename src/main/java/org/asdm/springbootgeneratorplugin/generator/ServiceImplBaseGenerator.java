package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.TemplateException;
import org.asdm.springbootgeneratorplugin.model.MetaColumn;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * EJB generator that now generates incomplete ejb classes based on MagicDraw
 * class model
 *
 * @ToDo: enhance resources/templates/ejbclass.ftl template and intermediate
 * data structure (@see myplugin.generator.fmmodel) in order to generate
 * complete ejb classes
 */

public class ServiceImplBaseGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;

    public ServiceImplBaseGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity) {
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

        int pkColumnsCounter = 0;
        String pkType = "";
        String pkName = "";
        for (final MetaColumn metaColumn : this.metaEntity.getColumns()) {
            if (metaColumn.isPartOfPrimaryKey()) {
                pkColumnsCounter++;
                pkType = metaColumn.getType();
                pkName = metaColumn.getName();
            }
        }

        if (pkColumnsCounter == 0) {
            this.metaEntity.setPrimaryKeyType("Long");
            this.metaEntity.setPrimaryKeyName("id");
        } else if (pkColumnsCounter == 1) {
            this.metaEntity.setPrimaryKeyType(pkType);
            this.metaEntity.setPrimaryKeyName(pkName);
        } else {
            this.metaEntity.setPrimaryKeyType(this.metaEntity.getName() + "Id");
            this.metaEntity.setPrimaryKeyName("id");
        }

        final Writer out;
        final Map<String, Object> context = new HashMap<String, Object>();
        try {
            final String serviceImplBaseFilePackage = MetaModel.getInstance().getMetaAppInfo().getName() + "/src/main/java/" + MetaModel.getInstance().getPackageBase() + "/service/impl/base";
            out = this.getWriter(this.metaEntity.getName() + "ServiceImplBase", serviceImplBaseFilePackage);
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