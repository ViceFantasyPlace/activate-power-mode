package com.jiyuanime;


import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 震动文本监听接口
 *
 * Created by suika on 15-12-13.
 */
public class ActivatePowerDocumentListener implements DocumentListener {

    @Override
    public void beforeDocumentChange(DocumentEvent documentEvent) {

    }

    @Override
    public void documentChanged(DocumentEvent documentEvent) {
        if (Config.IS_SHAKE) {
            if (ShakeManager.getInstance().isEnable() && !ShakeManager.getInstance().isShaking())
                ShakeManager.getInstance().shake();
        }
    }

}
