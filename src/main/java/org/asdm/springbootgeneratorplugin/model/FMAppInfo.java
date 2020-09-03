package org.asdm.springbootgeneratorplugin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FMAppInfo {
    private FMLibInfo info;
    private String name;
    private String description;
    private Double javaVersion;
    private List<FMLibInfo> dependencies;
}