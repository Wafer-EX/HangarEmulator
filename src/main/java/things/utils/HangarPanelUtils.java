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

package things.utils;

import things.HangarPanel;
import things.HangarState;
import things.enums.ScalingModes;

import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class HangarPanelUtils {
    private static final GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    public static double getBufferScaleFactor(JPanel panel, BufferedImage buffer) {
        if (HangarState.getScalingMode() == ScalingModes.Contain) {
            double scaleFactorHorizontal = (double) panel.getWidth() / buffer.getWidth();
            double scaleFactorVertical = (double) panel.getHeight() / buffer.getHeight();
            return Math.min(scaleFactorHorizontal, scaleFactorVertical);
        }
        else {
            return 1.0;
        }
    }

    public static Dimension getBufferScale(BufferedImage buffer, double scaleFactor) {
        int width = (int) (buffer.getWidth() * scaleFactor);
        int height = (int) (buffer.getHeight() * scaleFactor);
        return new Dimension(width, height);
    }

    public static void fitBufferToNewResolution(HangarPanel hangarPanel, Dimension resolution) {
        if (resolution.width > 0 && resolution.height > 0) {
            var changedBuffer = graphicsConfiguration.createCompatibleImage(resolution.width, resolution.height);
            HangarState.setResolution(resolution);
            hangarPanel.setBuffer(changedBuffer);
            hangarPanel.updateBufferTransformations();

            var displayable = hangarPanel.getDisplayable();
            if (displayable != null) {
                displayable.sizeChanged(resolution.width, resolution.height);
            }
        }
    }

    public static void repaintBufferRegion(HangarPanel hangarPanel, int x, int y, int width, int height) {
        var scaleFactor = hangarPanel.getBufferScaleFactor();
        var position = hangarPanel.getBufferPosition();
        int newWidth = (int) (width * scaleFactor);
        int newHeight = (int) (height * scaleFactor);
        hangarPanel.repaint(x + position.x, y + position.y, newWidth, newHeight);
    }

    public static void displayMEList(HangarPanel hangarPanel, List meList) {
        var layout = new GridLayout(6, 1, 4, 4);
        hangarPanel.setLayout(layout);

        for (int i = 0; i < meList.size(); i++) {
            var button = new JButton(meList.getString(i));
            int selectedIndex = i;

            button.addActionListener(e -> {
                meList.setSelectedIndex(selectedIndex, true);
                meList.runSelectCommand();
            });
            hangarPanel.add(button);
        }

        if (meList.getCommands().size() > 0) {
            hangarPanel.add(new JLabel("Options:", SwingConstants.CENTER));
            for (var command : meList.getCommands()) {
                var button = new JButton(command.getLabel());
                button.addActionListener(e -> meList.getCommandListener().commandAction(command, meList));
                hangarPanel.add(button);
            }
        }
    }
}