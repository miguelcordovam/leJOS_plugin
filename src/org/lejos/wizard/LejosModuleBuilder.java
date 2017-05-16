package org.lejos.wizard;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleWithNameAlreadyExists;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class LejosModuleBuilder extends JavaModuleBuilder {

    @Override
    public ModuleType getModuleType() {
        return LejosModuleType.getInstance();
    }

    @NotNull
    @Override
    public Module createAndCommitIfNeeded(@NotNull Project project, @Nullable ModifiableModuleModel model, boolean runFromProjectWizard) throws InvalidDataException, ConfigurationException, IOException, JDOMException, ModuleWithNameAlreadyExists {
        String moduleLibraryPath = "C:\\Miguel\\leJOS EV3\\lib\\pc\\ev3tools.jar";
        super.addModuleLibrary(moduleLibraryPath, "");

        return super.createAndCommitIfNeeded(project, model, runFromProjectWizard);
    }

}
