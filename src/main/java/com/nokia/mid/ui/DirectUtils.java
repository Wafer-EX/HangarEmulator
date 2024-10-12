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

package com.nokia.mid.ui;

import aq.waferex.hangaremulator.utils.microedition.ImageUtils;
import aq.waferex.hangaremulator.utils.nokia.DirectGraphicsUtils;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class DirectUtils {
    public static DirectGraphics getDirectGraphics(Graphics g) {
        if (g == null) {
            throw new NullPointerException();
        }
        return new DirectGraphics() {
            @Override
            public void setARGBColor(int argbColor) {
                var color = new Color(argbColor, true);
                g.getGraphics2D().setColor(color);
            }

            @Override
            public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
                if (img == null) {
                    throw new NullPointerException();
                }
                x = ImageUtils.alignX(img.getWidth(), x, anchor);
                y = ImageUtils.alignY(img.getHeight(), y, anchor);

                var manipulatedImage = ImageUtils.manipulateImage(img.getBufferedImage(), manipulation);
                g.getGraphics2D().drawImage(manipulatedImage, x, y, null);
            }

            @Override
            public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                int[] xPoints = new int[] { x1, x2, x3 };
                int[] yPoints = new int[] { y1, y2, y3 };

                g.getGraphics2D().setColor(new Color(argbColor, true));
                g.getGraphics2D().drawPolygon(xPoints, yPoints, 3);
            }

            @Override
            public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                int[] xPoints = new int[] { x1, x2, x3 };
                int[] yPoints = new int[] { y1, y2, y3 };

                g.getGraphics2D().setColor(new Color(argbColor, true));
                g.getGraphics2D().fillPolygon(xPoints, yPoints, 3);
            }

            @Override
            public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                g.getGraphics2D().setColor(new Color(argbColor, true));
                g.getGraphics2D().drawPolygon(xPoints, yPoints, nPoints);
            }

            @Override
            public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                g.getGraphics2D().setColor(new Color(argbColor, true));
                g.getGraphics2D().fillPolygon(xPoints, yPoints, nPoints);
            }

            @Override
            public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                if (pixels == null) {
                    throw new NullPointerException();
                }
                var image = new BufferedImage(width, height, DirectGraphicsUtils.getBufferedImageType(format));
                image.setRGB(0, 0, width, height, pixels, offset, scanlength);
                // TODO: don't create the new image?
                drawImage(new Image(image, false), x, y, 0, manipulation);
            }

            @Override
            public void drawPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                // TODO: write method logic
            }

            @Override
            public void drawPixels(short[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                // TODO: write method logic
            }

            @Override
            public void getPixels(int[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                // TODO: write method logic
            }

            @Override
            public void getPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                // TODO: write method logic
            }

            @Override
            public void getPixels(short[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                // TODO: write method logic
            }

            @Override
            public int getNativePixelFormat() {
                // TODO: write method logic
                return 0;
            }

            @Override
            public int getAlphaComponent() {
                // TODO: check it
                return g.getGraphics2D().getColor().getAlpha();
            }
        };
    }

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) throws ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException {
        if (imageData == null) {
            throw new NullPointerException();
        }
        try {
            var byteArrayInputStream = new ByteArrayInputStream(imageData, imageOffset, imageLength);
            return Image.createImage(byteArrayInputStream);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }

    public static Image createImage(int width, int height, int ARGBcolor) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        return new Image(width, height, ARGBcolor, true);
    }
}