package com.jiyuanime.particle.shape;

import com.jiyuanime.i18n.ActivatePowerModeProBundle;

import java.awt.*;

/**
 * 方形粒子
 */
public class SquareShape extends BaseParticleShape {
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public Shape getShape() {
        return Shape.SQUARE;
    }
    
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("setting.particle.shape.square");
    }
    
    @Override
    public void draw(Graphics2D g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}
