package com.jiyuanime.action;

/**
 * 震动开关
 * <p>
 * Created by suika on 15-12-22.
 */
public class ShakeSwitchAction extends BaseSwitchAction {

    @Override
    boolean getSwitchFieldValue() {
        return state.isShake;
    }

    @Override
    void setSwitchFieldValue(boolean isEnable) {
        state.isShake = isEnable;
    }
}
