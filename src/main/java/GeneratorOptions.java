/**
 * GeneratorOptions: options used for code generation. Every generator (ejb generator, forms generator etc) should
 * have one instance of this class
 */

public class GeneratorOptions {
    private String outputPath;
    private String templateName;
    private String templateDir;
    private String outputFileName;
    private Boolean overwrite;
    private String filePackage;

    public GeneratorOptions(final String outputPath, final String templateName,
							final String templateDir, final String outputFileName, final Boolean overwrite,
							final String filePackage) {
        super();
        this.outputPath = outputPath;
        this.templateName = templateName;
        this.templateDir = templateDir;
        this.outputFileName = outputFileName;
        this.overwrite = overwrite;
        this.filePackage = filePackage;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public void setOutputPath(final String outputPath) {
        this.outputPath = outputPath;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDir() {
        return this.templateDir;
    }

    public void setTemplateDir(final String templateDir) {
        this.templateDir = templateDir;
    }

    public String getOutputFileName() {
        return this.outputFileName;
    }

    public void setOutputFileName(final String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public Boolean getOverwrite() {
        return this.overwrite;
    }

    public void setOverwrite(final Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public String getFilePackage() {
        return this.filePackage;
    }

    public void setFilePackage(final String filePackage) {
        this.filePackage = filePackage;
    }


}
