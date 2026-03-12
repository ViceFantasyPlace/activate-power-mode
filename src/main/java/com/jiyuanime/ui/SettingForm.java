package com.jiyuanime.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ColorPanel;
import com.jiyuanime.ActivatePowerModeManage;
import com.jiyuanime.config.Config;
import com.jiyuanime.i18n.ActivatePowerModeProBundle;
import com.jiyuanime.particle.ParticlePanel;
import com.jiyuanime.particle.shape.BaseParticleShape;
import com.jiyuanime.particle.shape.Shape;
import com.jiyuanime.particle.shape.ShapeRegistry;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

/**
 * @author zzqo
 */
public class SettingForm {
    private JLabel particleColorLabel;
    private JLabel particleMaxCountLabel;

    private ColorPanel colorChooser;
    private JPanel rootPanel;
    private JCheckBox colorAutoCheckBox;
    private JSlider particleSizeSlider;
    private JSlider particleMaxCountSlider;
    private JLabel maxCountValue;
    private JLabel sizeValue;
    private JTextField comboFont;
    private JButton setDefaultButton;
    private JButton chooseFontButton;
    private JTextField activateBorder;
    private JLabel particleSizeLabel;
    private JLabel comboFontLabel;
    private JLabel effectTriggerBorderLabel;
    private JLabel comboLabel;
    private JLabel animationIntervalLabel;
    private JSlider animationIntervalSlider;
    private JLabel intervalValue;
    private JButton resetButton;
    private ParticlePreviewPanel particlePreviewPanel;
    private JComboBox<BaseParticleShape> particleShapeComboBox;
    private JCheckBox mixModeCheckBox;
    private JLabel particleShapeLabel;
    private JLabel particlePreviewLabel;
    private JCheckBox isComboCheckBox;

    private final Config.State state = Config.getInstance().state;
    private final ActivatePowerModeManage manager = ActivatePowerModeManage.getInstance();

    public SettingForm() {
        initLocalization();
        initListener();
        initSetting();
    }

    private void initLocalization() {
        particleMaxCountLabel.setText(ActivatePowerModeProBundle.message("settings.particle.max.count"));
        particleColorLabel.setText(ActivatePowerModeProBundle.message("settings.particle.color"));
        colorAutoCheckBox.setText(ActivatePowerModeProBundle.message("settings.particle.color.auto"));
        particleSizeLabel.setText(ActivatePowerModeProBundle.message("settings.particle.size"));
        animationIntervalLabel.setText(ActivatePowerModeProBundle.message("settings.animation.interval"));
        comboFontLabel.setText(ActivatePowerModeProBundle.message("settings.combo.font"));
        chooseFontButton.setText(ActivatePowerModeProBundle.message("settings.from.file"));
        setDefaultButton.setText(ActivatePowerModeProBundle.message("settings.reset.to.default"));
        effectTriggerBorderLabel.setText(ActivatePowerModeProBundle.message("settings.effect.trigger.border"));
        comboLabel.setText(ActivatePowerModeProBundle.message("settings.combo.time"));
        resetButton.setText(ActivatePowerModeProBundle.message("settings.reset.to.default"));
        particleShapeLabel.setText(ActivatePowerModeProBundle.message("setting.particle.shape"));
        mixModeCheckBox.setText(ActivatePowerModeProBundle.message("setting.mix.mode.enabled"));
        particlePreviewLabel.setText(ActivatePowerModeProBundle.message("setting.particle.preview"));
        isComboCheckBox.setText(ActivatePowerModeProBundle.message("action.ComboSwitchAction.text"));
    }

    private void initListener() {
        colorAutoCheckBox.addItemListener(event -> {
            JCheckBox item = (JCheckBox) event.getItem();
            colorChooser.setSelectedColor(null);
            colorChooser.setEditable(!item.isSelected());
            particlePreviewPanel.setParticleColor(null);
        });
        isComboCheckBox.addItemListener(event -> {
            JCheckBox item = (JCheckBox) event.getItem();
            activateBorder.setEnabled(item.isSelected());
        });
        particleSizeSlider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            sizeValue.setText(String.valueOf(source.getValue()));
        });
        particleMaxCountSlider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            maxCountValue.setText(String.valueOf(source.getValue()));
        });
        animationIntervalSlider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            intervalValue.setText(String.valueOf(source.getValue()));
        });
        setDefaultButton.addActionListener(event -> this.comboFont.setText(Config.DEFAULT));
        resetButton.addActionListener(event -> {
            Config.getInstance().defaultInitState();
            initSetting();
        });
        chooseFontButton.addActionListener(event -> {
            VirtualFile currentDir = LocalFileSystem.getInstance().findFileByIoFile(new File(System.getProperty("user.home")));
            Project project = null;
            if (currentDir != null) {
                project = ProjectUtil.guessProjectForFile(currentDir);
            }
            FileChooserDescriptor fontFileConfig = FileChooserDescriptorFactory.createSingleFileDescriptor("ttf")
                                                                               .withTitle(ActivatePowerModeProBundle.message("settings.combo.font"));
            FileChooser.chooseFile(fontFileConfig, project, currentDir, file -> this.comboFont.setText(file.getPath()));
        });

        particleShapeComboBox.setModel(new DefaultComboBoxModel<>(ShapeRegistry.getAllShapes().toArray(new BaseParticleShape[0])));
        particleShapeComboBox.addItemListener(e -> {
            BaseParticleShape shape = (BaseParticleShape) particleShapeComboBox.getSelectedItem();
            if (shape != null) {
                particlePreviewPanel.setCurrentShape(shape);
            }
        });

        mixModeCheckBox.addItemListener(e -> {
            boolean enabled = mixModeCheckBox.isSelected();
            if (particleShapeComboBox != null) {
                particleShapeComboBox.setEnabled(!enabled);
            }
            particlePreviewPanel.setEnableMixMode(enabled);
        });

        particleSizeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            particlePreviewPanel.setParticleSize(source.getValue());
        });
    }

    public void initSetting() {
        particleMaxCountSlider.setValue(state.particleMaxCount);
        animationIntervalSlider.setValue(state.animationInterval);
        maxCountValue.setText(String.valueOf(state.particleMaxCount));
        particleSizeSlider.setValue(state.particleSize);
        sizeValue.setText(String.valueOf(state.particleSize));
        activateBorder.setText(String.valueOf(state.effectBorder));

        if (state.particleColor == null) {
            colorAutoCheckBox.setSelected(true);
        } else {
            colorChooser.setSelectedColor(state.particleColor);
        }

        if (Objects.isNull(state.fontFileLocation)) {
            this.comboFont.setText(ActivatePowerModeProBundle.message("settings.default"));
        } else {
            this.comboFont.setText(state.fontFileLocation);
        }

        particleShapeComboBox.setModel(new DefaultComboBoxModel<>(ShapeRegistry.getAllShapes().toArray(new BaseParticleShape[0])));
        BaseParticleShape shape = ShapeRegistry.getByShape(state.particleShape);
        particleShapeComboBox.setSelectedItem(shape);

        isComboCheckBox.setSelected(state.isCombo);
        activateBorder.setEnabled(state.isCombo);
        mixModeCheckBox.setSelected(state.shapeMixMode);
        particleShapeComboBox.setEnabled(!state.shapeMixMode);

        particlePreviewPanel.setCurrentShape(shape);
        particlePreviewPanel.setParticleSize(state.particleSize);
        particlePreviewPanel.setParticleColor(state.particleColor);
        particlePreviewPanel.setEnableMixMode(state.shapeMixMode);
    }

    public JComponent getRootComponent() {
        return rootPanel;
    }

    public boolean isModified() {
        try {
            boolean shapeChanged = !Comparing.equal(
                    particleShapeComboBox.getSelectedItem() != null ? ((BaseParticleShape) particleShapeComboBox.getSelectedItem()).getShape() : Shape.CIRCLE,
                    state.particleShape
            );

            boolean mixModeChanged = mixModeCheckBox.isSelected() != state.shapeMixMode;
            boolean isComboChanged = isComboCheckBox.isSelected() != state.isCombo;

            return !Comparing.equal(state.particleMaxCount, particleMaxCountSlider.getValue()) ||
                    !Comparing.equal(state.animationInterval, animationIntervalSlider.getValue()) ||
                    !Comparing.equal(state.particleSize, particleSizeSlider.getValue()) ||
                    !Comparing.strEqual(String.valueOf(state.effectBorder), activateBorder.getText()) ||
                    !Comparing.strEqual(state.fontFileLocation == null ? Config.DEFAULT : state.fontFileLocation, comboFont.getText()) ||
                    !Comparing.equal(state.particleColor, colorAutoCheckBox.isSelected() ? null : colorChooser.getSelectedColor()) ||
                    shapeChanged ||
                    isComboChanged ||
                    mixModeChanged;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public void apply() throws ConfigurationException {
        int oldAnimationInterval = state.animationInterval;

        state.particleMaxCount = particleMaxCountSlider.getValue();
        state.animationInterval = animationIntervalSlider.getValue();
        state.particleSize = particleSizeSlider.getValue();
        if (!colorAutoCheckBox.isSelected() && colorChooser.getSelectedColor() == null) {
            throw new ConfigurationException(ActivatePowerModeProBundle.message("validation.particle.color.required"));
        }
        state.particleColor = colorAutoCheckBox.isSelected() ? null : colorChooser.getSelectedColor();

        if ("".equals(comboFont.getText())) {
            throw new ConfigurationException(ActivatePowerModeProBundle.message("validation.combo.font.must.be.set"));
        } else if (!Comparing.strEqual(Config.DEFAULT, comboFont.getText()) && !new File(comboFont.getText()).exists()) {
            throw new ConfigurationException(ActivatePowerModeProBundle.message("validation.font.file.must.exist"));
        }

        try {
            state.effectBorder = Integer.parseInt(activateBorder.getText());
        } catch (NumberFormatException e) {
            throw new ConfigurationException(ActivatePowerModeProBundle.message("validation.activate.border.should.be.number"));
        }

        String selectFonts = comboFont.getText().equals(Config.DEFAULT) ? null : comboFont.getText();
        if (!Comparing.strEqual(selectFonts, state.fontFileLocation)) {
            // 检测到字体变化，重启 power mode 组件
            state.fontFileLocation = selectFonts;
            for (Project project : ProjectManager.getInstance().getOpenProjects()) {
                manager.clearComboView(project);
                manager.initComboView(project);
            }
        }

        // 如果动画间隔发生变化，重启粒子动画任务
        if (state.isEnable && oldAnimationInterval != state.animationInterval) {
            ParticlePanel.getInstance().restartTask();
        }

        // 保存粒子形状配置
        BaseParticleShape selectedShape = (BaseParticleShape) particleShapeComboBox.getSelectedItem();
        if (selectedShape != null) {
            state.particleShape = selectedShape.getShape();
        }

        // 保存混合模式配置
        state.shapeMixMode = mixModeCheckBox.isSelected();

    }

}
