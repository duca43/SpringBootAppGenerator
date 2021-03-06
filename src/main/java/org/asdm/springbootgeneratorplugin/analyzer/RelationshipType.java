package org.asdm.springbootgeneratorplugin.analyzer;

public enum RelationshipType {
    NONE(null),
    ONE_TO_ONE("OneToOne"),
    ONE_TO_MANY("OneToMany"),
    MANY_TO_ONE("ManyToOne"),
    MANY_TO_MANY("ManyToMany");

    private final String name;

    RelationshipType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
