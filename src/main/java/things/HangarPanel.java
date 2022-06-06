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

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HangarPanel extends JPanel {
    private static HangarPanel instance;
    private static Displayable displayable;
    private BufferedImage flushedBuffer;

    private HangarPanel() {
        setPreferredSize(HangarState.getResolution());
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
        HangarPanel.displayable = displayable;
        if (getDisplayable() instanceof javax.microedition.lcdui.Canvas canvas) {
            canvas.showNotify();
        }
        if (getDisplayable() instanceof javax.microedition.lcdui.List meList) {
            var list = new JList(meList.getListModel());
            HangarFrame.getInstance().add(list);
        }
    }

    public void setFlushedBuffer(BufferedImage buffer) {
        this.flushedBuffer = buffer;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        HangarState.syncWithFrameRate();
        HangarState.applyRenderingHints(graphics);

        if (flushedBuffer != null) {
            graphics.drawImage(flushedBuffer, 0, 0, null);
            flushedBuffer = null;
        }
        else if (displayable != null) {
            boolean sizeMatches = displayable.getWidth() == this.getWidth() && displayable.getHeight() == this.getHeight();
            if (HangarState.getCanvasClearing()) {
                super.paintComponent(graphics);
            }

            if (displayable instanceof javax.microedition.lcdui.Canvas canvas) {
                if (!sizeMatches) {
                    HangarState.setResolution(getSize());
                    canvas.sizeChanged(this.getWidth(), this.getHeight());
                }
                canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
            }
        }
    }
}