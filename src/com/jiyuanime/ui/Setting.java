package com.jiyuanime.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.Comparing;
import com.intellij.ui.ColorPanel;
import com.jiyuanime.config.Config;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Setting implements Configurable {

    private JTextField particleMaxCountTextField;
    private ColorPanel colorChooser;
    private JPanel rootPanel;
    private JCheckBox colorAutoCheckBox;

    private Config.State state = Config.getInstance().state;

    @Nls
    @Override
    public String getDisplayName() {
        return "activate-power-mode";
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        initListener();

        initSetting();

        return this.rootPanel;
    }

    @Override
    public boolean isModified() {

        try {
            return !Comparing.equal(state.PARTICLE_MAX_COUNT, Integer.parseInt(particleMaxCountTextField.getText())) ||
                    !Comparing.equal(state.PARTICLE_COLOR, colorAutoCheckBox.isSelected() ? null : colorChooser.getSelectedColor());
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

        if(!colorAutoCheckBox.isSelected() && colorChooser.getSelectedColor() == null) {
            throw new ConfigurationException("'particle color' is not choose.'");
        }

        state.PARTICLE_COLOR = colorAutoCheckBox.isSelected() ? null : colorChooser.getSelectedColor();
    }

    private void initListener() {
        colorAutoCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox item = (JCheckBox) e.getItem();

                colorChooser.setSelectedColor(null);
                colorChooser.setEditable(!item.isSelected());
            }
        });
    }

    private void initSetting() {
        particleMaxCountTextField.setText(String.valueOf(state.PARTICLE_MAX_COUNT));
        if(state.PARTICLE_COLOR == null) {
            colorAutoCheckBox.setSelected(true);
        } else {
            colorChooser.setSelectedColor(state.PARTICLE_COLOR);
        }
    }
}
