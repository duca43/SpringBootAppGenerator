package org.asdm.springbootgeneratorplugin.analyzer;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import org.asdm.springbootgeneratorplugin.model.MetaColumn;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaEnumeration;
import org.asdm.springbootgeneratorplugin.model.MetaModel;
import org.asdm.springbootgeneratorplugin.util.Constants;

import java.util.*;

public class ModelAnalyzer {
    private final Package root;
    private final List<String> libraries;

    public ModelAnalyzer(final Package root) {
        this.root = root;
        this.libraries = new ArrayList<>();
    }

    public void prepareModel() throws AnalyzeException {
        MetaModel.getInstance().getEntities().clear();
        MetaModel.getInstance().getEnumerations().clear();
        this.processPackage(this.root);
    }

    private void processPackage(final Package pckg) throws AnalyzeException {
        if (pckg.getName() == null) {
            throw new AnalyzeException(Constants.PACKAGES_NAME_MESSAGE);
        }

        if (pckg.hasOwnedElement()) {
            for (final Element ownedElement : pckg.getOwnedElement()) {
                if (ownedElement instanceof Class) {
                    final Class cl = (Class) ownedElement;
                    final MetaEntity metaEntity = this.getClassData(cl);
                    MetaModel.getInstance().getEntities().add(metaEntity);
                }
                if (ownedElement instanceof Enumeration) {
                    final Enumeration enumeration = (Enumeration) ownedElement;
                    final MetaEnumeration metaEnumeration = this.getEnumerationData(enumeration);
                    MetaModel.getInstance().getEnumerations().add(metaEnumeration);
                }
            }

            for (final Element ownedElement : pckg.getOwnedElement()) {
                if (ownedElement instanceof Package) {
                    final Package ownedPackage = (Package) ownedElement;
                    if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, Constants.APP_TYPE) != null)
                        this.processPackage(ownedPackage);
                }
            }
        }
    }

    private MetaEntity getClassData(final Class clazz) throws AnalyzeException {
        if (clazz.getName() == null) {
            throw new AnalyzeException(Constants.CLASSES_NAME_MESSAGE);
        }

        final MetaEntity metaEntity = MetaEntity.builder()
                .name(clazz.getName())
                .visibility(clazz.getVisibility().toString())
                .columns(new ArrayList<>())
                .imports(new ArrayList<>())
                .build();

        final Iterator<Property> propertyIterator = ModelHelper.attributes(clazz);

        this.libraries.clear();

        final Iterator<Association> associations = ModelHelper.associations(clazz);
        final Map<Property, RelationshipType> relationshipTypeMap = new HashMap<>();
        final Map<Property, String> relationshipOwnerMap = new HashMap<>();
        while (associations.hasNext()) {
            final Association association = associations.next();

            final Property firstMemberEnd = ModelHelper.getFirstMemberEnd(association);
            final Property secondMemberEnd = ModelHelper.getSecondMemberEnd(association);

            final int firstMemberEndUpper = firstMemberEnd.getUpper();
            final int secondMemberEndUpper = secondMemberEnd.getUpper();

            if (firstMemberEnd.getClassifier() != null && firstMemberEnd.getClassifier().getName().equals(clazz.getName())) {
                if (firstMemberEndUpper == -1 && secondMemberEndUpper == -1) {
                    relationshipTypeMap.put(firstMemberEnd, RelationshipType.MANY_TO_MANY);
                    relationshipOwnerMap.put(firstMemberEnd, secondMemberEnd.getName());
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == 1) {
                    relationshipTypeMap.put(firstMemberEnd, RelationshipType.ONE_TO_ONE);
                    relationshipOwnerMap.put(firstMemberEnd, null);
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == -1) {
                    relationshipTypeMap.put(firstMemberEnd, RelationshipType.MANY_TO_ONE);
                    relationshipOwnerMap.put(firstMemberEnd, null);
                } else {
                    relationshipTypeMap.put(firstMemberEnd, RelationshipType.ONE_TO_MANY);
                    relationshipOwnerMap.put(firstMemberEnd, secondMemberEnd.getName());
                }
            }

            if (secondMemberEnd.getClassifier() != null && secondMemberEnd.getClassifier().getName().equals(clazz.getName())) {
                if (firstMemberEndUpper == -1 && secondMemberEndUpper == -1) {
                    relationshipTypeMap.put(secondMemberEnd, RelationshipType.MANY_TO_MANY);
                    relationshipOwnerMap.put(secondMemberEnd, null);
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == 1) {
                    relationshipTypeMap.put(secondMemberEnd, RelationshipType.ONE_TO_ONE);
                    relationshipOwnerMap.put(secondMemberEnd, null);
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == -1) {
                    relationshipTypeMap.put(secondMemberEnd, RelationshipType.ONE_TO_MANY);
                    relationshipOwnerMap.put(secondMemberEnd, firstMemberEnd.getName());
                } else {
                    relationshipTypeMap.put(secondMemberEnd, RelationshipType.MANY_TO_ONE);
                    relationshipOwnerMap.put(secondMemberEnd, null);
                }
            }
        }

        while (propertyIterator.hasNext()) {
            final Property property = propertyIterator.next();
            RelationshipType relationshipType = RelationshipType.NONE;
            String relationshipOwner = null;
            if (relationshipTypeMap.containsKey(property)) {
                relationshipType = relationshipTypeMap.get(property);
            }
            if (relationshipOwnerMap.containsKey(property)) {
                relationshipOwner = relationshipOwnerMap.get(property);
            }
            final MetaColumn metaColumn = this.getPropertyData(property, clazz, relationshipType, relationshipOwner);
            metaEntity.getColumns().add(metaColumn);
        }

        for (final String library : this.libraries) {
            metaEntity.getImports().add(library);
        }

        int pkColumnsCounter = 0;
        String pkType = "";
        String pkName = "";
        for (final MetaColumn metaColumn : metaEntity.getColumns()) {
            if (metaColumn.isPartOfPrimaryKey()) {
                pkColumnsCounter++;
                pkType = metaColumn.getType();
                pkName = metaColumn.getName();
            }
        }

        metaEntity.setPrimaryKeyColumnCounter(pkColumnsCounter);

        if (pkColumnsCounter == 0) {
            metaEntity.setPrimaryKeyType(Constants.LONG_CLASS);
            metaEntity.setPrimaryKeyName(Constants.ID);
        } else if (pkColumnsCounter == 1) {
            metaEntity.setPrimaryKeyType(pkType);
            metaEntity.setPrimaryKeyName(pkName);
        } else {
            metaEntity.setPrimaryKeyType(metaEntity.getName() + Constants.ID.substring(0, 1).toUpperCase() + Constants.ID.substring(1));
            metaEntity.setPrimaryKeyName(Constants.ID);
        }

        return metaEntity;
    }

    private MetaColumn getPropertyData(final Property property, final Class clazz, final RelationshipType relationshipType, final String relationshipOwner) throws AnalyzeException {
        final String attName = property.getName();
        if (attName == null) {
            throw new AnalyzeException("Properties of the class: " + clazz.getName() + " must have name!");
        }
        final Type attributeType = property.getType();
        if (attributeType == null) {
            throw new AnalyzeException("Property " + clazz.getName() + "." + property.getName() + " must have type!");
        }

        String typeName = attributeType.getName();
        if (typeName == null) {
            throw new AnalyzeException("Type ot the property " + clazz.getName() + "." + property.getName() + " must have name!");
        }

        final int lower = property.getLower();
        final int upper = property.getUpper();

        if (MetaModel.getInstance().getTypeMappingMap().containsKey(typeName)) {
            final TypeMapping typeMapping = MetaModel.getInstance().getTypeMappingMap().get(typeName);
            typeName = typeMapping.getDestinationType();
            if (typeMapping.getLibraryName() != null) {
                if (!this.libraries.contains(typeMapping.getLibraryName())) {
                    this.libraries.add(typeMapping.getLibraryName());
                }
            }
        }

        boolean isPartOfPrimaryKey = false;
        final List<Stereotype> stereotypes = StereotypesHelper.getStereotypes(property);
        if (!stereotypes.isEmpty()) {
            for (final Stereotype stereotype : stereotypes) {
                if (stereotype.getName().equals(Constants.PK_STEREOTYPE)) {
                    isPartOfPrimaryKey = true;
                }
            }
        }

        return MetaColumn.builder()
                .name(attName)
                .type(typeName)
                .visibility(property.getVisibility().toString())
                .lower(lower)
                .upper(upper)
                .unique(property.isUnique())
                .relationshipType(relationshipType.toString())
                .relationshipOwner(relationshipOwner)
                .partOfPrimaryKey(isPartOfPrimaryKey)
                .build();
    }

    private MetaEnumeration getEnumerationData(final Enumeration enumeration) throws AnalyzeException {
        final MetaEnumeration metaEnumeration = MetaEnumeration.builder()
                .name(enumeration.getName())
                .build();
        final List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
        for (int i = 0; i < list.size(); i++) {
            final EnumerationLiteral literal = list.get(i);
            if (literal.getName() == null) {
                throw new AnalyzeException("Items of the metaEnumeration " + metaEnumeration.getName() + " must have names!");
            }
            metaEnumeration.getValues().add(literal.getName());
        }
        return metaEnumeration;
    }
}