package org.asdm.springbootgeneratorplugin.model;

import lombok.Builder;
import lombok.Data;
import org.asdm.springbootgeneratorplugin.generator.BasicGenerator;

@Data
@Builder
public class Template {
    private String name;
    private boolean overwrite;
    private String extension;
    private String dir;
    private String pckg;
    private Class<? extends BasicGenerator> generatorClass;
}