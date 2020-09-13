package org.asdm.springbootgeneratorplugin.generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import lombok.Data;

import java.io.*;

/**
 * Abstract generator that creates necessary environment for code generation
 * (creating directory for code generation, fetching template, creating file with given name
 * for code generation etc). It should be ancestor for all generators in this project.
 */

@Data
public abstract class BasicGenerator {

    private final GeneratorOptions generatorOptions;
    private Configuration cfg;
    private Template template;

    public BasicGenerator(final GeneratorOptions generatorOptions) {
        this.generatorOptions = generatorOptions;
    }

    public void generate() throws IOException {
        if (this.generatorOptions.getOutputPath() == null) {
            throw new IOException("Output path is not defined!");
        }
        if (this.generatorOptions.getTemplateName() == null) {
            throw new IOException("Template name is not defined!");
        }
        if (this.generatorOptions.getOutputFileName() == null) {
            throw new IOException("Output file name is not defined!");
        }
//        if (this.filePackage == null) {
//            throw new IOException("Package name for code generation is not defined!");
//        }

        this.cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        final String tName = this.generatorOptions.getTemplateName() + ".ftl";
        try {
            final File dir = new File(this.generatorOptions.getTemplateDir());
            System.out.println(dir.getAbsolutePath());
            this.cfg.setDirectoryForTemplateLoading(dir);
            this.template = this.cfg.getTemplate(tName);
            final DefaultObjectWrapperBuilder builder =
                    new DefaultObjectWrapperBuilder(this.cfg.getIncompatibleImprovements());
            this.cfg.setObjectWrapper(builder.build());
            final File op = new File(this.generatorOptions.getOutputPath());
            if (!op.exists() && !op.mkdirs()) {
                throw new IOException(
                        "An error occurred during folder creation " + this.generatorOptions.getOutputPath());
            }
        } catch (final IOException e) {
            throw new IOException("Can't find template " + tName + ".", e);
        }

    }

    public Writer getWriter(final String fileNamePart, final String packageName) throws IOException {
        if (packageName.equals(this.generatorOptions.getFilePackage())) {
            this.generatorOptions.setFilePackage(packageName.replace(".", File.separator));
        }

        final String fullPath = this.generatorOptions.getOutputPath()
                + File.separator
                + (this.generatorOptions.getFilePackage().isEmpty() ? "" : this.packageToPath(this.generatorOptions.getFilePackage())
                + File.separator)
                + this.generatorOptions.getOutputFileName().replace("{0}", fileNamePart);

        final File of = new File(fullPath);
        if (!of.getParentFile().exists()) {
            if (!of.getParentFile().mkdirs()) {
                throw new IOException("An error occurred during output folder creation "
                        + this.generatorOptions.getOutputPath());
            }
        }

        System.out.println(of.getPath());
        System.out.println(of.getName());

        if (!this.generatorOptions.getOverwrite() && of.exists()) {
            return null;
        }

        return new OutputStreamWriter(new FileOutputStream(of));

    }

    protected String packageToPath(final String pack) {
        return pack.replace(".", File.separator);
    }
}
