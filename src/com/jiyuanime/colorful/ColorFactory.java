package com.jiyuanime.colorful;

import com.intellij.ui.JBColor;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by hentai_mew on 15-12-24.
 * 随机颜色工厂
 */
public class ColorFactory {

    private static final List<Color> colors = new ArrayList<>();

    private static void fill() {
        if (colors.size() == 0) {
            colors.add(JBColor.RED);
            colors.add(JBColor.ORANGE);
            colors.add(JBColor.YELLOW);
            colors.add(JBColor.GREEN);
            colors.add(JBColor.CYAN);
            colors.add(JBColor.BLUE);
            colors.add(JBColor.MAGENTA);
        }
    }

    public static List<Color> getColors() {
        fill();
        return colors;
    }

    private static Color getOne() {
        int max = getColors().size();
        int min = 0;
        Random random = new Random();
        int index = random.nextInt(max) % (max - min + 1) + min;
        return colors.get(index);
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
