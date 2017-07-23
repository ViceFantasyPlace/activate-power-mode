package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;

/**
 * 粒子开关
 * <p>
 * Created by suika on 15-12-22.
 */
public class ParticleSwitchAction extends BaseSwitchAction {
    @Override
    boolean getSwitchFieldValue() {
        return state.IS_SPARK;
    }

    @Override
    void setSwitchFieldValue(boolean is_enable) {
        state.IS_SPARK = is_enable;
    }
}
