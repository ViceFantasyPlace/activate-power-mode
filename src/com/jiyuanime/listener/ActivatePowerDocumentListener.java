package com.jiyuanime.listener;


import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.colorful.ColorFactory;
import com.jiyuanime.config.Config;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import com.jiyuanime.utils.IntegerUtil;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * 震动文本监听接口
 * Created by suika on 15-12-13.
 */
public class ActivatePowerDocumentListener implements DocumentListener {

    private Config.State state = Config.getInstance().state;

    private Project mProject;
    private ArrayList<Document> mDocumentList = new ArrayList<>();

    private Editor mEditor;

    private JLabel mComboLabel;

    public ActivatePowerDocumentListener(Project project) {
        mProject = project;
    }

    @Override
    public void beforeDocumentChange(DocumentEvent documentEvent) {

        ActivatePowerModeManage manage = ActivatePowerModeManage.getInstance();

        if (state.IS_COMBO) {
            // 文本变化在 CLICK_TIME_INTERVAL 时间内
            if (manage.getClickCombo() == 0 || System.currentTimeMillis() - manage.getClickTimeStamp() <= state.CLICK_TIME_INTERVAL) {
                manage.setClickCombo(manage.getClickCombo() + 1);
                state.MAX_CLICK_COMBO = manage.getClickCombo() > state.MAX_CLICK_COMBO ? manage.getClickCombo() : state.MAX_CLICK_COMBO;
            } else
                manage.setClickCombo(0);

            manage.setClickTimeStamp(System.currentTimeMillis());
        }

        if ((state.IS_COMBO && manage.getClickCombo() > state.OPEN_FUNCTION_BORDER && mProject != null) || (!state.IS_COMBO && mProject != null))
            handleActivatePower(manage);

        if (mComboLabel != null) {
            mComboLabel.setText(String.valueOf(manage.getClickCombo()));
            if (IntegerUtil.isSizeTable(manage.getClickCombo())) {
                manage.updateComboLabelPosition(mProject);
            }
        }
    }

    @Override
    public void documentChanged(DocumentEvent documentEvent) {

        Editor selectedTextEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
        if (mEditor == null || mEditor != selectedTextEditor)
            mEditor = selectedTextEditor;

        if (mEditor != null) {
            Point point = mEditor.logicalPositionToXY(mEditor.getCaretModel().getCurrentCaret().getLogicalPosition());
            ParticlePanel.getInstance().setCurrentCaretPosition(point);
        }
    }

    public boolean addDocument(Document document) {
        if (!mDocumentList.contains(document)) {
            mDocumentList.add(document);
            return true;
        } else {
            return false;
        }
    }

    private void unbindDocument(Document document, boolean isRemoveInList) {
        if (document != null && mDocumentList.contains(document)) {
            document.removeDocumentListener(this);
            if (isRemoveInList)
                mDocumentList.remove(document);
        }
    }

    public void clean(Document document, boolean isRemoveInList) {
        cleanEditorCaret();
        if (document != null)
            unbindDocument(document, isRemoveInList);
    }

    private void cleanEditorCaret() {
        mEditor = null;
    }

    public void destroy() {
        for (Document document : mDocumentList)
            clean(document, false);
        mDocumentList.clear();
        mProject = null;
    }

    /**
     * 处理ActivatePower效果
     */
    private void handleActivatePower(ActivatePowerModeManage manage) {
        Editor selectedTextEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
        if (mEditor == null || mEditor != selectedTextEditor)
            mEditor = selectedTextEditor;
        if (mEditor != null) {
            manage.resetEditor(mEditor);

            if (state.IS_SHAKE) {
                if (ShakeManager.getInstance().isEnable() && !ShakeManager.getInstance().isShaking())
                    ShakeManager.getInstance().shake();
            }

            if (state.IS_SPARK) {
                Color color;
                if (state.PARTICLE_COLOR != null) {
                    color = state.PARTICLE_COLOR;
                } else if (state.IS_COLORFUL) {
                    color = ColorFactory.gen(); // 生成一个随机颜色
                } else {
                    color = mEditor.getColorsScheme().getDefaultForeground();
                }

                int fontSize = mEditor.getColorsScheme().getEditorFontSize();

                ParticlePanel particlePanel = ParticlePanel.getInstance();
                if (particlePanel.isEnable()) {
                    particlePanel.sparkAtPositionAction(color, fontSize);
                }
            }
        }
    }

    public void setComboLabel(JLabel comboLabel) {
        mComboLabel = comboLabel;
    }
}
