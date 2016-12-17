package com.jiyuanime.particle;

import java.awt.*;

/**
 * 粒子位置计算工具
 * <p>
 * Created by KADO on 15/12/16.
 */
public class ParticlePositionCalculateUtil {

    public static int getParticleAreaWidth(int fontSize) {
        return fontSize * 20;
    }

    public static int getParticleAreaHeight(int fontSize) {
        return fontSize * 20;
    }

    public static Point getParticlePositionOnArea(int areaWidth, int areaHeight) {
        return new Point(areaWidth / 2, areaHeight / 2);
    }

    public static Point getParticleAreaPositionOnEditorArea(Point caretPoint, int areaWidth, int areaHeight) {
        return new Point(caretPoint.x - (areaWidth / 2), caretPoint.y - (areaHeight / 2));
    }
}
