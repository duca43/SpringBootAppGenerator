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

public class ModelGenerator extends BasicGenerator {

    private final MetaEntity metaEntity;

    public ModelGenerator(final GeneratorOptions generatorOptions, final MetaEntity metaEntity) {
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
        for (final MetaColumn metaColumn : this.metaEntity.getColumns()) {
            if (metaColumn.isPartOfPrimaryKey()) {
                pkColumnsCounter++;
            }
        }

        this.metaEntity.setPrimaryKeyColumnCounter(pkColumnsCounter);

        final Writer out;
        final Map<String, Object> context = new HashMap<String, Object>();
        try {
            final String modelFilePackage = MetaModel.getInstance().getMetaAppInfo().getName() + "/src/main/java/" + MetaModel.getInstance().getPackageBase() + "/model";
            out = this.getWriter(this.metaEntity.getName() + "Entity", modelFilePackage);
            if (out != null) {
                context.put("entity", this.metaEntity);
                context.put("properties", this.metaEntity.getColumns());
                context.put("packageBase", MetaModel.getInstance().getPackageBase());
                this.getTemplate().process(context, out);
                out.flush();
            }
        } catch (final TemplateException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}