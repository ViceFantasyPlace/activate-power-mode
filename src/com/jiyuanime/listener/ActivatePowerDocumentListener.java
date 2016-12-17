package com.jiyuanime.listener;


import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.jiyuanime.colorful.ColorFactory;
import com.jiyuanime.config.Config;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;

import java.awt.*;

/**
 * 震动文本监听接口
 * Created by suika on 15-12-13.
 */
public class ActivatePowerDocumentListener implements DocumentListener {

    private Project mProject;
    private Document mDocument;

    private Editor mEditor;
    private CaretModel mCaretModel;

    private ActivatePowerCaretListener mActivatePowerCaretListener = new ActivatePowerCaretListener();

    public ActivatePowerDocumentListener(Project project) {
        mProject = project;
    }

    @Override
    public void beforeDocumentChange(DocumentEvent documentEvent) {

        Config.State state = Config.getInstance().state;

        if (state.IS_SHAKE) {
            if (ShakeManager.getInstance().isEnable() && !ShakeManager.getInstance().isShaking())
                ShakeManager.getInstance().shake();
        }

        if (state.IS_SPARK) {

            if (mEditor == null)
                mEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
            if (mEditor != null) {

                if (mCaretModel == null)
                    mCaretModel = mEditor.getCaretModel();

                mCaretModel.addCaretListener(mActivatePowerCaretListener);

                Color color;
                if (state.IS_COLORFUL) {
                    color = ColorFactory.gen(); // 生成一个随机颜色
                } else {
                    color = mEditor.getColorsScheme().getDefaultForeground();
                }

                int fontSize = mEditor.getContentComponent().getFont().getSize();

                ParticlePanel particlePanel = ParticlePanel.getInstance();
                if (particlePanel.isEnable()) {
                    particlePanel.sparkAtPositionAction(color, fontSize);
                }

            }
        }
    }

    @Override
    public void documentChanged(DocumentEvent documentEvent) {

    }

    public void setDocument(Document document) {
        mDocument = document;
    }

    private void unbindDocument(Document document) {
        if (document != null && mDocument != null && document == mDocument) {
            mDocument.removeDocumentListener(this);
            mDocument = null;
        }
    }

    public void clean() {
        if (mCaretModel != null && mActivatePowerCaretListener != null) {
            mCaretModel.removeCaretListener(mActivatePowerCaretListener);
            mCaretModel = null;
        }
        if (mDocument != null)
            unbindDocument(mDocument);
        mEditor = null;
    }

    public void destroy() {
        clean();
        mActivatePowerCaretListener = null;
        mProject = null;
    }
}
