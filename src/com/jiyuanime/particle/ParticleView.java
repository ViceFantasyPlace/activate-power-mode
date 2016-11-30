package com.jiyuanime.particle;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * 粒子类
 *
 * Created by KADO on 15/12/15.
 */
class ParticleView {
    public static final int PARTICLE_WIDTH = 3;

    public Color mColor;
    public float mAlpha;
    public float x, y;
    public float vX, vY;

    public Point mPoint;

    private boolean isEnable = false;

    public ParticleView(Point point, Color color, boolean isEnable) {
        init(point, color, isEnable);
    }

    public void init(Point point, Color color, boolean isEnable) {
        this.isEnable = isEnable;
        this.mPoint = point;
        this.mColor = color;
        this.mAlpha = 1.0f;

        x = (float) this.mPoint.getX();
        y = (float) this.mPoint.getY();

        vX = (float) (-1.0 + ((Math.round(Math.random() * 100)) / 100.0) * 2);
        vY = (float) (-3.5 + ((Math.round(Math.random() * 100)) / 100.0) * 2);
    }

    public void reset(Point point, Color color, boolean isEnable) {
        init(point, color, isEnable);
    }

    public void update() {
        this.mAlpha *= 0.96;

        vY += 0.075;

        x = x + vX;
        y = y + vY;

        ColorSpace colorSpace = mColor.getColorSpace();
        float[] floats = mColor.getColorComponents(null);
        float newAlpha = mAlpha + 0.3f;
        if (newAlpha > 1.0f)
            newAlpha = 1.0f;
        else if (newAlpha < 0.0f)
            newAlpha = 0.0f;
        mColor = new Color(colorSpace, floats, newAlpha);
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
