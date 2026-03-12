package com.jiyuanime.particle.shape;

/**
 * 粒子形状枚举类
 * <p>
 * 定义可用的各种粒子形状
 * </p>
 * @author zzqo
 */
public enum Shape {

    // 圆形粒子
    CIRCLE("circle"),
    // 方形粒子
    SQUARE("square"),
    // 三角形粒子
    TRIANGLE("triangle"),
    // 菱形粒子
    Diamond("diamond"),
    // 星形粒子
    STAR("star");

    Shape(String code) {
        this.code = code;
    }

    private final String code;

    public String getCode() {
        return code;
    }

    public static Shape getByCode(String code) {
        for (Shape e : Shape.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static Shape getByCodeDefault(String code, Shape shape) {
        for (Shape e : Shape.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return shape;
    }
    
}
