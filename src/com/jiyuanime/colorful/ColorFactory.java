package com.jiyuanime.colorful;

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
            colors.add(Color.RED);
            colors.add(Color.ORANGE);
            colors.add(Color.YELLOW);
            colors.add(Color.GREEN);
            colors.add(Color.CYAN);
            colors.add(Color.BLUE);
            colors.add(Color.MAGENTA);
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
