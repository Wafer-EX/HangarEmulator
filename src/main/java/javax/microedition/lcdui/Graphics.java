package javax.microedition.lcdui;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import things.utils.FontUtils;
import things.utils.ImageUtils;

import javax.microedition.lcdui.game.Sprite;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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

    private final Graphics2D seGraphics;
    private java.awt.Font se_font;
    private int translateX = 0, translateY = 0;
    private int selectedStroke = SOLID;

    public Graphics(java.awt.Graphics seGraphics) {
        this.seGraphics = (Graphics2D) seGraphics;
    }

    public void translate(int x, int y) {
        seGraphics.translate(x, y);
        translateX += x;
        translateY += y;
    }

    public int getTranslateX() {
        return translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public void setColor(int red, int green, int blue) {
        seGraphics.setColor(new Color(red, green, blue));
    }

    public void setColor(int RGB) {
        seGraphics.setColor(new Color(RGB));
    }

    public Font getFont() {
        if (se_font == null) {
            return Font.getDefaultFont();
        }
        else {
            return new Font(se_font);
        }
    }

    public void setStrokeStyle(int style) {
        if (style == SOLID) {
            seGraphics.setStroke(new BasicStroke());
            selectedStroke = SOLID;
        }
        else if (style == DOTTED) {
            var stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 1 }, 0);
            seGraphics.setStroke(stroke);
            selectedStroke = DOTTED;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public int getStrokeStyle() {
        return selectedStroke;
    }

    public void setFont(Font font) {
        int convertedSize = FontUtils.convertSize(FontUtils.MICRO_EDITION, FontUtils.STANDART_EDITION, font.getSize());
        this.se_font = new java.awt.Font(java.awt.Font.SANS_SERIF, font.getStyle(), convertedSize);
    }

    public int getClipX() {
        return seGraphics.getClipBounds().x;
    }

    public int getClipY() {
        return seGraphics.getClipBounds().y;
    }

    public int getClipWidth() {
        return seGraphics.getClipBounds().width;
    }

    public int getClipHeight() {
        return seGraphics.getClipBounds().height;
    }

    public void clipRect(int x, int y, int width, int height) {
        seGraphics.clipRect(x, y, width, height);
    }

    public void setClip(int x, int y, int width, int height) {
        seGraphics.setClip(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        seGraphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRect(int x, int y, int width, int height) {
        seGraphics.fillRect(x, y, width, height);
    }

    public void drawRect(int x, int y, int width, int height) {
        seGraphics.drawRect(x, y, width, height);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        seGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        seGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        seGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawString(String str, int x, int y, int anchor) {
        var font = getFont();
        x = FontUtils.alignX(font, str, x, anchor);
        y = FontUtils.alignY(font, str, y, anchor);
        seGraphics.drawString(str, x, y);
    }

    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
        if (offset < str.length()) {
            if (offset + len > str.length()) {
                len = str.length() - offset;
            }
            String substring = str.substring(offset, offset + len);
            drawString(substring, x, y, anchor);
        }
    }

    public void drawChar(char character, int x, int y, int anchor) throws NotImplementedException {
        drawString(String.valueOf(character), x, y, anchor);
    }

    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws NotImplementedException {
        drawSubstring(new String(data), offset, length, x, y, anchor);
    }

    public void drawImage(Image img, int x, int y, int anchor) {
        x = ImageUtils.alignX(img.getWidth(), x, anchor);
        y = ImageUtils.alignY(img.getHeight(), y, anchor);
        seGraphics.drawImage(img.image, x, y, null);
    }

    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) {
        if (width > 0 && height > 0) {
            BufferedImage bufferedImage = (BufferedImage) src.image;
            BufferedImage subImage = bufferedImage.getSubimage(x_src, y_src, width, height);

            x_dest = ImageUtils.alignX(subImage.getWidth(), x_dest, anchor);
            y_dest = ImageUtils.alignY(subImage.getHeight(), y_dest, anchor);

            switch (transform) {
                // TODO: write rotation logic for TRANS_MIRROR_ROT90 and TRANS_MIRROR_ROT270
                case Sprite.TRANS_ROT180:
                    x_dest += width;
                    width = -width;
                    y_dest += height;
                    height = -height;
                    break;
                case Sprite.TRANS_MIRROR:
                    x_dest += width;
                    width = -width;
                    break;
                case Sprite.TRANS_MIRROR_ROT180:
                    y_dest += height;
                    height = -height;
                    break;
                case Sprite.TRANS_ROT90:
                    subImage = ImageUtils.rotateImage(bufferedImage, width, height, Math.PI / 2);
                    break;
                case Sprite.TRANS_ROT270:
                    subImage = ImageUtils.rotateImage(bufferedImage, width, height, Math.PI / 2 * 3);
                    break;
            }
            seGraphics.drawImage(subImage, x_dest, y_dest, width, height, null);
        }
    }

    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) {
        x_dest = ImageUtils.alignX(width, x_dest, anchor);
        y_dest = ImageUtils.alignY(height, y_dest, anchor);
        seGraphics.copyArea(x_src, y_src, width, height, x_dest, y_dest);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        seGraphics.fillPolygon(xPoints, yPoints, 3);
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