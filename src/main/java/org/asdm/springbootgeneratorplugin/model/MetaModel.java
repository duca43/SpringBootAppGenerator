package org.asdm.springbootgeneratorplugin.model;

import lombok.Getter;
import lombok.Setter;
import org.asdm.springbootgeneratorplugin.analyzer.TypeMapping;
import org.asdm.springbootgeneratorplugin.generator.BasicGenerator;
import org.asdm.springbootgeneratorplugin.generator.GeneratorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MetaModel {

    private static MetaModel model;

    @Setter
    private MetaAppInfo metaAppInfo;

    @Setter
    private String packageBase;

    private final List<MetaEntity> entities = new ArrayList<>();
    private final List<MetaEnumeration> enumerations = new ArrayList<>();
    private final Map<String, TypeMapping> typeMappingMap = new HashMap<>();
    private final List<Version> javaVersions = new ArrayList<>();
    private final List<Version> springBootVersions = new ArrayList<>();
    private final Map<String, GeneratorOptions> generatorOptions = new HashMap<>();
    private final Map<String, Class<? extends BasicGenerator>> generatorClasses = new HashMap<>();
    private final Map<String, MetaDependency> sqlDrivers = new HashMap<>();

    private MetaModel() {
    }

    public static MetaModel getInstance() {
        if (model == null) {
            model = new MetaModel();
        }
        return model;
    }
}