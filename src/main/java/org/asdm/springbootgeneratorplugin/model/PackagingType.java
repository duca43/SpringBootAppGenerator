package org.asdm.springbootgeneratorplugin.model;

public enum PackagingType {
    JAR("jar"),
    WAR("war");

    private final String name;

    PackagingType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}