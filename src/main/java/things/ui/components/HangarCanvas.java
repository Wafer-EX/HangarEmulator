/*
 * Copyright 2022 Kirill Lomakin
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

package things.ui.components;

import things.HangarState;
import things.enums.ScalingModes;
import things.ui.listeners.HangarMouseListener;
import things.utils.HangarCanvasUtils;
import things.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class HangarCanvas extends JPanel {
    private final Canvas canvas;
    private BufferedImage buffer;
    private final Point bufferPosition = new Point(0, 0);
    private double bufferScaleFactor = 1.0;
    private Dimension bufferScale = HangarState.getProfile().getResolution();
    private Runnable callSerially;
    private Timer serialCallTimer = new Timer();

    public HangarCanvas(Canvas canvas) {
        super();
        this.canvas = canvas;

        var mouseListener = new HangarMouseListener(this);
        var resolution = HangarState.getProfile().getResolution();

        this.setBuffer(ImageUtils.createCompatibleImage(resolution.width, resolution.height));
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (HangarState.getProfile().getScalingMode() == ScalingModes.ChangeResolution) {
                    HangarState.getProfile().setResolution(HangarCanvas.this.getSize());
                    HangarCanvasUtils.fitBufferToResolution(HangarCanvas.this, HangarCanvas.this.getSize());
                }
                HangarCanvas.this.updateBufferTransformations();
            }
        });
        this.refreshSerialCallTimer();
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public Point getBufferPosition() {
        return bufferPosition;
    }

    public double getBufferScaleFactor() {
        return bufferScaleFactor;
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
    }

    public void updateBufferTransformations() {
        bufferScaleFactor = HangarCanvasUtils.getBufferScaleFactor(this, buffer);

        int newWidth = (int) (buffer.getWidth() * bufferScaleFactor);
        int newHeight = (int) (buffer.getHeight() * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = getWidth() / 2 - bufferScale.width / 2;
        bufferPosition.y = getHeight() / 2 - bufferScale.height / 2;
        this.repaint();
    }

    public Image rescaleBuffer(BufferedImage image) {
        return switch (HangarState.getProfile().getScalingMode()) {
            case Contain -> buffer.getScaledInstance(bufferScale.width, bufferScale.height, Image.SCALE_AREA_AVERAGING);
            default -> image;
        };
    }

    public void refreshSerialCallTimer() {
        serialCallTimer.cancel();
        serialCallTimer.purge();
        serialCallTimer = new Timer();

        var frameRateInMilliseconds = HangarState.frameRateInMilliseconds();
        if (frameRateInMilliseconds >= 0) {
            serialCallTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (callSerially != null) {
                        callSerially.run();
                    }
                }
            }, 0, frameRateInMilliseconds);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (buffer != null) {
            var graphicsWithHints = HangarState.applyRenderingHints(buffer.getGraphics());
            if (HangarState.getProfile().getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
            }
            canvas.paint(new javax.microedition.lcdui.Graphics(graphicsWithHints, buffer));
            graphics.drawImage(rescaleBuffer(buffer), bufferPosition.x, bufferPosition.y, null);
        }
    }
}