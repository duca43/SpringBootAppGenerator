package org.asdm.springbootgeneratorplugin.view;

import net.miginfocom.swing.MigLayout;
import org.asdm.springbootgeneratorplugin.model.*;
import org.asdm.springbootgeneratorplugin.util.Constants;
import org.asdm.springbootgeneratorplugin.util.GeneratorUtil;
import org.asdm.springbootgeneratorplugin.util.StringUtil;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ProjectMetadataDialog extends JDialog {
    private static final long serialVersionUID = -4248675963608687407L;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private MetadataPanel metadataPanel;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;

    public ProjectMetadataDialog(final Window owner) {
        super(owner, Constants.GENERATE_DIALOG_TITLE, ModalityType.APPLICATION_MODAL);
        this.initComponents();
    }

    private void initComponents() {
        this.dialogPane = new JPanel();
        this.contentPanel = new JPanel();
        this.metadataPanel = new MetadataPanel(this);
        this.buttonBar = new JPanel();
        this.okButton = new JButton();
        this.cancelButton = new JButton();

        final Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        this.dialogPane.setLayout(new BorderLayout());

        this.contentPanel.setLayout(new MigLayout("fill,insets 10,hidemode 3,alignx center,gap 5 5", "[fill]", "[grow,fill]"));

        this.contentPanel.add(this.metadataPanel, "cell 0 0");

        this.dialogPane.add(this.contentPanel, BorderLayout.CENTER);

        this.buttonBar.setLayout(new MigLayout("insets dialog,alignx right", "[button,fill][button,fill]", null));

        this.okButton.setText(Constants.GENERATE);
        this.okButton.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -3108491457327146754L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final String group = ProjectMetadataDialog.this.metadataPanel.getGroup();
                    if (group == null || group.isEmpty()) {
                        JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), "Group id must be set!",
                                Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    final String artifact = ProjectMetadataDialog.this.metadataPanel.getArtifact();
                    if (artifact == null || artifact.isEmpty()) {
                        JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), "Artifact id must be set!",
                                Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    final MetaLibInfo appInfo = MetaLibInfo.builder()
                            .groupId(group)
                            .artifactId(artifact)
                            .version(Constants.INITIAL_APP_VERSION)
                            .build();

                    final Version springBootVersion = MetaModel.getInstance()
                            .getSpringBootVersions()
                            .stream()
                            .filter(version -> version.getVersionName().equals(ProjectMetadataDialog.this.metadataPanel.getSelectedSpringBootVersion()))
                            .findFirst().orElse(null);

                    if (springBootVersion == null) {
                        JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), "Spring Boot version must be set!",
                                Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    final PackagingType packaging;
                    if (ProjectMetadataDialog.this.metadataPanel.getSelectedPackaging().equals(PackagingType.JAR.name())) {
                        packaging = PackagingType.JAR;
                    } else {
                        packaging = PackagingType.WAR;
                    }

                    final Version javaVersion = MetaModel.getInstance()
                            .getJavaVersions()
                            .stream()
                            .filter(version -> version.getVersionName().equals(ProjectMetadataDialog.this.metadataPanel.getSelectedJavaVersion()))
                            .findFirst().orElse(null);

                    if (javaVersion == null) {
                        JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), "Java version must be set!",
                                Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    final MetaAppInfo metaAppInfo = MetaAppInfo.builder()
                            .info(appInfo)
                            .springBootVersion(springBootVersion.getConcreteVersion())
                            .packaging(packaging)
                            .name(StringUtil.modifyName(ProjectMetadataDialog.this.metadataPanel.getAppName()))
                            .description(ProjectMetadataDialog.this.metadataPanel.getDescription())
                            .javaVersion(javaVersion.getConcreteVersion())
                            .build();

                    final MetaDependency selectedSqlDriver = ProjectMetadataDialog.this.metadataPanel.getSelectedSqlDriver();
                    if (selectedSqlDriver != null) {
                        metaAppInfo.getDependencies().add(selectedSqlDriver);
                    }

                    MetaModel.getInstance().setMetaAppInfo(metaAppInfo);

                    final String basePackage = StringUtil.getBasePackage(metaAppInfo);
                    MetaModel.getInstance().setPackageBase(basePackage);

                    GeneratorUtil.runGeneratorsForEntity(ProjectMetadataDialog.this.metadataPanel.getOutputPath());

                    ProjectMetadataDialog.this.dispatchEvent(new WindowEvent(ProjectMetadataDialog.this, WindowEvent.WINDOW_CLOSING));

                    JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), Constants.GENERATE_SUCCESS_MESSAGE,
                            Constants.SUCCESS_MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
                } catch (final IOException | IllegalAccessException | InvocationTargetException | InstantiationException
                        | SAXException | ParserConfigurationException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(ProjectMetadataDialog.this.getOwner(), ex.getMessage(),
                            Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.buttonBar.add(this.okButton, "cell 0 0");

        this.cancelButton.setText(Constants.CANCEL);
        this.cancelButton.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -1767162435685865392L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                ProjectMetadataDialog.this.dispatchEvent(new WindowEvent(ProjectMetadataDialog.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        this.buttonBar.add(this.cancelButton, "cell 1 0");

        this.dialogPane.add(this.buttonBar, BorderLayout.SOUTH);
        contentPane.add(this.dialogPane, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 5));
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }
}