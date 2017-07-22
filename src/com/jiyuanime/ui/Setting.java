package com.jiyuanime.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.Comparing;
import com.jiyuanime.config.Config;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Setting implements Configurable {

    private JCheckBox enableCheckBox;
    private JCheckBox shakeCheckBox;
    private JCheckBox particleCheckBox;
    private JCheckBox comboCheckBox;
    private JCheckBox colorfulCheckBox;
    private JTextField particleMaxCountTextField;
    private JPanel rootPanel;

    private Config.State state = Config.getInstance().state;

    @Nls
    @Override
    public String getDisplayName() {
        return "activate-power-mode";
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        initSetting();

        return this.rootPanel;
    }

    @Override
    public boolean isModified() {

        try {
            return !Comparing.equal(state.IS_ENABLE, enableCheckBox.isSelected()) ||
                    !Comparing.equal(state.IS_COMBO, comboCheckBox.isSelected()) ||
                    !Comparing.equal(state.IS_SHAKE, shakeCheckBox.isSelected()) ||
                    !Comparing.equal(state.IS_SPARK, particleCheckBox.isSelected()) ||
                    !Comparing.equal(state.IS_COLORFUL, colorfulCheckBox.isSelected()) ||
                    !Comparing.equal(state.PARTICLE_MAX_COUNT, Integer.parseInt(particleMaxCountTextField.getText()));
        } catch (NumberFormatException $ex) {
            return true;
        }

    }

    @Override
    public void apply() throws ConfigurationException {

        try {
            int particle_max_count = Integer.parseInt(particleMaxCountTextField.getText());
            if(particle_max_count < 0) {
                throw new ConfigurationException("The 'particle max count' field must be greater than 0");
            }
            state.PARTICLE_MAX_COUNT = particle_max_count;
        } catch (NumberFormatException $ex) {
            throw new ConfigurationException("The 'particle max count' field format error.");
        }

        state.IS_ENABLE = enableCheckBox.isSelected();
        state.IS_COMBO = comboCheckBox.isSelected();
        state.IS_SHAKE = shakeCheckBox.isSelected();
        state.IS_SPARK = particleCheckBox.isSelected();
        state.IS_COLORFUL = colorfulCheckBox.isSelected();
    }

    private void initSetting() {
        enableCheckBox.setSelected(state.IS_ENABLE);
        comboCheckBox.setSelected(state.IS_COMBO);
        shakeCheckBox.setSelected(state.IS_SHAKE);
        particleCheckBox.setSelected(state.IS_SPARK);
        colorfulCheckBox.setSelected(state.IS_COLORFUL);
        particleMaxCountTextField.setText(String.valueOf(state.PARTICLE_MAX_COUNT));
    }
}
