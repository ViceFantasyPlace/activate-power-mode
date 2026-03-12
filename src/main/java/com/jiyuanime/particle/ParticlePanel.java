package com.jiyuanime.particle;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import com.jiyuanime.config.Config;
import com.jiyuanime.particle.shape.BaseParticleShape;
import com.jiyuanime.particle.shape.ShapeRegistry;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 粒子容器
 * <p>
 * Created by zzqo on 15/12/15.
 * @author zzqo
 */
public class ParticlePanel implements Runnable, Border {
    private final Logger log = Logger.getInstance(ParticlePanel.class);

    private static final JBColor BACKGROUND_COLOR = new JBColor(
            new Color(0x00FFFFFF, true),
            new Color(0x00FFFFFF, true)
    );
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> task;
    private static final int MAX_PARTICLE_COUNT = 100;
    private static ParticlePanel particlePanel;
    private int mParticleIndex = 0;
    private ConcurrentHashMap<String, ParticleView> particleViews = new ConcurrentHashMap<>();
    private JComponent nowEditorComponent;
    private int particleAreaWidth, particleAreaHeight;
    private BufferedImage particleAreaImage;
    private Graphics2D particleAreaGraphics;
    private final Point caretPoint = new Point();
    private final Point particleAreaSpeed = new Point();
    private volatile Point currentCaretPosition = null;
    private Thread thread;
    private volatile boolean isEnable = false;
    private final Config.State state = Config.getInstance().state;

    public static ParticlePanel getInstance() {
        if (particlePanel == null) {
            particlePanel = new ParticlePanel();
        }
        return particlePanel;
    }

    private ParticlePanel() {
    }

    @Override
    public void run() {
        if (isEnable) {
            task = SCHEDULER.scheduleAtFixedRate(this::eventLoop, 0, state.animationInterval, TimeUnit.MILLISECONDS);
        }
    }

    public void eventLoop() {
        if (particleAreaGraphics != null) {
            particleAreaGraphics.setBackground(BACKGROUND_COLOR);
            particleAreaGraphics.clearRect(0, 0, particleAreaWidth * 2, particleAreaHeight * 2);

            for (String key : particleViews.keySet()) {
                ParticleView particleView = particleViews.get(key);
                if (particleView != null && particleView.isEnable()) {
                    particleAreaGraphics.setColor(particleView.mColor);
                    
                    BaseParticleShape shape = particleView.getShape();
                    if (shape == null) {
                        shape = ShapeRegistry.getByShape(state.particleShape);
                    }
                    
                    shape.draw(
                        particleAreaGraphics,
                        (int) particleView.x,
                        (int) particleView.y,
                        state.particleSize
                    );

                    update(particleView);
                }
            }

            if (nowEditorComponent != null) {
                nowEditorComponent.repaint();
            }
        }

        if (!isEnable && task != null) {
            task.cancel(true);
        }
    }

    public void restartTask() {
        if (isEnable) {
            if (task != null) {
                task.cancel(false);
            }
            task = SCHEDULER.scheduleAtFixedRate(this::eventLoop, 0, state.animationInterval, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (particleAreaImage == null) {
            return;
        }

        Graphics2D graphics2 = (Graphics2D) g;
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        Point point = ParticlePositionCalculateUtil.getParticleAreaPositionOnEditorArea(caretPoint, particleAreaWidth, particleAreaHeight);
        graphics2.drawImage(particleAreaImage, point.x, point.y, c);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return JBUI.emptyInsets();
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    public void init(JComponent jComponent) {
        if (particleViews == null) {
            mParticleIndex = 0;
            particleViews = new ConcurrentHashMap<>();
        }

        if (nowEditorComponent != null) {
            nowEditorComponent.setBorder(null);
        }

        nowEditorComponent = jComponent;

        updateDrawer(jComponent);
    }

    public void reset(JComponent jComponent) {
        clear();
        init(jComponent);
        setEnableAction(true);
    }

    public void clear() {
        isEnable = false;

        if (task != null) {
            task.cancel(true);
            task = null;
        }

        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            thread = null;
        }

        if (nowEditorComponent != null) {
            nowEditorComponent.setBorder(null);
            nowEditorComponent = null;
        }

        if (particleAreaGraphics != null) {
            particleAreaGraphics = null;
        }

        if (particleAreaImage != null) {
            particleAreaImage = null;
        }
    }

    public void destroy() {
        clear();

        if (particleViews != null) {
            particleViews.clear();
        }
        particleViews = null;
    }

    public void update(ParticleView particleView) {
        if (particleView.mAlpha <= 0.1) {
            particleView.setEnable(false);
            return;
        }

        particleView.update();
    }

    public void updateDrawer(Component jComponent) {
        particleAreaWidth = ParticlePositionCalculateUtil.getParticleAreaWidth(jComponent.getFont().getSize());
        particleAreaHeight = ParticlePositionCalculateUtil.getParticleAreaHeight(jComponent.getFont().getSize());

        particleAreaImage = UIUtil.createImage(jComponent, particleAreaWidth, particleAreaHeight, BufferedImage.TYPE_INT_ARGB);
        particleAreaGraphics = particleAreaImage.createGraphics();
        particleAreaImage = particleAreaGraphics.getDeviceConfiguration().createCompatibleImage(particleAreaWidth, particleAreaHeight, Transparency.TRANSLUCENT);
        particleAreaGraphics.dispose();
        particleAreaGraphics = particleAreaImage.createGraphics();
    }

    private void particlesDeviation(Point speed) {
        for (String key : particleViews.keySet()) {
            ParticleView particle = particleViews.get(key);
            particle.setX(particle.x - speed.x);
            particle.setY(particle.y - speed.y);
        }
    }

    public void setEnableAction(boolean isEnable) {
        this.isEnable = isEnable;
        if (this.isEnable) {
            if (particleAreaImage != null && particleAreaGraphics != null && nowEditorComponent != null) {
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(this);
                }
                thread.start();
            } else {
                this.isEnable = false;
                log.warn("还没初始化 ParticlePanel");
            }
        } else {
            destroy();
        }
    }

    public void sparkAtPosition(@NotNull Point position, Color color, int fontSize) {
        particleAreaSpeed.setLocation(position.x - caretPoint.x, position.y - caretPoint.y);
        particlesDeviation(particleAreaSpeed);
        caretPoint.setLocation(position.x, position.y);

        particleAreaWidth = ParticlePositionCalculateUtil.getParticleAreaWidth(fontSize);
        particleAreaHeight = ParticlePositionCalculateUtil.getParticleAreaHeight(fontSize);

        Point particlePoint = ParticlePositionCalculateUtil.getParticlePositionOnArea(particleAreaWidth, particleAreaHeight);
        int particleNumber = state.particleMaxCount;

        for (int i = 0; i < particleNumber; i++) {
            BaseParticleShape shape;
            if (state.shapeMixMode) {
                shape = ShapeRegistry.getRandomShape();
            } else {
                shape = ShapeRegistry.getByShape(state.particleShape);
            }
            
            if (mParticleIndex >= MAX_PARTICLE_COUNT) {
                particleViews.get(String.valueOf(mParticleIndex % MAX_PARTICLE_COUNT)).reset(particlePoint, color, true, shape);
            } else {
                ParticleView particleView = new ParticleView(particlePoint, color, true, shape);
                particleViews.put(String.valueOf(mParticleIndex), particleView);
            }

            if (mParticleIndex < MAX_PARTICLE_COUNT * 10) {
                mParticleIndex++;
            } else {
                mParticleIndex = MAX_PARTICLE_COUNT;
            }
        }
    }

    public void sparkAtPositionAction(Color color, int fontSize) {
        if (currentCaretPosition != null) {
            sparkAtPosition(currentCaretPosition, color, fontSize);
            currentCaretPosition = null;
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public JComponent getNowEditorJComponent() {
        return nowEditorComponent;
    }

    public void setCurrentCaretPosition(Point currentCaretPosition) {
        this.currentCaretPosition = currentCaretPosition;
    }

}
