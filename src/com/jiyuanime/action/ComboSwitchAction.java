package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;

/**
 * Combo开关
 */
public class ComboSwitchAction extends BaseSwitchAction {
    @Override
    boolean getSwitchFieldValue() {
        return state.IS_COMBO;
    }

    @Override
    void setSwitchFieldValue(boolean is_enable) {
        state.IS_COMBO = is_enable;
    }
}
