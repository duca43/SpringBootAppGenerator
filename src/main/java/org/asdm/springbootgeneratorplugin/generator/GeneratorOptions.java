package org.asdm.springbootgeneratorplugin.generator;

import lombok.Data;

/**
 * Generator options used for code generation.
 * Each generator should have one instance of this class
 */

@Data
public class GeneratorOptions {
    private String templateName;
    private String templateDir;
    private String outputFileName;
    private String filePackage;
    private Boolean overwrite;

    public GeneratorOptions(final String templateName, final String templateDir, final String outputFileName, final Boolean overwrite) {
        this.templateName = templateName;
        this.templateDir = templateDir;
        this.outputFileName = outputFileName;
        this.overwrite = overwrite;
    }
}