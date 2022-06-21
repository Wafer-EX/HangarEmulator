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

package things;

import things.enums.ScalingModes;
import things.utils.HangarPanelUtils;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class HangarPanel extends JPanel {
    private static final GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    private static HangarPanel instance;
    private static Displayable displayable;
    private BufferedImage buffer;
    private Point bufferPosition = new Point(0, 0);
    private Dimension bufferScale = HangarState.getResolution();
    private Runnable callSerially;

    private HangarPanel() {
        var resolution = HangarState.getResolution();
        buffer = graphicsConfiguration.createCompatibleImage(resolution.width, resolution.height);

        setPreferredSize(resolution);
        setLayout(new GridLayout(5, 1));
        SwingUtilities.invokeLater(this::updateBufferTransformations);

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

    public static HangarPanel getInstance() {
        if (instance == null) {
            instance = new HangarPanel();
        }
        return instance;
    }

    public Displayable getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Displayable displayable) {
        removeAll();
        HangarPanel.displayable = displayable;

        if (displayable instanceof Canvas canvas) {
            var hangarFrame = HangarFrame.getInstance();
            hangarFrame.setHangarPanel();
            hangarFrame.requestFocus();
            canvas.showNotify();
        }
        else if (displayable instanceof List list) {
            HangarPanelUtils.displayMEList(this, list);
        }
        revalidate();
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
    }

    public void updateBufferTransformations() {
        bufferPosition = HangarPanelUtils.getBufferPosition(this, buffer);
        bufferScale = HangarPanelUtils.getBufferScale(this, buffer);
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        HangarState.applyRenderingHints(buffer.getGraphics());
        if (HangarState.getCanvasClearing()) {
            buffer.getGraphics().clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
        }

        if (displayable != null) {
            if (displayable instanceof Canvas canvas) {
                canvas.paint(new javax.microedition.lcdui.Graphics(buffer.getGraphics()));
                var scaledBuffer = buffer.getScaledInstance(bufferScale.width, bufferScale.height, Image.SCALE_AREA_AVERAGING);
                graphics.drawImage(scaledBuffer, bufferPosition.x, bufferPosition.y, null);
            }
        }

        if (callSerially != null) {
            callSerially.run();
        }
    }
}