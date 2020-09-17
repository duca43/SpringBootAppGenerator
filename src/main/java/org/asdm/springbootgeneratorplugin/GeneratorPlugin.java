package org.asdm.springbootgeneratorplugin;

import com.formdev.flatlaf.IntelliJTheme;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import lombok.SneakyThrows;
import org.asdm.springbootgeneratorplugin.action.GenerateAction;
import org.asdm.springbootgeneratorplugin.analyzer.TypeMapping;
import org.asdm.springbootgeneratorplugin.configurator.MainMenuConfigurator;
import org.asdm.springbootgeneratorplugin.model.MetaDependency;
import org.asdm.springbootgeneratorplugin.model.MetaModel;
import org.asdm.springbootgeneratorplugin.model.Version;
import org.asdm.springbootgeneratorplugin.util.Constants;
import org.asdm.springbootgeneratorplugin.util.XmlParser;

import java.util.List;
import java.util.Map;

public class GeneratorPlugin extends com.nomagic.magicdraw.plugins.Plugin {

    @SneakyThrows
    @Override
    public void init() {
        IntelliJTheme.install(GeneratorPlugin.class.getClassLoader().getResourceAsStream(Constants.PATH_TO_THEME));

        final ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
        manager.addMainMenuConfigurator(new MainMenuConfigurator(this.pluginActions()));

        final Map<String, TypeMapping> typeMappingMap = XmlParser.parseTypeMappingXml();
        if (typeMappingMap != null) {
            MetaModel.getInstance().getTypeMappingMap().putAll(typeMappingMap);
        }

        final List<Version> javaVersions = XmlParser.parseVersionsXml(Constants.JAVA_VERSIONS_FILENAME);
        if (javaVersions != null) {
            MetaModel.getInstance().getJavaVersions().addAll(javaVersions);
        }

        final List<Version> springBootVersions = XmlParser.parseVersionsXml(Constants.SPRING_BOOT_VERSIONS_FILENAME);
        if (springBootVersions != null) {
            MetaModel.getInstance().getSpringBootVersions().addAll(springBootVersions);
        }

        final Map<String, MetaDependency> sqlDrivers = XmlParser.parseSqlDriversXml();
        if (sqlDrivers != null) {
            MetaModel.getInstance().getSqlDrivers().putAll(sqlDrivers);
        }
    }

    private NMAction[] pluginActions() {
        final GenerateAction generateAction = new GenerateAction();
        generateAction.setEnabled(false);
        return new NMAction[]{generateAction};
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public boolean isSupported() {
        return true;
    }
}