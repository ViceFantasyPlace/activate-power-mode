package com.jiyuanime.config.converter;

import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * Color 类型的 XML 转换器
 */
public class ColorConverter extends Converter<Color> {
    
    @Override
    public @Nullable Color fromString(@NotNull String value) {
        if (value.trim().isEmpty()) {
            return null;
        }
        try {
            // 格式：#RRGGBB 或 RRGGBB
            value = value.replace("#", "").trim();
            if (value.length() == 6) {
                int r = Integer.parseInt(value.substring(0, 2), 16);
                int g = Integer.parseInt(value.substring(2, 4), 16);
                int b = Integer.parseInt(value.substring(4, 6), 16);
                return new Color(r, g, b);
            } else if (value.length() == 8) {
                // 包含透明度 #AARRGGBB
                int a = Integer.parseInt(value.substring(0, 2), 16);
                int r = Integer.parseInt(value.substring(2, 4), 16);
                int g = Integer.parseInt(value.substring(4, 6), 16);
                int b = Integer.parseInt(value.substring(6, 8), 16);
                return new Color(r, g, b, a);
            }
        } catch (NumberFormatException e) {
            // 解析失败，返回 null
        }
        return null;
    }
    
    @Override
    public @Nullable String toString(@NotNull Color color) {
        // 返回 #RRGGBB 格式
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}
