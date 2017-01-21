package com.jiyuanime;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import com.jiyuanime.config.Config;
import org.jetbrains.annotations.NotNull;

/**
 * com.jiyuanime.ActivatePowerModeApplicationPlugin
 * <p>
 * Created by suika on 15-12-13.
 */
public class ActivatePowerModeApplicationPlugin implements ApplicationComponent {

    private Config.State state = Config.getInstance().state;

    public ActivatePowerModeApplicationPlugin() {}

    @Override
    public void initComponent() {
        ProjectManager.getInstance().addProjectManagerListener(new ProjectManagerAdapter() {
            @Override
            public void projectOpened(Project project) {
                if (state.IS_ENABLE)
                    ActivatePowerModeManage.getInstance().init(project);
                super.projectOpened(project);
            }

            @Override
            public void projectClosed(Project project) {
                ActivatePowerModeManage.getInstance().destroy(project, true);
                super.projectClosed(project);
            }

            @Override
            public void projectClosing(Project project) {
                super.projectClosing(project);
            }

            @Override
            public boolean canCloseProject(Project project) {
                return super.canCloseProject(project);
            }
        });
    }

    @Override
    public void disposeComponent() {
        ActivatePowerModeManage.getInstance().destroyAll();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "com.jiyuanime.ActivatePowerModeApplicationPlugin";
    }
}
