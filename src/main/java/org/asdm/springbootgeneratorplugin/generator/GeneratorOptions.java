package org.asdm.springbootgeneratorplugin.generator;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * org.asdm.springbootgeneratorplugin.generator.GeneratorOptions: options used for code generation. Every generator (ejb generator, forms generator etc) should
 * have one instance of this class
 */

@Data
@AllArgsConstructor
public class GeneratorOptions {
    private String outputPath;
    private String templateName;
    private String templateDir;
    private String outputFileName;
    private Boolean overwrite;
    private String filePackage;
}
