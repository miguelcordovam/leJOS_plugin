package org.lejos.gui;

import com.intellij.openapi.project.Project;
import org.lejos.preferences.LejosPreferencesConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LejosPreferencesGUI {

    private JPanel rootPanel;
    private JTextField ev3Home;
    private JButton browse;
    private JLabel name;
    private JTextField ipAddress;


    private LejosPreferencesConfig lejosConfig;


    public void createUI(Project project) {
        lejosConfig = LejosPreferencesConfig.getInstance(project);
        ev3Home.setText(lejosConfig.getEv3Home());

        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogTitle("Select Lejos Home");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                fileChooser.showOpenDialog((Component) e.getSource());

                ev3Home.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    public void apply () {
        lejosConfig.setEv3Home(ev3Home.getText());
        lejosConfig.setIpAddress(ipAddress.getText());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void reset() {
        ev3Home.setText(lejosConfig.getEv3Home());
        ipAddress.setText(lejosConfig.getIpAddress());
    }

    public boolean isModified() {
        return !ev3Home.getText().equals(lejosConfig.getEv3Home()) || !ipAddress.getText().equals(lejosConfig.getIpAddress());
    }
}
