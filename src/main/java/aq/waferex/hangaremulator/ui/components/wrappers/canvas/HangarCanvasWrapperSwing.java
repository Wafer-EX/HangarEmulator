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
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HangarCanvasWrapperSwing extends HangarCanvasWrapper {
    // TODO: add quality support
    private BufferedImage buffer;

    public HangarCanvasWrapperSwing(Canvas canvas) {
        super(canvas);

        var resolution = HangarState.getGraphicsSettings().getResolution();
        this.buffer = ImageUtils.createCompatibleImage(resolution.width, resolution.height);

        var mouseListener = new HangarMouseListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

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
        // TODO: return back to this code I guess, don't render into canvas, render into texture
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