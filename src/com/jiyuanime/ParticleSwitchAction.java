package com.jiyuanime;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

/**
 * 粒子开关
 *
 * Created by suika on 15-12-22.
 */
public class ParticleSwitchAction extends AnAction {

    public ParticleSwitchAction()
    {
        Presentation presentation = this.getTemplatePresentation();
        if(Config.IS_SPARK)
        {
            presentation.setIcon(AllIcons.General.InspectionsOK);
        }
        else
        {
            presentation.setIcon(null);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if(Config.IS_SPARK)
        {
            this._disable(e.getPresentation());
        }
        else
        {
            this._enable(e.getPresentation());
        }
    }

    private void _disable(Presentation presentation)
    {
        Config.IS_SPARK = false;
        presentation.setIcon(null);
    }

    private void _enable(Presentation presentation)
    {
        Config.IS_SPARK = true;
        presentation.setIcon(AllIcons.General.InspectionsOK);
    }
}
