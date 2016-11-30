package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;

/**
 * 震动开关
 * <p>
 * Created by suika on 15-12-22.
 */
public class ShakeSwitchAction extends AnAction {

    Config.State state = Config.getInstance().state;

    public ShakeSwitchAction() {
        Presentation presentation = this.getTemplatePresentation();
        if (state.IS_SHAKE) {
            presentation.setIcon(AllIcons.General.InspectionsOK);
        } else {
            presentation.setIcon(null);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (state.IS_SHAKE) {
            this._disable(e.getPresentation());
        } else {
            this._enable(e.getPresentation());
        }
    }

    private void _disable(Presentation presentation) {
        state.IS_SHAKE = false;
        presentation.setIcon(null);
    }

    private void _enable(Presentation presentation) {
        state.IS_SHAKE = true;
        presentation.setIcon(AllIcons.General.InspectionsOK);
    }
}
