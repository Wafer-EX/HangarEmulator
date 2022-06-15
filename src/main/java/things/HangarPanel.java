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

import javax.microedition.lcdui.Displayable;
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
    private BufferedImage flushedBuffer;
    private Runnable callSerially;

    private HangarPanel() {
        var resolution = HangarState.getResolution();
        buffer = graphicsConfiguration.createCompatibleImage(resolution.width, resolution.height);
        setPreferredSize(resolution);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (HangarState.getScalingMode() == ScalingModes.ChangeResolution) {
                    HangarState.setResolution(e.getComponent().getSize());
                    HangarPanelUtils.fitBufferToResolution((HangarPanel) e.getComponent());
                }
                repaint();
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

        if (getDisplayable() instanceof javax.microedition.lcdui.Canvas canvas) {
            HangarFrame.getInstance().setHangarPanel();
            canvas.showNotify();
        }
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public void setFlushedBuffer(BufferedImage buffer) {
        this.flushedBuffer = buffer;
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        HangarState.applyRenderingHints(buffer.getGraphics());
        if (HangarState.getCanvasClearing()) {
            buffer.getGraphics().clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
        }

        var position = HangarPanelUtils.getBufferPosition(this, buffer);
        var scale = HangarPanelUtils.getBufferScale(this, buffer);

        if (displayable != null) {
            if (displayable instanceof javax.microedition.lcdui.Canvas canvas) {
                canvas.paint(new javax.microedition.lcdui.Graphics(buffer.getGraphics()));
            }
            else if (displayable instanceof javax.microedition.lcdui.List list) {
                list.paint(buffer.getGraphics());
            }
            graphics.drawImage(buffer.getScaledInstance(scale.width, scale.height, Image.SCALE_AREA_AVERAGING), position.x, position.y, null);
        }

        if (flushedBuffer != null) {
            graphics.drawImage(flushedBuffer.getScaledInstance(scale.width, scale.height, Image.SCALE_AREA_AVERAGING), position.x, position.y, null);
            flushedBuffer = null;
        }

        if (callSerially != null) {
            callSerially.run();
        }
    }
}