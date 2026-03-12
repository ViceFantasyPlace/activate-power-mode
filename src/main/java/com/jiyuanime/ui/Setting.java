package com.jiyuanime.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.jiyuanime.i18n.ActivatePowerModeProBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author zzqo
 */
public class Setting implements Configurable {
    private SettingForm form;

    @Nls
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("settings.title");
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        form = new SettingForm();
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return form.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        form.apply();
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }
}
