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
public class ActivatePowerEnableAction extends AnAction {

    private Config.State state = Config.getInstance().state;

    public ActivatePowerEnableAction() {
        if (state.IS_ENABLE) {
            showEnable(getTemplatePresentation());
        } else {
            showDisable(getTemplatePresentation());
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (state.IS_ENABLE) {
            disable(e.getPresentation());
            ActivatePowerModeManage.getInstance().destroy();
        } else {
            enable(e.getPresentation());
            ActivatePowerModeManage.getInstance().init(e.getProject());
        }
    }

    private void disable(Presentation presentation) {
        state.IS_ENABLE = false;
        showDisable(presentation);
    }

    private void enable(Presentation presentation) {
        state.IS_ENABLE = true;
        showEnable(presentation);
    }

    private void showEnable(Presentation presentation) {
        presentation.setIcon(AllIcons.General.InspectionsOK);
        presentation.setDescription("enable");
        presentation.setText("enable");
    }

    private void showDisable(Presentation presentation) {
        presentation.setIcon(AllIcons.Actions.Cancel);
        presentation.setDescription("disable");
        presentation.setText("disable");
    }
}
