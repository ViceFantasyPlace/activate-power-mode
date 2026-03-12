package com.jiyuanime.particle;

import com.intellij.ui.JBColor;
import com.jiyuanime.particle.shape.BaseParticleShape;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * 粒子类
 * <p>
 * Created by zzqo on 15/12/15.
 * @author zzqo
 */
public class ParticleView {
    public Color mColor;
    public float mAlpha;
    public float x, y;
    public float vX, vY;

    public Point mPoint;
    
    private BaseParticleShape shape;

    private boolean isEnable = false;

    public ParticleView(Point point, Color color, boolean isEnable) {
        this(point, color, isEnable, null);
    }
    
    public ParticleView(Point point, Color color, boolean isEnable, BaseParticleShape shape) {
        init(point, color, isEnable, shape);
    }

    public void init(Point point, Color color, boolean isEnable) {
        init(point, color, isEnable, null);
    }
    
    public void init(Point point, Color color, boolean isEnable, BaseParticleShape shape) {
        this.isEnable = isEnable;
        this.mPoint = point;
        this.mColor = color;
        this.mAlpha = 1.0f;
        this.shape = shape;

        x = (float) this.mPoint.getX();
        y = (float) this.mPoint.getY();

        vX = (float) (-1.0 + ((Math.round(Math.random() * 100)) / 100.0) * 2);
        vY = (float) (-3.5 + ((Math.round(Math.random() * 100)) / 100.0) * 2);
    }

    public void reset(Point point, Color color, boolean isEnable) {
        init(point, color, isEnable, null);
    }
    
    public void reset(Point point, Color color, boolean isEnable, BaseParticleShape shape) {
        init(point, color, isEnable, shape);
    }

    public void update() {
        this.mAlpha *= 0.96f;

        vY += 0.075f;

        x = x + vX;
        y = y + vY;

        ColorSpace colorSpace = mColor.getColorSpace();
        float[] floats = mColor.getColorComponents(null);
        float newAlpha = mAlpha + 0.3f;
        if (newAlpha > 1.0f) {
            newAlpha = 1.0f;
        } else if (newAlpha < 0.0f) {
            newAlpha = 0.0f;
        }
        mColor = new JBColor(new Color(colorSpace, floats, newAlpha), new Color(colorSpace, floats, newAlpha));
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
    
    public BaseParticleShape getShape() {
        return shape;
    }
    
    public void setShape(BaseParticleShape shape) {
        this.shape = shape;
    }
}
