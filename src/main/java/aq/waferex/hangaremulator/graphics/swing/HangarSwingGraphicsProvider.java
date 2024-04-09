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

package aq.waferex.hangaremulator.graphics.swing;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.graphics.HangarGraphicsProvider;
import aq.waferex.hangaremulator.graphics.HangarImage;
import aq.waferex.hangaremulator.graphics.HangarOffscreenBuffer;
import aq.waferex.hangaremulator.utils.microedition.FontUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.microedition.lcdui.Graphics.DOTTED;
import static javax.microedition.lcdui.Graphics.SOLID;

public class HangarSwingGraphicsProvider extends HangarGraphicsProvider {
    private final Graphics2D seGraphics;
    private java.awt.Font seFont;
    private int selectedStroke = SOLID;

    public HangarSwingGraphicsProvider(java.awt.Graphics seGraphics) {
        this.seGraphics = HangarState.applyAntiAliasing(seGraphics);
    }

    @Override
    public void translate(int x, int y) {
        seGraphics.translate(x, y);
        super.translate(x, y);
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
    public void setClip(int x, int y, int width, int height) {
        seGraphics.setClip(x, y, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        seGraphics.setColor(color);
        seGraphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean isFilled) {
        seGraphics.setColor(color);
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        if (isFilled) {
            seGraphics.fillPolygon(xPoints, yPoints, 3);
        }
        else {
            seGraphics.drawPolygon(xPoints, yPoints, 3);
        }
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color, boolean isFilled) {
        seGraphics.setColor(color);
        if (isFilled) {
            seGraphics.fillRect(x, y, width, height);
        }
        else {
            seGraphics.drawRect(x, y, width, height);
        }
    }

    @Override
    public void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color, boolean isFilled) {
        seGraphics.setColor(color);
        if (isFilled) {
            seGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
        else {
            seGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color, boolean isFilled) {
        seGraphics.setColor(color);
        if (isFilled) {
            seGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
        }
        else {
            seGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
        }
    }

    @Override
    public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, Color color, boolean isFilled) {
        seGraphics.setColor(color);
        if (isFilled) {
            seGraphics.fillPolygon(xPoints, yPoints, nPoints);
        }
        else {
            seGraphics.drawPolygon(xPoints, yPoints, nPoints);
        }
    }

    @Override
    public void drawString(String str, int x, int y, int anchor, Color color) throws NullPointerException, IllegalArgumentException {
        seGraphics.setColor(color);
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
    public void drawImage(HangarImage img, int x, int y) throws IllegalArgumentException, NullPointerException {
        if (img instanceof HangarSwingImage swingImage) {
            seGraphics.drawImage(swingImage.getBufferedImage(), x, y, null);
        }
    }

    @Override
    public void drawRegion(HangarImage src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        if (src instanceof HangarSwingImage swingImage) {
            var imageRegion = swingImage.getBufferedImage().getSubimage(x_src, y_src, width, height);
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
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer instanceof HangarSwingOffscreenBuffer swingOffscreenBuffer) {
            seGraphics.drawImage(swingOffscreenBuffer.getBufferedImage(), 0, 0, null);
        }
    }
}