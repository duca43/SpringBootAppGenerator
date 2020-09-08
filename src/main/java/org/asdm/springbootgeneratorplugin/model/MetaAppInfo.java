package org.asdm.springbootgeneratorplugin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MetaAppInfo {
    private MetaLibInfo info;
    private String name;
    private String description;
    private Double javaVersion;
    private List<MetaLibInfo> dependencies = new ArrayList<>();

    public MetaAppInfo(final MetaLibInfo info, final String name, final String description, final Double javaVersion) {
        this.info = info;
        this.name = name;
        this.description = description;
        this.javaVersion = javaVersion;
    }
}