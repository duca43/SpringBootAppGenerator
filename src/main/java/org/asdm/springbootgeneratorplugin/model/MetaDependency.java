package org.asdm.springbootgeneratorplugin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MetaDependency extends MetaLibInfo {
    private String scope;

    public MetaDependency(final String groupId, final String artifactId, final String version, final String scope) {
        super(groupId, artifactId, version);
        this.scope = scope;
    }
}