package com.jiyuanime;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import org.jetbrains.annotations.NotNull;

/**
 * com.jiyuanime.ActivatePowerModeApplicationPlugin
 *
 * Created by suika on 15-12-13.
 */
public class ActivatePowerModeApplicationPlugin implements ApplicationComponent {

    EditorFocusListener editorFocusListener;
    ActivatePowerDocumentListener mActivatePowerDocumentListener;
    ActivatePowerCaretListener mActivatePowerCaretListener;

    public ActivatePowerModeApplicationPlugin() {
    }

    @Override
    public void initComponent() {
        editorFocusListener = new EditorFocusListener();
        mActivatePowerDocumentListener = new ActivatePowerDocumentListener();
        mActivatePowerCaretListener = new ActivatePowerCaretListener();

        EditorFactory.getInstance().addEditorFactoryListener(editorFocusListener, () -> {});

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(mActivatePowerDocumentListener);

        EditorFactory.getInstance().getEventMulticaster().addCaretListener(mActivatePowerCaretListener);

    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here

        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(mActivatePowerDocumentListener);
        EditorFactory.getInstance().getEventMulticaster().removeCaretListener(mActivatePowerCaretListener);
        EditorFactory.getInstance().removeEditorFactoryListener(editorFocusListener);

        ShakeManager.getInstance().destroy();
        ParticlePanel.getInstance().destroy();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "com.jiyuanime.ActivatePowerModeApplicationPlugin";
    }
}
