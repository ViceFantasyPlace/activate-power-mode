package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.i18n.ActivatePowerModeProBundle;


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
        presentation.setText(ActivatePowerModeProBundle.message("action.ActivatePowerEnable.text"));
        presentation.setDescription(ActivatePowerModeProBundle.message("action.ActivatePowerEnable.description"));
    }

    @Override
    protected void showDisable(Presentation presentation) {
        presentation.setIcon(AllIcons.Actions.Cancel);
        presentation.setText(ActivatePowerModeProBundle.message("action.ActivatePowerEnable.text"));
        presentation.setDescription(ActivatePowerModeProBundle.message("action.ActivatePowerEnable.description"));
    }

    @Override
    boolean getSwitchFieldValue() {
        return state.isEnable;
    }

    @Override
    void setSwitchFieldValue(boolean isEnable) {
        state.isEnable = isEnable;
    }
}
