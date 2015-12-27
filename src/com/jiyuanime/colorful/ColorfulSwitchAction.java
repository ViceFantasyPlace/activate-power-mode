package com.jiyuanime.colorful;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.Config;


/**
 * Created by hentai_mew on 15-12-24.
 * 彩虹色块开关
 */
public class ColorfulSwitchAction extends AnAction {

    public ColorfulSwitchAction() {
        Presentation presentation = this.getTemplatePresentation();
        if (Config.IS_COLORFUL) {
            presentation.setIcon(AllIcons.General.InspectionsOK);
        } else {
            presentation.setIcon(null);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (Config.IS_COLORFUL) {
            this._disable(e.getPresentation());
        } else {
            this._enable(e.getPresentation());
        }
    }

    private void _disable(Presentation presentation) {
        Config.IS_COLORFUL = false;
        presentation.setIcon(null);
    }

    private void _enable(Presentation presentation) {
        Config.IS_COLORFUL = true;
        presentation.setIcon(AllIcons.General.InspectionsOK);
    }
}
