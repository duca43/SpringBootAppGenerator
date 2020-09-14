package org.asdm.springbootgeneratorplugin;

import org.asdm.springbootgeneratorplugin.generator.*;
import org.asdm.springbootgeneratorplugin.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String[] args) {
        final MetaLibInfo appInfo = new MetaLibInfo("org.test", "my-app", "0.0.1-SNAPSHOT");
        final MetaAppInfo metaAppInfo = new MetaAppInfo(appInfo, "My App", "Some description", 1.8);
        metaAppInfo.getDependencies().add(new MetaLibInfo("com.h2database", "h2", "1.4.200"));

        MetaModel.getInstance().setMetaAppInfo(metaAppInfo);

        final String basePackage = metaAppInfo.getInfo().getGroupId() + "." + metaAppInfo.getInfo().getArtifactId().replaceAll("-", "").toLowerCase();
        MetaModel.getInstance().setPackageBase(basePackage);

        final GeneratorOptions generatorOptions = new GeneratorOptions("C:/Users/Marko/Desktop/gen", "pom", "src/main/resources/templates", "{0}.xml", true, null);
        final PomGenerator pomGenerator = new PomGenerator(generatorOptions, metaAppInfo);
        pomGenerator.generate();

        generatorOptions.setTemplateName("application");
        generatorOptions.setOutputFileName("{0}.java");
        final ApplicationGenerator applicationGenerator = new ApplicationGenerator(generatorOptions, metaAppInfo);
        applicationGenerator.generate();

//        final MetaColumn metaColumn1 = new MetaColumn("id", "Integer", "private", 1, 1, true, null, true);
//        final MetaColumn metaColumn12 = new MetaColumn("id2", "Integer", "private", 1, 1, true, null, true);
        final MetaColumn metaColumn2 = MetaColumn.builder().name("username").type("String").visibility("private").lower(1).upper(1)
                .unique(true).relationshipType(null).partOfPrimaryKey(false).build();
        final MetaColumn metaColumn3 = MetaColumn.builder().name("password").type("String").visibility("private").lower(1).upper(1)
                .unique(false).relationshipType(null).partOfPrimaryKey(false).build();

        final MetaEntity metaEntity = MetaEntity.builder()
                .name("User")
                .visibility("public")
                .columns(new ArrayList<>())
                .build();
//        metaEntity.getColumns().add(metaColumn1);
//        metaEntity.getColumns().add(metaColumn12);
        metaEntity.getColumns().add(metaColumn2);
        metaEntity.getColumns().add(metaColumn3);

        final MetaEnumeration metaEnumeration = MetaEnumeration.builder()
                .name("UserType")
                .build();
        metaEnumeration.getValues().add("ADMINISTRATOR");
        metaEnumeration.getValues().add("AUTHOR");
        metaEnumeration.getValues().add("USER");

        generatorOptions.setTemplateDir("src/main/resources/templates/model");
        generatorOptions.setTemplateName("model");
        generatorOptions.setOutputFileName("{0}.java");
        final ModelGenerator modelGenerator = new ModelGenerator(generatorOptions, metaEntity);
        modelGenerator.generate();


        int pkColumnsCounter = 0;
        final List<MetaColumn> columns = new ArrayList<>();
        for (final MetaColumn column : metaEntity.getColumns()) {
            if (column.isPartOfPrimaryKey()) {
                pkColumnsCounter++;
                columns.add(column);
            }
        }
        if (pkColumnsCounter > 1) {
            generatorOptions.setTemplateName("embedded_key");
            final EmbeddedKeyGenerator embeddedKeyGenerator = new EmbeddedKeyGenerator(generatorOptions, metaEntity, columns);
            embeddedKeyGenerator.generate();
        }

        generatorOptions.setTemplateName("enum");
        final EnumGenerator enumGenerator = new EnumGenerator(generatorOptions, metaEnumeration);
        enumGenerator.generate();

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

        generatorOptions.setTemplateDir("src/main/resources/templates/service/impl");
        generatorOptions.setTemplateName("service_impl_base");
        generatorOptions.setOverwrite(true);
        final ServiceImplBaseGenerator serviceImplBaseGenerator = new ServiceImplBaseGenerator(generatorOptions, metaEntity);
        serviceImplBaseGenerator.generate();

        generatorOptions.setTemplateName("service_impl");
        generatorOptions.setOverwrite(false);
        final ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(generatorOptions, metaEntity);
        serviceImplGenerator.generate();

        generatorOptions.setTemplateDir("src/main/resources/templates/controller");
        generatorOptions.setTemplateName("controller_base");
        generatorOptions.setOverwrite(true);
        final ControllerBaseGenerator controllerBaseGenerator = new ControllerBaseGenerator(generatorOptions, metaEntity);
        controllerBaseGenerator.generate();

        generatorOptions.setTemplateName("controller");
        generatorOptions.setOverwrite(false);
        final ControllerGenerator controllerGenerator = new ControllerGenerator(generatorOptions, metaEntity);
        controllerGenerator.generate();

        generatorOptions.setTemplateDir("src/main/resources/templates/exception");
        generatorOptions.setTemplateName("no_entity_found_exception");
        generatorOptions.setOverwrite(true);
        final NoEntityFoundExceptionGenerator noEntityFoundExceptionGenerator = new NoEntityFoundExceptionGenerator(generatorOptions, metaEntity);
        noEntityFoundExceptionGenerator.generate();
    }
}
