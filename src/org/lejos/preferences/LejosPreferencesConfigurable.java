package org.lejos.preferences;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lejos.gui.LejosPreferencesGUI;

import javax.swing.*;

public class LejosPreferencesConfigurable implements SearchableConfigurable {

    private final LejosPreferencesConfig lejosConfig;
    private final Project project;
    private LejosPreferencesGUI preferencesGUI;

    public LejosPreferencesConfigurable(@NotNull Project project) {
        this.project = project;
        lejosConfig = LejosPreferencesConfig.getInstance(project);
    }

    @NotNull
    @Override
    public String getId() {
        return "preferences.LejosPreferencesConfigurable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "LeJOS Plugin";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preferences.LejosPreferencesConfigurable";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        preferencesGUI = new LejosPreferencesGUI();
        preferencesGUI.createUI(project);
        return preferencesGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return preferencesGUI.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        preferencesGUI.apply();
    }

    @Override
    public void reset() {
        preferencesGUI.reset();
    }
}
