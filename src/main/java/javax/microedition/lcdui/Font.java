package javax.microedition.lcdui;

import things.CanvasPanel;

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

    public java.awt.Font font;

    public Font(java.awt.Font font) {
        this.font = font;
    }

    public static Font getDefaultFont() {
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 14));
    }

    public static Font getFont(int face, int style, int size) {
        switch (size) {
            case SIZE_SMALL: size = 10; break;
            case SIZE_MEDIUM: size = 12; break;
            case SIZE_LARGE: size = 14; break;
        }
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, style, size));
    }

    public int getSize() {
        // TODO: font size converter
        return font.getSize();
    }

    public int getStyle() {
        return font.getStyle();
    }

    public int getFace() {
        // TODO: font face converter
        return 0;
    }

    public boolean isPlain() {
        return font.isPlain();
    }

    public boolean isBold() {
        return font.isBold();
    }

    public boolean isItalic() {
        return font.isItalic();
    }

    public boolean isUnderlined() {
        // TODO: check font for underline
        return false;
    }

    public int getHeight() {
        var metrics = CanvasPanel.getInstance().getGraphics().getFontMetrics(font);
        return metrics.getHeight();
    }

    public int getBaselinePosition() {
        // TODO: it is correct?
        return font.getSize();
    }

    public int charWidth(char ch) {
        var metrics = CanvasPanel.getInstance().getGraphics().getFontMetrics(font);
        return metrics.charWidth(ch);
    }

    public int charsWidth(char[] ch, int offset, int length) {
        // TODO: write method logic
        return 0;
    }

    public int stringWidth(String str) {
        var metrics = CanvasPanel.getInstance().getGraphics().getFontMetrics(font);
        return metrics.stringWidth(str);
    }

    public int substringWidth(String str, int offset, int len) {
        var substring = str.substring(offset, len + offset);
        return stringWidth(substring);
    }
}