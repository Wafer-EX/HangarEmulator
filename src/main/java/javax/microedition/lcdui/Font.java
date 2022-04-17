package javax.microedition.lcdui;

import things.CanvasPanel;
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
        int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, size);
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, style, convertedSize));
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
        var metrics = CanvasPanel.getInstance().getGraphics().getFontMetrics(se_font);
        return metrics.getHeight();
    }

    public int getBaselinePosition() {
        // TODO: it is correct?
        return se_font.getSize();
    }

    public int charWidth(char ch) {
        var metrics = CanvasPanel.getInstance().getGraphics().getFontMetrics(se_font);
        return metrics.charWidth(ch);
    }

    public int charsWidth(char[] ch, int offset, int length) {
        // TODO: write method logic
        return 0;
    }

    public int stringWidth(String str) {
        var graphics = CanvasPanel.getInstance().getGraphics();
        var metrics = graphics.getFontMetrics(se_font);
        return metrics.stringWidth(str);
    }

    public int substringWidth(String str, int offset, int len) {
        var substring = str.substring(offset, len + offset);
        return stringWidth(substring);
    }
}