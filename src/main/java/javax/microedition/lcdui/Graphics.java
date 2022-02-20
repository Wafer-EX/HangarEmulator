package javax.microedition.lcdui;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

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

    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

    public int getTranslateX() {
        // TODO: write method logic
        return 0;
    }

    public int getTranslateY() {
        // TODO: write method logic
        return 0;
    }

    public void setColor(int red, int green, int blue) {
        graphics.setColor(new Color(red, green, blue));
    }

    public void setColor(int RGB) {
        graphics.setColor(new Color(RGB));
    }

    public Font getFont() {
        return new Font(font);
    }

    public void setStrokeStyle(int style) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("setStrokeStyle");
    }

    public int getStrokeStyle() {
        return font.getStyle();
    }

    public void setFont(Font font) {
        this.font = new java.awt.Font(java.awt.Font.SANS_SERIF, font.getStyle(), font.getSize());
    }

    public int getClipX() {
        return graphics.getClip().getBounds().x;
    }

    public int getClipY() {
        return graphics.getClip().getBounds().y;
    }

    public int getClipWidth() {
        return graphics.getClip().getBounds().width;
    }

    public int getClipHeight() {
        return graphics.getClip().getBounds().height;
    }

    public void clipRect(int x, int y, int width, int height) {
        graphics.clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        graphics.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawString(String str, int x, int y, int anchor) {
        // TODO: write text align logic
        graphics.drawString(str, x, y);
    }

    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws NotImplementedException {
        // TODO: rewrite method logic
        drawString(str, x, y, anchor);
    }

    public void drawChar(char character, int x, int y, int anchor) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("drawChar");
    }

    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("drawChars");
    }

    public void drawImage(Image img, int x, int y, int anchor) {
        if ((anchor & Graphics.RIGHT) != 0) x -= img.getWidth();
        else if ((anchor & Graphics.HCENTER) != 0) x -= img.getWidth() / 2;

        if ((anchor & Graphics.BOTTOM) != 0) y -= img.getHeight();
        else if ((anchor & Graphics.VCENTER) != 0) y -= img.getHeight() / 2;

        graphics.drawImage(img.image, x, y, null);
    }

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("drawRegion");
    }

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) {
        // TODO: write align logic
        graphics.copyArea(x_src, y_src, width, height, x_dest, y_dest);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        graphics.fillPolygon(xPoints, yPoints, 3);
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("drawRGB");
    }

    public int getDisplayColor(int color) {
        // TODO: it is correct?
        return color;
    }
}