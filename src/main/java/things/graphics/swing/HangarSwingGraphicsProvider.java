/*
 * Copyright 2023-2024 Wafer EX
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

package things.graphics.swing;

import com.nokia.mid.ui.DirectGraphics;
import things.HangarState;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.utils.microedition.FontUtils;
import things.utils.microedition.ImageUtils;
import things.utils.nokia.DirectGraphicsUtils;

import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.microedition.lcdui.Graphics.DOTTED;
import static javax.microedition.lcdui.Graphics.SOLID;

public class HangarSwingGraphicsProvider extends HangarGraphicsProvider {
    private final Graphics2D seGraphics;
    private final BufferedImage seImage;
    private java.awt.Font seFont;
    private int translateX = 0, translateY = 0;
    private int selectedStroke = SOLID;
    private DirectGraphics directGraphics;

    public HangarSwingGraphicsProvider(java.awt.Graphics seGraphics, BufferedImage seImage) {
        this.seGraphics = HangarState.applyAntiAliasing(seGraphics);
        this.seImage = seImage;
    }

    @Override
    public DirectGraphics getDirectGraphics(javax.microedition.lcdui.Graphics graphics) {
        if (directGraphics == null) {
            directGraphics = new DirectGraphics() {
                @Override
                public void setARGBColor(int argbColor) {
                    seGraphics.setColor(new Color(argbColor, true));
                }

                @Override
                public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
                    if (img == null) {
                        throw new NullPointerException();
                    }
                    var image = new Image(DirectGraphicsUtils.manipulateImage(img.getSEImage(), manipulation), true);
                    HangarSwingGraphicsProvider.this.drawImage(image, x, y, anchor);
                }

                @Override
                public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                    var xPoints = new int[]{x1, x2, x3};
                    var yPoints = new int[]{y1, y2, y3};
                    setARGBColor(argbColor);
                    seGraphics.drawPolygon(xPoints, yPoints, 3);
                }

                @Override
                public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                    var xPoints = new int[]{x1, x2, x3};
                    var yPoints = new int[]{y1, y2, y3};
                    setARGBColor(argbColor);
                    seGraphics.fillPolygon(xPoints, yPoints, 3);
                }

                @Override
                public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                    if (xPoints == null || yPoints == null) {
                        throw new NullPointerException();
                    }
                    setARGBColor(argbColor);
                    seGraphics.drawPolygon(xPoints, yPoints, argbColor);
                }

                @Override
                public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                    if (xPoints == null || yPoints == null) {
                        throw new NullPointerException();
                    }
                    setARGBColor(argbColor);
                    seGraphics.fillPolygon(xPoints, yPoints, nPoints);
                }

                @Override
                public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    if (pixels == null) {
                        throw new NullPointerException();
                    }
                    var image = new BufferedImage(width, height, DirectGraphicsUtils.getBufferedImageType(format));
                    image.setRGB(0, 0, width, height, pixels, offset, scanlength);
                    image = DirectGraphicsUtils.manipulateImage(image, manipulation);
                    HangarSwingGraphicsProvider.this.drawImage(new Image(image, true), x, y, 0);
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
                    return HangarSwingGraphicsProvider.this.seGraphics.getColor().getAlpha();
                }
            };
        }
        return directGraphics;
    }

    @Override
    public void translate(int x, int y) {
        seGraphics.translate(x, y);
        super.translate(x, y);
    }

    @Override
    public int getColor() {
        return seGraphics.getColor().getRGB();
    }

    @Override
    public int getRedComponent() {
        return seGraphics.getColor().getRed();
    }

    @Override
    public int getGreenComponent() {
        return seGraphics.getColor().getGreen();
    }

    @Override
    public int getBlueComponent() {
        return seGraphics.getColor().getBlue();
    }

    @Override
    public int getGrayScale() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
        seGraphics.setColor(new Color(red, green, blue));
    }

    @Override
    public void setColor(int RGB) {
        seGraphics.setColor(new Color(RGB, false));
    }

    @Override
    public void setGrayScale(int value) throws IllegalArgumentException {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException();
        }
        // TODO: write method logic
    }

    @Override
    public javax.microedition.lcdui.Font getFont() {
        if (seFont == null) {
            return javax.microedition.lcdui.Font.getDefaultFont();
        }
        return new javax.microedition.lcdui.Font(seFont);
    }

    @Override
    public void setStrokeStyle(int style) throws IllegalArgumentException {
        switch (style) {
            case SOLID -> {
                seGraphics.setStroke(new BasicStroke());
                selectedStroke = SOLID;
            }
            case DOTTED -> {
                var stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 1 }, 0);
                seGraphics.setStroke(stroke);
                selectedStroke = DOTTED;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public int getStrokeStyle() {
        return selectedStroke;
    }

    @Override
    public void setFont(javax.microedition.lcdui.Font font) {
        if (font != null) {
            int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, font.getSize());
            this.seFont = new java.awt.Font(java.awt.Font.SANS_SERIF, font.getStyle(), convertedSize);
        }
        else {
            this.seFont = javax.microedition.lcdui.Font.getDefaultFont().getSEFont();
        }
    }

    @Override
    public int getClipX() {
        var clipBounds = seGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.x;
    }

    @Override
    public int getClipY() {
        var clipBounds = seGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.y;
    }

    @Override
    public int getClipWidth() {
        var profile = HangarState.getProfileManager().getCurrentProfile();
        var clipBounds = seGraphics.getClipBounds();
        var resolution = profile.getResolution();
        return clipBounds == null ? resolution.width : clipBounds.width;
    }

    @Override
    public int getClipHeight() {
        var profile = HangarState.getProfileManager().getCurrentProfile();
        var clipBounds = seGraphics.getClipBounds();
        var resolution = profile.getResolution();
        return clipBounds == null ? resolution.height : clipBounds.height;
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        seGraphics.clipRect(x, y, width, height);
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        seGraphics.setClip(x, y, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        seGraphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            seGraphics.fillRect(x, y, width, height);
        }
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            seGraphics.drawRect(x, y, width, height);
        }
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            seGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            seGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    @Override
    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException {
        if (str == null) {
            throw new NullPointerException();
        }
        var meFont = getFont();
        x = FontUtils.alignX(meFont, str, x, anchor);
        y = FontUtils.alignY(meFont, str, y, anchor);
        seGraphics.setFont(seFont);
        seGraphics.drawString(str, x, y);
    }

    @Override
    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws StringIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        if (offset < str.length()) {
            if (offset + len > str.length()) {
                len = str.length() - offset;
            }
            String substring = str.substring(offset, offset + len);
            drawString(substring, x, y, anchor);
        }
    }

    @Override
    public void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException {
        drawString(String.valueOf(character), x, y, anchor);
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        drawSubstring(new String(data), offset, length, x, y, anchor);
    }

    @Override
    public void drawImage(Image img, int x, int y, int anchor) throws IllegalArgumentException, NullPointerException {
        if (img == null) {
            throw new NullPointerException();
        }
        x = ImageUtils.alignX(img.getWidth(), x, anchor);
        y = ImageUtils.alignY(img.getHeight(), y, anchor);
        seGraphics.drawImage(img.getSEImage(), x, y, null);
    }

    @Override
    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        if (src == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var imageRegion = src.getSEImage().getSubimage(x_src, y_src, width, height);
            var transformedImage = ImageUtils.transformImage(imageRegion, transform);
            x_dest = ImageUtils.alignX(transformedImage.getWidth(), x_dest, anchor);
            y_dest = ImageUtils.alignY(transformedImage.getHeight(), y_dest, anchor);
            seGraphics.drawImage(transformedImage, x_dest, y_dest, width, height, null);
        }
    }

    @Override
    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        x_dest = ImageUtils.alignX(width, x_dest, anchor);
        y_dest = ImageUtils.alignY(height, y_dest, anchor);
        seGraphics.copyArea(x_src, y_src, width, height, x_dest, y_dest);
    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        seGraphics.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (rgbData == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var image = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, rgbData, offset, scanlength);
            seGraphics.drawImage(image, x, y, null);
        }
    }

    @Override
    public int getDisplayColor(int color) {
        // TODO: check it
        return color;
    }

    @Override
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer instanceof HangarSwingOffscreenBuffer swingOffscreenBuffer) {
            seGraphics.drawImage(swingOffscreenBuffer.getBufferedImage(), 0, 0, null);
        }
    }
}