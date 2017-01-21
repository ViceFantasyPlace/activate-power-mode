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
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 效果管理器
 * Created by KADO on 2017/1/11.
 */
public class ActivatePowerModeManage {

    public static ActivatePowerModeManage sActivatePowerModeManage;

    public static ActivatePowerModeManage getInstance() {
        if (sActivatePowerModeManage == null) {
            sActivatePowerModeManage = new ActivatePowerModeManage();
        }
        return sActivatePowerModeManage;
    }

    private Config.State state = Config.getInstance().state;

    private ActivatePowerDocumentListener mActivatePowerDocumentListener;

    private Project mProject;
    private Editor mCurrentEditor;

    private long mClickTimeStamp;
    private int mClickCombo;

    public void init(Project project) {
        mProject = project;

        if (mProject != null) {
            // 监听FileEditor的状态
            MessageBusConnection connection = mProject.getMessageBus().connect();
            connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
                @Override
                public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileOpened(source, file);

                    initDocument(source.getProject(), FileDocumentManager.getInstance().getDocument(file));
                }

                @Override
                public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                    super.fileClosed(source, file);

                    if (mActivatePowerDocumentListener != null)
                        mActivatePowerDocumentListener.clean(FileDocumentManager.getInstance().getDocument(file), true);
                }

                @Override
                public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                    super.selectionChanged(event);

                    if (state.IS_ENABLE) {
                        destroyShake();
                        destroyParticle();
                        mCurrentEditor = null;
                    }
                }
            });

            FileEditorManager fileEditorManager = FileEditorManager.getInstance(mProject);

            if (fileEditorManager != null) {
                Editor editor = fileEditorManager.getSelectedTextEditor();
                if (editor != null) {
                    destroyShake();
                    destroyParticle();
                    mCurrentEditor = null;

                    initDocument(mProject, editor.getDocument());
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
        if (document != null && project != null) {
            if (mActivatePowerDocumentListener == null || mActivatePowerDocumentListener.getProject() != project)
                mActivatePowerDocumentListener = new ActivatePowerDocumentListener(project);

            if (mActivatePowerDocumentListener.addDocument(document))
                document.addDocumentListener(mActivatePowerDocumentListener);
        }
    }

    public void destroy() {
        destroyShake();
        destroyParticle();
        destroyDocumentListener();
        mCurrentEditor = null;
        destroyProjectMessageBus();
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

    private void destroyDocumentListener() {
        if (mActivatePowerDocumentListener != null) {
            mActivatePowerDocumentListener.destroy();
            mActivatePowerDocumentListener = null;
        }
    }

    private void destroyShake() {
        ShakeManager.getInstance().destroy();
    }

    private void destroyParticle() {
        ParticlePanel.getInstance().destroy();
    }

    private void destroyProjectMessageBus() {
        if (mProject != null) {
            MessageBusConnection connection = mProject.getMessageBus().connect();
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
        mProject = null;
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
