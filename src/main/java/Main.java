import org.asdm.springbootgeneratorplugin.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String[] args) {
        final MetaLibInfo appInfo = new MetaLibInfo("org.test", "my-app", "0.0.1-SNAPSHOT");
        final MetaAppInfo metaAppInfo = new MetaAppInfo(appInfo, "My App", "Some description", 1.8);
        metaAppInfo.getDependencies().add(new MetaLibInfo("mysql", "mysql-connector-java", "8.0.21"));

        MetaModel.getInstance().setMetaAppInfo(metaAppInfo);

        final String basePackage = metaAppInfo.getInfo().getGroupId() + "." + metaAppInfo.getInfo().getArtifactId().replaceAll("-", "").toLowerCase();
        MetaModel.getInstance().setPackageBase(basePackage);

        final GeneratorOptions generatorOptions = new GeneratorOptions("E:/temp", "pom", "src/main/resources/templates", "{0}.xml", true, null);
        final PomGenerator pomGenerator = new PomGenerator(generatorOptions, metaAppInfo);
        pomGenerator.generate();

        generatorOptions.setTemplateName("application");
        generatorOptions.setOutputFileName("{0}.java");
        final ApplicationGenerator applicationGenerator = new ApplicationGenerator(generatorOptions, metaAppInfo);
        applicationGenerator.generate();

        final MetaColumn metaColumn1 = new MetaColumn("id", "Integer", "private", 1, 1, true, null, true);
        final MetaColumn metaColumn12 = new MetaColumn("id2", "Integer", "private", 1, 1, true, null, true);
        final MetaColumn metaColumn2 = new MetaColumn("username", "String", "private", 1, 1, true, "ManyToOne", false);
        final MetaColumn metaColumn3 = new MetaColumn("password", "String", "private", 1, 1, false, null, false);

        final MetaEntity metaEntity = new MetaEntity("User", "public");
        metaEntity.getColumns().add(metaColumn1);
        metaEntity.getColumns().add(metaColumn12);
        metaEntity.getColumns().add(metaColumn2);
        metaEntity.getColumns().add(metaColumn3);

        generatorOptions.setTemplateDir("src/main/resources/templates/model");
        generatorOptions.setTemplateName("model");
        generatorOptions.setOutputFileName("{0}.java");
        final ModelGenerator modelGenerator = new ModelGenerator(generatorOptions, metaEntity);
        modelGenerator.generate();


        int pkColumnsCounter = 0;
        List<MetaColumn> columns = new ArrayList<>();
        for (MetaColumn column: metaEntity.getColumns()){
            if (column.isPartOfPrimaryKey()){
                pkColumnsCounter++;
                columns.add(column);
            }
        }
        if (pkColumnsCounter > 1){
            generatorOptions.setTemplateName("embedded_key");
            generatorOptions.setOutputFileName("{0}.java");
            final EmbeddedKeyGenerator embeddedKeyGenerator = new EmbeddedKeyGenerator(generatorOptions, metaEntity, columns);
            embeddedKeyGenerator.generate();
        }

        generatorOptions.setTemplateDir("src/main/resources/templates/repository");
        generatorOptions.setTemplateName("repository_base");
        generatorOptions.setOutputFileName("{0}.java");
        final RepositoryBaseGenerator repositoryBaseGenerator = new RepositoryBaseGenerator(generatorOptions, metaEntity);
        repositoryBaseGenerator.generate();

        generatorOptions.setTemplateName("repository");
        generatorOptions.setOverwrite(false);
        final RepositoryGenerator repositoryGenerator = new RepositoryGenerator(generatorOptions, metaEntity);
        repositoryGenerator.generate();

        generatorOptions.setTemplateDir("src/main/resources/templates/service");
        generatorOptions.setTemplateName("service_base");
        generatorOptions.setOverwrite(true);
        final ServiceBaseGenerator serviceBaseGenerator = new ServiceBaseGenerator(generatorOptions, metaEntity);
        serviceBaseGenerator.generate();

        generatorOptions.setTemplateName("service");
        generatorOptions.setOverwrite(false);
        final ServiceGenerator serviceGenerator = new ServiceGenerator(generatorOptions, metaEntity);
        serviceGenerator.generate();
    }
}