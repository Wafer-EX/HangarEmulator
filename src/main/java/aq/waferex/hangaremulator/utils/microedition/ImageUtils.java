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

package aq.waferex.hangaremulator.utils.microedition;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import java.awt.*;
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

    public static BufferedImage rotateImage(BufferedImage originalImage, int rotateTimes) {
        boolean flipDimensions = originalImage.getWidth() != originalImage.getHeight() && rotateTimes % 2 != 0;
        int width = flipDimensions ? originalImage.getHeight() : originalImage.getWidth();
        int height = flipDimensions ? originalImage.getWidth() : originalImage.getHeight();
        double theta = (Math.PI / 2) * rotateTimes;

        var rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics2D = rotatedImage.createGraphics();

        if (flipDimensions) {
            int translate = (width - height) >> 1;
            graphics2D.translate(translate, translate);
        }
        graphics2D.rotate(theta, width / 2.0, height / 2.0);
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

    public static BufferedImage transformImage(BufferedImage image, int spriteTransformConst) throws IllegalArgumentException {
        switch (spriteTransformConst) {
            case Sprite.TRANS_NONE -> { }
            case Sprite.TRANS_ROT90 -> image = ImageUtils.rotateImage(image, 1);
            case Sprite.TRANS_ROT180 -> image = ImageUtils.rotateImage(image, 2);
            case Sprite.TRANS_ROT270 -> image = ImageUtils.rotateImage(image, 3);
            default -> {
                image = ImageUtils.mirrorImageHorizontal(image);
                switch (spriteTransformConst) {
                    case Sprite.TRANS_MIRROR -> { }
                    case Sprite.TRANS_MIRROR_ROT90 -> image = ImageUtils.rotateImage(image, 1);
                    case Sprite.TRANS_MIRROR_ROT180 -> image = ImageUtils.rotateImage(image, 2);
                    case Sprite.TRANS_MIRROR_ROT270 -> image = ImageUtils.rotateImage(image, 3);
                    default -> throw new IllegalArgumentException();
                }
            }
        }
        return image;
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return graphicsConfiguration.createCompatibleImage(width, height);
    }
}