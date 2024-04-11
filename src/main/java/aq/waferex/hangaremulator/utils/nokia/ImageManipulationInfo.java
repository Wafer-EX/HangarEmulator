/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.utils.nokia;

import com.nokia.mid.ui.DirectGraphics;

public record ImageManipulationInfo(boolean flipX, boolean flipY, int rotateTimes) {
    public static ImageManipulationInfo getInfo(int manipulation, int imageWidth, int imageHeight) {
        boolean flipX = (manipulation & DirectGraphics.FLIP_HORIZONTAL) == DirectGraphics.FLIP_HORIZONTAL;
        boolean flipY = (manipulation & DirectGraphics.FLIP_VERTICAL) == DirectGraphics.FLIP_VERTICAL;
        int rotateTimes = 0;

        if ((manipulation & DirectGraphics.ROTATE_90) == DirectGraphics.ROTATE_90) {
            rotateTimes = -1;
        }
        if ((manipulation & DirectGraphics.ROTATE_180) == DirectGraphics.ROTATE_180) {
            rotateTimes = -2;
        }
        if ((manipulation & DirectGraphics.ROTATE_270) == DirectGraphics.ROTATE_270) {
            rotateTimes = -3;
        }

        return new ImageManipulationInfo(flipX, flipY, rotateTimes);
    }
}