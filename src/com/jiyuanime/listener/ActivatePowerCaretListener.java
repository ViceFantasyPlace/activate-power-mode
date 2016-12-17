package com.jiyuanime.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.jiyuanime.particle.ParticlePanel;

import java.awt.*;

/**
 * 光标监听接口
 * Created by KADO on 15/12/15.
 */
public class ActivatePowerCaretListener implements CaretListener {

    @Override
    public void caretPositionChanged(CaretEvent caretEvent) {
        Editor nowEditor = caretEvent.getEditor();
        Point point = nowEditor.logicalPositionToXY(caretEvent.getNewPosition());
        ParticlePanel.getInstance().setCurrentCaretPosition(point);
    }

    @Override
    public void caretAdded(CaretEvent caretEvent) {}

    @Override
    public void caretRemoved(CaretEvent caretEvent) {}
}
