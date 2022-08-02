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

package things.utils;

import things.ui.HangarFrame;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class FontUtils {
    public static final int MICRO_EDITION = 0;
    public static final int STANDART_EDITION = 1;
    public static final int SE_SIZE_SMALL = 12;
    public static final int SE_SIZE_MEDIUM = 14;
    public static final int SE_SIZE_LARGE = 16;

    public static int convertSize(int from, int to, int fontSize) {
        if (from != to) {
            if (from == MICRO_EDITION) {
                switch (fontSize) {
                    case Font.SIZE_SMALL: return SE_SIZE_SMALL;
                    case Font.SIZE_MEDIUM: return SE_SIZE_MEDIUM;
                    case Font.SIZE_LARGE: return SE_SIZE_LARGE;
                }
            }
            else if (from == STANDART_EDITION) {
                switch (fitSizeToSE(fontSize)) {
                    case SE_SIZE_SMALL: return Font.SIZE_SMALL;
                    case SE_SIZE_MEDIUM: return Font.SIZE_MEDIUM;
                    case SE_SIZE_LARGE: return Font.SIZE_LARGE;
                }
            }
        }
        return fontSize;
    }

    public static int discardMismatchedStyle(int fontStyle) {
        if (fontStyle != Font.STYLE_PLAIN && fontStyle != Font.STYLE_BOLD && fontStyle != Font.STYLE_ITALIC) {
            fontStyle = Font.STYLE_PLAIN;
        }
        return fontStyle;
    }

    public static int fitSizeToSE(int fontSize) {
        if (fontSize < SE_SIZE_SMALL) {
            fontSize = SE_SIZE_SMALL;
        }
        else if (fontSize > SE_SIZE_SMALL && fontSize < SE_SIZE_LARGE) {
            fontSize = SE_SIZE_MEDIUM;
        }
        else if (fontSize > SE_SIZE_LARGE) {
            fontSize = SE_SIZE_LARGE;
        }
        return fontSize;
    }

    public static int alignX(Font font, String str, int x, int anchor) {
        int stringWidth = font.stringWidth(str);
        int alignedX = x;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedX -= stringWidth;
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedX -= stringWidth >> 1;
        }
        return alignedX;
    }

    public static int alignY(Font font, String str, int y, int anchor) {
        var hangarPanel = HangarFrame.getInstance().getHangarGamePanel();
        var graphics = hangarPanel.getBuffer().getGraphics();
        var metrics = graphics.getFontMetrics(font.getSEFont());
        var stringSize = metrics.getStringBounds(str, graphics);

        int alignedY = y;
        if ((anchor & Graphics.BOTTOM) != 0) {
            alignedY -= stringSize.getMaxY();
        }
        else if (anchor == 0 || (anchor & Graphics.TOP) != 0) {
            alignedY += stringSize.getMaxY() + (font.getHeight() >> 1);
        }
        return alignedY;
    }
}