package org.asdm.springbootgeneratorplugin.util;

import org.asdm.springbootgeneratorplugin.analyzer.TypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlParser {
    public static Map<String, TypeMapping> parseTypeMappingXml() {
        try {
            Map<String, TypeMapping> typeMappingMap = new HashMap<>();
            File file = new File("src/main/resources/type_mapping.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("typeMapping");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    TypeMapping typeMapping = TypeMapping.builder()
                            .modelType(element.getElementsByTagName("modelType").item(0).getTextContent())
                            .destinationType(element.getElementsByTagName("destinationType").item(0).getTextContent())
                            .build();

                    if (element.getElementsByTagName("libraryName").getLength() != 0){
                        typeMapping.setLibraryName(element.getElementsByTagName("libraryName").item(0).getTextContent());
                    }
                    typeMappingMap.put(typeMapping.getModelType(), typeMapping);
                }
            }

            return typeMappingMap;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
