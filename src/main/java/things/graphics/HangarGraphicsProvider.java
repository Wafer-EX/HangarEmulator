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
    // TODO: simplify
    DirectGraphics getDirectGraphics(Graphics graphics);

    void translate(int x, int y);

    int getTranslateX();

    int getTranslateY();

    int getColor();

    int getRedComponent();

    int getGreenComponent();

    int getBlueComponent();

    int getGrayScale();

    void setColor(int red, int green, int blue) throws IllegalArgumentException;

    void setColor(int RGB);

    void setGrayScale(int value) throws IllegalArgumentException;

    Font getFont();

    void setStrokeStyle(int style) throws IllegalArgumentException;

    int getStrokeStyle();

    void setFont(Font font);

    int getClipX();

    int getClipY();

    int getClipWidth();

    int getClipHeight();

    void clipRect(int x, int y, int width, int height);

    void setClip(int x, int y, int width, int height);

    void drawLine(int x1, int y1, int x2, int y2);

    void fillRect(int x, int y, int width, int height);

    void drawRect(int x, int y, int width, int height);

    void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

    void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

    void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle);

    void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);

    void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException;

    void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws StringIndexOutOfBoundsException, IllegalArgumentException, NullPointerException;

    void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException;

    void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException;

    void drawImage(Image img, int x, int y, int anchor) throws IllegalArgumentException, NullPointerException;

    void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException;

    void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException;

    void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3);

    void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException;

    int getDisplayColor(int color);

    void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer);
}