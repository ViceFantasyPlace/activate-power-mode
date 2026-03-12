package com.jiyuanime.particle.shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 形状注册表（工厂模式）
 * <p>
 * Created by zzqo on 2026/03/12.
 */
public class ShapeRegistry {
    private static final Map<Shape, BaseParticleShape> SHAPES = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        // 注册所有内置形状
        register(new CircleShape());
        register(new SquareShape());
        register(new TriangleShape());
        register(new StarShape());
        register(new DiamondShape());
    }

    /**
     * 注册一个形状
     */
    public static void register(BaseParticleShape shape) {
        SHAPES.put(shape.getShape(), shape);
    }

    /**
     * 根据枚举获取形状
     */
    public static BaseParticleShape getByShape(Shape shape) {
        return SHAPES.get(shape);
    }

    /**
     * 获取所有已注册的形状
     */
    public static Collection<BaseParticleShape> getAllShapes() {
        return SHAPES.values().stream().sorted(Comparator.comparing(BaseParticleShape::getOrder)).toList();
    }

    /**
     * 随机获取一个形状
     */
    public static BaseParticleShape getRandomShape() {
        List<BaseParticleShape> shapeList = new ArrayList<>(SHAPES.values());
        if (shapeList.isEmpty()) {
            return null;
        }

        return shapeList.get(RANDOM.nextInt(shapeList.size()));
    }

    /**
     * 获取形状数量
     */
    public static int count() {
        return SHAPES.size();
    }
}
