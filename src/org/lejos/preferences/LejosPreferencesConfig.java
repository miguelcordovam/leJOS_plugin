package org.lejos.preferences;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "LejosPreferencesConfig",
        storages = {
                @Storage("LejosPreferencesConfig.xml")
        }
)
public class LejosPreferencesConfig implements PersistentStateComponent<LejosPreferencesConfig> {

    public String ev3Home;

    public LejosPreferencesConfig() {
        String ev3HomeEnv = System.getenv("EV3_HOME");
        ev3Home = ev3HomeEnv != null ? ev3HomeEnv : "";
    }

    public String getEv3Home() {
        return ev3Home;
    }

    public void setEv3Home(String ev3Home) {
        this.ev3Home = ev3Home;
    }

    @Nullable
    @Override
    public LejosPreferencesConfig getState() {
        return this;
    }

    @Override
    public void loadState(LejosPreferencesConfig lejosPreferencesConfig) {
        XmlSerializerUtil.copyBean(lejosPreferencesConfig, this);
    }

    public static LejosPreferencesConfig getInstance(Project project) {
        LejosPreferencesConfig config = ServiceManager.getService(project, LejosPreferencesConfig.class);
        return config;
    }
}
