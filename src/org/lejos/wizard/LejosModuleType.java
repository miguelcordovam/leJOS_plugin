package org.lejos.wizard;

import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import org.jetbrains.annotations.NotNull;

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

//    @Override
//    public Icon getBigIcon() {
//        return AllIcons.General.Information;
//    }
//
//    @Override
//    public Icon getNodeIcon(@Deprecated boolean b) {
//        return AllIcons.General.Information;
//    }



}
