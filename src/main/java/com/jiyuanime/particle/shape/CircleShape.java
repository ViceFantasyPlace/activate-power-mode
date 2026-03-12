package com.jiyuanime.particle.shape;

import com.jiyuanime.i18n.ActivatePowerModeProBundle;

import java.awt.*;

/**
 * 圆形粒子
 */
public class CircleShape extends BaseParticleShape {

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Shape getShape() {
        return Shape.CIRCLE;
    }
    
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("setting.particle.shape.circle");
    }
    
    @Override
    public void draw(Graphics2D g, int x, int y, int size) {
        g.fillOval(x, y, size, size);
    }
}
