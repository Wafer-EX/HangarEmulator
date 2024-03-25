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

import things.graphics.HangarGraphicsProvider;

import java.awt.*;

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

    private final HangarGraphicsProvider graphicsProvider;
    private Color color;

    public Graphics(HangarGraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
        this.color = Color.BLACK;
    }

    public HangarGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    public void translate(int x, int y) {
        graphicsProvider.translate(x, y);
    }

    public int getTranslateX() {
        return graphicsProvider.getTranslateX();
    }

    public int getTranslateY() {
        return graphicsProvider.getTranslateY();
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
        return graphicsProvider.getGrayScale();
    }

    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        color = new Color(red, green, blue);
    }

    public void setColor(int RGB) {
        color = new Color(RGB);
    }

    public void setGrayScale(int value) throws IllegalArgumentException {
        graphicsProvider.setGrayScale(value);
    }

    public Font getFont() {
        return graphicsProvider.getFont();
    }

    public void setStrokeStyle(int style) throws IllegalArgumentException {
        graphicsProvider.setStrokeStyle(style);
    }

    public int getStrokeStyle() {
        return graphicsProvider.getStrokeStyle();
    }

    public void setFont(Font font) {
        graphicsProvider.setFont(font);
    }

    public int getClipX() {
        return graphicsProvider.getClipX();
    }

    public int getClipY() {
        return graphicsProvider.getClipY();
    }

    public int getClipWidth() {
        return graphicsProvider.getClipWidth();
    }

    public int getClipHeight() {
        return graphicsProvider.getClipHeight();
    }

    public void clipRect(int x, int y, int width, int height) {
        graphicsProvider.clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        graphicsProvider.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        graphicsProvider.drawLine(x1, y1, x2, y2, color);
    }

    public void fillRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            graphicsProvider.drawRectangle(x, y, width, height, color, true);
        }
    }

    public void drawRect(int x, int y, int width, int height) {
        if (width > 0 && height > 0) {
            graphicsProvider.drawRectangle(x, y, width, height, color, false);
        }
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            graphicsProvider.drawRoundRectangle(x, y, width, height, arcWidth, arcHeight, color, false);
        }
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (width > 0 && height > 0) {
            graphicsProvider.drawRoundRectangle(x, y, width, height, arcWidth, arcHeight, color, true);
        }
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphicsProvider.drawArc(x, y, width, height, startAngle, arcAngle, color, true);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphicsProvider.drawArc(x, y, width, height, startAngle, arcAngle, color, false);
    }

    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException {
        graphicsProvider.drawString(str, x, y, anchor, color);
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
        graphicsProvider.drawImage(img, x, y, anchor);
    }

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        graphicsProvider.drawRegion(src, x_src, y_src, width, height, transform, x_dest, y_dest, anchor);
    }

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        graphicsProvider.copyArea(x_src, y_src, width, height, x_dest, y_dest, anchor);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        graphicsProvider.drawTriangle(x1, y1, x2, y2, x3, y3, color, true);
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        graphicsProvider.drawRGB(rgbData, offset, scanlength, x, y, width, height, processAlpha);
    }

    public int getDisplayColor(int color) {
        // TODO: check it
        return color;
    }
}