import org.asdm.springbootgeneratorplugin.model.*;

public class Main {

    public static void main(final String[] args) {
        final MetaLibInfo appInfo = new MetaLibInfo("org.test", "my-app", "0.0.1-SNAPSHOT");
        final MetaAppInfo metaAppInfo = new MetaAppInfo(appInfo, "My App", "Some description", 1.8);
        metaAppInfo.getDependencies().add(new MetaLibInfo("mysql", "mysql-connector-java", "8.0.21"));

        MetaModel.getInstance().setMetaAppInfo(metaAppInfo);

        final String basePackage = metaAppInfo.getInfo().getGroupId() + "." + metaAppInfo.getInfo().getArtifactId().replaceAll("-", "").toLowerCase();
        MetaModel.getInstance().setPackageBase(basePackage);

        final GeneratorOptions generatorOptions = new GeneratorOptions("D:/temp", "pom", "src/main/resources/templates", "{0}.xml", true, null);
        final PomGenerator pomGenerator = new PomGenerator(generatorOptions, metaAppInfo);
        pomGenerator.generate();

        final MetaColumn metaColumn1 = new MetaColumn("id", "Integer", "private", 1, 1, true, false, null, true);
        final MetaColumn metaColumn2 = new MetaColumn("username", "String", "private", 1, 1, true, false, null, false);
        final MetaColumn metaColumn3 = new MetaColumn("password", "String", "private", 1, 1, false, false, null, false);

        final MetaEntity metaEntity = new MetaEntity("User", "public");
        metaEntity.getColumns().add(metaColumn1);
        metaEntity.getColumns().add(metaColumn2);
        metaEntity.getColumns().add(metaColumn3);

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