package org.asdm.springbootgeneratorplugin.util;

import lombok.extern.slf4j.Slf4j;
import org.asdm.springbootgeneratorplugin.generator.BasicGenerator;
import org.asdm.springbootgeneratorplugin.generator.GeneratorOptions;
import org.asdm.springbootgeneratorplugin.model.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GeneratorUtil {

    public static void runGeneratorsForEntity(String outputPath) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException, SAXException, ParserConfigurationException, ClassNotFoundException {
        if (outputPath == null || outputPath.isEmpty()) {
            outputPath = System.getProperty(Constants.HOME_DIR_PROPERTY_KEY);
        }

        GeneratorUtil.initGeneratorOptions();

        log.info("Generating Spring Boot project to following path: {}", outputPath);

        final Map<String, GeneratorOptions> generatorOptions = MetaModel.getInstance().getGeneratorOptions();

        for (final Map.Entry<String, Class<? extends BasicGenerator>> entry : MetaModel.getInstance().getGeneratorClasses().entrySet()) {
            final Constructor<?> constructor = entry.getValue().getConstructors()[0];
            if (constructor.getParameterCount() == 2) {
                if (constructor.getParameterTypes()[0].equals(GeneratorOptions.class)
                        && constructor.getParameterTypes()[1].equals(String.class)) {
                    final BasicGenerator generator = (BasicGenerator) constructor.newInstance(generatorOptions.get(entry.getKey()), outputPath);
                    generator.generate();
                }
            }
        }

        for (final MetaEntity metaEntity : MetaModel.getInstance().getEntities()) {
            log.info("Generating code for following entity: {}", metaEntity.getName());
            for (final Map.Entry<String, Class<? extends BasicGenerator>> entry : MetaModel.getInstance().getGeneratorClasses().entrySet()) {
                final Constructor<?> constructor = entry.getValue().getConstructors()[0];
                if (constructor.getParameterCount() == 3) {
                    if (constructor.getParameterTypes()[0].equals(GeneratorOptions.class) &&
                            constructor.getParameterTypes()[1].equals(MetaEntity.class) &&
                            constructor.getParameterTypes()[2].equals(String.class)) {
                        final BasicGenerator generator
                                = (BasicGenerator) constructor.newInstance(generatorOptions.get(entry.getKey()), metaEntity, outputPath);
                        generator.generate();
                    }
                } else if (constructor.getParameterCount() == 4) {
                    if (constructor.getParameterTypes()[0].equals(GeneratorOptions.class) &&
                            constructor.getParameterTypes()[1].equals(MetaEntity.class) &&
                            constructor.getParameterTypes()[2].equals(List.class) &&
                            constructor.getParameterTypes()[3].equals(String.class)) {
                        int pkColumnsCounter = 0;
                        final List<MetaColumn> columns = new ArrayList<>();
                        for (final MetaColumn column : metaEntity.getColumns()) {
                            if (column.isPartOfPrimaryKey()) {
                                pkColumnsCounter++;
                                columns.add(column);
                            }
                        }
                        if (pkColumnsCounter > 1) {
                            final BasicGenerator generator
                                    = (BasicGenerator) constructor.newInstance(generatorOptions.get(entry.getKey()), metaEntity, columns, outputPath);
                            generator.generate();
                        }
                    }
                }
            }
            log.info("Generating code for entity {} has finished", metaEntity.getName());
        }

        for (final MetaEnumeration metaEnumeration : MetaModel.getInstance().getEnumerations()) {
            log.info("Generating code for following enumeration: {}", metaEnumeration.getName());
            for (final Map.Entry<String, Class<? extends BasicGenerator>> entry : MetaModel.getInstance().getGeneratorClasses().entrySet()) {
                final Constructor<?> constructor = entry.getValue().getConstructors()[0];
                if (constructor.getParameterCount() == 3) {
                    if (constructor.getParameterTypes()[0].equals(GeneratorOptions.class) &&
                            constructor.getParameterTypes()[1].equals(MetaEnumeration.class) &&
                            constructor.getParameterTypes()[2].equals(String.class)) {
                        final BasicGenerator generator
                                = (BasicGenerator) constructor.newInstance(generatorOptions.get(entry.getKey()), metaEnumeration, outputPath);
                        generator.generate();
                    }
                }
            }
            log.info("Generating code for enumeration {} has finished", metaEnumeration.getName());
        }

        log.info("Generating process has finished");
    }

    public static void initGeneratorOptions() throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {
        final List<Template> templates = XmlParser.parseTemplatesXml();

        if (templates == null) {
            return;
        }

        MetaModel.getInstance().getGeneratorOptions().clear();
        MetaModel.getInstance().getGeneratorClasses().clear();

        templates.forEach(template -> {
            final GeneratorOptions generatorOptions = new GeneratorOptions(template.getName(),
                    Constants.RESOURCES_PATH + File.separator + template.getDir(),
                    "{0}." + template.getExtension(),
                    template.isOverwrite());
            final String filePackage;
            if (template.getExtension().equals(Constants.JAVA)) {
                filePackage = MetaModel.getInstance().getMetaAppInfo().getName()
                        + File.separator
                        + Constants.SRC_PATH
                        + File.separator
                        + StringUtil.transformPackageToPath(MetaModel.getInstance().getPackageBase())
                        + File.separator
                        + template.getPckg();
            } else {
                filePackage = MetaModel.getInstance().getMetaAppInfo().getName()
                        + File.separator
                        + template.getPckg();
            }

            generatorOptions.setFilePackage(filePackage);

            MetaModel.getInstance().getGeneratorOptions().put(template.getName(), generatorOptions);
            MetaModel.getInstance().getGeneratorClasses().put(template.getName(), template.getGeneratorClass());
        });
    }
}