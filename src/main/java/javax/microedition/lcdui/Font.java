package javax.microedition.lcdui;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Font {
    public java.awt.Font font;

    public Font(java.awt.Font font) {
        this.font = font;
    }

    public static Font getFont(int face, int style, int size) {
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, style, size));
    }

    public static Font getDefaultFont() {
        return new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 14));
    }

    public int stringWidth(String str) {
        return font.getSize();
    }

    public int getBaselinePosition() {
        return font.getSize();
    }

    public int substringWidth(String str, int offset, int len) {
        return stringWidth(str.substring(offset, offset + len));
    }
}