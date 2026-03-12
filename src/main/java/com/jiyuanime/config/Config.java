package com.jiyuanime.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.OptionTag;
import com.jiyuanime.config.converter.ColorConverter;
import com.jiyuanime.config.converter.ShapeConverter;
import com.jiyuanime.i18n.ActivatePowerModeProBundle;
import com.jiyuanime.particle.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * 配置文件
 * Created by KADO on 15/12/17.
 *
 * @author zzqo
 */
@State(name = "ActivatePowerModePro", storages = {@Storage("ActivatePowerModePro.xml")})
public class Config implements PersistentStateComponent<Config.State> {
    public static final String DEFAULT = ActivatePowerModeProBundle.message("settings.default");

    @Nullable
    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    public State state = new State();

    public Config() {
        defaultInitState();
    }

    public void defaultInitState() {
        // 是否开启
        state.isEnable = true;
        // 是否震动
        state.isShake = false;
        // 是否显示火花
        state.isSpark = true;
        // 色彩鲜艳的配置项
        state.isColorful = true;
        // 开启效果的界限
        state.effectBorder = 30;
        // 是否开启连击
        state.isCombo = false;
        // 每次生成的粒子量
        state.particleMaxCount = 5;
        // 粒子颜色，为 null 则代表 auto
        state.particleColor = null;
        // 粒子大小
        state.particleSize = 6;
        // 粒子动画刷新间隔（ms）
        state.animationInterval = 35;
        // 字体文件位置
        state.fontFileLocation = null;
        // 粒子形状 ID（默认圆形）
        state.particleShape = Shape.CIRCLE;
        // 是否启用混合模式
        state.shapeMixMode = false;
    }

    public static Config getInstance() {
        return ApplicationManager.getApplication().getService(Config.class);
    }

    public static final class State {

        /**
         * 是否开启
         */
        public boolean isEnable = true;

        /**
         * 是否震动
         */
        public boolean isShake = false;

        /**
         * 是否显示火花
         */
        public boolean isSpark = true;

        /**
         * 色彩鲜艳的配置项
         */
        public boolean isColorful = false;

        /**
         * 开启效果的界限
         */
        public int effectBorder = 30;

        /**
         * 敲击的时间间隔
         */
        public long clickTimeInterval = 7777;

        /**
         * 敲击的最大连击数
         */
        public int maxClickCombo = 0;

        /**
         * 是否开启连击
         */
        public boolean isCombo = false;

        /**
         * 每次生成的粒子量
         */
        public int particleMaxCount = 5;

        /**
         * 粒子颜色，为 null 则代表 auto
         */
        @OptionTag(converter = ColorConverter.class)
        public Color particleColor = null;

        /**
         * 粒子大小
         */
        public int particleSize = 6;

        /**
         * 粒子动画刷新间隔（ms）
         */
        public int animationInterval = 35;

        /**
         * 字体文件位置
         */
        public String fontFileLocation = null;

        /**
         * 粒子形状 ID（默认圆形）
         */
        @OptionTag(converter = ShapeConverter.class)
        public Shape particleShape = Shape.CIRCLE;

        /**
         * 是否启用混合模式
         */
        public boolean shapeMixMode = false;
    }


}
