package org.lejos.config;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.intellij.application.options.HtmlLanguageCodeStyleSettingsProvider.getDisplayName;

public class LejosConfigurationFactory extends ConfigurationFactory {
    protected LejosConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull
    RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new LejosRunConfiguration(getDisplayName(), project, this);
    }

    @Override
    public @NotNull
    @NonNls
    String getId() {
        return "LejosRunConfigurationType";
    }


}
