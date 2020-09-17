package org.asdm.springbootgeneratorplugin.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MetaAppInfo {
    private MetaLibInfo info;
    private String springBootVersion;
    private PackagingType packaging;
    private String name;
    private String description;
    private String javaVersion;
    private List<MetaDependency> dependencies;

    public List<MetaDependency> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<>();
        }
        return this.dependencies;
    }
}