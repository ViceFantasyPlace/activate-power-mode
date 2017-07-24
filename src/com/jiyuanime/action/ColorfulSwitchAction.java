package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.jiyuanime.config.Config;


/**
 * Created by hentai_mew on 15-12-24.
 * 彩虹色块开关
 */
public class ColorfulSwitchAction extends BaseSwitchAction {
    @Override
    boolean getSwitchFieldValue() {
        return state.IS_COLORFUL;
    }

    @Override
    void setSwitchFieldValue(boolean is_enable) {
        state.IS_COLORFUL = is_enable;
    }
}
