/*
 * Copyright 2022-2024 Wafer EX
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

package aq.waferex.hangaremulator.utils;

import aq.waferex.hangaremulator.ui.components.wrappers.HangarCanvasWrapper;
import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;

import javax.swing.*;
import java.awt.*;

public final class CanvasWrapperUtils {
    public static double getBufferScaleFactor(JPanel panel, int width, int height) {
        if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.Contain) {
            float scalingInUnits = SystemUtils.getScalingInUnits();
            double scaleFactorHorizontal = ((double) panel.getWidth() / width) * scalingInUnits;
            double scaleFactorVertical = ((double) panel.getHeight() / height) * scalingInUnits;
            return Math.min(scaleFactorHorizontal, scaleFactorVertical);
        }
        return 1.0;
    }

    // TODO: or use it, or remove
//    public static void fitBufferToResolution(HangarCanvasWrapperSwing canvasWrapper, Dimension resolution) {
//        if (resolution.width > 0 && resolution.height > 0) {
//            if (canvasWrapper != null) {
//                var changedBuffer = ImageUtils.createCompatibleImage(resolution.width, resolution.height);
//                var displayable = HangarState.getMainFrame().getViewport().getDisplayable();
//
//                canvasWrapper.setBuffer(changedBuffer);
//                canvasWrapper.updateBufferTransformations();
//
//                if (displayable != null) {
//                    displayable.sizeChanged(resolution.width, resolution.height);
//                }
//            }
//        }
//    }

    public static Point canvasPointToPanel(HangarCanvasWrapper canvasWrapper, int x, int y) {
        // TODO: check this method
        var scaleFactor = canvasWrapper.getScaleFactor();
        var position = canvasWrapper.getDisplayedArea();

        return new Point(
                position.x + (int) (x * scaleFactor),
                position.y + (int) (y * scaleFactor)
        );
    }

    public static Point panelPointToCanvas(HangarCanvasWrapper canvasWrapper, int x, int y) {
        var scaleFactor = canvasWrapper.getScaleFactor();
        var position = canvasWrapper.getDisplayedArea();

        return new Point(
                (int) ((x - position.x) / scaleFactor),
                (int) ((y - position.y) / scaleFactor)
        );
    }
}