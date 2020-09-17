package org.asdm.springbootgeneratorplugin.view;

import net.miginfocom.swing.MigLayout;
import org.asdm.springbootgeneratorplugin.model.MetaDependency;
import org.asdm.springbootgeneratorplugin.model.MetaModel;
import org.asdm.springbootgeneratorplugin.model.PackagingType;
import org.asdm.springbootgeneratorplugin.util.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MetadataPanel extends JPanel {
    private static final long serialVersionUID = -2317682566793415786L;
    private final Window parent;
    private JLabel springBootVersionsTitle;
    private JPanel springBootVersionsContainer;
    private JRadioButton springBootVersion1;
    private JRadioButton springBootVersion2;
    private JRadioButton springBootVersion3;
    private JRadioButton springBootVersion4;
    private JRadioButton springBootVersion5;
    private JRadioButton springBootVersion6;
    private JRadioButton springBootVersion7;
    private JRadioButton springBootVersion8;
    private JLabel projectMetadataTitle;
    private JLabel groupLabel;
    private JTextField groupTextField;
    private JLabel artifactLabel;
    private JTextField artifactTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JLabel outputLabel;
    private JTextField outputTextField;
    private JButton outputBrowseButton;
    private JFileChooser fileChooser;
    private JLabel sqlDriverLabel;
    private JComboBox<String> sqlDriversComboBox;
    private JLabel packagingLabel;
    private JRadioButton jarPackaging;
    private JRadioButton warPackaging;
    private JLabel javaVersionLabel;
    private JRadioButton javaVersion1;
    private JRadioButton javaVersion2;
    private JRadioButton javaVersion3;
    private ButtonGroup springBootVersionButtonGroup;
    private ButtonGroup packagingButtonGroup;
    private ButtonGroup javaVersionButtonGroup;

    public MetadataPanel(final Window parent) {
        this.parent = parent;
        this.initComponents();
    }

    private void initComponents() {
        this.springBootVersionsTitle = new JLabel();
        this.springBootVersionsContainer = new JPanel();
        this.springBootVersion1 = new JRadioButton();
        this.springBootVersion2 = new JRadioButton();
        this.springBootVersion3 = new JRadioButton();
        this.springBootVersion4 = new JRadioButton();
        this.springBootVersion5 = new JRadioButton();
        this.springBootVersion6 = new JRadioButton();
        this.springBootVersion7 = new JRadioButton();
        this.springBootVersion8 = new JRadioButton();
        this.projectMetadataTitle = new JLabel();
        this.groupLabel = new JLabel();
        this.groupTextField = new JTextField();
        this.artifactLabel = new JLabel();
        this.artifactTextField = new JTextField();
        this.nameLabel = new JLabel();
        this.nameTextField = new JTextField();
        this.descriptionLabel = new JLabel();
        this.descriptionTextField = new JTextField();
        this.outputLabel = new JLabel();
        this.outputTextField = new JTextField();
        this.outputBrowseButton = new JButton();
        this.sqlDriverLabel = new JLabel();
        this.sqlDriversComboBox = new JComboBox<>();
        this.packagingLabel = new JLabel();
        this.jarPackaging = new JRadioButton();
        this.warPackaging = new JRadioButton();
        this.javaVersionLabel = new JLabel();
        this.javaVersion1 = new JRadioButton();
        this.javaVersion2 = new JRadioButton();
        this.javaVersion3 = new JRadioButton();

        this.setLayout(new MigLayout("fill,hidemode 3,alignx center", "[fill][grow,fill]", "[][][][][][][][][][][]"));

        this.springBootVersionsTitle.setText(Constants.SPRING_BOOT_TITLE);
        this.springBootVersionsTitle.setFont(this.springBootVersionsTitle.getFont().deriveFont(22f).deriveFont(Font.BOLD));
        this.add(this.springBootVersionsTitle, "cell 0 0 2 1");

        this.springBootVersionsContainer.setLayout(new MigLayout("fill,hidemode 3", "[fill]", "[][]"));

        this.springBootVersion1.setText(MetaModel.getInstance().getSpringBootVersions().get(0).getVersionName());
        this.springBootVersion1.setSelected(true);
        this.springBootVersion1.setActionCommand(this.springBootVersion1.getText());
        this.springBootVersionsContainer.add(this.springBootVersion1, "cell 0 0");
        this.springBootVersion2.setText(MetaModel.getInstance().getSpringBootVersions().get(1).getVersionName());
        this.springBootVersion2.setActionCommand(this.springBootVersion2.getText());
        this.springBootVersionsContainer.add(this.springBootVersion2, "cell 0 0");
        this.springBootVersion3.setText(MetaModel.getInstance().getSpringBootVersions().get(2).getVersionName());
        this.springBootVersion3.setActionCommand(this.springBootVersion3.getText());
        this.springBootVersionsContainer.add(this.springBootVersion3, "cell 0 0");
        this.springBootVersion4.setText(MetaModel.getInstance().getSpringBootVersions().get(3).getVersionName());
        this.springBootVersion4.setActionCommand(this.springBootVersion4.getText());
        this.springBootVersionsContainer.add(this.springBootVersion4, "cell 0 0");
        this.springBootVersion5.setText(MetaModel.getInstance().getSpringBootVersions().get(4).getVersionName());
        this.springBootVersion5.setActionCommand(this.springBootVersion5.getText());
        this.springBootVersionsContainer.add(this.springBootVersion5, "cell 0 1");
        this.springBootVersion6.setText(MetaModel.getInstance().getSpringBootVersions().get(5).getVersionName());
        this.springBootVersion6.setActionCommand(this.springBootVersion6.getText());
        this.springBootVersionsContainer.add(this.springBootVersion6, "cell 0 1");
        this.springBootVersion7.setText(MetaModel.getInstance().getSpringBootVersions().get(6).getVersionName());
        this.springBootVersion7.setActionCommand(this.springBootVersion7.getText());
        this.springBootVersionsContainer.add(this.springBootVersion7, "cell 0 1");
        this.springBootVersion8.setText(MetaModel.getInstance().getSpringBootVersions().get(7).getVersionName());
        this.springBootVersion8.setActionCommand(this.springBootVersion8.getText());
        this.springBootVersionsContainer.add(this.springBootVersion8, "cell 0 1");
        this.add(this.springBootVersionsContainer, "cell 0 1 2 1");

        this.springBootVersionButtonGroup = new ButtonGroup();
        this.springBootVersionButtonGroup.add(this.springBootVersion1);
        this.springBootVersionButtonGroup.add(this.springBootVersion2);
        this.springBootVersionButtonGroup.add(this.springBootVersion3);
        this.springBootVersionButtonGroup.add(this.springBootVersion4);
        this.springBootVersionButtonGroup.add(this.springBootVersion5);
        this.springBootVersionButtonGroup.add(this.springBootVersion6);
        this.springBootVersionButtonGroup.add(this.springBootVersion7);
        this.springBootVersionButtonGroup.add(this.springBootVersion8);

        this.projectMetadataTitle.setText(Constants.PROJECT_METADATA_TITLE);
        this.projectMetadataTitle.setFont(this.projectMetadataTitle.getFont().deriveFont(22f).deriveFont(Font.BOLD));
        this.add(this.projectMetadataTitle, "cell 0 2 2 1");

        this.groupLabel.setText(Constants.GROUP_TITLE);
        this.add(this.groupLabel, "cell 0 3,alignx right,growx 0");

        this.groupTextField.setText(Constants.DEFAULT_GROUP);
        this.add(this.groupTextField, "cell 1 3");

        this.artifactLabel.setText(Constants.ARTIFACT_TITLE);
        this.add(this.artifactLabel, "cell 0 4,alignx right,growx 0");

        this.artifactTextField.setText(Constants.DEFAULT_ARTIFACT_AND_NAME);
        this.artifactTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.updateName();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.updateName();
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.updateName();
            }

            public void updateName() {
                MetadataPanel.this.nameTextField.setText(MetadataPanel.this.artifactTextField.getText());
            }
        });
        this.add(this.artifactTextField, "cell 1 4");

        this.nameLabel.setText(Constants.NAME_TITLE);
        this.add(this.nameLabel, "cell 0 5,alignx right,growx 0");

        this.nameTextField.setText(Constants.DEFAULT_ARTIFACT_AND_NAME);
        this.add(this.nameTextField, "cell 1 5");

        this.descriptionLabel.setText(Constants.DESCRIPTION_TITLE);
        this.add(this.descriptionLabel, "cell 0 6,alignx right,growx 0");

        this.descriptionTextField.setText(Constants.DEFAULT_DESCRIPTION);
        this.add(this.descriptionTextField, "cell 1 6");

        this.outputLabel.setText(Constants.OUTPUT_TITLE);
        this.add(this.outputLabel, "cell 0 7,alignx right,growx 0");
        this.add(this.outputTextField, "cell 1 7,growx");

        this.outputBrowseButton.setText(Constants.BROWSE);
        this.add(this.outputBrowseButton, "cell 1 7");

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.outputTextField.setText(this.fileChooser.getCurrentDirectory().getAbsolutePath());

        this.outputBrowseButton.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -3814035743062877358L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                final int option = MetadataPanel.this.fileChooser.showOpenDialog(MetadataPanel.this.parent);
                if (option == JFileChooser.APPROVE_OPTION) {
                    final File file = MetadataPanel.this.fileChooser.getSelectedFile();
                    MetadataPanel.this.outputTextField.setText(file.getAbsolutePath());
                    MetadataPanel.this.outputTextField.setCaretPosition(0);
                }
            }
        });

        this.sqlDriverLabel.setText(Constants.SQL_DRIVER_TITLE);
        this.add(this.sqlDriverLabel, "cell 0 8,alignx right,growx 0");

        for (final String sqlDriverDisplayName : MetaModel.getInstance().getSqlDrivers().keySet()) {
            this.sqlDriversComboBox.addItem(sqlDriverDisplayName);
        }

        this.add(this.sqlDriversComboBox, "cell 1 8");

        this.packagingLabel.setText(Constants.PACKAGING_TITLE);
        this.add(this.packagingLabel, "cell 0 9,alignx right,growx 0");

        this.jarPackaging.setText(PackagingType.JAR.toString());
        this.jarPackaging.setSelected(true);
        this.jarPackaging.setActionCommand(this.jarPackaging.getText());
        this.add(this.jarPackaging, "cell 1 9,alignx left,growx 0");

        this.warPackaging.setText(PackagingType.WAR.toString());
        this.warPackaging.setActionCommand(this.warPackaging.getText());
        this.add(this.warPackaging, "cell 1 9");

        this.packagingButtonGroup = new ButtonGroup();
        this.packagingButtonGroup.add(this.jarPackaging);
        this.packagingButtonGroup.add(this.warPackaging);

        this.javaVersionLabel.setText(Constants.JAVA_TITLE);
        this.add(this.javaVersionLabel, "cell 0 10,alignx trailing,growx 0");

        this.javaVersionButtonGroup = new ButtonGroup();

        if (MetaModel.getInstance().getJavaVersions().size() > 0) {
            this.javaVersion1.setText(MetaModel.getInstance().getJavaVersions().get(0).getVersionName());
            this.add(this.javaVersion1, "cell 1 10,alignx left,growx 0");
            this.javaVersionButtonGroup.add(this.javaVersion1);
            this.javaVersion1.setSelected(true);
            this.javaVersion1.setActionCommand(this.javaVersion1.getText());
            if (MetaModel.getInstance().getJavaVersions().size() > 1) {
                this.javaVersion2.setText(MetaModel.getInstance().getJavaVersions().get(1).getVersionName());
                this.add(this.javaVersion2, "cell 1 10,alignx left,growx 0");
                this.javaVersionButtonGroup.add(this.javaVersion2);
                this.javaVersion2.setActionCommand(this.javaVersion2.getText());
                if (MetaModel.getInstance().getJavaVersions().size() > 2) {
                    this.javaVersion3.setText(MetaModel.getInstance().getJavaVersions().get(2).getVersionName());
                    this.add(this.javaVersion3, "cell 1 10");
                    this.javaVersionButtonGroup.add(this.javaVersion3);
                    this.javaVersion3.setActionCommand(this.javaVersion3.getText());
                }
            }
        }
    }

    public String getGroup() {
        return this.groupTextField.getText();
    }

    public String getArtifact() {
        return this.artifactTextField.getText();
    }

    public String getAppName() {
        return this.nameTextField.getText();
    }

    public String getDescription() {
        return this.descriptionTextField.getText();
    }

    public String getOutputPath() {
        if (this.outputTextField.getText().isEmpty()) {
            return this.fileChooser.getCurrentDirectory().getAbsolutePath();
        }
        return this.outputTextField.getText();
    }

    public MetaDependency getSelectedSqlDriver() {
        final String selectedItem = (String) this.sqlDriversComboBox.getSelectedItem();
        if (selectedItem == null) {
            return null;
        }
        return MetaModel.getInstance().getSqlDrivers().get(selectedItem);
    }

    public String getSelectedSpringBootVersion() {
        return this.springBootVersionButtonGroup.getSelection().getActionCommand();
    }

    public String getSelectedPackaging() {
        return this.packagingButtonGroup.getSelection().getActionCommand();
    }

    public String getSelectedJavaVersion() {
        return this.javaVersionButtonGroup.getSelection().getActionCommand();
    }
}