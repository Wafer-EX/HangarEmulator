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

package things.graphics;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import java.awt.*;

public abstract class HangarGraphicsProvider {
    private int translateX = 0, translateY = 0;

    public void translate(int x, int y) {
        translateX += x;
        translateY += y;
    }

    public int getTranslateX() {
        return translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public abstract int getGrayScale();

    public abstract void setGrayScale(int value) throws IllegalArgumentException;

    public abstract Font getFont();

    public abstract void setStrokeStyle(int style) throws IllegalArgumentException;

    public abstract int getStrokeStyle();

    public abstract void setFont(Font font);

    public abstract void setClip(int x, int y, int width, int height);

    public abstract void drawLine(int x1, int y1, int x2, int y2, Color color);

    public abstract void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean isFilled);

    public abstract void drawRectangle(int x, int y, int width, int height, Color color, boolean isFilled);

    public abstract void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color, boolean isFilled);

    public abstract void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color, boolean isFilled);

    public abstract void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, Color color, boolean isFilled);

    public abstract void drawString(String str, int x, int y, int anchor, Color color) throws NullPointerException, IllegalArgumentException;

    public abstract void drawImage(Image img, int x, int y) throws IllegalArgumentException, NullPointerException;

    public abstract void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException;

    public abstract void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException;

    public abstract void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException;

    public abstract void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer);
}