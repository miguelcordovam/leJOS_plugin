package org.lejos.config;

import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LejosRunConfigurationType extends ApplicationConfigurationType {

    @NonNls
    public static final String ID = "LejosRunConfigurationType";

    private final ConfigurationFactory lejosRunConfigurationFactory;

    public LejosRunConfigurationType() {
        lejosRunConfigurationFactory = new ConfigurationFactoryEx(this) {

            @Override
            public Icon getIcon(@NotNull RunConfiguration configuration) {
                return LejosRunConfigurationType.this.getIcon();
            }

            @NotNull
            @Override
            public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
                return new LejosRunConfiguration(getDisplayName(), project, this);
            }

            @Override
            public void onNewConfigurationCreated(@NotNull RunConfiguration configuration) {
                ((ModuleBasedConfiguration)configuration).onNewConfigurationCreated();
            }
        };
    }


    @Override
    public String getDisplayName() {
        return "LeJOS";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "LeJOS Run configuration";
    }

    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/icons/ev3_16x16.png");
    }

    @NotNull
    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{lejosRunConfigurationFactory};
    }
}
