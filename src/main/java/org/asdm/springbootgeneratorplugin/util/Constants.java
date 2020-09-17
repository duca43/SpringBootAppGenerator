package org.asdm.springbootgeneratorplugin.util;

import java.awt.*;
import java.io.File;

public class Constants {

    //Strings
    public static final String GENERATE_ACTION_ID = "GENERATE_ID";
    public static final String GENERATE_ACTION = "Generate Spring Boot app";
    public static final String GENERATE_ACTION_DESC = "Generate current ER model as Spring Boot app";
    public static final String TOOLS_CATEGORY = "TOOLS";
    public static final String PATH_TO_THEME = "themes/cyan.theme.json";
    public static final String PATH_TO_GENERATE_ICON = "images/generate_icon.png";
    public static final String ERROR_MESSAGE_TITLE = "Error!";
    public static final String NO_MODEL_MESSAGE = "Please create/open model you want to generate Spring Boot project for.";
    public static final String INITIAL_APP_VERSION = "0.0.1-SNAPSHOT";
    public static final String SPRING_BOOT_TITLE = "Spring Boot";
    public static final String PROJECT_METADATA_TITLE = "Project Metadata";
    public static final String GROUP_TITLE = "Group";
    public static final String ARTIFACT_TITLE = "Artifact";
    public static final String NAME_TITLE = "Name";
    public static final String DESCRIPTION_TITLE = "Description";
    public static final String SQL_DRIVER_TITLE = "SQL driver";
    public static final String OUTPUT_TITLE = "Output folder";
    public static final String BROWSE = "Browse...";
    public static final String PACKAGING_TITLE = "Packaging";
    public static final String JAVA_TITLE = "Java";
    public static final String GENERATE = "Generate";
    public static final String CANCEL = "Cancel";
    public static final String GENERATE_DIALOG_TITLE = "Spring Boot app generator";
    public static final String PACKAGES_NAME_MESSAGE = "Each package must have name!";
    public static final String APP_TYPE = "BusinessApp";
    public static final String CLASSES_NAME_MESSAGE = "Each class must have name!";
    public static final String LONG_CLASS = "Long";
    public static final String ID = "id";
    public static final String PK_STEREOTYPE = "PrimaryKey";
    public static final String TYPE_MAPPING_FILENAME = "data/type_mapping.xml";
    public static final String TYPE_MAPPING_TAG = "typeMapping";
    public static final String MODEL_TYPE_TAG = "modelType";
    public static final String DESTINATION_TYPE_TAG = "destinationType";
    public static final String LIB_NAME_TAG = "libraryName";
    public static final String JAVA_VERSIONS_FILENAME = "data/java_versions.xml";
    public static final String SPRING_BOOT_VERSIONS_FILENAME = "data/spring_boot_versions.xml";
    public static final String VERSION_TAG = "version";
    public static final String VERSION_NAME_TAG = "versionName";
    public static final String CONCRETE_VERSION_TAG = "concreteVersion";
    public static final String HOME_DIR_PROPERTY_KEY = "user.home";
    public static final String TEMPLATES_FILENAME = "data/templates.xml";
    public static final String TEMPLATE_TAG = "template";
    public static final String NAME_TAG = "name";
    public static final String OVERWRITE_TAG = "overwrite";
    public static final String EXTENSION_TAG = "extension";
    public static final String DIR_TAG = "dir";
    public static final String PACKAGE_TAG = "package";
    public static final String GENERATOR_CLASS_TAG = "generatorClass";
    public static final String SQL_DRIVERS_FILENAME = "data/sql_drivers.xml";
    public static final String DEPENDENCY_TAG = "dependency";
    public static final String DISPLAY_NAME_TAG = "displayName";
    public static final String GROUP_ID_TAG = "groupId";
    public static final String ARTIFACT_ID_TAG = "artifactId";
    public static final String SCOPE_TAG = "scope";
    public static final String RESOURCES_PATH = "src" + File.separator + "main" + File.separator + "resources";
    public static final String SRC_PATH = "src" + File.separator + "main" + File.separator + "java";
    public static final String JAVA = "java";
    public static final String DEFAULT_GROUP = "com.example";
    public static final String DEFAULT_ARTIFACT_AND_NAME = "demo";
    public static final String DEFAULT_DESCRIPTION = "Generated Spring Boot project";
    public static final String SUCCESS_MESSAGE_TITLE = "Success";
    public static final String GENERATE_SUCCESS_MESSAGE = "Spring Boot project is generated successfully!";
    public static final String POM_FILENAME = "pom";

    //Integers
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
}