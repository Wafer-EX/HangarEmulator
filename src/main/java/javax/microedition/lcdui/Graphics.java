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

package javax.microedition.lcdui;

import things.HangarState;
import things.utils.microedition.FontUtils;
import things.utils.microedition.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphics {
    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;
    public static final int SOLID = 0;
    public static final int DOTTED = 1;

    private final Graphics2D seGraphics;
    private final BufferedImage seImage;
    private java.awt.Font seFont;
    private int translateX = 0, translateY = 0;
    private int selectedStroke = SOLID;

    public Graphics(java.awt.Graphics seGraphics, BufferedImage seImage) {
        this.seGraphics = (Graphics2D) seGraphics;
        this.seImage = seImage;
    }

    public java.awt.Graphics getSEGraphics() {
        return seGraphics;
    }

    public BufferedImage getSEImage() {
        return seImage;
    }

    public void translate(int x, int y) {
        seGraphics.translate(x, y);
        translateX += x;
        translateY += y;
    }

    public int getTranslateX() {
        return translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public int getColor() {
        return seGraphics.getColor().getRGB();
    }

    public int getRedComponent() {
        return seGraphics.getColor().getRed();
    }

    public int getGreenComponent() {
        return seGraphics.getColor().getGreen();
    }

    public int getBlueComponent() {
        return seGraphics.getColor().getBlue();
    }

    public int getGrayScale() {
        // TODO: write method logic
        return 0;
    }

    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
        seGraphics.setColor(new Color(red, green, blue));
    }

    public void setColor(int RGB) {
        seGraphics.setColor(new Color(RGB, false));
    }

    public void setGrayScale(int value) throws IllegalArgumentException {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException();
        }
        // TODO: write method logic
    }

    public Font getFont() {
        if (seFont == null) {
            return Font.getDefaultFont();
        }
        return new Font(seFont);
    }

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

    public int getStrokeStyle() {
        return selectedStroke;
    }

    public void setFont(Font font) {
        if (font != null) {
            int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, font.getSize());
            this.seFont = new java.awt.Font(java.awt.Font.SANS_SERIF, font.getStyle(), convertedSize);
        }
        else {
            this.seFont = Font.getDefaultFont().getSEFont();
        }
    }

    public int getClipX() {
        var clipBounds = seGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.x;
    }

    public int getClipY() {
        var clipBounds = seGraphics.getClipBounds();
        return clipBounds == null ? 0 : clipBounds.y;
    }

    public int getClipWidth() {
        var clipBounds = seGraphics.getClipBounds();
        var profile = HangarState.getProfile();
        return clipBounds == null ? profile.getResolution().width : clipBounds.width;
    }

    public int getClipHeight() {
        var clipBounds = seGraphics.getClipBounds();
        var profile = HangarState.getProfile();
        return clipBounds == null ? profile.getResolution().height : clipBounds.height;
    }

    public void clipRect(int x, int y, int width, int height) {
        seGraphics.clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        seGraphics.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        seGraphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            seGraphics.fillRect(x, y, width, height);
        }
    }

    public void drawRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            seGraphics.drawRect(x, y, width, height);
        }
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            seGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            seGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

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

    public void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException {
        drawString(String.valueOf(character), x, y, anchor);
    }

    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        drawSubstring(new String(data), offset, length, x, y, anchor);
    }

    public void drawImage(Image img, int x, int y, int anchor) throws IllegalArgumentException, NullPointerException {
        if (img == null) {
            throw new NullPointerException();
        }
        x = ImageUtils.alignX(img.getWidth(), x, anchor);
        y = ImageUtils.alignY(img.getHeight(), y, anchor);
        seGraphics.drawImage(img.getSEImage(), x, y, null);
    }

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        if (width > 0 && height > 0) {
            var imageRegion = src.getSEImage().getSubimage(x_src, y_src, width, height);
            var transformedImage = ImageUtils.transformImage(imageRegion, transform);
            x_dest = ImageUtils.alignX(transformedImage.getWidth(), x_dest, anchor);
            y_dest = ImageUtils.alignY(transformedImage.getHeight(), y_dest, anchor);
            seGraphics.drawImage(transformedImage, x_dest, y_dest, width, height, null);
        }
    }

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        x_dest = ImageUtils.alignX(width, x_dest, anchor);
        y_dest = ImageUtils.alignY(height, y_dest, anchor);
        seGraphics.copyArea(x_src, y_src, width, height, x_dest, y_dest);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        seGraphics.fillPolygon(xPoints, yPoints, 3);
    }

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

    public int getDisplayColor(int color) {
        return Color.WHITE.getRGB();
    }
}