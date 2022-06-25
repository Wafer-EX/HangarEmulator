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
import things.ui.HangarFrame;
import things.ui.input.HangarMouseListener;
import things.utils.HangarPanelUtils;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class HangarPanel extends JPanel {
    private Displayable displayable;
    private BufferedImage buffer;
    private Point bufferPosition = new Point(0, 0);
    private double bufferScaleFactor = 1.0;
    private Dimension bufferScale = HangarState.getResolution();
    private Runnable callSerially;

    public HangarPanel() {
        var hangarMouseListener = new HangarMouseListener(this);
        var resolution = HangarState.getResolution();
        var timer = new Timer();

        setBuffer(new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_RGB));
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setPreferredSize(resolution);

        addMouseListener(hangarMouseListener);
        addMouseMotionListener(hangarMouseListener);

        // TODO: change period dynamically (or when changing framerate)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (callSerially != null) {
                    callSerially.run();
                }
            }
        }, 0, HangarState.frameRateInMilliseconds());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var hangarPanel = (HangarPanel) e.getComponent();
                if (HangarState.getScalingMode() == ScalingModes.ChangeResolution) {
                    var resolution = e.getComponent().getSize();
                    HangarPanelUtils.fitBufferToNewResolution(hangarPanel, resolution);
                }
                hangarPanel.updateBufferTransformations();
            }
        });
    }

    public Displayable getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Displayable displayable) {
        removeAll();
        this.displayable = displayable;

        if (displayable instanceof Canvas canvas) {
            var hangarFrame = HangarFrame.getInstance();
            hangarFrame.requestFocus();
            updateBufferTransformations();
            SwingUtilities.invokeLater(canvas::showNotify);
        }
        else if (displayable instanceof List list) {
            HangarPanelUtils.displayMEList(this, list);
        }
        revalidate();
        repaint();
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
        bufferScaleFactor = HangarPanelUtils.getBufferScaleFactor(this, buffer);

        int newWidth = (int) (buffer.getWidth() * bufferScaleFactor);
        int newHeight = (int) (buffer.getHeight() * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = getWidth() / 2 - bufferScale.width / 2;
        bufferPosition.y = getHeight() / 2 - bufferScale.height / 2;
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (buffer != null && displayable instanceof Canvas canvas) {
            var graphicsWithHints = HangarState.applyRenderingHints(buffer.getGraphics());
            if (HangarState.getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
            }
            canvas.paint(new javax.microedition.lcdui.Graphics(graphicsWithHints));

            var scaledBuffer = buffer.getScaledInstance(bufferScale.width, bufferScale.height, Image.SCALE_AREA_AVERAGING);
            graphics.drawImage(scaledBuffer, bufferPosition.x, bufferPosition.y, null);
        }
    }
}