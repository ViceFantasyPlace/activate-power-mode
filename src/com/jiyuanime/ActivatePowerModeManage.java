package com.jiyuanime;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBusConnection;
import com.jiyuanime.config.Config;
import com.jiyuanime.listener.ActivatePowerDocumentListener;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 效果管理器
 * Created by KADO on 2017/1/11.
 */
public class ActivatePowerModeManage {

    private static ActivatePowerModeManage sActivatePowerModeManage;

    public static ActivatePowerModeManage getInstance() {
        if (sActivatePowerModeManage == null) {
            sActivatePowerModeManage = new ActivatePowerModeManage();
        }
        return sActivatePowerModeManage;
    }

    private Config.State state = Config.getInstance().state;

    private HashMap<Project, ActivatePowerDocumentListener> mDocListenerMap = new HashMap<>();
    private Editor mCurrentEditor;

    private long mClickTimeStamp;
    private int mClickCombo;

    public void init(Project project) {

        if (project != null) {
            // 监听FileEditor的状态
            MessageBusConnection connection = project.getMessageBus().connect();
            connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
                @Override
                public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileOpened(source, file);

                    destroyShake();
                    destroyParticle();
                    mCurrentEditor = null;

                    initDocument(source.getProject(), FileDocumentManager.getInstance().getDocument(file));
                }

                @Override
                public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileClosed(source, file);

                    ActivatePowerDocumentListener activatePowerDocumentListener = mDocListenerMap.get(source.getProject());
                    if (activatePowerDocumentListener != null)
                        activatePowerDocumentListener.clean(FileDocumentManager.getInstance().getDocument(file), true);
                }

                @Override
                public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                    super.selectionChanged(event);

                    if (state.IS_ENABLE) {
                        destroyShake();
                        destroyParticle();
                        mCurrentEditor = null;

                        FileEditorManager fileEditorManager = event.getManager();
                        VirtualFile virtualFile = event.getNewFile();
                        if (virtualFile != null)
                            initDocument(fileEditorManager.getProject(), FileDocumentManager.getInstance().getDocument(virtualFile));
                    }
                }
            });

            FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);

            if (fileEditorManager != null) {
                Editor editor = fileEditorManager.getSelectedTextEditor();
                if (editor != null) {
                    destroyShake();
                    destroyParticle();
                    mCurrentEditor = null;

                    initDocument(project, editor.getDocument());
                }
            }

        } else
            System.out.println("ActivatePowerEnableAction " + "初始化数据失败");

    }

    private void initEditor(Editor editor) {
        if (editor != null) {
            initShake(editor.getComponent());
            initParticle(editor.getContentComponent());
        }
    }

    private void initDocument(Project project, Document document) {
        if (project != null && document != null) {
            ActivatePowerDocumentListener activatePowerDocumentListener = mDocListenerMap.get(project);
            if (activatePowerDocumentListener == null) {
                activatePowerDocumentListener = new ActivatePowerDocumentListener(project);
                mDocListenerMap.put(project, activatePowerDocumentListener);
            }
            if (activatePowerDocumentListener.addDocument(document))
                document.addDocumentListener(activatePowerDocumentListener);
        }
    }

    public void destroy(Project project, boolean isRemoveProject) {
        destroyShake();
        destroyParticle();
        destroyDocumentListener(project, isRemoveProject);
        mCurrentEditor = null;
        destroyProjectMessageBus(project, isRemoveProject);
    }

    public void destroyAll() {
        for (Project project : mDocListenerMap.keySet()) {
            destroy(project, false);
        }
        mDocListenerMap.clear();
    }

    private void initShake(JComponent jComponent) {
        Config.State state = Config.getInstance().state;
        if (state.IS_SHAKE) {
            if (ShakeManager.getInstance().getNowEditorJComponent() != jComponent) {
                ShakeManager.getInstance().reset(jComponent);
            }
        }
    }

    private void initParticle(JComponent jContentComponent) {
        Config.State state = Config.getInstance().state;
        if (state.IS_SPARK) {
            if (ParticlePanel.getInstance().getNowEditorJComponent() != jContentComponent) {
                ParticlePanel.getInstance().reset(jContentComponent);
                jContentComponent.setBorder(ParticlePanel.getInstance());
            }
        }
    }

    public void resetEditor(Editor editor) {
        if (mCurrentEditor != editor) {
            mCurrentEditor = editor;
            initEditor(mCurrentEditor);
        }
    }

    private void destroyDocumentListener(Project project, boolean isRemoveProject) {
        ActivatePowerDocumentListener activatePowerDocumentListener = mDocListenerMap.get(project);
        if (activatePowerDocumentListener != null) {
            activatePowerDocumentListener.destroy();
            if (isRemoveProject)
                mDocListenerMap.remove(project);
        }
    }

    private void destroyShake() {
        ShakeManager.getInstance().destroy();
    }

    private void destroyParticle() {
        ParticlePanel.getInstance().destroy();
    }

    private void destroyProjectMessageBus(Project project, boolean isRemoveProject) {
        if (project != null) {
            MessageBusConnection connection = project.getMessageBus().connect();
            connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
                @Override
                public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileOpened(source, file);
                }

                @Override
                public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileClosed(source, file);
                }

                @Override
                public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                    super.selectionChanged(event);
                }
            });
        }
        if (isRemoveProject)
            mDocListenerMap.remove(project);
    }

    public long getClickTimeStamp() {
        return mClickTimeStamp;
    }

    public void setClickTimeStamp(long clickTimeStamp) {
        mClickTimeStamp = clickTimeStamp;
    }

    public int getClickCombo() {
        return mClickCombo;
    }

    public void setClickCombo(int clickCombo) {
        mClickCombo = clickCombo;
    }
}
