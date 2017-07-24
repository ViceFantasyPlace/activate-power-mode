package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;

abstract class BaseSwitchAction extends AnAction {

    protected Config.State state = Config.getInstance().state;

    public BaseSwitchAction() {
        super();

        Presentation presentation = this.getTemplatePresentation();

        if(getSwitchFieldValue()) {
            showEnable(presentation);
        } else {
            showDisable(presentation);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        if(getSwitchFieldValue()) {
            disable(event);
        } else {
            enable(event);
        }
    }

    protected void disable(AnActionEvent event) {
        setSwitchFieldValue(false);
        showDisable(event.getPresentation());
    }

    protected void enable(AnActionEvent event) {
        setSwitchFieldValue(true);
        showEnable(event.getPresentation());
    }

    protected void showEnable(Presentation presentation){
        presentation.setIcon(AllIcons.General.InspectionsOK);
    }

    protected void showDisable(Presentation presentation){
        presentation.setIcon(null);
    }

    abstract boolean getSwitchFieldValue();

    abstract void setSwitchFieldValue(boolean is_enable);
}
