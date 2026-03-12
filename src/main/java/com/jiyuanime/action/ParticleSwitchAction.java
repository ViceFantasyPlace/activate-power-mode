package com.jiyuanime.action;

/**
 * 粒子开关
 * <p>
 * Created by suika on 15-12-22.
 */
public class ParticleSwitchAction extends BaseSwitchAction {
    @Override
    boolean getSwitchFieldValue() {
        return state.isSpark;
    }

    @Override
    void setSwitchFieldValue(boolean isEnable) {
        state.isSpark = isEnable;
    }
}
