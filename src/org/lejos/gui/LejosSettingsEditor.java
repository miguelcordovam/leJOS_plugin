package org.lejos.gui;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.configurations.ConfigurationUtil;
import com.intellij.execution.ui.ClassBrowser;
import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiMethodUtil;
import com.intellij.ui.EditorTextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;
import org.lejos.config.LejosRunConfiguration;
import org.lejos.preferences.LejosPreferencesConfig;

import javax.swing.*;

public class LejosSettingsEditor extends SettingsEditor<LejosRunConfiguration> {

    private final Project myProject;
    private final ConfigurationModuleSelector myModuleSelector;

    private LabeledComponent<EditorTextFieldWithBrowseButton> myMainClass;
    private JPanel myWholePanel;
    private LabeledComponent<ModulesComboBox> myModule;
    private JCheckBox supportKotlinCheckBox;

    private Module myModuleContext;
    private LejosPreferencesConfig lejosConfig;

    public LejosSettingsEditor(Project project) {
        this.myProject = project;
        lejosConfig = LejosPreferencesConfig.getInstance(project);
        myModuleSelector = new ConfigurationModuleSelector(project, myModule.getComponent());
        supportKotlinCheckBox.setSelected(lejosConfig.getSupportKotlin());
        supportKotlinCheckBox.addChangeListener(e -> {
            lejosConfig.setSupportKotlin(supportKotlinCheckBox.isSelected());
        });

        ClassBrowser.createApplicationClassBrowser(project, myModuleSelector).setField(myMainClass.getComponent());
    }

    @Override
    protected void resetEditorFrom(@NotNull LejosRunConfiguration configuration) {
        myModuleSelector.reset(configuration);
        myMainClass.getComponent().setText(configuration.MAIN_CLASS_NAME);
    }

    @Override
    protected void applyEditorTo(@NotNull LejosRunConfiguration configuration) throws ConfigurationException {
        myModuleSelector.applyTo(configuration);
        configuration.MAIN_CLASS_NAME = myMainClass.getComponent().getText();
        Module selectedModule = (Module)myModule.getComponent().getSelectedItem();
        configuration.setModule(selectedModule);
        configuration.setModule(ModuleManager.getInstance(myProject).getModules()[0]);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myWholePanel;
    }

    private void createUIComponents() {
        myMainClass = new LabeledComponent<>();
        myMainClass.setComponent(new EditorTextFieldWithBrowseButton(myProject, true, new JavaCodeFragment.VisibilityChecker() {
            @Override
            public JavaCodeFragment.VisibilityChecker.Visibility isDeclarationVisible(PsiElement declaration, PsiElement place) {
                if (declaration instanceof PsiClass) {
                    final PsiClass aClass = (PsiClass)declaration;
                    if (ConfigurationUtil.MAIN_CLASS.value(aClass) && PsiMethodUtil.findMainMethod(aClass) != null) {
                        return Visibility.VISIBLE;
                    }
                }
                return Visibility.NOT_VISIBLE;
            }
        }));
    }
}
