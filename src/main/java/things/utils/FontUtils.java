package things.utils;

import things.CanvasPanel;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class FontUtils {
    public static final int MICRO_EDITION = 0;
    public static final int STANDART_EDITION = 1;
    public static final int SE_SIZE_SMALL = 10;
    public static final int SE_SIZE_MEDIUM = 12;
    public static final int SE_SIZE_LARGE = 14;

    public static int ConvertSize(int from, int to, int fontSize) {
        if (from != to) {
            if (from == MICRO_EDITION) {
                switch (fontSize) {
                    case Font.SIZE_SMALL: fontSize = SE_SIZE_SMALL; break;
                    case Font.SIZE_MEDIUM: fontSize = SE_SIZE_MEDIUM; break;
                    case Font.SIZE_LARGE: fontSize = SE_SIZE_LARGE; break;
                }
            }
            else if (from == STANDART_EDITION) {
                fontSize = FitSizeToStandartEdition(fontSize);
                switch (fontSize) {
                    case SE_SIZE_SMALL: fontSize = Font.SIZE_SMALL;
                    case SE_SIZE_MEDIUM: fontSize = Font.SIZE_MEDIUM;
                    case SE_SIZE_LARGE: fontSize = Font.SIZE_LARGE;
                }
            }
        }
        return fontSize;
    }

    public static int FitSizeToStandartEdition(int fontSize) {
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

    public static int AlignX(Font font, String str, int x, int anchor) {
        int stringWidth = font.stringWidth(str);
        int alignedX = x;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedX -= stringWidth;
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedX -= stringWidth / 2;
        }
        return alignedX;
    }

    public static int AlignY(Font font, String str, int y, int anchor) {
        var graphics = CanvasPanel.getInstance().getGraphics();
        var metrics = graphics.getFontMetrics(font.se_font);
        var stringHeight = metrics.getStringBounds(str, graphics);

        int alignedY = y;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedY -= stringHeight.getMaxY();
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedY -= stringHeight.getCenterY();
        }
        return alignedY;
    }
}