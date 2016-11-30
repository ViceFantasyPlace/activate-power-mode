package com.jiyuanime;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.jiyuanime.listener.ActivatePowerCaretListener;
import com.jiyuanime.listener.ActivatePowerDocumentListener;
import com.jiyuanime.listener.ActivatePowerEditorFocusListener;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.shake.ShakeManager;
import org.jetbrains.annotations.NotNull;

/**
 * com.jiyuanime.ActivatePowerModeApplicationPlugin
 * <p>
 * Created by suika on 15-12-13.
 */
public class ActivatePowerModeApplicationPlugin implements ApplicationComponent {

    private ActivatePowerEditorFocusListener mActivatePowerEditorFocusListener;
    private ActivatePowerDocumentListener mActivatePowerDocumentListener;
    private ActivatePowerCaretListener mActivatePowerCaretListener;

    public ActivatePowerModeApplicationPlugin() {
    }

    @Override
    public void initComponent() {
        mActivatePowerEditorFocusListener = new ActivatePowerEditorFocusListener();
        mActivatePowerDocumentListener = new ActivatePowerDocumentListener();
        mActivatePowerCaretListener = new ActivatePowerCaretListener();

        EditorFactory.getInstance().addEditorFactoryListener(mActivatePowerEditorFocusListener, () -> {});
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(mActivatePowerDocumentListener);
        EditorFactory.getInstance().getEventMulticaster().addCaretListener(mActivatePowerCaretListener);
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here

        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(mActivatePowerDocumentListener);
        EditorFactory.getInstance().getEventMulticaster().removeCaretListener(mActivatePowerCaretListener);
        EditorFactory.getInstance().removeEditorFactoryListener(mActivatePowerEditorFocusListener);

        ShakeManager.getInstance().destroy();
        ParticlePanel.getInstance().destroy();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "com.jiyuanime.ActivatePowerModeApplicationPlugin";
    }
}
