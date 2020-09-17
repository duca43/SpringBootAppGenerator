package org.asdm.springbootgeneratorplugin.util;

import org.apache.xerces.parsers.XMLParser;
import org.asdm.springbootgeneratorplugin.analyzer.TypeMapping;
import org.asdm.springbootgeneratorplugin.generator.BasicGenerator;
import org.asdm.springbootgeneratorplugin.model.MetaDependency;
import org.asdm.springbootgeneratorplugin.model.Template;
import org.asdm.springbootgeneratorplugin.model.Version;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParser {
    public static Map<String, TypeMapping> parseTypeMappingXml() throws IOException, SAXException, ParserConfigurationException {
        final NodeList nodeList = getNodeListFromXml(Constants.TYPE_MAPPING_FILENAME, Constants.TYPE_MAPPING_TAG);
        if (nodeList == null) {
            return null;
        }
        final Map<String, TypeMapping> typeMappingMap = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;
                final TypeMapping typeMapping = TypeMapping.builder()
                        .modelType(element.getElementsByTagName(Constants.MODEL_TYPE_TAG).item(0).getTextContent())
                        .destinationType(element.getElementsByTagName(Constants.DESTINATION_TYPE_TAG).item(0).getTextContent())
                        .build();

                if (element.getElementsByTagName(Constants.LIB_NAME_TAG).getLength() != 0) {
                    typeMapping.setLibraryName(element.getElementsByTagName(Constants.LIB_NAME_TAG).item(0).getTextContent());
                }
                typeMappingMap.put(typeMapping.getModelType(), typeMapping);
            }
        }
        return typeMappingMap;
    }

    public static List<Version> parseVersionsXml(final String filename) throws IOException, SAXException, ParserConfigurationException {
        final NodeList nodeList = getNodeListFromXml(filename, Constants.VERSION_TAG);
        if (nodeList == null) {
            return null;
        }
        final List<Version> versions = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;
                final Version version = Version.builder()
                        .versionName(element.getElementsByTagName(Constants.VERSION_NAME_TAG).item(0).getTextContent())
                        .concreteVersion(element.getElementsByTagName(Constants.CONCRETE_VERSION_TAG).item(0).getTextContent())
                        .build();

                versions.add(version);
            }
        }
        return versions;
    }

    public static List<Template> parseTemplatesXml() throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
        final NodeList nodeList = getNodeListFromXml(Constants.TEMPLATES_FILENAME, Constants.TEMPLATE_TAG);
        if (nodeList == null) {
            return null;
        }
        final List<Template> templates = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;

                final NodeList packageElements = element.getElementsByTagName(Constants.PACKAGE_TAG);
                String pckg = "";
                if (packageElements.getLength() > 0) {
                    pckg = packageElements.item(0).getTextContent();
                }

                final Class<?> clazz = Class.forName(element.getElementsByTagName(Constants.GENERATOR_CLASS_TAG).item(0).getTextContent());
                final Class<? extends BasicGenerator> generatorClass = (Class<? extends BasicGenerator>) clazz;

                final Template template = Template.builder()
                        .name(element.getElementsByTagName(Constants.NAME_TAG).item(0).getTextContent())
                        .overwrite(Boolean.parseBoolean(element.getElementsByTagName(Constants.OVERWRITE_TAG).item(0).getTextContent()))
                        .extension(element.getElementsByTagName(Constants.EXTENSION_TAG).item(0).getTextContent())
                        .dir(element.getElementsByTagName(Constants.DIR_TAG).item(0).getTextContent())
                        .pckg(pckg)
                        .generatorClass(generatorClass)
                        .build();

                templates.add(template);
            }
        }
        return templates;
    }

    public static Map<String, MetaDependency> parseSqlDriversXml() throws IOException, SAXException, ParserConfigurationException {
        final NodeList nodeList = getNodeListFromXml(Constants.SQL_DRIVERS_FILENAME, Constants.DEPENDENCY_TAG);
        if (nodeList == null) {
            return null;
        }
        final Map<String, MetaDependency> sqlDrivers = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;
                final MetaDependency metaDependency = new MetaDependency(element.getElementsByTagName(Constants.GROUP_ID_TAG).item(0).getTextContent(),
                        element.getElementsByTagName(Constants.ARTIFACT_ID_TAG).item(0).getTextContent(),
                        null,
                        element.getElementsByTagName(Constants.SCOPE_TAG).item(0).getTextContent());

                sqlDrivers.put(element.getElementsByTagName(Constants.DISPLAY_NAME_TAG).item(0).getTextContent(), metaDependency);
            }
        }
        return sqlDrivers;
    }

    public static NodeList getNodeListFromXml(final String resourceName, final String tagName) throws ParserConfigurationException, IOException, SAXException {
        final InputStream resource = XMLParser.class.getClassLoader().getResourceAsStream(resourceName);
        if (resource == null) {
            return null;
        }
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(resource);
        document.getDocumentElement().normalize();
        return document.getElementsByTagName(tagName);
    }
}