package org.lejos.wizard;

import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LejosModuleType extends JavaModuleType {

    private static final String ID = "LEJOS_TYPE";

    public LejosModuleType() {
        super(ID);
    }

    public static LejosModuleType getInstance() {
        return (LejosModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public LejosModuleBuilder createModuleBuilder() {
        return new LejosModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "LeJOS EV3 Project";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "LeJOS EV3 Project";
    }

    @Override
    public Icon getBigIcon() {
        return IconLoader.findIcon("/icons/ev3_32x32.png");
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return IconLoader.findIcon("/icons/ev3_16x16.png");
    }


}
