package org.asdm.springbootgeneratorplugin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Version {
    private String versionName;
    private String concreteVersion;
}