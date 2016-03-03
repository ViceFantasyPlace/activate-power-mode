package com.jiyuanime;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

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

        state.IS_SPARK = true;

        state.IS_SHAKE = true;

        state.IS_COLORFUL = false;

    }

    public static Config getInstance() {
        return ServiceManager.getService(Config.class);
    }

    public static final class State {

        /**
         * 是否震动
         */
        public boolean IS_SHAKE;

        /**
         * 是否显示火花
         */
        public boolean IS_SPARK;

        /**
         * 色彩鲜艳的配置项
         */
        public boolean IS_COLORFUL;
    }


}
