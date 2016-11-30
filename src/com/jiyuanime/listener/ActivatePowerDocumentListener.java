package com.jiyuanime.listener;


import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.jiyuanime.config.Config;
import com.jiyuanime.shake.ShakeManager;

/**
 * 震动文本监听接口
 * <p>
 * Created by suika on 15-12-13.
 */
public class ActivatePowerDocumentListener implements DocumentListener {

    @Override
    public void beforeDocumentChange(DocumentEvent documentEvent) {

    }

    @Override
    public void documentChanged(DocumentEvent documentEvent) {

        Config.State state = Config.getInstance().state;

        if (state.IS_SHAKE) {
            if (ShakeManager.getInstance().isEnable() && !ShakeManager.getInstance().isShaking())
                ShakeManager.getInstance().shake();
        }
    }

}
