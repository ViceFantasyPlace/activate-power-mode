package com.jiyuanime.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * 配置文件
 * Created by KADO on 15/12/17.
 */
@State(
    name = "activate-power-mode",
    storages = {
            @Storage(
                    id = "activate-power-mode",
                    file = "$APP_CONFIG$/activate-power-mode_setting.xml"
            )
    }
)
public class Config implements PersistentStateComponent<Config.State> {

    @Nullable
    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void loadState(State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    public State state = new State();

    public Config() {

        defaultInitState();

    }

    public void defaultInitState() {

        state.IS_ENABLE = true;

        state.IS_SPARK = true;

        state.IS_SHAKE = true;

        state.IS_COMBO = true;

        state.IS_COLORFUL = false;

        state.PARTICLE_MAX_COUNT = 5;

        state.PARTICLE_COLOR = null;
    }

    public static Config getInstance() {
        return ServiceManager.getService(Config.class);
    }

    public static final class State {

        /**
         * 是否开启
         */
        public boolean IS_ENABLE = true;

        /**
         * 是否震动
         */
        public boolean IS_SHAKE = true;

        /**
         * 是否显示火花
         */
        public boolean IS_SPARK = true;

        /**
         * 色彩鲜艳的配置项
         */
        public boolean IS_COLORFUL = false;

        /**
         * 开启效果的界限
         */
        public int OPEN_FUNCTION_BORDER = 30;

        /**
         * 敲击的时间间隔
         */
        public long CLICK_TIME_INTERVAL = 7777;

        /**
         * 敲击的最大连击数
         */
        public int MAX_CLICK_COMBO;

        /**
         * 是否开启 Combo
         */
        public boolean IS_COMBO = true;

        /**
         * 每次生成的粒子量
         */
        public int PARTICLE_MAX_COUNT = 5;

        /**
         * 粒子颜色,为null则代表auto
         */
        public Color PARTICLE_COLOR = null;
    }


}
