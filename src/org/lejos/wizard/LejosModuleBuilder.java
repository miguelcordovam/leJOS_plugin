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
import org.lejos.preferences.LejosPreferencesConfig;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class LejosModuleBuilder extends JavaModuleBuilder {

    private LejosPreferencesConfig lejosConfig;

    @Override
    public ModuleType getModuleType() {
        return LejosModuleType.getInstance();
    }

    @NotNull
    @Override
    public Module createAndCommitIfNeeded(@NotNull Project project, @Nullable ModifiableModuleModel model, boolean runFromProjectWizard) throws InvalidDataException, ConfigurationException, IOException, JDOMException, ModuleWithNameAlreadyExists {
        final String OS = System.getProperty("os.name").toLowerCase();
        String suffix = null;
        if (OS.contains("win")) {
            // We are in a windows environment
            suffix = "\\lib";
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix") || OS.contains("mac")) {
            // We are in a Unix (including mac) environment
            suffix = "/lib";
        }
        lejosConfig = LejosPreferencesConfig.getInstance(project);
        Path lejosJarHome = Paths.get(lejosConfig.getEv3Home() + suffix);

        Files.walkFileTree(lejosJarHome, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    addLibrary(file.toString());
                }
                return CONTINUE;
            }
        });

        return super.createAndCommitIfNeeded(project, model, runFromProjectWizard);
    }

    private void addLibrary(String library) {
        super.addModuleLibrary(library, "");
    }

}
