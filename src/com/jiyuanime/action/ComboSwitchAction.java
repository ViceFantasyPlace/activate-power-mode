package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;

/**
 * Combo开关
 */
public class ComboSwitchAction extends AnAction {

    Config.State state = Config.getInstance().state;

    public ComboSwitchAction() {
        Presentation presentation = this.getTemplatePresentation();
        if (state.IS_COMBO) {
            presentation.setIcon(AllIcons.General.InspectionsOK);
        } else {
            presentation.setIcon(null);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (state.IS_COMBO) {
            this._disable(e.getPresentation());
        } else {
            this._enable(e.getPresentation());
        }
    }

    private void _disable(Presentation presentation) {
        state.IS_COMBO = false;
        presentation.setIcon(null);
    }

    private void _enable(Presentation presentation) {
        state.IS_COMBO = true;
        presentation.setIcon(AllIcons.General.InspectionsOK);
    }
}
