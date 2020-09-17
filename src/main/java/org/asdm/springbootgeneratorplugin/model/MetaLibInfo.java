package org.asdm.springbootgeneratorplugin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaLibInfo {
    protected String groupId;
    protected String artifactId;
    protected String version;
}