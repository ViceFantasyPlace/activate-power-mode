package com.jiyuanime.colorful;

import com.intellij.ui.JBColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hentai_mew on 15-12-24.
 * 随机颜色工厂
 */
public class ColorFactory {

    private ColorFactory() {}

    private static final Random RANDOM = new Random();

    private static final List<Color> COLORS = new ArrayList<>();

    private static void fill() {
        if (COLORS.isEmpty()) {
            COLORS.add(JBColor.RED);
            COLORS.add(JBColor.ORANGE);
            COLORS.add(JBColor.YELLOW);
            COLORS.add(JBColor.GREEN);
            COLORS.add(JBColor.CYAN);
            COLORS.add(JBColor.BLUE);
            COLORS.add(JBColor.MAGENTA);
        }
    }

    public static List<Color> getColors() {
        fill();
        return COLORS;
    }

    private static Color getOne() {
        int max = getColors().size();
        int min = 0;
        int index = RANDOM.nextInt(max) % (max - min + 1) + min;
        return COLORS.get(index);
    }

    /**
     * 获取一个随机色
     *
     * @return 随机Color对象
     */
    public static Color gen() {
        return getOne();
    }

}
