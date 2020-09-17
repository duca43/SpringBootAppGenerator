package org.asdm.springbootgeneratorplugin.action;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import org.asdm.springbootgeneratorplugin.analyzer.AnalyzeException;
import org.asdm.springbootgeneratorplugin.analyzer.ModelAnalyzer;
import org.asdm.springbootgeneratorplugin.util.Constants;
import org.asdm.springbootgeneratorplugin.view.ProjectMetadataDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GenerateAction extends MDAction {

    private static final long serialVersionUID = -813747069262622575L;

    public GenerateAction() {
        super(Constants.GENERATE_ACTION_ID, Constants.GENERATE_ACTION,
                KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK), null);
        this.setDescription(Constants.GENERATE_ACTION_DESC);
        final URL resource = GenerateAction.class.getClassLoader().getResource(Constants.PATH_TO_GENERATE_ICON);
        if (resource != null) {
            this.setSmallIcon(new ImageIcon(resource));
        }
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        if (Application.getInstance().getProject() == null || Application.getInstance().getProject().getModel() == null) {
            JOptionPane.showMessageDialog(Application.getInstance().getMainFrame(), Constants.NO_MODEL_MESSAGE,
                    Constants.ERROR_MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        final Package model = Application.getInstance().getProject().getModel();
        final ModelAnalyzer analyzer = new ModelAnalyzer(model);

        try {
            analyzer.prepareModel();
            final ProjectMetadataDialog projectMetadataDialog = new ProjectMetadataDialog(Application.getInstance().getMainFrame());
            projectMetadataDialog.setVisible(true);
        } catch (final AnalyzeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}