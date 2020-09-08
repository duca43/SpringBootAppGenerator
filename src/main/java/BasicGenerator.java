import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;

import java.io.*;

/**
 * Abstract generator that creates necessary environment for code generation
 * (creating directory for code generation, fetching template, creating file with given name
 * for code generation etc). It should be ancestor for all generators in this project.
 */

public abstract class BasicGenerator {

    private final GeneratorOptions generatorOptions;
    private String outputPath;
    private String templateName;
    private String templateDir;
    private String outputFileName;
    private boolean overwrite = false;
    private String filePackage;
    private Configuration cfg;
    private Template template;

    public BasicGenerator(final GeneratorOptions generatorOptions) {
        this.generatorOptions = generatorOptions;
        this.outputPath = generatorOptions.getOutputPath();
        this.templateName = generatorOptions.getTemplateName();
        this.templateDir = generatorOptions.getTemplateDir();
        this.outputFileName = generatorOptions.getOutputFileName();
        this.overwrite = generatorOptions.getOverwrite();
        this.filePackage = generatorOptions.getFilePackage();
    }

    public void generate() throws IOException {
        if (this.outputPath == null) {
            throw new IOException("Output path is not defined!");
        }
        if (this.templateName == null) {
            throw new IOException("Template name is not defined!");
        }
        if (this.outputFileName == null) {
            throw new IOException("Output file name is not defined!");
        }
//        if (this.filePackage == null) {
//            throw new IOException("Package name for code generation is not defined!");
//        }

        this.cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        final String tName = this.templateName + ".ftl";
        try {
            final File dir = new File(this.templateDir);
            System.out.println(dir.getAbsolutePath());
            this.cfg.setDirectoryForTemplateLoading(dir);
            this.template = this.cfg.getTemplate(tName);
            final DefaultObjectWrapperBuilder builder =
                    new DefaultObjectWrapperBuilder(this.cfg.getIncompatibleImprovements());
            this.cfg.setObjectWrapper(builder.build());
            final File op = new File(this.outputPath);
            if (!op.exists() && !op.mkdirs()) {
                throw new IOException(
                        "An error occurred during folder creation " + this.outputPath);
            }
        } catch (final IOException e) {
            throw new IOException("Can't find template " + tName + ".", e);
        }

    }

    public Writer getWriter(final String fileNamePart, final String packageName) throws IOException {
        if (packageName != this.filePackage) {
            packageName.replace(".", File.separator);
            this.filePackage = packageName;
        }

        final String fullPath = this.outputPath
                + File.separator
                + (this.filePackage.isEmpty() ? "" : this.packageToPath(this.filePackage)
                + File.separator)
                + this.outputFileName.replace("{0}", fileNamePart);

        final File of = new File(fullPath);
        if (!of.getParentFile().exists()) {
            if (!of.getParentFile().mkdirs()) {
                throw new IOException("An error occurred during output folder creation "
                        + this.outputPath);
            }
        }

        System.out.println(of.getPath());
        System.out.println(of.getName());

        if (!this.isOverwrite() && of.exists()) {
            return null;
        }

        return new OutputStreamWriter(new FileOutputStream(of));

    }

    protected String packageToPath(final String pack) {
        return pack.replace(".", File.separator);
    }

    public boolean isOverwrite() {
        return this.overwrite;
    }

    public void setOverwrite(final boolean overwrite) {
        this.overwrite = overwrite;
    }

    public Writer getWriter() throws IOException {
        return this.getWriter("", this.filePackage);

    }

    public void setOutputPath(final String output) {
        this.outputPath = output;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public void setTemplateDir(final String templateDir) {
        this.templateDir = templateDir;
    }

    public void setOutputFileName(final String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public Configuration getCfg() {
        return this.cfg;
    }

    public void setCfg(final Configuration cfg) {
        this.cfg = cfg;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public String getTemplateDir() {
        return this.templateDir;
    }

    public String getOutputFileName() {
        return this.outputFileName;
    }

    public String getFilePackage() {
        return this.filePackage;
    }

    public void setFilePackage(final String filePackage) {
        this.filePackage = filePackage;
    }

}
