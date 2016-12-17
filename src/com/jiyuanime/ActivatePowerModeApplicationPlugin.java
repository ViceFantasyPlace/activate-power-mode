package com.jiyuanime;

import com.intellij.openapi.components.ApplicationComponent;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import org.jetbrains.annotations.NotNull;

/**
 * com.jiyuanime.ActivatePowerModeApplicationPlugin
 * <p>
 * Created by suika on 15-12-13.
 */
public class ActivatePowerModeApplicationPlugin implements ApplicationComponent {

    public ActivatePowerModeApplicationPlugin() {}

    @Override
    public void initComponent() {}

    @Override
    public void disposeComponent() {
        ShakeManager.getInstance().destroy();
        ParticlePanel.getInstance().destroy();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "com.jiyuanime.ActivatePowerModeApplicationPlugin";
    }
}
