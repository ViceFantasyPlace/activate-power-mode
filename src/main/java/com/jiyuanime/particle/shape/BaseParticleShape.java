package com.jiyuanime.particle.shape;

import java.awt.*;

/**
 * 粒子形状抽象基类
 * <p>
 * Created by zzqo on 2026/03/12.
 */
public abstract class BaseParticleShape {
    
    /**
     * 绘制形状的核心方法
     * @param g Graphics2D 对象
     * @param x X 坐标
     * @param y Y 坐标
     * @param size 尺寸大小
     */
    public abstract void draw(Graphics2D g, int x, int y, int size);

    /**
     * 获取排序标识
     * @return 排序字符串
     */
    public abstract int getOrder();
    
    /**
     * 获取形状唯一标识
     */
    public abstract Shape getShape();
    
    /**
     * 获取显示名称（支持国际化）
     */
    public abstract String getDisplayName();
    
    @Override
    public String toString() {
        return getDisplayName();
    }
}
