package com.jiyuanime.listener;


import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.colorful.ColorFactory;
import com.jiyuanime.config.Config;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import com.jiyuanime.utils.IntegerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 震动文本监听接口
 * Created by suika on 15-12-13.
 */
public class ActivatePowerDocumentListener implements DocumentListener, Disposable {

    private final Config.State state = Config.getInstance().state;
    private static ActivatePowerDocumentListener instance;

    private Project mProject;
    private final ArrayList<Document> documentList = new ArrayList<>();

    private Editor mEditor;

    private JLabel mComboLabel;

    public ActivatePowerDocumentListener(Project project) {
        mProject = project;
        instance = this;
    }

    public static ActivatePowerDocumentListener getInstance() {
        return instance;
    }

    @Override
    public void beforeDocumentChange(@NotNull DocumentEvent documentEvent) {

        ActivatePowerModeManage manage = ActivatePowerModeManage.getInstance();

        if (state.isCombo) {
            // 文本变化在 clickTimeInterval 时间内
            if (manage.getClickCombo() == 0 || System.currentTimeMillis() - manage.getClickTimeStamp() <= state.clickTimeInterval) {
                manage.setClickCombo(manage.getClickCombo() + 1);
                state.maxClickCombo = Math.max(manage.getClickCombo(), state.maxClickCombo);
            } else {
                manage.setClickCombo(0);
            }

            manage.setClickTimeStamp(System.currentTimeMillis());
        }

        if ((state.isCombo && manage.getClickCombo() > state.effectBorder && mProject != null) || (!state.isCombo && mProject != null)) {
            handleActivatePower(manage);
        }

        if (mComboLabel != null) {
            mComboLabel.setText(String.valueOf(manage.getClickCombo()));
            if (IntegerUtil.isSizeTable(manage.getClickCombo())) {
                manage.updateComboLabelPosition(mProject);
            }
        }
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent documentEvent) {
        if (mProject == null) {
            return;
        }

        Editor selectedTextEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
        if (mEditor == null || mEditor != selectedTextEditor) {
            mEditor = selectedTextEditor;
        }

        // 判断不处于批量模式
        if (mEditor != null && !mEditor.getDocument().isInBulkUpdate()) {
            Point point = mEditor.visualPositionToXY(mEditor.getCaretModel().getCurrentCaret().getSelectionEndPosition());
            ParticlePanel.getInstance().setCurrentCaretPosition(point);
        }
    }

    public boolean addDocument(Document document) {
        if (!documentList.contains(document)) {
            documentList.add(document);
            return true;
        } else {
            return false;
        }
    }

    private void unbindDocument(Document document, boolean isRemoveInList) {
        if (document != null && documentList.contains(document)) {
            document.removeDocumentListener(this);
            if (isRemoveInList) {
                documentList.remove(document);
            }
        }
    }

    public void clean(Document document, boolean isRemoveInList) {
        cleanEditorCaret();
        if (document != null) {
            unbindDocument(document, isRemoveInList);
        }
    }

    private void cleanEditorCaret() {
        mEditor = null;
    }

    public void destroy() {
        for (Document document : documentList) {
            clean(document, false);
        }
        documentList.clear();
        mProject = null;
    }

    /**
     * 处理ActivatePower效果
     */
    public void handleActivatePower(ActivatePowerModeManage manage) {
        Editor selectedTextEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
        if (mEditor == null || mEditor != selectedTextEditor) {
            mEditor = selectedTextEditor;
        }
        if (mEditor == null) {
            return;
        }

        manage.resetEditor(mEditor);
        if (state.isShake) {
            if (ShakeManager.getInstance().isEnable() && !ShakeManager.getInstance().isShaking()) {
                ShakeManager.getInstance().shake();
            }
        }

        if (state.isSpark) {
            Color color;
            if (state.particleColor != null) {
                color = state.particleColor;
            } else if (state.isColorful) {
                // 生成一个随机颜色
                color = ColorFactory.gen();
            } else {
                EditorEx editorEx = (EditorEx) mEditor;
                EditorHighlighter editorHighlighter = editorEx.getHighlighter();
                HighlighterIterator highlighterIterator = editorHighlighter.createIterator(mEditor.getCaretModel().getCurrentCaret().getOffset());
                Color fontColor = highlighterIterator.getTextAttributes().getForegroundColor();
                color = fontColor != null ? fontColor : mEditor.getColorsScheme().getDefaultForeground();
            }

            int fontSize = mEditor.getColorsScheme().getEditorFontSize();

            ParticlePanel particlePanel = ParticlePanel.getInstance();
            if (particlePanel.isEnable()) {
                particlePanel.sparkAtPositionAction(color, fontSize);
            }
        }
    }

    public void setComboLabel(JLabel comboLabel) {
        mComboLabel = comboLabel;
    }

    @Override
    public void dispose() {
        destroy();
    }
}
