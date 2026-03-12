package com.jiyuanime.particle.shape;

import com.jiyuanime.i18n.ActivatePowerModeProBundle;

import java.awt.*;

/**
 * 菱形粒子
 */
public class DiamondShape extends BaseParticleShape {
    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public Shape getShape() {
        return Shape.Diamond;
    }
    
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("setting.particle.shape.diamond");
    }
    
    @Override
    public void draw(Graphics2D g, int x, int y, int size) {
        int[] xPoints = {x + size/2, x + size, x + size/2, x};
        int[] yPoints = {y, y + size/2, y + size, y + size/2};
        g.fillPolygon(xPoints, yPoints, 4);
    }
}
