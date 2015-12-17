package com.jiyuanime;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

/**
 * 震动控制器
 *
 * Created by KADO on 15/12/15.
 */
public class ShakeManager {
    final int SHAKE_MIN = 1;
    final int SHAKE_MAX = 3;

    private static ShakeManager mShakeManager;

    private boolean isShaking = false;
    private boolean isEnable = false;
    private java.util.Timer mTimer = new Timer();
    private JComponent mNowEditorJComponent;

    public static ShakeManager getInstance() {

        if (mShakeManager == null) {
            mShakeManager = new ShakeManager();
        }
        return mShakeManager;
    }

    private ShakeManager() {}

    public void init(JComponent jComponent) {
        this.mNowEditorJComponent = jComponent;
        if (mTimer == null)
            mTimer = new Timer();
        isEnable = true;
    }

    public void reset(JComponent jComponent) {
        clear();
        init(jComponent);
    }

    public void clear() {
        isEnable = false;
        mNowEditorJComponent = null;
    }

    public void destroy() {
        clear();

        mTimer = null;
    }

    public void shake() {
        if (isEnable && mNowEditorJComponent != null) {
            isShaking = true;

            int x = shakeIntensity(SHAKE_MIN, SHAKE_MAX);
            int y = shakeIntensity(SHAKE_MIN, SHAKE_MAX);

            if (mTimer != null) {
                mTimer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            SwingUtilities.invokeLater(() -> mNowEditorJComponent.setLocation(x, y));
                            Thread.sleep(75);
                            SwingUtilities.invokeLater(() -> mNowEditorJComponent.setLocation(0, 0));
                            isShaking = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1);
            }
        } else {
            System.out.println("还没初始化 ShakeManager");
        }

    }

    private int shakeIntensity(int min, int max) {
        int direction = Math.random() > 0.5 ? -1 : 1;
        return ((int) Math.round(Math.random()*(max-min)+min)) * direction;
    }

    public boolean isShaking() {
        return isShaking;
    }

    public void setShaking(boolean shaking) {
        isShaking = shaking;
    }

    public JComponent getNowEditorJComponent() {
        return mNowEditorJComponent;
    }

    public void setNowEditorJComponent(JComponent nowEditorJComponent) {
        mNowEditorJComponent = nowEditorJComponent;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
