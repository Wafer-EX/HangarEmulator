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

import javax.imageio.ImageIO;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class DirectUtils {
    public static DirectGraphics getDirectGraphics(Graphics g) {
        if (g == null || g.getGraphicsProvider() == null) {
            throw new NullPointerException();
        }
        return new DirectGraphics() {
            private Color argbColor;

            @Override
            public void setARGBColor(int argbColor) {
                this.argbColor = new Color(argbColor, true);
            }

            @Override
            public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
                if (img == null) {
                    throw new NullPointerException();
                }
                var image = new Image(DirectGraphicsUtils.manipulateImage(img.getSEImage(), manipulation), true);
                x = ImageUtils.alignX(img.getWidth(), x, anchor);
                y = ImageUtils.alignY(img.getHeight(), y, anchor);
                g.getGraphicsProvider().drawImage(image, x, y);
            }

            @Override
            public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                setARGBColor(argbColor);
                g.getGraphicsProvider().drawTriangle(x1, y1, x2, y2, x3, y3, this.argbColor, false);
            }

            @Override
            public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                setARGBColor(argbColor);
                g.getGraphicsProvider().drawTriangle(x1, y1, x2, y2, x3, y3, this.argbColor, true);
            }

            @Override
            public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                setARGBColor(argbColor);
                g.getGraphicsProvider().drawPolygon(xPoints, xOffset, yPoints, yOffset, nPoints, this.argbColor, false);
            }

            @Override
            public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                setARGBColor(argbColor);
                g.getGraphicsProvider().drawPolygon(xPoints, xOffset, yPoints, yOffset, nPoints, this.argbColor, true);
            }

            @Override
            public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                if (pixels == null) {
                    throw new NullPointerException();
                }
                var image = new BufferedImage(width, height, DirectGraphicsUtils.getBufferedImageType(format));
                image.setRGB(0, 0, width, height, pixels, offset, scanlength);
                image = DirectGraphicsUtils.manipulateImage(image, manipulation);
                g.getGraphicsProvider().drawImage(new Image(image, true), x, y);
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
                return argbColor.getAlpha();
            }
        };
    }

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) throws ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException {
        if (imageData == null) {
            throw new NullPointerException();
        }
        try {
            var byteArrayInputStream = new ByteArrayInputStream(imageData, imageOffset, imageLength);
            var image = ImageIO.read(byteArrayInputStream);
            return new Image(image, true);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }

    public static Image createImage(int width, int height, int ARGBcolor) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics2d = (Graphics2D) image.getGraphics();
        graphics2d.setColor(new Color(ARGBcolor, true));
        graphics2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        return new Image(image, true);
    }
}