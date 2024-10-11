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

package javax.microedition.lcdui;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.utils.microedition.FontUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.swing.*;
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

    private Graphics2D graphics2D;

    private Font meFont;
    private int selectedStroke = SOLID;
    private int translateX = 0, translateY = 0;

    private Rectangle clip = new Rectangle(0, 0, 240, 320);
    private Color color;

    public Graphics(Graphics2D graphics2D) {
        this.graphics2D = HangarState.applyVectorAntiAliasing(graphics2D);
        this.color = Color.BLACK;
        this.meFont = Font.getDefaultFont();
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public void translate(int x, int y) {
        graphics2D.translate(x, y);
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
        return color.getRGB();
    }

    public int getRedComponent() {
        return color.getRed();
    }

    public int getGreenComponent() {
        return color.getGreen();
    }

    public int getBlueComponent() {
        return color.getBlue();
    }

    public int getGrayScale() {
        // TODO: check it
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
    }

    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        color = new Color(red, green, blue);
    }

    public void setColor(int RGB) {
        color = new Color(RGB);
    }

    public void setGrayScale(int value) throws IllegalArgumentException {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException();
        }
        // TODO: check it
        color = new Color(value);
    }

    public Font getFont() {
        return meFont;
    }

    public void setStrokeStyle(int style) throws IllegalArgumentException {
        switch (style) {
            case SOLID -> {
                graphics2D.setStroke(new BasicStroke());
                selectedStroke = SOLID;
            }
            case DOTTED -> {
                var stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 1 }, 0);
                graphics2D.setStroke(stroke);
                selectedStroke = DOTTED;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public int getStrokeStyle() {
        return selectedStroke;
    }

    public void setFont(Font font) {
        this.meFont = font;
    }

    public int getClipX() {
        return clip.x;
    }

    public int getClipY() {
        return clip.y;
    }

    public int getClipWidth() {
        return clip.width;
    }

    public int getClipHeight() {
        return clip.height;
    }

    public void clipRect(int x, int y, int width, int height) {
        clip = (Rectangle) clip.createIntersection(new Rectangle(x, y, width, height));
        setClip(clip.x, clip.y, clip.width, clip.height);
    }

    public void setClip(int x, int y, int width, int height) {
        clip = new Rectangle(x, y, width, height);
        graphics2D.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics2D.setColor(color);
        graphics2D.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            graphics2D.setColor(color);
            graphics2D.fillRect(x, y, width, height);
        }
    }

    public void drawRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            graphics2D.setColor(color);
            graphics2D.drawRect(x, y, width, height);
        }
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            graphics2D.setColor(color);
            graphics2D.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            graphics2D.setColor(color);
            graphics2D.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics2D.setColor(color);
        graphics2D.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics2D.setColor(color);
        graphics2D.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException {
        graphics2D.setColor(color);
        if (str == null) {
            throw new NullPointerException();
        }
        var meFont = getFont();
        x = FontUtils.alignX(meFont, str, x, anchor);
        y = FontUtils.alignY(meFont, str, y, anchor);
        graphics2D.setFont(meFont.getSEFont());
        graphics2D.drawString(str, x, y);
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
        graphics2D.drawImage(img.getBufferedImage(), x, y, null);
    }

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        if (src == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var imageRegion = src.getBufferedImage().getSubimage(x_src, y_src, width, height);
            var transformedImage = ImageUtils.transformImage(imageRegion, transform);
            x_dest = ImageUtils.alignX(transformedImage.getWidth(), x_dest, anchor);
            y_dest = ImageUtils.alignY(transformedImage.getHeight(), y_dest, anchor);
            graphics2D.drawImage(transformedImage, x_dest, y_dest, width, height, null);
        }
    }

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        x_dest = ImageUtils.alignX(width, x_dest, anchor);
        y_dest = ImageUtils.alignY(height, y_dest, anchor);
        graphics2D.copyArea(x_src, y_src, width, height, x_dest, y_dest);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        graphics2D.setColor(color);
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        graphics2D.fillPolygon(xPoints, yPoints, 3);
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (rgbData == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var image = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, rgbData, offset, scanlength);
            graphics2D.drawImage(image, x, y, null);
        }
    }

    public int getDisplayColor(int color) {
        // TODO: check it
        return color;
    }
}