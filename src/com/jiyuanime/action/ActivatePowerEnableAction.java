package com.jiyuanime.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
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
 * ActivatePower 开启 Action
 * Created by KADO on 2016/12/1.
 */
public class ActivatePowerEnableAction extends AnAction {

    private Config.State state = Config.getInstance().state;

    private ActivatePowerDocumentListener mActivatePowerDocumentListener;

    private Project mProject;

    public ActivatePowerEnableAction() {
        if (state.IS_ENABLE) {
            showEnable(getTemplatePresentation());
        } else {
            showDisable(getTemplatePresentation());
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (state.IS_ENABLE) {
            disable(e.getPresentation());
            destroy();
        } else {
            enable(e.getPresentation());
            init(e);
        }
    }

    private void init(AnActionEvent e) {
        if (mProject == null)
            mProject = e.getProject();

        if (mProject != null) {
            // 监听FileEditor的状态
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

                    if (state.IS_ENABLE) {
                        destroyShake();
                        destroyParticle();

                        VirtualFile newFile = event.getNewFile();
                        if (newFile != null) {
                            FileEditorManager fileEditorManager = event.getManager();
                            Editor editor = fileEditorManager.getSelectedTextEditor();
                            initEditor(editor);
                            Document newDocument = FileDocumentManager.getInstance().getDocument(newFile);
                            initDocument(newDocument);
                        }
                    }
                }
            });

            destroyShake();
            destroyParticle();

            Editor editor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
            if (editor != null) {
                initEditor(editor);
                initDocument(editor.getDocument());
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

    private void initDocument(Document document) {
        if (document != null) {
            if (mActivatePowerDocumentListener == null)
                mActivatePowerDocumentListener = new ActivatePowerDocumentListener(mProject);

            mActivatePowerDocumentListener.clean();
            mActivatePowerDocumentListener.setDocument(document);
            document.addDocumentListener(mActivatePowerDocumentListener);
        }
    }

    private void destroy() {
        destroyShake();
        destroyParticle();

        if (mActivatePowerDocumentListener != null) {
            mActivatePowerDocumentListener.destroy();
            mActivatePowerDocumentListener = null;
        }
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

    private void destroyShake() {
        ShakeManager.getInstance().destroy();
    }

    private void destroyParticle() {
        ParticlePanel.getInstance().destroy();
    }

    private void disable(Presentation presentation) {
        state.IS_ENABLE = false;
        showDisable(presentation);
    }

    private void enable(Presentation presentation) {
        state.IS_ENABLE = true;
        showEnable(presentation);
    }

    private void showEnable(Presentation presentation) {
        presentation.setIcon(AllIcons.General.InspectionsOK);
        presentation.setDescription("enable");
        presentation.setText("enable");
    }

    private void showDisable(Presentation presentation) {
        presentation.setIcon(AllIcons.Actions.Cancel);
        presentation.setDescription("disable");
        presentation.setText("disable");
    }
}
