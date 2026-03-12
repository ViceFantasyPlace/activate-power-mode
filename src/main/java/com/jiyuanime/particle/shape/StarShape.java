package com.jiyuanime.particle.shape;

import com.jiyuanime.i18n.ActivatePowerModeProBundle;

import java.awt.*;

/**
 * 星形粒子（五角星）
 */
public class StarShape extends BaseParticleShape {
    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public Shape getShape() {
        return Shape.STAR;
    }
    
    @Override
    public String getDisplayName() {
        return ActivatePowerModeProBundle.message("setting.particle.shape.star");
    }
    
    @Override
    public void draw(Graphics2D g, int x, int y, int size) {
        // 计算五角星的 10 个顶点
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        double angle = Math.PI / 5;
        // 外半径
        int r1 = size / 2;
        // 内半径
        int r2 = r1 / 2;
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        
        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? r1 : r2;
            double theta = i * angle - Math.PI / 2;
            xPoints[i] = centerX + (int)(r * Math.cos(theta));
            yPoints[i] = centerY + (int)(r * Math.sin(theta));
        }

        g.fillPolygon(xPoints, yPoints, 10);
    }
}
