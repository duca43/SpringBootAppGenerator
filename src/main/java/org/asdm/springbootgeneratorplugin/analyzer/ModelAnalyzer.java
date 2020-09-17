package org.asdm.springbootgeneratorplugin.analyzer;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import org.asdm.springbootgeneratorplugin.model.MetaColumn;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaEnumeration;
import org.asdm.springbootgeneratorplugin.model.MetaModel;
import org.asdm.springbootgeneratorplugin.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        final Iterator<Property> it = ModelHelper.attributes(clazz);

        this.libraries.clear();

        final Iterator<Association> associations = ModelHelper.associations(clazz);
        RelationshipType relationshipType = RelationshipType.NONE;
        while (associations.hasNext()) {
            final Association association = associations.next();

            final Property firstMemberEnd = ModelHelper.getFirstMemberEnd(association);
            final Property secondMemberEnd = ModelHelper.getSecondMemberEnd(association);

            final int firstMemberEndUpper = firstMemberEnd.getUpper();
            final int secondMemberEndUpper = secondMemberEnd.getUpper();

            if (firstMemberEnd.getClassifier() != null && firstMemberEnd.getClassifier().getName().equals(clazz.getName())) {
                if (firstMemberEndUpper == -1 && secondMemberEndUpper == -1) {
                    relationshipType = RelationshipType.MANY_TO_MANY;
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == 1) {
                    relationshipType = RelationshipType.ONE_TO_ONE;
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == -1) {
                    relationshipType = RelationshipType.MANY_TO_ONE;
                } else {
                    relationshipType = RelationshipType.ONE_TO_MANY;
                }
            }

            if (secondMemberEnd.getClassifier() != null && secondMemberEnd.getClassifier().getName().equals(clazz.getName())) {
                if (firstMemberEndUpper == -1 && secondMemberEndUpper == -1) {
                    relationshipType = RelationshipType.MANY_TO_MANY;
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == 1) {
                    relationshipType = RelationshipType.ONE_TO_ONE;
                } else if (firstMemberEndUpper == 1 && secondMemberEndUpper == -1) {
                    relationshipType = RelationshipType.ONE_TO_MANY;
                } else {
                    relationshipType = RelationshipType.MANY_TO_ONE;
                }
            }
        }

        while (it.hasNext()) {
            final Property p = it.next();
            final MetaColumn metaColumn = this.getPropertyData(p, clazz, relationshipType);
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

    private MetaColumn getPropertyData(final Property property, final Class clazz, final RelationshipType relationshipType) throws AnalyzeException {
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

        //TODO: Create stereotype enum

        return MetaColumn.builder()
                .name(attName)
                .type(typeName)
                .visibility(property.getVisibility().toString())
                .lower(lower)
                .upper(upper)
                .unique(property.isUnique())
                .relationshipType(relationshipType.name())
                .partOfPrimaryKey(isPartOfPrimaryKey)
                .build();
    }

    private MetaEnumeration getEnumerationData(final Enumeration enumeration) throws AnalyzeException {
        final MetaEnumeration metaEnumeration = MetaEnumeration.builder()
                .name(enumeration.getName())
                .build();
        final List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
        for (int i = 0; i < list.size() - 1; i++) {
            final EnumerationLiteral literal = list.get(i);
            if (literal.getName() == null) {
                throw new AnalyzeException("Items of the metaEnumeration " + metaEnumeration.getName() + " must have names!");
            }
            metaEnumeration.getValues().add(literal.getName());
        }
        return metaEnumeration;
    }
}