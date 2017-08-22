package org.lejos.config;

import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.vfs.VirtualFile;
import lejos.remote.ev3.RMIMenu;
import org.jetbrains.annotations.Nullable;
import org.lejos.preferences.LejosPreferencesConfig;
import org.lejos.util.JarCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.util.ArrayList;

import static com.intellij.execution.ui.ConsoleViewContentType.ERROR_OUTPUT;
import static com.intellij.execution.ui.ConsoleViewContentType.NORMAL_OUTPUT;


public class LejosRunProcessHandler extends ProcessHandler {

    private LejosPreferencesConfig lejosConfig;

    @Override
    protected void destroyProcessImpl() {

    }

    @Override
    protected void detachProcessImpl() {

    }

    @Override
    public boolean detachIsDefault() {
        return false;
    }

    @Nullable
    @Override
    public OutputStream getProcessInput() {
        return null;
    }

    public void start (ConsoleView console, Project project, String mainClass) {

        String basePath = project.getBasePath();
        Module[] modules = ModuleManager.getInstance(project).getModules();

        console.print("LeJOS running...\n", NORMAL_OUTPUT);

        VirtualFile compilerOutputPath = CompilerModuleExtension.getInstance(modules[0]).getCompilerOutputPath();
        String jarName = modules[0].getName() + ".jar";

        try {
            Files.deleteIfExists(Paths.get(basePath + "/" + jarName));
        } catch (IOException e) {

        }

        JarCreator jarCreator = new JarCreator(compilerOutputPath.getCanonicalPath(), basePath + "/" + jarName , mainClass, new ArrayList<>());

        try {
            jarCreator.run();
            console.print("Jar file has been created successfully\n", NORMAL_OUTPUT);

            lejosConfig = LejosPreferencesConfig.getInstance(project);
            RMIMenu rmiMenu = (RMIMenu) Naming.lookup("//" + lejosConfig.getIpAddress() + "/RemoteMenu");

            File file = new File(basePath + "/" + modules[0].getName() + ".jar");
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            inputStream.read(bytes);
            inputStream.close();

            String fileName = "/home/lejos/programs/" + jarName;

            rmiMenu.deleteFile(fileName);

            console.print("Using the EV3 menu for upload and to execute program\n", NORMAL_OUTPUT);
            rmiMenu.uploadFile(fileName, bytes);
            console.print("IP address is /" + lejosConfig.getIpAddress() + "\n", NORMAL_OUTPUT);
            console.print("Uploading to " + lejosConfig.getIpAddress() + " ...\n", NORMAL_OUTPUT);

            console.print("Program has been uploaded\n", NORMAL_OUTPUT);

            rmiMenu.runProgram(modules[0].getName());
            console.print("Running program ...\n", NORMAL_OUTPUT);

            console.print("leJOS EV3 plugin launch complete\n", NORMAL_OUTPUT);
        } catch (ConnectException e) {
            console.print("Could not connect to brick. Check if brick is connected to your pc and IP address is correct", ERROR_OUTPUT);
        } catch (Exception e) {
            console.print("An error has occurred", ERROR_OUTPUT);
        }
    }
}