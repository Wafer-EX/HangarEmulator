/*
 * Copyright 2023-2024 Wafer EX
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

package aq.waferex.hangaremulator.ui.components.wrappers;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.ui.listeners.HangarMouseListener;
import aq.waferex.hangaremulator.utils.CanvasWrapperUtils;
import aq.waferex.hangaremulator.utils.SystemUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

public class HangarCanvasWrapper extends JPanel {
    protected final Canvas canvas;
    private Runnable callSerially;
    private Timer serialCallTimer = new Timer();

    protected Dimension bufferScale;
    protected double bufferScaleFactor = 1.0;
    protected Point bufferPosition = new Point(0, 0);

    public HangarCanvasWrapper(Canvas canvas) {
        super(new CardLayout());
        this.canvas = canvas;

        var resolution = HangarState.getGraphicsSettings().getResolution();
        // TODO: initialize it in different place
        HangarState.setScreenImage(ImageUtils.createCompatibleImage(resolution.width, resolution.height));

        var mouseListener = new HangarMouseListener(this);
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
                updateBufferTransformations();
            }
        });

        this.updateBufferTransformations();
        this.refreshSerialCallTimer();
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
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

    public Rectangle getDisplayedArea() {
        var graphicsSettings = HangarState.getGraphicsSettings();
        var resolution = graphicsSettings.getResolution();

        int width = (int) (resolution.width * bufferScaleFactor);
        int height = (int) (resolution.height * bufferScaleFactor);
        return new Rectangle(bufferPosition.x, bufferPosition.y, width, height);
    }

    public double getScaleFactor() {
        return bufferScaleFactor;
    }

    public void updateBufferTransformations() {
        var graphicsSettings = HangarState.getGraphicsSettings();
        var resolution = graphicsSettings.getResolution();

        bufferScaleFactor = CanvasWrapperUtils.getBufferScaleFactor(this, resolution.width, resolution.height);
        float scalingInUnits = SystemUtils.getScalingInUnits();

        int newWidth = (int) (resolution.width * bufferScaleFactor);
        int newHeight = (int) (resolution.height * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = (int) ((getWidth() * scalingInUnits) / 2 - bufferScale.width / 2);
        bufferPosition.y = (int) ((getHeight() * scalingInUnits) / 2 - bufferScale.height / 2);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        var graphics2d = (Graphics2D) graphics;
        var transform = graphics2d.getTransform();
        transform.setToScale(1.0, 1.0);
        graphics2d.setTransform(transform);

        var graphicsSettings = HangarState.getGraphicsSettings();
        var screenImage = HangarState.getScreenImage();

        if (screenImage != null) {
            var graphicsWithHints = HangarState.applyAntiAliasing(screenImage.getGraphics());
            if (graphicsSettings.getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
            }
            // TODO: use graphics with hints or setup these settings in graphics object?
            canvas.paint(new javax.microedition.lcdui.Graphics(screenImage));
            graphics2d.drawImage(screenImage, bufferPosition.x, bufferPosition.y, bufferScale.width, bufferScale.height, null);
        }
    }
}