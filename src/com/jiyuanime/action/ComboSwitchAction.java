package com.jiyuanime.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.jiyuanime.ActivatePowerModeManage;

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

    @Override
    protected void disable(AnActionEvent event) {
        super.disable(event);
        ActivatePowerModeManage.getInstance().clearComboView(event.getProject());
    }

    @Override
    protected void enable(AnActionEvent event) {
        super.enable(event);
        ActivatePowerModeManage.getInstance().initComboView(event.getProject());
    }
}
