package javax.microedition.lcdui;

import java.awt.*;

public class Graphics {
    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;
    public static final int SOLID = 0;
    public static final int DOTTED = 1;

    private final java.awt.Graphics graphics;
    private java.awt.Font font;

    public Graphics(java.awt.Graphics graphics) {
        this.graphics = graphics;
    }

    public void setColor(int red, int green, int blue) {
        graphics.setColor(new Color(red, green, blue));
    }

    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    public void drawImage(Image img, int x, int y, int anchor) {
        if ((anchor & Graphics.RIGHT) != 0) x -= img.getWidth();
        else if ((anchor & Graphics.HCENTER) != 0) x -= img.getWidth() / 2;

        if ((anchor & Graphics.BOTTOM) != 0) y -= img.getHeight();
        else if ((anchor & Graphics.VCENTER) != 0) y -= img.getHeight() / 2;

        graphics.drawImage(img.image, x, y, null);
    }

    public void setClip(int x, int y, int width, int height) {
        graphics.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public Font getFont() {
        return new Font(font);
    }

    public void setFont(Font font) {
        this.font = new java.awt.Font(java.awt.Font.SANS_SERIF, font.getStyle(), font.getSize());
    }

    public void drawString(String str, int x, int y, int anchor) {
        // TODO: text align
        graphics.drawString(str, x, y);
    }
}