package com.jiyuanime.listener;

import com.intellij.openapi.editor.Editor;
import com.jiyuanime.config.Config;
import com.jiyuanime.colorful.ColorFactory;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;

import javax.swing.*;
import java.awt.*;

/**
 * 光标监听接口
 * Created by KADO on 15/12/15.
 */
public class ActivatePowerCaretListener implements com.intellij.openapi.editor.event.CaretListener {

    @Override
    public void caretPositionChanged(com.intellij.openapi.editor.event.CaretEvent caretEvent) {
        Editor nowEditor = caretEvent.getEditor();
        JComponent jContentComponent = nowEditor.getContentComponent();
        JComponent jComponent = nowEditor.getComponent();

        Config.State state = Config.getInstance().state;

        if (state.IS_SHAKE) {
            if (ShakeManager.getInstance().getNowEditorJComponent() != jComponent) {
                ShakeManager.getInstance().reset(jComponent);
            }
        }

        if (state.IS_SPARK) {
            ParticlePanel particlePanel = ParticlePanel.getInstance();
            if (!particlePanel.isEnable()) {
                particlePanel.init(jContentComponent);
                particlePanel.setEnableAction(true);
                jContentComponent.setBorder(particlePanel);
            } else {
                if (ParticlePanel.getInstance().getNowEditorJComponent() != jContentComponent) {
                    ParticlePanel.getInstance().reset(jContentComponent);
                    jContentComponent.setBorder(particlePanel);
                }
            }

            Point point = nowEditor.logicalPositionToXY(caretEvent.getNewPosition());
            Color color;
            if (state.IS_COLORFUL)
                color = ColorFactory.gen(); //生成一个随机颜色
            else
                color = caretEvent.getEditor().getColorsScheme().getDefaultForeground();
            int fontSize = caretEvent.getEditor().getContentComponent().getFont().getSize();

            if (ParticlePanel.getInstance().isEnable())
                ParticlePanel.getInstance().sparkAtPosition(point, color, fontSize);
        }
    }

    @Override
    public void caretAdded(com.intellij.openapi.editor.event.CaretEvent caretEvent) {

    }

    @Override
    public void caretRemoved(com.intellij.openapi.editor.event.CaretEvent caretEvent) {

    }
}
