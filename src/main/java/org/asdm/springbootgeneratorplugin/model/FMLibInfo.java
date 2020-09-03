package org.asdm.springbootgeneratorplugin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FMLibInfo {
    private String groupId;
    private String artifactId;
    private String version;
}