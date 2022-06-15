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

import things.HangarState;
import things.enums.ScalingModes;

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

    public static Dimension getBufferScale(JPanel panel, BufferedImage buffer) {
        var scaleFactor = getBufferScaleFactor(panel, buffer);
        int width = (int) (buffer.getWidth() * scaleFactor);
        int height = (int) (buffer.getHeight() * scaleFactor);
        return new Dimension(width, height);
    }

    public static Point getBufferPosition(JPanel panel, BufferedImage buffer) {
        var position = new Point(0, 0);
        switch (HangarState.getScalingMode()) {
            case None -> {
                position.x = panel.getWidth() / 2 - buffer.getWidth() / 2;
                position.y = panel.getHeight() / 2 - buffer.getHeight() / 2;
            }
            case Contain -> {
                var scaleFactor = getBufferScaleFactor(panel, buffer);
                position.x = (int) (panel.getWidth() / 2 - buffer.getWidth() * scaleFactor / 2);
                position.y = (int) (panel.getHeight() / 2 - buffer.getHeight() * scaleFactor / 2);
            }
        }
        return position;
    }
}