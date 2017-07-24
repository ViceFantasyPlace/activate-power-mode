package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.config.Config;


/**
 * ActivatePower 开启 Action
 * Created by KADO on 2016/12/1.
 */
public class ActivatePowerEnableAction extends BaseSwitchAction {

    @Override
    protected void disable(AnActionEvent event) {
        super.disable(event);
        ActivatePowerModeManage.getInstance().destroyAll();
    }

    @Override
    protected void enable(AnActionEvent event) {
        super.enable(event);
        ActivatePowerModeManage.getInstance().init(event.getProject());
    }

    @Override
    protected void showEnable(Presentation presentation) {
        presentation.setIcon(AllIcons.General.InspectionsOK);
        presentation.setDescription("enable");
        presentation.setText("enable");
    }

    @Override
    protected void showDisable(Presentation presentation) {
        presentation.setIcon(AllIcons.Actions.Cancel);
        presentation.setDescription("disable");
        presentation.setText("disable");
    }

    @Override
    boolean getSwitchFieldValue() {
        return state.IS_ENABLE;
    }

    @Override
    void setSwitchFieldValue(boolean is_enable) {
        state.IS_ENABLE = is_enable;
    }
}
