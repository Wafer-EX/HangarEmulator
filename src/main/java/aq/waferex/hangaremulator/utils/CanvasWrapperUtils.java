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
import org.joml.Matrix4f;

import java.awt.*;

public final class CanvasWrapperUtils {
    public static float getImageScaleFactor(int imageWidth, int imageHeight, int viewportWidth, int viewportHeight) {
        float scaleFactorHorizontal = ((float) viewportWidth / imageWidth);
        float scaleFactorVertical = ((float) viewportHeight / imageHeight);
        return Math.min(scaleFactorHorizontal, scaleFactorVertical);
    }

    public static Matrix4f getScreenImageProjectionMatrix(int viewportWidth, int viewportHeight, int screenImageWidth, int screenImageHeight) {
        var matrix = new Matrix4f().ortho2D(0, viewportWidth, viewportHeight, 0);

        // TODO: replace None with Percent
        float scaleFactor = switch (HangarState.getGraphicsSettings().getScalingMode()) {
            case None, ChangeResolution -> 1.0f;
            case Contain -> CanvasWrapperUtils.getImageScaleFactor(screenImageWidth, screenImageHeight, viewportWidth, viewportHeight);
        };

        if (HangarState.getGraphicsSettings().getScalingMode() != ScalingModes.ChangeResolution) {
            matrix = matrix.mul(new Matrix4f()
                    .scale(scaleFactor)
                    .translate(
                            viewportWidth / scaleFactor / 2.0f - screenImageWidth / 2.0f,
                            viewportHeight / scaleFactor / 2.0f - screenImageHeight / 2.0f,
                            0.0f
                    )
            );
        }

        return matrix;
    }

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