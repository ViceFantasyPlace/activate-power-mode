package com.jiyuanime.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.config.Config;
import org.jetbrains.annotations.NotNull;

/**
 * 手动清空计数器
 * <p>
 * Author: sinar
 * 2020/3/24 23:37
 */
public class ClearComboCountAction extends AnAction {

    protected Config.State state = Config.getInstance().state;

    protected ActivatePowerModeManage manager = ActivatePowerModeManage.getInstance();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        state.maxClickCombo = 0;
        manager.clearComboView(event.getProject());
        manager.initComboView(event.getProject());
    }
}
