package com.jiyuanime.particle.shape;

import com.jiyuanime.i18n.ActivatePowerModeProBundle;

import java.awt.*;

/**
 * 三角形粒子
 */
public class TriangleShape extends BaseParticleShape {
    @Override
    public int getOrder() {
        return 3;
    }
    
    @Override
    public Shape getShape() {
        return Shape.TRIANGLE;
    }
    
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("setting.particle.shape.triangle");
    }
    
    @Override
    public void draw(Graphics2D g, int x, int y, int size) {
        int[] xPoints = {x + size/2, x, x + size};
        int[] yPoints = {y, y + size, y + size};
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
