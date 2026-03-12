package com.jiyuanime.ui;

import com.intellij.ui.JBColor;
import com.jiyuanime.particle.shape.BaseParticleShape;
import com.jiyuanime.particle.shape.Shape;
import com.jiyuanime.particle.shape.ShapeRegistry;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * 粒子预览面板
 *
 * @author zzqo
 */
public class ParticlePreviewPanel extends JPanel {

    private BaseParticleShape particleShape;
    private Color particleColor;
    private boolean enableMixMode;
    private int particleSize;

    public ParticlePreviewPanel() {
        this.particleShape = ShapeRegistry.getByShape(Shape.CIRCLE);
        this.enableMixMode = false;
        this.particleSize = 6;
        setBackground(new JBColor(new Color(0x00FFFFFF, true), new Color(0x00FFFFFF, true)));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = particleColor;
        if (particleColor == null) {
            color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        }
        
        if (enableMixMode) {
            // 混合模式：绘制多个不同形状的粒子（5 列 x2 行）
            int cols = 5;
            int rows = 2;
            int size = particleSize * 2;
            int gap = size / 2;

            // 计算起始位置，使整体居中
            int totalWidth = cols * size + (cols - 1) * gap;
            int totalHeight = rows * size + (rows - 1) * gap;
            int startX = (getWidth() - totalWidth) / 2;
            int startY = (getHeight() - totalHeight) / 2;

            for (int i = 0; i < cols * rows; i++) {
                int col = i % cols;
                int row = i / cols;
                int x = startX + col * (size + gap);
                int y = startY + row * (size + gap);

                // 每个粒子使用不同的随机形状和颜色
                BaseParticleShape shape = ShapeRegistry.getRandomShape();

                g2d.setColor(new JBColor(color, color));

                if (shape != null) {
                    shape.draw(g2d, x, y, size);
                }
            }
        } else {
            // 单一形状模式：只绘制一个图形，居中显示
            int size = particleSize * 2;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            g2d.setColor(new JBColor(color, color));

            if (particleShape != null) {
                particleShape.draw(g2d, x, y, size);
            }
        }

        g2d.dispose();
    }


    /**
     * 粒子形状
     *
     * @param particleShape 粒子形状
     */
    public void setCurrentShape(@NotNull BaseParticleShape particleShape) {
        this.particleShape = particleShape;
        repaint();
    }

    /**
     * 设置粒子大小
     *
     * @param particleSize 粒子大小
     */
    public void setParticleSize(int particleSize) {
        this.particleSize = particleSize;
        repaint();
    }

    /**
     * 设置粒子颜色
     *
     * @param particleColor 粒子颜色
     */
    public void setParticleColor(Color particleColor) {
        this.particleColor = particleColor;
        repaint();
    }

    /**
     * 设置是否开启混合模式
     *
     * @param enableMixMode 混合模式
     */
    public void setEnableMixMode(boolean enableMixMode) {
        this.enableMixMode = enableMixMode;
        repaint();
    }
}
