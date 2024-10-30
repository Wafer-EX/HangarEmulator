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

import aq.waferex.hangaremulator.utils.microedition.FontUtils;

import java.util.Arrays;

public class Font {
    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_UNDERLINED = 4;
    public static final int SIZE_SMALL = 8;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_LARGE = 16;
    public static final int FACE_SYSTEM = 0;
    public static final int FACE_MONOSPACE = 32;
    public static final int FACE_PROPORTIONAL = 64;
    public static final int FONT_STATIC_TEXT = 0;
    public static final int FONT_INPUT_TEXT = 1;

    private final java.awt.Font seFont;
    private final int meFace;
    private final int meStyle;
    private final int meSize;

    private Font(java.awt.Font font, int meFace, int meStyle, int meSize) throws NullPointerException {
        this.seFont = font;
        this.meFace = meFace;
        this.meStyle = meStyle;
        this.meSize = meSize;
    }

    public java.awt.Font getSEFont() {
        return seFont;
    }

    public static Font getDefaultFont() {
        return getFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM);
    }

    public static Font getFont(int face, int style, int size) throws IllegalArgumentException {
        if (face != FACE_SYSTEM && face != FACE_MONOSPACE && face != FACE_PROPORTIONAL) {
            throw new IllegalArgumentException();
        }
        if (style != STYLE_PLAIN && style != STYLE_BOLD && style != STYLE_ITALIC && style != STYLE_UNDERLINED) {
            throw new IllegalArgumentException();
        }
        if (size != SIZE_SMALL && size != SIZE_MEDIUM && size != SIZE_LARGE) {
            throw new IllegalArgumentException();
        }

        int convertedStyle = FontUtils.discardMismatchedStyle(style);
        int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, size);
        var seFont = new java.awt.Font(java.awt.Font.SANS_SERIF, convertedStyle, convertedSize);

        return new Font(seFont, face, style, size);
    }

    public int getSize() {
        return meSize;
    }

    public int getStyle() {
        return meStyle;
    }

    public int getFace() {
        return meFace;
    }

    public boolean isPlain() {
        return seFont.isPlain();
    }

    public boolean isBold() {
        return seFont.isBold();
    }

    public boolean isItalic() {
        return seFont.isItalic();
    }

    public boolean isUnderlined() {
        // TODO: check underline
        return false;
    }

    public int getHeight() {
        var canvas = new java.awt.Canvas();
        var metrics = canvas.getFontMetrics(seFont);
        return metrics.getHeight();
    }

    public int getBaselinePosition() {
        return seFont.getSize();
    }

    public int charWidth(char ch) {
        var canvas = new java.awt.Canvas();
        var metrics = canvas.getFontMetrics(seFont);
        return metrics.charWidth(ch);
    }

    public int charsWidth(char[] ch, int offset, int length) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (ch == null) {
            throw new NullPointerException();
        }
        return substringWidth(Arrays.toString(ch), offset, length);
    }

    public int stringWidth(String str) throws NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        var canvas = new java.awt.Canvas();
        var metrics = canvas.getFontMetrics(seFont);
        return metrics.stringWidth(str);
    }

    public int substringWidth(String str, int offset, int len) throws StringIndexOutOfBoundsException, NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        var substring = str.substring(offset, len + offset);
        return stringWidth(substring);
    }
}