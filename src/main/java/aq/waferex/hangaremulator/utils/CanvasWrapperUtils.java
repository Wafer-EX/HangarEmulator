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

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;
import org.joml.Matrix4f;

import java.awt.*;

public final class CanvasWrapperUtils {
    // TODO: should I move it somewhere?
    private static float percentScalingModeScaleFactor = 1.0f;

    public static float getPercentScalingModeScaleFactor() {
        return percentScalingModeScaleFactor;
    }

    public static void setPercentScalingModeScaleFactor(float value) {
        percentScalingModeScaleFactor = value;
    }

    public static float getImageScaleFactor(int imageWidth, int imageHeight, int viewportWidth, int viewportHeight) {
        return switch (HangarState.getGraphicsSettings().getScalingMode()) {
            // TODO: replace None with Percent
            case Percent -> percentScalingModeScaleFactor;
            case ChangeResolution -> 1.0f;
            case Fit -> {
                float scaleFactorHorizontal = ((float) viewportWidth / imageWidth);
                float scaleFactorVertical = ((float) viewportHeight / imageHeight);
                yield Math.min(scaleFactorHorizontal, scaleFactorVertical);
            }
        };
    }

    public static Matrix4f getScreenImageProjectionMatrix(int viewportWidth, int viewportHeight, int screenImageWidth, int screenImageHeight) {
        float scaleFactor = getImageScaleFactor(screenImageWidth, screenImageHeight, viewportWidth, viewportHeight);
        var matrix = new Matrix4f().ortho2D(0, viewportWidth, viewportHeight, 0);

        if (HangarState.getGraphicsSettings().getScalingMode() != ScalingModes.ChangeResolution) {
            matrix = matrix.mul(new Matrix4f()
                    .scale(scaleFactor)
                    .translate(
                            (float) Math.floor(viewportWidth / scaleFactor / 2.0f - screenImageWidth / 2.0f),
                            (float) Math.floor(viewportHeight / scaleFactor / 2.0f - screenImageHeight / 2.0f),
                            0.0f
                    )
            );
        }

        return matrix;
    }

    public static Point convertMousePointToScreenImage(int mouseX, int mouseY, int viewportWidth, int viewportHeight, float scalingInUnits) {
        var screenImage = HangarState.getScreenImage();
        int screenImageWidth = screenImage.getWidth();
        int screenImageHeight = screenImage.getHeight();

        float scaleFactor = CanvasWrapperUtils.getImageScaleFactor(screenImageWidth, screenImageHeight, viewportWidth, viewportHeight);
        float imagePosX = viewportWidth / 2.0f - (screenImageWidth * scaleFactor) / 2.0f;
        float imagePosY = viewportHeight / 2.0f - (screenImageHeight * scaleFactor) / 2.0f;

        return new Point(
                (int) ((mouseX * scalingInUnits - imagePosX) / scaleFactor),
                (int) ((mouseY * scalingInUnits - imagePosY) / scaleFactor)
        );
    }
}