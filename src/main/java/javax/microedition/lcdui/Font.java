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

import things.HangarPanel;
import things.utils.FontUtils;

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

    public java.awt.Font se_font;

    public Font(java.awt.Font font) {
        this.se_font = font;
    }

    public static Font getDefaultFont() {
        return getFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM);
    }

    public static Font getFont(int face, int style, int size) {
        int convertedStyle = FontUtils.discardMismatchedStyle(style);
        int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, size);
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, convertedStyle, convertedSize));
    }

    public int getSize() {
        return FontUtils.convertSize(FontUtils.STANDART_EDITION, FontUtils.MICRO_EDITION, se_font.getSize());
    }

    public int getStyle() {
        return FontUtils.discardMismatchedStyle(se_font.getStyle());
    }

    public int getFace() {
        // TODO: font face converter
        return 0;
    }

    public boolean isPlain() {
        return se_font.isPlain();
    }

    public boolean isBold() {
        return se_font.isBold();
    }

    public boolean isItalic() {
        return se_font.isItalic();
    }

    public boolean isUnderlined() {
        // TODO: check font for underline
        return false;
    }

    public int getHeight() {
        var graphics = HangarPanel.getInstance().getGraphics();
        var metrics = graphics.getFontMetrics(se_font);
        return metrics.getHeight();
    }

    public int getBaselinePosition() {
        // TODO: it is correct?
        return FontUtils.convertSize(FontUtils.STANDART_EDITION, FontUtils.MICRO_EDITION, se_font.getSize());
    }

    public int charWidth(char ch) {
        var graphics = HangarPanel.getInstance().getGraphics();
        var metrics = graphics.getFontMetrics(se_font);
        return metrics.charWidth(ch);
    }

    public int charsWidth(char[] ch, int offset, int length) {
        // TODO: write method logic
        return 0;
    }

    public int stringWidth(String str) {
        var graphics = HangarPanel.getInstance().getGraphics();
        var metrics = graphics.getFontMetrics(se_font);
        return metrics.stringWidth(str);
    }

    public int substringWidth(String str, int offset, int len) {
        var substring = str.substring(offset, len + offset);
        return stringWidth(substring);
    }
}