/*
 * Copyright 2022-2023 Kirill Lomakin
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

package things.ui.components.wrappers.canvas;

import things.HangarState;
import things.enums.ScalingModes;
import things.graphics.swing.HangarSwingGraphicsProvider;
import things.profiles.HangarProfile;
import things.ui.listeners.HangarKeyListener;
import things.ui.listeners.HangarMouseListener;
import things.ui.listeners.events.HangarProfileEvent;
import things.ui.listeners.events.HangarProfileManagerEvent;
import things.utils.CanvasWrapperUtils;
import things.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class HangarCanvasWrapperSwing extends HangarCanvasWrapper {
    // TODO: add quality support
    private BufferedImage buffer;
    private final Point bufferPosition = new Point(0, 0);
    private double bufferScaleFactor = 1.0;
    private Dimension bufferScale = HangarState.getProfileManager().getCurrentProfile().getResolution();

    public HangarCanvasWrapperSwing(Canvas canvas) {
        super(canvas);

        var profile = HangarState.getProfileManager().getCurrentProfile();
        var mouseListener = new HangarMouseListener(this);
        var resolution = profile.getResolution();

        this.setBuffer(ImageUtils.createCompatibleImage(resolution.width, resolution.height));
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var profile = HangarState.getProfileManager().getCurrentProfile();
                if (profile.getScalingMode() == ScalingModes.ChangeResolution) {
                    profile.setResolution(HangarCanvasWrapperSwing.this.getSize());
                    CanvasWrapperUtils.fitBufferToResolution(HangarCanvasWrapperSwing.this, getSize());
                }
                HangarCanvasWrapperSwing.this.updateBufferTransformations();
            }
        });

        HangarState.getProfileManager().addProfileManagerListener(e -> {
            switch (e.getStateChange()) {
                case HangarProfileManagerEvent.PROFILE_SET -> {
                    // TODO: write code here
                }
                case HangarProfileManagerEvent.PROFILE_UNSET -> {
                    for (var profileListener : e.getProfile().getProfileListeners()) {
                        e.getProfile().removeProfileListener(profileListener);
                    }
                }
            }
        });

        HangarState.getProfileManager().getCurrentProfile().addProfileListener(e -> {
            switch (e.getStateChange()) {
                case HangarProfileEvent.MIDLET_KEYCODES_CHANGED -> {
                    for (var keyListener : getKeyListeners()) {
                        if (keyListener instanceof HangarKeyListener hangarKeyListener) {
                            hangarKeyListener.getPressedKeys().clear();
                        }
                    }
                }
                case HangarProfileEvent.SCALING_MODE_CHANGED -> SwingUtilities.invokeLater(() -> {
                    if (e.getValue() == ScalingModes.ChangeResolution) {
                        var contentPane = HangarState.getMainFrame().getContentPane();
                        var source = (HangarProfile) e.getSource();
                        source.setResolution(contentPane.getSize());
                    }
                    updateBufferTransformations();
                });
                case HangarProfileEvent.RESOLUTION_CHANGED -> SwingUtilities.invokeLater(() -> CanvasWrapperUtils.fitBufferToResolution(this, (Dimension) e.getValue()));
                case HangarProfileEvent.FRAME_RATE_CHANGED -> refreshSerialCallTimer();
            }
        });
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

        int newWidth = (int) (buffer.getWidth() * bufferScaleFactor);
        int newHeight = (int) (buffer.getHeight() * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = getWidth() / 2 - bufferScale.width / 2;
        bufferPosition.y = getHeight() / 2 - bufferScale.height / 2;
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
        if (buffer != null) {
            var graphicsWithHints = HangarState.applyAntiAliasing(buffer.getGraphics());
            var profile = HangarState.getProfileManager().getCurrentProfile();

            if (profile.getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
            }
            canvas.paint(new javax.microedition.lcdui.Graphics(new HangarSwingGraphicsProvider(graphicsWithHints)));
            graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics2d.drawImage(buffer, bufferPosition.x, bufferPosition.y, bufferScale.width, bufferScale.height, null);
        }
    }
}