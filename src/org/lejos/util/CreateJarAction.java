package org.lejos.util;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import lejos.remote.ev3.RMIMenu;
import org.lejos.preferences.LejosPreferencesConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE;

/**
 * Created by Miguel on 5/21/2017.
 */
public class CreateJarAction extends AnAction {

    private LejosPreferencesConfig lejosConfig;

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiElement psiFile = event.getData(PSI_FILE);
        Project project = event.getData(CommonDataKeys.PROJECT);

        String basePath = project.getBasePath();

        PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile;
        final PsiClass[] classes = psiJavaFile.getClasses();

        String qualifiedName = classes[0].getQualifiedName();

        Module[] modules = ModuleManager.getInstance(project).getModules();

        VirtualFile compilerOutputPath = CompilerModuleExtension.getInstance(modules[0]).getCompilerOutputPath();

        JarCreator jarCreator = new JarCreator(compilerOutputPath.getCanonicalPath(), basePath + "/" + modules[0].getName() + ".jar", qualifiedName, new ArrayList<>());

        try {
            jarCreator.run();

            lejosConfig = LejosPreferencesConfig.getInstance(project);
            RMIMenu rmiMenu = (RMIMenu) Naming.lookup("//" + lejosConfig.getIpAddress() + "/RemoteMenu");

            File file = new File(basePath + "/" + modules[0].getName() + ".jar");
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            inputStream.read(bytes);
            inputStream.close();

            rmiMenu.uploadFile("/home/lejos/programs/" + modules[0].getName() + ".jar", bytes);

            rmiMenu.runProgram(modules[0].getName());
//            rmiMenu.runProgram("untitled");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
