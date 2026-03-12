package com.jiyuanime.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.startup.ProjectActivity;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.config.Config;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 处理项目的初始化和清理
 */
public class ActivatePowerModeListener implements ProjectActivity, ProjectManagerListener {

    private final Config.State state = Config.getInstance().state;

    public ActivatePowerModeListener() {
    
    }
    
    /**
     * 项目打开时初始化
     */
    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        if (state.isEnable) {
            ActivatePowerModeManage.getInstance().init(project);
        }

        return null;
    }

    /**
     * 项目关闭时清理
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        ActivatePowerModeManage.getInstance().destroy(project, true);
    }


}
