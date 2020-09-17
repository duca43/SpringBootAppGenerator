package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Abstract generator that creates necessary environment for code generation
 * (creating directory for code generation, fetching template, creating file with given name
 * for code generation etc). It should be ancestor for all generators in this project.
 */

@Data
@Slf4j
public abstract class BasicGenerator {

    private final GeneratorOptions generatorOptions;
    private Configuration configuration;
    private Template template;
    protected String outputPath;

    public BasicGenerator(final GeneratorOptions generatorOptions, final String outputPath) {
        this.generatorOptions = generatorOptions;
        this.outputPath = outputPath;
    }

    public void generate() throws IOException {
        if (this.outputPath == null) {
            throw new IOException("Output path is not defined!");
        }
        if (this.generatorOptions.getTemplateName() == null) {
            throw new IOException("Template name is not defined!");
        }
        if (this.generatorOptions.getOutputFileName() == null) {
            throw new IOException("Output file name is not defined!");
        }

        this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        final String templateFilename = this.generatorOptions.getTemplateName() + ".ftl";
        try {
            final File dir = new File(this.generatorOptions.getTemplateDir());
            log.info("Template path: {}", dir.getAbsolutePath());
            this.configuration.setDirectoryForTemplateLoading(dir);
            this.template = this.configuration.getTemplate(templateFilename);
            final DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(this.configuration.getIncompatibleImprovements());
            this.configuration.setObjectWrapper(builder.build());
            final File file = new File(this.outputPath);
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException("An error occurred during creation of following folder: " + this.outputPath);
            }
        } catch (final IOException e) {
            throw new IOException("Can't find template " + templateFilename + ".", e);
        }
    }

    public Writer getWriter(final String filename) throws IOException {
        final String fullPath = this.outputPath
                + File.separator
                + this.generatorOptions.getFilePackage()
                + File.separator
                + this.generatorOptions.getOutputFileName().replace("{0}", filename);

        final File file = new File(fullPath);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("An error occurred during creation of following folder: " + this.outputPath);
        }

        log.info("File path: {}", file.getPath());
        log.info("File name: {}", file.getName());

        if (!this.generatorOptions.getOverwrite() && file.exists()) {
            return null;
        }

        return new OutputStreamWriter(new FileOutputStream(file));
    }
}