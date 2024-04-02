/*
 * Copyright 2022-2024 Wafer EX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package aq.waferex.hangaremulator.ui.components.wrappers.canvas;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.graphics.swing.HangarSwingGraphicsProvider;
import aq.waferex.hangaremulator.ui.listeners.HangarMouseListener;
import aq.waferex.hangaremulator.utils.CanvasWrapperUtils;
import aq.waferex.hangaremulator.utils.SystemUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class HangarCanvasWrapperSwing extends HangarCanvasWrapper {
    // TODO: add quality support
    private BufferedImage buffer = null;
    private Dimension bufferScale;
    private double bufferScaleFactor = 1.0;
    private final Point bufferPosition = new Point(0, 0);

    public HangarCanvasWrapperSwing(Canvas canvas) {
        super(canvas);

        this.bufferScale = HangarState.getGraphicsSettings().getResolution();

        var mouseListener = new HangarMouseListener(this);
        var resolution = HangarState.getGraphicsSettings().getResolution();

        this.setBuffer(ImageUtils.createCompatibleImage(resolution.width, resolution.height));
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var graphicsSettings = HangarState.getGraphicsSettings();
                if (graphicsSettings.getScalingMode() == ScalingModes.ChangeResolution) {
                    float scalingInUnits = SystemUtils.getScalingInUnits();
                    int realWidth = (int) (getWidth() * scalingInUnits);
                    int realHeight = (int) (getHeight() * scalingInUnits);
                    graphicsSettings.setResolution(new Dimension(realWidth, realHeight));
                }
                HangarCanvasWrapperSwing.this.updateBufferTransformations();
            }
        });

        // TODO: remove?
//        HangarState.getProfileManager().getCurrentProfile().addProfileListener(e -> {
//            switch (e.getStateChange()) {
//                case HangarProfileEvent.MIDLET_KEYCODES_CHANGED -> {
//                    for (var keyListener : getKeyListeners()) {
//                        if (keyListener instanceof HangarKeyListener hangarKeyListener) {
//                            hangarKeyListener.getPressedKeys().clear();
//                        }
//                    }
//                }
//                case HangarProfileEvent.SCALING_MODE_CHANGED -> SwingUtilities.invokeLater(() -> {
//                    if (e.getValue() == ScalingModes.ChangeResolution) {
//                        var contentPane = HangarState.getMainFrame().getContentPane();
//                        var source = (HangarProfile) e.getSource();
//                        source.setResolution(contentPane.getSize());
//                    }
//                    updateBufferTransformations();
//                });
//                case HangarProfileEvent.RESOLUTION_CHANGED -> SwingUtilities.invokeLater(() -> CanvasWrapperUtils.fitBufferToResolution(this, (Dimension) e.getValue()));
//                case HangarProfileEvent.FRAME_RATE_CHANGED -> refreshSerialCallTimer();
//            }
//        });
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    @Override
    public double getScaleFactor() {
        return bufferScaleFactor;
    }

    public void updateBufferTransformations() {
        bufferScaleFactor = CanvasWrapperUtils.getBufferScaleFactor(this, buffer);
        float scalingInUnits = SystemUtils.getScalingInUnits();

        int newWidth = (int) (buffer.getWidth() * bufferScaleFactor);
        int newHeight = (int) (buffer.getHeight() * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = (int) ((getWidth() * scalingInUnits) / 2 - bufferScale.width / 2);
        bufferPosition.y = (int) ((getHeight() * scalingInUnits) / 2 - bufferScale.height / 2);
        this.repaint();
    }

    @Override
    public Rectangle getDisplayedArea() {
        int width = (int) (buffer.getWidth() * bufferScaleFactor);
        int height = (int) (buffer.getWidth() * bufferScaleFactor);
        return new Rectangle(bufferPosition.x, bufferPosition.y, width, height);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        var graphics2d = (Graphics2D) graphics;
        var transform = graphics2d.getTransform();
        transform.setToScale(1.0, 1.0);
        graphics2d.setTransform(transform);

        var graphicsSettings = HangarState.getGraphicsSettings();
        if (graphicsSettings.getScalingMode() == ScalingModes.ChangeResolution) {
            var graphicsWithHints = HangarState.applyAntiAliasing(graphics);
            // TODO: clear "canvas" if enabled
            canvas.paint(new javax.microedition.lcdui.Graphics(new HangarSwingGraphicsProvider(graphicsWithHints)));
        }
        else if (buffer != null) {
            var graphicsWithHints = HangarState.applyAntiAliasing(buffer.getGraphics());
            if (graphicsSettings.getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
            }
            canvas.paint(new javax.microedition.lcdui.Graphics(new HangarSwingGraphicsProvider(graphicsWithHints)));
            graphics2d.drawImage(buffer, bufferPosition.x, bufferPosition.y, bufferScale.width, bufferScale.height, null);
        }
    }
}