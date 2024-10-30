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

package aq.waferex.hangaremulator.utils.microedition;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class FontUtils {
    public static int discardMismatchedStyle(int fontStyle) {
        if (fontStyle != Font.STYLE_PLAIN && fontStyle != Font.STYLE_BOLD && fontStyle != Font.STYLE_ITALIC) {
            fontStyle = Font.STYLE_PLAIN;
        }
        return fontStyle;
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
        var canvas = new java.awt.Canvas();
        var metrics = canvas.getFontMetrics(font.getSEFont());
        var stringSize = metrics.getStringBounds(str, canvas.getGraphics());

        int alignedY = y;
        if ((anchor & Graphics.BOTTOM) != 0) {
            alignedY -= (int) stringSize.getMaxY();
        }
        else if (anchor == 0 || (anchor & Graphics.TOP) != 0) {
            alignedY += (int) (stringSize.getMaxY() + (font.getHeight() >> 1));
        }
        return alignedY;
    }
}