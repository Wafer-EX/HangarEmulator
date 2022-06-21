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

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import java.awt.image.BufferedImage;

public final class ImageUtils {
    public static int alignX(int imageWidth, int x, int anchor) {
        int alignedX = x;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedX -= imageWidth;
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedX -= imageWidth >> 1;
        }
        return alignedX;
    }

    public static int alignY(int imageHeight, int y, int anchor) {
        int alignedY = y;
        if ((anchor & Graphics.BOTTOM) != 0) {
            alignedY -= imageHeight;
        }
        else if ((anchor & Graphics.VCENTER) != 0) {
            alignedY -= imageHeight >> 1;
        }
        return alignedY;
    }

    public static BufferedImage rotateImage(BufferedImage originalImage, double theta, boolean flipDimensions) {
        int width = flipDimensions ? originalImage.getHeight() : originalImage.getWidth();
        int height = flipDimensions ? originalImage.getWidth() : originalImage.getHeight();

        var rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics2D = rotatedImage.createGraphics();

        if (flipDimensions) {
            graphics2D.translate((width - height) / 2, (width - height) / 2);
        }
        graphics2D.rotate(theta, width >> 1, height >> 1);
        graphics2D.drawRenderedImage(originalImage, null);

        return rotatedImage;
    }

    public static BufferedImage mirrorImageVertical(BufferedImage originalImage) {
        var mirroredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        var graphics2D = mirroredImage.createGraphics();

        graphics2D.translate(0, originalImage.getHeight());
        graphics2D.drawImage(originalImage, 0, 0, originalImage.getWidth(), -originalImage.getHeight(), null);
        return mirroredImage;
    }

    public static BufferedImage mirrorImageHorizontal(BufferedImage originalImage) {
        var mirroredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        var graphics2D = mirroredImage.createGraphics();

        graphics2D.translate(originalImage.getWidth(), 0);
        graphics2D.drawImage(originalImage, 0, 0, -originalImage.getWidth(), originalImage.getHeight(), null);
        return mirroredImage;
    }

    public static BufferedImage transformImage(BufferedImage image, int spriteTransformConst) {
        switch (spriteTransformConst) {
            case Sprite.TRANS_NONE -> { }
            case Sprite.TRANS_ROT90 -> image = ImageUtils.rotateImage(image, Math.PI / 2, true);
            case Sprite.TRANS_ROT180 -> image = ImageUtils.rotateImage(image, Math.PI, false);
            case Sprite.TRANS_ROT270 -> image = ImageUtils.rotateImage(image, Math.PI / 2 * 3, true);
            default -> {
                image = ImageUtils.mirrorImageHorizontal(image);
                switch (spriteTransformConst) {
                    case Sprite.TRANS_MIRROR_ROT90 -> image = ImageUtils.rotateImage(image, Math.PI / 2, true);
                    case Sprite.TRANS_MIRROR_ROT180 -> image = ImageUtils.rotateImage(image, Math.PI, false);
                    case Sprite.TRANS_MIRROR_ROT270 -> image = ImageUtils.rotateImage(image, Math.PI / 2 * 3, true);
                }
            }
        }
        return image;
    }
}