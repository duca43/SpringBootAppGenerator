package org.asdm.springbootgeneratorplugin.analyzer;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import org.asdm.springbootgeneratorplugin.model.MetaColumn;
import org.asdm.springbootgeneratorplugin.model.MetaEntity;
import org.asdm.springbootgeneratorplugin.model.MetaEnumeration;
import org.asdm.springbootgeneratorplugin.model.MetaModel;

import java.util.Iterator;
import java.util.List;


public class ModelAnalyzer {
    //root model package
    private final Package root;

    //java root package for generated code
    private final String filePackage;

    public ModelAnalyzer(final Package root, final String filePackage) {
        super();
        this.root = root;
        this.filePackage = filePackage;
    }

    public Package getRoot() {
        return this.root;
    }

    public void prepareModel() throws AnalyzeException {
        MetaModel.getInstance().getEntities().clear();
        MetaModel.getInstance().getEnumerations().clear();
        this.processPackage(this.root, this.filePackage);
    }

    private void processPackage(final Package pack, final String packageOwner) throws AnalyzeException {
        if (pack.getName() == null) throw
                new AnalyzeException("Packages must have names!");

        String packageName = packageOwner;
        if (pack != this.root) {
            packageName += "." + pack.getName();
        }

        if (pack.hasOwnedElement()) {
            for (final Element ownedElement : pack.getOwnedElement()) {
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

            for (final Element ownedElement : pack.getOwnedElement()) {
                if (ownedElement instanceof Package) {
                    final Package ownedPackage = (Package) ownedElement;
                    this.processPackage(ownedPackage, packageName);
                }
            }
        }
    }

    private MetaEntity getClassData(final Class cl) throws AnalyzeException {
        if (cl.getName() == null)
            throw new AnalyzeException("Classes must have names!");

        final MetaEntity metaEntity = new MetaEntity(cl.getName(), cl.getVisibility().toString());
        final Iterator<Property> it = ModelHelper.attributes(cl);

        final Iterator<Association> associations = ModelHelper.associations(cl);
        RelationshipType relationshipType = RelationshipType.NONE;
        while (associations.hasNext()) {
            final Association association = associations.next();

            final Property firstMemberEnd = ModelHelper.getFirstMemberEnd(association);
            final Property secondMemberEnd = ModelHelper.getSecondMemberEnd(association);

            final int firstMemberEndUpper = firstMemberEnd.getUpper();
            final int secondMemberEndUpper = secondMemberEnd.getUpper();

            if (firstMemberEnd.getClassifier() != null && firstMemberEnd.getClassifier().getName().equals(cl.getName())) {
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

            if (secondMemberEnd.getClassifier() != null && secondMemberEnd.getClassifier().getName().equals(cl.getName())) {
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
            final MetaColumn metaColumn = this.getPropertyData(p, cl, relationshipType);
            metaEntity.getColumns().add(metaColumn);
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
            metaEntity.setPrimaryKeyType("Long");
            metaEntity.setPrimaryKeyName("id");
        } else if (pkColumnsCounter == 1) {
            metaEntity.setPrimaryKeyType(pkType);
            metaEntity.setPrimaryKeyName(pkName);
        } else {
            metaEntity.setPrimaryKeyType(metaEntity.getName() + "Id");
            metaEntity.setPrimaryKeyName("id");
        }

        return metaEntity;
    }

    private MetaColumn getPropertyData(final Property p, final Class cl, RelationshipType relationshipType) throws AnalyzeException {
        final String attName = p.getName();
        if (attName == null)
            throw new AnalyzeException("Properties of the class: " + cl.getName() +
                    " must have names!");
        final Type attType = p.getType();
        if (attType == null)
            throw new AnalyzeException("Property " + cl.getName() + "." +
                    p.getName() + " must have type!");

        final String typeName = attType.getName();
        if (typeName == null)
            throw new AnalyzeException("Type ot the property " + cl.getName() + "." +
                    p.getName() + " must have name!");

        final int lower = p.getLower();
        final int upper = p.getUpper();

        boolean isPartOfPrimaryKey = false;
        final List<Stereotype> stereotypes = StereotypesHelper.getStereotypes(p);
        if (!stereotypes.isEmpty()){
            for (Stereotype stereotype: stereotypes){
                if (stereotype.getName().equals("PrimaryKey")){
                    isPartOfPrimaryKey = true;
                }
            }
        }

        //TODO: Create stereotype enum

        return new MetaColumn(attName, typeName, p.getVisibility().toString(),
                lower, upper, p.isUnique(), relationshipType.toString(), isPartOfPrimaryKey);
    }

    private MetaEnumeration getEnumerationData(final Enumeration enumeration) throws AnalyzeException {
        final MetaEnumeration metaEnumeration = new MetaEnumeration(enumeration.getName());
        final List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
        for (int i = 0; i < list.size() - 1; i++) {
            final EnumerationLiteral literal = list.get(i);
            if (literal.getName() == null)
                throw new AnalyzeException("Items of the metaEnumeration " + metaEnumeration.getName() +
                        " must have names!");
            metaEnumeration.getValues().add(literal.getName());
        }
        return metaEnumeration;
    }


}
