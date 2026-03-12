package com.jiyuanime.config.converter;

import com.intellij.util.xmlb.Converter;
import com.jiyuanime.particle.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 类描述
 *
 * @author 成都深地领航能源科技有限公司
 */
public class ShapeConverter extends Converter<Shape> {
    @Override
    public @Nullable Shape fromString(@NotNull String code) {
        return Shape.getByCode(code);
    }

    @Override
    public @Nullable String toString(Shape shape) {
        return shape.getCode();
    }
}
