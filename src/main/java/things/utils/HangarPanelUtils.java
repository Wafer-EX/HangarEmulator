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

import things.ui.components.HangarGamePanel;
import things.HangarState;
import things.enums.ScalingModes;

import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class HangarPanelUtils {
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

    public static void fitBufferToNewResolution(HangarGamePanel hangarGamePanel, Dimension resolution) {
        if (resolution.width > 0 && resolution.height > 0) {
            HangarState.setResolution(resolution);

            if (hangarGamePanel != null) {
                var changedBuffer = ImageUtils.createCompatibleImage(resolution.width, resolution.height);
                var displayable = hangarGamePanel.getDisplayable();

                hangarGamePanel.setBuffer(changedBuffer);
                hangarGamePanel.updateBufferTransformations();

                if (displayable != null) {
                    displayable.sizeChanged(resolution.width, resolution.height);
                }
            }
        }
    }

    public static Point canvasPointToPanel(HangarGamePanel hangarGamePanel, int x, int y) {
        // TODO: check this method
        var scaleFactor = hangarGamePanel.getBufferScaleFactor();
        var position = hangarGamePanel.getBufferPosition();

        var point = new Point();
        point.x = position.x + (int) (x * scaleFactor);
        point.y = position.y + (int) (y * scaleFactor);
        return point;
    }

    public static Point panelPointToCanvas(HangarGamePanel hangarGamePanel, int x, int y) {
        var scaleFactor = hangarGamePanel.getBufferScaleFactor();
        var position = hangarGamePanel.getBufferPosition();

        var point = new Point();
        point.x = (int) ((x - position.x) / scaleFactor);
        point.y = (int) ((y - position.y) / scaleFactor);
        return point;
    }

    public static void displayMEList(HangarGamePanel hangarGamePanel, List meList) {
        var layout = new GridLayout(6, 1, 4, 4);
        hangarGamePanel.setLayout(layout);

        for (int i = 0; i < meList.size(); i++) {
            var button = new JButton(meList.getString(i));
            int selectedIndex = i;

            button.addActionListener(e -> {
                meList.setSelectedIndex(selectedIndex, true);
                meList.runSelectCommand();
            });
            hangarGamePanel.add(button);
        }

        if (meList.getCommands().size() > 0) {
            hangarGamePanel.add(new JLabel("Options:", SwingConstants.CENTER));
            for (var command : meList.getCommands()) {
                var button = new JButton(command.getLabel());
                button.addActionListener(e -> meList.getCommandListener().commandAction(command, meList));
                hangarGamePanel.add(button);
            }
        }
    }
}