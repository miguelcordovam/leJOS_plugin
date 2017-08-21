package org.lejos.config;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LejosRunConfiguration extends ApplicationConfiguration {

    private final Project myProject;

    public LejosRunConfiguration(String name, Project project, ConfigurationFactory factory) {
        super(name, project, factory);
        this.myProject = project;
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new LejosSettingsEditor(myProject);
    }

    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {

        return new JavaApplicationCommandLineState<LejosRunConfiguration>(LejosRunConfiguration.this, env) {

            @Nullable
            protected ConsoleView createConsole(@NotNull Executor executor, ProcessHandler processHandler) throws ExecutionException {
                ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(myProject).getConsole();
                consoleView.attachToProcess(processHandler);
                return  consoleView;
            }

            @NotNull
            @Override
            public ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
                final MyProcessHandler processHandler = new MyProcessHandler();
                final ConsoleView console = createConsole(executor, processHandler);
                processHandler.start(console, myProject, LejosRunConfiguration.this.MAIN_CLASS_NAME);
                return new DefaultExecutionResult(console, processHandler, createActions(console, processHandler, executor));
            }

            @Override
            protected JavaParameters createJavaParameters() throws ExecutionException {
                return super.createJavaParameters();
            }
        };
    }
}
