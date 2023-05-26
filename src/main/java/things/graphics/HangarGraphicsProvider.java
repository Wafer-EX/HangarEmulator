/*
 * Copyright 2023 Kirill Lomakin
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

import com.nokia.mid.ui.DirectGraphics;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public interface HangarGraphicsProvider {
    public DirectGraphics getDirectGraphics(Graphics graphics);

    public void translate(int x, int y);

    public int getTranslateX();

    public int getTranslateY();

    public int getColor();

    public int getRedComponent();

    public int getGreenComponent();

    public int getBlueComponent();

    public int getGrayScale();

    public void setColor(int red, int green, int blue) throws IllegalArgumentException;

    public void setColor(int RGB);

    public void setGrayScale(int value) throws IllegalArgumentException;

    public Font getFont();

    public void setStrokeStyle(int style) throws IllegalArgumentException;

    public int getStrokeStyle();

    public void setFont(Font font);

    public int getClipX();

    public int getClipY();

    public int getClipWidth();

    public int getClipHeight();

    public void clipRect(int x, int y, int width, int height);

    public void setClip(int x, int y, int width, int height);

    public void drawLine(int x1, int y1, int x2, int y2);

    public void fillRect(int x, int y, int width, int height);

    public void drawRect(int x, int y, int width, int height);

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle);

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);

    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException;

    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws StringIndexOutOfBoundsException, IllegalArgumentException, NullPointerException;

    public void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException;

    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException;

    public void drawImage(Image img, int x, int y, int anchor) throws IllegalArgumentException, NullPointerException;

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException;

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException;

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3);

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException;

    public int getDisplayColor(int color);

    public void drawPolygon(int[] x, int[] y, int numberOfPoints);

    public void fillPolygon(int[] x, int[] y, int numberOfPoints);

    public int getAlphaComponent();

    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer);
}