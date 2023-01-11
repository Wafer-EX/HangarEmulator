/*
 * Copyright 2022-2023 Kirill Lomakin
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

package things.utils.nokia;

import com.nokia.mid.ui.DirectGraphics;
import things.utils.microedition.ImageUtils;

import java.awt.image.BufferedImage;

public class DirectGraphicsUtils {
    public static BufferedImage manipulateImage(BufferedImage image, int manipulation) {
        if ((manipulation & DirectGraphics.ROTATE_90) == DirectGraphics.ROTATE_90) {
            image = ImageUtils.rotateImage(image, -Math.PI / 2, true);
        }
        if ((manipulation & DirectGraphics.ROTATE_180) == DirectGraphics.ROTATE_180) {
            image = ImageUtils.rotateImage(image, -Math.PI, false);
        }
        if ((manipulation & DirectGraphics.ROTATE_270) == DirectGraphics.ROTATE_270) {
            image = ImageUtils.rotateImage(image, -Math.PI / 2 * 3, true);
        }
        if ((manipulation & DirectGraphics.FLIP_VERTICAL) == DirectGraphics.FLIP_VERTICAL) {
            image = ImageUtils.mirrorImageVertical(image);
        }
        if ((manipulation & DirectGraphics.FLIP_HORIZONTAL) == DirectGraphics.FLIP_HORIZONTAL) {
            image = ImageUtils.mirrorImageHorizontal(image);
        }
        return image;
    }

    public static int getBufferedImageType(int directGraphicsImageType) throws IllegalArgumentException {
        // TODO: do something with formats without analogues
        return switch (directGraphicsImageType) {
            case DirectGraphics.TYPE_BYTE_1_GRAY -> BufferedImage.TYPE_BYTE_GRAY;
            //case DirectGraphics.TYPE_BYTE_1_GRAY_VERTICAL -> 0;
            //case DirectGraphics.TYPE_BYTE_2_GRAY -> 0;
            //case DirectGraphics.TYPE_BYTE_4_GRAY -> 0;
            //case DirectGraphics.TYPE_BYTE_8_GRAY -> 0;
            //case DirectGraphics.TYPE_BYTE_332_RGB -> 0;
            //case DirectGraphics.TYPE_USHORT_4444_ARGB -> 0;
            //case DirectGraphics.TYPE_USHORT_444_RGB -> 0;
            case DirectGraphics.TYPE_USHORT_555_RGB -> BufferedImage.TYPE_USHORT_555_RGB;
            //case DirectGraphics.TYPE_USHORT_1555_ARGB -> 0;
            case DirectGraphics.TYPE_USHORT_565_RGB -> BufferedImage.TYPE_USHORT_565_RGB;
            case DirectGraphics.TYPE_INT_888_RGB -> BufferedImage.TYPE_INT_RGB;
            case DirectGraphics.TYPE_INT_8888_ARGB -> BufferedImage.TYPE_INT_ARGB;
            default -> throw new IllegalArgumentException();
        };
    }
}